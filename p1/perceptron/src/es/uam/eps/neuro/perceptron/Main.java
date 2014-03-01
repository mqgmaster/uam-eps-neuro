package es.uam.eps.neuro.perceptron;

import es.uam.eps.neuro.perceptron.domain.InputData;
import es.uam.eps.neuro.perceptron.service.FileService;

public class Main {
	
	public static void main(String[] args) {
		InputData data = new InputData(FileService.read("../shuffledtest.txt"));
		//FileService.save("../shuffledtest.txt", data.shuffleFileLines());
		Perceptron perceptron = new Perceptron(data, 2.0/3.0, 1);
		perceptron.startTraining();
		perceptron.startTest();
	}

}
