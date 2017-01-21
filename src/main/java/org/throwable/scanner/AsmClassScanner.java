package org.throwable.scanner;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.ParameterNode;
import org.throwable.demo.ClassFaker;
import org.throwable.utils.ClassUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zjc
 * @version 2017/1/21 23:21
 * @description
 */
public class AsmClassScanner extends BaseScanner implements ClassScanner {


	@Override
	public Map<Class<?>, List<Method>> doScanMethodAnnotation(String packageName, Class<?> annotation) throws Exception {
		Map<String, String> classNameMap = findAllClassNames(packageName, true);
		Map<Class<?>, List<Method>> result = new HashMap<>();
		for (Map.Entry<String, String> entry : classNameMap.entrySet()) {
			DefaultClassVisitor cv = new DefaultClassVisitor(Opcodes.ASM5);
			ClassReader cr = new ClassReader(ClassUtils.getClassLoader().getResourceAsStream(entry.getKey()));
			cr.accept(cv, ClassReader.EXPAND_FRAMES);
			List<MethodNode> methodNodes = cv.getMethodNodes();
			if (!methodNodes.isEmpty()) {
				List<Method> methods = new ArrayList<>();
				for (MethodNode methodNode : methodNodes) {
					DefaultMethodVisitor mv = new DefaultMethodVisitor(Opcodes.ASM5);
					methodNode.accept(mv);
					List<AnnotationNode> annotationNodes = mv.getAnnotationNodes();
					if (!annotationNodes.isEmpty()) {
						Class<?> target = Class.forName(entry.getValue());
						for (AnnotationNode annotationNode : annotationNodes) {
							Type type = Type.getType(annotationNode.desc);
							if (annotation.getName().equals(type.getClassName())) {
								Type[] types = Type.getArgumentTypes(methodNode.desc);
								if (null != types && types.length > 0) {
									Class<?>[] paramTypes = parseTypesToParamTypes(types);
									Method method = target.getDeclaredMethod(methodNode.name, paramTypes);
									if (null != method) {
										methods.add(method);
									}
								}
							}
						}
						if (!methods.isEmpty()) {
							result.put(target, methods);
						}
					}
				}
			}
		}
		return result;
	}


}
