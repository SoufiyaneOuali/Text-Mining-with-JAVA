package map_Creating;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Mainn {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
			String text="يعرف الذكاء الاصطناعي بأنه الذكاء الذي تبديه الآلات والبرامج بما يحاكي القدرات الذهنية البشرية وأنماط عملها، مثل القدرة على"
					+ " التعلم والاستنتاج ورد الفعل على أوضاع لم تبرمج في الآلة، كما أنه اسم لحقل أكاديمي يعنى بكيفية صنع حواسيب وبرامج قادرة على اتخاذ سلوك ذكيلفعل ";
			
			String text2="ويعرف كبار الباحثين الذكاء الاصطناعي تستوعب بيئتها وتتخذ إجراءات تزيد من فرص نجاحها، في"
					+ " حين يعرفه جون مكارثي -الذي وضع هذا المصطلح  علم وهندسة صنع آلات ذكية";
			
			String text3="مكنوأثبتت تقنية التعلم العميق قدرتها على التعرف على الصور وفهم الكلام والترجمة من لغة إلى أخرى، وغير ذلك من القدرات التي أغرت الشركات"
					+ " الأميركية في وادي السليكون، وتحديدا فيسبوك وغوغل، على الاستثمار وتكثيف الأبحاث فيها، متجاهلين تحذيرات من أن تطور الذكاء الاصطناعي قد يهدد البشرية";
			
			String terme="علم";
			String terme2="ذكاء";
			
			FinalClass fc= new FinalClass();
			
			Map<String,Integer>m1=fc.createMap(text);
			Map<String,Integer> m2=fc.createMap(text2);
			Map<String,Integer> m3=fc.createMap(text3);
			
			// System.out.println(m1.keySet() ); //to retrieve all keys
			
			/*
			System.out.println(" dkchi li bghit ");
			for (Entry<String, Integer> entry : m1.entrySet()) {
			    String key = entry.getKey();
			    int val = entry.getValue();
			    System.out.println(key+"  "+val);
			}
			System.out.println(" map  de texts");
			*/
			
			System.out.println("\n map de document 1 \n "+m1);
			System.out.println("\n map de document 2 \n "+m2);
			System.out.println("\n map de document 3 \n "+m3);
			
			System.out.println(" \n ------------------------------------------------------------- \n ");
			float tf1=fc.tf_Fonction(terme, m1);
			System.out.println(" \n TF de terme 1="+tf1);
			//---------------------------------------
			
			float tf2=fc.tf_Fonction(terme, m2);
			System.out.println("\n TF de terme 2="+tf2);
			
			//---------------------------------------
			float tf3=fc.tf_Fonction(terme,m3);
			System.out.println("\n TF de terme 3="+tf3);
			
			
			//maps qui contient  TF  de chaque doc .
			
			System.out.println(" \n ------------------------------------------------------------- \n ");
			
			Map<String,Float>Doc1_TF_Map=fc.TF_doc(m1);
			System.out.println("TF map de document 1 \n"+Doc1_TF_Map);
			
			System.out.println(" \n ------------------------------------------------------------- \n ");
			
			Map<String,Float>Doc2_TF_Map=fc.TF_doc(m2);
			System.out.println("TF map de document 2 \n"+Doc2_TF_Map);

			System.out.println(" \n ------------------------------------------------------------- \n ");
			
			Map<String,Float>Doc3_TF_Map=fc.TF_doc(m3);
			System.out.println("TF map de document 3 \n"+Doc3_TF_Map);

			//---------------------------------------IDF----------------------------

			
			Map <String, Map<String, Integer>> corpus=new HashMap();
			corpus.put("doc1", m1);
			corpus.put("doc2", m2);
			corpus.put("doc3", m3);
			
			System.out.println(" \n ------------------------------------------------------------- \n ");
			
			System.out.println("\n IDF DE  premier terme");
			float idf=fc.idf_Fonction(terme, corpus);
		    System.out.println("\n IDF="+idf);


		    System.out.println("\n IDF DE  deuxieme terme");
		    float idf2=fc.idf_Fonction(terme2, corpus);
		    System.out.println("\n IDF="+idf2);
		 
		    //maps qui contient  IDF  de chaque doc .
		   
		    System.out.println(" \n ------------------------------------------------------------- \n ");			
		    Map<String,Float>Doc1_IDF_Map=fc.IDF_doc(m1,corpus);
			System.out.println("IDF map de document 1 \n"+Doc1_IDF_Map);
		  
			System.out.println(" \n ------------------------------------------------------------- \n ");			
			Map<String,Float>Doc2_IDF_Map=fc.IDF_doc(m1,corpus);
			System.out.println("IDF map de document 2 \n"+Doc2_IDF_Map);
			
			System.out.println(" \n ------------------------------------------------------------- \n ");
			Map<String,Float>Doc3_IDF_Map=fc.IDF_doc(m3,corpus);
			System.out.println("IDF map de document 3 \n"+Doc3_IDF_Map);
			
			
			//maps qui contient  TF_IDF  de chaque doc .
			
			System.out.println(" \n ------------------------------------------------------------- \n ");
			Map<String,Float>Doc1_TF_IDF_Map=fc.TF_IDF_doc(m1,corpus);
			System.out.println("TF_IDF map de document 1 \n"+Doc1_TF_IDF_Map);
			
			System.out.println(" \n ------------------------------------------------------------- \n ");
			Map<String,Float>Doc2_TF_IDF_Map=fc.TF_IDF_doc(m2,corpus);
			System.out.println("TF_IDF map de document 2 \n"+Doc2_TF_IDF_Map);
			
			System.out.println(" \n ------------------------------------------------------------- \n ");
			Map<String,Float>Doc3_TF_IDF_Map=fc.TF_IDF_doc(m3,corpus);
			System.out.println("TF_IDF map de document 3 \n"+Doc3_TF_IDF_Map);
			
			
			
			

		  /*   calculer le cos entre la requette et le document  */
			
			System.out.println(" \n -----Traitement de Requette ----------------------------------------------------- \n ");
			String requette="  الاصطناعي  الذكاء تعريف";
			//map de requette 
			Map<String,Integer>Req_Map=fc.createMap(requette);
			System.out.println("map de Requette \n"+Req_Map);
			System.out.println(" \n ------------------------------------------------------------- \n ");
			// TF de Requette 
			
			Map<String,Float>Req_TF_Map=fc.TF_doc(Req_Map);
			System.out.println("TF map de Requette \n"+Req_TF_Map);
			
			//Map <String, Map<String, Integer>> Req_corpus=new HashMap();
			//Req_corpus.put("requette1", Req_Map);
			corpus.put("requette1", Req_Map);
			
			//IDF de requette	
			System.out.println(" \n ------------------------------------------------------------- \n ");
			Map<String,Float>Req_IDF_Map=fc.IDF_doc(Req_Map,corpus);
			System.out.println("IDF map de Requette \n"+Req_IDF_Map);
			
			
			//poid de requette 
			System.out.println(" \n ------------------------------------------------------------- \n ");
			Map<String,Float>Req_TF_IDF_Map=fc.TF_IDF_doc(Req_Map,corpus);
			System.out.println("TF_IDF map de Requette \n"+Req_TF_IDF_Map);
			
			
			System.out.println(" \n -----------Calculer la Distance COS (map et map )  --------------------------------- \n ");
			
			Cos_Similarity first_query=new Cos_Similarity();
			
			double dis_Sim_doc1_req =Cos_Similarity.Cos_Sim_map_map(Req_Map, m1);
			System.out.println("Cos req _ doc 1 \n"+dis_Sim_doc1_req);
			
			double dis_Sim_doc2_req =first_query.Cos_Sim_map_map(Req_Map, m2);
			System.out.println("Cos req _ doc 2 \n"+dis_Sim_doc2_req);
			
			double dis_Sim_doc3_req =first_query.Cos_Sim_map_map(Req_Map, m3);
			System.out.println("Cos req _ doc 3 \n"+dis_Sim_doc3_req);
			
			System.out.println(" \n -----------Calculer la Distance COS (TF_idf,TF_idf) --------------------------------- \n ");
			
			double B_dis_Sim_doc1_req =Cos_Similarity.Cos_Sim_map_TF_IDF(Req_TF_IDF_Map,Doc1_TF_IDF_Map);
			System.out.println("Cos req _ doc 1 \n"+dis_Sim_doc1_req);
			
			double B_dis_Sim_doc2_req =Cos_Similarity.Cos_Sim_map_TF_IDF(Req_TF_IDF_Map,Doc2_TF_IDF_Map);
			System.out.println("Cos req _ doc 2 \n"+dis_Sim_doc2_req);
			
			double B_dis_Sim_doc3_req =Cos_Similarity.Cos_Sim_map_TF_IDF(Req_TF_IDF_Map,Doc3_TF_IDF_Map);
			System.out.println("Cos req _ doc 3 \n"+dis_Sim_doc3_req);
			
			
			System.out.println(" \n -----------Calculer la Distance COS & Carpus  --------------------------------- \n ");
			
			
			Map <String, Map<String, Float>> CORP_Poids=new HashMap();
			CORP_Poids.put("text", Doc1_TF_IDF_Map);
			CORP_Poids.put("text2", Doc2_TF_IDF_Map);
			CORP_Poids.put("text3", Doc3_TF_IDF_Map);
			
			
			// Best_Resultat(CORP_Poids,Req_TF_IDF_Map);
			
			 Map<String,Double> result= Cos_Similarity.Cos_corpus_query(Req_TF_IDF_Map,CORP_Poids);
			 System.out.println("result trier \n"+result);
			 
			 //afficher les resultat .
			
				for (Entry<String, Double> entry : result.entrySet()) 
				{
				    String key = entry.getKey();
				    System.out.println("\n ------------------ \n");
				    
				    switch(key) 
				    {
				    	case "text":
				    		 System.out.print(text);
				    		break;
				    	case "text2":
				    		System.out.print(text2);
				    		break;
				    	default :
				    		System.out.print(text3);
				    }

				}
			
	}

}
