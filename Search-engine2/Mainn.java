package documents;

import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
			String text="??? ????? ?????";
			String text2="??? ?????";
			String text3="????? ?????";
			
			String terme="???";
			String terme2="???";


			FinalClass fc= new FinalClass();

			Map<String,Integer>m1=fc.createMap(text);
			Map<String,Integer> m2=fc.createMap(text2);
			Map<String,Integer> m3=fc.createMap(text3);
			
			System.out.println("hello Text Mining");
			System.out.println("map de document 1"+m1);
			System.out.println("map de document 2"+m2);
			System.out.println("map de document 3"+m3);

			float tf1=fc.tf_Fonction(terme, m1);
			// float tf2=fc.tf_Fonction(terme, m2);
			//float tf3=fc.tf_Fonction(terme,m3);

			System.out.println("TF="+tf1);
			//System.out.println(tf2);
			//System.out.println(tf3);

			Map <String, Map<String, Integer>> corpus=new HashMap();
			corpus.put("doc1", m1);
			corpus.put("doc2", m2);
			corpus.put("doc3", m3);

			System.out.println("le premier terme");

			float idf=fc.idf_Fonction(terme, corpus);

		    System.out.println("IDF="+idf);
		    
		    float p=fc.idf_Fonction(terme, corpus)*fc.tf_Fonction(terme, m1);
		    System.out.println("poid="+p);


		    System.out.println("le 2 eme terme");
		    float idf2=fc.idf_Fonction(terme2, corpus);

		    System.out.println("IDF="+idf2);

		    System.out.println("le terme qui a le plus grand IDF est le plus important"
		    		+ " car il apparait dans peu de documents");

	}

}
