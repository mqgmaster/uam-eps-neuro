package es.uam.eps.neuro.autoencoder;

import java.util.ArrayList;

import es.uam.eps.neuro.domain.OutputData;
import es.uam.eps.neuro.service.FileService;

public class Autoencoder {
	
	private static OutputData outputData = new OutputData();
	
	public static void construye_bd_autoencoder(int n, String nombre_fichero){
		if(n<1){
			System.out.println("n tiene que ser > 1");
			return;
		}
		
		//Primera linea n^2 n^2
		outputData.add(n*n);
		outputData.add(n*n);
		outputData.newLine();
		
		//Tabla vacia
		for(int i=0; i<n*n*2 ; i++){
			outputData.add(0);
		}
		outputData.newLine();
		
		construye_horizontales(n);
		construye_verticales(n);
		construye_convinados(n);
		
		FileService.save(nombre_fichero, outputData.getFileLines());
	}
	
	public static void construye_horizontales(int n){
		for(int k=0; k<n; k++){
			ArrayList<ArrayList<Integer>> tabla = new ArrayList<>();
			for(int i=0; i<n; i++){
				ArrayList<Integer> aux = new ArrayList<>();
				for(int j=0; j<n; j++){
					if(i==k){
						aux.add(1);
						outputData.add(1);
					}else{
						aux.add(0);
						outputData.add(0);
					}
				}
				tabla.add(aux);
			}
			for(int i=0; i<n; i++){
				for(int j=0; j<n; j++){
					outputData.add(tabla.get(i).get(j));
				}
			}
			outputData.newLine();
			
			
			for(int l=k+1; l<n; l++){
				for(int i=0; i<n; i++){
					for(int j=0; j<n; j++){
						if(i==l){
							outputData.add(1);
						}else{
							outputData.add(tabla.get(i).get(j));
						}
					}
				}
				for(int i=0; i<n; i++){
					for(int j=0; j<n; j++){
						if(i==l){
							outputData.add(1);
						}else{
							outputData.add(tabla.get(i).get(j));
						}
					}
				}
				outputData.newLine();
			}
		}
		
		
		
	}
	
	public static void construye_verticales(int n){
		for(int k=0; k<n; k++){
			ArrayList<ArrayList<Integer>> tabla = new ArrayList<>();
			for(int i=0; i<n; i++){
				ArrayList<Integer> aux = new ArrayList<>();
				for(int j=0; j<n; j++){
					if(j==k){
						aux.add(1);
						outputData.add(1);
					}else{
						aux.add(0);
						outputData.add(0);
					}
				}
				tabla.add(aux);
			}
			for(int i=0; i<n; i++){
				for(int j=0; j<n; j++){
					outputData.add(tabla.get(i).get(j));
				}
			}
			outputData.newLine();
			
			for(int l=k+1; l<n; l++){
				for(int i=0; i<n; i++){
					for(int j=0; j<n; j++){
						if(j==l){
							outputData.add(1);
						}else{
							outputData.add(tabla.get(i).get(j));
						}
					}
				}
				for(int i=0; i<n; i++){
					for(int j=0; j<n; j++){
						if(j==l){
							outputData.add(1);
						}else{
							outputData.add(tabla.get(i).get(j));
						}
					}
				}
				outputData.newLine();
			}
		}
	}
	
	public static void construye_convinados(int n){
		for(int l=0; l<n; l++){
			for(int k=0; k<n; k++){
				ArrayList<ArrayList<Integer>> tabla = new ArrayList<>();
				for(int i=0; i<n; i++){
					ArrayList<Integer> aux = new ArrayList<>();
					for(int j=0; j<n; j++){
						if(i==l || j==k){
							aux.add(1);
							outputData.add(1);
						}else{
							aux.add(0);
							outputData.add(0);
						}
					}
					tabla.add(aux);
				}
				for(int i=0; i<n; i++){
					for(int j=0; j<n; j++){
						outputData.add(tabla.get(i).get(j));
					}
				}
				outputData.newLine();
			}
		}
	}
	
	public static void adapta_fichero_serie(String entrada, String salida, int np){
		//Lee el fichero de entrada
		ArrayList<String> data = FileService.read(entrada);
		
		//Comprueba el numero de puntos de la serie y el numero de puntos anteriores (np)
		if(data.size()<=np){
			System.out.println("El numero de puntos de la serie es menor o igual que el numero de puntos anteriores (np)");
			return;
		}
		
		//Crea fichero con el formato indicado
		OutputData outputData = new OutputData();
		//Cabecera
		outputData.add(np+" 2");
		outputData.newLine();
		//Datos
		for(int i=0; i<data.size()-np; i++){
			for(int j=0; j<np; j++){
				outputData.add(data.get(i+j));
			}

			if(Double.parseDouble(data.get(i+np))>Double.parseDouble(data.get(i+np-1)))
				outputData.add("1 0"); //Aumenta
			else
				outputData.add("0 1"); //No aumenta
			outputData.newLine();
		}
		FileService.save(salida, outputData.getFileLines());
	}
	
}
