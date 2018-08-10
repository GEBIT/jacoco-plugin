package hudson.plugins.jacocotransition.annotation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.jacoco.core.internal.InputStreams;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;

public class AnnotationExtractor {

	private Map<String, String> map;

	public AnnotationExtractor(Map<String, String> map) {
		this.map = map;
	}

	public void analyzeClass(File file) throws IOException {
		FileInputStream is = null;
		try {
			is = new FileInputStream(file);
			analyzeClass(is);

		} finally {
			if(is != null){
				is.close();
			}
		}
	}

	public void analyzeClass(InputStream input) throws IOException {
		analyzeClass(InputStreams.readFully(input));
	}

	public void analyzeClass(byte[] bytes) {
		ClassReader reader = new ClassReader(bytes);
		AnnotatedClassVisitor classVisitor = new AnnotatedClassVisitor(Opcodes.ASM6, null);
		classVisitor.setMap(map);
		reader.accept(classVisitor, 0);
		// System.err.println(map);
	}

}
