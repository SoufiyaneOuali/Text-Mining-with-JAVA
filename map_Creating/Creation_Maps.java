package map_Creating;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.Iterator;


import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
//import java.io.FileOutputStream;
//import java.io.ObjectOutputStream;
//import java.io.BufferedReader;

import safar.util.remover.Remover;
import safar.basic.morphology.stemmer.interfaces.IStemmer;
import safar.basic.morphology.stemmer.model.WordStemmerAnalysis;
import safar.basic.morphology.stemmer.factory.StemmerFactory;



public class Creation_Maps {
	Map<String, Map<String, Integer>> maps_occurenec = new HashMap<String, Map<String, Integer>>();
	String inputFile;
    //String outputFile_oc;
    //String outputFile_TF_IDF;
    int n;
    int nbTotal = 0;
    Map<String,Integer> mapTermes = new HashMap<String,Integer>(); //tous les termes
    float[][] TF;
    float[] IDF;
    float[][] TF_IDF;
    
    // Map<String,float> results = new HashMap();

    public Creation_Maps(String inputFile, int n) throws FileNotFoundException, IOException {
    	this.inputFile = inputFile;
        //this.outputFile_oc = outputFile_oc;
        //this.outputFile_TF_IDF = outputFile_TF_IDF;
        this.n = n;
        createMapOccurence();
        System.out.println("maps occurenec, done +++++++++++++++++++++");
        Creation_Maps_TF_IDF(maps_occurenec);
        System.out.println("TF_IDF, done +++++++++++++++++++++++++++++");
    }

	public void createMapOccurence() throws FileNotFoundException, IOException {
		File droit= new File(inputFile);
		File[] dry = droit.listFiles();
		//System.out.println("+++++++++++++++++++++"+dry.length);
		for(int i=0; i<dry.length; i++) {
			File[] files = dry[i].listFiles();
			//System.out.println("----------------"+files.length);
			for(int j = 0; j<files.length; j++) {
				//System.out.println("+++++***********+++++++"+files[j].getName());
				int c; 
				StringBuffer sb = new StringBuffer();
				try {
				FileReader fr = new FileReader(files[j]);
				while((c=fr.read())!=-1)
					sb.append((char)c);

                String textWithoutSW = Remover.removeStopWords(sb.toString());
				IStemmer stemmer = null;
				if(n==0)
					stemmer = StemmerFactory.getLight10Implementation();
				if(n==1)
					stemmer = StemmerFactory.getKhojaImplementation();
				//Stem the text using the specified stemmer
				List<WordStemmerAnalysis> listResult = stemmer.stem(textWithoutSW);
				
				Map<String, Integer> results = new HashMap<String, Integer>();
				for (WordStemmerAnalysis wsa : listResult) {
					String stem = wsa.getListStemmerAnalysis().get(0).getMorpheme();
					if (!results.containsKey(stem)) {
			    		    if(stem != null && stem.length() >= 2)
			   				results.put(stem,1);
			  		}
			        else {
		   			    int k = results.get(stem);
		   			    k++;
		   			    results.put(stem, k);
					}
					
					if (!mapTermes.containsKey(stem))
						mapTermes.put(stem, 1);
				}
				
				maps_occurenec.put(files[j].getAbsolutePath().replace('\\','/'),results);
                fr.close();
                }
				catch(Exception e)
                		{ e.printStackTrace(); }
		    }
	    }
		
		System.out.println("******************");
		
		// stocker le map dans un fichier :
		
	    /*File flic = new File(outputFile_oc);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(flic));
        oos.writeObject(maps_occurenec);
        oos.close();
        */
    }
	
	
	


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

        // ***************** (2)la frequence des termes dans un fichier *****************

        public float TermeFrequency(String terme, Map<String,Integer> map) {
            float d = 0;
            if(map!=null && map.containsKey(terme)!=false)
                d = map.get(terme)/(float)NbrTermes(map);
            return d;
        }

			
        // ***************** (3) la raret� des termes dans le Decument *****************

        public float InverseDocFrequency(String terme, Map<String,Map<String, Integer>> map){
            Set<Map.Entry<String, Map<String,Integer>>> KeyMap = map.entrySet();
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
            return (float) Math.log(map.size()/nbr);
        }
        
        
        
        public void Creation_Maps_TF_IDF(Map<String, Map<String, Integer>> maps_occurenec) throws FileNotFoundException, IOException {
        	
        	String terme;
        	Map<String, Integer> map = new HashMap<String,Integer>();
        	int l = mapTermes.size();
        	int c = maps_occurenec.size();
            TF = new float[l][c];
            
            int i=0;
        	Set<Map.Entry<String,Integer>> keyMap = mapTermes.entrySet();
            Iterator<Map.Entry<String,Integer>> itrMap = keyMap.iterator();
            while(itrMap.hasNext()){
                Map.Entry<String,Integer> entreeFile = (Map.Entry<String,Integer>)itrMap.next();
                terme = entreeFile.getKey();
                
                int j=0;
                Set<Map.Entry<String, Map<String, Integer>>> keyMaps = maps_occurenec.entrySet();
                Iterator<Map.Entry<String, Map<String, Integer>>> itrMaps = keyMaps.iterator();
                while(itrMaps.hasNext()){
                    Map.Entry<String, Map<String, Integer>> entreeFiles = (Map.Entry<String, Map<String, Integer>>)itrMaps.next();
                    map = entreeFiles.getValue();
                    TF[i][j] = TermeFrequency(terme, map);
                    j++;
                }
                i++;
            }
            
            
            IDF = new float[l];
            i=0;
            while(itrMap.hasNext()){
                Map.Entry<String,Integer> entreeFile = (Map.Entry<String,Integer>)itrMap.next();
                terme = entreeFile.getKey();
                IDF[i] = InverseDocFrequency(terme, maps_occurenec);
                i++;
            }
            
            
            TF_IDF = new float[l][c];
            for (i=0;i<l;i++)
            	for(int j=0;j<c;j++)
            		TF_IDF[i][j] = TF[i][j] * IDF[i];
        }
        
        
        
        
        
        
        public Map<String, Map<String, Integer>> createMapOccurenceRequets(String inputNewFiles) throws FileNotFoundException, IOException {
        	
        	Map<String, Map<String, Integer>> maps_occurenec_requets = new HashMap<String, Map<String, Integer>>();
        	
        	File droit= new File(inputNewFiles);
    		File[] dry = droit.listFiles();
    		//System.out.println("+++++++++++++++++++++"+dry.length);
    		for(int i=0; i<dry.length; i++) {
    			File[] files = dry[i].listFiles();
    			//System.out.println("----------------"+files.length);
    			for(int j = 0; j<files.length; j++) {
    				//System.out.println("+++++***********+++++++"+files[j].getName());
    				int c; 
    				StringBuffer sb = new StringBuffer();
    				try {
    				FileReader fr = new FileReader(files[j]);
    				while((c=fr.read())!=-1)
    					sb.append((char)c);

                    String textWithoutSW = Remover.removeStopWords(sb.toString());
    				IStemmer stemmer = null;
    				if(n==0)
    					stemmer = StemmerFactory.getLight10Implementation();
    				if(n==1)
    					stemmer = StemmerFactory.getKhojaImplementation();
    				//Stem the text using the specified stemmer
    				List<WordStemmerAnalysis> listResult = stemmer.stem(textWithoutSW);
    				
    				Map<String, Integer> results = new HashMap<String, Integer>();
    				for (WordStemmerAnalysis wsa : listResult) {
    					String stem = wsa.getListStemmerAnalysis().get(0).getMorpheme();
    					if (!results.containsKey(stem)) {
    			    		    if(stem != null && stem.length() >= 2)
    			   				results.put(stem,1);
    			  		}
    			        else {
    		   			    int k = results.get(stem);
    		   			    k++;
    		   			    results.put(stem, k);
    					}
    				}
    				maps_occurenec_requets.put(files[j].getAbsolutePath().replace('\\','/'),results);
                    fr.close();
                    }
    				catch(Exception e)
                    		{ e.printStackTrace(); }
    		    }
    	    }
    		return maps_occurenec_requets;
    	}
        
        
        
     
}
