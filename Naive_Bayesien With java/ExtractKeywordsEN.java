import org.tartarus.snowball.ext.PorterStemmer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class ExtractKeywordsEN {
    public static String stop_words;

    static {
        try {
            stop_words = create_stop_words("/Users/AvyaTiK/Desktop/M2/Text mining/Text Mining Project/libs/stopwords.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String create_stop_words(String filepath) throws IOException {
        File files = new File(filepath);
        List<String> tampon = Files.readAllLines(Paths.get(filepath));
        String stop_words = String.join(" ", tampon);
        return stop_words;
    }

    static String convertToString(File file) throws FileNotFoundException {
        Scanner in;
        in = new Scanner(file);
        StringBuilder content = new StringBuilder("");
        for(; in.hasNext() ; content.append(in.nextLine()));
        return content.toString();
    }

    public static HashMap<String, HashMap<String, Integer>> create_Corpus(String filepath) throws IOException {
        HashMap<String, HashMap<String, Integer>> Corpus = new HashMap<>();
        HashMap<String, Integer> origin_words = new HashMap<>();
        File files = new File(filepath);
        for (File f : files.listFiles()) {
//            List<String> tampon = Files.readAllLines(Paths.get(f.getAbsolutePath()));
//            String text = String.join(" ", tampon);
//
            String text=convertToString(f);
            text = text.replaceAll("[^\\w\\s]", "");
            text = text.replaceAll("[0-9]","");
            String[] words = text.split(" ");
            for (String word : words) {
                if (!stop_words.toLowerCase().contains(word.toLowerCase()) && (word.length()>2)) {
                    PorterStemmer stemmer = new PorterStemmer();
                    stemmer.setCurrent(word);
                    stemmer.stem();
                    String stemmed_word = stemmer.getCurrent();
                    int counter = 0;
                    if (!origin_words.containsKey(stemmed_word)) {
                        origin_words.put(stemmed_word, counter + 1);
                    } else {
                        origin_words.replace(stemmed_word, origin_words.get(stemmed_word) + 1);
                    }
                }
                Corpus.put(f.getName(), origin_words);

            }
        }
        return Corpus;
    }

    public static HashMap<String, Integer> create_doc(String docpath) throws IOException {
        HashMap<String, Integer> Doc = new HashMap<>();
        List<String> tampon = Files.readAllLines(Paths.get(docpath));
        String text = String.join(" ", tampon);
        text = text.replaceAll("[^\\w\\s]", "");
        text = text.replaceAll("[0-9]","");
        String[] words = text.split(" ");
        for (String word : words) {
            if (!stop_words.toLowerCase().contains(word.toLowerCase()) && (word.length()>2)) {
                PorterStemmer stemmer = new PorterStemmer();
                stemmer.setCurrent(word);
                stemmer.stem();
                String stemmed_word = stemmer.getCurrent();
                int counter = 0;
                if (!Doc.containsKey(stemmed_word)) {
                    Doc.put(stemmed_word, counter + 1);
                } else {
                    Doc.replace(stemmed_word, Doc.get(stemmed_word) + 1);
                }

            }
        }
        return Doc;
    }


}

