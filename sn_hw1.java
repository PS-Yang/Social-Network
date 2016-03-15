package sn_hw1;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class sn_hw1 {
	public static double degree=0; //degree
	private static int node=0;//node
	private static int diameter=0;//diameter
	private static double cof=0;
	public static Hashtable<String,ArrayList> ht=new Hashtable<String,ArrayList>();
	private static Hashtable<String,Integer> distribution=new Hashtable<String,Integer>();
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String input_path="./data/com-amazon.ungraph.txt";
		String output_path="./output.csv";
		readfile(input_path);
		coefficient();
		count_diameter();
		//System.out.println("HT"+ht.size());
		System.out.println("Average Degree\t: "+degree);
		System.out.println("Average clustering coefficient: "+cof);
		System.out.println("Diameter\t: "+diameter);
		writefile(output_path);
	}
	
	private static void readfile(String path) throws IOException
	{
		ArrayList<String> list = new ArrayList<>();
		ArrayList<String> list2 = new ArrayList<>();
		//Hashtable<String,ArrayList> ht=new Hashtable<String,ArrayList>();
		FileInputStream fs=new FileInputStream(path); 
		BufferedReader br =new BufferedReader(new InputStreamReader(fs,"UTF-8"));
		String str="";
		int line=1; //行數
		while((str=br.readLine())!=null)
		{
			if(line==3)
			{
				String str_a[]=str.split(" ");
				node=Integer.valueOf(str_a[2]);
				degree=((double)Integer.valueOf(str_a[4])*2)/Integer.valueOf(str_a[2]);
			}
			else if (line>=5)
			{
				String array[]=str.split("\t");
				/*----------A->B-----------*/
				list=ht.get(array[0]);
				if(ht.get(array[0])==null) //hash 沒值
				{
					list= new ArrayList<>();
					//list.clear();
				}
				list.add(array[1]); //把值放進list
				ht.put(array[0],list);//更新hashtable
				/*----------B->A-----------*/
				list2=ht.get(array[1]);
				if(ht.get(array[1])==null)
				{
					list2= new ArrayList<>();
					//list.clear();
				}
				list2.add(array[0]);
				ht.put(array[1],list2);
				
			}
			line++;
		}
		fs.close();
	}
	private static void coefficient(){
		double triple=0;
		int triangle=0;
		Enumeration e = ht.keys();
		ArrayList<String> KeyAL = new ArrayList<String>();
		
		while(e. hasMoreElements())
		{	 
			  String key= e.nextElement().toString();  //取得ht的Key值
			  String size=""+ht.get(key).size(); //KEY值對應的VALUE SIZE
			  /*------------------distribution------------------*/
			  if(distribution.get(size)==null)
			  {
				  distribution.put(size, 1);//table 沒有 初始化為1
			  }
			  else 
			  {
				  int count=distribution.get(size);//次數
				  distribution.put(size, count+1);//table 有 count++
			  }
			  /*----------------coefficient-----------------*/
			  if(ht.get(key).size()!=1)
			  {
				  triple= ((double)ht.get(key).size()*(ht.get(key).size()-1))/2;//triple數量
				  triangle=0;
				  ArrayList<String> al=ht.get(key); //Key值對應的LIST
				  for(int i=0;i<al.size();i++)
				  {
					  for(int j=i+1;j<al.size();j++)
					  {
						  ArrayList<String> al2=ht.get(al.get(i));
						  if(al2.contains(al.get(j)))
						  {
							  triangle=triangle+1;
						  }
					  }
				  }
				  cof=cof+triangle/triple;
			  }
			  /*--------------------------------------------*/ 
			  
		}
		cof=cof/node; //cof加總後要/N得到正確值
		
	}
	private static void count_diameter() {
		Hashtable<String, Integer> ht_used=new Hashtable<>();
		Hashtable<String, Integer> ht_current=new Hashtable<>();
		
		Enumeration e1 = ht.keys();
		int count=1;
		while(e1. hasMoreElements())
		{
			int d=0;
			String k1= e1.nextElement().toString();  //取得Key值
			ht_used.put(k1, 1);
			for (int i = 0; i < ht.get(k1).size(); i++) {
				ht_current.put(ht.get(k1).get(i).toString(), 1);
			}
			d=d+1;
			while(ht_current.size()!=0)
			{
				ht_used.putAll(ht_current);
				Hashtable<String, Integer> tem=new Hashtable<>();
				Enumeration e_current = ht_current.keys();
				while(e_current. hasMoreElements())
				{
					String k_current= e_current.nextElement().toString();  //取得Key值
					
					ArrayList<String> temp=new ArrayList<>();
					temp=ht.get(k_current);
					for (String t:temp) 
					{
						if(ht_used.get(t)==null /*&& tem.get(t)!=1*/)
						{
							tem.put(t, 1);
						}
					}
				}
				ht_current.clear();
				ht_current.putAll(tem);
				tem.clear();
				d++;
			}
			if((d-1)>diameter)
				diameter=d-1;
			ht_used.clear();
			System.out.println("N="+count);
			System.out.print("Current Diameter\t: "+(d-1));
			System.out.println("\tMax Diameter\t: "+diameter);
			
			count++;
		}
	}
	private static void writefile(String path) throws IOException{
		StringBuilder sb = new StringBuilder();
		sb.append("Degree Distribution,Degree,count"+"\n");
		Enumeration e = distribution.keys();
		while(e. hasMoreElements())
		{	 
			  String s= e.nextElement().toString();
			  sb.append(","+s+","+distribution.get(s)+"\n");
		}
		 //輸出檔案
		 FileWriter fw = new FileWriter(path);
		 String result = sb.toString();
	     fw.write(result);
	     fw.flush();
	 	 fw.close();
	}
}
