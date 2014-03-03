package es.uam.eps.neuro.perceptron;

import java.util.ArrayList;

import es.uam.eps.neuro.perceptron.domain.InputData;
import es.uam.eps.neuro.perceptron.domain.InputRow;
import es.uam.eps.neuro.perceptron.domain.OutputData;

public class Adaline {
	
	//definiciones estaticas respeto a la respuesta de la neurona
    public static final Double THRESHOLD = 0.0; //umbral
	public static final Double LEARNING_RATE = 0.3; //constante de entrenamiento
	public static final Double TRAINING_WEIGHT_CHANGE_TOLERANCE = 0.0001;
		
	//definiciones respeto al fichero de entrada
	public static final Double CLASS_ONE = 1.0;
	public static final String CLASS_ONE_STRING = "0 1"; //debe ser exactamente la misma del fichero de entrada
	public static final Double CLASS_TWO = -1.0;
	public static final String CLASS_TWO_STRING = "1 0"; //debe ser exactamente la misma del fichero de entrada
	
	//variables principales del algoritmo
	private Double bias; //sesgo
	private ArrayList<Double> inputWeights = new ArrayList<>();
	private InputData trainingData;
	private InputData testData;
	private OutputData outputData = new OutputData();
	private int maxTrainingRounds; //maximo de rondas de entrenamiento
	private int trainingRounds = 0;
	private int testErrors = 0;
	
	public Adaline(InputData data, Double trainingDataPercentage, int maxTrainingRounds) {
		this.maxTrainingRounds = maxTrainingRounds-1;
		setData(data, trainingDataPercentage);
		initializeWeights(data.getTotalInputs());
	}
	
	public Adaline(InputData data, Double trainingDataPercentage, ArrayList<Double> inputWeights, double bias) {
		this.maxTrainingRounds = maxTrainingRounds-1;
		setData(data, trainingDataPercentage);
		this.inputWeights = inputWeights;
		this.bias = bias;
	}

	public void setData(InputData data, Double trainingDataPercentage) {
		ArrayList<InputData> allData = data.getData(trainingDataPercentage);
		trainingData = allData.get(0);
		testData = allData.get(1);
	}
	
	private void initializeWeights(int totalInputs) {
		bias = Math.random() - 0.5;
		for (int i = 0; i < totalInputs; i++) {
			inputWeights.add(Math.random() - 0.5);
		}
	}
	
	public void startTraining() {
		System.out.println("Starting Training");
		InputRow inputRow;
		Double partialResponse;
		Double biggerWeightChange = 0.0;
		
		for (int i = 0; i < trainingData.getRows().size(); i++) {

			inputRow = trainingData.getRows().get(i);
			
			//para cada linha
//			for (Double input : inputRow.getAll()) {
//				System.out.print(input + "\t");
//			}
			
			//calcular resposta parcial da neurona
			partialResponse = calculatePartialResponse(inputRow);
//			System.out.print(partialResponse + "\t");
			
			switch (inputRow.getTargetRepresentation()) {
			case CLASS_ONE_STRING:
//				System.out.print(CLASS_ONE + "\t");
				biggerWeightChange = updateWeights(inputRow, CLASS_ONE, partialResponse);
				break;
			case CLASS_TWO_STRING:
//				System.out.print(CLASS_TWO + "\t");
				biggerWeightChange = updateWeights(inputRow, CLASS_TWO, partialResponse);
				break;
			default:
				System.out.print("\nFichero no contiene datos para aprendizaje.\nStoping training...");
				return;
			}
			
			if (i == trainingData.getRows().size()-1) {
//				System.out.println("Diff: "+biggerWeightChange+"\t");
				if (trainingRounds < maxTrainingRounds && biggerWeightChange > TRAINING_WEIGHT_CHANGE_TOLERANCE) {
					trainingRounds++;
					i = -1;
				}
			}
		}
		
		System.out.println((trainingRounds+1) + " iteraciones");
	}
	
	protected Double updateWeights(InputRow inputRow, Double target, Double partialResponse) {
		Double biggerWeightChange = 0.0;
		Double delta;
		Double deltaAbs;
		for (int i = 0; i < inputWeights.size(); i++) {
			delta = LEARNING_RATE * (target - partialResponse) * inputRow.get(i);

//			System.out.print(inputWeights.get(i) + "\t");
			inputWeights.set(i, inputWeights.get(i) + delta);			
//			System.out.print(inputWeights.get(i) + "\t");
			
			deltaAbs = Math.abs(delta);
			if (biggerWeightChange < deltaAbs) {
				biggerWeightChange = deltaAbs;
			}
		}
//		System.out.print(bias + "\t");
		bias += LEARNING_RATE * (target - partialResponse);
//		System.out.println(bias + "\t");
		
		return biggerWeightChange;
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
			partialResponse = calculatePartialResponse(inputRow);
			//System.out.print(partialResponse + "\t");
			
			//calcular resposta resposta completa
			neuronOutput = calculateResponse(partialResponse);
			
			if (neuronOutput == CLASS_ONE) {
				outputData.add(CLASS_ONE_STRING);
				System.out.print("Classe: predicha 1\treal: " + inputRow.getTargetRepresentation());
				if (!inputRow.getTargetRepresentation().equals(CLASS_ONE_STRING)) {
					testErrors++;
					System.out.print("\tError");
				}
			} else {
				outputData.add(CLASS_TWO_STRING);
				System.out.print("Classe: predicha 2\treal: " + inputRow.getTargetRepresentation());
				if (!inputRow.getTargetRepresentation().equals(CLASS_TWO_STRING)) {
					testErrors++;
					System.out.print("\tError");
				}
			}
			outputData.newLine();
			System.out.println("");
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
	
	protected Double calculateResponse(Double partialResponse) {
		if (partialResponse >= THRESHOLD) {
			return CLASS_ONE;
		} 
		return CLASS_TWO;
	}
	
	protected Double calculatePartialResponse(InputRow inputRow) {
		Double sum = 0.0;
		for (Double input : inputRow.getAll()) {
			sum += input * inputWeights.get(inputRow.getAll().indexOf(input));
		}
		return bias + sum;
	}

	public ArrayList<Double> getInputWeights() {
		return inputWeights;
	}

	public Double getBias() {
		return bias;
	}

	public OutputData getOutputData() {
		return outputData;
	}
	
}
