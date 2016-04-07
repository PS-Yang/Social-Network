package sn_hw2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;

public class sn_hw2 {
	public static double degree=0; //degree
	private static int node=0;//node
	private static int diameter=0;//diameter
	private static String maxnode="";
	private static double cof=0;
	public static Hashtable<String,ArrayList> ht=new Hashtable<String,ArrayList>();
	private static Hashtable<String,Integer> distribution=new Hashtable<String,Integer>();
	private static String name_index[]={"韋小寶","蘇荃","方怡","沐劍屏","雙兒","建寧公主","阿珂","曾柔","鄭成功","陳近南","馮錫範","施琅","劉國軒","周全斌","甘輝","馬信","鄭克爽","吳大鵬","王潭","蔡德忠","方大洪","馬超興","李式開","林永超","姚必達","鄺天雄","古至中","舒化龍","吳六奇","尹香主","祁彪清","崔瞎子","關安基","李力世","玄貞道人","風際中","徐天川","高彥超","錢老本","樊綱","賈老六","賈金刀","莊三少奶","洪安通","柳燕","鄧炳春","胖頭陀","瘦頭陀","毛東珠","陸高軒","張淡月","無根道人","鍾志靈","殷錦","許雪亭","雲素梅","章老三","玄燁","康熙","小玄子","佟圖賴","海大富","鰲拜","陶紅英","索尼","蘇克薩哈","小桂子","平威","老吳","董金魁","溫有道","溫有方","烏老公","察克旦","白爾赫","額圖","康親王","索額圖","蕊初","吳三桂","吳應熊","夏國相","馬寶","楊溢之","江百勝","齊元凱","郎武師","吳之榮","張勇","王進寶","孫思克","巴朗星","馬佑","張國桂","曹申吉","李本深","朱國治","甘文","巴泰","衛周祚","米思翰","對喀納","杜立德","圖海","明珠","折爾肯","達爾禮","趙良棟","富春","施老六","熊老二","瑞棟","張康年","趙齊賢","瓜管帶","盧一峰","彭參將","和察博","葛通","楊光先","黃甫","燦邴珠","林興珠","勒爾錦","莫洛","阿濟赤","洪朝","路副將","李蔚","何佑","伊桑阿","薩布素","朋春","巴海","李雷","勒德洪","馮溥","王熙","黃機","吳正治","宗德宜","班副將","郎坦","佟國綱","馬喇","阿爾尼","馬齊","慕天顏","湯若望","南懷仁","心溪","行癡","行顛","澄心","澄光","晦聰大師","澄通","淨濟","淨清","淨本","淨源","澄識","澄觀","法勝","雲雁道人","雲鶴道人","白寒松","白寒楓","蘇岡","劉一舟","沐劍聲","柳大洪","吳立身","敖彪","九難","阿琪","元義方","司徒伯雷","司徒鶴","呼巴音","巴顏","皇甫閣","勝羅陀","達和爾","桑結嘉錯","罕帖摩","葛爾丹","蘇菲亞","羅剎國總督","西奧圖三世","彼得一世","娜達麗亞","阿萊克修斯·米海洛維支","伊凡","亞爾青斯基","圖爾布青","華伯斯基","齊洛諾夫","費要多羅·果羅文","韋虎頭","韋銅錘","韋雙雙","查伊璜","吳寶宇","孫長老","韋春花","茅十八","馬博仁","姚春","雷一嘯","武通","林桂鼎","神照上人","李西華","宋三","小一劃","李自成","胡逸之","何盛","韋皋","歸辛樹","歸二娘","歸鐘","何惕守","泰都統","菊芳","蘭香","邢四"};
	private static List<String> name_list=new ArrayList<String>();
	private static int weight[][]=null;    // 一張有權重的圖
	private static int allpath[][]=null;    // 一張有權重的圖
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String input_path="./data/result.txt";
		String output_path="./output.csv";
		readfile(input_path); //沒權重
		coefficient();
		count_diameter();
		System.out.println("Average Degree\t: "+degree);
		System.out.println("Average clustering coefficient: "+cof);
		System.out.println("Diameter\t: "+diameter);
		writefile(output_path);
		input_path="./data/result_w.txt";
		readfile(input_path,"weight"); //沒權重
		count_diameter("weight");
	}
	private static void readfile(String path) throws IOException
	{
		ArrayList<String> list = new ArrayList<>();
		ArrayList<String> list2 = new ArrayList<>();
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
				String array[]=str.split(" ");
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
				maxnode=array[0];
			}
			line++;
		}
		fs.close();
	}
	private static void readfile(String path,String type) throws IOException
	{
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
				weight=new int [node][node];
				allpath=new int [node][node];
				for (int i = 0; i < name_index.length; i++) 
				{
					name_list.add(name_index[i]);
				}
					
			}
			else if (line>=5)
			{
				String array[]=str.split(" ");
				weight[name_list.indexOf(array[0])][name_list.indexOf(array[1])]=Integer.valueOf(array[2]);
				weight[name_list.indexOf(array[1])][name_list.indexOf(array[0])]=Integer.valueOf(array[2]);
			}
			line++;
		}
		fs.close();
		for (int i = 0; i < weight.length; i++) 
		{
			for (int j = 0; j < weight.length; j++) 
			{
				if(i==j)
					weight[i][j]=0;
				else if(weight[i][j]==0)
					weight[i][j]=999999999;
			}
		}
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
		Hashtable<String, Integer> not_root=new Hashtable<String, Integer>();
		int count=1;
		while(count<=10)
		{
			Random ran=new Random();
			String k1= "";
			int d=0;
			do {
				int key=ran.nextInt(Integer.valueOf(node)-1)+1;
				k1= name_index[key];  //取得Key值
			} while (ht.get(k1)==null);
			ht_used.put(k1, 1);
			for (int i = 0; i < ht.get(k1).size(); i++) 
			{
					ht_current.put(ht.get(k1).get(i).toString(), 1);
			}
			d=d+1;
			String edgenode="";
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
						if(ht_used.get(t)==null && tem.get(t)==null)
						{
							tem.put(t, d);
							edgenode=t;
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
			
			d=0;
			ht_used.put(edgenode, 1);
			for (int i = 0; i < ht.get(edgenode).size(); i++) 
			{
					ht_current.put(ht.get(edgenode).get(i).toString(), 1);
			}
			d=d+1;
			String str2="";
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
						if(ht_used.get(t)==null && tem.get(t)==null)
						{
							tem.put(t, d);
							str2=t;
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
	private static void count_diameter(String type) {
//		System.out.println("---------D "+(0)+"---------");
//		for (int s=0; s<node; s++) // 計算每一個i點與每一個j點
//    	{
//    		
//    		for (int t=0; t<node; t++)
//    		{
//    			if(weight[s][t]==999999999)
//    				System.out.print("∞\t");
//    			else
//    				System.out.print(weight[s][t]+"\t");
//    		}
//    		System.out.println();
//    	}
        for (int k=0; k<node; k++)// 嘗試每一個中繼點
        {
        	for (int i=0; i<node; i++) // 計算每一個i點與每一個j點
                for (int j=0; j<node; j++)
                    if (weight[i][k] + weight[k][j] < weight[i][j] && weight[i][k]!=99 && weight[k][j]!=99)
                    {
                    	weight[i][j] = weight[i][k] + weight[k][j];
                    	allpath[i][j] = k;
                    }
//        	System.out.println("---------weight "+(k+1)+"---------");
//        	if(k==node-1)
//        	for (int s=0; s<node; s++) // 計算每一個i點與每一個j點
//        	{
//        		
//        		for (int t=0; t<node; t++)
//        		{
//        			if(weight[s][t]==999999999)
//        				System.out.print("∞\t");
//        			else
//        				System.out.print(weight[s][t]+"\t");
//        		}
//        		System.out.println();
//        	}
//        	System.out.println();
        }
        int ddd=0;
        for (int i=0; i<node; i++) 
            for (int j=0; j<node; j++)
            	if(weight[i][j]>=ddd && weight[i][j]!=999999999)
            		ddd=weight[i][j];
        
        System.out.println("Diameter(weight)\t: "+ddd);
        
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
