package es.uam.eps.neuro.backpropagation;

import es.uam.eps.neuro.autoencoder.Autoencoder;
import es.uam.eps.neuro.domain.InputData;
import es.uam.eps.neuro.service.FileService;

public class Main {

	public static void main(String[] args) {
		
		Autoencoder.construye_bd_autoencoder(3, "patrones3.txt");
		part1();

		//Autoencoder.adapta_fichero_serie("serie-ejemplo.txt", "serie.txt", 1);
		//Autoencoder.adapta_fichero_serie("p3serie1.txt", "serie.txt", 1);
		//part2();
	}
	
	private static void part1() {
		InputData data;
		Backpropagation neural;
		
		data = new InputData(FileService.read("patrones3.txt"), true);

		neural = new Backpropagation(data, 1.0, 0.3, 5);
		neural.startTraining();
		neural.startTest();
		FileService.save("results/data_5.txt", neural.getOutputData().getFileLines());
		FileService.save("results/data_ecm_5.txt", neural.getOutputECM().getFileLines());

	}
	
	private static void part2() {
		InputData data;
		Backpropagation neural;
		
		data = new InputData(FileService.read("serie.txt"), false);

		neural = new Backpropagation(data, 0.25, 0.2, 4);
		neural.startTraining();
		neural.startTest();
		FileService.save("results/data.txt", neural.getOutputData().getFileLines());
		FileService.save("results/data_ecm.txt", neural.getOutputECM().getFileLines());

	}
}
