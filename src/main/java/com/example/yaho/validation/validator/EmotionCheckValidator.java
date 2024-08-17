package com.example.yaho.validation.validator;

import com.example.yaho.apiPayload.code.status.ErrorStatus;
import com.example.yaho.validation.annotation.CheckEmotion;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmotionCheckValidator implements ConstraintValidator<CheckEmotion, Integer> {
    @Override
    public void initialize(CheckEmotion constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value < 1 || value > 9) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.EMOTION_NOT_FOUND.toString()).addConstraintViolation();
            return false;
        }
        return true;
    }
}
