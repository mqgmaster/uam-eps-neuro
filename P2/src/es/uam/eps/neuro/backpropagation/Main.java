package es.uam.eps.neuro.backpropagation;

import es.uam.eps.neuro.domain.InputData;
import es.uam.eps.neuro.service.FileService;

public class Main {

	public static void main(String[] args) {
		//part2();
		
		//part3();
		
		//part4();
		
		//part5();
		
		part6();
	}
	
	//Tarea 2: chequeo del funcionamiento de la red con los ficheros de 
	//la práctica anterior
	private static void part2() {
		InputData data;
		Backpropagation neural;
		
		//NOR
		data = new InputData(FileService.read("data/nor.txt"));
		
		neural = new Backpropagation(data, 1.0, 0.2, 2);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/nor0.2_2.txt", neural.getOutputData().getFileLines());
		FileService.save("output/ECM/EMC_nor0.2_2.txt", neural.getOutputECM().getFileLines());

		neural = new Backpropagation(data, 1.0, 0.2, 4);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/nor0.2_4.txt", neural.getOutputData().getFileLines());
		FileService.save("output/ECM/EMC_nor0.2_4.txt", neural.getOutputECM().getFileLines());

		
		neural = new Backpropagation(data, 1.0, 0.1, 4);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/nor0.1_4.txt", neural.getOutputData().getFileLines());
		FileService.save("output/ECM/EMC_nor0.1_4.txt", neural.getOutputECM().getFileLines());
		
		//NAND
		data = new InputData(FileService.read("data/nand.txt"));
		
		neural = new Backpropagation(data, 1.0, 0.2, 2);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/nand0.2_2.txt", neural.getOutputData().getFileLines());
		FileService.save("output/ECM/EMC_nand0.2_2.txt", neural.getOutputECM().getFileLines());

		neural = new Backpropagation(data, 1.0, 0.2, 4);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/nand0.2_4.txt", neural.getOutputData().getFileLines());
		FileService.save("output/ECM/EMC_nand0.2_4.txt", neural.getOutputECM().getFileLines());

		neural = new Backpropagation(data, 1.0, 0.1, 4);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/nand0.1_4.txt", neural.getOutputData().getFileLines());
		FileService.save("output/ECM/EMC_nand0.1_4.txt", neural.getOutputECM().getFileLines());
		
		//XOR
		data = new InputData(FileService.read("data/xor.txt"));
		
		neural = new Backpropagation(data, 1.0, 0.2, 2);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/xor0.2_2.txt", neural.getOutputData().getFileLines());
		FileService.save("output/ECM/EMC_xor0.2_2.txt", neural.getOutputECM().getFileLines());
		
		neural = new Backpropagation(data, 1.0, 0.1, 4);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/xor0.1_4.txt", neural.getOutputData().getFileLines());
		FileService.save("output/ECM/EMC_xor0.1_4.txt", neural.getOutputECM().getFileLines());

		neural = new Backpropagation(data, 1.0, 0.1, 20);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/xor0.1_20.txt", neural.getOutputData().getFileLines());
		FileService.save("output/ECM/EMC_xor0.1_20.txt", neural.getOutputECM().getFileLines());
		
		//problema_real1
//		data = new InputData(FileService.read("data/problema_real1.txt"));
//		FileService.save("shuffled/problema_real1.txt", data.shuffleFileLines());
		data = new InputData(FileService.read("shuffled/problema_real1.txt"));
		
		neural = new Backpropagation(data, 2.0/3.0, 0.2, 2);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/problema_real1_0.2_2.txt", neural.getOutputData().getFileLines());
		FileService.save("output/ECM/EMC_problema_real1_0.2_2.txt", neural.getOutputECM().getFileLines());
		
		neural = new Backpropagation(data, 2.0/3.0, 0.1, 4);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/problema_real1_0.1_4.txt", neural.getOutputData().getFileLines());
		FileService.save("output/ECM/EMC_problema_real1_0.1_4.txt", neural.getOutputECM().getFileLines());
		
		//problema_real2
//		data = new InputData(FileService.read("data/problema_real2.txt"));
//		FileService.save("shuffled/problema_real2.txt", data.shuffleFileLines());
		data = new InputData(FileService.read("shuffled/problema_real2.txt"));
		
		neural = new Backpropagation(data, 2.0/3.0, 0.2, 2);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/problema_real2_0.2_2.txt", neural.getOutputData().getFileLines());
		FileService.save("output/ECM/EMC_problema_real2_0.2_2.txt", neural.getOutputECM().getFileLines());
		
		neural = new Backpropagation(data, 2.0/3.0, 0.1, 4);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/problema_real2_0.1_4.txt", neural.getOutputData().getFileLines());
		FileService.save("output/ECM/EMC_problema_real2_0.1_4.txt", neural.getOutputECM().getFileLines());

	}
	
	//Tarea 3: predicción en problemas con más de dos clases.
	private static void part3() {
		InputData data;
		Backpropagation neural;
		
		//problema_real_3clases
		//data = new InputData(FileService.read("data/problema-real-3clases.txt"));
		
		//FileService.save("shuffled/problema_real_3clases.txt", data.shuffleFileLines());
		data = new InputData(FileService.read("shuffled/problema_real_3clases.txt"));
		
		neural = new Backpropagation(data, 2.0/3.0, 0.2, 2);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/problema_real_3clases_0.2_2.txt", neural.getOutputData().getFileLines());
		FileService.save("output/ECM/problema_real_3clases_0.2_2.txt", neural.getOutputECM().getFileLines());
		
		neural = new Backpropagation(data, 2.0/3.0, 0.1, 4);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/problema_real_3clases_0.1_4.txt", neural.getOutputData().getFileLines());
		FileService.save("output/ECM/problema_real_3clases_0.1_4.txt", neural.getOutputECM().getFileLines());

	}
	
	//Tarea 4: predicción en un problema complejo.
	private static void part4() {
		InputData data;
		Backpropagation neural;
		
		data = new InputData(FileService.read("shuffled/problema_real_4.txt"));
		
		neural = new Backpropagation(data, 2.0/3.0, 0.2, 2);
		neural.getTrainingData().printStats();
		neural.startTraining();
		neural.startTest();
		FileService.save("part4/problema_real4_0.2_2.txt", neural.getOutputECM().getFileLines());
		
		neural = new Backpropagation(data, 2.0/3.0, 0.1, 4);
		neural.startTraining();
		neural.startTest();
		FileService.save("part4/problema_real4_0.1_4.txt", neural.getOutputECM().getFileLines());

	}
	
	//Tarea 5: normalización de los datos.
	private static void part5() {
		InputData data;
		Backpropagation neural;
		
		data = new InputData(FileService.read("shuffled/problema_real_4.txt"));
				
		neural = new Backpropagation(data, 2.0/3.0, 0.2, 2);
		neural.getTestData().normalizeBasedOn(neural.getTrainingData());
		neural.getTrainingData().normalize();
		neural.startTraining();
		neural.startTest();
		
		FileService.save("part5/problema_real4_0.2_2.txt", neural.getOutputECM().getFileLines());
		
		neural = new Backpropagation(data, 2.0/3.0, 0.1, 4);
		neural.getTestData().normalizeBasedOn(neural.getTrainingData());
		neural.getTrainingData().normalize();
		neural.startTraining();
		neural.startTest();
		FileService.save("part5/problema_real4_0.1_4.txt", neural.getOutputECM().getFileLines());

	}
	
	//Tarea 6: predicción de datos no etiquetados.
	private static void part6() {
		InputData data;
		Backpropagation neural;
		
		data = new InputData(FileService.read("data/problema_real2.txt"));
		
		neural = new Backpropagation(data, 1.0, 0.2, 2);
		neural.startTraining();
		//FileService.save("part6/problema_real2_0.2_2.txt", neural.getOutputECM().getFileLines());
		
		//problema_real2_no_etiquetados.txt
		data = new InputData(FileService.read("data/problema_real2_no_etiquetados.txt"));
		
		neural = new Backpropagation(data, 1.0, 0.2, 2, neural.getwWeights(), neural.getvWeights());
		neural.startTest();
		FileService.save("part6/predicciones_nnet.txt", neural.getOutputData().getFileLines());
	}
}
