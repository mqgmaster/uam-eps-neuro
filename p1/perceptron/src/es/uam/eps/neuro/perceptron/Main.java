package es.uam.eps.neuro.perceptron;

import es.uam.eps.neuro.perceptron.domain.InputData;
import es.uam.eps.neuro.perceptron.service.FileService;

public class Main {
	
	public static void main(String[] args) {
		InputData data = new InputData(FileService.read("../test.txt"));
		Perceptron perceptron = new Perceptron(data);
		perceptron.startTraining();
		perceptron.startTest();
		
	}

}
