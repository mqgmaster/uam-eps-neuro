package es.uam.eps.neuro.domain;

import java.util.ArrayList;
import java.util.Collections;

public class InputRow {
	
	private ArrayList<Double> inputs = new ArrayList<>();
	private ArrayList<Integer> targetClass = new ArrayList<>();
	private ArrayList<Integer> bipolarTargetClass = new ArrayList<>();
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
	
	public void addToTargetClass(String input) {
		targetClass.add(Integer.parseInt(input));
	}
	
	public void addToBipolarTargetClass(String input) {
		int value = -1;
		if(Integer.parseInt(input)==1)
			value = 1;
		bipolarTargetClass.add(value);
	}
	
	public int getTargetClass(){
		return targetClass.indexOf(Collections.max(targetClass));
	}	
	public int getTargetValue(int oputputNeuron){
		return targetClass.get(oputputNeuron);
	}
	public int getBipolarTargetValue(int oputputNeuron){
		return bipolarTargetClass.get(oputputNeuron);
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
