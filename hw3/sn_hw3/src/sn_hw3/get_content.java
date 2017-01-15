package sn_hw3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import com.chenlb.mmseg4j.ComplexSeg;
import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MMSeg;
import com.chenlb.mmseg4j.Seg;
import com.chenlb.mmseg4j.Word;

public class get_content {
private static String cookie="";
protected Dictionary dic;
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		new get_content().run(args);
	}
	protected void run(String[] args) throws IOException {
		for (int z = 1; z <= 100; z++) 
		{
			System.out.println("page"+z);
			StringBuilder data = new StringBuilder();//文章推文
			FileInputStream fs=new FileInputStream("./list_exist/Gossiping/"+z+".txt"); 
			BufferedReader br=new BufferedReader(new InputStreamReader(fs,"BIG5"));;
			String str="";
			while((str=br.readLine())!=null)
			{
				
				if(args.length > 0) {
					str = args[0];
				}
				
				System.out.println(segWords(str, " | "));
			}
		}
	}
	public get_content() {
		System.setProperty("mmseg.dic.path", "./src/SegChinese/data");	//這裡可以指定自訂詞庫
		dic = Dictionary.getInstance();
	}
	protected Seg getSeg() {
		return new ComplexSeg(dic);
	}
	public String segWords(String txt, String wordSpilt) throws IOException {
		Reader input = new StringReader(txt);
		StringBuilder sb = new StringBuilder();
		Seg seg = getSeg();
		MMSeg mmSeg = new MMSeg(input, seg);
		Word word = null;
		boolean first = true;
		while((word=mmSeg.next())!=null) {
			if(!first) {
				sb.append(wordSpilt);
			}
			String w = word.getString();
			sb.append(w);
			first = false;		
		}
		return sb.toString();
	}
}
