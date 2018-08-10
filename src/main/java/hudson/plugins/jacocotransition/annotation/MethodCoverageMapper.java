package hudson.plugins.jacocotransition.annotation;

import java.util.Map;

import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.analysis.ICoverageNode;
import org.jacoco.core.analysis.ILine;
import org.jacoco.core.analysis.IMethodCoverage;

public class MethodCoverageMapper extends Mapper implements IMethodCoverage {

	
	private IMethodCoverage cov;
	private String prefix;

	public MethodCoverageMapper(IMethodCoverage cov, Map<String,String> map, String prefix ){
		super(map);
		this.cov=cov;
		this.prefix=prefix;
	}

	public ElementType getElementType() {
		return cov.getElementType();
	}

	public String getName() {
		return get("MethodCoverageMapper getName",prefix+"."+cov.getName());
	}

	public int getFirstLine() {
		return cov.getFirstLine();
	}

	public ICounter getInstructionCounter() {
		return cov.getInstructionCounter();
	}

	public int getLastLine() {
		return cov.getLastLine();
	}

	public ICounter getBranchCounter() {
		return cov.getBranchCounter();
	}

	public ILine getLine(int paramInt) {
		return cov.getLine(paramInt);
	}

	public ICounter getLineCounter() {
		return cov.getLineCounter();
	}

	public ICounter getComplexityCounter() {
		return cov.getComplexityCounter();
	}

	public ICounter getMethodCounter() {
		return cov.getMethodCounter();
	}

	public ICounter getClassCounter() {
		return cov.getClassCounter();
	}

	public ICounter getCounter(CounterEntity paramCounterEntity) {
		return cov.getCounter(paramCounterEntity);
	}

	public String getDesc() {
		return cov.getDesc();
	}

	public ICoverageNode getPlainCopy() {
		return cov.getPlainCopy();
	}

	public String getSignature() {
		return cov.getSignature();
	}
	
	
}
