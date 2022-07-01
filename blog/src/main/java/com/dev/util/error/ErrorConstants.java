package com.dev.util.error;

import com.dev.util.AppConstants;

import java.net.URI;

public final class ErrorConstants {

    private ErrorConstants(){
    }

    public static final String DEFAULT_BASE_STRING = "https://www.google.com/";
    public static final URI DEFAULT_TYPE = URI.create(DEFAULT_BASE_STRING + "error");

    public static final String BAD_REQUEST_KEY = "400";
    public static final String NOT_FOUND_KEY = "404";
    public static final String NO_CONTENT_KEY = "204";
    public static final String ALLOWED_FILE_TYPES_ERROR_KEY = "1";

    public static final String CONFLICT_ID_NOT_FOUND_MESSAGE = "ID can't be 0";
    public static final String ID_NOT_FOUND_MESSAGE = "ID can't be empty";

    public static final String USER_NOT_FOUND_MESSAGE = "User not found";
    public static final String POST_NOT_FOUND_MESSAGE = "Post not found";
    public static final String USER_POST_NOT_FOUND_MESSAGE = "User's post not found for the given ID";
    public static final String ID_DELETED_OR_NOT_FOUND_MESSAGE = "ID has already been deleted or not found";

    public static final String ALLOWED_FILE_TYPES_ERROR_MESSAGE = "Image allowed type: " + AppConstants.getAllowedFileTypesAsString();

}
