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
	//private ArrayList<Double> wbias= new ArrayList<>();					//sesgo inicial
	//private ArrayList<Double> vbias= new ArrayList<>();					//sesgo inicial
	private ArrayList<ArrayList<Double>> wWeights = new ArrayList<>();
	private ArrayList<ArrayList<Double>> vWeights = new ArrayList<>();
	
	/** NUMBER OF NEURONS **/
	private int R; //R = number of training samples
	private int N; //N = number of attributes + x0 bias
	private int P; //P = number of neurons in the hidden layer + z0 bias
	private int M; //M = number of target classes
	
	private InputData trainingData;
	private InputData testData;
	private OutputData outputData = new OutputData();
	private boolean hasUpdatedWeights = false;
	private double learningRate;
		
	private int maxTrainingRounds = MAX_ROUNDS; //maximo de rondas de entrenamiento
	private int trainingRounds = 0;
	private int trainingErrors = 0;
	private int testErrors = 0;
	
	public Backpropagation(InputData data, Double trainingDataPrecentage, double learningRate, int numNeuronHiddenLayer){
		setData(data, trainingDataPrecentage);
		this.learningRate = learningRate;
		
		R = trainingData.getRows().size();
		N = trainingData.getTotalInputs() + 1;
		P = numNeuronHiddenLayer + 1;
		M = trainingData.getTotalTargets();

		/** 1) INICIALIZACION DE PESOS Y SESGOS [-0.5, 0.5] **/
		initializeWeights(P-1, N, vWeights); //Matrix Nx(P-1) in ArrayList<ArrayList<Double>> (P-1)xN
		initializeWeights(M, P, wWeights); //Matrix PxM in ArrayList<ArrayList<Double>> MxP
		
	}
	
	public void setData(InputData data, Double trainingDataPercentage) {
		ArrayList<InputData> allData = data.getData(trainingDataPercentage);
		trainingData = allData.get(0);
		testData = allData.get(1);
	}
	
	private void initializeWeights(int rows, int columns, ArrayList<ArrayList<Double>> weights) {
		for (int i = 0; i < rows; i++) {
			ArrayList<Double> aux = new ArrayList<>();
			for(int j = 0; j < columns; j++){
				aux.add(Math.random()-0.5);
			}
			weights.add(aux);
		}
	}
	
	public void startTraining() {
		System.out.println("Starting Training");
		InputRow inputRow;
		ArrayList<Double> xi;
		ArrayList<Double> z_inj;
		ArrayList<Double> zj;
		ArrayList<Double> y_ink;
		ArrayList<Double> yk;
		
		/** 1) MIENTRAS NO SUPERE EL NUMERO DE EPOCAS SE DE LA CONDICION DE PARADA **/
		for(int round=0; round<maxTrainingRounds; round++){
			/** 2) PARA CADA CONJUNTO DE ENTRENAMIENTO **/
		for (int r = 0; r < R; r++) {
			xi = new ArrayList<>();
			z_inj = new ArrayList<>();
			zj = new ArrayList<>();
			y_ink = new ArrayList<>();
			yk = new ArrayList<>();
			
			/** 3) ESTABLECE LAS NEURONAS DE ENTRADA **/
			inputRow = trainingData.getRows().get(r);
			xi = inputRow.getAll();
			xi.add(0, 1.0); //bias
			
			/** 4) RESPUESTA DE LA CAPA OCULTA **/
			zj.add(1.0); //bias
			for(int j=0; j<P-1; j++){
				//z_inj = v0j + SUMi->N(xi*vij)
				double aux = vWeights.get(j).get(0); // v0j
				for(int i=1; i<N; i++){
					aux += xi.get(i-1) * vWeights.get(j).get(i); //+ (xi * vij)
				}
				z_inj.add(aux);
				zj.add(bipolarSigmoid(aux)); //zj = f(z_inj)
			}
			
			/** 5) RESPUESTA DE LA SALIDA **/
			for(int k=0; k<M ; k++){
				//y_ink = w0j + SUMj->P(zj*wjk)
				double aux = 0.0;
				for(int j=0; j<P; j++){
					aux += zj.get(j) * wWeights.get(k).get(j); //+ (zj * wjk)
				}
				y_ink.add(aux);
				yk.add(bipolarSigmoid(aux)); //yk = f(y_ink)
			}
			
			/** 6) RETROPORGRAMACION DEL ERROR DE LA CAPA SALIDA **/
			ArrayList<Double> errk = new ArrayList<>();
			ArrayList<Double> err_inj = new ArrayList<>();
			ArrayList<Double> errj = new ArrayList<>();
//			ArrayList<Double> varWjkBias = new ArrayList<>();
//			ArrayList<Double> varVijBias = new ArrayList<>();
			ArrayList<ArrayList<Double>> deltaWjk = new ArrayList<>();
			ArrayList<ArrayList<Double>> deltaVij = new ArrayList<>();
			
			//errk = (tk - yk)*f'(y_ink)
			for(int k=0; k<M; k++){
				errk.add((inputRow.getTargetValue(k)-yk.get(k))*bipolarSigmoidPrima(yk.get(k)));
			}

			//deltaWjk = learningRate * errk * zj
			for(int k=0; k<M; k++){
				deltaWjk.add(new ArrayList<Double>());
				double auxCte = learningRate*errk.get(k);
				deltaWjk.get(k).add(auxCte); //bias
				for(int j=1; j<P; j++){
					deltaWjk.get(k).add(auxCte*zj.get(j));
				}
			}

			/** 7) RETROPORGRAMACION DEL ERROR DE LA CAPA OCULTA **/
			//err_inj = SUMk->M(errk*wjk)
			for(int j=0; j<P; j++){
				double aux = 0.0;
				for(int k=0; k<M; k++){
					aux += errk.get(k)*deltaWjk.get(k).get(j);
				}
				err_inj.add(aux);
				errj.add(aux*bipolarSigmoidPrima(zj.get(j)));
			}
			
			/*
			
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
			*/
			/** 8) ACTUALIZA PESOS Y SESGO**/
			/*for(int j=0; j<wWeights.size(); j++){
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
			*/
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
		ArrayList<Double> xi;		
		ArrayList<Double> z_inj = new ArrayList<>();
		ArrayList<Double> zj = new ArrayList<>();
		ArrayList<Double> y_ink = new ArrayList<>();
		ArrayList<Double> yk = new ArrayList<>();
		
		for (int r = 0; r < testData.getRows().size(); r++) {
			xi = new ArrayList<>();
			z_inj = new ArrayList<>();
			zj = new ArrayList<>();
			y_ink = new ArrayList<>();
			yk = new ArrayList<>();
			
			/** 3) ESTABLECE LAS NEURONAS DE ENTRADA **/
			inputRow = trainingData.getRows().get(r);
			//para cada linha
			for (Double input : inputRow.getAll()) {
				System.out.print(input + "\t");
			}
			xi = inputRow.getAll();
			xi.add(0, 1.0); //bias
			
			/** 4) RESPUESTA DE LA CAPA OCULTA **/
			zj.add(1.0); //bias
			for(int j=0; j<P-1; j++){
				//z_inj = v0j + SUMi->N(xi*vij)
				double aux = vWeights.get(j).get(0); // v0j
				for(int i=1; i<N; i++){
					aux += xi.get(i-1) * vWeights.get(j).get(i); //+ (xi * vij)
				}
				z_inj.add(aux);
				zj.add(bipolarSigmoid(aux)); //zj = f(z_inj)
			}
			
			/** 5) RESPUESTA DE LA SALIDA **/
			for(int k=0; k<M ; k++){
				//y_ink = w0j + SUMj->P(zj*wjk)
				double aux = 0.0;
				for(int j=0; j<P; j++){
					aux += zj.get(j) * wWeights.get(k).get(j); //+ (zj * wjk)
				}
				y_ink.add(aux);
				yk.add(bipolarSigmoid(aux)); //yk = f(y_ink)
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
//		System.out.print(wbias + "\t");
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
