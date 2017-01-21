package org.throwable.demo;

import org.throwable.scanner.AsmClassScanner;
import org.throwable.scanner.ClassScanner;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author zjc
 * @version 2017/1/22 0:29
 * @description
 */
public class ClassFakerDemo {

	@ClassFaker
	public void hello(Object o,String ss){

	}

	public static void main(String[] args) throws Exception {
		AsmClassScanner scanner = new AsmClassScanner();
		Map<Class<?>,List<Method>> p = scanner.doScanMethodAnnotation("org.throwable", ClassFaker.class);
		if (null != p && !p.isEmpty()){
			for (Map.Entry<Class<?>,List<Method>> entry : p.entrySet()){
				System.out.println("key --> " + entry.getKey().getName());
				System.out.println("value --> " + entry.getValue().toString());
			}
		}
	}
}
