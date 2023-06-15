package map_Creating;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Mainn {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

			//-----------------

			String inputFile = "D:\\my study\\MA\\BdSas_S3\\TEXT-MINING\\TP3_Classification_Supérviser\\TP3_Anass Houdou\\TP3_Anass Houdou\\Textes";
			
			//--------------
			FinalClass Obj_Map= new FinalClass(inputFile);
			
			//--------------
			System.out.println("\n map de document 1 \n ");
			System.out.println(Obj_Map.Corpus);
			
			//--------------
			System.out.println(Obj_Map.mapTermes);
			
			//--------------

			System.out.println("\n2 : ***************************************************************************************");
			int len=80;
			
			// 75% taux de train train
			Bayes bayes = new Bayes(Obj_Map.Corpus, Obj_Map.mapTermes, len, 0.75); 
			
			System.out.println("Training th model :");
			
			// the probabilty of   classes postif
			System.out.println("P(+) = ");
			System.out.println(" \n "+bayes.probClassPos);
			
			// the probabilty of   classes Negatif
			System.out.println("P(-) = ");
			System.out.println(" \n "+bayes.probClassNeg);
			
			//P(Word/class + )
			System.out.println("P(terms/+) = ");
			System.out.println("\n "+bayes.probTermesPos);
			
			//P(Word/class - )
			System.out.println("P(terms/-) = ");
			System.out.println("\n "+bayes.probTermesNeg);
			
			//arg max II P(class(+,-)/word[k])
			System.out.println("La prediction de test : ");
			System.out.println("\n"+bayes.predict_test);
			
			System.out.println("Le score de test : ");
			System.out.println("\n"+bayes.score);
			
			
			System.out.println("\n -----------------------------------------------------------");
			System.out.println("La prediction sur  nouveau data  : ");
			
			String inputNewFiles ="D:\\my study\\MA\\BdSas_S3\\TEXT-MINING\\TP3_Classification_Supérviser\\TP3_Anass Houdou\\TP3_Anass Houdou\\NewTextes";
			Map<String, Map<String, Integer>> maps_occurenec_requets = Obj_Map.createMapOccurenceRequets(inputNewFiles);
			System.out.println("New requete :");
			System.out.println(maps_occurenec_requets);
			
			Map<String, String> map_predict = bayes.prediction(maps_occurenec_requets);
			System.out.println("La prédiction de nouvelle requete :");
			System.out.println(map_predict);
			
			
	}

}
