package Naive_Bayesien;

import java.util.*;

public class Naive_Bayesien {
    HashMap<Integer, HashMap<String, Double>> proba_word = new HashMap();
    HashMap<Integer, Double> proba_classe = new HashMap();

    public Naive_Bayesien() {
    }

    public void training(HashMap<String, HashMap<String, Integer>> var1) {
        Set<String> mots = new HashSet<String>();
        int num_doc=0;
        for(String classe: var1.keySet()) {
            num_doc ++;
            int cl = Document.getClasse(classe);
            if(!proba_word.containsKey(cl)) {
                HashMap<String,Double> x = new HashMap<String,Double>();
                this.proba_word.put(cl, x);
                this.proba_classe.put(cl,0.0);
            }
            this.proba_classe.replace(cl, proba_classe.get(cl)+1);
            for(String mot : var1.get(classe).keySet()) {
                if(!proba_word.get(cl).containsKey(mot)) {
                    proba_word.get(cl).put(mot, 1.0);
                }
                proba_word.get(cl).replace(mot, proba_word.get(cl).get(mot) + var1.get(classe).get(mot));
                mots.add(mot);
            }

        }
    }

    public int predict(ArrayList<String> words) {
        double max = -1.0D;
        int classe = 0;
        Iterator var7 = this.proba_word.keySet().iterator();

        while(var7.hasNext()) {
            int cl = (Integer)var7.next();
            double proba = 1.0D;
            proba *= (Double)this.proba_classe.get(cl);
            Iterator var11 = words.iterator();

            while(var11.hasNext()) {
                String word = (String)var11.next();
                if (((HashMap)this.proba_word.get(cl)).containsKey(word)) {
                    proba *= (Double)((HashMap)this.proba_word.get(cl)).get(word);
                }
            }

            if (proba >= max) {
                max = proba;
                classe = cl;
            }
        }

        return classe;
    }
}

