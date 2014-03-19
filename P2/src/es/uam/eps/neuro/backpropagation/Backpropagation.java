package es.uam.eps.neuro.backpropagation;

import java.util.ArrayList;
import java.util.Collections;

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
	private ArrayList<Double> wbias= new ArrayList<>();					//sesgo inicial
	private ArrayList<Double> vbias= new ArrayList<>();					//sesgo inicial
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
		
		ArrayList<Double> z_inj;
		ArrayList<Double> zj;
		ArrayList<Double> y_ink;
		ArrayList<Double> yk;
		
		for(int round=0; round<maxTrainingRounds; round++){
		for (int n = 0; n < trainingData.getRows().size(); n++) {
			z_inj = new ArrayList<>();
			zj = new ArrayList<>();
			y_ink = new ArrayList<>();
			yk = new ArrayList<>();
			
			
			inputRow = trainingData.getRows().get(n);
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
			for(int k=0; k<wWeights.size(); k++){
				y_ink.add(calculatePartialResponse(zj, wWeights.get(k), wbias.get(k)));
			}
			
			//calcular resposta resposta completa
			for(Double y : y_ink){
				yk.add(bipolarSigmoid(y));
			}
			
			/** 6) RETROPORGRAMACION DEL ERROR DE LA CAPA SALIDA **/
			ArrayList<Double> errk = new ArrayList<>();
			ArrayList<Double> err_inj = new ArrayList<>();
			ArrayList<Double> errj = new ArrayList<>();
			ArrayList<Double> varWjkBias = new ArrayList<>();
			ArrayList<Double> varVijBias = new ArrayList<>();
			ArrayList<ArrayList<Double>> varWjk = new ArrayList<>();
			ArrayList<ArrayList<Double>> varVij = new ArrayList<>();
			
			//COMPROBAR Tk!!!!!
			for(int k=0; k<yk.size(); k++){
				errk.add((inputRow.getBipolarTargetValue(k)-yk.get(k))*bipolarSigmoidPrima(yk.get(k)));
			}
			
			for(int j=0; j<zj.size(); j++){
				varWjk.add(new ArrayList<Double>());
				for(int k=0; k<errk.size(); k++){
					varWjk.get(j).add(learningRate*errk.get(k)* zj.get(j));
				}
			}
			for(int k=0; k<errk.size(); k++){
				varWjkBias.add(learningRate*errk.get(k));
			}
			
			/** 7) RETROPORGRAMACION DEL ERROR DE LA CAPA OCULTA **/
			for(int j=0; j<varWjk.size(); j++){
				for(int k=0; k<varWjk.get(j).size(); k++){
					err_inj.add(errk.get(k)*varWjk.get(j).get(k));
				}
				errj.add(err_inj.get(j)*bipolarSigmoidPrima(zj.get(j)));
			}
			
			for(int i=0; i<inputRow.getAll().size(); i++){
				varVij.add(new ArrayList<Double>());
				for(int j=0; j<errj.size(); j++){
					varVij.get(i).add(learningRate*errj.get(j)* inputRow.get(i));
				}
			}
			for(int j=0; j<errj.size(); j++){
				varVijBias.add(learningRate*errj.get(j));
			}
			
			/** 8) ACTUALIZA PESOS Y SESGO**/
			for(int j=0; j<wWeights.size(); j++){
				for(int k=0; k<wWeights.get(j).size(); k++){
					wWeights.get(j).set(k, wWeights.get(j).get(k)+varWjk.get(j).get(k));
				}
			}
			for(int k=0; k<wWeights.get(0).size(); k++){
				wbias.set(k, wbias.get(k)+varWjkBias.get(k));
			}
			
			for(int i=0; i<vWeights.size(); i++){
				for(int j=0; j<vWeights.get(i).size(); j++){
					vWeights.get(i).set(j, vWeights.get(i).get(j)+varWjk.get(i).get(j));
				}
			}
			for(int k=0; k<vWeights.get(0).size(); k++){
				vbias.set(k, vbias.get(k)+varVijBias.get(k));
			}
			
			/*if (n == trainingData.getRows().size()-1 && hasUpdatedWeights) {
				if (trainingRounds < maxTrainingRounds) {
					trainingRounds++;
					n = -1;
					hasUpdatedWeights = false;
				}
			}*/
		}
		System.out.println("Errores en entrenamiento: " + trainingErrors + ". En " + (round+1) + " iteraciones");
		}
	}

	public double startTest() {
		System.out.println("\nStarting Test");
		InputRow inputRow;
		
		ArrayList<Double> z_inj = new ArrayList<>();
		ArrayList<Double> zj = new ArrayList<>();
		ArrayList<Double> y_ink = new ArrayList<>();
		ArrayList<Double> yk = new ArrayList<>();
		
		for (int i = 0; i < testData.getRows().size(); i++) {
			
			inputRow = testData.getRows().get(i);
			//para cada linha
			for (Double input : inputRow.getAll()) {
				System.out.print(input + "\t");
			}
			
			/** RESPUESTA DE LA CAPA OCULTA **/
			//calcular resposta parcial da neurona oculta
			for(int j=0; j<vWeights.size(); j++){
				z_inj.add(calculatePartialResponse(inputRow.getAll(), vWeights.get(j), vbias.get(j)));
			}
			
			//calcular resposta resposta completa oculta
			for(Double z_in : z_inj){
				zj.add(bipolarSigmoid(z_in));
			}
			
			/** RESPUESTA DE LA SALIDA **/
			//calcular resposta parcial da neurona
			for(int k=0; k<wWeights.size(); k++){
				y_ink.add(calculatePartialResponse(zj, wWeights.get(k), wbias.get(k)));
			}
			
			//calcular resposta resposta completa
			for(Double y : y_ink){
				yk.add(bipolarSigmoid(y));
			}
			
			/** CLASIFICA SEGUN EL MAXIMO YK **/
			int outputClass = yk.indexOf(Collections.max(yk));
			outputData.add(outputClass);
			System.out.print("Classe: predicha " + outputClass + "\treal: " + inputRow.getTargetClass());
			if(outputClass!=inputRow.getTargetClass()){
				testErrors++;
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
		for (int n=0; n<inputRow.size(); n++) {
			sum += inputRow.get(n) * weights.get(n);
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
