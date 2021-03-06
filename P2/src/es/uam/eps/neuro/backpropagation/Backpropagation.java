package es.uam.eps.neuro.backpropagation;

import java.util.ArrayList;
import java.util.Collections;

import es.uam.eps.neuro.domain.InputData;
import es.uam.eps.neuro.domain.InputRow;
import es.uam.eps.neuro.domain.OutputData;

public class Backpropagation {
	// definiciones estaticas respeto a la respuesta de la neurona
	private static final Integer MAX_ROUNDS = 1000;
	private static final Double ECM = 0.1;
	private static final Double ECM_DIFF = 0.000001;

	// variables principales del algoritmo
	private ArrayList<ArrayList<Double>> wWeights = new ArrayList<>();
	private ArrayList<ArrayList<Double>> vWeights = new ArrayList<>();

	/** NUMBER OF NEURONS **/
	private int R; // R = number of training samples
	private int N; // N = number of attributes + x0 bias
	private int P; // P = number of neurons in the hidden layer + z0 bias
	private int M; // M = number of target classes

	private double learningRate; // constante de entrenamiento
	private InputData trainingData;
	private InputData testData;
	private OutputData outputData = new OutputData();
	private OutputData outputECM = new OutputData();

	private int maxTrainingRounds = MAX_ROUNDS; // maximo de rondas de entrenamiento
	private int testErrors = 0;

	public Backpropagation(InputData data, Double trainingDataPrecentage,
			double learningRate, int numNeuronHiddenLayer) {
		setData(data, trainingDataPrecentage);
		this.learningRate = learningRate;

		R = trainingData.getRows().size();
		N = trainingData.getTotalInputs() + 1;
		P = numNeuronHiddenLayer + 1;
		M = trainingData.getTotalTargets();

		/** 1) INICIALIZACION DE PESOS Y SESGOS [-0.5, 0.5] **/
		initializeWeights(P - 1, N, vWeights); // Matrix Nx(P-1) in ArrayList<ArrayList<Double>> (P-1)xN
		initializeWeights(M, P, wWeights); // Matrix PxM in ArrayList<ArrayList<Double>> MxP

	}
	
	public Backpropagation(InputData data, Double trainingDataPrecentage,
			double learningRate, int numNeuronHiddenLayer,
			ArrayList<ArrayList<Double>> wWeights, ArrayList<ArrayList<Double>> vWeights) {
		setData(data, trainingDataPrecentage);
		this.learningRate = learningRate;

		R = trainingData.getRows().size();
		N = trainingData.getTotalInputs() + 1;
		P = numNeuronHiddenLayer + 1;
		M = trainingData.getTotalTargets();

		this.wWeights = wWeights;
		this.vWeights = vWeights;
	}

	public void setData(InputData data, Double trainingDataPercentage) {
		ArrayList<InputData> allData = data.getData(trainingDataPercentage);
		trainingData = allData.get(0);
		testData = allData.get(1);
	}

	private void initializeWeights(int rows, int columns,
			ArrayList<ArrayList<Double>> weights) {
		for (int i = 0; i < rows; i++) {
			ArrayList<Double> aux = new ArrayList<>();
			for (int j = 0; j < columns; j++) {
				aux.add(Math.random() - 0.5);
//				aux.add(0.5);
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
		
		double prevECM = 10.0;

		/**
		 * 1) MIENTRAS NO SUPERE EL NUMERO DE EPOCAS SE DE LA CONDICION DE PARADA
		 **/
		for (int round = 0; round < maxTrainingRounds; round++) {
			double auxECM = 0.0;
			/** 2) PARA CADA CONJUNTO DE ENTRENAMIENTO **/
			for (int r = 0; r < R; r++) {
				xi = new ArrayList<>();
				z_inj = new ArrayList<>();
				zj = new ArrayList<>();
				y_ink = new ArrayList<>();
				yk = new ArrayList<>();

				/** 3) ESTABLECE LAS NEURONAS DE ENTRADA **/
				inputRow = trainingData.getRows().get(r);
				xi = new ArrayList<Double>(inputRow.getAll());
				xi.add(0, 1.0); // bias

				/** 4) RESPUESTA DE LA CAPA OCULTA **/
				zj.add(1.0); // bias
				for (int j = 0; j < P - 1; j++) {
					// z_inj = v0j + SUMi->N(xi*vij)
					double aux = vWeights.get(j).get(0); // v0j
					for (int i = 1; i < N; i++) {
						aux += xi.get(i) * vWeights.get(j).get(i); // + (xi * vij)
					}
					z_inj.add(aux);
					zj.add(bipolarSigmoid(aux)); // zj = f(z_inj)
				}

				/** 5) RESPUESTA DE LA SALIDA **/
				for (int k = 0; k < M; k++) {
					// y_ink = w0j + SUMj->P(zj*wjk)
					double aux = 0.0;
					for (int j = 0; j < P; j++) {
						aux += zj.get(j) * wWeights.get(k).get(j); // + (zj * wjk)
					}
					y_ink.add(aux);
					yk.add(bipolarSigmoid(aux)); // yk = f(y_ink)

					auxECM += Math.pow(yk.get(k)-inputRow.getTargetValue(k),2);

				}

				/** 6) RETROPORGRAMACION DEL ERROR DE LA CAPA SALIDA **/
				ArrayList<Double> errk = new ArrayList<>();
				ArrayList<Double> err_inj = new ArrayList<>();
				ArrayList<Double> errj = new ArrayList<>();
				ArrayList<ArrayList<Double>> deltaWjk = new ArrayList<>();
				ArrayList<ArrayList<Double>> deltaVij = new ArrayList<>();

				// errk = (tk - yk)*f'(y_ink)
				for (int k = 0; k < M; k++) {
					errk.add((inputRow.getTargetValue(k) - yk.get(k))
							* bipolarSigmoidPrima(yk.get(k)));
				}

				// deltaWjk = learningRate * errk * zj
				for (int k = 0; k < M; k++) {
					deltaWjk.add(new ArrayList<Double>());
					double auxCte = learningRate * errk.get(k);
					deltaWjk.get(k).add(auxCte); // bias
					for (int j = 1; j < P; j++) {
						deltaWjk.get(k).add(auxCte * zj.get(j));
					}
				}

				/** 7) RETROPORGRAMACION DEL ERROR DE LA CAPA OCULTA **/
				// err_inj = SUMk->M(errk*wjk)
				for (int j = 1; j < P; j++) {
					double aux = 0.0;
					for (int k = 0; k < M; k++) {
						aux += errk.get(k) * wWeights.get(k).get(j);
					}
					err_inj.add(aux);
					errj.add(bipolarSigmoidPrima(zj.get(j))*aux);
				}

				// deltaVij = learningRate * errj * xi
				for (int j = 0; j < P - 1; j++) {
					deltaVij.add(new ArrayList<Double>());
					double auxCte = learningRate * errj.get(j);
					//deltaVij.get(j).add(auxCte);
					for (int i = 0; i < N; i++) {
						deltaVij.get(j).add(auxCte * xi.get(i));
					}
				}

				/** 8) ACTUALIZA PESOS Y SESGO **/
				for (int k = 0; k < M; k++) {
					for (int j = 0; j < P; j++) {
						double newW = wWeights.get(k).get(j)
								+ deltaWjk.get(k).get(j);
						wWeights.get(k).set(j, newW);
					}
				}

				for (int j = 0; j < P - 1; j++) {
					for (int i = 0; i < N; i++) {
						double newV = vWeights.get(j).get(i)
								+ deltaVij.get(j).get(i);
						vWeights.get(j).set(i, newV);
					}
				}
			}
			double ecm = auxECM/R;
			outputECM.add(ecm);
//			outputECM.add(prevECM-ecm);
			outputECM.newLine();
//			System.out.println("Errores en entrenamiento ECM: " + ecm + ". Diff"+(prevECM-ecm)+" En " + (round+1) + " iteraciones");
			if(ecm<=ECM || prevECM-ecm<ECM_DIFF){
				break;
			}
			prevECM = ecm;
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
			// para cada linha
			for (Double input : inputRow.getAll()) {
				System.out.print(input + "\t");
			}
			xi = new ArrayList<Double>(inputRow.getAll());
			xi.add(0, 1.0); // bias

			/** 4) RESPUESTA DE LA CAPA OCULTA **/
			zj.add(1.0); // bias
			for (int j = 0; j < P - 1; j++) {
				// z_inj = v0j + SUMi->N(xi*vij)
				double aux = vWeights.get(j).get(0); // v0j
				for (int i = 1; i < N; i++) {
					aux += xi.get(i) * vWeights.get(j).get(i); // + (xi * vij)
				}
				z_inj.add(aux);
				zj.add(bipolarSigmoid(aux)); // zj = f(z_inj)
			}

			/** 5) RESPUESTA DE LA SALIDA **/
			for (int k = 0; k < M; k++) {
				// y_ink = w0j + SUMj->P(zj*wjk)
				double aux = 0.0;
				for (int j = 0; j < P; j++) {
					aux += zj.get(j) * wWeights.get(k).get(j); // + (zj * wjk)
				}
				y_ink.add(aux);
				yk.add(bipolarSigmoid(aux)); // yk = f(y_ink)
			}

			/** CLASIFICA SEGUN EL MAXIMO YK **/
			int outputClass = yk.indexOf(Collections.max(yk));
			for(int i=0; i<testData.getTotalTargets(); i++){
				if(i==outputClass)
					outputData.add(1);
				else
					outputData.add(0);
			}
			System.out.print("Clase: predicha " + outputClass + "\treal: "
					+ inputRow.getTargetClass());
			if (outputClass != inputRow.getTargetClass()) {
				testErrors++;
			}

			System.out.println("");
			outputData.newLine();
		}

		System.out.println("Errores en test: " + testErrors + ". "
						+ ((double) testErrors / testData.getRows().size())
						* 100 + "%");
		return ((double) testErrors / testData.getRows().size()) * 100;
	}

	protected void printWeights() {
		for (int i = 0; i < wWeights.size(); i++) {
			System.out.print(wWeights.get(i) + "\t");
			System.out.print("-\t");
		}
		// System.out.print(wbias + "\t");
		System.out.println("-\t");
	}


	private Double bipolarSigmoid(Double partialResponse) {
		double r = (2 / (1 + Math.exp(-partialResponse))) - 1;
		return r;
	}

	private Double bipolarSigmoidPrima(Double bipolarSigmoid) {
		double r = (1 + bipolarSigmoid) * (1 - bipolarSigmoid) * 0.5;
		return r;
	}

	public ArrayList<ArrayList<Double>> getvWeights() {
		return vWeights;
	}

	public ArrayList<ArrayList<Double>> getwWeights() {
		return wWeights;
	}

	public OutputData getOutputData() {
		return outputData;
	}

	public OutputData getOutputECM() {
		return outputECM;
	}

	public InputData getTestData() {
		return testData;
	}

	public InputData getTrainingData() {
		return trainingData;
	}
}
