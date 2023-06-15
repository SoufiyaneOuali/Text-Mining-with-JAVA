package map_Creating;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NaiveByse {
	

	public class NaiveBaiyes {

		 String[] mots;//le vecteur des mots non repeté
		 Map<String,Map<String,Integer>> corpus;
		 //corpus sous forme de vecteur de chaque doc Map<String,Integer> et son nom avec dernierclass String
		 //le vect de doc contient chaque mot et son occurence (0 s'il n'existe pas)
		 String[]clas ;//les class "label"
		 
		 //methode pour calculer la proba de chaque class ex:p(+)
		 float probaClass(String c) 
		 {
			     int n=corpus.size();
			      float probac;
			      int conteur=0;
				 Iterator iterator = corpus.entrySet().iterator();
			       while (iterator.hasNext()) 
			       {
			       Map.Entry mapentry = (Map.Entry) iterator.next();
			          String nomdoc=(String) mapentry.getKey() ;
			        	  if(nomdoc.substring(-1).equalsIgnoreCase(c)) 
			        	  {
			        		  conteur++;
			        	  }
			          }
			       probac=conteur/n;
			       
			       return probac;
		 }
			      
				 
		 //nombre de mots dans une class
		 int motsClass(String c)
		 {
			 int conteur=0;
			 Iterator iterator = corpus.entrySet().iterator();
		       while (iterator.hasNext()) 
		       {
		       Map.Entry mapentry = (Map.Entry) iterator.next();
		       String nomdoc=(String) mapentry.getKey() ;
	     	      if(nomdoc.substring(-1).equalsIgnoreCase(c))
	     	      {
	     	    	 Map<String,Integer> m=(Map<String, Integer>) mapentry.getValue();
	     	    	Iterator iterator2 = m.entrySet().iterator();
	     	       while (iterator2.hasNext()) 
	     	        {
	     	         Map.Entry mapentry2 = (Map.Entry) iterator2.next();
	     	            conteur+=(int) mapentry2.getValue();
	     	  }
		 }
		       }
		       return conteur;
		       }
		 //calculer la probabilite d'un terme par rapport a une class p(mot/+)
		 float motProba(String mot, String c) {
			 int m=mots.length;//nomre de mots non repetes
			 int n=motsClass(c);//nombre de mot dans class c 
			 int k=0;
			 float probamotc;
			 Iterator iterator = corpus.entrySet().iterator();
		       while (iterator.hasNext()) {
		       Map.Entry mapentry = (Map.Entry) iterator.next();
		       String nomdoc=(String) mapentry.getKey() ;
	     	      if(nomdoc.substring(-1).equalsIgnoreCase(c)) {
	     	    	 Map<String,Integer> m1=(Map<String, Integer>) mapentry.getValue();
	     	    	 k+=m1.get(mot);
	     	    	  
	     	  }
		 }     
		       probamotc=(k+1)/(n+m);
		       return probamotc;
		       }
		 
		 //classification d'un document 
		 
		 String classification(String doc) {
			
			 Map<String,Float> lisproba = new HashMap();
			 //la probabilite de ce terme par rapport a chacune des class
			 String listdoc[]=doc.split(" ");
			 for(int j=0;j<clas.length;j++) {
				 float p=probaClass( clas[j]);
				 for(int i=0;i<listdoc.length;i++){
					 p*=motProba(listdoc[i], clas[j]);
				 }
				 lisproba.putIfAbsent(clas[j], p);
			 }
			 
			 float max=0;
			 String c="";
			 Iterator iterator = lisproba.entrySet().iterator();
		       while (iterator.hasNext()) {
		       Map.Entry mapentry = (Map.Entry) iterator.next();
		            float a=(float) mapentry.getValue();
		            if(a>max) {
		            	max=a;
		            	c=(String) mapentry.getKey();
		            }
		       }
		       
			 return c;
			 
		 }
			 
			 
			 
		 }
		 




}
