package es.uam.eps.neuro.perceptron;

import java.util.ArrayList;

import es.uam.eps.neuro.perceptron.domain.InputData;

public class Perceptron {
	
	public static final Double THRESHOLD = 0.2; //umbral
	public static final Double LEARNING_RATE = 1.0; //constante de entrenamiento
	private Double bias = 0.0; //sesgo
	private ArrayList<Double> inputWeights = new ArrayList<>();
	public static final Double CLASS_ONE = 1.0;
	public static final Double CLASS_TWO = 2.0;
	public static final Double UNDEFINED = 0.0;
	public static final Integer TOTAL_CLASS = 2;
	
	public Perceptron(InputData data) {
		for (int i = 0; i < data.getTotalInputs(); i++) {
			inputWeights.add(0.0);
		}
	}
	
	public void train(InputData data) {
		
	}
}
