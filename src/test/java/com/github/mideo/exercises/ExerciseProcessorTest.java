package com.github.mideo.exercises;

import com.google.testing.compile.Compilation;
import com.google.testing.compile.Compiler;
import com.google.testing.compile.JavaFileObjects;
import org.junit.jupiter.api.Test;

import javax.tools.JavaFileObject;

import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;
import static com.google.testing.compile.CompilationSubject.assertThat;


class ExerciseProcessorTest {
  @Test
  void processorGeneratesRegistryWithAllAnnotatedClasses() throws IOException {
    // Annotation
    JavaFileObject dailyExerciseAnnotation = JavaFileObjects.forSourceString(
        "com.github.mideo.exercises.DailyExercise",
        """
                package com.github.mideo.exercises;
               \s
                import java.lang.annotation.ElementType;
                import java.lang.annotation.Retention;
                import java.lang.annotation.RetentionPolicy;
                import java.lang.annotation.Target;
                @Retention(RetentionPolicy.SOURCE)
                @Target(ElementType.TYPE)
                public @interface DailyExercise {
                }
           \s"""
    );

    // Interface
    JavaFileObject exerciseInterface = JavaFileObjects.forSourceString(
        "com.github.mideo.exercises.DailyExercise",
        """
               package com.github.mideo.exercises;
              \s
               interface Exercise<T, K> {
                 T partOne();
                 K partTwo();
               }
           \s"""
    );

    // Implementation 1
    JavaFileObject dummyTask = JavaFileObjects.forSourceString(
        "com.github.mideo.exercises.Dummy",
        """
              package com.github.mideo.exercises;
             \s
              @DailyExercise
              public class Dummy implements Exercise<Integer, Integer>{
                @Override
                public Integer partOne() {
                  return 1;
                }
                @Override
                public Integer partTwo() {
                  return 2;
                }
              }
           \s"""
    );

    Compilation compilation = Compiler.javac()
        .withProcessors(new ExerciseProcessor())
        .compile(dailyExerciseAnnotation, exerciseInterface, dummyTask);

    // Processor must succeed
    assertThat(compilation).succeeded();

    // Verify generated file exists
    JavaFileObject generated = compilation.generatedSourceFile(
        "com.github.mideo.generated.DailyExerciseRegistry"
    ).orElseThrow(() -> new AssertionError("DailyExerciseRegistry registry not found"));

    String generatedSrc = generated.getCharContent(false).toString();

    // Verify expected content

    assertThat(generatedSrc).contains("public final class DailyExerciseRegistry");
    assertThat(generatedSrc).contains("com.github.mideo.exercises.Dummy()");


  }
}