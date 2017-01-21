package org.throwable.scanner;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author zjc
 * @version 2017/1/21 23:21
 * @description
 */
public interface ClassScanner {

	Map<Class<?>,List<Method>> doScanMethodAnnotation(String packageName,Class<?> annotation) throws Exception ;
}
