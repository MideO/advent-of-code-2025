package com.github.mideo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class InputReader {
  private InputReader(){}

  public static Optional<String> readInput(String resourceFileName) {
    var stream = InputReader.class.getClassLoader().getResourceAsStream(resourceFileName);
    if (stream == null) return Optional.empty();

    try (stream) {
      return Optional.of(new String(stream.readAllBytes(), StandardCharsets.UTF_8));
    } catch (IOException e) {
      return Optional.empty();
    }
  }

  public static List<String> readLines(String resourceFileName) {
    return readInput(resourceFileName)
        .map(content -> Arrays.asList(content.split("\\R")))
        .orElse(List.of());
  }

}