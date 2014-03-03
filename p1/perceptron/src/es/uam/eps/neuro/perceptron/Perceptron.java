package es.uam.eps.neuro.perceptron;

import java.util.ArrayList;

import es.uam.eps.neuro.perceptron.domain.InputData;
import es.uam.eps.neuro.perceptron.domain.InputRow;
import es.uam.eps.neuro.perceptron.domain.OutputData;

public class Perceptron {
	
	//definiciones estaticas respeto a la respuesta de la neurona
	public static final Double THRESHOLD = 0.2; 	//umbral
	public static final Double LEARNING_RATE = 0.3; //constante de entrenamiento
	
	//definiciones respeto al fichero de entrada
	public static final Double CLASS_ONE = 1.0;
	public static final String CLASS_ONE_STRING = "0 1"; //debe ser exactamente la misma del fichero de entrada
	public static final Double CLASS_TWO = -1.0;
	public static final String CLASS_TWO_STRING = "1 0"; //debe ser exactamente la misma del fichero de entrada
	public static final Double UNDEFINED = 0.0;
	public static final String UNDEFINED_STRING = "0 0";
	
	//variables principales del algoritmo
	private Double bias = 0.0; 						//sesgo inicial
	private ArrayList<Double> inputWeights = new ArrayList<>();
	private InputData trainingData;
	private InputData testData;
	private OutputData outputData = new OutputData();
	private boolean hasUpdatedWeights = false;
	private int maxTrainingRounds = 50; //maximo de rondas de entrenamiento
	private int trainingRounds = 0;
	private int trainingErrors = 0;
	private int testErrors = 0;
	
	public Perceptron(InputData data, Double trainingDataPercentage, int maxTrainingRounds) {
		this.maxTrainingRounds = maxTrainingRounds;
		setData(data, trainingDataPercentage);
		initializeWeights(data.getTotalInputs());
	}
	
	public Perceptron(InputData data, Double trainingDataPercentage, ArrayList<Double> inputWeights) {
		setData(data, trainingDataPercentage);
		this.inputWeights = inputWeights;
	}
	
	public void setData(InputData data, Double trainingDataPercentage) {
		ArrayList<InputData> allData = data.getData(trainingDataPercentage);
		trainingData = allData.get(0);
		testData = allData.get(1);
	}
	
	private void initializeWeights(int totalInputs) {
		for (int i = 0; i < totalInputs; i++) {
			inputWeights.add(0.0);
		}
	}
	
	public void startTraining() {
		System.out.println("Starting Training");
		InputRow inputRow;
		Double partialResponse;
		Double neuronOutput;
		
		for (int i = 0; i < trainingData.getRows().size(); i++) {
			
			inputRow = trainingData.getRows().get(i);
			//para cada linha
//			for (Double input : inputRow.getAll()) {
//				System.out.print(input + "\t");
//			}
			
			//calcular resposta parcial da neurona
			partialResponse = calculatePartialResponse(inputRow);
//			System.out.print(partialResponse + "\t");
			
			//calcular resposta resposta completa
			neuronOutput = calculateResponse(partialResponse);
//			System.out.print(neuronOutput + "\t");
			
			//se target diferente de output
			switch (inputRow.getTargetRepresentation()) {
			case CLASS_ONE_STRING:
//				System.out.print(CLASS_ONE + "\t");
				if (neuronOutput != CLASS_ONE) {
					//update pesos
					trainingErrors++;
					updateWeights(inputRow, CLASS_ONE);
				} //else printWeights();
				break;
			case CLASS_TWO_STRING:
//				System.out.print(CLASS_TWO + "\t");
				if (neuronOutput != CLASS_TWO) {
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
	
//	public double startTest() {
//		return startTest(false);
//	}
	
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
			partialResponse = calculatePartialResponse(inputRow);
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
		for (int i = 0; i < inputWeights.size(); i++) {
			System.out.print(inputWeights.get(i) + "\t");
			System.out.print("-\t");
		}
		System.out.print(bias + "\t");
		System.out.println("-\t");
	}
	
	private void updateWeights(InputRow inputRow, Double target) {
		if (!hasUpdatedWeights) {
			hasUpdatedWeights = true;
		}
		for (int i = 0; i < inputWeights.size(); i++) {
//			System.out.print(inputWeights.get(i) + "\t");
			inputWeights.set(i, inputWeights.get(i) + (LEARNING_RATE * target * inputRow.get(i)));
//			System.out.print(inputWeights.get(i) + "\t");
		}
//		System.out.print(bias + "\t");
		bias += LEARNING_RATE * target;
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
	
	private Double calculatePartialResponse(InputRow inputRow) {
		Double sum = 0.0;
		for (Double input : inputRow.getAll()) {
			sum += input * inputWeights.get(inputRow.getAll().indexOf(input));
		}
		return bias + sum;
	}
	
	public ArrayList<Double> getInputWeights() {
		return inputWeights;
	}

	public OutputData getOutputData() {
		return outputData;
	}
}
