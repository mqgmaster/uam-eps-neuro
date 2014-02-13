import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class DataFile {
	
	private ArrayList<ArrayList<Integer>> data;
	
	public void readfile(String fileName) {
	      File archivo = null;
	      FileReader fr = null;
	      BufferedReader br = null;
	 
	      try {
	         archivo = new File (fileName);
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);

	         String linea;
	         int i = 0;
	         while((linea=br.readLine())!=null){
	            System.out.println(linea);
	            StringTokenizer st = new StringTokenizer(linea);
	            while (st.hasMoreTokens()) {
	                //System.out.println(st.nextToken());
	                data.get(i).add(Integer.parseInt(st.nextToken()));
	            }
	            i++;
	         }
	      }
	      catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         try{                   
	            if( null != fr ){  
	               fr.close();    
	            }                 
	         }catch (Exception e2){
	            e2.printStackTrace();
	         }
	      }
	   }

	public ArrayList<ArrayList<Integer>> getData() {
		return data;
	}
}
