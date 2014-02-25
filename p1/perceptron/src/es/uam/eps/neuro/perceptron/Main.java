package es.uam.eps.neuro.perceptron;

import es.uam.eps.neuro.perceptron.domain.InputData;
import es.uam.eps.neuro.perceptron.service.FileService;

public class Main {
	
	public static void main(String[] args) {
		InputData data = new InputData(FileService.read("../problema_real1.txt"));
		Perceptron perceptron = new Perceptron(data, 2.0/3.0, 50);
		perceptron.startTraining();
		perceptron.startTest();
	}

}
