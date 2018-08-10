package org.jacoco.core.internal.analysis;

import org.jacoco.core.internal.flow.ClassProbesAdapter;
import org.jacoco.core.internal.flow.ClassProbesVisitor;
import org.objectweb.asm.AnnotationVisitor;

public class ClassProbesAdapterAnnotation extends ClassProbesAdapter {

	public ClassProbesAdapterAnnotation(ClassProbesVisitor cv, boolean trackFrames) {
		super(cv, trackFrames);
		// TODO Auto-generated constructor stub
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		System.err.println(">>>>>>"+desc+"  "+visible);
		return super.visitAnnotation(desc, visible);
	}
}
