package hudson.plugins.jacocotransition.model;

import java.util.Locale;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import hudson.plugins.jacocotransition.model.CoverageGraphLayout.CoverageType;

import static hudson.plugins.jacocotransition.model.CoverageGraphLayout.CoverageType.BRANCH;
import static hudson.plugins.jacocotransition.model.CoverageGraphLayout.CoverageType.CLAZZ;
import static hudson.plugins.jacocotransition.model.CoverageGraphLayout.CoverageType.COMPLEXITY;
import static hudson.plugins.jacocotransition.model.CoverageGraphLayout.CoverageType.INSTRUCTION;
import static hudson.plugins.jacocotransition.model.CoverageGraphLayout.CoverageType.LINE;
import static hudson.plugins.jacocotransition.model.CoverageGraphLayout.CoverageType.METHOD;
import static hudson.plugins.jacocotransition.model.CoverageGraphLayout.CoverageValue.COVERED;
import static hudson.plugins.jacocotransition.model.CoverageGraphLayout.CoverageValue.MISSED;
import static hudson.plugins.jacocotransition.model.CoverageGraphLayout.CoverageValue.PERCENTAGE;
import static hudson.plugins.jacocotransition.model.CoverageGraphLayout.CoverageValue.values;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static org.junit.Assert.assertEquals;

public class CoverageGraphLayoutTest {

    private Locale localeBackup;

    @Before
    public void setUp() {
        localeBackup = Locale.getDefault();
    }

    @After
    public void tearDown() {
        Locale.setDefault(localeBackup);
    }

    @Test
    public void type() {
        Locale.setDefault(ENGLISH);

        assertEquals("New Coverage Types", 6, CoverageType.values().length);
        assertEquals("instructions", INSTRUCTION.getMessage());
        assertEquals("workflow", LINE.getMessage());
        assertEquals("branch", BRANCH.getMessage());
        assertEquals("transition", METHOD.getMessage());
        assertEquals("activity", CLAZZ.getMessage());
        assertEquals("complexity", COMPLEXITY.getMessage());

        Locale.setDefault(GERMAN);
        assertEquals("Anweisungen", INSTRUCTION.getMessage());
        assertEquals("Workflow", LINE.getMessage());
        assertEquals("Branch", BRANCH.getMessage());
        assertEquals("Transitionen", METHOD.getMessage());
        assertEquals("Aktivity", CLAZZ.getMessage());
        assertEquals("KomplexitÃ¤t", COMPLEXITY.getMessage()); // TODO there might be an encoding issue with our resources?

    }

    @Test
    public void value() {
        Locale.setDefault(ENGLISH);
        assertEquals("New Coverage Value", 3, values().length);
        assertEquals("workflow covered", COVERED.getMessage(LINE));
        assertEquals("workflow missed", MISSED.getMessage(LINE));
        assertEquals("workflow", PERCENTAGE.getMessage(LINE));

        Locale.setDefault(GERMAN);
        assertEquals("Workflow abgedeckt", COVERED.getMessage(LINE));
        assertEquals("Workflow nicht abgedeckt", MISSED.getMessage(LINE));
        assertEquals("Workflow", PERCENTAGE.getMessage(LINE));
    }
}