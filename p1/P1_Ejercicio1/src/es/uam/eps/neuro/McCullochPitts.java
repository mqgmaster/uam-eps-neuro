package es.uam.eps.neuro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;


public class McCullochPitts {

	private ArrayList<ArrayList<Integer>> data;
	private ArrayList<Integer> layerX;
	private ArrayList<Neuron> layerZ;
	private ArrayList<Neuron> layerA;
	private ArrayList<Neuron> layerB;
	private ArrayList<Neuron> layerY;
	
	
	
	public McCullochPitts() {
		data = new ArrayList<ArrayList<Integer>>();
		layerX = new ArrayList<Integer>();
		layerZ = new ArrayList<Neuron>();
		layerA = new ArrayList<Neuron>();
		layerB = new ArrayList<Neuron>();
		layerY = new ArrayList<Neuron>();
		
		for (int i = 0; i < 3; i++) {
			layerX.add(0);
			layerZ.add(new Neuron(2, 0));
			layerA.add(new Neuron(2, 0));
			layerB.add(new Neuron(2, 0));
		}
		for (int i = 0; i < 2; i++) {
			layerY.add(new Neuron(2, 0));
		}
	}
	
	public ArrayList<Neuron> neural(ArrayList<Integer> input, PrintWriter pw){
		double sum = 0;
		
		// Se activa hacia arriba Y1
		sum = 0;
		for (Neuron n : layerA) {
			sum += 2 * n.getValue();
		}
		if (sum >= layerY.get(0).getThreadhold()) {
			layerY.get(0).setValue(1);
		} else {
			layerY.get(0).setValue(0);
		}

		// Se activa hacia abajo Y2
		sum = 0;
		for (Neuron n : layerB) {
			sum += 2 * n.getValue();
		}
		if (sum >= layerY.get(1).getThreadhold()) {
			layerY.get(1).setValue(1);
		} else {
			layerY.get(1).setValue(0);
		}

		// LayerA->Y1
		sum = layerZ.get(2).getValue() + layerX.get(0);
		if (sum >= layerA.get(0).getThreadhold()) {
			layerA.get(0).setValue(1);
		} else {
			layerA.get(0).setValue(0);
		}

		sum = layerZ.get(1).getValue() + layerX.get(2);
		if (sum >= layerA.get(1).getThreadhold()) {
			layerA.get(1).setValue(1);
		} else {
			layerA.get(1).setValue(0);
		}

		sum = layerZ.get(0).getValue() + layerX.get(1);
		if (sum >= layerA.get(2).getThreadhold()) {
			layerA.get(2).setValue(1);
		} else {
			layerA.get(2).setValue(0);
		}

		// LayerB->Y2
		sum = layerZ.get(1).getValue() + layerX.get(0);
		if (sum >= layerB.get(0).getThreadhold()) {
			layerB.get(0).setValue(1);
		} else {
			layerB.get(0).setValue(0);
		}

		sum = layerZ.get(2).getValue() + layerX.get(1);
		if (sum >= layerB.get(1).getThreadhold()) {
			layerB.get(1).setValue(1);
		} else {
			layerB.get(1).setValue(0);
		}

		sum = layerZ.get(0).getValue() + layerX.get(2);
		if (sum >= layerB.get(2).getThreadhold()) {
			layerB.get(2).setValue(1);
		} else {
			layerB.get(2).setValue(0);
		}
		
		//layerZ = layerX
		for(int i=0; i<3; i++){
			sum = layerX.get(i)*2;
			if(sum >= layerZ.get(i).getThreadhold()){
				layerZ.get(i).setValue(1);
			}else{
				layerZ.get(i).setValue(0);
			}
		}
		
		//layerX = input
		layerX = new ArrayList<Integer>();
		for (int i = 0; i < 3; i++) {
			layerX.add(input.get(i));
			System.out.print("x" + i + " = " + layerX.get(i) + "\t");
		}
		System.out.println();
		
		//System.out.println("Y1="+layerY.get(0).getValue()+" Y2="+layerY.get(1).getValue());
		pw.println(layerX.get(0)+" "+layerX.get(1)+" "+layerX.get(2)+" "+
		layerZ.get(0).getValue()+" "+layerZ.get(1).getValue()+" "+layerZ.get(2).getValue()+" "+
		layerA.get(0).getValue()+" "+layerA.get(1).getValue()+" "+layerA.get(2).getValue()+" "+
		layerB.get(0).getValue()+" "+layerB.get(1).getValue()+" "+layerB.get(2).getValue()+" "+
		layerY.get(0).getValue()+" "+layerY.get(1).getValue()+"\n");
		
		return layerY;
	}



	public static void main(String[] args) {

		// Comprobar los argumentos de entrada
		if (args.length < 2) {
			System.out.println("Introduzca el nombre del fichero de entradas y el de salidas.");
			return;
		}

		String inputFile = args[0];
		String outputFile = args[1];

		McCullochPitts mcp = new McCullochPitts();

		FileReader fr = null;
		BufferedReader br = null;
		FileWriter fw = null;
		PrintWriter pw = null;

		try {
			fr = new FileReader(new File(inputFile));
			br = new BufferedReader(fr);

			// Lectura del fichero
			String linea;
			int numLinea = 0;//Equivale al tiempo
			ArrayList<Integer> aux;
			while ((linea = br.readLine()) != null) {
				System.out.println("Linea " + numLinea + ":" + linea);
				String[] x = linea.split(" ");
				
				aux = new ArrayList<Integer>();
				for (int i = 0; i < 3; i++) {
					aux.add(Integer.parseInt(x[i]));
					System.out.print("x" + i + " = " + aux.get(i) + "\t");
				}
				mcp.data.add(aux);
				System.out.println();
				
				numLinea++;
			}
			
			fw = new FileWriter(outputFile);
			pw = new PrintWriter(fw);
			aux = new ArrayList<Integer>();
			for(int i=0; i<3; i++)
				aux.add(0);
			pw.println("x1 x2 x3 z1 z2 z3 a1 a2 a3 b1 b2 b3 y1 y2");
			for(int i=0; i<mcp.data.size()+2; i++){
				if(i>=mcp.data.size()){
					mcp.neural(aux, pw);
				}else{
					mcp.neural(mcp.data.get(i), pw);
				}
				System.out.println("Y1="+mcp.layerY.get(0).getValue()+" Y2="+mcp.layerY.get(1).getValue());

				//pw.println(i+"\tY1="+mcp.layerY.get(0).getValue()+" Y2="+mcp.layerY.get(1).getValue());// Escribe en el fichero de salida

			}
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fr) {
					fr.close();
				}
				if (null != fw) {
					fw.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

}
