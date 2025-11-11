package com.sample.electronicStore.electronicStore.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
@Slf4j
public class ImageNameValidator implements ConstraintValidator<ImageNameValid,String> {


    @Override
    public void initialize(ImageNameValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        log.info("Image Name : {}",s);
        if(s==null || s.isEmpty()){
            return false;
        }else{
            return s.matches("^[a-zA-Z0-9_.-]*$");
        }
    }
}
