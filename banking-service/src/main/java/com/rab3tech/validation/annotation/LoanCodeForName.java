package com.rab3tech.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy =LoanCodeConstrainValidate.class)//for making business logic for another class
@Target({ElementType.METHOD,ElementType.FIELD}) //validating for method(getter) and attribute
@Retention(RetentionPolicy.RUNTIME)
public @interface LoanCodeForName {

	//define default name code
	public String Value()default "";
	
	//define default error message
	public String message()default "must have more than 5";
	
	//define default groups
	public Class<?>[] groups() default {};
	
	//define default payloads
	public Class<? extends Payload>[] payload() default {};
}
