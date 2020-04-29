package org.bouncycastle.pqc.crypto.xmss;

public final class WOTSPlusPublicKeyParameters {
    private final byte[][] publicKey;

    protected WOTSPlusPublicKeyParameters(WOTSPlusParameters wOTSPlusParameters, byte[][] bArr) {
        if (wOTSPlusParameters == null) {
            throw new NullPointerException("params == null");
        } else if (bArr == null) {
            throw new NullPointerException("publicKey == null");
        } else if (XMSSUtil.hasNullPointer(bArr)) {
            throw new NullPointerException("publicKey byte array == null");
        } else if (bArr.length != wOTSPlusParameters.getLen()) {
            throw new IllegalArgumentException("wrong publicKey size");
        } else {
            for (byte[] bArr2 : bArr) {
                if (bArr2.length != wOTSPlusParameters.getDigestSize()) {
                    throw new IllegalArgumentException("wrong publicKey format");
                }
            }
            this.publicKey = XMSSUtil.cloneArray(bArr);
        }
    }

    /* access modifiers changed from: protected */
    public byte[][] toByteArray() {
        return XMSSUtil.cloneArray(this.publicKey);
    }
}
