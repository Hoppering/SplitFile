package com.meinekleinepupkin.splitfile.service;


import static com.meinekleinepupkin.splitfile.utils.FilesUtils.createFolderForSplitFile;

import com.meinekleinepupkin.splitfile.utils.FilesPartUtils;
import com.meinekleinepupkin.splitfile.utils.FilesRemoveUtil;
import com.meinekleinepupkin.splitfile.utils.FilesUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Splitter {

  public static void splitForManyFiles(String pathToFolder, File file, int countOfSplit,
      int amountExtraBytes) throws IOException {
    FilesUtils.createMetaFile(pathToFolder, file);
    for (int i = 1; i <= countOfSplit; i++) {
      int lastFilesBytes = FilesPartUtils.createPartOfFile(file, pathToFolder, amountExtraBytes,
          Files.readAllBytes(file.toPath()), i, countOfSplit);
      if (i == countOfSplit && amountExtraBytes != 0) {
        FilesPartUtils.createPartOfFileWithExtraBytes(file, pathToFolder, amountExtraBytes,
            Files.readAllBytes(file.toPath()), i + 1, lastFilesBytes);
      }
    }
  }

  public static File joinForManyFiles(String pathToFolder) throws IOException {
    String fileName = FilesUtils.readMetaDataName(pathToFolder);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    File resFile = new File(pathToFolder + "/" + fileName);
    List<String> files = FilesPartUtils.getAllParts(pathToFolder);
    for (String item : files) {
      File file = new File(item);
      byte[] fileBytes = Files.readAllBytes(file.toPath());
      outputStream.write(fileBytes);
      FilesRemoveUtil.removeFileOrFolder(file.toPath().toString());
    }
    byte[] resultBytesForFile = outputStream.toByteArray();
    Files.write(resFile.toPath(), resultBytesForFile);
    FilesRemoveUtil.removeFileOrFolder(pathToFolder + "/METAFILE.txt");
    return resFile;
  }

  public static Map<Integer, Integer> checkPossiblyAmount(File file) throws Exception {
    String pathFolder = createFolderForSplitFile(file);
    Map<Integer, Integer> possiblyAmount = new HashMap<>();
    for (int amountToSplit = 2; amountToSplit < 10; amountToSplit++) {
      for (int numberExtraBytes = 0; numberExtraBytes < 10; numberExtraBytes++) {
        Splitter.splitForManyFiles(pathFolder, file, amountToSplit, numberExtraBytes);
        File testFile = Splitter.joinForManyFiles(pathFolder);
        if (Arrays.equals(Files.readAllBytes(file.toPath()),
            Files.readAllBytes(testFile.toPath()))) {
          possiblyAmount.put(amountToSplit, numberExtraBytes);
        }
      }
    }

    FilesRemoveUtil.removeFileOrFolder(pathFolder + "/" + file.getName());
    FilesRemoveUtil.removeFileOrFolder(pathFolder);
    return possiblyAmount;
  }
}
