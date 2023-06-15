package Naive_Bayesien;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TermFrequency {
    HashMap<String, HashMap<String, Integer>> tfOccurence = new HashMap();

    public TermFrequency() {
    }

    public void occurence(Document doc) {
        HashMap<String, Integer> tf = new HashMap();
        Set<String> unique = new HashSet(doc.words);
        Iterator var5 = unique.iterator();

        while(var5.hasNext()) {
            String key = (String)var5.next();
            if (!key.isEmpty()) {
                int occ = Collections.frequency(doc.words, key);
                tf.put(key, occ);
            }
        }

        this.tfOccurence.put(doc.path, tf);
    }
}
