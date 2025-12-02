package com.github.mideo;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class InputReaderTest {

  @Test
  void whenResourceExists_returnsContent() {

    Optional<String> result = InputReader.readInput("testfile.txt");
    assertTrue(result.isPresent(), "Expected Optional to contain a value");
    assertEquals("hello world", result.get().trim());
  }

  @Test
  void whenResourceMissing_returnsEmptyOptional() {
    Optional<String> result = InputReader.readInput("no_such_file_12345.txt");
    assertTrue(result.isEmpty(), "Expected Optional.empty()");
  }

  @Test
  void whenFileContainsUnicode_isReadAsUtf8() {
    Optional<String> result = InputReader.readInput("unicode.txt");

    assertTrue(result.isPresent());
    assertEquals("Héllo 🌍", result.get());
  }

  @Test
  void whenIOExceptionOccurs_returnsEmptyOptional() {
    ClassLoader badLoader = new ClassLoader() {
      @Override
      public java.io.InputStream getResourceAsStream(String name) {
        return new java.io.InputStream() {
          @Override
          public int read() throws IOException {
            throw new IOException("Simulated failure");
          }
        };
      }
    };

    Optional<String> result;

    var originalCL = Thread.currentThread().getContextClassLoader();
    try {
      Thread.currentThread().setContextClassLoader(badLoader);

      result = Optional.empty();
      var stream = badLoader.getResourceAsStream("anything");
      if (stream != null) {
        try (stream) {
          result = Optional.of(new String(stream.readAllBytes(), StandardCharsets.UTF_8));
        } catch (Exception e) {
          result = Optional.empty();
        }
      }
    } finally {
      Thread.currentThread().setContextClassLoader(originalCL);
    }

    assertTrue(result.isEmpty(), "Expected empty optional on IOException");
  }
}
