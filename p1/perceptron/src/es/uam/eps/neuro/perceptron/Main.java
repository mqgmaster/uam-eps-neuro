package es.uam.eps.neuro.perceptron;

import es.uam.eps.neuro.perceptron.domain.InputData;
import es.uam.eps.neuro.perceptron.service.FileService;

public class Main {
	
	public static void main(String[] args) {
		////para crear el archivo shuffled
		//InputData data = new InputData(FileService.read("../problema_real1.txt"));
		//FileService.save("../shuffled.txt", data.shuffleFileLines());
		
		/////para probar
		//InputData data = new InputData(FileService.read("../shuffled.txt"));
		//Adaline neuron = new Adaline(data, 2.0/3.0, 100);
		//neuron.startTraining();
		//neuron.startTest();
	}

}
