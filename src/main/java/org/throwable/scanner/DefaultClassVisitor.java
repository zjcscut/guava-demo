package org.throwable.scanner;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.MethodNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zjc
 * @version 2017/1/21 23:36
 * @description
 */
public class DefaultClassVisitor extends ClassVisitor{

	private List<MethodNode> methodNodes;

	public DefaultClassVisitor(int i) {
		super(i);
		methodNodes= new ArrayList<>();
	}

	public DefaultClassVisitor(int i, ClassVisitor classVisitor) {
		super(i, classVisitor);
	}

	@Override
	public void visit(int i, int i1, String s, String s1, String s2, String[] strings) {
		super.visit(i, i1, s, s1, s2, strings);
	}

	@Override
	public void visitSource(String s, String s1) {
		super.visitSource(s, s1);
	}

	@Override
	public void visitOuterClass(String s, String s1, String s2) {
		super.visitOuterClass(s, s1, s2);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String s, boolean b) {
		return super.visitAnnotation(s,b);
	}

	@Override
	public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String s, boolean b) {
		return super.visitTypeAnnotation(i, typePath, s, b);
	}

	@Override
	public void visitAttribute(Attribute attribute) {
		super.visitAttribute(attribute);
	}

	@Override
	public void visitInnerClass(String s, String s1, String s2, int i) {
		super.visitInnerClass(s, s1, s2, i);
	}

	@Override
	public FieldVisitor visitField(int i, String s, String s1, String s2, Object o) {
		return super.visitField(i, s, s1, s2, o);
	}

	@Override
	public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
		MethodNode node = new MethodNode(i, s, s1, s2, strings);
		methodNodes.add(node);
		return node;
	}

	@Override
	public void visitEnd() {
		super.visitEnd();
	}

	public List<MethodNode> getMethodNodes() {
		return methodNodes;
	}

}
