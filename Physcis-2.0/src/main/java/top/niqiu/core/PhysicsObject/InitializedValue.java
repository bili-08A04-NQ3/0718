package top.niqiu.core.PhysicsObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InitializedValue {
    String name() default "-";
    double min() default -10.0D;
    double max() default 10.0D;
    double defaultValue() default 0.0D;
}
