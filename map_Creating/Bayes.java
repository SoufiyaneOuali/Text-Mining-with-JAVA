package map_Creating;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;



public class Bayes {
	
	// la creation des paramétres :
	Map<String, Map<String, Integer>> maps = new HashMap<String, Map<String, Integer>>();
	Map<String,Integer> mapTermes;
	Map<String, Map<String, Integer>> map_train = new HashMap<String, Map<String, Integer>>();
	Map<String, Map<String, Integer>> map_test = new HashMap<String, Map<String, Integer>>();
	int len;
	
	// résultat de bayes :
	float probClassPos;
	float probClassNeg;
	Map<String,Float> probTermesPos = new HashMap<String,Float>();
	Map<String,Float> probTermesNeg = new HashMap<String,Float>();
	Map<String, String> predict_test = new HashMap<String, String>();
	float score; // le score de test
	 
	
// ********************************* le constructeur : *******************************************************
	public Bayes(Map<String, Map<String, Integer>> maps, Map<String,Integer> mapTermes, int len, double p)
	{
		this.maps = maps;
		this.mapTermes =  mapTermes;
		this.len = len;
		
		// train test split
		train_test_split(p);
		
		// train
		probClassPos = prob_class('+');
		probClassNeg = prob_class('-');
		probTermesPos = prob_terms('+');
		probTermesNeg = prob_terms('-');
		
		// test
		predict_test = prediction(map_test);
		score = score(predict_test, len);
		
	}
	

// ********************** 1 ere fonction : le nombre des documents dans une class **************************
	public int nb_class(char c)  // '+' ou '-'
	{
		Set<Map.Entry<String, Map<String,Integer>>> KeyMap = map_train.entrySet();
	    Iterator<Map.Entry<String,Map<String,Integer>>> itrMap = KeyMap.iterator();
	    
        int nb = 0;
        while (itrMap.hasNext())
	    {
	        Map.Entry<String,Map<String,Integer>> entreeMap = (Map.Entry<String, Map<String,Integer>>)itrMap.next();
	        String key = entreeMap.getKey();
	        String nome = key.substring(len);
	        
	        if(nome.charAt(nome.length()-5)==c) nb = nb + 1;
	    }
        return nb;
	}
	
	
// ****************************** 2 eme fonction : probabilitÃ© d'une class *******************************
	public float prob_class(char c) // '+' ou '-'
	{
		float prob = (float)nb_class(c)/map_train.size();
	    return prob;
	}
	
	
// *************************** 3 eme fonction : le nombre de termes d'un document : ******************************
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
	
	
// *************************** 4 eme fonction : le nombre des termes dans la classe : ******************************
	public int nbTermsClass(char c)
	{
		int n = 0;
		Set<Map.Entry<String, Map<String,Integer>>> KeyMap = map_train.entrySet();
		Iterator<Map.Entry<String,Map<String,Integer>>> itrMap = KeyMap.iterator();
		while (itrMap.hasNext())
		{
		    Map.Entry<String,Map<String,Integer>> entreeMap = (Map.Entry<String, Map<String,Integer>>)itrMap.next();
		    String key = entreeMap.getKey();
		    String nome = key.substring(len);
		    
		    if(nome.charAt(nome.length()-5)==c)
		    {
		    	Map<String,Integer> map = entreeMap.getValue();
		    	n = n + NbrTermes(map);
		    } 	
		}
		return n;
	}
	
	
	
// **************** 4 eme fonction : le nombre de fois le terme se présente dans une class *********************
	public int nb_k_Terme(String terme, char c)
	{
		int k = 0;
		Set<Map.Entry<String, Map<String,Integer>>> KeyMap = map_train.entrySet();
		Iterator<Map.Entry<String,Map<String,Integer>>> itrMap = KeyMap.iterator();
		while (itrMap.hasNext())
		{
		    Map.Entry<String,Map<String,Integer>> entreeMap = (Map.Entry<String, Map<String,Integer>>)itrMap.next();
		    String key = entreeMap.getKey();
		    String nome = key.substring(len);
		    
		    if(nome.charAt(nome.length()-5)==c)
		    {
		    	Map<String,Integer> map = entreeMap.getValue();
		    	if (map.containsKey(terme))
		    		k = k + map.get(terme);
		    } 	
		}
		return k;
	}
	
	
	// ******* 5 eme fonction : renvoi un map qui calcul les probabilitÃ©s de tout les termes dans un class ********
	public Map<String,Float> prob_terms(char c) //'+' ou '-'
	{
		Map<String,Float> map = new HashMap<String,Float>();
		
		int m = mapTermes.size();
		int n = nbTermsClass(c);
		int nb_K;
		float prob;
		
		Set<Map.Entry<String,Integer>> KeyMap2 = mapTermes.entrySet();
		Iterator<Map.Entry<String,Integer>> itrMap2 = KeyMap2.iterator();
		while (itrMap2.hasNext())
		{
			Map.Entry<String,Integer> entreeMap2 = (Map.Entry<String,Integer>)itrMap2.next();
		    String terme = entreeMap2.getKey();
		    nb_K = nb_k_Terme(terme, c);
		    prob = (float)(nb_K+1)/(n+m);
		    map.put(terme,prob);
		}
		
		return map;
	}
	
	
	
	// ****************************** 6 eme fonction : test ********************************
	public Map<String, String> prediction(Map<String, Map<String, Integer>> map_test)
	{
		float prob_pos;
		float prob_neg;
		String c;
		Map<String, String> map_predict = new HashMap<String, String>();
		
		Set<Map.Entry<String, Map<String,Integer>>> KeyMap = map_test.entrySet();
		Iterator<Map.Entry<String,Map<String,Integer>>> itrMap = KeyMap.iterator();
		while (itrMap.hasNext())
		{
		    Map.Entry<String,Map<String,Integer>> entreeMap = (Map.Entry<String, Map<String,Integer>>)itrMap.next();
		    String key = entreeMap.getKey();
		    Map<String,Integer> map = entreeMap.getValue();
		    
		    prob_pos = 1;
		    prob_neg = 1;
		    Set<Map.Entry<String,Integer>> KeyMap1 = map.entrySet();
			Iterator<Map.Entry<String,Integer>> itrMap1 = KeyMap1.iterator();
			while (itrMap1.hasNext())
			{
				Map.Entry<String,Integer> entreeMap1 = (Map.Entry<String,Integer>)itrMap1.next();
			    String terme = entreeMap1.getKey();
			    int k = entreeMap1.getValue();
			    
			    if(prob_terms('+').containsKey(terme))
			    {
			    	prob_pos = prob_pos * (float)Math.pow(prob_terms('+').get(terme),k);
				    prob_neg = prob_neg * (float)Math.pow(prob_terms('-').get(terme),k);
			    }
			}
			prob_pos = prob_pos * prob_class('+');
			prob_neg = prob_neg * prob_class('-');
			if(prob_pos>prob_neg)
				c = "+";
			else
				c = "-";
			map_predict.put(key.replace('\\','/'),c);
		}
		
		return map_predict;
	}

	
	
	
	// ************************** 7 eme fonction : train test split ******************************
		public void train_test_split(double p)
		{
			double nb_train_f = maps.size()*p;
			int nb_train = (int) nb_train_f;
			
			String doc;
			Map<String, Integer> map;
			
			Set<Map.Entry<String, Map<String, Integer>>> keyMaps = maps.entrySet();
            Iterator<Map.Entry<String, Map<String, Integer>>> itrMaps = keyMaps.iterator();
            int i = 0;
            while(itrMaps.hasNext()){
                Map.Entry<String, Map<String, Integer>> entreeMap = (Map.Entry<String, Map<String, Integer>>)itrMaps.next();
                
                doc = entreeMap.getKey();
                map = entreeMap.getValue();
                
                if(i<nb_train)
                {
                	
                    map_train.put(doc, map);
                }
                	
                else
                {
                    map_test.put(doc, map);
                }
                i++;  
            }
		}
		
		
		// ************************** 7 eme fonction : train test split ******************************
				public float score(Map<String, String> map, int len)
				{
					String doc;
					String nome;
					String c;
					int nv = 0;
					
					Set<Map.Entry<String,String>> keyMaps = map.entrySet();
		            Iterator<Map.Entry<String, String>> itrMaps = keyMaps.iterator();
		            while(itrMaps.hasNext())
		            {
		            	Map.Entry<String,String> entreeMap = (Map.Entry<String,String>)itrMaps.next();
		                
		                doc = entreeMap.getKey();
		                nome = doc.substring(len);
		                c = entreeMap.getValue();
		                
		    		    if(nome.charAt(nome.length()-5) == c.charAt(0))
		    		    {
		    		    	nv++;
		    		    }
		              }
		           return nv/map.size();
				}
		
				
				
				
		

}