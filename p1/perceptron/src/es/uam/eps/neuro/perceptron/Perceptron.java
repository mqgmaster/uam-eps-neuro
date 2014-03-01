package es.uam.eps.neuro.perceptron;

import java.util.ArrayList;

import es.uam.eps.neuro.perceptron.domain.InputData;
import es.uam.eps.neuro.perceptron.domain.InputRow;

public class Perceptron {
	
	public static final Double THRESHOLD = 0.2; //umbral
	public static final Double LEARNING_RATE = 1.0; //constante de entrenamiento
	private Double bias = 0.0; //sesgo
	private ArrayList<Double> inputWeights = new ArrayList<>();
	public static final Double CLASS_ONE = 1.0;
	public static final String CLASS_ONE_STRING = "01"; //debe ser exactamente la misma del fichero de entrada
	public static final Double CLASS_TWO = -1.0;
	public static final String CLASS_TWO_STRING = "10"; //debe ser exactamente la misma del fichero de entrada
	public static final Double UNDEFINED = 0.0;
	private InputData trainingData;
	private InputData testData;
	private boolean hasUpdatedWeights = false;
	private int maxTrainingRounds = 50; //maximo de rondas de entrenamiento
	private int trainingRounds = 0;
	private int trainingErrors = 0;
	private int testErrors = 0;
	
	public Perceptron(InputData data, Double trainingDataPercentage, int maxTrainingRounds) {
		ArrayList<InputData> allData = data.getData(trainingDataPercentage);
		trainingData = allData.get(0);
		testData = allData.get(1);
		for (int i = 0; i < data.getTotalInputs(); i++) {
			inputWeights.add(0.0);
		}
		this.maxTrainingRounds = maxTrainingRounds;
	}
	
	public void startTraining() {
		System.out.println("Starting Training");
		
		for (int i = 0; i < trainingData.getRows().size(); i++) {
			
			InputRow inputRow = trainingData.getRows().get(i);
			//para cada linha
			for (Double input : inputRow.getAll()) {
				System.out.print(input + "\t");
			}
			
			//calcular resposta parcial da neurona
			Double partialResponse = calculatePartialResponse(inputRow);
			System.out.print(partialResponse + "\t");
			
			//calcular resposta resposta completa
			Double neuronOutput = calculateResponse(partialResponse);
			System.out.print(neuronOutput + "\t");
			
			//se target diferente de output
			switch (inputRow.getTargetRepresentation()) {
			case CLASS_ONE_STRING:
				System.out.print(CLASS_ONE + "\t");
				if (neuronOutput != CLASS_ONE) {
					//update pesos
					trainingErrors++;
					updateWeights(inputRow, CLASS_ONE);
				} else printWeights();
				break;
			case CLASS_TWO_STRING:
				System.out.print(CLASS_TWO + "\t");
				if (neuronOutput != CLASS_TWO) {
					//update pesos
					trainingErrors++;
					updateWeights(inputRow, CLASS_TWO);
				} else printWeights();
				break;
			default:
				System.out.print("Algo esta mal");
				break;
			}
			
			if (i == trainingData.getRows().size()-1 && hasUpdatedWeights) {
				if (trainingRounds < maxTrainingRounds) {
					trainingRounds++;
					i = -1;
					hasUpdatedWeights = false;
				}
			}
		}
		
		System.out.println("Errores en entrenamiento: " + trainingErrors + ". En " + (trainingRounds+1) + " iteraciones");
	}
	
	private String getTargetLabelFromInputRow(InputRow inputRow) {
		switch (inputRow.getTargetRepresentation()) {
		case CLASS_ONE_STRING:
			return "1";
		case CLASS_TWO_STRING:
			return "2";
		default:
			System.out.print("Algo esta mal");
			break;
		}
		System.out.print("algo pasa con el fichero de entrada");
		return "Undefined";
	}
	
	public void startTest() {
		System.out.println("\nStarting Test");
		
		for (int i = 0; i < testData.getRows().size(); i++) {
			
			InputRow inputRow = testData.getRows().get(i);
			//para cada linha
			for (Double input : inputRow.getAll()) {
				System.out.print(input + "\t");
			}
			
			//calcular resposta parcial da neurona
			Double partialResponse = calculatePartialResponse(inputRow);
			//System.out.print(partialResponse + "\t");
			
			//calcular resposta resposta completa
			Double neuronOutput = calculateResponse(partialResponse);
			
			if (neuronOutput == CLASS_ONE) {
				System.out.print("Classe: predicha 1\treal: " + getTargetLabelFromInputRow(inputRow));
				if (!inputRow.getTargetRepresentation().equals(CLASS_ONE_STRING)) {
					testErrors++;
					System.out.print("\tError");
				}
			} else if (neuronOutput == CLASS_TWO) {
				System.out.print("Classe: predicha 2\treal: " + getTargetLabelFromInputRow(inputRow));
				if (!inputRow.getTargetRepresentation().equals(CLASS_TWO_STRING)) {
					testErrors++;
					System.out.print("\tError");
				}
			} else {
				testErrors++;
				System.out.print("Classe: predicha Undefined\treal: " + getTargetLabelFromInputRow(inputRow));
				System.out.print("\tError");
			}
			System.out.println("");
		}
		
		System.out.println("Errores en test: " + testErrors + ". " + ((double) testErrors/testData.getRows().size()) * 100 + "%");
	}
	
	private void printWeights() {
		for (int i = 0; i < inputWeights.size(); i++) {
			System.out.print(inputWeights.get(i) + "\t");
			System.out.print("-\t");
		}
		System.out.print(bias + "\t");
		System.out.println("-\t");
	}
	
	private void updateWeights(InputRow inputRow, Double target) {
		if (!hasUpdatedWeights) {
			hasUpdatedWeights = true;
		}
		for (int i = 0; i < inputWeights.size(); i++) {
			System.out.print(inputWeights.get(i) + "\t");
			inputWeights.set(i, inputWeights.get(i) + (LEARNING_RATE * target * inputRow.get(i)));
			System.out.print(inputWeights.get(i) + "\t");
		}
		System.out.print(bias + "\t");
		bias += LEARNING_RATE * target;
		System.out.println(bias + "\t");
	}
	
	private Double calculateResponse(Double partialResponse) {
		if (partialResponse > THRESHOLD) {
			return CLASS_ONE;
		} else if (partialResponse < -THRESHOLD) {
			return CLASS_TWO;
		}
		return UNDEFINED;
	}
	
	private Double calculatePartialResponse(InputRow inputRow) {
		Double sum = 0.0;
		for (Double input : inputRow.getAll()) {
			sum += input * inputWeights.get(inputRow.getAll().indexOf(input));
		}
		return bias + sum;
	}
}
