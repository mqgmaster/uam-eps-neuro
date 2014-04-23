package es.uam.eps.neuro.backpropagation;

import java.util.ArrayList;

import es.uam.eps.neuro.autoencoder.Autoencoder;
import es.uam.eps.neuro.domain.InputData;
import es.uam.eps.neuro.service.FileService;

public class Main {

	public static void main(String[] args) {
		
		//Autoencoder.construye_bd_autoencoder(3, "patrones3.txt");
		//part1();

		//Autoencoder.adapta_fichero_serie("serie-ejemplo.txt", "serie.txt", 2);
		Autoencoder.adapta_fichero_serie("p3_serie1.txt", "serie5.txt", 5);
		part2();
	}
	
	private static void part1() {
		InputData data;
		Backpropagation neural;
		
		data = new InputData(FileService.read("patrones3.txt"), true);

		neural = new BackpropagationEncoded(data, 1.0, 0.3, 5);
		neural.startTraining();
		neural.startTest();
		FileService.save("results/data_5.txt", neural.getOutputData().getFileLines());
		FileService.save("results/data_ecm_5.txt", neural.getOutputECM().getFileLines());

	}
	
	private static void part2() {
		InputData data;
		Backpropagation neural;
		
		data = new InputData(FileService.read("serie5.txt"), false);

		neural = new Backpropagation(data, 0.5, 0.1, 6);
		neural.startTraining();
		neural.startTest();
		FileService.save("results/2data_0.5_5.txt", neural.getOutputData().getFileLines());
		FileService.save("results/2data_ecm_0.5_5.txt", neural.getOutputECM().getFileLines());
	}
}
