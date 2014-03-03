package es.uam.eps.neuro.perceptron;

import java.io.IOException;

import es.uam.eps.neuro.perceptron.domain.InputData;
import es.uam.eps.neuro.perceptron.service.FileService;

public class Main {
	
	public static void main(String[] args) throws IOException {
		InputData data;
		//para crear el archivo shuffled
//		data = new InputData(FileService.read("../problema_real2_no_etiquetados.txt"));
//		FileService.save("../shu_problema_real2_no_etiquetados.txt", data.shuffleFileLines());
		
		///para probar
		data = new InputData(FileService.read("../shu_problema_real2.txt"));
		Perceptron neuron = new Perceptron(data, 2.0/3.0, 1);
		neuron.startTraining();
		
		//para cambiar los datos de la neurona sin cambiar pesos
//		InputData newdata = new InputData(FileService.read("../shu_problema_real2_no_etiquetados.txt"));
//		neuron.setData(newdata, 1.0);
		neuron.startTest();
		
		//para grabar en fichero la salida de la neurona
		//FileService.save("../res_shu_problema_real2_ada.txt", neuron.getOutputData().getFileLines());
		
				
		/* APARTADO 3 
		
		data = new InputData(FileService.read("../shuffled_pr2.txt"));
		Perceptron neuron;
		//Adaline neuron;
		int i=0;
		double numE=Math.pow(2, i);
		ArrayList<String> results = new ArrayList<>();
		while(numE<=1000){
			neuron = new Perceptron(data, 2.0/3.0, (int)numE);
			//neuron = new Adaline(data, 2.0/3.0, (int)numE);
			neuron.startTraining();
			results.add(numE+"\t"+Double.toString(neuron.startTest(null)));
			i++;
			numE = Math.pow(2, i);
		}
		FileService.save("../p_results02_03_2.txt", results);
		
		
		/* APARTADO 4 */
		//data = new InputData(FileService.read("../nand2.txt"));
		/*Perceptron neuronP = new Perceptron(data, 1.0, 1000);
		neuronP.startTraining();
		neuronP.startTest(null);
		*/
		/*Adaline neuronA = new Adaline(data, 1.0, 1000);
		neuronA.startTraining();
		neuronA.startTest(null);
		*/
		
		/* APARTADO 5 */
		//Mezclamos los datos de entrenamiento
		/*data = new InputData(FileService.read("../problema_real2_2.txt"));
		FileService.save("../shuffled_pr2_2.txt", data.shuffleFileLines());
		*/
		/*data = new InputData(FileService.read("../shuffled_pr2_2.txt"));
		
		Perceptron per;
		Adaline ada;
		ArrayList<String> resultsPer = new ArrayList<>();
		ArrayList<String> resultsAda = new ArrayList<>();
		
		try {
			File fileP = new File("../predicciones_perceptron_2.txt");
			File fileA = new File("../predicciones_adaline_2.txt");
 
			if (!fileP.exists()) {
				fileP.createNewFile();
			}
			if (!fileA.exists()) {
				fileA.createNewFile();
			}
 
			BufferedWriter bwP = new BufferedWriter(new FileWriter(fileP.getAbsoluteFile()));
			BufferedWriter bwA = new BufferedWriter(new FileWriter(fileA.getAbsoluteFile()));
			
				per = new Perceptron(data, 1.0, 100);
				per.startTraining();
				data2 = new InputData(FileService.read("../problema_real2_no_etiquetados_2.txt"));
				Perceptron per2 = new Perceptron(data2, 1.0, per.getInputWeights());
				resultsPer.add(8+"\t"+Double.toString(per2.startTest(bwP)));

				ada = new Adaline(data, 1.0, 100);
				ada.startTraining();
				Adaline ada2 = new Adaline(data2, 1.0, ada.getInputWeights(), ada.getBias());
				resultsAda.add(10+"\t"+Double.toString(ada2.startTest(bwA)));
				
			
			bwP.close();
			bwA.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		}

		FileService.save("../p_results02_01.txt", resultsPer);
		FileService.save("../a_results00_01.txt", resultsAda);
		*/
	}

}
