package com.meinekleinepupkin.splitfile.utils;

import java.io.File;

public class FilesRemoveUtil {
  public static void removeFileOrFolder(String pathToFile){
    File file = new File(pathToFile);
    file.delete();
  }

  public static String removeExtension(String fileName) {
    int lastIndex = fileName.lastIndexOf('.');
    if (lastIndex != -1) {
      fileName = fileName.substring(0, lastIndex);
    }
    return fileName;
  }
}
