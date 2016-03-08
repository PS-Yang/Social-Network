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
import java.util.ArrayList;
import java.util.Hashtable;

public class sn_hw1 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String str="";
		double degree=0;
		int node=0;
		//int[][] aaa=new int [334863][334863];
		ArrayList<String> list = new ArrayList<>();
		ArrayList<String> list2 = new ArrayList<>();
		Hashtable<String,ArrayList> ht=new Hashtable<String,ArrayList>();
		Hashtable<String,Integer> distribution=new Hashtable<String,Integer>();
		
		FileInputStream fs=new FileInputStream("./data/com-amazon.ungraph.txt"); 
		BufferedReader br =new BufferedReader(new InputStreamReader(fs,"UTF-8"));
		int line=1; //���
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
				if(ht.get(array[0])==null) //hash �S��
				{
					list= new ArrayList<>();
					//list.clear();
				}
				list.add(array[1]); //��ȩ�ilist
				ht.put(array[0],list);//��shashtable
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
		double cof=0;
		double triple=0;
		int triangle=0;
		
		Enumeration e = ht.keys();
		while(e. hasMoreElements())
		{	 
			  String s= e.nextElement().toString();  //���oht��Key��
			  String size=""+ht.get(s).size(); //KEY�ȹ�����VALUE SIZE
			  /*------------------distribution------------------*/
			  if(distribution.get(size)==null)
			  {
				  distribution.put(size, 1);//table �S�� ��l�Ƭ�1
			  }
			  else 
			  {
				  int count=distribution.get(size);//����
				  distribution.put(size, count+1);//table �� count++
			  }
			  /*----------------coefficient-----------------*/
			  if(ht.get(s).size()!=1)
			  {
				  triple= ((double)ht.get(s).size()*(ht.get(s).size()-1))/2;//triple�ƶq
				  triangle=0;
				  ArrayList<String> al=ht.get(s); //Key�ȹ�����LIST
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
		cof=cof/node; //cof�[�`��n/N�o�쥿�T��
//		int d[][]=new int [node][node];    // �̵u���|����
//		
//		
//		for (int s=0; s<node; s++) // �p��C�@��i�I�P�C�@��j�I
//    	{
//    		for (int t=0; t<node; t++)
//    		{
//    			if(d[s][t]==99)
//    				System.out.print("��\t");
//    			else
//    				System.out.print(d[s][t]+"\t");
//    		}
//    		System.out.println();
//    	}
		
		
		System.out.println("Average Degree\t: "+degree);
		System.out.println("Average clustering coefficient: "+cof);
		System.out.println("Diameter\t: ");

		StringBuilder sb = new StringBuilder();
		sb.append("Degree Distribution,Degree,count"+"\n");
		e = distribution.keys();
		//int edge=0;
		while(e. hasMoreElements())
		{	 
			  String s= e.nextElement().toString();
			 // System.out.println("Degree "+s+" : "+distribution.get(s));
			  //edge=edge+distribution.get(s)*Integer.valueOf(s);
			  sb.append(","+s+","+distribution.get(s)+"\n");
		}
		 //System.out.println(edge);
		 //��X�ɮ�
//		 FileWriter fw = new FileWriter("./output.csv");
//		 String result = sb.toString();
//	     fw.write(result);
//	     fw.flush();
//	 	 fw.close();
	}
	
}
