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

}
