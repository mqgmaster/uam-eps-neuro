package es.uam.eps.neuro.backpropagation;

import java.util.ArrayList;

import es.uam.eps.neuro.autoencoder.Autoencoder;
import es.uam.eps.neuro.domain.InputData;
import es.uam.eps.neuro.service.FileService;

public class Main {

	public static void main(String[] args) {
		
		part1();

		//Autoencoder.adapta_fichero_serie("serie-ejemplo.txt", "serie.txt", 2);
		part2();
	}
	
	private static void part1() {
		InputData data;
		Backpropagation neural;
		
		Autoencoder.construye_bd_autoencoder(3, "patrones3.txt");
		data = new InputData(FileService.read("patrones3.txt"), true);

		neural = new BackpropagationEncoded(data, 1.0, 0.3, 4);
		neural.startTraining();
		neural.startTest();
		FileService.save("results/data_4.txt", neural.getOutputData().getFileLines());
		FileService.save("results/data_ecm_4.txt", neural.getOutputECM().getFileLines());
		
		neural = new BackpropagationEncoded(data, 1.0, 0.3, 5);
		neural.startTraining();
		neural.startTest();
		FileService.save("results/data_5.txt", neural.getOutputData().getFileLines());
		FileService.save("results/data_ecm_5.txt", neural.getOutputECM().getFileLines());
	}
		
	private static void part2() {
		InputData data;

		Autoencoder.adapta_fichero_serie("p3_serie1.txt", "serie1_np1.txt", 1);
		Autoencoder.adapta_fichero_serie("p3_serie1.txt", "serie1_np2.txt", 2);
		Autoencoder.adapta_fichero_serie("p3_serie1.txt", "serie1_np5.txt", 5);
		
		Autoencoder.adapta_fichero_serie("p3_serie2.txt", "serie2_np1.txt", 1);
		Autoencoder.adapta_fichero_serie("p3_serie2.txt", "serie2_np2.txt", 2);
		Autoencoder.adapta_fichero_serie("p3_serie2.txt", "serie2_np5.txt", 5);

		data = new InputData(FileService.read("serie1_np1.txt"), false);
		test(data,1,1);
		data = new InputData(FileService.read("serie1_np2.txt"), false);
		test(data,1,2);
		data = new InputData(FileService.read("serie1_np5.txt"), false);
		test(data,1,5);
		
		data = new InputData(FileService.read("serie2_np1.txt"), false);
		test(data,2,1);
		data = new InputData(FileService.read("serie2_np2.txt"), false);
		test(data,2,2);
		data = new InputData(FileService.read("serie2_np5.txt"), false);
		test(data,2,5);
	}
	
	private static void test(InputData data, int serie, int np){
		Backpropagation neural;

		neural = new Backpropagation(data, 0.25, 0.01, 5);
		neural.startTraining();
		neural.startTest();
		FileService.save("results/p2_s"+serie+"_data_0.25_"+np+".txt", neural.getOutputData().getFileLines());
		FileService.save("results/p2_s"+serie+"_data_ecm_0.25_"+np+".txt", neural.getOutputECM().getFileLines());
		
		neural = new Backpropagation(data, 0.5, 0.01, 5);
		neural.startTraining();
		neural.startTest();
		FileService.save("results/p2_s"+serie+"_data_0.5_"+np+".txt", neural.getOutputData().getFileLines());
		FileService.save("results/p2_s"+serie+"_data_ecm_0.5_"+np+".txt", neural.getOutputECM().getFileLines());
	}
}
