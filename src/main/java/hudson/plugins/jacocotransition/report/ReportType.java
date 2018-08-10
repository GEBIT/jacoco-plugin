package hudson.plugins.jacocotransition.report;

import java.awt.Color;

import hudson.plugins.jacocotransition.model.CoverageGraphLayout;
import hudson.plugins.jacocotransition.model.CoverageGraphLayout.CoverageType;

public enum ReportType {
	WORKFLOW(CoverageType.LINE, Color.BLUE, Color.CYAN), ACTIVITY(CoverageType.CLAZZ, Color.YELLOW,
			Color.BLACK), TRANSITION(CoverageType.METHOD, Color.RED, Color.GREEN);

	private CoverageGraphLayout.CoverageType jacocoCoverageType;
	private Color missed;
	private Color covered;

	ReportType(CoverageGraphLayout.CoverageType jacocoCoverageType, Color missed, Color covered) {
		this.jacocoCoverageType = jacocoCoverageType;
		this.missed = missed;
		this.covered = covered;
	}

	public Color getMissed() {
		return missed;
	}

	public Color getCovered() {
		return covered;
	}

	public CoverageGraphLayout.CoverageType getJacocoCoverageType() {
		return jacocoCoverageType;
	}

	public Color get(CoverageGraphLayout.CoverageValue cv) {
		switch (cv) {
		case COVERED:
			return getCovered();

		case MISSED:
			return getMissed();

		default:
			throw new IllegalArgumentException();

		}
	}
	
	public String getMissedStr() {
		return getHexStr(missed);
	}

	public String getCoveredStr() {
		return getHexStr(covered);
	}



	public String getStr(CoverageGraphLayout.CoverageValue cv) {
		switch (cv) {
		case COVERED:
			return getCoveredStr();

		case MISSED:
			return getMissedStr();

		default:
			throw new IllegalArgumentException();
		}
	}

	public String getHexStr(Color c) {
        return toHexStr(c.getRed(), c.getGreen(), c.getBlue());
      }

      
      public static String toHexStr(int r, int g, int b) {
        return "#" + toBrowserHexValue(r) + toBrowserHexValue(g) + toBrowserHexValue(b);
      }

      private static String toBrowserHexValue(int number) {
        StringBuilder builder = new StringBuilder(Integer.toHexString(number & 0xff));
        while (builder.length() < 2) {
          builder.append("0");
        }
        return builder.toString().toUpperCase();
      }
	
}
