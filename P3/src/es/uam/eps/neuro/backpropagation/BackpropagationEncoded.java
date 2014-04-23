package es.uam.eps.neuro.backpropagation;

import java.util.ArrayList;

import es.uam.eps.neuro.domain.InputData;
import es.uam.eps.neuro.domain.InputRow;

public class BackpropagationEncoded extends Backpropagation {

	public BackpropagationEncoded(InputData data,
			Double trainingDataPrecentage, double learningRate,
			int numNeuronHiddenLayer, ArrayList<ArrayList<Double>> wWeights,
			ArrayList<ArrayList<Double>> vWeights) {
		super(data, trainingDataPrecentage, learningRate, numNeuronHiddenLayer,
				wWeights, vWeights);
	}

	public BackpropagationEncoded(InputData data,
			Double trainingDataPrecentage, double learningRate,
			int numNeuronHiddenLayer) {
		super(data, trainingDataPrecentage, learningRate, numNeuronHiddenLayer);
	}

	@Override
	public void classify(InputRow inputRow, ArrayList<Double> yk) {
		/** CLASIFICA SEGUN EL MAXIMO YK **/
		testPixelErrors = 0;
		ArrayList<Double> output = new ArrayList<>();
		for(int i=0; i< M ; i++){
			if(yk.get(i)<0){
				output.add(-0.9);
				outputData.add(-0.9);
			}else{
				output.add(0.9);
				outputData.add(0.9);
			}
			
			if(!output.get(i).equals(inputRow.getTargetValue(i))){
				testPixelErrors++;
			}
			System.out.println("Clase: predicha " + output.get(i) + "\treal: "
					+ inputRow.getTargetValue(i));
		}
		testErrors += testPixelErrors;
		System.out.println("Errores de pixel: " + testPixelErrors + ". "
				+ ((double) testPixelErrors / M)
				* 100 + "%");
		outputData.newLine();
	}
}
