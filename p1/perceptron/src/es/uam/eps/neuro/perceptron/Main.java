package es.uam.eps.neuro.perceptron;

import java.util.ArrayList;
import java.util.Collections;

import es.uam.eps.neuro.perceptron.service.FileService;

public class Main {
	
	public static void main(String[] args) {
		ArrayList<String> list = FileService.read("D:\\problema_real1.txt");
		//for (String string : list) {
		//	System.out.println(string);
		//}
		System.out.println("shuffle");
		Collections.shuffle(list);
		for (String string : list) {
			System.out.println(string);
		}
	}

}
