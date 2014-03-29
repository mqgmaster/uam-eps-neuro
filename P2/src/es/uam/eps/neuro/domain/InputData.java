package es.uam.eps.neuro.domain;

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
	private boolean shuffled = false;
	
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
		if (inputData.isEmpty()) {
			prepareRows();
		}
		return inputData;
	}
	
	public ArrayList<String> shuffleFileLines() {
		if (fileLines.isEmpty()) {
			System.out.println("filelines is empty");
			return null;
		}
		try {
			Collections.shuffle(fileLines);
			shuffled = true;
			//first line params
			fileLines.add(0, params);
		} catch (Exception e) {
			System.out.println("Error in raw data (original file lines)");
		}
		return fileLines;
	}
	
	private void prepareRows() {
		if (fileLines.isEmpty()) {
			System.out.println("filelines is empty");
			return;
		}
		if (shuffled) {
			fileLines.remove(0);
		}
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
					inputRow.addToTargetClass(input);
					inputRow.addToBipolarTargetClass(input);
				}
			}
			inputData.add(inputRow);
		}
	}
	
	public void normalize() {
		normalizeBasedOn(this);
	}
	
	public void normalizeBasedOn(InputData data) {
		if (inputData.isEmpty()) {
			prepareRows();
		}
		ArrayList<Double> allMeans = data.calculateMean();
		ArrayList<Double> allStandardDeviations = data.calculateStandardDeviation(allMeans);
		for(InputRow row : inputData) {
			for (int i = 0; i < totalInputs; i++) {
				row.getAll().set(i, (row.get(i) - allMeans.get(i)) / allStandardDeviations.get(i));
				//System.out.println(row.get(i));
			}
		}
	}
	
	public ArrayList<Double> calculateStandardDeviation(ArrayList<Double> allMeans) {
		ArrayList<Double> allStandardDeviations = new ArrayList<>(Collections.nCopies(totalInputs, 0.0));
		
		for(InputRow row : inputData) {
			for (int i = 0; i < totalInputs; i++) {
				//System.out.println(row.get(i) + " - " + allMeans.get(i) + " ²");
				allStandardDeviations.set(i, 
					allStandardDeviations.get(i) + Math.pow(row.get(i) - allMeans.get(i), 2));
			}
		}
		for (int i = 0; i < totalInputs; i++) {
			allStandardDeviations.set(i, 
				Math.sqrt(allStandardDeviations.get(i) / (inputData.size() - 1)));
			//System.out.println(allStandardDeviations.get(i));
		}
		
		return allStandardDeviations;
	}
	
	public ArrayList<Double> calculateMean() {
		ArrayList<Double> allMeans = new ArrayList<>(Collections.nCopies(totalInputs, 0.0));
		for(InputRow row : inputData) {
			for (int i = 0; i < totalInputs; i++) {
				allMeans.set(i, allMeans.get(i) + row.get(i));
			}
		}
		for (int i = 0; i < totalInputs; i++) {
			allMeans.set(i, allMeans.get(i) / inputData.size());
			//System.out.println(allMeans.get(i));
		}
		return allMeans;
	}
	
	public ArrayList<InputData> getData(Double firstPartitionPercentage) {
		if (inputData.isEmpty()) {
			prepareRows();
		}
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
	
	public void print() {
		for (InputRow row : inputData) {
			row.print();
			System.out.println("");
		}
	}
	
	public void printStats() {
		System.out.println("Stats of database");
		System.out.println("Means:");
		ArrayList<Double> allMeans = calculateMean();
		for (Double doub : allMeans) {
			System.out.print(doub + "\t");
		}
		System.out.println("");
		System.out.println("Standard deviations:");
		for (Double doub : calculateStandardDeviation(allMeans)) {
			System.out.print(doub + "\t");
		}
		System.out.println("");
	}
}

