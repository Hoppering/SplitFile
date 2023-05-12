package com.meinekleinepupkin.splitfile.utils;

import java.io.File;
import java.util.Date;

public class FilesUtils {

  public static String createFolderForSplitFile(File file) {
    String fileName = FilesRemoveUtil.removeExtension(file.getName());
    String filePath = file.getParent();
    File folder = new File(filePath + "/" + new Date().getTime() + "-" + fileName);
    if (!folder.exists()) {
      folder.mkdir();
    }
    return folder.getAbsolutePath();
  }
}
