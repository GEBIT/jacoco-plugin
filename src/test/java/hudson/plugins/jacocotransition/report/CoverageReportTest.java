package hudson.plugins.jacocotransition.report;

import static org.junit.Assert.*;

import hudson.plugins.jacocotransition.ExecutionFileLoader;
import hudson.plugins.jacocotransition.JacocoHealthReportThresholds;
import hudson.plugins.jacocotransition.JacocoIntegrityTrendTransitionBuildAction;
import hudson.plugins.jacocotransition.report.CoverageReport;
import hudson.util.StreamTaskListener;
import org.junit.Test;


public class CoverageReportTest {
    @Test
    public void testGetBuild() throws Exception {
        CoverageReport report = new CoverageReport(action, new ExecutionFileLoader());
        assertNull(report.getBuild());
    }

    @Test
    public void testName() throws Exception {
        CoverageReport report = new CoverageReport(action, new ExecutionFileLoader());
        assertEquals("TrendIntegrityJacoco", report.getName());
    }

    @Test
    public void testDoJaCoCoExec() throws Exception {
        CoverageReport report = new CoverageReport(action, new ExecutionFileLoader());
        assertNotNull(report);
        // TODO: how to simulate JaCoCoBuildAction without full Jenkins test-framework?
        // report.doJacocoExec();
    }

    @Test
    public void testThresholds() throws Exception {
        CoverageReport report = new CoverageReport(action, new ExecutionFileLoader());
        report.setThresholds(new JacocoHealthReportThresholds());
    }

    private JacocoIntegrityTrendTransitionBuildAction action = new JacocoIntegrityTrendTransitionBuildAction(null, null, StreamTaskListener.fromStdout(), null, null);
}
