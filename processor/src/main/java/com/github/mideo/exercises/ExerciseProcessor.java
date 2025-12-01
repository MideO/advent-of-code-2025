package com.github.mideo.exercises;


import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.JavaFileObject;
import java.io.Writer;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedAnnotationTypes("com.github.mideo.exercises.DailyExercise")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
public class ExerciseProcessor extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    Set<? extends Element> exercises = roundEnv.getElementsAnnotatedWith(DailyExercise.class);
    if (exercises.isEmpty()) return true;

    try {
      JavaFileObject file = processingEnv.getFiler().createSourceFile(
          "com.github.mideo.exercises.DailyExerciseRegistry"
      );
      try (Writer writer = file.openWriter()) {
        writer.write("""
            package com.github.mideo.exercises;

            import com.github.mideo.exercises.Exercise;

            public final class DailyExerciseRegistry {
                public static java.util.List<Exercise> getExercises() {
                    return java.util.List.of(
            """);
        writer.write(
            exercises.stream()
                .map(e -> "            new " + ((TypeElement) e).getQualifiedName() + "()")
                .collect(Collectors.joining(",\n"))
        );

        writer.write("""
                    );
                }
            }
            """);
      }
    } catch (Exception ex) {
      throw new RuntimeException("ExerciseProcessor Failed!!", ex);
    }
    return true;
  }
}