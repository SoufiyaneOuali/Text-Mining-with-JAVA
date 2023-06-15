package Naive_Bayesien;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Corpus {
    public HashMap<String, HashMap<String, Double>> tf_idf = new HashMap();
    public ArrayList<Document> docs = new ArrayList();
    public int size = 0;

    public Corpus(String path) throws IOException {
        File directory = new File(path);
        File[] folders = directory.listFiles();
        File[] var7 = folders;
        int var6 = folders.length;

        for(int var5 = 0; var5 < var6; ++var5) {
            File doc = var7[var5];
            Document d = new Document(doc.getPath());
            d.load();
            this.add(d);
        }

    }

    public void add(Document d) {
        this.docs.add(d);
        this.tf_idf.put(d.path, new HashMap());
        ++this.size;
    }


}
