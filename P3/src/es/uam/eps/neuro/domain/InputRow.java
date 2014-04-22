package es.uam.eps.neuro.domain;

import java.util.ArrayList;
import java.util.Collections;

public class InputRow {
	
	private ArrayList<Double> inputs = new ArrayList<>();
	private ArrayList<Double> targetClass = new ArrayList<>();
	private String target = "";
	private boolean encoded;
	
	public InputRow() {
		encoded = true;
	}
	
	public InputRow(boolean encoded) {
		this.encoded = encoded;
	}
	
	public void add(Double input) {
		if (encoded) {
			if(input==0)
				inputs.add(-0.9);
			else
				inputs.add(0.9);
		} else {
			inputs.add(input);
		}
		//System.out.println("add " + input);
	}
	
	public void addToTargetRepresentation(String input) {
		if (target.isEmpty()) {
			target = input;
		} else {
			target = target + " " + input;
		}
	}
	
	public void addToTargetClass(String input) {
		if (encoded) {
			double value = -0.9;
			if(Double.parseDouble(input)==1) {
				value = 0.9;
			}
			targetClass.add(value);
		} else {
			targetClass.add(Double.valueOf(input));
		}
	}
	
	public int getTargetClass(){
		return targetClass.indexOf(Collections.max(targetClass));
	}	
	public double getTargetValue(int oputputNeuron){
		return targetClass.get(oputputNeuron);
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
			System.out.print(input + " ");
		}
	}
}
