package contact_book.Cherednychenko.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME) // аннотауия применяется во врмя исполнения порграммы
@Target(ElementType.FIELD)//аннотация применима к полю
public @interface SystemProp {
    String value();
}
