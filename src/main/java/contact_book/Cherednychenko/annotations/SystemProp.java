package contact_book.Cherednychenko.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME) // аннотауия применяется во время исполнения порграммы
@Target(ElementType.FIELD)//аннотация применима к полю (не к методу и не к классу)
public @interface SystemProp {
    String value();
}

