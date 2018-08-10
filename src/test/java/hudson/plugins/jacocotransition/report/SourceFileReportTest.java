package hudson.plugins.jacocotransition.report;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import hudson.plugins.jacocotransition.report.SourceFileReport;



public class SourceFileReportTest {
    @Test
    public void test() {
        SourceFileReport report = new SourceFileReport();
        report.setName("myname");
        assertEquals("myname", report.getName());
    }
}
