package com.pragma.challenge.technology_service.domain.validation;

import com.pragma.challenge.technology_service.domain.exceptions.standard_exception.BadRequest;
import com.pragma.challenge.technology_service.domain.validation.annotation.Size;
import java.lang.reflect.Field;
import java.util.Objects;

public class ValidSize {
  public static void valid(Object object) {
    Class<?> clazz = object.getClass();
    for (Field field : clazz.getDeclaredFields()) {
      field.setAccessible(true);
      if (field.isAnnotationPresent(Size.class)) {
        try {
          Object value = field.get(object);
          Size annotation = field.getAnnotation(Size.class);
          if (Objects.isNull(annotation)) continue;
          if (Objects.isNull(value)) {
            throw new BadRequest();
          }
          if (value instanceof String string) {
            if (annotation.min() >= 0 && string.length() < annotation.min()) throw new BadRequest();
            if (annotation.max() >= 0 && string.length() > annotation.max()) throw new BadRequest();
          } else {
            throw new IllegalArgumentException("The field is not a string.");
          }
        } catch (IllegalAccessException ignore) {
        }
      }
    }
  }
}
