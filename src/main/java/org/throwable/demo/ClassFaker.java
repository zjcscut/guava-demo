package org.throwable.demo;

import java.lang.annotation.*;

/**
 * @author zjc
 * @version 2017/1/22 0:20
 * @description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface ClassFaker {
}
