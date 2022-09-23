package com.meinekleinepupkin.splitfile.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilesUtils {

  public static void createMetaFile(String pathToFolder, File file) throws IOException {
    String pathToMeta = pathToFolder + "/METAFILE.txt";
    File metaFile = new File(pathToMeta);
    metaFile.createNewFile();
    FileWriter myWriter = new FileWriter(pathToMeta);
    myWriter.write(file.getName() + ";" + file.length());
    myWriter.close();
  }

  public static String readMetaDataName(String pathToFolder) throws Exception {
    String pathToMeta = pathToFolder + "/METAFILE.txt";
    return readFileAsString(pathToMeta).substring(0,
        readFileAsString(pathToMeta).indexOf(";"));

  }

  public static int readMetaDataSize(String pathToFolder) throws Exception {
    String pathToMeta = pathToFolder + "/METAFILE.txt";
    return Integer.parseInt(readFileAsString(pathToMeta).substring(
        readFileAsString(pathToMeta).indexOf(";") + 1));
  }

  public static String readFileAsString(String fileName) throws Exception {
    String data = "";
    data = new String(Files.readAllBytes(Paths.get(fileName)));
    return data;
  }

  public static List<String> getAllParts(String pathToFolder) {
    return Stream.of(Objects.requireNonNull(
            new File(pathToFolder).listFiles()))
        .filter(File::isFile)
        .filter(file -> {
          int value = file.getName().lastIndexOf('.');
          return file.getName().substring(value + 1).equals("kreker");
        })
        .map(File::getAbsolutePath)
        .collect(Collectors.toList());
  }

  public static String removeExtension(String fileName) {
    int lastIndex = fileName.lastIndexOf('.');
    if (lastIndex != -1) {
      fileName = fileName.substring(0, lastIndex);
    }
    return fileName;
  }

  public static String createFolderForSplitFile(File file) {
    String dateTime = String.valueOf(new Date().getTime());
    String fileName = removeExtension(file.getName());
    String filePath = file.getParent();
    File folder = new File(filePath + "/" + new Date().getTime() + "-" + fileName);
    if (!folder.exists()) {
      folder.mkdir();
    }
    return folder.getAbsolutePath();
  }
}
