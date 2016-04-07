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
	private static String name_index[]={"���p�_","Ĭ��","���","�N�C��","����","�ع礽�D","����","���X","�G���\","����n","�����d","�I�w","�B��a","�P���y","�̽�","���H","�G�J�n","�d�j�P","����","���w��","��j�x","���W��","�����}","�L�öW","�����F","�K�Ѷ�","�j�ܤ�","�Τ��s","�d���_","�����D","�³C�M","�Z�M�l","���w��","���O�@","�ȭs�D�H","���ڤ�","�}�Ѥt","���۶W","���ѥ�","�Ժ�","��Ѥ�","����M","���T�֥�","�x�w�q","�h�P","�H���K","�D�Y��","�G�Y��","��F�]","�����a","�i�H��","�L�ڹD�H","����F","���A","�\���F","������","���ѤT","���M","�d��","�p�Ȥl","�c�Ͽ�","���j�I","����","�����^","����","Ĭ�J�ī�","�p�ۤl","����","�ѧd","������","�Ŧ��D","�Ŧ���","�Q�Ѥ�","��J��","�պ���","�B��","�d�ˤ�","���B��","����","�d�T��","�d����","�L���","���_","������","���ʳ�","������","���Z�v","�d���a","�i�i","���i�_","�]��J","�ڮԬP","����","�i���","��ӦN","�����`","����v","�̤�","�ڮ�","�éP��","�̫俫","��د�","���߼w","�Ϯ�","���]","�麸��","�F��§","���}��","�I�K","�I�Ѥ�","���ѤG","���","�i�d�~","������","�ʺޱa","�c�@�p","�^�ѱN","�M���","���q","������","���j","���ԯ]","�L���]","�Ǻ��A","����","���٨�","�x��","���ƱN","����","���","����","�ĥ���","�B�K","�ڮ�","���p","�Ǽw�x","����","����","����","�d���v","�v�w�y","�Z�ƱN","���Z","�c���","����","������","����","�}���C","���Y��","�n�h��","�߷�","��è","���A","���","���","���o�j�v","��q","�b��","�b�M","�b��","�b��","����","���[","�k��","�����D�H","���b�D�H","�մH�Q","�մH��","Ĭ��","�B�@��","�N�C�n","�h�j�x","�d�ߨ�","�γC","�E��","���X","���q��","�q�{�B�p","�q�{�b","�I�ڭ�","���C","�Өj��","��ù��","�F�M��","�ᵲ�ſ�","�u����","������","Ĭ���","ù�b���`��","����ϤT�@","���o�@�@","�R�F�R��","���ܧJ�״��P�̮�������","��Z","�Ⱥ��C����","�Ϻ����C","�اB����","�����դ�","�O�n�hù�P�Gù��","�����Y","������","������","�d��X","�d�_�t","�]����","���K��","�T�Q�K","���դ�","���K","�p�@�S","�Z�q","�L�۹�","���ӤW�H","�����","���T","�p�@��","���ۦ�","�J�h��","��","���o","�k����","�k�G�Q","�k��","�󱧦u","������","���","����","���|"};
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
		int line=1; //���
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
