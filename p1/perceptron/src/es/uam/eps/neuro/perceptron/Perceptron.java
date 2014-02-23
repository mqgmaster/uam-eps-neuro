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
	
	public Perceptron(InputData data) {
		this.data = data;
		for (int i = 0; i < data.getTotalInputs(); i++) {
			inputWeights.add(0.0);
		}
	}
	
	public void startTraining() {
		//para cada linha
		InputRow inputRow = data.getData().get(0);
		
		//calcular resposta parcial da neurona
		Double partialResponse = calculatePartialResponse(inputRow);
		System.out.println(partialResponse);
		
		//calcular resposta resposta completa
		Double neuronOutput = calculateResponse(partialResponse);
		System.out.println(neuronOutput);
		
		//se target diferente de output
		switch (inputRow.getTargetRepresentation()) {
		case CLASS_ONE_STRING:
			if (neuronOutput != CLASS_ONE) {
				System.out.println("alterar pesos, deveria ser classe 1");
			}
			break;
		case CLASS_TWO_STRING:
			if (neuronOutput != CLASS_TWO) {
				System.out.println("alterar pesos, deveria ser classe 2");
			}
		default:
			break;
		}
		
		//calcular novos pesos e sesgo
		
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
