import safar.basic.morphology.stemmer.factory.StemmerFactory;
import safar.basic.morphology.stemmer.interfaces.IStemmer;
import safar.basic.morphology.stemmer.model.WordStemmerAnalysis;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class TFIDF {
    //Variable global pour enregistrer le Corpus
    static HashMap<String, HashMap<String, Integer>> Doc=new HashMap<>();

    //Calcul TF
    public static HashMap<String, Double> tf(HashMap<String, Integer> doc) {
        HashMap<String, Double>res=new HashMap();
        double r=0;
            for (Map.Entry<String, Integer> e : doc.entrySet()){
                r = e.getValue();
                res.put(e.getKey(), r/doc.size());
            }
        return res;
    }
    //Calcul IDF
    public static HashMap<String, Double> idf(HashMap<String, Integer> doc) {
        HashMap<String, Double>res=new HashMap();
        double n = 0;
        for (Map.Entry<String, Integer> e : doc.entrySet()) {
            n=0;
            for (HashMap<String, Integer> value : Doc.values() ) {
                for (String word : value.keySet()) {
                    if (e.getKey().equalsIgnoreCase(word)) {
                        n++;
                        break;
                    }
                }

            }
            res.put(e.getKey(), Math.log(Doc.size() / n));
        }
        return res;
    }
    //Calcul TF_IDF
    public static HashMap<String, Double> tf_idf(HashMap<String, Integer> doc){
        HashMap<String, Double>res=new HashMap();
        HashMap<String, Double> r = tf(doc);
        HashMap<String, Double> v = idf(doc);
        if (!r.isEmpty()){
        for (Map.Entry<String, Integer> e : doc.entrySet()) {
            res.put(e.getKey(), r.get(e.getKey())*v.get(e.getKey()));
        }
        }
        return res;
    }
    //Creation de Corpus
    public static HashMap<String, HashMap<String,Integer>>Corpus(String path) throws IOException {
        File Doc = new File(path);
        HashMap<String, HashMap<String, Integer>> Docs = new HashMap<>();
        for (File f : Doc.listFiles()) {
            ExtractKeywords e = new ExtractKeywords(f);
            Docs.put(f.getName(), transfer(e.generateKeywords()));
        }
        return Docs;
    }
    //Creation de document
    public static HashMap<String, Integer> doc(String path) throws FileNotFoundException {
            File f = new File(path);
            ExtractKeywords e=new ExtractKeywords(f);
            ArrayList<String> documents = e.generateKeywords();
            HashMap<String, Integer> tab= transfer(documents);
            return tab;
        }
    //Creation la requete
    public static HashMap<String, Integer> query(String requete) throws FileNotFoundException {
        ExtractKeywords e=new ExtractKeywords(requete);
        ArrayList<String> documents = e.generateKeywords();
        HashMap<String, Integer> tab= transfer(documents);
        return tab;
    }
    //Ordonner les maps
    public static LinkedHashMap<String, Double> sort(HashMap<String, Double> TempMap) {

        List<String> mapKeys = new ArrayList<>(TempMap.keySet());
        List<Double> mapValues = new ArrayList<>(TempMap.values());
        Collections.sort(mapValues);
        Collections.reverse(mapValues);

        LinkedHashMap<String, Double> sortedMap =  new LinkedHashMap<>();

        Iterator<Double> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Double val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                Double comp1 = TempMap.get(key);
                Double comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }
    //Afficher les result
    public static void  affiche(HashMap<String,Integer>document){
        HashMap<String,Double> d=tf_idf(document);
        HashMap<String,Double> temp=new HashMap<>();
        for (String value : Doc.keySet() ) {
            temp.put(value,Cos.cosSim(d,tf_idf(Doc.get(value))));
        }
        HashMap<String, Double> result = sort(temp);
        System.out.println("\t******************************************");

        for (Map.Entry<String, Double> entry : result.entrySet()) {
            // les similarites egaux a 0 ne s'affichent pas.
            if (entry.getValue() == 0) break;
            System.out.println("La ressemblance avec le document  "+entry.getKey()+ "   :\n \t" + entry.getValue());
        }
        System.out.println("\t******************************************");
    }
    //La fonction de Stemmer
    public static ArrayList<String> strem(String t) {
        IStemmer strm = StemmerFactory.getKhojaImplementation();
        List<WordStemmerAnalysis> s = strm.stem(t);
        ListIterator<WordStemmerAnalysis> it = s.listIterator();

        ArrayList<String> terms = new ArrayList();
        while (it.hasNext()) {
            WordStemmerAnalysis word = it.next();
            if (!word.getListStemmerAnalysis().get(0).getType().equals("STOPWORD")&&(!word.getListStemmerAnalysis().get(0).getType().equals(null))) {
                terms.add(word.getListStemmerAnalysis().get(0).getMorpheme());
            }
        }
        return terms;
    }
    //Faire tout les modifications sur la map
    public static HashMap<String, Integer> transfer(ArrayList<String> documents) {
        String text ="";
        for (String s : documents)
        {
            text += s + "\t";
        }
        IStemmer stemmer = StemmerFactory.getKhojaImplementation();
        List<WordStemmerAnalysis> s = stemmer.stem(text);
        ListIterator<WordStemmerAnalysis> it = s.listIterator();
        HashMap<String, Integer> tab= new HashMap<String, Integer>();
        while (it.hasNext()) {
            WordStemmerAnalysis word = it.next();

            if ((word.getListStemmerAnalysis().get(0).getType() != "STOPWORD")&&(!word.getListStemmerAnalysis().get(0).getType().equals(null))) {
                Integer count = tab.get(word.getListStemmerAnalysis().get(0).getMorpheme());
                if (count != null)
                    tab.put(word.getListStemmerAnalysis().get(0).getMorpheme(), count + 1);
                else
                    tab.put(word.getListStemmerAnalysis().get(0).getMorpheme(), 1);
            }
        }

        return tab;
    }


}