package es.uam.eps.neuro.perceptron.domain;

import java.util.ArrayList;

public class OutputData {
	
	private ArrayList<String> data = new ArrayList<>();
	private StringBuilder buffer = new StringBuilder();
	
	public void add(Double value) {
		add(String.valueOf(value));
	}
	
	public void add(Integer value) {
		add(String.valueOf(value));
	}
	
	public void add(String value) {
		buffer.append(value + "\t");
	}
	
	public void newLine() {
		data.add(buffer.toString());
		buffer.setLength(0);
	}
	
	public ArrayList<String> getFileLines() {
		return data;
	}
}
