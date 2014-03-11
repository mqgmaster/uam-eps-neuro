package es.uam.eps.neuro.backpropagation;

import java.util.ArrayList;

import es.uam.eps.neuro.domain.InputData;
import es.uam.eps.neuro.domain.InputRow;
import es.uam.eps.neuro.domain.OutputData;

public class Backpropagation {
	//definiciones estaticas respeto a la respuesta de la neurona
	public static final Double THRESHOLD = 0.2; 	//umbral
	//public static final Double LEARNING_RATE = 0.3; //constante de entrenamiento
	public static final Integer MAX_ROUNDS = 1000;
	
	//definiciones respeto al fichero de entrada
	public static final Double CLASS_ONE = 1.0;
	public static final String CLASS_ONE_STRING = "0 1"; //debe ser exactamente la misma del fichero de entrada
	public static final Double CLASS_TWO = -1.0;
	public static final String CLASS_TWO_STRING = "1 0"; //debe ser exactamente la misma del fichero de entrada
	public static final Double UNDEFINED = 0.0;
	public static final String UNDEFINED_STRING = "0 0";
	
	//variables principales del algoritmo
	private ArrayList<Double> wbias; 						//sesgo inicial
	private ArrayList<Double> vbias; 						//sesgo inicial
	private ArrayList<ArrayList<Double>> wWeights = new ArrayList<>();
	private ArrayList<ArrayList<Double>> vWeights = new ArrayList<>();
	
	private InputData trainingData;
	private InputData testData;
	private OutputData outputData = new OutputData();
	private boolean hasUpdatedWeights = false;
	private double learningRate;
		
	private int maxTrainingRounds = MAX_ROUNDS; //maximo de rondas de entrenamiento
	private int trainingRounds = 0;
	private int trainingErrors = 0;
	private int testErrors = 0;
	
	public Backpropagation(InputData data, Double trainingDataPrecentage, double learningRate, int numNeuronOcultLayer){
		setData(data, trainingDataPrecentage);
		this.learningRate = learningRate;
		initializeWeights(numNeuronOcultLayer, vbias);
		for(int i=0; i<numNeuronOcultLayer; i++){
			vWeights.add(new ArrayList<Double>());
			initializeWeights(data.getTotalInputs(), vWeights.get(i));
		}
		initializeWeights(data.getTotalTargets(), wbias);
		for(int i=0; i<data.getTotalTargets(); i++){
			wbias.add(Math.random()-0.5);
			wWeights.add(new ArrayList<Double>());
			initializeWeights(numNeuronOcultLayer, wWeights.get(i));
		}
	}
	
	public void setData(InputData data, Double trainingDataPercentage) {
		ArrayList<InputData> allData = data.getData(trainingDataPercentage);
		trainingData = allData.get(0);
		testData = allData.get(1);
	}
	
	private void initializeWeights(int totalInputs, ArrayList<Double> weights) {
		for (int i = 0; i < totalInputs; i++) {
			weights.add(Math.random()-0.5);
		}
	}
	
	public void startTraining() {
		System.out.println("Starting Training");
		InputRow inputRow;
		
		for (int i = 0; i < trainingData.getRows().size(); i++) {
			ArrayList<Double> z_inj = new ArrayList<>();
			ArrayList<Double> zj = new ArrayList<>();
			ArrayList<Double> y_ink = new ArrayList<>();
			ArrayList<Double> yk = new ArrayList<>();
			
			inputRow = trainingData.getRows().get(i);
			//para cada linha
//			for (Double input : inputRow.getAll()) {
//				System.out.print(input + "\t");
//			}
			
			/** 4) RESPUESTA DE LA CAPA OCULTA **/
			//calcular resposta parcial da neurona oculta
			for(int j=0; j<vWeights.size(); j++){
				z_inj.add(calculatePartialResponse(inputRow.getAll(), vWeights.get(j), vbias.get(j)));
			}
			
			//calcular resposta resposta completa oculta
			for(Double z_in : z_inj){
				zj.add(bipolarSigmoid(z_in));
			}
			
			/** 5) RESPUESTA DE LA SALIDA **/
			//calcular resposta parcial da neurona
			for(int k=0; k<vWeights.size(); k++){
				y_ink.add(calculatePartialResponse(zj, wWeights.get(k), wbias.get(k)));
			}
			
			//calcular resposta resposta completa
			for(Double y : y_ink){
				yk.add(bipolarSigmoid(y));
			}
			
			/** 6) RETROPORGRAMACION **/			
			//se target diferente de output
			
			switch (inputRow.getTargetRepresentation()) {
			case CLASS_ONE_STRING:
//				System.out.print(CLASS_ONE + "\t");
				if (yk.get(0) != CLASS_ONE) {
					//update pesos
					trainingErrors++;
					updateWeights(inputRow, CLASS_ONE);
				} //else printWeights();
				break;
			case CLASS_TWO_STRING:
//				System.out.print(CLASS_TWO + "\t");
				if (yk.get(0) != CLASS_TWO) {
					//update pesos
					trainingErrors++;
					updateWeights(inputRow, CLASS_TWO);
				} //else printWeights();
				break;
			default:
				System.out.print("\nFichero no contiene datos para aprendizaje.\nStoping training...");
				return;
			}
			
			if (i == trainingData.getRows().size()-1 && hasUpdatedWeights) {
				if (trainingRounds < maxTrainingRounds) {
					trainingRounds++;
					i = -1;
					hasUpdatedWeights = false;
				}
			}
		}
		
		System.out.println("Errores en entrenamiento: " + trainingErrors + ". En " + (trainingRounds+1) + " iteraciones");
	}

	public double startTest() {
		System.out.println("\nStarting Test");
		InputRow inputRow;
		Double partialResponse;
		Double neuronOutput;
		
		for (int i = 0; i < testData.getRows().size(); i++) {
			
			inputRow = testData.getRows().get(i);
			//para cada linha
			for (Double input : inputRow.getAll()) {
				System.out.print(input + "\t");
			}
			
			//calcular resposta parcial da neurona
			partialResponse = calculatePartialResponse(inputRow.getAll(), vWeights.get(0), vbias.get(0));
			//System.out.print(partialResponse + "\t");
			
			//calcular resposta resposta completa
			neuronOutput = calculateResponse(partialResponse);
			
			if (neuronOutput == CLASS_ONE) {
				outputData.add(CLASS_ONE_STRING);
				System.out.print("Classe: predicha " + CLASS_ONE_STRING + 
						"\treal: " + inputRow.getTargetRepresentation());
				if (!inputRow.getTargetRepresentation().equals(CLASS_ONE_STRING)) {
					testErrors++;
					System.out.print("\tError");
				}
			} else if (neuronOutput == CLASS_TWO) {
				outputData.add(CLASS_TWO_STRING);
				System.out.print("Classe: predicha " + CLASS_TWO_STRING + 
						"\treal: " + inputRow.getTargetRepresentation());
				if (!inputRow.getTargetRepresentation().equals(CLASS_TWO_STRING)) {
					testErrors++;
					System.out.print("\tError");
				}
			} else {
				outputData.add(UNDEFINED_STRING);
				testErrors++;
				System.out.print("Classe: predicha 0 0\treal: " + inputRow.getTargetRepresentation());
				System.out.print("\tError");
			}
			System.out.println("");
			outputData.newLine();
		}
		
		System.out.println("Errores en test: " + testErrors + ". " + ((double) testErrors/testData.getRows().size()) * 100 + "%");
		return ((double) testErrors/testData.getRows().size()) * 100;
	}
	
	protected void printWeights() {
		for (int i = 0; i < wWeights.size(); i++) {
			System.out.print(wWeights.get(i) + "\t");
			System.out.print("-\t");
		}
		System.out.print(wbias + "\t");
		System.out.println("-\t");
	}
	
	private void updateWeights(InputRow inputRow, Double target) {
		if (!hasUpdatedWeights) {
			hasUpdatedWeights = true;
		}
		for (int i = 0; i < wWeights.size(); i++) {
//			System.out.print(inputWeights.get(i) + "\t");
//			wWeights.set(i, wWeights.get(i) + (learningRate * target * inputRow.get(i)));
//			System.out.print(inputWeights.get(i) + "\t");
		}
//		System.out.print(bias + "\t");
//		wbias += learningRate * target;
//		System.out.println(bias + "\t");
	}
	
	private Double calculateResponse(Double partialResponse) {
		if (partialResponse > THRESHOLD) {
			return CLASS_ONE;
		} else if (partialResponse < -THRESHOLD) {
			return CLASS_TWO;
		}
		return UNDEFINED;
	}
	
	private Double bipolarSigmoid(Double partialResponse){
		double r = (2/(1+Math.exp(-partialResponse)))-1;
		return r;
	}
	
	private Double bipolarSigmoidPrima(Double bipolarSigmoid){
		double r = (1+bipolarSigmoid)*(1-bipolarSigmoid)*0.5;
		return r;
	}
	
	private Double calculatePartialResponse(ArrayList<Double> inputRow, ArrayList<Double> weights, double bias) {
		Double sum = 0.0;
		for (Double input : inputRow) {
			sum += input * weights.get(inputRow.indexOf(input));
		}
		return bias + sum;
	}
	
	public ArrayList<ArrayList<Double>> getWWeights() {
		return wWeights;
	}

	public OutputData getOutputData() {
		return outputData;
	}
}
