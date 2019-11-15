package com.ebook.drama.standfordNLP;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
         try {
	        String file = args[0];
	        readText(file);	
	       // String two = args[1];
	    }
	    catch (ArrayIndexOutOfBoundsException e){
	        System.out.println("ArrayIndexOutOfBoundsException caught");
	    }
	    finally {

	    }
        
    }
    public static void readText(String fileName){
    	FileReader fileReader;
   		BufferedReader bReader;
    	try{
	    	String line = "";
	        String cvsSplitBy = " ";
	        fileReader = new FileReader(new File(fileName));
	        bReader = new BufferedReader(fileReader);
	        line = bReader.readLine();
	        System.out.println(line);
	        while ((line = bReader.readLine()) != null ) {
	                //String[] data = line.split(cvsSplitBy);
	                // for(int i=0;i<data.length;i++){
	                // 	String temp = data[i].replaceAll("\\W+", "");
	                // 	this.insert(temp.toLowerCase());
	                // }
	                System.out.println(line);

	            }
	        bReader.close();
	        fileReader.close();

    	}
    	catch(IOException e){
    		System.out.println("error"+e);
    	}
	}
}
