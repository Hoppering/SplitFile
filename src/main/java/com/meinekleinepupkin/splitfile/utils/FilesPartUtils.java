package com.meinekleinepupkin.splitfile.utils;


import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class FilesPartUtils {

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
