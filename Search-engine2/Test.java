package document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import safar.basic.morphology.stemmer.factory.StemmerFactory;
import safar.basic.morphology.stemmer.interfaces.IStemmer;
import safar.basic.morphology.stemmer.model.WordStemmerAnalysis;
import safar.util.remover.Remover;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n=1;
		String textWithoutSW = Remover.removeStopWords("سوف يقتلهم");
		System.out.println(textWithoutSW);
		IStemmer stemmer = null;
		if(n==0)
		 stemmer = StemmerFactory.getLight10Implementation();
		if(n==1)
		 stemmer = StemmerFactory.getKhojaImplementation();
        // Stem the text using the specified stemmer
        List<WordStemmerAnalysis> listResult = stemmer.stem(textWithoutSW);
        Map<String, Integer> results = new HashMap();

  	  for (WordStemmerAnalysis wsa : listResult) {
  	    String stem = wsa.getListStemmerAnalysis().get(0).getMorpheme();
  	    if (!results.containsKey(stem)) {
  	      if (stem != null && stem.length() >= 2) {
  	        results.put(stem, 1);
  	        System.out.println(stem);
  	      }
  	    } else {
  	      int k = results.get(stem);
  	      k++;
  	      results.put(stem, k);
  	    System.out.println(stem);
  	    }
  	  }
	}

}
