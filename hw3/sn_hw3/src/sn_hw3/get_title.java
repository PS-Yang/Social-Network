package sn_hw3;

import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class get_title {
	private static String cookie="";
	public static void main(String[] args) throws IOException, InterruptedException {
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
		Document doc=Jsoup.connect("https://www.ptt.cc/bbs/Gossiping/index.html")
				.cookie("over18", cookie).get();
		
		Element backpage =doc.select("div[class=btn-group pull-right]>a").get(1);
		String link[]=backpage.toString().split("\"");//得到 bbs/Gossiping/index15091.html
		String index=link[3].substring(20,link[3].indexOf("."));//15091 頁數
		int page=Integer.valueOf(index)+1; //Last
		
		while(page>=1)
		{
			Thread.sleep(100);  //
			
			System.out.println("page="+page);
			try {
				Document doc2=Jsoup.connect("https://www.ptt.cc/bbs/Gossiping/index"+page+".html")
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
		
		FileWriter fw = new FileWriter("./list/Gossiping/want.txt");
		String result_want = want.toString();
	    fw.write(result_want);
	    
	    fw = new FileWriter("./list/Gossiping/other.txt");
	    String result_other = other.toString();
	    fw.write(result_other);
	    
	    fw.flush();
	 	fw.close();
	 	System.out.println("Write end!!");
		
	}

}
