package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public final class EAN8Writer extends UPCEANWriter {
    private static final int CODE_WIDTH = 67;

    public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) throws WriterException {
        if (format == BarcodeFormat.EAN_8) {
            return super.encode(contents, format, width, height, hints);
        }
        throw new IllegalArgumentException("Can only encode EAN_8, but got " + format);
    }

    public boolean[] encode(String contents) {
        int length = contents.length();
        switch (length) {
            case 7:
                try {
                    contents = contents + UPCEANReader.getStandardUPCEANChecksum(contents);
                    break;
                } catch (FormatException fe) {
                    throw new IllegalArgumentException(fe);
                }
            case 8:
                try {
                    if (!UPCEANReader.checkStandardUPCEANChecksum(contents)) {
                        throw new IllegalArgumentException("Contents do not pass checksum");
                    }
                } catch (FormatException e) {
                    throw new IllegalArgumentException("Illegal contents");
                }
                break;
            default:
                throw new IllegalArgumentException("Requested contents should be 8 digits long, but got " + length);
        }
        boolean[] result = new boolean[67];
        int pos = appendPattern(result, 0, UPCEANReader.START_END_PATTERN, true) + 0;
        for (int i = 0; i <= 3; i++) {
            pos += appendPattern(result, pos, UPCEANReader.L_PATTERNS[Character.digit(contents.charAt(i), 10)], false);
        }
        int pos2 = pos + appendPattern(result, pos, UPCEANReader.MIDDLE_PATTERN, false);
        for (int i2 = 4; i2 <= 7; i2++) {
            pos2 += appendPattern(result, pos2, UPCEANReader.L_PATTERNS[Character.digit(contents.charAt(i2), 10)], true);
        }
        appendPattern(result, pos2, UPCEANReader.START_END_PATTERN, true);
        return result;
    }
}
