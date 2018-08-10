package hudson.plugins.jacocotransition.refine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.ICoverageVisitor;
import org.jacoco.core.data.ExecutionData;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.internal.BytecodeVersion;
import org.jacoco.core.internal.ContentTypeDetector;
import org.jacoco.core.internal.InputStreams;
import org.jacoco.core.internal.Pack200Streams;
import org.jacoco.core.internal.analysis.ClassAnalyzer;
import org.jacoco.core.internal.analysis.ClassAnalyzerAnnotation;
import org.jacoco.core.internal.analysis.ClassCoverageImpl;
import org.jacoco.core.internal.analysis.ClassProbesAdapterAnnotation;
import org.jacoco.core.internal.analysis.StringPool;
import org.jacoco.core.internal.data.CRC64;
import org.jacoco.core.internal.flow.ClassProbesAdapter;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.TypePath;

import hudson.plugins.jacocotransition.annotation.AnnotationExtractor;


public class AnalyzerAnnotation extends Analyzer {
	private final ExecutionDataStore executionData;
	private final ICoverageVisitor coverageVisitor;
	private final StringPool stringPool;
	private final Map<String,String> map;

	
	public AnalyzerAnnotation(ExecutionDataStore executionData, ICoverageVisitor coverageVisitor, Map<String, String> map) {
		super((ExecutionDataStore)null,(ICoverageVisitor)null);
		this.executionData = executionData;
		this.coverageVisitor = coverageVisitor;
		this.stringPool = new StringPool();
		this.map=map;
	}

	private ClassVisitor createAnalyzingVisitor(long classid, String className) {
		ExecutionData data = this.executionData.get(classid);
		boolean noMatch;
		boolean[] probes;
	
		if (data == null) {
			probes = null;
			noMatch = this.executionData.contains(className);
		} else {
			probes = data.getProbes();
			noMatch = false;
		}
		final ClassCoverageImpl coverage = new ClassCoverageImpl(className, classid, noMatch);

		//ClassAnalyzer analyzer = new ClassAnalyzerAnnotation(coverage, probes, this.stringPool) {
		ClassAnalyzer analyzer = new ClassAnalyzer(coverage, probes, this.stringPool) {
			public void visitEnd() {
				super.visitEnd();
				coverageVisitor.visitCoverage(coverage);
			}
		};
		return new ClassProbesAdapter(analyzer, false);
	}

	public void analyzeClass(ClassReader reader) {
		analyzeClass(reader.b);
	}

	private void analyzeClass(byte[] source) {
		long classId = CRC64.classId(source);
		int version = BytecodeVersion.get(source);
		byte[] b = BytecodeVersion.downgradeIfNeeded(version, source);
		ClassReader reader = new ClassReader(b);
		ClassVisitor visitor = createAnalyzingVisitor(classId, reader.getClassName());
		//ClassVisitorAnno visitwrap = new ClassVisitorAnno(visitor);
	
		
		//reader.accept(visitwrap, 0);
		
		reader.accept(visitor,0);
		
		AnnotationExtractor ae = new AnnotationExtractor(map);
		ae.analyzeClass(b);
		
		
	}

	public void analyzeClass(byte[] buffer, String location) throws IOException {
		try {
			analyzeClass(buffer);
		} catch (RuntimeException cause) {
			throw analyzerError(location, cause);
		}
	}

	public void analyzeClass(InputStream input, String location) throws IOException {
		byte[] buffer;
		try {
			buffer = InputStreams.readFully(input);
		} catch (IOException e) {
			throw analyzerError(location, e);
		}
		analyzeClass(buffer, location);
	}

	private IOException analyzerError(String location, Exception cause) {
		IOException ex = new IOException(String.format("Error while analyzing %s.", new Object[] { location }));

		ex.initCause(cause);
		return ex;
	}

	public int analyzeAll(final InputStream input, final String location)
			throws IOException {
		final ContentTypeDetector detector;
		try {
			detector = new ContentTypeDetector(input);
		} catch (final IOException e) {
			throw analyzerError(location, e);
		}
		switch (detector.getType()) {
		case ContentTypeDetector.CLASSFILE:
			analyzeClass(detector.getInputStream(), location);
			return 1;
		case ContentTypeDetector.ZIPFILE:
			return analyzeZip(detector.getInputStream(), location);
		case ContentTypeDetector.GZFILE:
			return analyzeGzip(detector.getInputStream(), location);
		case ContentTypeDetector.PACK200FILE:
			return analyzePack200(detector.getInputStream(), location);
		default:
			return 0;
		}
	}


	public int analyzeAll(File file) throws IOException {
		int count = 0;
		if (file.isDirectory()) {
			try {
			for (File f : file.listFiles()){
				count += analyzeAll(f);
			} } catch (NullPointerException e) {
				System.err.println("NPE for file"+file.getCanonicalPath());
			}
		} else {
			InputStream in = new FileInputStream(file);
			try {
				count += analyzeAll(in, file.getPath());
			} finally {
				in.close();
			}
		}
		return count;
	}

	
	
	public int analyzeAll(String path, File basedir) throws IOException {
		int count = 0;
		StringTokenizer st = new StringTokenizer(path, File.pathSeparator);

		while (st.hasMoreTokens()) {
			count += analyzeAll(new File(basedir, st.nextToken()));
		}
		return count;
	}

	private int analyzeZip(InputStream input, String location) throws IOException {
		ZipInputStream zip = new ZipInputStream(input);
		ZipEntry entry;
		int count = 0;
		while ((entry = nextEntry(zip, location)) != null) {
			count += analyzeAll(zip, location + "@" + entry.getName());
		}
		return count;
	}

	private ZipEntry nextEntry(ZipInputStream input, String location) throws IOException {
		try {
			return input.getNextEntry();
		} catch (IOException e) {
			throw analyzerError(location, e);
		}
	}

	private int analyzeGzip(InputStream input, String location) throws IOException {
		GZIPInputStream gzipInputStream;
		try {
			gzipInputStream = new GZIPInputStream(input);
		} catch (IOException e) {
			throw analyzerError(location, e);
		}
		return analyzeAll(gzipInputStream, location);
	}

	private int analyzePack200(InputStream input, String location) throws IOException {
		InputStream unpackedInput;
		try {
			unpackedInput = Pack200Streams.unpack(input);
		} catch (IOException e) {
			throw analyzerError(location, e);
		}
		return analyzeAll(unpackedInput, location);
	}
}
