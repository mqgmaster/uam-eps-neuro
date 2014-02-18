import java.util.HashMap;


public class Neuron {

	private double threadhold;
	private int value;
	private HashMap<Neuron,Double> signals;
		
	public Neuron(double threadhold, int value) {
		super();
		this.threadhold = threadhold;
		this.value = value;
	}
	public double getThreadhold() {
		return threadhold;
	}
	public void setThreadhold(double threadhold) {
		this.threadhold = threadhold;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public HashMap<Neuron, Double> getSignals() {
		return signals;
	}
	public void setSignals(HashMap<Neuron, Double> signals) {
		this.signals = signals;
	}
	
	public void addSignal(Neuron n, Double w){
		signals.put(n, w);
	}
}
