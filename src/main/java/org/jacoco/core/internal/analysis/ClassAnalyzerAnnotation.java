package org.jacoco.core.internal.analysis;

import org.jacoco.core.analysis.IMethodCoverage;
import org.jacoco.core.internal.analysis.ClassAnalyzer;
import org.jacoco.core.internal.analysis.ClassCoverageImpl;
import org.jacoco.core.internal.analysis.MethodAnalyzer;
import org.jacoco.core.internal.analysis.StringPool;
import org.jacoco.core.internal.analysis.filter.Filters;
import org.jacoco.core.internal.flow.MethodProbesVisitor;
import org.jacoco.core.internal.instr.InstrSupport;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.TypePath;

public class ClassAnalyzerAnnotation extends ClassAnalyzer {

	

	public ClassAnalyzerAnnotation(ClassCoverageImpl coverage, boolean[] probes, StringPool stringPool) {
		
		super(coverage, probes, stringPool);
		System.err.println("new ClassAnalyzerAnnotation!! "+coverage.getClass()+" "+coverage);
	}
	
	
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		System.err.println("ClassAnalyzerAnnotation visit Annotation (callsuper)!!====>"+desc+" "+visible);
		

		AnnotationVisitor xx = super.visitAnnotation(desc, visible);
		System.err.println("ClassAnalyzerAnnotation annotaion visited="+xx.getClass()+" "+xx.toString());
		return xx;
	}

	public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
		System.err.println("!!!====>"+desc+" "+visible+" "+typePath.toString());
		return super.visitTypeAnnotation(typeRef, typePath, desc, visible);
	}

	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		super.visit(version, access, name, signature, superName, interfaces);
	}

	public MethodProbesVisitor visitMethod(int access, String name, String desc, String signature,
			String[] exceptions) {
		System.err.println("ClassAnalyzerAnnotation: call Super for visitMethod "+name);
		return super.visitMethod(access, name, desc, signature, exceptions);
		
	}
	
/*
	public MethodProbesVisitor visitMethod(int access, String name, String desc, String signature,
			String[] exceptions) {
		InstrSupport.assertNotInstrumented(name, this.coverage.getName());

		//System.err.println("====>"+name+"  "+desc);
		return super.visitMethod(access, name, desc, signature, exceptions);
		
	}
	public void visitTotalProbeCount(int count) {
	}
*/
	

	
}
