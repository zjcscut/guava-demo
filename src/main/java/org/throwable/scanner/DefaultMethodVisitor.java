package org.throwable.scanner;

import org.objectweb.asm.*;
import org.objectweb.asm.tree.AnnotationNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zjc
 * @version 2017/1/21 23:37
 * @description
 */
public class DefaultMethodVisitor extends MethodVisitor {

	private List<AnnotationNode> annotationNodes;

	public DefaultMethodVisitor(int i) {
		super(i);
		annotationNodes = new ArrayList<>();
	}

	public DefaultMethodVisitor(int i, MethodVisitor methodVisitor) {
		super(i, methodVisitor);
	}

	@Override
	public void visitParameter(String s, int i) {
		super.visitParameter(s, i);
	}

	@Override
	public AnnotationVisitor visitAnnotationDefault() {
		return super.visitAnnotationDefault();
	}

	@Override
	public AnnotationVisitor visitAnnotation(String s, boolean b) {
		AnnotationNode node = new AnnotationNode(s);
		if (b){
			annotationNodes.add(node);
		}
		return node;
	}

	@Override
	public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String s, boolean b) {
		return super.visitTypeAnnotation(i, typePath, s, b);
	}

	@Override
	public AnnotationVisitor visitParameterAnnotation(int i, String s, boolean b) {
		return super.visitParameterAnnotation(i, s, b);
	}

	@Override
	public void visitAttribute(Attribute attribute) {
		super.visitAttribute(attribute);
	}

	@Override
	public void visitCode() {
		super.visitCode();
	}

	@Override
	public void visitFrame(int i, int i1, Object[] objects, int i2, Object[] objects1) {
		super.visitFrame(i, i1, objects, i2, objects1);
	}

	@Override
	public void visitInsn(int i) {
		super.visitInsn(i);
	}

	@Override
	public void visitIntInsn(int i, int i1) {
		super.visitIntInsn(i, i1);
	}

	@Override
	public void visitVarInsn(int i, int i1) {
		super.visitVarInsn(i, i1);
	}

	@Override
	public void visitTypeInsn(int i, String s) {
		super.visitTypeInsn(i, s);
	}

	@Override
	public void visitFieldInsn(int i, String s, String s1, String s2) {
		super.visitFieldInsn(i, s, s1, s2);
	}

	@Override
	public void visitMethodInsn(int i, String s, String s1, String s2) {
		super.visitMethodInsn(i, s, s1, s2);
	}

	@Override
	public void visitMethodInsn(int i, String s, String s1, String s2, boolean b) {
		super.visitMethodInsn(i, s, s1, s2, b);
	}

	@Override
	public void visitInvokeDynamicInsn(String s, String s1, Handle handle, Object... objects) {
		super.visitInvokeDynamicInsn(s, s1, handle, objects);
	}

	@Override
	public void visitJumpInsn(int i, Label label) {
		super.visitJumpInsn(i, label);
	}

	@Override
	public void visitLabel(Label label) {
		super.visitLabel(label);
	}

	@Override
	public void visitLdcInsn(Object o) {
		super.visitLdcInsn(o);
	}

	@Override
	public void visitIincInsn(int i, int i1) {
		super.visitIincInsn(i, i1);
	}

	@Override
	public void visitTableSwitchInsn(int i, int i1, Label label, Label... labels) {
		super.visitTableSwitchInsn(i, i1, label, labels);
	}

	@Override
	public void visitLookupSwitchInsn(Label label, int[] ints, Label[] labels) {
		super.visitLookupSwitchInsn(label, ints, labels);
	}

	@Override
	public void visitMultiANewArrayInsn(String s, int i) {
		super.visitMultiANewArrayInsn(s, i);
	}

	@Override
	public AnnotationVisitor visitInsnAnnotation(int i, TypePath typePath, String s, boolean b) {
		return super.visitInsnAnnotation(i, typePath, s, b);
	}

	@Override
	public void visitTryCatchBlock(Label label, Label label1, Label label2, String s) {
		super.visitTryCatchBlock(label, label1, label2, s);
	}

	@Override
	public AnnotationVisitor visitTryCatchAnnotation(int i, TypePath typePath, String s, boolean b) {
		return super.visitTryCatchAnnotation(i, typePath, s, b);
	}

	@Override
	public void visitLocalVariable(String s, String s1, String s2, Label label, Label label1, int i) {
		super.visitLocalVariable(s, s1, s2, label, label1, i);
	}

	@Override
	public AnnotationVisitor visitLocalVariableAnnotation(int i, TypePath typePath, Label[] labels, Label[] labels1, int[] ints, String s, boolean b) {
		return super.visitLocalVariableAnnotation(i, typePath, labels, labels1, ints, s, b);
	}

	@Override
	public void visitLineNumber(int i, Label label) {
		super.visitLineNumber(i, label);
	}

	@Override
	public void visitMaxs(int i, int i1) {
		super.visitMaxs(i, i1);
	}

	@Override
	public void visitEnd() {
		super.visitEnd();
	}

	public List<AnnotationNode> getAnnotationNodes() {
		return annotationNodes;
	}
}
