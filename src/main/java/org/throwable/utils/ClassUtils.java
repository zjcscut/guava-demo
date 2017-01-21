package org.throwable.utils;

/**
 * @author zjc
 * @version 2017/1/21 21:35
 * @description
 */
public final class ClassUtils {

	public static ClassLoader getClassLoader() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (null == loader) {
			loader = ClassUtils.class.getClassLoader();
			if (null == loader) {
				loader = ClassLoader.getSystemClassLoader();
				if (null == loader) {
					throw new RuntimeException("could not get classloader!!!");
				}
			}
		}
		return loader;
	}
}
