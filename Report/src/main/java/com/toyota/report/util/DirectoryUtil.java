package com.toyota.report.util;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DirectoryUtil {

    public static String getFontNameFromDirectory(String directoryPath) {

        String fontName = "font.ttf";

        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    return file.getName();
                }
            }
        }
        return fontName;
    }
}