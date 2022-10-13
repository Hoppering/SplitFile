package com.meinekleinepupkin.splitfile.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class FilesUtils {

  public static void createMetaFile(String pathToFolder, File file) {
    String pathToMeta = pathToFolder + "/METAFILE.txt";
    File metaFile = new File(pathToMeta);
    try (FileWriter myWriter = new FileWriter(pathToMeta)) {
      metaFile.createNewFile();
      myWriter.write(file.getName() + ";" + file.length());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static String readMetaDataName(String pathToFolder) throws IOException {
    String pathToMeta = pathToFolder + "/METAFILE.txt";
    return readFileAsString(pathToMeta).substring(0,
        readFileAsString(pathToMeta).indexOf(";"));

  }

  public static String readFileAsString(String fileName) throws IOException {
      return new String(Files.readAllBytes(Paths.get(fileName)));
  }

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
