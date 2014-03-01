package es.uam.eps.neuro.perceptron.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileService {
	
	public static ArrayList<String> read(String fileFullPath) {
		ArrayList<String> list = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileFullPath)))	{
			String sCurrentLine;
	
			while ((sCurrentLine = br.readLine()) != null) {
				list.add(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		System.out.println("Reading file: " + fileFullPath);
		return list;
	}
	
	public static void save(String fileFullPath, ArrayList<String> fileLines) {
		try {
			File file = new File(fileFullPath);
 
			if (!file.exists()) {
				file.createNewFile();
			}
 
			BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			for (String line : fileLines) {
				bw.write(line + "\n");
			}
			
			bw.close();
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

