package es.uam.eps.neuro;

public class Neuron {

	private double threadhold;
	private int value;
	
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
	
}
