package com.dev.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppConstants {

    private AppConstants() {

    }

    public static final Long LIKE_NUMBER_0 = 0L;

    public static final List<String> ALLOWED_FILE_TYPES_ARRAY = new ArrayList<>(Arrays.asList("png", "jpg"));

    public static String getAllowedFileTypesAsString() {
        StringBuilder fileTypesCommaSeparated = new StringBuilder();
        for (int i = 0; i < ALLOWED_FILE_TYPES_ARRAY.size(); i++) {
            fileTypesCommaSeparated.append(ALLOWED_FILE_TYPES_ARRAY.get(i));
            if (i < ALLOWED_FILE_TYPES_ARRAY.size() - 1) {
                fileTypesCommaSeparated.append(",");
            }
        }
        return fileTypesCommaSeparated.toString();
    }
}
