package com.example.laptop.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {

    public static final String AUTHORIZATION = "Authorization";

    public static final Path PATH_CODE_FOLDER = Paths.get("code").toAbsolutePath().normalize();

    public static final String CODE_FOLDER = PATH_CODE_FOLDER.toString();

    public static final String DEFAULT_PAGE = "0";

    public static final String DEFAULT_PAGE_SIZE = "20";

    public static String getHeaderValues(String fileName) {
        return "inline; filename=\"" + fileName + "\"";
    }
}