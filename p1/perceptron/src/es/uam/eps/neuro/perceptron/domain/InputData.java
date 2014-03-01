package es.uam.eps.neuro.perceptron.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InputData {
	
	private int totalInputs;
	private int totalTargets; 
	private String params;
	private ArrayList<String> fileLines;
	//Array de Array de entradas
	private List<InputRow> inputData = new ArrayList<>();
	
	public InputData(ArrayList<String> fileLines) {
		this.fileLines = fileLines;
		params = fileLines.remove(0);
		String[] paramsVector = params.split(" ");
		totalInputs = Integer.valueOf(paramsVector[0]);
		totalTargets = Integer.valueOf(paramsVector[1]);
		
		System.out.println("inputs " + totalInputs + " classes: " + totalTargets);
	}
	
	public InputData(List<InputRow> inputData, Integer totalInputs, Integer totalTargets) {
		this.inputData = inputData;
		this.totalTargets = totalTargets;
		this.totalInputs = totalInputs;
	}

	public int getTotalInputs() {
		return totalInputs;
	}

	public int getTotalTargets() {
		return totalTargets;
	}
	
	public List<InputRow> getRows() {
		return inputData;
	}
	
	public ArrayList<String> shuffleFileLines() {
		try {
			Collections.shuffle(fileLines);
			//first line params
			fileLines.add(0, params);
		} catch (Exception e) {
			System.out.println("Error in raw data (original file lines)");
		}
		return fileLines;
	}
	
	private void prepareRows() {
		for (String inputs : fileLines) {
			InputRow inputRow = new InputRow();
			//remove duplicate spaces. Eso es un overhead. El archivo de entrada
			//deberia ser standard.
			String[] inputsArray = inputs.replaceAll("\\s+", " ").split(" ");
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
		}
	}
	
	public ArrayList<InputData> getData(Double firstPartitionPercentage) {
		prepareRows();
		int firstPartitionFinalIndex = (int) (firstPartitionPercentage * inputData.size());
		int secondPartitionStartIndex;
		if (firstPartitionPercentage == 1.0) {
			secondPartitionStartIndex = 0;
		} else {
			secondPartitionStartIndex = firstPartitionFinalIndex;
		}
		
		ArrayList<InputData> dividedData = new ArrayList<>();
		InputData firstPart = new InputData(
				inputData.subList(0, firstPartitionFinalIndex),
				this.totalInputs, 
				this.totalTargets);
		InputData secondPart = new InputData(
				inputData.subList(secondPartitionStartIndex, inputData.size()),
				this.totalInputs, 
				this.totalTargets);
		dividedData.add(firstPart);
		dividedData.add(secondPart);
		return dividedData;
	}
}
