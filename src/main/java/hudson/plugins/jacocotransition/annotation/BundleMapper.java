package hudson.plugins.jacocotransition.annotation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.jacoco.core.analysis.IBundleCoverage;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.analysis.ICoverageNode;
import org.jacoco.core.analysis.IPackageCoverage;
import org.jacoco.core.internal.analysis.CounterImpl;

public class BundleMapper extends Mapper implements IBundleCoverage {
	private IBundleCoverage cov;
	private Collection<IPackageCoverage> packageList;

	private CounterImpl workflowCounter;
	private CounterImpl methodCounter;
	private CounterImpl activityCounter;

	public BundleMapper(IBundleCoverage cov, Map<String, String> map) {
		super(map);
		this.cov = cov;
	}

	public ElementType getElementType() {
		return cov.getElementType();
	}

	public String getName() {
		return get("Bundlename", cov.getName());
	}

	public Collection<IPackageCoverage> getPackages() {
		if (packageList == null) {
			packageList = new ArrayList<>();
			for (IPackageCoverage ic : cov.getPackages()) {
				if (!"de/gebit/ep/transitioncoverage/csv2jacoco/generated".equals(ic.getName())) {
					packageList.add(new PackageCoverageMapper(ic, map));
				}
			}
		}
		return packageList;
	}

	public ICounter getInstructionCounter() {
		return cov.getInstructionCounter();
	}

	public ICounter getBranchCounter() {
		return cov.getBranchCounter();
	}

	//used as workflow counter for missed/covered workflows
	public ICounter getLineCounter() {
		if (workflowCounter == null) {
			for (IPackageCoverage ic : getPackages()) {
				if (workflowCounter == null) {
					workflowCounter = CounterImpl.getInstance(ic.getLineCounter());
				} else {
					workflowCounter = workflowCounter.increment(ic.getLineCounter());
				}
			}
		}
		if (workflowCounter == null) {
			workflowCounter = CounterImpl.getInstance(0, 0);
		}
		return workflowCounter;
	}

	public ICounter getComplexityCounter() {
		return cov.getComplexityCounter();
	}

	public ICounter getMethodCounter() {
		if (methodCounter == null) {
			methodCounter = CounterImpl.COUNTER_0_0;
			for (IPackageCoverage packageCoverage : getPackages()) {
				methodCounter = methodCounter.increment(packageCoverage.getMethodCounter());
			}
		}
		return methodCounter;
	}

	public ICounter getClassCounter() {
		if(activityCounter == null) {
			activityCounter=CounterImpl.COUNTER_0_0;
			for (IPackageCoverage packageCoverage : getPackages()) {
				activityCounter = activityCounter.increment(packageCoverage.getClassCounter());
			}
		}
		return activityCounter;
	}

	public ICounter getCounter(CounterEntity paramCounterEntity) {
		return cov.getCounter(paramCounterEntity);
	}

	public ICoverageNode getPlainCopy() {
		return cov.getPlainCopy();
	}


}
