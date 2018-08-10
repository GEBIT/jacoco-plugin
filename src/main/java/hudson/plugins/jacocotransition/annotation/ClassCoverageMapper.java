package hudson.plugins.jacocotransition.annotation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.analysis.ICoverageNode;
import org.jacoco.core.analysis.ILine;
import org.jacoco.core.analysis.IMethodCoverage;
import org.jacoco.core.internal.analysis.CounterImpl;

public class ClassCoverageMapper extends Mapper implements IClassCoverage {

	private IClassCoverage cov;
	private Collection<IMethodCoverage> methodList;
	private Boolean initCalled;
	private ICounter methodCounter; 
	
	public ClassCoverageMapper(IClassCoverage cov, Map<String, String> map) {
		super(map);
		this.cov = cov;
	}

	public boolean isInitCalled() {
		if(initCalled == null){
			getMethods();
		}
		return initCalled;
	}
	
	public ElementType getElementType() {
		return cov.getElementType();
	}

	public String getName() {
		return get("ClassCoverageMapper getName", cov.getName());
	}

	public ICounter getInstructionCounter() {
		return cov.getInstructionCounter();
	}

	public ICounter getBranchCounter() {
		return cov.getBranchCounter();
	}

	public ICounter getLineCounter() {
		return cov.getLineCounter();
	}

	public ICounter getComplexityCounter() {
		return cov.getComplexityCounter();
	}

	public ICounter getMethodCounter() {
		
		if(methodCounter== null) {
			ICounter counter = cov.getMethodCounter();
			int missed= counter.getMissedCount();
			int covered= counter.getCoveredCount();
			
			if(isInitCalled()){
				covered--;
			} else {
				missed--;
			}
			methodCounter = CounterImpl.getInstance(missed,covered);
		} 
		return methodCounter;

	}

	public ICounter getClassCounter() {
		return cov.getClassCounter();
	}

	public ICounter getCounter(CounterEntity paramCounterEntity) {
		return cov.getCounter(paramCounterEntity);
	}

	public int getFirstLine() {
		return cov.getFirstLine();
	}

	public long getId() {
		return cov.getId();
	}

	public String[] getInterfaceNames() {
		return cov.getInterfaceNames();
	}

	public int getLastLine() {
		return cov.getLastLine();
	}

	public ILine getLine(int paramInt) {
		return cov.getLine(paramInt);
	}

	public Collection<IMethodCoverage> getMethods() {
		if (methodList == null) {
			methodList = new ArrayList<>();
			for (IMethodCoverage c : cov.getMethods()) {
				if(c.getName().endsWith("<init>")) {
					ICounter counter = cov.getMethodCounter();
					initCalled = counter.getCoveredCount()>0;
				}
				else {
					methodList.add(new MethodCoverageMapper(c, map, cov.getName()));
				}
			}
		}
		return methodList;
	}

	public String getPackageName() {
		return cov.getPackageName();
	}

	public ICoverageNode getPlainCopy() {
		return cov.getPlainCopy();
	}

	public String getSignature() {
		return cov.getSignature();
	}

	public String getSourceFileName() {
		return cov.getSourceFileName();
	}

	public String getSuperName() {
		return cov.getSuperName();
	}

	public boolean isNoMatch() {
		return cov.isNoMatch();
	}

}
