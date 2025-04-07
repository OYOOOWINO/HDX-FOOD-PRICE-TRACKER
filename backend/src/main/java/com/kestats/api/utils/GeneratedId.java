package com.kestats.api.utils;

import org.hibernate.annotations.IdGeneratorType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@IdGeneratorType(CustomIdGenerator.class)
@Target({ FIELD, METHOD })
@Retention(RUNTIME)
 public @interface GeneratedId {
}
