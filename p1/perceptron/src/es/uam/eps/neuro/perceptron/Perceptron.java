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
	public static final String CLASS_ONE_STRING = "01";
	public static final Double CLASS_TWO = -1.0;
	public static final String CLASS_TWO_STRING = "10";
	public static final Double UNDEFINED = 0.0;
	private InputData data;
	private int inputRowsSize;
	private boolean hasUpdatedWeights = false;
	public static final int MAX_ROUNDS = 5; //maximo de rondas de entrenamiento
	private int totalRounds = 0;
	
	public Perceptron(InputData data) {
		this.data = data;
		for (int i = 0; i < data.getTotalInputs(); i++) {
			inputWeights.add(0.0);
		}
		this.inputRowsSize = data.getData().size();
	}
	
	public void startTraining() {
		System.out.println("x1\tx2\ty_in\ty\tt\tw1\tnewW1\tw2\tnewW2\tb\tnewB");
		
		for (int i = 0; i < inputRowsSize; i++) {
			
			InputRow inputRow = data.getData().get(i);
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
					updateWeights(inputRow, CLASS_ONE);
				} else printWeights();
				break;
			case CLASS_TWO_STRING:
				System.out.print(CLASS_TWO + "\t");
				if (neuronOutput != CLASS_TWO) {
					//update pesos
					updateWeights(inputRow, CLASS_TWO);
				} else printWeights();
				break;
			default:
				System.out.print("Algo esta mal");
				break;
			}
			
			if (i == inputRowsSize-1 && hasUpdatedWeights) {
				if (totalRounds <= MAX_ROUNDS) {
					totalRounds++;
					i = -1;
					hasUpdatedWeights = false;
				}
			}
		}
	}
	
	public void startTest() {
		System.out.println("\nStart Test\nx1\tx2");
		
		for (int i = 0; i < inputRowsSize; i++) {
			
			InputRow inputRow = data.getData().get(i);
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
				System.out.println("Classe 1\t");
			} else if (neuronOutput == CLASS_TWO) {
				System.out.println("Classe 2\t");
			} else {
				System.out.println("Undefined\t");
			}
		}
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
