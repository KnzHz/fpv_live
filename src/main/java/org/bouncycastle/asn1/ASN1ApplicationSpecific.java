package org.bouncycastle.asn1;

import java.io.IOException;
import kotlin.jvm.internal.ByteCompanionObject;
import org.bouncycastle.util.Arrays;

public abstract class ASN1ApplicationSpecific extends ASN1Primitive {
    protected final boolean isConstructed;
    protected final byte[] octets;
    protected final int tag;

    ASN1ApplicationSpecific(boolean z, int i, byte[] bArr) {
        this.isConstructed = z;
        this.tag = i;
        this.octets = Arrays.clone(bArr);
    }

    public static ASN1ApplicationSpecific getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1ApplicationSpecific)) {
            return (ASN1ApplicationSpecific) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return getInstance(ASN1Primitive.fromByteArray((byte[]) obj));
            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to construct object from byte[]: " + e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("unknown object in getInstance: " + obj.getClass().getName());
        }
    }

    protected static int getLengthOfHeader(byte[] bArr) {
        byte b = bArr[1] & 255;
        if (b == 128 || b <= Byte.MAX_VALUE) {
            return 2;
        }
        byte b2 = b & ByteCompanionObject.MAX_VALUE;
        if (b2 <= 4) {
            return b2 + 2;
        }
        throw new IllegalStateException("DER length more than 4 bytes: " + ((int) b2));
    }

    private byte[] replaceTagNumber(int i, byte[] bArr) throws IOException {
        int i2;
        if ((bArr[0] & 31) == 31) {
            i2 = 2;
            byte b = bArr[1] & 255;
            if ((b & ByteCompanionObject.MAX_VALUE) == 0) {
                throw new ASN1ParsingException("corrupted stream - invalid high tag number found");
            }
            int i3 = 0;
            while (b >= 0 && (b & 128) != 0) {
                i3 = ((b & ByteCompanionObject.MAX_VALUE) | i3) << 7;
                b = bArr[i2] & 255;
                i2++;
            }
        } else {
            i2 = 1;
        }
        byte[] bArr2 = new byte[((bArr.length - i2) + 1)];
        System.arraycopy(bArr, i2, bArr2, 1, bArr2.length - 1);
        bArr2[0] = (byte) i;
        return bArr2;
    }

    /* access modifiers changed from: package-private */
    public boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (!(aSN1Primitive instanceof ASN1ApplicationSpecific)) {
            return false;
        }
        ASN1ApplicationSpecific aSN1ApplicationSpecific = (ASN1ApplicationSpecific) aSN1Primitive;
        return this.isConstructed == aSN1ApplicationSpecific.isConstructed && this.tag == aSN1ApplicationSpecific.tag && Arrays.areEqual(this.octets, aSN1ApplicationSpecific.octets);
    }

    /* access modifiers changed from: package-private */
    public void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        int i = 64;
        if (this.isConstructed) {
            i = 96;
        }
        aSN1OutputStream.writeEncoded(i, this.tag, this.octets);
    }

    /* access modifiers changed from: package-private */
    public int encodedLength() throws IOException {
        return StreamUtil.calculateTagLength(this.tag) + StreamUtil.calculateBodyLength(this.octets.length) + this.octets.length;
    }

    public int getApplicationTag() {
        return this.tag;
    }

    public byte[] getContents() {
        return Arrays.clone(this.octets);
    }

    public ASN1Primitive getObject() throws IOException {
        return ASN1Primitive.fromByteArray(getContents());
    }

    public ASN1Primitive getObject(int i) throws IOException {
        if (i >= 31) {
            throw new IOException("unsupported tag number");
        }
        byte[] encoded = getEncoded();
        byte[] replaceTagNumber = replaceTagNumber(i, encoded);
        if ((encoded[0] & 32) != 0) {
            replaceTagNumber[0] = (byte) (replaceTagNumber[0] | 32);
        }
        return ASN1Primitive.fromByteArray(replaceTagNumber);
    }

    public int hashCode() {
        return ((this.isConstructed ? 1 : 0) ^ this.tag) ^ Arrays.hashCode(this.octets);
    }

    public boolean isConstructed() {
        return this.isConstructed;
    }
}
