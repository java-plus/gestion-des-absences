package fr.gda.model;

public class Data {

	private String type = "bar";
	private String labels;
	private String label = "Synthèse par jour";
	private int[] data;
	private String[] backgroundColor;
	private String[] borderColor;
	private int borderWidth = 1;
	private boolean beginAtZero = true;

	// Creating toString
	@Override
	public String toString() {
		return "dataChart [type=" + type + ", data= {" + " labels= [" + labels + "]," + " datasets= [{" + label + ", data= [" + data + "]," + ", backgroundColor= [" + backgroundColor + "]," + " borderColor= [" + borderColor + "]," + " borderWidth= " + borderWidth + "}]" + "}," + " option= {" + " scales= {" + " yAxes= [{" + " ticks= {" + " beginAtZero= " + beginAtZero + "}" + "}]" + "}" + "}"
				+ "}";

	}
}
