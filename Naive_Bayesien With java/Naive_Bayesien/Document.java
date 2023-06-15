package Naive_Bayesien;
import org.tartarus.snowball.ext.PorterStemmer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Document {
    public String path;
    public double norm;
    int cluster;
    int classe;
    //String classe;
    ArrayList<String> words;
    //static final ArrayList<String> stopwords = load("C:\\Users\\Anass AlJarroudi\\Desktop\\eclipse\\Text Mining Project\\libs\\stopwords.txt");
    public static String stopwords;
    static {
        try {
            stopwords = create_stop_words("/Users/Anass AlJarroudi/Desktop/eclipse/Text Mining Project/libs/stopwords.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Document(int cluster) {
        this.cluster = cluster;
    }

    public Document(Document doc) {
    }

    public Document(String path) {
        this.path = path;
        this.words = new ArrayList();
        this.cluster = -1;
    }
    public static String create_stop_words(String filepath) throws IOException {
        File files = new File(filepath);
        List<String> tampon = Files.readAllLines(Paths.get(filepath));
        String stop_words = String.join(" ", tampon);
        return stop_words;
    }
    public static int getClasse(String doc) {
        String[] x = doc.split("_");
        return Integer.parseInt(x[1]);
        //return x[1];
    }

    public String stem(String var1) {
        String stemmed_word="";
        if (!stopwords.toLowerCase().contains(var1) && (var1.length()>2)) {
            PorterStemmer stemmer = new PorterStemmer();
            stemmer.setCurrent(var1);
            stemmer.stem();
            stemmed_word = stemmer.getCurrent();}
        return stemmed_word;
    }

    public void load() {
        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(this.path), "utf-8"));
        } catch (UnsupportedEncodingException var11) {
            System.out.println("Encoding error !");
        } catch (FileNotFoundException var12) {
            System.out.println("File not Found !");
        }

        String text = "";

        try {
            String line;
            String[] result;
            while((line = in.readLine()) != null) {
                result = line.replaceAll("[^\\w\\s]", " ").split("\\s");
                line = "";
                String[] var8 = result;
                int var7 = result.length;

                for(int var6 = 0; var6 < var7; ++var6) {
                    String word = var8[var6];
                    String w = word.toLowerCase();
                    //
                    String stemmed = this.stem(w);
                    if (!stemmed.isEmpty()) {
                        line = line + stemmed + " ";
                    }
                }

                text = text + " " + line;
            }

            result = text.split("\\s");
            ArrayList<String> un_words = new ArrayList(Arrays.asList(result));
            Iterator var16 = un_words.iterator();

            while(var16.hasNext()) {
                String word = (String)var16.next();
                String cleaned = word.replace(" ", "");
                if (!cleaned.isEmpty()) {
                    this.words.add(cleaned);
                }
            }

            this.classe = getClasse(this.path);
        } catch (IOException var13) {
            System.out.println("I/O error");
        }

    }

    public static ArrayList<String> load(String path) {
        ArrayList<String> arr = new ArrayList();
        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf-8"));
        } catch (UnsupportedEncodingException var6) {
            System.out.println("Encoding error !");
        } catch (FileNotFoundException var7) {
            System.out.println("File not Found !");
        }

        String var4 = "";

        try {
            String line;
            while((line = in.readLine()) != null) {
                arr.add(line);
            }

            return arr;
        } catch (IOException var8) {
            System.out.println("I/O error");
            return arr;
        }
    }

//    public static void main(String[] args) {
//        Document d =new Document("data");
//        System.out.println(d.stem("goodmorning"));
//    }
}
