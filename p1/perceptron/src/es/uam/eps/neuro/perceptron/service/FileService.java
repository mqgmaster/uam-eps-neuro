package es.uam.eps.neuro.perceptron.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import es.uam.eps.neuro.perceptron.domain.InputData;

public class FileService {
	
	public static ArrayList<String> read(String filename) {
		ArrayList<String> list = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename)))	{
			String sCurrentLine;
	
			while ((sCurrentLine = br.readLine()) != null) {
				list.add(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return list;
	}
	
	public static InputData getData(String filename) {
		ArrayList<String> list = FileService.read(filename);
		return new InputData(list);
	}
}

