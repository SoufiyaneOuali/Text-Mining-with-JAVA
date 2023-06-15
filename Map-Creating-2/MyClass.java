import java.io.IOException;
import java.io.FileNotFoundException;



public class MyClass {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		
		
		String inputFile = "F:\\ANASS HOUDOU\\1 Cours\\3 Master\\S3\\7 Text et Web  Mining\\TP\\TP1\\Textes";
		String outputFile_oc = "F:\\ANASS HOUDOU\\1 Cours\\3 Master\\S3\\7 Text et Web  Mining\\TP\\TP1\\Textes\\outputFile_oc.txt";
		String outputFile_TF_IDF = "F:\\ANASS HOUDOU\\1 Cours\\3 Master\\S3\\7 Text et Web  Mining\\TP\\TP1\\Textes\\outputFile_TF_IDF.txt";
		int n = 1;
		
		Creation_Maps obj = new Creation_Maps(inputFile, outputFile_oc, outputFile_TF_IDF, n);
		
		/*
		obj.maps_occurenec;
		obj.IDF;
		obj.TF;
		obj.TF_IDF;
		*/
		
		
		System.out.println("");
		System.out.println("Les maps :");
		System.out.println(obj.maps_occurenec);
		
		System.out.println("");
		System.out.println("La matrice TF[i]");
		for (int i=0;i<obj.TF.length;i++) {
			for (int j=0;j<obj.TF[i].length;j++) {
				System.out.print(obj.TF[i][j]);
		        System.out.print("\t\t");
			}
			System.out.println("");
		}
	
	    System.out.println("");
		System.out.println("La vecteur IDF[i]");
		for (int i=0;i<obj.IDF.length;i++) {
				System.out.print(obj.IDF[i]);
		        System.out.print("\t\t");
			}
		
		System.out.println("");
		System.out.println("La matrice TF_IDF[i,j]");
		for (int i=0;i<obj.TF_IDF.length;i++) {
			for (int j=0;j<obj.TF_IDF[i].length;j++) {
				System.out.print(obj.TF_IDF[i][j]);
		        System.out.print("\t\t");
			}
			System.out.println("");
		}
		
		
	}
	

}