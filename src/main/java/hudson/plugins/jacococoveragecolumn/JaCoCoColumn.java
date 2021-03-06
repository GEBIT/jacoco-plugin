package hudson.plugins.jacococoveragecolumn;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.model.Job;
import hudson.model.Run;
import hudson.plugins.jacocotransition.JacocoIntegrityTrendTransitionBuildAction;
import hudson.plugins.jacocotransition.model.Coverage;
import hudson.views.ListViewColumnDescriptor;
import hudson.views.ListViewColumn;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

import net.sf.json.JSONObject;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

import javax.annotation.Nonnull;

/**
 * View column that shows the code coverage (line) percentage
 *
 */
public class JaCoCoColumn extends ListViewColumn {

	@DataBoundConstructor
	public JaCoCoColumn() {
	}

	public boolean hasCoverage(final Job<?, ?> job) {
		final Run<?, ?> lastSuccessfulBuild = job.getLastSuccessfulBuild();
		return lastSuccessfulBuild != null &&
				lastSuccessfulBuild.getAction(JacocoIntegrityTrendTransitionBuildAction.class) != null;
	}

	public String getPercent(final Job<?, ?> job) {
		final StringBuilder stringBuilder = new StringBuilder();

		if (!hasCoverage(job)) {
			stringBuilder.append("N/A");
		} else {
			final Run<?, ?> lastSuccessfulBuild = job.getLastSuccessfulBuild();
			final Double percent = getLinePercent(lastSuccessfulBuild);
			stringBuilder.append(percent);
		}

		return stringBuilder.toString();
	}

	public String getLineColor(final Job<?, ?> job, final BigDecimal amount) {
		if (amount == null) {
			return null;
		}

		if(job != null && !hasCoverage(job)) {
			return CoverageRange.NA.getLineHexString();
		}

		return CoverageRange.valueOf(amount.doubleValue()).getLineHexString();
	}

	public String getFillColor(final Job<?, ?> job, final BigDecimal amount) {
		if (amount == null) {
			return null;
		}

		if(job != null && !hasCoverage(job)) {
			return CoverageRange.NA.getFillHexString();
		}

		final Color c = CoverageRange.fillColorOf(amount.doubleValue());
		return CoverageRange.colorAsHexString(c);
	}

	public BigDecimal getLineCoverage(final Job<?, ?> job) {
		final Run<?, ?> lastSuccessfulBuild = job.getLastSuccessfulBuild();
		return BigDecimal.valueOf(getLinePercent(lastSuccessfulBuild));
	}

	private Double getLinePercent(final Run<?, ?> lastSuccessfulBuild) {
		final Float percentageFloat = getPercentageFloat(lastSuccessfulBuild);
		final double doubleValue = percentageFloat.doubleValue();

		final int decimalPlaces = 2;
		BigDecimal bigDecimal = new BigDecimal(doubleValue);

		// setScale is immutable
		bigDecimal = bigDecimal.setScale(decimalPlaces,
				RoundingMode.HALF_UP);
		return bigDecimal.doubleValue();
	}

	private Float getPercentageFloat(final Run<?, ?> lastSuccessfulBuild) {
		if(lastSuccessfulBuild == null) {
			return 0f;
		}

		final JacocoIntegrityTrendTransitionBuildAction action = lastSuccessfulBuild
				.getAction(JacocoIntegrityTrendTransitionBuildAction.class);

		if(action == null) {
			return 0f;
		}

		final Coverage ratio = action.getLineCoverage();
		return ratio.getPercentageFloat();
	}

	@Extension
	public static final Descriptor<ListViewColumn> DESCRIPTOR = new DescriptorImpl();

	@Override
	public Descriptor<ListViewColumn> getDescriptor() {
		return DESCRIPTOR;
	}

	private static class DescriptorImpl extends ListViewColumnDescriptor {
		@Override
		public ListViewColumn newInstance(final StaplerRequest req,
										  @Nonnull final JSONObject formData) {
			return new JaCoCoColumn();
		}
		
		@Override
		public boolean shownByDefault() {
			return false;
		}

		@Nonnull
		@Override
		public String getDisplayName() {
			return "JaCoCo Line Coverage";
		}
	}
}
