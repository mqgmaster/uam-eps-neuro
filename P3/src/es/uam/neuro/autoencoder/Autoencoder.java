package es.uam.neuro.autoencoder;

import es.uam.eps.neuro.domain.OutputData;
import es.uam.eps.neuro.service.FileService;

public class Autoencoder {
	
	private OutputData outputData = new OutputData();
	
	public void construye_bd_autoencoder(int n, String nombre_fichero){
		//Primera linea n n
		outputData.add(n+" "+n);
		outputData.newLine();
		
		//Tabla vacia
		ArrayList<Integer>
		
		
		FileService.save(nombre_fichero+".txt", outputData.getFileLines());
	}
	
	public void construye_horizontales(){
		
	}
	
	public void construye_verticales(){
		
	}
	
	public void construye_convinados(){
		
	}
	
}
