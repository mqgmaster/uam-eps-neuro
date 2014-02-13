import java.util.ArrayList;


public class Neural {

	private ArrayList<ArrayList<Double>> weighs;
	private ArrayList<Neuron> inputs; //Data
	private ArrayList<Neuron> outputs;
	private ArrayList<Neuron> capas;
	
	public int calculateValues(ArrayList<Integer> data){
		
		for(int i=0; i<weighs.size(); i++){
			for(int j=0; j<weighs.get(i).size(); j++){
				
			}
		}
		
		return 0;
	}
	
	public ArrayList<ArrayList<Double>> getWeighs() {
		return weighs;
	}
	public void setWeighs(ArrayList<ArrayList<Double>> weighs) {
		this.weighs = weighs;
	}
	public ArrayList<Neuron> getInputs() {
		return inputs;
	}
	public void setInputs(ArrayList<Neuron> inputs) {
		this.inputs = inputs;
	}
	public ArrayList<Neuron> getOutputs() {
		return outputs;
	}
	public void setOutputs(ArrayList<Neuron> outputs) {
		this.outputs = outputs;
	}
	public ArrayList<Neuron> getCapas() {
		return capas;
	}
	public void setCapas(ArrayList<Neuron> capas) {
		this.capas = capas;
	}
	
}
