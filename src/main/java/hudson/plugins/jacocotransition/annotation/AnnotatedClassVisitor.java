package hudson.plugins.jacocotransition.annotation;

import java.util.Map;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class AnnotatedClassVisitor extends ClassVisitor {

	private Map<String,String> map;
	private String clazzname;
	
	public AnnotatedClassVisitor(int api, ClassVisitor cv) {
		super(api, cv);	
	}
	
	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public Map<String, String> getMap() {
		return map;
	}


	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		//System.err.println("!!>>>>> CLASS "+name+"  "+signature);
		clazzname=name;
		super.visit(version, access, name, signature, superName, interfaces);
	}
	
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		//System.err.println("!>>>> ANOO desc="+desc);
		AnnotationTransitationVisitor annoVisitor = new AnnotationTransitationVisitor(api, super.visitAnnotation(desc, visible));
		annoVisitor.setMap(map);
		annoVisitor.setPrefix(clazzname);
		return annoVisitor;
	}
	
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		//System.err.println("!>>>> METHOD "+name+" "+desc+" "+signature);
		AnnotatedMethodVisitor amv = new AnnotatedMethodVisitor(api,super.visitMethod(access, name, desc, signature, exceptions));
		amv.setMap(map);
		amv.setPrefix(clazzname+"."+name);
		return amv;
	}
	
	public void visitEnd() {
		super.visitEnd();
	}
	
	
}
