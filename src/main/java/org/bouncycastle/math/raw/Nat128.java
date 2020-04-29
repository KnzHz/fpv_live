package org.bouncycastle.math.raw;

import java.math.BigInteger;
import org.bouncycastle.util.Pack;

public abstract class Nat128 {
    private static final long M = 4294967295L;

    public static int add(int[] iArr, int[] iArr2, int[] iArr3) {
        long j = 0 + (((long) iArr[0]) & 4294967295L) + (((long) iArr2[0]) & 4294967295L);
        iArr3[0] = (int) j;
        long j2 = (j >>> 32) + (((long) iArr[1]) & 4294967295L) + (((long) iArr2[1]) & 4294967295L);
        iArr3[1] = (int) j2;
        long j3 = (j2 >>> 32) + (((long) iArr[2]) & 4294967295L) + (((long) iArr2[2]) & 4294967295L);
        iArr3[2] = (int) j3;
        long j4 = (j3 >>> 32) + (((long) iArr[3]) & 4294967295L) + (((long) iArr2[3]) & 4294967295L);
        iArr3[3] = (int) j4;
        return (int) (j4 >>> 32);
    }

    public static int addBothTo(int[] iArr, int[] iArr2, int[] iArr3) {
        long j = 0 + (((long) iArr[0]) & 4294967295L) + (((long) iArr2[0]) & 4294967295L) + (((long) iArr3[0]) & 4294967295L);
        iArr3[0] = (int) j;
        long j2 = (j >>> 32) + (((long) iArr[1]) & 4294967295L) + (((long) iArr2[1]) & 4294967295L) + (((long) iArr3[1]) & 4294967295L);
        iArr3[1] = (int) j2;
        long j3 = (j2 >>> 32) + (((long) iArr[2]) & 4294967295L) + (((long) iArr2[2]) & 4294967295L) + (((long) iArr3[2]) & 4294967295L);
        iArr3[2] = (int) j3;
        long j4 = (j3 >>> 32) + (((long) iArr[3]) & 4294967295L) + (((long) iArr2[3]) & 4294967295L) + (((long) iArr3[3]) & 4294967295L);
        iArr3[3] = (int) j4;
        return (int) (j4 >>> 32);
    }

    public static int addTo(int[] iArr, int i, int[] iArr2, int i2, int i3) {
        long j = (((long) i3) & 4294967295L) + (((long) iArr[i + 0]) & 4294967295L) + (((long) iArr2[i2 + 0]) & 4294967295L);
        iArr2[i2 + 0] = (int) j;
        long j2 = (j >>> 32) + (((long) iArr[i + 1]) & 4294967295L) + (((long) iArr2[i2 + 1]) & 4294967295L);
        iArr2[i2 + 1] = (int) j2;
        long j3 = (j2 >>> 32) + (((long) iArr[i + 2]) & 4294967295L) + (((long) iArr2[i2 + 2]) & 4294967295L);
        iArr2[i2 + 2] = (int) j3;
        long j4 = (j3 >>> 32) + (((long) iArr[i + 3]) & 4294967295L) + (((long) iArr2[i2 + 3]) & 4294967295L);
        iArr2[i2 + 3] = (int) j4;
        return (int) (j4 >>> 32);
    }

    public static int addTo(int[] iArr, int[] iArr2) {
        long j = 0 + (((long) iArr[0]) & 4294967295L) + (((long) iArr2[0]) & 4294967295L);
        iArr2[0] = (int) j;
        long j2 = (j >>> 32) + (((long) iArr[1]) & 4294967295L) + (((long) iArr2[1]) & 4294967295L);
        iArr2[1] = (int) j2;
        long j3 = (j2 >>> 32) + (((long) iArr[2]) & 4294967295L) + (((long) iArr2[2]) & 4294967295L);
        iArr2[2] = (int) j3;
        long j4 = (j3 >>> 32) + (((long) iArr[3]) & 4294967295L) + (((long) iArr2[3]) & 4294967295L);
        iArr2[3] = (int) j4;
        return (int) (j4 >>> 32);
    }

    public static int addToEachOther(int[] iArr, int i, int[] iArr2, int i2) {
        long j = 0 + (((long) iArr[i + 0]) & 4294967295L) + (((long) iArr2[i2 + 0]) & 4294967295L);
        iArr[i + 0] = (int) j;
        iArr2[i2 + 0] = (int) j;
        long j2 = (j >>> 32) + (((long) iArr[i + 1]) & 4294967295L) + (((long) iArr2[i2 + 1]) & 4294967295L);
        iArr[i + 1] = (int) j2;
        iArr2[i2 + 1] = (int) j2;
        long j3 = (j2 >>> 32) + (((long) iArr[i + 2]) & 4294967295L) + (((long) iArr2[i2 + 2]) & 4294967295L);
        iArr[i + 2] = (int) j3;
        iArr2[i2 + 2] = (int) j3;
        long j4 = (j3 >>> 32) + (((long) iArr[i + 3]) & 4294967295L) + (((long) iArr2[i2 + 3]) & 4294967295L);
        iArr[i + 3] = (int) j4;
        iArr2[i2 + 3] = (int) j4;
        return (int) (j4 >>> 32);
    }

    public static void copy(int[] iArr, int[] iArr2) {
        iArr2[0] = iArr[0];
        iArr2[1] = iArr[1];
        iArr2[2] = iArr[2];
        iArr2[3] = iArr[3];
    }

    public static void copy64(long[] jArr, long[] jArr2) {
        jArr2[0] = jArr[0];
        jArr2[1] = jArr[1];
    }

    public static int[] create() {
        return new int[4];
    }

    public static long[] create64() {
        return new long[2];
    }

    public static int[] createExt() {
        return new int[8];
    }

    public static long[] createExt64() {
        return new long[4];
    }

    public static boolean diff(int[] iArr, int i, int[] iArr2, int i2, int[] iArr3, int i3) {
        boolean gte = gte(iArr, i, iArr2, i2);
        if (gte) {
            sub(iArr, i, iArr2, i2, iArr3, i3);
        } else {
            sub(iArr2, i2, iArr, i, iArr3, i3);
        }
        return gte;
    }

    public static boolean eq(int[] iArr, int[] iArr2) {
        for (int i = 3; i >= 0; i--) {
            if (iArr[i] != iArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean eq64(long[] jArr, long[] jArr2) {
        for (int i = 1; i >= 0; i--) {
            if (jArr[i] != jArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static int[] fromBigInteger(BigInteger bigInteger) {
        if (bigInteger.signum() < 0 || bigInteger.bitLength() > 128) {
            throw new IllegalArgumentException();
        }
        int[] create = create();
        int i = 0;
        while (bigInteger.signum() != 0) {
            create[i] = bigInteger.intValue();
            bigInteger = bigInteger.shiftRight(32);
            i++;
        }
        return create;
    }

    public static long[] fromBigInteger64(BigInteger bigInteger) {
        if (bigInteger.signum() < 0 || bigInteger.bitLength() > 128) {
            throw new IllegalArgumentException();
        }
        long[] create64 = create64();
        int i = 0;
        while (bigInteger.signum() != 0) {
            create64[i] = bigInteger.longValue();
            bigInteger = bigInteger.shiftRight(64);
            i++;
        }
        return create64;
    }

    public static int getBit(int[] iArr, int i) {
        if (i == 0) {
            return iArr[0] & 1;
        }
        int i2 = i >> 5;
        if (i2 < 0 || i2 >= 4) {
            return 0;
        }
        return (iArr[i2] >>> (i & 31)) & 1;
    }

    public static boolean gte(int[] iArr, int i, int[] iArr2, int i2) {
        for (int i3 = 3; i3 >= 0; i3--) {
            int i4 = iArr[i + i3] ^ Integer.MIN_VALUE;
            int i5 = iArr2[i2 + i3] ^ Integer.MIN_VALUE;
            if (i4 < i5) {
                return false;
            }
            if (i4 > i5) {
                return true;
            }
        }
        return true;
    }

    public static boolean gte(int[] iArr, int[] iArr2) {
        for (int i = 3; i >= 0; i--) {
            int i2 = iArr[i] ^ Integer.MIN_VALUE;
            int i3 = iArr2[i] ^ Integer.MIN_VALUE;
            if (i2 < i3) {
                return false;
            }
            if (i2 > i3) {
                return true;
            }
        }
        return true;
    }

    public static boolean isOne(int[] iArr) {
        if (iArr[0] != 1) {
            return false;
        }
        for (int i = 1; i < 4; i++) {
            if (iArr[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isOne64(long[] jArr) {
        if (jArr[0] != 1) {
            return false;
        }
        for (int i = 1; i < 2; i++) {
            if (jArr[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isZero(int[] iArr) {
        for (int i = 0; i < 4; i++) {
            if (iArr[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isZero64(long[] jArr) {
        for (int i = 0; i < 2; i++) {
            if (jArr[i] != 0) {
                return false;
            }
        }
        return true;
    }

    public static void mul(int[] iArr, int i, int[] iArr2, int i2, int[] iArr3, int i3) {
        long j = 4294967295L & ((long) iArr2[i2 + 0]);
        long j2 = 4294967295L & ((long) iArr2[i2 + 1]);
        long j3 = 4294967295L & ((long) iArr2[i2 + 2]);
        long j4 = 4294967295L & ((long) iArr2[i2 + 3]);
        long j5 = ((long) iArr[i + 0]) & 4294967295L;
        long j6 = 0 + (j5 * j);
        iArr3[i3 + 0] = (int) j6;
        long j7 = (j6 >>> 32) + (j5 * j2);
        iArr3[i3 + 1] = (int) j7;
        long j8 = (j7 >>> 32) + (j5 * j3);
        iArr3[i3 + 2] = (int) j8;
        long j9 = (j8 >>> 32) + (j5 * j4);
        iArr3[i3 + 3] = (int) j9;
        iArr3[i3 + 4] = (int) (j9 >>> 32);
        for (int i4 = 1; i4 < 4; i4++) {
            i3++;
            long j10 = ((long) iArr[i + i4]) & 4294967295L;
            long j11 = 0 + (j10 * j) + (((long) iArr3[i3 + 0]) & 4294967295L);
            iArr3[i3 + 0] = (int) j11;
            long j12 = (j11 >>> 32) + (j10 * j2) + (((long) iArr3[i3 + 1]) & 4294967295L);
            iArr3[i3 + 1] = (int) j12;
            long j13 = (j12 >>> 32) + (j10 * j3) + (((long) iArr3[i3 + 2]) & 4294967295L);
            iArr3[i3 + 2] = (int) j13;
            long j14 = (j13 >>> 32) + (j10 * j4) + (((long) iArr3[i3 + 3]) & 4294967295L);
            iArr3[i3 + 3] = (int) j14;
            iArr3[i3 + 4] = (int) (j14 >>> 32);
        }
    }

    public static void mul(int[] iArr, int[] iArr2, int[] iArr3) {
        long j = 4294967295L & ((long) iArr2[0]);
        long j2 = 4294967295L & ((long) iArr2[1]);
        long j3 = 4294967295L & ((long) iArr2[2]);
        long j4 = 4294967295L & ((long) iArr2[3]);
        long j5 = ((long) iArr[0]) & 4294967295L;
        long j6 = 0 + (j5 * j);
        iArr3[0] = (int) j6;
        long j7 = (j6 >>> 32) + (j5 * j2);
        iArr3[1] = (int) j7;
        long j8 = (j7 >>> 32) + (j5 * j3);
        iArr3[2] = (int) j8;
        long j9 = (j8 >>> 32) + (j5 * j4);
        iArr3[3] = (int) j9;
        iArr3[4] = (int) (j9 >>> 32);
        for (int i = 1; i < 4; i++) {
            long j10 = ((long) iArr[i]) & 4294967295L;
            long j11 = 0 + (j10 * j) + (((long) iArr3[i + 0]) & 4294967295L);
            iArr3[i + 0] = (int) j11;
            long j12 = (j11 >>> 32) + (j10 * j2) + (((long) iArr3[i + 1]) & 4294967295L);
            iArr3[i + 1] = (int) j12;
            long j13 = (j12 >>> 32) + (j10 * j3) + (((long) iArr3[i + 2]) & 4294967295L);
            iArr3[i + 2] = (int) j13;
            long j14 = (j13 >>> 32) + (j10 * j4) + (((long) iArr3[i + 3]) & 4294967295L);
            iArr3[i + 3] = (int) j14;
            iArr3[i + 4] = (int) (j14 >>> 32);
        }
    }

    public static long mul33Add(int i, int[] iArr, int i2, int[] iArr2, int i3, int[] iArr3, int i4) {
        long j = ((long) i) & 4294967295L;
        long j2 = ((long) iArr[i2 + 0]) & 4294967295L;
        long j3 = 0 + (j * j2) + (((long) iArr2[i3 + 0]) & 4294967295L);
        iArr3[i4 + 0] = (int) j3;
        long j4 = ((long) iArr[i2 + 1]) & 4294967295L;
        long j5 = (j3 >>> 32) + j2 + (j * j4) + (((long) iArr2[i3 + 1]) & 4294967295L);
        iArr3[i4 + 1] = (int) j5;
        long j6 = ((long) iArr[i2 + 2]) & 4294967295L;
        long j7 = (j5 >>> 32) + j4 + (j * j6) + (((long) iArr2[i3 + 2]) & 4294967295L);
        iArr3[i4 + 2] = (int) j7;
        long j8 = ((long) iArr[i2 + 3]) & 4294967295L;
        long j9 = (j7 >>> 32) + (j * j8) + j6 + (((long) iArr2[i3 + 3]) & 4294967295L);
        iArr3[i4 + 3] = (int) j9;
        return (j9 >>> 32) + j8;
    }

    public static int mul33DWordAdd(int i, long j, int[] iArr, int i2) {
        long j2 = ((long) i) & 4294967295L;
        long j3 = 4294967295L & j;
        long j4 = 0 + (j2 * j3) + (((long) iArr[i2 + 0]) & 4294967295L);
        iArr[i2 + 0] = (int) j4;
        long j5 = j >>> 32;
        long j6 = (j4 >>> 32) + (j2 * j5) + j3 + (((long) iArr[i2 + 1]) & 4294967295L);
        iArr[i2 + 1] = (int) j6;
        long j7 = (j6 >>> 32) + (((long) iArr[i2 + 2]) & 4294967295L) + j5;
        iArr[i2 + 2] = (int) j7;
        long j8 = (j7 >>> 32) + (((long) iArr[i2 + 3]) & 4294967295L);
        iArr[i2 + 3] = (int) j8;
        return (int) (j8 >>> 32);
    }

    public static int mul33WordAdd(int i, int i2, int[] iArr, int i3) {
        long j = ((long) i2) & 4294967295L;
        long j2 = ((((long) i) & 4294967295L) * j) + (((long) iArr[i3 + 0]) & 4294967295L) + 0;
        iArr[i3 + 0] = (int) j2;
        long j3 = (j2 >>> 32) + j + (((long) iArr[i3 + 1]) & 4294967295L);
        iArr[i3 + 1] = (int) j3;
        long j4 = (j3 >>> 32) + (((long) iArr[i3 + 2]) & 4294967295L);
        iArr[i3 + 2] = (int) j4;
        if ((j4 >>> 32) == 0) {
            return 0;
        }
        return Nat.incAt(4, iArr, i3, 3);
    }

    public static int mulAddTo(int[] iArr, int i, int[] iArr2, int i2, int[] iArr3, int i3) {
        long j = ((long) iArr2[i2 + 0]) & 4294967295L;
        long j2 = ((long) iArr2[i2 + 1]) & 4294967295L;
        long j3 = ((long) iArr2[i2 + 2]) & 4294967295L;
        long j4 = ((long) iArr2[i2 + 3]) & 4294967295L;
        long j5 = 0;
        for (int i4 = 0; i4 < 4; i4++) {
            long j6 = ((long) iArr[i + i4]) & 4294967295L;
            long j7 = 0 + (j6 * j) + (((long) iArr3[i3 + 0]) & 4294967295L);
            iArr3[i3 + 0] = (int) j7;
            long j8 = (j7 >>> 32) + (j6 * j2) + (((long) iArr3[i3 + 1]) & 4294967295L);
            iArr3[i3 + 1] = (int) j8;
            long j9 = (j8 >>> 32) + (j6 * j3) + (((long) iArr3[i3 + 2]) & 4294967295L);
            iArr3[i3 + 2] = (int) j9;
            long j10 = (j9 >>> 32) + (j6 * j4) + (((long) iArr3[i3 + 3]) & 4294967295L);
            iArr3[i3 + 3] = (int) j10;
            long j11 = j5 + (((long) iArr3[i3 + 4]) & 4294967295L) + (j10 >>> 32);
            iArr3[i3 + 4] = (int) j11;
            j5 = j11 >>> 32;
            i3++;
        }
        return (int) j5;
    }

    public static int mulAddTo(int[] iArr, int[] iArr2, int[] iArr3) {
        long j = ((long) iArr2[0]) & 4294967295L;
        long j2 = ((long) iArr2[1]) & 4294967295L;
        long j3 = ((long) iArr2[2]) & 4294967295L;
        long j4 = ((long) iArr2[3]) & 4294967295L;
        long j5 = 0;
        for (int i = 0; i < 4; i++) {
            long j6 = ((long) iArr[i]) & 4294967295L;
            long j7 = 0 + (j6 * j) + (((long) iArr3[i + 0]) & 4294967295L);
            iArr3[i + 0] = (int) j7;
            long j8 = (j7 >>> 32) + (j6 * j2) + (((long) iArr3[i + 1]) & 4294967295L);
            iArr3[i + 1] = (int) j8;
            long j9 = (j8 >>> 32) + (j6 * j3) + (((long) iArr3[i + 2]) & 4294967295L);
            iArr3[i + 2] = (int) j9;
            long j10 = (j9 >>> 32) + (j6 * j4) + (((long) iArr3[i + 3]) & 4294967295L);
            iArr3[i + 3] = (int) j10;
            long j11 = j5 + (((long) iArr3[i + 4]) & 4294967295L) + (j10 >>> 32);
            iArr3[i + 4] = (int) j11;
            j5 = j11 >>> 32;
        }
        return (int) j5;
    }

    public static int mulWord(int i, int[] iArr, int[] iArr2, int i2) {
        long j = 0;
        long j2 = ((long) i) & 4294967295L;
        int i3 = 0;
        do {
            long j3 = j + ((((long) iArr[i3]) & 4294967295L) * j2);
            iArr2[i2 + i3] = (int) j3;
            j = j3 >>> 32;
            i3++;
        } while (i3 < 4);
        return (int) j;
    }

    public static int mulWordAddExt(int i, int[] iArr, int i2, int[] iArr2, int i3) {
        long j = ((long) i) & 4294967295L;
        long j2 = 0 + ((((long) iArr[i2 + 0]) & 4294967295L) * j) + (((long) iArr2[i3 + 0]) & 4294967295L);
        iArr2[i3 + 0] = (int) j2;
        long j3 = (j2 >>> 32) + ((((long) iArr[i2 + 1]) & 4294967295L) * j) + (((long) iArr2[i3 + 1]) & 4294967295L);
        iArr2[i3 + 1] = (int) j3;
        long j4 = (j3 >>> 32) + ((((long) iArr[i2 + 2]) & 4294967295L) * j) + (((long) iArr2[i3 + 2]) & 4294967295L);
        iArr2[i3 + 2] = (int) j4;
        long j5 = (j4 >>> 32) + (j * (((long) iArr[i2 + 3]) & 4294967295L)) + (((long) iArr2[i3 + 3]) & 4294967295L);
        iArr2[i3 + 3] = (int) j5;
        return (int) (j5 >>> 32);
    }

    public static int mulWordDwordAdd(int i, long j, int[] iArr, int i2) {
        long j2 = ((long) i) & 4294967295L;
        long j3 = 0 + ((4294967295L & j) * j2) + (((long) iArr[i2 + 0]) & 4294967295L);
        iArr[i2 + 0] = (int) j3;
        long j4 = (j3 >>> 32) + (j2 * (j >>> 32)) + (((long) iArr[i2 + 1]) & 4294967295L);
        iArr[i2 + 1] = (int) j4;
        long j5 = (j4 >>> 32) + (((long) iArr[i2 + 2]) & 4294967295L);
        iArr[i2 + 2] = (int) j5;
        if ((j5 >>> 32) == 0) {
            return 0;
        }
        return Nat.incAt(4, iArr, i2, 3);
    }

    public static int mulWordsAdd(int i, int i2, int[] iArr, int i3) {
        long j = ((((long) i) & 4294967295L) * (((long) i2) & 4294967295L)) + (((long) iArr[i3 + 0]) & 4294967295L) + 0;
        iArr[i3 + 0] = (int) j;
        long j2 = (j >>> 32) + (((long) iArr[i3 + 1]) & 4294967295L);
        iArr[i3 + 1] = (int) j2;
        if ((j2 >>> 32) == 0) {
            return 0;
        }
        return Nat.incAt(4, iArr, i3, 2);
    }

    public static void square(int[] iArr, int i, int[] iArr2, int i2) {
        long j = ((long) iArr[i + 0]) & 4294967295L;
        int i3 = 3;
        int i4 = 8;
        int i5 = 0;
        while (true) {
            int i6 = i3 - 1;
            long j2 = ((long) iArr[i3 + i]) & 4294967295L;
            long j3 = j2 * j2;
            int i7 = i4 - 1;
            iArr2[i2 + i7] = (i5 << 31) | ((int) (j3 >>> 33));
            i4 = i7 - 1;
            iArr2[i2 + i4] = (int) (j3 >>> 1);
            i5 = (int) j3;
            if (i6 <= 0) {
                long j4 = j * j;
                iArr2[i2 + 0] = (int) j4;
                long j5 = ((long) iArr[i + 1]) & 4294967295L;
                long j6 = ((((long) (i5 << 31)) & 4294967295L) | (j4 >>> 33)) + (j5 * j);
                int i8 = (int) j6;
                iArr2[i2 + 1] = (((int) (j4 >>> 32)) & 1) | (i8 << 1);
                int i9 = i8 >>> 31;
                long j7 = (j6 >>> 32) + (((long) iArr2[i2 + 2]) & 4294967295L);
                long j8 = ((long) iArr[i + 2]) & 4294967295L;
                long j9 = j7 + (j8 * j);
                int i10 = (int) j9;
                iArr2[i2 + 2] = i9 | (i10 << 1);
                int i11 = i10 >>> 31;
                long j10 = (j9 >>> 32) + (j8 * j5) + (((long) iArr2[i2 + 3]) & 4294967295L);
                long j11 = (j10 >>> 32) + (((long) iArr2[i2 + 4]) & 4294967295L);
                long j12 = ((long) iArr[i + 3]) & 4294967295L;
                long j13 = (((long) iArr2[i2 + 5]) & 4294967295L) + (j11 >>> 32);
                long j14 = (j10 & 4294967295L) + (j * j12);
                int i12 = (int) j14;
                iArr2[i2 + 3] = i11 | (i12 << 1);
                int i13 = i12 >>> 31;
                long j15 = (j14 >>> 32) + (j12 * j5) + (j11 & 4294967295L);
                long j16 = (j15 >>> 32) + (j12 * j8) + (j13 & 4294967295L);
                long j17 = (j16 >>> 32) + (((long) iArr2[i2 + 6]) & 4294967295L) + (j13 >>> 32);
                int i14 = (int) j15;
                iArr2[i2 + 4] = i13 | (i14 << 1);
                int i15 = i14 >>> 31;
                int i16 = (int) j16;
                iArr2[i2 + 5] = i15 | (i16 << 1);
                int i17 = i16 >>> 31;
                int i18 = (int) j17;
                iArr2[i2 + 6] = i17 | (i18 << 1);
                iArr2[i2 + 7] = (i18 >>> 31) | ((iArr2[i2 + 7] + ((int) (j17 >>> 32))) << 1);
                return;
            }
            i3 = i6;
        }
    }

    public static void square(int[] iArr, int[] iArr2) {
        long j = ((long) iArr[0]) & 4294967295L;
        int i = 3;
        int i2 = 8;
        int i3 = 0;
        while (true) {
            int i4 = i - 1;
            long j2 = ((long) iArr[i]) & 4294967295L;
            long j3 = j2 * j2;
            int i5 = i2 - 1;
            iArr2[i5] = (i3 << 31) | ((int) (j3 >>> 33));
            i2 = i5 - 1;
            iArr2[i2] = (int) (j3 >>> 1);
            i3 = (int) j3;
            if (i4 <= 0) {
                long j4 = j * j;
                iArr2[0] = (int) j4;
                long j5 = ((long) iArr[1]) & 4294967295L;
                long j6 = ((((long) (i3 << 31)) & 4294967295L) | (j4 >>> 33)) + (j5 * j);
                int i6 = (int) j6;
                iArr2[1] = (((int) (j4 >>> 32)) & 1) | (i6 << 1);
                int i7 = i6 >>> 31;
                long j7 = (j6 >>> 32) + (((long) iArr2[2]) & 4294967295L);
                long j8 = ((long) iArr[2]) & 4294967295L;
                long j9 = j7 + (j8 * j);
                int i8 = (int) j9;
                iArr2[2] = i7 | (i8 << 1);
                int i9 = i8 >>> 31;
                long j10 = (j9 >>> 32) + (j8 * j5) + (((long) iArr2[3]) & 4294967295L);
                long j11 = (j10 >>> 32) + (((long) iArr2[4]) & 4294967295L);
                long j12 = ((long) iArr[3]) & 4294967295L;
                long j13 = (((long) iArr2[5]) & 4294967295L) + (j11 >>> 32);
                long j14 = (j10 & 4294967295L) + (j * j12);
                int i10 = (int) j14;
                iArr2[3] = i9 | (i10 << 1);
                int i11 = i10 >>> 31;
                long j15 = (j14 >>> 32) + (j12 * j5) + (j11 & 4294967295L);
                long j16 = (j15 >>> 32) + (j12 * j8) + (j13 & 4294967295L);
                long j17 = (j16 >>> 32) + (((long) iArr2[6]) & 4294967295L) + (j13 >>> 32);
                int i12 = (int) j15;
                iArr2[4] = i11 | (i12 << 1);
                int i13 = i12 >>> 31;
                int i14 = (int) (j16 & 4294967295L);
                iArr2[5] = i13 | (i14 << 1);
                int i15 = i14 >>> 31;
                int i16 = (int) j17;
                iArr2[6] = i15 | (i16 << 1);
                iArr2[7] = (i16 >>> 31) | ((iArr2[7] + ((int) (j17 >>> 32))) << 1);
                return;
            }
            i = i4;
        }
    }

    public static int sub(int[] iArr, int i, int[] iArr2, int i2, int[] iArr3, int i3) {
        long j = 0 + ((((long) iArr[i + 0]) & 4294967295L) - (((long) iArr2[i2 + 0]) & 4294967295L));
        iArr3[i3 + 0] = (int) j;
        long j2 = (j >> 32) + ((((long) iArr[i + 1]) & 4294967295L) - (((long) iArr2[i2 + 1]) & 4294967295L));
        iArr3[i3 + 1] = (int) j2;
        long j3 = (j2 >> 32) + ((((long) iArr[i + 2]) & 4294967295L) - (((long) iArr2[i2 + 2]) & 4294967295L));
        iArr3[i3 + 2] = (int) j3;
        long j4 = (j3 >> 32) + ((((long) iArr[i + 3]) & 4294967295L) - (((long) iArr2[i2 + 3]) & 4294967295L));
        iArr3[i3 + 3] = (int) j4;
        return (int) (j4 >> 32);
    }

    public static int sub(int[] iArr, int[] iArr2, int[] iArr3) {
        long j = 0 + ((((long) iArr[0]) & 4294967295L) - (((long) iArr2[0]) & 4294967295L));
        iArr3[0] = (int) j;
        long j2 = (j >> 32) + ((((long) iArr[1]) & 4294967295L) - (((long) iArr2[1]) & 4294967295L));
        iArr3[1] = (int) j2;
        long j3 = (j2 >> 32) + ((((long) iArr[2]) & 4294967295L) - (((long) iArr2[2]) & 4294967295L));
        iArr3[2] = (int) j3;
        long j4 = (j3 >> 32) + ((((long) iArr[3]) & 4294967295L) - (((long) iArr2[3]) & 4294967295L));
        iArr3[3] = (int) j4;
        return (int) (j4 >> 32);
    }

    public static int subBothFrom(int[] iArr, int[] iArr2, int[] iArr3) {
        long j = 0 + (((((long) iArr3[0]) & 4294967295L) - (((long) iArr[0]) & 4294967295L)) - (((long) iArr2[0]) & 4294967295L));
        iArr3[0] = (int) j;
        long j2 = (j >> 32) + (((((long) iArr3[1]) & 4294967295L) - (((long) iArr[1]) & 4294967295L)) - (((long) iArr2[1]) & 4294967295L));
        iArr3[1] = (int) j2;
        long j3 = (j2 >> 32) + (((((long) iArr3[2]) & 4294967295L) - (((long) iArr[2]) & 4294967295L)) - (((long) iArr2[2]) & 4294967295L));
        iArr3[2] = (int) j3;
        long j4 = (j3 >> 32) + (((((long) iArr3[3]) & 4294967295L) - (((long) iArr[3]) & 4294967295L)) - (((long) iArr2[3]) & 4294967295L));
        iArr3[3] = (int) j4;
        return (int) (j4 >> 32);
    }

    public static int subFrom(int[] iArr, int i, int[] iArr2, int i2) {
        long j = 0 + ((((long) iArr2[i2 + 0]) & 4294967295L) - (((long) iArr[i + 0]) & 4294967295L));
        iArr2[i2 + 0] = (int) j;
        long j2 = (j >> 32) + ((((long) iArr2[i2 + 1]) & 4294967295L) - (((long) iArr[i + 1]) & 4294967295L));
        iArr2[i2 + 1] = (int) j2;
        long j3 = (j2 >> 32) + ((((long) iArr2[i2 + 2]) & 4294967295L) - (((long) iArr[i + 2]) & 4294967295L));
        iArr2[i2 + 2] = (int) j3;
        long j4 = (j3 >> 32) + ((((long) iArr2[i2 + 3]) & 4294967295L) - (((long) iArr[i + 3]) & 4294967295L));
        iArr2[i2 + 3] = (int) j4;
        return (int) (j4 >> 32);
    }

    public static int subFrom(int[] iArr, int[] iArr2) {
        long j = 0 + ((((long) iArr2[0]) & 4294967295L) - (((long) iArr[0]) & 4294967295L));
        iArr2[0] = (int) j;
        long j2 = (j >> 32) + ((((long) iArr2[1]) & 4294967295L) - (((long) iArr[1]) & 4294967295L));
        iArr2[1] = (int) j2;
        long j3 = (j2 >> 32) + ((((long) iArr2[2]) & 4294967295L) - (((long) iArr[2]) & 4294967295L));
        iArr2[2] = (int) j3;
        long j4 = (j3 >> 32) + ((((long) iArr2[3]) & 4294967295L) - (((long) iArr[3]) & 4294967295L));
        iArr2[3] = (int) j4;
        return (int) (j4 >> 32);
    }

    public static BigInteger toBigInteger(int[] iArr) {
        byte[] bArr = new byte[16];
        for (int i = 0; i < 4; i++) {
            int i2 = iArr[i];
            if (i2 != 0) {
                Pack.intToBigEndian(i2, bArr, (3 - i) << 2);
            }
        }
        return new BigInteger(1, bArr);
    }

    public static BigInteger toBigInteger64(long[] jArr) {
        byte[] bArr = new byte[16];
        for (int i = 0; i < 2; i++) {
            long j = jArr[i];
            if (j != 0) {
                Pack.longToBigEndian(j, bArr, (1 - i) << 3);
            }
        }
        return new BigInteger(1, bArr);
    }

    public static void zero(int[] iArr) {
        iArr[0] = 0;
        iArr[1] = 0;
        iArr[2] = 0;
        iArr[3] = 0;
    }
}
