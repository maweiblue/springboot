package org.springframework.lang;

import java.lang.annotation.*;

//import javax.annotation.Nonnull;
//import javax.annotation.meta.TypeQualifierNickname;

@Target({ElementType.METHOD,ElementType.PARAMETER,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Nonnull
//@TypeQualifierNickname
public @interface NonNull {
}
