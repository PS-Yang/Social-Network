package sn_hw2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class outputjson {
	private static String name_index[]={"韋小寶","蘇荃","方怡","沐劍屏","雙兒","建寧公主","阿珂","曾柔","鄭成功","陳近南","馮錫範","施琅","劉國軒","周全斌","甘輝","馬信","鄭克爽","吳大鵬","王潭","蔡德忠","方大洪","馬超興","李式開","林永超","姚必達","鄺天雄","古至中","舒化龍","吳六奇","尹香主","祁彪清","崔瞎子","關安基","李力世","玄貞道人","風際中","徐天川","高彥超","錢老本","樊綱","賈老六","賈金刀","莊三少奶","洪安通","柳燕","鄧炳春","胖頭陀","瘦頭陀","毛東珠","陸高軒","張淡月","無根道人","鍾志靈","殷錦","許雪亭","雲素梅","章老三","玄燁","康熙","小玄子","佟圖賴","海大富","鰲拜","陶紅英","索尼","蘇克薩哈","小桂子","平威","老吳","董金魁","溫有道","溫有方","烏老公","察克旦","白爾赫","額圖","康親王","索額圖","蕊初","吳三桂","吳應熊","夏國相","馬寶","楊溢之","江百勝","齊元凱","郎武師","吳之榮","張勇","王進寶","孫思克","巴朗星","馬佑","張國桂","曹申吉","李本深","朱國治","甘文","巴泰","衛周祚","米思翰","對喀納","杜立德","圖海","明珠","折爾肯","達爾禮","趙良棟","富春","施老六","熊老二","瑞棟","張康年","趙齊賢","瓜管帶","盧一峰","彭參將","和察博","葛通","楊光先","黃甫","燦邴珠","林興珠","勒爾錦","莫洛","阿濟赤","洪朝","路副將","李蔚","何佑","伊桑阿","薩布素","朋春","巴海","李雷","勒德洪","馮溥","王熙","黃機","吳正治","宗德宜","班副將","郎坦","佟國綱","馬喇","阿爾尼","馬齊","慕天顏","湯若望","南懷仁","心溪","行癡","行顛","澄心","澄光","晦聰大師","澄通","淨濟","淨清","淨本","淨源","澄識","澄觀","法勝","雲雁道人","雲鶴道人","白寒松","白寒楓","蘇岡","劉一舟","沐劍聲","柳大洪","吳立身","敖彪","九難","阿琪","元義方","司徒伯雷","司徒鶴","呼巴音","巴顏","皇甫閣","勝羅陀","達和爾","桑結嘉錯","罕帖摩","葛爾丹","蘇菲亞","羅剎國總督","西奧圖三世","彼得一世","娜達麗亞","阿萊克修斯·米海洛維支","伊凡","亞爾青斯基","圖爾布青","華伯斯基","齊洛諾夫","費要多羅·果羅文","韋虎頭","韋銅錘","韋雙雙","查伊璜","吳寶宇","孫長老","韋春花","茅十八","馬博仁","姚春","雷一嘯","武通","林桂鼎","神照上人","李西華","宋三","小一劃","李自成","胡逸之","何盛","韋皋","歸辛樹","歸二娘","歸鐘","何惕守","泰都統","菊芳","蘭香","邢四"};
	private static List<String> name_list=new ArrayList<String>();
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		for (int i = 0; i < name_index.length; i++) 
		{
			name_list.add(name_index[i]);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("{\n\"nodes\":\n[\n");
		for (int i = 0; i < name_index.length; i++) {
			if(i!=name_index.length-1)
			sb.append("\t{\"name\" : \""+name_index[i]+"\", \"image\" : \"node.png\" },\n");
			else 
			sb.append("\t{\"name\" : \""+name_index[i]+"\", \"image\" : \"node.png\" }\n],\n");
		}
		sb.append("\"edges\":[\n");
		FileInputStream fs=new FileInputStream("./data/result_w.txt"); 
		BufferedReader br =new BufferedReader(new InputStreamReader(fs,"UTF-8"));
		String str="";
		int line=1; //行數
		while((str=br.readLine())!=null)
		{
			if (line>=5)
			{
				String array[]=str.split(" ");
				if(Integer.valueOf(array[2])>=200)
				{
					sb.append("  { \"source\": "+name_list.indexOf(array[0])+" , ");
					sb.append("\"target\": "+name_list.indexOf(array[1])+" , ");
					sb.append("\"relation\":\""+array[2]+"\" },\n");
				}
			}
			line++;
		}
		sb.append("]\n}\n");
		FileWriter fw = new FileWriter("./relation200.txt");
		String result = sb.toString();
	    fw.write(result);
	    fw.flush();
	 	fw.close();
	}

}
