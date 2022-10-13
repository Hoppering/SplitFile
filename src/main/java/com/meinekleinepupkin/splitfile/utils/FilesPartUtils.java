package com.meinekleinepupkin.splitfile.utils;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class FilesPartUtils {

  public static int createPartOfFile(File file, String pathToFolder, int amountExtraBytes,
      byte[] allFilesBytes, int numberOfPart, int countOfSplit) throws IOException {
    String partOfFile =
        FilesRemoveUtil.removeExtension(file.getName()) + "-" + numberOfPart + ".kreker";
    File res = new File(pathToFolder + "/" + partOfFile);

    int lengthSplitFile = allFilesBytes.length - amountExtraBytes;
    int start = (lengthSplitFile / countOfSplit) * (numberOfPart - 1);
    int end = (lengthSplitFile / countOfSplit) * numberOfPart;

    byte[] resFilesBytes = Arrays.copyOfRange(allFilesBytes, start, end);
    Files.write(res.toPath(), resFilesBytes);
    return end;
  }

  public static void createPartOfFileWithExtraBytes(File file, String pathToFolder,
      int amountExtraBytes,
      byte[] allFilesBytes, int numberOfPart, int startByteOfFile) throws IOException {

    String partOfFile =
        FilesRemoveUtil.removeExtension(file.getName()) + "-" + numberOfPart + ".kreker";
    File res = new File(pathToFolder + "/" + partOfFile);
    byte[] resFilesBytes = Arrays.copyOfRange(allFilesBytes, startByteOfFile,
        (startByteOfFile + amountExtraBytes));
    Files.write(res.toPath(), resFilesBytes);

  }

  public static List<String> getAllParts(String pathToFolder) {
    return Stream.of(Objects.requireNonNull(
            new File(pathToFolder).listFiles()))
        .filter(File::isFile)
        .filter(file -> {
          int value = file.getName().lastIndexOf('.');
          return file.getName().substring(value + 1).equals("kreker");
        })
        .map(File::getAbsolutePath).toList();
  }

}
