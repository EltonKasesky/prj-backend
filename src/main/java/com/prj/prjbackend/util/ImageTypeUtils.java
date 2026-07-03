package com.prj.prjbackend.util;

public class ImageTypeUtils {
    private static final String DEFAULT_TYPE = "image/jpeg";

    public static String detect(byte[] image) {
        if (image == null || image.length < 4) {
            return DEFAULT_TYPE;
        }

        if ((image[0] & 0xFF) == 0xFF && (image[1] & 0xFF) == 0xD8) {
            return "image/jpeg";
        }

        if ((image[0] & 0xFF) == 0x89 && image[1] == 'P' && image[2] == 'N' && image[3] == 'G') {
            return "image/png";
        }

        if (image[0] == 'G' && image[1] == 'I' && image[2] == 'F') {
            return "image/gif";
        }

        if (image.length >= 12
                && image[0] == 'R' && image[1] == 'I' && image[2] == 'F' && image[3] == 'F'
                && image[8] == 'W' && image[9] == 'E' && image[10] == 'B' && image[11] == 'P') {
            return "image/webp";
        }

        return DEFAULT_TYPE;
    }
}
