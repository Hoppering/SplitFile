package com.meinekleinepupkin.splitfile.service;


import com.meinekleinepupkin.splitfile.utils.FilesPartUtils;
import com.meinekleinepupkin.splitfile.utils.FilesRemoveUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Splitter {

  public static void splitForManyFiles(String pathToFolder, File file, int numParts)
      throws IOException {

    final long fileSize = file.length();
    final long chunkSize = fileSize / numParts;
    final long remainingBytes = fileSize % numParts;

    try (final FileInputStream inputStream = new FileInputStream(file)) {
      byte[] buffer = new byte[(int) chunkSize];
      for (int i = 0; i < numParts; i++) {
        final String chunkFileName =
            pathToFolder + "\\" + new Date().getTime() + "-#-" + file.getName() + ".kreker";
        try (FileOutputStream outputStream = new FileOutputStream(chunkFileName)) {
          int bytesRead = 0;
          while (bytesRead < chunkSize) {
            int bytesToRead = (int) Math.min(buffer.length, chunkSize - bytesRead);
            int n = inputStream.read(buffer, 0, bytesToRead);
            if (n <= 0) {
              break;
            }
            outputStream.write(buffer, 0, n);
            bytesRead += n;
          }
          if (i == numParts - 1 && remainingBytes > 0) {
            final byte[] remainingBuffer = new byte[(int) remainingBytes];
            final int n = inputStream.read(remainingBuffer);
            outputStream.write(remainingBuffer, 0, n);
          }
        }
      }
    }
  }

  public static void mergeFiles(String pathToFolder)
      throws IOException {
    final List<String> files = FilesPartUtils.getAllParts(pathToFolder);
    final int index = files.get(0).indexOf("-#-");
    try (final FileOutputStream outputStream = new FileOutputStream(
        pathToFolder + "\\" + FilesRemoveUtil.removeExtension(files.get(0).substring(index + 3)))) {
      byte[] buffer = new byte[1024];
      int bytesRead;

      for (final String chunkFileName : files) {
        try (final FileInputStream inputStream = new FileInputStream(chunkFileName)) {
          while ((bytesRead = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, bytesRead);
          }
        }
        FilesRemoveUtil.removeFileOrFolder(chunkFileName);
      }
    }
  }
}
