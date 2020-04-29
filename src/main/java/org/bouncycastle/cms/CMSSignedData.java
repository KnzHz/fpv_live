package org.bouncycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.SignedData;
import org.bouncycastle.asn1.cms.SignerInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cert.X509AttributeCertificateHolder;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.Encodable;
import org.bouncycastle.util.Store;

public class CMSSignedData implements Encodable {
    private static final CMSSignedHelper HELPER = CMSSignedHelper.INSTANCE;
    ContentInfo contentInfo;
    private Map hashes;
    CMSTypedData signedContent;
    SignedData signedData;
    SignerInformationStore signerInfoStore;

    public CMSSignedData(InputStream inputStream) throws CMSException {
        this(CMSUtils.readContentInfo(inputStream));
    }

    public CMSSignedData(Map map, ContentInfo contentInfo2) throws CMSException {
        this.hashes = map;
        this.contentInfo = contentInfo2;
        this.signedData = getSignedData();
    }

    public CMSSignedData(Map map, byte[] bArr) throws CMSException {
        this(map, CMSUtils.readContentInfo(bArr));
    }

    public CMSSignedData(ContentInfo contentInfo2) throws CMSException {
        this.contentInfo = contentInfo2;
        this.signedData = getSignedData();
        ASN1Encodable content = this.signedData.getEncapContentInfo().getContent();
        if (content == null) {
            this.signedContent = null;
        } else if (content instanceof ASN1OctetString) {
            this.signedContent = new CMSProcessableByteArray(this.signedData.getEncapContentInfo().getContentType(), ((ASN1OctetString) content).getOctets());
        } else {
            this.signedContent = new PKCS7ProcessableObject(this.signedData.getEncapContentInfo().getContentType(), content);
        }
    }

    public CMSSignedData(CMSProcessable cMSProcessable, InputStream inputStream) throws CMSException {
        this(cMSProcessable, CMSUtils.readContentInfo((InputStream) new ASN1InputStream(inputStream)));
    }

    public CMSSignedData(final CMSProcessable cMSProcessable, ContentInfo contentInfo2) throws CMSException {
        if (cMSProcessable instanceof CMSTypedData) {
            this.signedContent = (CMSTypedData) cMSProcessable;
        } else {
            this.signedContent = new CMSTypedData() {
                /* class org.bouncycastle.cms.CMSSignedData.AnonymousClass1 */

                public Object getContent() {
                    return cMSProcessable.getContent();
                }

                public ASN1ObjectIdentifier getContentType() {
                    return CMSSignedData.this.signedData.getEncapContentInfo().getContentType();
                }

                public void write(OutputStream outputStream) throws IOException, CMSException {
                    cMSProcessable.write(outputStream);
                }
            };
        }
        this.contentInfo = contentInfo2;
        this.signedData = getSignedData();
    }

    public CMSSignedData(CMSProcessable cMSProcessable, byte[] bArr) throws CMSException {
        this(cMSProcessable, CMSUtils.readContentInfo(bArr));
    }

    private CMSSignedData(CMSSignedData cMSSignedData) {
        this.signedData = cMSSignedData.signedData;
        this.contentInfo = cMSSignedData.contentInfo;
        this.signedContent = cMSSignedData.signedContent;
        this.signerInfoStore = cMSSignedData.signerInfoStore;
    }

    public CMSSignedData(byte[] bArr) throws CMSException {
        this(CMSUtils.readContentInfo(bArr));
    }

    private SignedData getSignedData() throws CMSException {
        try {
            return SignedData.getInstance(this.contentInfo.getContent());
        } catch (ClassCastException e) {
            throw new CMSException("Malformed content.", e);
        } catch (IllegalArgumentException e2) {
            throw new CMSException("Malformed content.", e2);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0039, code lost:
        if (r4.size() != 0) goto L_0x003b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0029, code lost:
        if (r3.size() != 0) goto L_0x002b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.bouncycastle.cms.CMSSignedData replaceCertificatesAndCRLs(org.bouncycastle.cms.CMSSignedData r7, org.bouncycastle.util.Store r8, org.bouncycastle.util.Store r9, org.bouncycastle.util.Store r10) throws org.bouncycastle.cms.CMSException {
        /*
            r0 = 0
            org.bouncycastle.cms.CMSSignedData r6 = new org.bouncycastle.cms.CMSSignedData
            r6.<init>(r7)
            if (r8 != 0) goto L_0x000a
            if (r9 == 0) goto L_0x0066
        L_0x000a:
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            if (r8 == 0) goto L_0x0018
            java.util.List r2 = org.bouncycastle.cms.CMSUtils.getCertificatesFromStore(r8)
            r1.addAll(r2)
        L_0x0018:
            if (r9 == 0) goto L_0x0021
            java.util.List r2 = org.bouncycastle.cms.CMSUtils.getAttributeCertificatesFromStore(r9)
            r1.addAll(r2)
        L_0x0021:
            org.bouncycastle.asn1.ASN1Set r3 = org.bouncycastle.cms.CMSUtils.createBerSetFromList(r1)
            int r1 = r3.size()
            if (r1 == 0) goto L_0x0066
        L_0x002b:
            if (r10 == 0) goto L_0x0064
            java.util.List r1 = org.bouncycastle.cms.CMSUtils.getCRLsFromStore(r10)
            org.bouncycastle.asn1.ASN1Set r4 = org.bouncycastle.cms.CMSUtils.createBerSetFromList(r1)
            int r1 = r4.size()
            if (r1 == 0) goto L_0x0064
        L_0x003b:
            org.bouncycastle.asn1.cms.SignedData r0 = new org.bouncycastle.asn1.cms.SignedData
            org.bouncycastle.asn1.cms.SignedData r1 = r7.signedData
            org.bouncycastle.asn1.ASN1Set r1 = r1.getDigestAlgorithms()
            org.bouncycastle.asn1.cms.SignedData r2 = r7.signedData
            org.bouncycastle.asn1.cms.ContentInfo r2 = r2.getEncapContentInfo()
            org.bouncycastle.asn1.cms.SignedData r5 = r7.signedData
            org.bouncycastle.asn1.ASN1Set r5 = r5.getSignerInfos()
            r0.<init>(r1, r2, r3, r4, r5)
            r6.signedData = r0
            org.bouncycastle.asn1.cms.ContentInfo r0 = new org.bouncycastle.asn1.cms.ContentInfo
            org.bouncycastle.asn1.cms.ContentInfo r1 = r6.contentInfo
            org.bouncycastle.asn1.ASN1ObjectIdentifier r1 = r1.getContentType()
            org.bouncycastle.asn1.cms.SignedData r2 = r6.signedData
            r0.<init>(r1, r2)
            r6.contentInfo = r0
            return r6
        L_0x0064:
            r4 = r0
            goto L_0x003b
        L_0x0066:
            r3 = r0
            goto L_0x002b
        */
        throw new UnsupportedOperationException("Method not decompiled: org.bouncycastle.cms.CMSSignedData.replaceCertificatesAndCRLs(org.bouncycastle.cms.CMSSignedData, org.bouncycastle.util.Store, org.bouncycastle.util.Store, org.bouncycastle.util.Store):org.bouncycastle.cms.CMSSignedData");
    }

    public static CMSSignedData replaceSigners(CMSSignedData cMSSignedData, SignerInformationStore signerInformationStore) {
        CMSSignedData cMSSignedData2 = new CMSSignedData(cMSSignedData);
        cMSSignedData2.signerInfoStore = signerInformationStore;
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        for (SignerInformation signerInformation : signerInformationStore.getSigners()) {
            aSN1EncodableVector.add(CMSSignedHelper.INSTANCE.fixAlgID(signerInformation.getDigestAlgorithmID()));
            aSN1EncodableVector2.add(signerInformation.toASN1Structure());
        }
        DERSet dERSet = new DERSet(aSN1EncodableVector);
        DERSet dERSet2 = new DERSet(aSN1EncodableVector2);
        ASN1Sequence aSN1Sequence = (ASN1Sequence) cMSSignedData.signedData.toASN1Primitive();
        ASN1EncodableVector aSN1EncodableVector3 = new ASN1EncodableVector();
        aSN1EncodableVector3.add(aSN1Sequence.getObjectAt(0));
        aSN1EncodableVector3.add(dERSet);
        for (int i = 2; i != aSN1Sequence.size() - 1; i++) {
            aSN1EncodableVector3.add(aSN1Sequence.getObjectAt(i));
        }
        aSN1EncodableVector3.add(dERSet2);
        cMSSignedData2.signedData = SignedData.getInstance(new BERSequence(aSN1EncodableVector3));
        cMSSignedData2.contentInfo = new ContentInfo(cMSSignedData2.contentInfo.getContentType(), cMSSignedData2.signedData);
        return cMSSignedData2;
    }

    private boolean verifyCounterSignature(SignerInformation signerInformation, SignerInformationVerifierProvider signerInformationVerifierProvider) throws OperatorCreationException, CMSException {
        if (!signerInformation.verify(signerInformationVerifierProvider.get(signerInformation.getSID()))) {
            return false;
        }
        for (SignerInformation signerInformation2 : signerInformation.getCounterSignatures().getSigners()) {
            if (!verifyCounterSignature(signerInformation2, signerInformationVerifierProvider)) {
                return false;
            }
        }
        return true;
    }

    public Store<X509AttributeCertificateHolder> getAttributeCertificates() {
        return HELPER.getAttributeCertificates(this.signedData.getCertificates());
    }

    public Store<X509CRLHolder> getCRLs() {
        return HELPER.getCRLs(this.signedData.getCRLs());
    }

    public Store<X509CertificateHolder> getCertificates() {
        return HELPER.getCertificates(this.signedData.getCertificates());
    }

    public Set<AlgorithmIdentifier> getDigestAlgorithmIDs() {
        HashSet hashSet = new HashSet(this.signedData.getDigestAlgorithms().size());
        Enumeration objects = this.signedData.getDigestAlgorithms().getObjects();
        while (objects.hasMoreElements()) {
            hashSet.add(AlgorithmIdentifier.getInstance(objects.nextElement()));
        }
        return Collections.unmodifiableSet(hashSet);
    }

    public byte[] getEncoded() throws IOException {
        return this.contentInfo.getEncoded();
    }

    public Store getOtherRevocationInfo(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return HELPER.getOtherRevocationInfo(aSN1ObjectIdentifier, this.signedData.getCRLs());
    }

    public CMSTypedData getSignedContent() {
        return this.signedContent;
    }

    public String getSignedContentTypeOID() {
        return this.signedData.getEncapContentInfo().getContentType().getId();
    }

    public SignerInformationStore getSignerInfos() {
        if (this.signerInfoStore == null) {
            ASN1Set signerInfos = this.signedData.getSignerInfos();
            ArrayList arrayList = new ArrayList();
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 == signerInfos.size()) {
                    break;
                }
                SignerInfo instance = SignerInfo.getInstance(signerInfos.getObjectAt(i2));
                ASN1ObjectIdentifier contentType = this.signedData.getEncapContentInfo().getContentType();
                if (this.hashes == null) {
                    arrayList.add(new SignerInformation(instance, contentType, this.signedContent, null));
                } else {
                    arrayList.add(new SignerInformation(instance, contentType, null, this.hashes.keySet().iterator().next() instanceof String ? (byte[]) this.hashes.get(instance.getDigestAlgorithm().getAlgorithm().getId()) : (byte[]) this.hashes.get(instance.getDigestAlgorithm().getAlgorithm())));
                }
                i = i2 + 1;
            }
            this.signerInfoStore = new SignerInformationStore(arrayList);
        }
        return this.signerInfoStore;
    }

    public int getVersion() {
        return this.signedData.getVersion().getValue().intValue();
    }

    public boolean isCertificateManagementMessage() {
        return this.signedData.getEncapContentInfo().getContent() == null && this.signedData.getSignerInfos().size() == 0;
    }

    public boolean isDetachedSignature() {
        return this.signedData.getEncapContentInfo().getContent() == null && this.signedData.getSignerInfos().size() > 0;
    }

    public ContentInfo toASN1Structure() {
        return this.contentInfo;
    }

    public boolean verifySignatures(SignerInformationVerifierProvider signerInformationVerifierProvider) throws CMSException {
        return verifySignatures(signerInformationVerifierProvider, false);
    }

    public boolean verifySignatures(SignerInformationVerifierProvider signerInformationVerifierProvider, boolean z) throws CMSException {
        for (SignerInformation signerInformation : getSignerInfos().getSigners()) {
            try {
                if (!signerInformation.verify(signerInformationVerifierProvider.get(signerInformation.getSID()))) {
                    return false;
                }
                if (!z) {
                    for (SignerInformation signerInformation2 : signerInformation.getCounterSignatures().getSigners()) {
                        if (!verifyCounterSignature(signerInformation2, signerInformationVerifierProvider)) {
                            return false;
                        }
                    }
                    continue;
                }
            } catch (OperatorCreationException e) {
                throw new CMSException("failure in verifier provider: " + e.getMessage(), e);
            }
        }
        return true;
    }
}
