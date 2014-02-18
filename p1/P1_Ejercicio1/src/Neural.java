import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


public class Neural {

	private ArrayList<ArrayList<Double>> weighs;
	private ArrayList<ArrayList<Neuron>> layer;
	private ArrayList<Neuron> neural;
	/**
	 * Calcula los valores de cada neurona de la red
	 *  en un tiempo dada una entrada
	 * @param data
	 * @return
	 */
	public ArrayList<Neuron> calculateValues(ArrayList<Integer> data){
		/*Calcula el valor de cada neurona*/
		for(int i=0; i<neural.size(); i++){
			double sum = 0.0;
			Iterator it = neural.get(i).getSignals().entrySet().iterator();		
			while(it.hasNext()){
				Entry<Neuron,Double> e = (Entry<Neuron,Double>) it.next();
				sum += (e.getKey().getValue() * e.getValue()); //Calcula el sumatorio de senales de entrada
			}
			/*Actualiza el valor de la neurona segun el umbral*/
			if(sum>=neural.get(i).getThreadhold()){
				neural.get(i).setValue(1);
			}else{
				neural.get(i).setValue(0);
			}
		}		
		return neural;
	}
	
	public void createNeural(ArrayList<Integer> data){
		layer = new ArrayList<ArrayList<Neuron>>();
		layer.add(new ArrayList<Neuron>());
		/*Actualiza la capa de entrada*/
		for(int i=0; i< data.size(); i++){
			layer.get(0).add(new Neuron(0,data.get(i)));
		}
		/*Crea la red neuronal*/
	}
	
	public ArrayList<ArrayList<Double>> getWeighs() {
		return weighs;
	}
	public void setWeighs(ArrayList<ArrayList<Double>> weighs) {
		this.weighs = weighs;
	}
	public ArrayList<ArrayList<Neuron>> getLayer() {
		return layer;
	}
	public void setLayer(ArrayList<ArrayList<Neuron>> layer) {
		this.layer = layer;
	}
	
}
