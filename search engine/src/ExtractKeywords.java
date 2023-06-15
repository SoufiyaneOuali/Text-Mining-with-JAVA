import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ExtractKeywords {

    private String document;
    private Scanner in;

    public ExtractKeywords(String request) throws FileNotFoundException {
        //File file = new File(path);
        document = request;
    }
    public ExtractKeywords(File file) throws FileNotFoundException {
        document = convertToString(file);
    }

    String convertToString(File file) throws FileNotFoundException {
        in = new Scanner(file);
        StringBuilder content = new StringBuilder("");
        for(; in.hasNext() ; content.append(in.nextLine()));
        return content.toString();
    }

    ArrayList<String> generateKeywords() {
        document = document.replaceAll("[^\\p{InARABIC} ]", "");
        ArrayList<String> keywords = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(document, " ");
        for(; st.hasMoreTokens(); keywords.add(st.nextToken().trim()));
        return keywords;
    }
}