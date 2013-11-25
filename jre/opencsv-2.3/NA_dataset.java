package Test;

import au.com.bytecode.opencsv.*;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.util.ArrayList;

public class NA_dataset
{
	static ArrayList<String> Queue = new ArrayList<String>();
	
	private void readqueueback() 
	{
		// TODO Auto-generated method stub
		try
		{
		CSVReader reader = new CSVReader(new FileReader("F:\\mapping.csv"));
	    String [] nextLine;
	    while ((nextLine = reader.readNext()) != null) 
	    {
	    	int index= Integer.parseInt(nextLine[0]);
	    	Queue.add(index, nextLine[1]);
	    }
	    reader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	    	
	}
	public static void main(String[] args) 
	{   
		try
		{
		NA_dataset A=new NA_dataset();
		A.readqueueback();
		File NA_DataSet=new File("F:\\","na_dataset.csv");
	    FileWriter writer=new FileWriter(NA_DataSet,true);
	     
		CSVReader csvReader1 = new CSVReader(new FileReader("F:\\edge_list.csv"));
		String[] row = null;
		int count=0;
		while((row = csvReader1.readNext()) != null)
		{
		    Integer first=Integer.parseInt(row[0]);
		    Integer second=Integer.parseInt(row[1]);
		    writer.write(Queue.get(first));
		    writer.write(',');
          	writer.write(Queue.get(second));
          	writer.write('\n');
          	System.out.println("First: "+ Queue.get(first)+" Second: "+ Queue.get(second));
          	System.out.println("Count:"+ (++count));
          	writer.flush();
          	
		}
		csvReader1.close();
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
}