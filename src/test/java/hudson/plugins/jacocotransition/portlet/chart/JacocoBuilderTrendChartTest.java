package hudson.plugins.jacocotransition.portlet.chart;

import org.junit.Test;

import hudson.plugins.jacocotransition.portlet.chart.JacocoIntegrityTrendTransitionBuilderTrendChart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JacocoBuilderTrendChartTest {
    @Test
    public void construct() throws Exception {
        JacocoIntegrityTrendTransitionBuilderTrendChart chart = new JacocoIntegrityTrendTransitionBuilderTrendChart("chart", "200", "500", "3");
        assertEquals(3, chart.getDaysNumber());
        assertEquals(200, chart.getWidth());
        assertEquals(500, chart.getHeight());

        // causes an NPE because Stapler.getCurrentRequest() returns null: assertNotNull(chart.getSummaryGraph());
    }

    @Test
    public void descriptor() throws Exception {
        JacocoIntegrityTrendTransitionBuilderTrendChart.DescriptorImpl descriptor = new JacocoIntegrityTrendTransitionBuilderTrendChart.DescriptorImpl();
        assertNotNull(descriptor.getDisplayName());
    }
}