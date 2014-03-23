package es.uam.eps.neuro.backpropagation;

import es.uam.eps.neuro.domain.InputData;
import es.uam.eps.neuro.service.FileService;

public class Main {

	public static void main(String[] args) {
		InputData data;
		//para crear el archivo shuffled
		//data = new InputData(FileService.read("../problema_real1.txt"));
		//FileService.save("../shu_problema_real1.txt", data.shuffleFileLines());
				
		//para probar
		data = new InputData(FileService.read("data/or.txt"));
		Backpropagation neural = new Backpropagation(data, 1.0, 1, 5);
		neural.startTraining();
		neural.startTest();
		
		//para cambiar los datos de la neurona sin cambiar pesos
//		InputData newdata = new InputData(FileService.read("../shu_problema_real2_no_etiquetados.txt"));
//		neural.setData(newdata, 1.0);
//		neural.startTest();
		//para grabar en fichero la salida de la neurona
//		FileService.save("../res_shu_problema_real2_ada.txt", neural.getOutputData().getFileLines());
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
		
		neural = new Backpropagation(data, 1.0, 0.5, 4);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/nor0.5_4.txt", neural.getOutputData().getFileLines());
		
		//NAND
		data = new InputData(FileService.read("data/nand.txt"));
		
		neural = new Backpropagation(data, 1.0, 0.2, 2);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/nand0.2_2.txt", neural.getOutputData().getFileLines());
		
		neural = new Backpropagation(data, 1.0, 0.5, 4);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/nand0.5_4.txt", neural.getOutputData().getFileLines());
		
		//XOR
		data = new InputData(FileService.read("data/xor.txt"));
		
		neural = new Backpropagation(data, 1.0, 0.2, 2);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/xor0.2_2.txt", neural.getOutputData().getFileLines());
		
		neural = new Backpropagation(data, 1.0, 0.5, 4);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/xor0.5_4.txt", neural.getOutputData().getFileLines());
		
		//problema_real1
		data = new InputData(FileService.read("data/problema_real1.txt"));
		
		neural = new Backpropagation(data, 2.0/3.0, 0.2, 2);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/problema_real1_0.2_2.txt", neural.getOutputData().getFileLines());
		
		neural = new Backpropagation(data, 2.0/3.0, 0.5, 4);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/problema_real1_0.5_4.txt", neural.getOutputData().getFileLines());
		
		//problema_real2
		data = new InputData(FileService.read("data/problema_real2.txt"));
		
		neural = new Backpropagation(data, 2.0/3.0, 0.2, 2);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/problema_real2_0.2_2.txt", neural.getOutputData().getFileLines());
		
		neural = new Backpropagation(data, 2.0/3.0, 0.5, 4);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/problema_real2_0.5_4.txt", neural.getOutputData().getFileLines());
	}
	
	//Tarea 3: predicción en problemas con más de dos clases.
	private static void part3() {
		InputData data;
		Backpropagation neural;
		
		//problema_real_3clases
		data = new InputData(FileService.read("data/problema_real_3clases.txt"));
		
		neural = new Backpropagation(data, 2.0/3.0, 0.2, 2);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/problema_real_3clases_0.2_2.txt", neural.getOutputData().getFileLines());
		
		neural = new Backpropagation(data, 2.0/3.0, 0.5, 4);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/problema_real_3clases_0.5_4.txt", neural.getOutputData().getFileLines());
	}
	
	//Tarea 4: predicción en un problema complejo.
	private static void part4() {
		InputData data;
		Backpropagation neural;
		
		//problema_real4.txt
		data = new InputData(FileService.read("data/problema_real4.txt"));
		
		neural = new Backpropagation(data, 2.0/3.0, 0.2, 2);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/problema_real4_0.2_2.txt", neural.getOutputData().getFileLines());
		
		neural = new Backpropagation(data, 2.0/3.0, 0.5, 4);
		neural.startTraining();
		neural.startTest();
		FileService.save("output/problema_real4_0.5_4.txt", neural.getOutputData().getFileLines());
	}
	
	//Tarea 5: normalización de los datos.
	private static void part5() {
		
	}
	
	//Tarea 6: predicción de datos no etiquetados.
	private static void part6() {
		InputData data;
		Backpropagation neural;
		
		//problema_real2_no_etiquetados.txt
		data = new InputData(FileService.read("data/problema_real2_no_etiquetados.txt"));
		
		neural = new Backpropagation(data, 1.0, 0.5, 4);
		neural.startTest();
		FileService.save("output/predicciones_nnet_0.5_4.txt", neural.getOutputData().getFileLines());
	}
}
