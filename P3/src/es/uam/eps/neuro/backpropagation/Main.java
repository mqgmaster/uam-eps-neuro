package es.uam.eps.neuro.backpropagation;

import es.uam.eps.neuro.domain.InputData;
import es.uam.eps.neuro.service.FileService;

public class Main {

	public static void main(String[] args) {
		part2();
		
		//part3();
		
		//part4();
		
		//part5();
		
		//part6();
	}
	
	private static void part2() {
		InputData data;
		Backpropagation neural;
		
		data = new InputData(FileService.read("patrones3.txt"));

		neural = new Backpropagation(data, 1.0, 0.2, 2);
		neural.startTraining();
		neural.startTest();
		FileService.save("results/data.txt", neural.getOutputData().getFileLines());
		FileService.save("results/data_ecm.txt", neural.getOutputECM().getFileLines());

	}
}
