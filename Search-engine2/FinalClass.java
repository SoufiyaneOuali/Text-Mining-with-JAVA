package documents;

import java.util.Map;

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

	Map<String,Integer> createMap(String text){
		
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

  	  for (WordStemmerAnalysis wsa : listResult) {
  	    String stem = wsa.getListStemmerAnalysis().get(0).getMorpheme();
  	    if (!results.containsKey(stem)) {
  	      if (stem != null && stem.length() >= 2) {
  	        results.put(stem, 1);         
  	      }
  	    } else {
  	      int k = results.get(stem);
  	      k++;
  	      results.put(stem, k);
  	    }
  	  }
  	  return results;
	}
	
	//*********************************************************************************************/
	
	
	//une fonction qui calcule le TF d un terme par rapport a un document 

	float tf_Fonction(String terme,Map<String,Integer> document){
		
		//occurence du terme dans le map

	  	  int freq=document.get(terme);

	  	  //nombre totale des termes de doc 
	  	  int nbre_terme=0;
	  	Iterator iterator = document.entrySet().iterator();
	       while (iterator.hasNext()) {
	      Map.Entry mapentry = (Map.Entry) iterator.next();
	      nbre_terme=nbre_terme+(int) mapentry.getValue();//nbre totale des termes 
	    } 
	    
	    //calcul TF 
	    float tf;
	    tf=(float)(freq)/(float)(nbre_terme);
	     return tf;	
	}
	
	
	//*************************************************************************************/
	
	//focntion qui calcul idf
	float idf_Fonction(String terme,Map <String, Map<String, Integer>> corpus ){
		
	    //nombre de document dans le corpus
	    int nombre_doc=0;
	    int nbre_doc_ex=0;
	    //parcourir le map
	    Iterator iterator = corpus.entrySet().iterator();
	    while (iterator.hasNext()) {
	       nombre_doc++;
	       Map.Entry mapentry = (Map.Entry) iterator.next();
	       Map<String,Integer> m= (Map<String, Integer>) mapentry.getValue() ;
	       int exist =0;
	       Iterator iterator2 = m.entrySet().iterator();
	       while (iterator2.hasNext()) {
			      Map.Entry mapentry2 = (Map.Entry) iterator2.next();
			      if(mapentry2.getKey().equals(terme)){
		            exist=1;} 
	      }
	       if(exist==1) {nbre_doc_ex++;}
	    	 
	      }
	    
		float a=(float)(nombre_doc);
		float b=(float)(nbre_doc_ex);
	   // double a=(nombre_doc)/(nbre_doc_ex);
		float idf=(float) Math.abs(Math.log((a/b)));
		//double idf=Math.log(a);
	    
	    //return idf;
	    //idf=idf+Integer.toString(nombre_doc)+Integer.toString(nbre_doc_ex);
	     return idf;
	    } 
	    
	//*************************************************************************************/
	
	//fonction qui calcul le poid d'un terme 
	 float poid(String terme,Map<String,Integer> document,Map <String, Map<String, Integer>> corpus) {
		 
		 float tf=tf_Fonction( terme,document);
		 float idf=(float)idf_Fonction( terme,corpus );
		 float p=tf*idf;
		 return p;
		 
	 }
	    }  
 


