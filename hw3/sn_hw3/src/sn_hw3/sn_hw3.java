package sn_hw3;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;

import org.jsoup.Connection.Method;

public class sn_hw3 {
	private static String cookie="";
	
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		get_title("Gossiping"); //先取得標題列
		get_content("Gossiping"); //取得內容推須文
		get_title("HatePolitics"); //先取得標題列
		get_content("HatePolitics"); //取得內容推須文
		content_deal("Gossiping","exist",3450,"_fix");
		content_deal("Gossiping","not",3450,"_fix");
		content_deal("HatePolitics","exist",3000,"_fix");
		content_deal("HatePolitics","not",3000,"_fix");
		content_deal("Gossiping","exist",3450,"_push");
		content_deal("Gossiping","not",3450,"_push");//BIG5
		content_deal("HatePolitics","exist",3000,"_push");//UTF-8
		content_deal("HatePolitics","not",3000,"_push");
		
	}
	public static void get_title(String board) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		Snippet snippet=new Snippet();
		try {
			snippet.ssl();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}
		Connection.Response response=Jsoup.connect("https://www.ptt.cc/ask/over18")
								.data("yes","yes").method(Method.POST).execute();
		cookie=response.cookie("over18");
		StringBuilder want = new StringBuilder();//有蔡英文
		StringBuilder other = new StringBuilder();//沒有蔡英文
		//取得頁數
		Document doc=Jsoup.connect("https://www.ptt.cc/bbs/"+board+"/index.html")
				.cookie("over18", cookie).get();
		
		Element backpage =doc.select("div[class=btn-group pull-right]>a").get(1);
		String link[]=backpage.toString().split("\"");//得到 bbs/Gossiping/index15091.html
		String index="";
		if(board.equals("Gossiping"))
			index=link[3].substring(20,link[3].indexOf("."));//15091 頁數
		else {
			index=link[3].substring(23,link[3].indexOf("."));//15091 頁數
		}
		int page=Integer.valueOf(index)+1; //Last
		
		while(page>=1)
		{
			Thread.sleep(100);  //
			
			System.out.println("page="+page);
			try {
				Document doc2=Jsoup.connect("https://www.ptt.cc/bbs/"+board+"/index"+page+".html")
						.cookie("over18", cookie).get();
				//System.out.println(doc);
				Elements list =doc2.select("div[class=r-ent]>div[class=title]>a");
				for(int i=0;i<list.size();i++)
				{
					if(list.get(i).toString().contains("蔡英文"))
					{
						//System.out.println(list.get(i));
						want.append(list.get(i)+"\n");
					}
					else 
					{
						//System.out.println(list.get(i));
						other.append(list.get(i)+"\n");
					}
				}
				page--;
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}
			
		}
		System.out.println("List -end!!");
		
		FileWriter fw = new FileWriter("./list/"+board+"/want.txt");
		String result_want = want.toString();
	    fw.write(result_want);
	    
	    fw = new FileWriter("./list/"+board+"/other.txt");
	    String result_other = other.toString();
	    fw.write(result_other);
	    
	    fw.flush();
	 	fw.close();
	 	System.out.println("Write end!!");
		
	}

	public static void get_content(String board) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		Snippet snippet=new Snippet();
		try {
			snippet.ssl();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}
		Connection.Response response=Jsoup.connect("https://www.ptt.cc/ask/over18")
								.data("yes","yes").method(Method.POST).execute();
		cookie=response.cookie("over18");
		StringBuilder data = new StringBuilder();//文章推文
		FileInputStream fs=new FileInputStream("./list/"+board+"/want.txt"); 
		BufferedReader br =new BufferedReader(new InputStreamReader(fs,"UTF-8"));
		String str=br.readLine();
		int line=1; //行數
		while(true)
		{
			String s[]=str.split("\"");
			String url="https://www.ptt.cc"+s[1];
			Thread.sleep(100);
			System.out.println(line);
			try {
				Document doc2=Jsoup.connect(url)
						.cookie("over18", cookie).get();
				//System.out.println(doc);
				Elements list =doc2.select("div[class=push]");
				for(int i=0;i<list.size();i++)
				{
					Element t1=list.get(i).select("span").get(0);
					Element t2=list.get(i).select("span").get(2);
					//System.out.println(t1.text());
					//System.out.println(t2.text().substring(1));
					data.append(t1.text()+" "+t2.text().substring(1)+"\n");
				}
				 FileWriter fw = new FileWriter("./list_exist/"+board+"/"+line+".txt");
			     String result = data.toString();
			     fw.write(result);
			     fw.flush();
			  	 fw.close();
			  	 data.delete( 0, data.length() );
			  	 line++;
				 if((str=br.readLine())==null)
					break;
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
				if(e.toString().contains("Status=404"))
				{
					str=br.readLine();
					if(str==null)
						break;
				}
			}
			
		}
				
		
		FileInputStream fs2=new FileInputStream("./list/"+board+"/other.txt"); 
		BufferedReader br2 =new BufferedReader(new InputStreamReader(fs2,"UTF-8"));
		str=br2.readLine();
		line--;
		while(line>=1)
		{
			String s[]=str.split("\"");
			String url="https://www.ptt.cc"+s[1];
			Thread.sleep(100);
			System.out.println(line);
			try {
				Document doc2=Jsoup.connect(url)
						.cookie("over18", cookie).get();
				//System.out.println(doc);
				Elements list =doc2.select("div[class=push]");
				for(int i=0;i<list.size();i++)
				{
					Element t1=list.get(i).select("span").get(0);
					Element t2=list.get(i).select("span").get(2);
					//System.out.println(t1.text());
					//System.out.println(t2.text().substring(1));
					data.append(t1.text()+" "+t2.text().substring(1)+"\n");
				}
				 FileWriter fw = new FileWriter("./list_not/"+board+"/"+line+".txt");
			     String result = data.toString();
			     fw.write(result);
			     fw.flush();
			  	 fw.close();
			  	 data.delete( 0, data.length() );
			  	 line--;
				 if((str=br2.readLine())==null)
					break;
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
				if(e.toString().contains("Status=404"))
				{
					str=br2.readLine();
					if(str==null)
						break;
				}
			}
			
		}
	}
	
	public static void content_deal(String board,String type,int length,String type2) throws IOException, InterruptedException {
		//int z=1;
		for (int z = 1; z <= length; z++) 
		{
			System.out.println(z);
			StringBuilder data = new StringBuilder();//文章推文
			FileInputStream fs=new FileInputStream("./list_"+type+"/"+board+"/"+z+".txt"); 
			BufferedReader br=null;
			if(board.equals("Gossiping"))
			{
				br=new BufferedReader(new InputStreamReader(fs,"BIG5"));
			}
			else {
				br=new BufferedReader(new InputStreamReader(fs,"UTF-8"));
			}
			Hashtable<String,Integer> word=new Hashtable<String,Integer>();
			int total=0; //總共文字次數
			String str="";
			while((str=br.readLine())!=null)
			{
				//System.out.println(str.substring(0));
				String pushString=str.substring(0,1); //推OR噓
				if(type2.equals("_push") && pushString.equals("推"))
				{
					//System.out.println("1");
					JiebaSegmenter segmenter = new JiebaSegmenter();
					String segString=segmenter.process(str, SegMode.INDEX).toString();
					segString=segString.replaceAll(Pattern.quote("[["),"").replaceAll(Pattern.quote("]]"),"");
					String str2[]=segString.split(Pattern.quote("], ["));
					for (int i = 0; i < str2.length; i++) 
					{
						//System.out.println(str2[i]);
						String str3[]=str2[i].split(",");
						if(word.get(str3[0])==null && str3[0]!=" ")
						{
							word.put(str3[0], 1);
							total++;
						}
						else {
							int count=word.get(str3[0]);
							word.put(str3[0], count+1);
							total++;
						}
					}
				}
				else if(type2.equals("_fix"))
				{
					JiebaSegmenter segmenter = new JiebaSegmenter();
					String segString=segmenter.process(str, SegMode.INDEX).toString();
					segString=segString.replaceAll(Pattern.quote("[["),"").replaceAll(Pattern.quote("]]"),"");
					String str2[]=segString.split(Pattern.quote("], ["));
					for (int i = 0; i < str2.length; i++) 
					{
						//System.out.println(str2[i]);
						String str3[]=str2[i].split(",");
						if(word.get(str3[0])==null && str3[0]!=" ")
						{
							word.put(str3[0], 1);
							total++;
						}
						else {
							int count=word.get(str3[0]);
							word.put(str3[0], count+1);
							total++;
						}
					}
				}
				
			}
			data.append("total,"+total+"\n");
			Enumeration e = word.keys();
			while(e. hasMoreElements())
			{	 
				String key= e.nextElement().toString();  //取得ht的Key值
				data.append(key+","+word.get(key)+"\n");
			}
			 FileWriter fw = new FileWriter("./list_"+type+"/"+board+type2+"/"+z+".txt");
		     String result = data.toString();
		     fw.write(result);
		     fw.flush();
		  	 fw.close();
		}
		
	}

	public static void content_dealjas(String board,String type,int length,String type2) throws IOException, InterruptedException {
		
		for (int z = 1; z <= length; z++) 
		{
			System.out.println(z);
			StringBuilder data = new StringBuilder();//文章推文
			FileInputStream fs=new FileInputStream("./list_"+type+"/"+board+"/"+z+".txt"); 
			BufferedReader br=null;
			if(board.equals("Gossiping"))
			{
				br=new BufferedReader(new InputStreamReader(fs,"BIG5"));
			}
			else {
				br=new BufferedReader(new InputStreamReader(fs,"UTF-8"));
			}
			Hashtable<String,Integer> word=new Hashtable<String,Integer>();
			int total=0; //總共文字次數
			String str="";
			while((str=br.readLine())!=null)
			{
				//System.out.println(str.substring(0));
				String pushString=str.substring(0,1); //推OR噓
				if(type2.equals("_push") && pushString.equals("推"))
				{
					//System.out.println("1");
					JiebaSegmenter segmenter = new JiebaSegmenter();
					String segString=segmenter.process(str, SegMode.INDEX).toString();
					segString=segString.replaceAll(Pattern.quote("[["),"").replaceAll(Pattern.quote("]]"),"");
					String str2[]=segString.split(Pattern.quote("], ["));
					for (int i = 0; i < str2.length; i++) 
					{
						//System.out.println(str2[i]);
						String str3[]=str2[i].split(",");
						if(word.get(str3[0])==null && str3[0]!=" ")
						{
							word.put(str3[0], 1);
							total++;
						}
						else {
							int count=word.get(str3[0]);
							word.put(str3[0], count+1);
							total++;
						}
					}
				}
				else if(type2.equals("_fix"))
				{
					JiebaSegmenter segmenter = new JiebaSegmenter();
					String segString=segmenter.process(str, SegMode.INDEX).toString();
					segString=segString.replaceAll(Pattern.quote("[["),"").replaceAll(Pattern.quote("]]"),"");
					String str2[]=segString.split(Pattern.quote("], ["));
					for (int i = 0; i < str2.length; i++) 
					{
						//System.out.println(str2[i]);
						String str3[]=str2[i].split(",");
						if(word.get(str3[0])==null && str3[0]!=" ")
						{
							word.put(str3[0], 1);
							total++;
						}
						else {
							int count=word.get(str3[0]);
							word.put(str3[0], count+1);
							total++;
						}
					}
				}
				
			}
			data.append("total,"+total+"\n");
			Enumeration e = word.keys();
			while(e. hasMoreElements())
			{	 
				String key= e.nextElement().toString();  //取得ht的Key值
				data.append(key+","+word.get(key)+"\n");
			}
			 FileWriter fw = new FileWriter("./list_"+type+"/"+board+type2+".json",true);
		     String result = data.toString();
		     fw.write(result);
		    
		     fw.flush();
		  	 fw.close();
		}
		
	}
}
