/* (C)2022 */
package com.follybuilders.paradisium.pattern;

import heronarts.lx.LX;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.stream.Stream;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.reflections.Reflections;

@TestInstance(Lifecycle.PER_CLASS)
public class InstantiateAllPatternsTest {
  Stream<Class<? extends ParadisiumBasePattern>> findPatterns() {
    Reflections reflection = new Reflections("com.follybuilders");
    return reflection.getSubTypesOf(ParadisiumBasePattern.class).stream()
        .filter(p -> !Modifier.isAbstract(p.getModifiers()))
        .sorted(Comparator.comparing(Class::getName, String::compareTo));
  }

  @ParameterizedTest
  @MethodSource("findPatterns")
  void validatePatternsFromBaseInstantiate(Class<?> cls)
      throws IllegalAccessException, InstantiationException, NoSuchMethodException,
          InvocationTargetException {
    System.out.println("Testing pattern: " + cls.getName());
    cls.getConstructor(LX.class).newInstance(new LX());
  }
}
