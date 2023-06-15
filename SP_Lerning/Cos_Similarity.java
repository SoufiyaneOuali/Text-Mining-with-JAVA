package SP_Lerning;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Cos_Similarity 
{

	// cos entre map de requette  et map de documente .
	
	public static double Cos_Sim_map_map(Map<String, Integer> query, Map<String, Integer> doc)
    {

        HashMap<String, Integer> temp = new HashMap();
        for (String S: query.keySet()) 
        {
            if (doc.containsKey(S)) 
            {
                temp.put(S, query.get(S));
            }
            else 
            {
                temp.put(S, 0);
            }
        }

        List<Integer> data= new ArrayList<>(temp.values());
        List<Integer> requete= new ArrayList<>(query.values());

        if(data.isEmpty() || requete.isEmpty()){
            return 0.0;
        }
        
        Double nor_temp = N(data);
        Double nor_requete = N(requete);

        double sum = 0;
        for (int i=0; i<data.size(); i++) 
        {
            sum += data.get(i)*requete.get(i);
        }
        
        if(nor_requete==0 || nor_temp==0) return 0;
        return sum / (nor_temp * nor_requete);
    }
	
	 //--------------------------------------------------------------------------------------------
	// cos entre TF_IDF de requette  et TF_IDF de  documente .
	
     public static double Cos_Sim_map_TF_IDF(Map<String, Float > query, Map<String, Float> doc)
    {

        HashMap<String, Float> temp = new HashMap();
        for (String S: query.keySet()) 
        {
            if (doc.containsKey(S)) 
            {
                temp.put(S,query.get(S));
            }
            else 
            {
                temp.put(S, (float) 0);
            }
        }

        List<Float> data= new ArrayList<>(temp.values());
        List<Float> requete= new ArrayList<>(query.values());

        if(data.isEmpty() || requete.isEmpty()){
            return 0.0;
        }
        
        Double nor_temp = NDoub(data);
        Double nor_requete = NDoub(requete);

        double sum = 0;
        for (int i=0; i<data.size(); i++) 
        {
            sum += data.get(i)*requete.get(i);
        }
        
        if(nor_requete==0 || nor_temp==0) return 0;
        return sum / (nor_temp * nor_requete);
    }
    
     //--------------------------------------------------------------------------------------------
     // La fonction qui calcul la norm
     public static double N(List<Integer> doc)
     {

         Double nortemp = 0.0;
         for (int i = 0; i < doc.size(); i++) 
         {

             nortemp += doc.get(i)*doc.get(i);

         }
         return Math.sqrt(nortemp);
     }
     //--------------------------------------------------------------------------------------------
     public static double NDoub(List<Float> doc)
     {

         Double nortemp = 0.0;
         for (int i = 0; i < doc.size(); i++) 
         {

             nortemp += doc.get(i)*doc.get(i);

         }
         return Math.sqrt(nortemp);
     }
     
     //--------------------------------------------------------------------------------------------
     
     public float idf_Fonction(String terme, Map<String,Map<String, Integer>> corpus)
     {
         Set<Map.Entry<String, Map<String,Integer>>> KeyMap = corpus.entrySet();
         Iterator<Map.Entry<String,Map<String,Integer>>> itrMap = KeyMap.iterator();
         int nbr = 0;
         while (itrMap.hasNext()){
             Map.Entry<String,Map<String,Integer>> entreeMap = (Map.Entry<String, Map<String,Integer>>)itrMap.next();
             Map<String,Integer> value = entreeMap.getValue();
             if (value.containsKey(terme)==true)
                 nbr++;
         }
         if (nbr==0) 
             nbr = 1;
         return (float) Math.log(corpus.size()/nbr);
     }
     
     //--------------------------------------------------------------------------------------------
    public static Map<String,Double> Cos_corpus_query(Map<String, Float> Req_TF_IDF_Map, Map<String,Map<String, Float>> corpus)
     {
    	 
         Set<Map.Entry<String, Map<String,Float>>> KeyMap = corpus.entrySet();
         
         Iterator<Map.Entry<String,Map<String,Float>>> itrMap = KeyMap.iterator();
         
         Map<String,Double> results = new HashMap<String, Double>();
         
         while (itrMap.hasNext())
         {
        	 
             Map.Entry<String,Map<String,Float>> entreeMap = (Map.Entry<String, Map<String,Float>>)itrMap.next();
            
             System.out.print(" \n entreeMap : "+entreeMap+" \n ");
             
             Map<String,Float> value = entreeMap.getValue();
             
             String doc_Name=entreeMap.getKey();
             
             float cos_value=(float)Cos_Sim_map_TF_IDF(Req_TF_IDF_Map,value);           
             results.put(doc_Name, (double) cos_value);
             
             
             System.out.print(" \n REQ_TF_IDF : "+Req_TF_IDF_Map+" \n ");
             System.out.print(" \n value : "+value+" \n ");
             
             System.out.print(" \n doc_name : "+doc_Name+" \n ");
             System.out.print(" \n "+doc_Name+" : "+(float)cos_value+" \n ");
         }
         LinkedHashMap<String, Double> resultstrier=sort(results); 
		
         return resultstrier;

      
     }
     
     
/*	Map<String,Float> Cos_Sim1(Map<String, Double> query, Map<String, Double> doc)
	{
		Map<String,Float> results = new HashMap<String, Float>();
		
		
		
		return results;
		
	}
	}
*/
     //Ordonner les maps
     public static LinkedHashMap<String, Double> sort(Map<String, Double> results) 
     {

         List<String> mapKeys = new ArrayList<>(results.keySet());
         List<Double> mapValues = new ArrayList<>(results.values());
         Collections.sort(mapValues);
         Collections.reverse(mapValues);

         LinkedHashMap<String, Double> sortedMap =  new LinkedHashMap<>();

         Iterator<Double> valueIt = mapValues.iterator();
         while (valueIt.hasNext()) {
             Double val = valueIt.next();
             Iterator<String> keyIt = mapKeys.iterator();

             while (keyIt.hasNext()) {
                 String key = keyIt.next();
                 Double comp1 = results.get(key);
                 Double comp2 = val;

                 if (comp1.equals(comp2)) {
                     keyIt.remove();
                     sortedMap.put(key, val);
                     break;
                 }
             }
         }
         return sortedMap;
     }

}
