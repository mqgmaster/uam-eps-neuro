package es.uam.eps.neuro.perceptron.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InputData {
	
	private Double totalInputs;
	private Double totalClass; 
	//Array de Array de entradas
	private List<InputRow> inputData = new ArrayList<>();
	
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
	
	public InputData(List<InputRow> inputData, Double totalInputs, Double totalClass) {
		this.inputData = inputData;
		this.totalClass = totalClass;
		this.totalInputs = totalInputs;
	}

	public Double getTotalInputs() {
		return totalInputs;
	}

	public Double getTotalClass() {
		return totalClass;
	}
	
	public List<InputRow> getRows() {
		return inputData;
	}
	
	public ArrayList<InputData> getData(Double firstPartitionPercentage) {
		int firstPartitionFinalIndex = (int) (firstPartitionPercentage * inputData.size());
		int secondPartitionStartIndex;
		if (firstPartitionPercentage == 1.0) {
			secondPartitionStartIndex = 0;
		} else {
			secondPartitionStartIndex = firstPartitionFinalIndex;
		}
		Collections.shuffle(inputData);
		ArrayList<InputData> dividedData = new ArrayList<>();
		InputData firstPart = new InputData(
				inputData.subList(0, firstPartitionFinalIndex),
				this.totalInputs, 
				this.totalClass);
		InputData secondPart = new InputData(
				inputData.subList(secondPartitionStartIndex, inputData.size()),
				this.totalInputs, 
				this.totalClass);
		dividedData.add(firstPart);
		dividedData.add(secondPart);
		return dividedData;
	}
}
