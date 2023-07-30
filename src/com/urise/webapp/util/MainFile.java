package com.urise.webapp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class MainFile {
    public static void main(String[] args) {
        String filePath = "./.gitignore";
        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        showDirs(new StringBuilder("/home/miux/Java/basejava/src"), "");

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println((char) fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void  showDirs(StringBuilder sB, String gap) {
        File dir = new File(String.valueOf(sB));
        String[] dirList = dir.list();
        if (dirList != null) {
            Arrays.sort(dirList);
            if (dir.isDirectory()) {
                for (String s : dirList) {
                    String temp = sB.toString();
                    System.out.println(gap + s);
                    StringBuilder sB1 = new StringBuilder(temp + "/" + s);
                    showDirs(sB1, gap + "  ");
                }
            }
        }
    }
}
