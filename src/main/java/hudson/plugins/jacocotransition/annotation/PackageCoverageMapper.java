package hudson.plugins.jacocotransition.annotation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.analysis.ICoverageNode;
import org.jacoco.core.analysis.IPackageCoverage;
import org.jacoco.core.analysis.ISourceFileCoverage;
import org.jacoco.core.internal.analysis.CounterImpl;

public class PackageCoverageMapper extends Mapper implements IPackageCoverage {

	private IPackageCoverage cov;
	private Collection<IClassCoverage> classList;
	private CounterImpl methodCounter;

	public PackageCoverageMapper(IPackageCoverage cov, Map<String, String> map) {
		super(map);
		this.cov = cov;
	}

	
	public String shortWorkflowName() {
		String name= cov.getName();
		
		int index = name.lastIndexOf("/");
		if(index > 0 && index < name.length()) {
			name = name.substring(index);
		} 
		
		return name;
	}
	
	public String getWorkflowType(){
		String[] split = cov.getName().split("/");
		if(split.length>1){
			return split[1];
		}
		return "default";
	}
	
	public ElementType getElementType() {
		return cov.getElementType();
	}

	public String getName() {
		return get(" PackageCoverageMapper getName)", cov.getName());
	}

	public Collection<IClassCoverage> getClasses() {
		if (classList == null) {
			classList = new ArrayList<>();
			for (IClassCoverage c : cov.getClasses()) {
				classList.add(new ClassCoverageMapper(c, map));
			}
		}
		return classList;
	}

	public ICounter getInstructionCounter() {
		return cov.getInstructionCounter();
	}

	public Collection<ISourceFileCoverage> getSourceFiles() {
		return cov.getSourceFiles();
	}

	public ICounter getBranchCounter() {
		return cov.getBranchCounter();
	}

	public ICounter getLineCounter() {
		if(getClassCounter().getCoveredCount() > 0) return 
				CounterImpl.getInstance(0, 1);
		
		return CounterImpl.getInstance(1, 0);
	}

	public ICounter getComplexityCounter() {
		return cov.getComplexityCounter();
	}

	public ICounter getMethodCounter() {
		if(methodCounter== null) {
			methodCounter = CounterImpl.getInstance(0,0);
			 for(IClassCoverage claz : getClasses()) {
				 methodCounter =  methodCounter.increment(claz.getMethodCounter());
			 }
		}
		return methodCounter;
	}

	public ICounter getClassCounter() {
		return cov.getClassCounter();
	}

	public ICounter getCounter(CounterEntity paramCounterEntity) {
		return cov.getCounter(paramCounterEntity);
	}

	public ICoverageNode getPlainCopy() {
		return cov.getPlainCopy();
	}

}
