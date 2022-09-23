package com.meinekleinepupkin.splitfile.service;


import static com.meinekleinepupkin.splitfile.utils.FilesUtils.createFolderForSplitFile;
import static com.meinekleinepupkin.splitfile.utils.FilesUtils.removeExtension;

import com.meinekleinepupkin.splitfile.utils.FilesUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Splitter {

  public static void splitForManyFiles(String pathToFolder, File file, int count) throws Exception {
    FilesUtils.createMetaFile(pathToFolder, file);
    byte[] preFileBytes = Files.readAllBytes(file.toPath());
    for (int i = 1; i <= count; i++) {
      String partOfFile = removeExtension(file.getName()) + "-" + i + ".kreker";
      File res = new File(
          pathToFolder + "/" + partOfFile);
      int start = (preFileBytes.length / count) * (i - 1);
      int end = (preFileBytes.length / count) * i;
      byte[] resFilesBytes = Arrays.copyOfRange(preFileBytes, start, end);
      Files.write(res.toPath(), resFilesBytes);
    }
  }

  public static File joinForManyFiles(String pathToFolder) throws Exception {
    String fileName = FilesUtils.readMetaDataName(pathToFolder);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    File resFile = new File(pathToFolder + "/" + fileName);
    List<String> files = FilesUtils.getAllParts(pathToFolder);
    for (String item : files) {
      File file = new File(item);
      byte[] fileBytes = Files.readAllBytes(file.toPath());
      file.delete();
      outputStream.write(fileBytes);
    }
    byte[] resultBytesForFile = outputStream.toByteArray();
    Files.write(resFile.toPath(), resultBytesForFile);
    File metaFile = new File(pathToFolder + "/METAFILE.txt");
    metaFile.delete();
    return resFile;
  }

  public static List<Integer> checkPossiblyAmount(File file) throws Exception {
    //String pathFolder = createFolderForSplitFile(removeExtension(file.getName()));
    String pathFolder = createFolderForSplitFile(file);
    List<Integer> possiblyAmount = new ArrayList<>();
    for (int i = 2; i <= 20; i++) {
      Splitter.splitForManyFiles(pathFolder, file, i);
      File testFile = Splitter.joinForManyFiles(pathFolder);
      if (Arrays.equals(Files.readAllBytes(file.toPath()),
          Files.readAllBytes(testFile.toPath()))) {
        possiblyAmount.add(i);
      }
    }

    File fileTest = new File(pathFolder + "/" + file.getName());
    fileTest.delete();
    File fileTestFolder = new File(pathFolder);
    fileTestFolder.delete();
    return possiblyAmount;
  }
}
