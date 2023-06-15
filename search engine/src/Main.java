
import java.io.IOException;
import java.util.*;

public class Main {

        public static void main (String[]args) throws IOException {
                //Creation d'un document
                //HashMap<String,Integer>document= TFIDF.doc("data/Test.txt");
                //Creation d'un requete text
                HashMap<String,Integer>document= TFIDF.query("وفق الإجراءات  المقررة  بأحكام مخططات توجيه");
                //Creation du corpus
                TFIDF.Doc= TFIDF.Corpus("data/");
                //Chercher la simulation d'un doc avec le Corpus
                TFIDF.affiche(document);
                //Chercher la simulation d'un chaine de caractère avec le Corpus
                //TFIDF.affiche(document);






        }


}
