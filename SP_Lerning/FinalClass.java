package SP_Lerning;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import safar.basic.morphology.stemmer.factory.StemmerFactory;
import safar.basic.morphology.stemmer.interfaces.IStemmer;
import safar.basic.morphology.stemmer.model.WordStemmerAnalysis;
import safar.util.remover.Remover;

public class FinalClass {
	//    une fonction qui cree  le Map d'un document 

//  une fonction qui cree  le Map d'un document 

	Map<String,Integer> createMap(String text) 
	{
		int n=1;

		String textWithoutSW = Remover.removeStopWords(text);
		IStemmer stemmer = null;

		if(n==0)
		 stemmer = StemmerFactory.getLight10Implementation();
		if(n==1)
		 stemmer = StemmerFactory.getKhojaImplementation();

      // Stem the text using the specified stemmer

      List<WordStemmerAnalysis> listResult = stemmer.stem(textWithoutSW);
      Map<String,Integer> results = new HashMap();

	  for (WordStemmerAnalysis wsa : listResult) 
	  {
	    String stem = wsa.getListStemmerAnalysis().get(0).getMorpheme();
	    if (!results.containsKey(stem)) 
	    {
	      if (stem != null && stem.length() >= 2) 
	      {
	        results.put(stem, 1);         
	      }
	    } 
	    else 
	    {
	      int k = results.get(stem);
	      k++;
	      results.put(stem, k);
	    }
	  }
	  return results;
	}

	//*********************************************************************************************/
	
	// ***************** Fonction qui calcule nombre de termes d'un document *****************

    public int NbrTermes(Map<String,Integer> map) {
        //System.out.println("--------------------------------------------");
        int nbrtermes = 0;
        Set<Map.Entry<String,Integer>> keyMap = map.entrySet();
        Iterator<Map.Entry<String,Integer>> itrMap = keyMap.iterator();
        while(itrMap.hasNext()){
            Map.Entry<String,Integer> entreeFile = (Map.Entry<String,Integer>)itrMap.next();
            nbrtermes += entreeFile.getValue();
        }
        //System.out.println("nb="+nbrtermes);
        return nbrtermes;
    } 


    // ***************** TF de terme *************************************************//

    public float tf_Fonction(String terme, Map<String,Integer> map) {
        float d = 0;
        if(map!=null && map.containsKey(terme)!=false)
            d = map.get(terme)/(float)NbrTermes(map);
        return d;
    }
   // ***************** TF de doc *************************************************//
    Map<String,Float> TF_doc( Map<String,Integer> Doc_map)
	{
     
    	Map<String,Float> results = new HashMap<String, Float>();
    	 
		//System.out.println("TF_DOC_is_creating_Dont interept ");
    	
		for (Entry<String, Integer> entry : Doc_map.entrySet()) 
		{
		    String key = entry.getKey();
		    float tf_val=tf_Fonction(key,Doc_map);
		    results.put(key, tf_val);
		}
		
		//System.out.println("tf_doc is done successfly");

	  return results;
	}

    
    // ***************** IDF *****************
    
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
    
   // ***************** IDF DE DOC *****************
    
    Map<String,Float> IDF_doc( Map<String,Integer> Doc_map , Map<String,Map<String, Integer>> corpus)
	{
     
    	Map<String,Float> results = new HashMap<String, Float>();
    	 
		//System.out.println("TF_DOC_is_creating_Dont interept ");
    	
		for (Entry<String, Integer> entry : Doc_map.entrySet()) 
		{
		    String key = entry.getKey();
		    float idf_val=idf_Fonction(key,corpus);
		    results.put(key, idf_val);
		}
		
		//System.out.println("tf_doc is done successfly");

	  return results;
	}
	//*************************************************************************************/
	//*********************************************************************************************/
	
	
	//fonction qui calcul le poid d'un terme 
    
	 float poid(String terme,Map<String,Integer> document,Map <String, Map<String, Integer>> corpus) 
	 {
		 float tf=tf_Fonction( terme,document);
		 float idf=(float)idf_Fonction( terme,corpus );
		 float p=tf*idf;
		 return p;
		 
	 }
	 
	//fonction qui calcul le poid d'un doc
	 
	 Map<String,Float> TF_IDF_doc( Map<String,Integer> Doc_map , Map<String,Map<String, Integer>> corpus)
		{
	     
	    	Map<String,Float> results = new HashMap<String, Float>();
	    	 
			//System.out.println("TF_DOC_is_creating_Dont interept ");
	    	
			for (Entry<String, Integer> entry : Doc_map.entrySet()) 
			{
			    String key = entry.getKey();
			    
			    float tf_idf_val=poid(key,Doc_map,corpus);
			    
			    results.put(key, tf_idf_val);
			}
			
			//System.out.println("tf_doc is done successfly");

		  return results;
		}
	 
}  
 


