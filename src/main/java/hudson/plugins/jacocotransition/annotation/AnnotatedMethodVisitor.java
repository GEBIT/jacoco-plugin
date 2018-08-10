package hudson.plugins.jacocotransition.annotation;

import java.util.Map;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;

public class AnnotatedMethodVisitor extends MethodVisitor {

	private Map<String,String> map;
	private String prefix;
	
	public AnnotatedMethodVisitor(int api, MethodVisitor mv) {
		super(api, mv);
		// TODO Auto-generated constructor stub
	}

	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		//System.err.println("!!!!!>>>> AnnotatedMethodVisitor ANOO desc="+desc);
		AnnotationTransitationVisitor anno = new  AnnotationTransitationVisitor(api,super.visitAnnotation(desc, visible));
		anno.setMap(map);
		anno.setPrefix(prefix);
		return anno;
	}
	
	
	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public Map<String, String> getMap() {
		return map;
	}
}
