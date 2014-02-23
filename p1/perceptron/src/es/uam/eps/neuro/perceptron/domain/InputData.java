package es.uam.eps.neuro.perceptron.domain;

import java.util.ArrayList;

public class InputData {
	
	private Double totalInputs;
	private Double totalClass; 
	//Array de Array de entradas
	private ArrayList<InputRow> inputData = new ArrayList<>();
	
	public InputData(ArrayList<String> fileLines) {
		String params = fileLines.remove(0);
		String[] paramsVector = params.split(" ");
		totalInputs = Double.valueOf(paramsVector[0]);
		totalClass = Double.valueOf(paramsVector[1]);
		
		System.out.println("inputs " + totalInputs + " classes: " + totalClass);
		//Collections.shuffle(list);
		for (String inputs : fileLines) {
			InputRow inputRow = new InputRow();
			String[] inputsArray = inputs.split(" ");
			int i = 0;
			for (String input : inputsArray) {
				if (i < totalInputs) {
					inputRow.add(Double.valueOf(input));
					i++;
				} else {
					inputRow.addToTargetRepresentation(input);
				}
			}
			inputData.add(inputRow);
			System.out.println(" ");
		}
	}

	public Double getTotalInputs() {
		return totalInputs;
	}

	public Double getTotalClass() {
		return totalClass;
	}
	
	public ArrayList<InputRow> getData() {
		return inputData;
	}
}
