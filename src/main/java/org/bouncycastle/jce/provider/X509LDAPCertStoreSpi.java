package org.bouncycastle.jce.provider;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CRL;
import java.security.cert.CRLSelector;
import java.security.cert.CertSelector;
import java.security.cert.CertStoreException;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertStoreSpi;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRLSelector;
import java.security.cert.X509CertSelector;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.x509.CertificatePair;
import org.bouncycastle.jce.X509LDAPCertStoreParameters;

public class X509LDAPCertStoreSpi extends CertStoreSpi {
    private static String LDAP_PROVIDER = "com.sun.jndi.ldap.LdapCtxFactory";
    private static String REFERRALS_IGNORE = "ignore";
    private static final String SEARCH_SECURITY_LEVEL = "none";
    private static final String URL_CONTEXT_PREFIX = "com.sun.jndi.url";
    private X509LDAPCertStoreParameters params;

    public X509LDAPCertStoreSpi(CertStoreParameters certStoreParameters) throws InvalidAlgorithmParameterException {
        super(certStoreParameters);
        if (!(certStoreParameters instanceof X509LDAPCertStoreParameters)) {
            throw new InvalidAlgorithmParameterException(X509LDAPCertStoreSpi.class.getName() + ": parameter must be a " + X509LDAPCertStoreParameters.class.getName() + " object\n" + certStoreParameters.toString());
        }
        this.params = (X509LDAPCertStoreParameters) certStoreParameters;
    }

    private Set certSubjectSerialSearch(X509CertSelector x509CertSelector, String[] strArr, String str, String str2) throws CertStoreException {
        String name;
        HashSet hashSet = new HashSet();
        try {
            if (x509CertSelector.getSubjectAsBytes() == null && x509CertSelector.getSubjectAsString() == null && x509CertSelector.getCertificate() == null) {
                hashSet.addAll(search(str, "*", strArr));
            } else {
                String str3 = null;
                if (x509CertSelector.getCertificate() != null) {
                    name = x509CertSelector.getCertificate().getSubjectX500Principal().getName("RFC1779");
                    str3 = x509CertSelector.getCertificate().getSerialNumber().toString();
                } else {
                    name = x509CertSelector.getSubjectAsBytes() != null ? new X500Principal(x509CertSelector.getSubjectAsBytes()).getName("RFC1779") : x509CertSelector.getSubjectAsString();
                }
                hashSet.addAll(search(str, "*" + parseDN(name, str2) + "*", strArr));
                if (!(str3 == null || this.params.getSearchForSerialNumberIn() == null)) {
                    hashSet.addAll(search(this.params.getSearchForSerialNumberIn(), "*" + str3 + "*", strArr));
                }
            }
            return hashSet;
        } catch (IOException e) {
            throw new CertStoreException("exception processing selector: " + e);
        }
    }

    private DirContext connectLDAP() throws NamingException {
        Properties properties = new Properties();
        properties.setProperty("java.naming.factory.initial", LDAP_PROVIDER);
        properties.setProperty("java.naming.batchsize", "0");
        properties.setProperty("java.naming.provider.url", this.params.getLdapURL());
        properties.setProperty("java.naming.factory.url.pkgs", URL_CONTEXT_PREFIX);
        properties.setProperty("java.naming.referral", REFERRALS_IGNORE);
        properties.setProperty("java.naming.security.authentication", "none");
        return new InitialDirContext(properties);
    }

    private Set getCACertificates(X509CertSelector x509CertSelector) throws CertStoreException {
        String[] strArr = {this.params.getCACertificateAttribute()};
        Set certSubjectSerialSearch = certSubjectSerialSearch(x509CertSelector, strArr, this.params.getLdapCACertificateAttributeName(), this.params.getCACertificateSubjectAttributeName());
        if (certSubjectSerialSearch.isEmpty()) {
            certSubjectSerialSearch.addAll(search(null, "*", strArr));
        }
        return certSubjectSerialSearch;
    }

    private Set getCrossCertificates(X509CertSelector x509CertSelector) throws CertStoreException {
        String[] strArr = {this.params.getCrossCertificateAttribute()};
        Set certSubjectSerialSearch = certSubjectSerialSearch(x509CertSelector, strArr, this.params.getLdapCrossCertificateAttributeName(), this.params.getCrossCertificateSubjectAttributeName());
        if (certSubjectSerialSearch.isEmpty()) {
            certSubjectSerialSearch.addAll(search(null, "*", strArr));
        }
        return certSubjectSerialSearch;
    }

    private Set getEndCertificates(X509CertSelector x509CertSelector) throws CertStoreException {
        return certSubjectSerialSearch(x509CertSelector, new String[]{this.params.getUserCertificateAttribute()}, this.params.getLdapUserCertificateAttributeName(), this.params.getUserCertificateSubjectAttributeName());
    }

    private String parseDN(String str, String str2) {
        int i;
        String substring = str.substring(str.toLowerCase().indexOf(str2.toLowerCase()) + str2.length());
        int indexOf = substring.indexOf(44);
        if (indexOf == -1) {
            indexOf = substring.length();
        }
        while (substring.charAt(i - 1) == '\\') {
            i = substring.indexOf(44, i + 1);
            if (i == -1) {
                i = substring.length();
            }
        }
        String substring2 = substring.substring(0, i);
        String substring3 = substring2.substring(substring2.indexOf(61) + 1);
        if (substring3.charAt(0) == ' ') {
            substring3 = substring3.substring(1);
        }
        if (substring3.startsWith("\"")) {
            substring3 = substring3.substring(1);
        }
        return substring3.endsWith("\"") ? substring3.substring(0, substring3.length() - 1) : substring3;
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x00e1 A[SYNTHETIC, Splitter:B:28:0x00e1] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Set search(java.lang.String r9, java.lang.String r10, java.lang.String[] r11) throws java.security.cert.CertStoreException {
        /*
            r8 = this;
            r0 = 0
            r3 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.StringBuilder r1 = r1.append(r9)
            java.lang.String r2 = "="
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.StringBuilder r1 = r1.append(r10)
            java.lang.String r1 = r1.toString()
            if (r9 != 0) goto L_0x001d
            r1 = r0
        L_0x001d:
            java.util.HashSet r4 = new java.util.HashSet
            r4.<init>()
            javax.naming.directory.DirContext r2 = r8.connectLDAP()     // Catch:{ Exception -> 0x00f7, all -> 0x00f4 }
            javax.naming.directory.SearchControls r5 = new javax.naming.directory.SearchControls     // Catch:{ Exception -> 0x00c1 }
            r5.<init>()     // Catch:{ Exception -> 0x00c1 }
            r0 = 2
            r5.setSearchScope(r0)     // Catch:{ Exception -> 0x00c1 }
            r6 = 0
            r5.setCountLimit(r6)     // Catch:{ Exception -> 0x00c1 }
        L_0x0034:
            int r0 = r11.length     // Catch:{ Exception -> 0x00c1 }
            if (r3 >= r0) goto L_0x00ea
            r0 = 1
            java.lang.String[] r6 = new java.lang.String[r0]     // Catch:{ Exception -> 0x00c1 }
            r0 = 0
            r7 = r11[r3]     // Catch:{ Exception -> 0x00c1 }
            r6[r0] = r7     // Catch:{ Exception -> 0x00c1 }
            r5.setReturningAttributes(r6)     // Catch:{ Exception -> 0x00c1 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c1 }
            r0.<init>()     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r7 = "(&("
            java.lang.StringBuilder r0 = r0.append(r7)     // Catch:{ Exception -> 0x00c1 }
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r7 = ")("
            java.lang.StringBuilder r0 = r0.append(r7)     // Catch:{ Exception -> 0x00c1 }
            r7 = 0
            r7 = r6[r7]     // Catch:{ Exception -> 0x00c1 }
            java.lang.StringBuilder r0 = r0.append(r7)     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r7 = "=*))"
            java.lang.StringBuilder r0 = r0.append(r7)     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00c1 }
            if (r1 != 0) goto L_0x008b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00c1 }
            r0.<init>()     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r7 = "("
            java.lang.StringBuilder r0 = r0.append(r7)     // Catch:{ Exception -> 0x00c1 }
            r7 = 0
            r6 = r6[r7]     // Catch:{ Exception -> 0x00c1 }
            java.lang.StringBuilder r0 = r0.append(r6)     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r6 = "=*)"
            java.lang.StringBuilder r0 = r0.append(r6)     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r0 = r0.toString()     // Catch:{ Exception -> 0x00c1 }
        L_0x008b:
            org.bouncycastle.jce.X509LDAPCertStoreParameters r6 = r8.params     // Catch:{ Exception -> 0x00c1 }
            java.lang.String r6 = r6.getBaseDN()     // Catch:{ Exception -> 0x00c1 }
            javax.naming.NamingEnumeration r6 = r2.search(r6, r0, r5)     // Catch:{ Exception -> 0x00c1 }
        L_0x0095:
            boolean r0 = r6.hasMoreElements()     // Catch:{ Exception -> 0x00c1 }
            if (r0 == 0) goto L_0x00e5
            java.lang.Object r0 = r6.next()     // Catch:{ Exception -> 0x00c1 }
            javax.naming.directory.SearchResult r0 = (javax.naming.directory.SearchResult) r0     // Catch:{ Exception -> 0x00c1 }
            javax.naming.directory.Attributes r0 = r0.getAttributes()     // Catch:{ Exception -> 0x00c1 }
            javax.naming.NamingEnumeration r0 = r0.getAll()     // Catch:{ Exception -> 0x00c1 }
            java.lang.Object r0 = r0.next()     // Catch:{ Exception -> 0x00c1 }
            javax.naming.directory.Attribute r0 = (javax.naming.directory.Attribute) r0     // Catch:{ Exception -> 0x00c1 }
            javax.naming.NamingEnumeration r0 = r0.getAll()     // Catch:{ Exception -> 0x00c1 }
        L_0x00b3:
            boolean r7 = r0.hasMore()     // Catch:{ Exception -> 0x00c1 }
            if (r7 == 0) goto L_0x0095
            java.lang.Object r7 = r0.next()     // Catch:{ Exception -> 0x00c1 }
            r4.add(r7)     // Catch:{ Exception -> 0x00c1 }
            goto L_0x00b3
        L_0x00c1:
            r0 = move-exception
            r1 = r0
        L_0x00c3:
            java.security.cert.CertStoreException r0 = new java.security.cert.CertStoreException     // Catch:{ all -> 0x00dd }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00dd }
            r3.<init>()     // Catch:{ all -> 0x00dd }
            java.lang.String r4 = "Error getting results from LDAP directory "
            java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x00dd }
            java.lang.StringBuilder r1 = r3.append(r1)     // Catch:{ all -> 0x00dd }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x00dd }
            r0.<init>(r1)     // Catch:{ all -> 0x00dd }
            throw r0     // Catch:{ all -> 0x00dd }
        L_0x00dd:
            r0 = move-exception
            r1 = r0
        L_0x00df:
            if (r2 == 0) goto L_0x00e4
            r2.close()     // Catch:{ Exception -> 0x00f2 }
        L_0x00e4:
            throw r1
        L_0x00e5:
            int r0 = r3 + 1
            r3 = r0
            goto L_0x0034
        L_0x00ea:
            if (r2 == 0) goto L_0x00ef
            r2.close()     // Catch:{ Exception -> 0x00f0 }
        L_0x00ef:
            return r4
        L_0x00f0:
            r0 = move-exception
            goto L_0x00ef
        L_0x00f2:
            r0 = move-exception
            goto L_0x00e4
        L_0x00f4:
            r1 = move-exception
            r2 = r0
            goto L_0x00df
        L_0x00f7:
            r1 = move-exception
            r2 = r0
            goto L_0x00c3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.jce.provider.X509LDAPCertStoreSpi.search(java.lang.String, java.lang.String, java.lang.String[]):java.util.Set");
    }

    public Collection engineGetCRLs(CRLSelector cRLSelector) throws CertStoreException {
        String parseDN;
        String[] strArr = {this.params.getCertificateRevocationListAttribute()};
        if (!(cRLSelector instanceof X509CRLSelector)) {
            throw new CertStoreException("selector is not a X509CRLSelector");
        }
        X509CRLSelector x509CRLSelector = (X509CRLSelector) cRLSelector;
        HashSet hashSet = new HashSet();
        String ldapCertificateRevocationListAttributeName = this.params.getLdapCertificateRevocationListAttributeName();
        HashSet<byte[]> hashSet2 = new HashSet();
        if (x509CRLSelector.getIssuerNames() != null) {
            for (Object obj : x509CRLSelector.getIssuerNames()) {
                if (obj instanceof String) {
                    parseDN = parseDN((String) obj, this.params.getCertificateRevocationListIssuerAttributeName());
                } else {
                    parseDN = parseDN(new X500Principal((byte[]) obj).getName("RFC1779"), this.params.getCertificateRevocationListIssuerAttributeName());
                }
                hashSet2.addAll(search(ldapCertificateRevocationListAttributeName, "*" + parseDN + "*", strArr));
            }
        } else {
            hashSet2.addAll(search(ldapCertificateRevocationListAttributeName, "*", strArr));
        }
        hashSet2.addAll(search(null, "*", strArr));
        try {
            CertificateFactory instance = CertificateFactory.getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME);
            for (byte[] bArr : hashSet2) {
                CRL generateCRL = instance.generateCRL(new ByteArrayInputStream(bArr));
                if (x509CRLSelector.match(generateCRL)) {
                    hashSet.add(generateCRL);
                }
            }
            return hashSet;
        } catch (Exception e) {
            throw new CertStoreException("CRL cannot be constructed from LDAP result " + e);
        }
    }

    public Collection engineGetCertificates(CertSelector certSelector) throws CertStoreException {
        if (!(certSelector instanceof X509CertSelector)) {
            throw new CertStoreException("selector is not a X509CertSelector");
        }
        X509CertSelector x509CertSelector = (X509CertSelector) certSelector;
        HashSet hashSet = new HashSet();
        Set<byte[]> endCertificates = getEndCertificates(x509CertSelector);
        endCertificates.addAll(getCACertificates(x509CertSelector));
        endCertificates.addAll(getCrossCertificates(x509CertSelector));
        try {
            CertificateFactory instance = CertificateFactory.getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME);
            for (byte[] bArr : endCertificates) {
                if (!(bArr == null || bArr.length == 0)) {
                    ArrayList<byte[]> arrayList = new ArrayList();
                    arrayList.add(bArr);
                    try {
                        CertificatePair instance2 = CertificatePair.getInstance(new ASN1InputStream(bArr).readObject());
                        arrayList.clear();
                        if (instance2.getForward() != null) {
                            arrayList.add(instance2.getForward().getEncoded());
                        }
                        if (instance2.getReverse() != null) {
                            arrayList.add(instance2.getReverse().getEncoded());
                        }
                    } catch (IOException | IllegalArgumentException e) {
                    }
                    for (byte[] bArr2 : arrayList) {
                        try {
                            Certificate generateCertificate = instance.generateCertificate(new ByteArrayInputStream(bArr2));
                            if (x509CertSelector.match(generateCertificate)) {
                                hashSet.add(generateCertificate);
                            }
                        } catch (Exception e2) {
                        }
                    }
                }
            }
            return hashSet;
        } catch (Exception e3) {
            throw new CertStoreException("certificate cannot be constructed from LDAP result: " + e3);
        }
    }
}
