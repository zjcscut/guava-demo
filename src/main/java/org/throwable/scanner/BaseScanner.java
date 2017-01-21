package org.throwable.scanner;

import org.objectweb.asm.Type;
import org.throwable.utils.ClassUtils;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author zjc
 * @version 2017/1/21 21:32
 * @description 基础扫描器
 */
public class BaseScanner {

	public static final String CLASS_SUFFIX = ".class";
	public static final String FILE_PROTOCL = "file";
	public static final String JAR_PROTOCL = "jar";
	public static final String PATH_SEPARATOR = File.pathSeparator;
	public static final String SEPARATOR = File.separator;

	@Deprecated
	public Enumeration<URL> findAllClassPathResources(String path) throws IOException {
		if (null == path) {
			throw new RuntimeException("path must not be null");
		}
		if (path.endsWith(".")) {
			path = path.substring(0, path.length() - 1);
		}
		String parentPath = path.replace(".", SEPARATOR);
		return ClassUtils.getClassLoader().getResources(parentPath);
	}

	protected Map<String, String> findAllClassNames(String path, boolean isInculdedJar)
			throws URISyntaxException, IOException {
		if (null == path) {
			throw new RuntimeException("path must not be null");
		}
		if (path.endsWith(".")) {
			path = path.substring(0, path.length() - 1);
		}
		String parentPath = path.replace(".", SEPARATOR);
		Enumeration<URL> urls = ClassUtils.getClassLoader().getResources(parentPath);
		if (null != parentPath && parentPath.length() > 0) {
			if (!parentPath.endsWith(SEPARATOR)) {
				parentPath = parentPath + SEPARATOR;
			}
		}
		Map<String, String> classNameMap = new HashMap<>();
		while (null != urls && urls.hasMoreElements()) {
			URL url = urls.nextElement();
			String protocol = url.getProtocol();
			if (isInculdedJar) {
				if (FILE_PROTOCL.equals(protocol)) {
					File dir = new File(url.toURI());
					if (dir.isDirectory()) {
						System.out.println("扫描文件Directory");
						parseClassDir(dir, null, classNameMap, parentPath);
					} else {
						System.out.println("扫描非文件Directory");
						parseZipFile(dir, classNameMap, parentPath);
					}
				} else if (JAR_PROTOCL.equals(protocol)) {
					System.out.println("扫描jar");
					parseJarFile(url, classNameMap, parentPath);
				} else {
					throw new RuntimeException("unsupported protocol type!!!!");
				}
			} else {
				if (FILE_PROTOCL.equals(protocol)) {
					File dir = new File(url.toURI());
					if (dir.isDirectory()) {
						parseClassDirWithoutZip(dir, null, classNameMap, parentPath);
					} else {
						throw new RuntimeException("file must be directory!!!!");
					}
				} else {
					throw new RuntimeException("unsupported protocol type!!!!");
				}
			}
		}
		return classNameMap;
	}

	protected void parseClassDir(final File dir, final String childPath, Map<String, String> classMap, final String parentPath)
			throws IOException {
		File[] files = dir.listFiles();
		if (null != files && files.length > 0) {
			for (File child : files) {
				String newPath = childPath == null ? child.getName() : childPath + SEPARATOR + child.getName();
				if (child.isDirectory()) {
					parseClassDir(child, newPath, classMap, parentPath);
				} else if (newPath.endsWith(CLASS_SUFFIX)) {
					String className = parentPath + newPath;
					System.out.println("DIR --- 添加class: key - " + className + "; value - " + className.replace(CLASS_SUFFIX, ""));
					parseClassFile(className, classMap);
				} else if (newPath.endsWith(JAR_PROTOCL)) {
					parseZipFile(child, classMap, parentPath);
				}
			}
		}
	}

	protected void parseClassDirWithoutZip(final File dir, final String childPath,
										   Map<String, String> classMap, final String parentPath)
			throws IOException {
		File[] files = dir.listFiles();
		if (null != files && files.length > 0) {
			for (File child : files) {
				String newPath = childPath == null ? child.getName() : childPath + SEPARATOR + child.getName();
				if (child.isDirectory()) {
					parseClassDirWithoutZip(child, newPath, classMap, parentPath);
				} else if (newPath.endsWith(CLASS_SUFFIX)) {
					String className = parentPath + newPath;
					System.out.println("DIR --- 添加class: key - " + className + "; value - " + className.replace(CLASS_SUFFIX, ""));
					parseClassFile(className, classMap);
				}
			}
		}
	}

	protected void parseClassFile(final String name, Map<String, String> classMap) {
		classMap.put(fileNameFormat(name), fileNameToclassName(name));
	}

	protected void parseZipFile(final File file, Map<String, String> classMap, String parentPath) throws IOException {
		final ZipFile zip = new ZipFile(file);
		final Enumeration<? extends ZipEntry> entries = zip.entries();
		while (entries.hasMoreElements()) {
			final ZipEntry entry = entries.nextElement();
			if (entry.isDirectory()) {
				continue;
			}
			final String name = entry.getName();
			if (name.endsWith(CLASS_SUFFIX)) {
				String className = parentPath + name;
				System.out.println("ZIP --- 添加class: key - " + className + "; value - " + className.replace(CLASS_SUFFIX, ""));
				parseClassFile(className.replace("\\", ""), classMap);
			}
		}
	}

	protected void parseJarFile(URL url, Map<String, String> classMap, String parentPath) throws IOException {
		JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
		Enumeration<JarEntry> entries = jar.entries();
		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			if (entry.isDirectory()) {
				continue;
			}
			String name = entry.getName();
			if (name.endsWith(CLASS_SUFFIX)) {
				String className = parentPath + name;
				System.out.println("JAR ----- 添加class: key - " + className + "; value - " + className.replace(CLASS_SUFFIX, ""));
				parseClassFile(className, classMap);
			}
		}
	}

	public String fileNameFormat(String fileName){
		return fileName.replace("/",SEPARATOR).replace("\\",SEPARATOR);
	}

	public String fileNameToclassName(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf(CLASS_SUFFIX)).replace("/", ".").replace("\\", ".");
	}

	protected Class<?>[] parseTypesToParamTypes(Type[] types) throws ClassNotFoundException {
		Class<?>[] clazzes = new Class<?>[types.length];
		for (int i = 0; i < types.length; i++) {
			Class<?> clazz = Class.forName(types[i].getClassName());
			clazzes[i] = clazz;
		}
		return clazzes;
	}

	public static void main(String[] args) throws IOException, URISyntaxException {
//		System.out.println(File.pathSeparator);
//		System.out.println(File.separator);
		BaseScanner baseScanner = new BaseScanner();
//		Enumeration<URL> urlEnumeration = baseScanner.findAllClassPathResources("org.throwable");
//		while (urlEnumeration.hasMoreElements()){
//			URL url = urlEnumeration.nextElement();
//			System.out.println("protocol : " + url.getProtocol());
//			System.out.println("path : " + url.getPath());
//			System.out.println("file : " + url.getFile());
//		}
		Map<String, String> maps = baseScanner.findAllClassNames("org.throwable", true);
		for (Map.Entry<String, String> entry : maps.entrySet()) {
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}
	}
}
