package es.uam.eps.neuro.domain;

import java.util.ArrayList;

public class InputRow {
	
	private ArrayList<Double> inputs = new ArrayList<>();
	private String target = "";
	
	public void add(Double input) {
		inputs.add(input);
		//System.out.println("add " + input);
	}

	public void addToTargetRepresentation(String input) {
		if (target.isEmpty()) {
			target = input;
		} else {
			target = target + " " + input;
		}
	}
	
	public Double get(int index) {
		return inputs.get(index);
	}
	
	public ArrayList<Double> getAll() {
		return inputs;
	}
	
	public String getTargetRepresentation() {
		return target;
	}
	
	public void print() {
		for (Double input : inputs) {
			System.out.print(input);
			System.out.print(" ");
		}
	}
}
