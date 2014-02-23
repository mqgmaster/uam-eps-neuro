package es.uam.eps.neuro.perceptron;

import es.uam.eps.neuro.perceptron.domain.InputData;
import es.uam.eps.neuro.perceptron.service.FileService;

public class Main {
	
	public static void main(String[] args) {
		InputData data = FileService.getData("../problema_real1.txt");
		Perceptron perceptron = new Perceptron(data);
		
	}

}
