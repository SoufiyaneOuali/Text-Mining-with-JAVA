package map_Creating;
import java.io.IOException;
import java.util.Map;
import java.io.FileNotFoundException;



public class MyClass {

	public static void main(String[] args) throws FileNotFoundException, IOException
	{
		
		System.out.println("\n1 : ***************************************************************************************");
		String inputFile = "F:\\ANASS HOUDOU\\1 Cours\\3 Master\\S3\\7 Text et Web  Mining\\TP\\TP3\\Textes";
		int n = 1;
		
		Creation_Maps obj = new Creation_Maps(inputFile, n);
		
		System.out.println("Les maps :");
		System.out.println(obj.maps_occurenec);
		


		
		System.out.println("\n2 : ***************************************************************************************");
		int len=80; // "C:\\f.txt" len=length("C:\\")=4
		
		Bayes bayes = new Bayes(obj.maps_occurenec, obj.mapTermes, len, 0.6); // 60% de train
		
		System.out.println("Entrainement de model :");
		System.out.println("P(+) = "+bayes.probClassPos);
		System.out.println("P(-) = "+bayes.probClassNeg);
		System.out.println("P(terms/+) = "+bayes.probTermesPos);
		System.out.println("P(terms/-) = "+bayes.probTermesNeg);
		System.out.println("La prediction de test : "+bayes.predict_test);
		System.out.println("Le score de test : "+bayes.score);
		
		
		System.out.println("\n3 : ***************************************************************************************");
		String inputNewFiles = "F:\\ANASS HOUDOU\\1 Cours\\3 Master\\S3\\7 Text et Web  Mining\\TP\\TP3\\NewTextes";
		
		Map<String, Map<String, Integer>> maps_occurenec_requets = obj.createMapOccurenceRequets(inputNewFiles);
		System.out.println("New requete :");
		System.out.println(maps_occurenec_requets);
		
		Map<String, String> map_predict = bayes.prediction(maps_occurenec_requets);
		System.out.println("La prédiction de nouvelle requete :");
		System.out.println(map_predict);
		
		
		
	}
	

}
