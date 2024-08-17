package com.example.yaho.validation.annotation;

import com.example.yaho.validation.validator.EmotionCheckValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmotionCheckValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckEmotion {

    String message() default "이모티콘 요청으로 1~10 까지의 숫자를 입력하세요.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}