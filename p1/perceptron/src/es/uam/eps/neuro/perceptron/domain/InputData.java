package es.uam.eps.neuro.perceptron.domain;

import java.util.ArrayList;

public class InputData {
	
	private Double totalInputs;
	private Double totalClass; 
	private ArrayList<ArrayList<Double>> inputData = new ArrayList<>();
	
	public InputData(ArrayList<String> fileLines) {
		String params = fileLines.remove(0);
		String[] paramsVector = params.split(" ");
		totalInputs = Double.valueOf(paramsVector[0]);
		totalClass = Double.valueOf(paramsVector[1]);
		
		System.out.println("inputs " + totalInputs + " classes: " + totalClass);
		//Collections.shuffle(list);
		for (String inputs : fileLines) {
			ArrayList<Double> inputDataLine = new ArrayList<>();
			String[] inputsArray = inputs.split(" ");
			for (String input : inputsArray) {
				inputDataLine.add(Double.valueOf(input));
			}
			for (Double input : inputDataLine) {
				System.out.print(input);
				System.out.print(" ");
			}
			inputData.add(inputDataLine);
			System.out.println(" ");
		}
	}

	public Double getTotalInputs() {
		return totalInputs;
	}

	public Double getTotalClass() {
		return totalClass;
	}
	
	public ArrayList<ArrayList<Double>> getData() {
		return inputData;
	}
}
