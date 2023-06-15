package Naive_Bayesien;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import static Naive_Bayesien.Clear.OUTPUT_PATH;

public class CrossValidation {
    static String DATA_PATH = "C:\\Users\\Anass AlJarroudi\\Desktop\\eclipse\\Text Mining Project\\20_newsgroups";

    public CrossValidation() {
    }

    public static ArrayList<Path> split(int n, String path) throws IOException {
        ArrayList<Path> folds = new ArrayList();
        File directory = new File(path);
        File[] folders = directory.listFiles();
        int i = 1;
        File[] var9 = folders;
        int var8 = folders.length;
       // System.out.println(var8);
        for(int var7 = 1; var7 < var8; ++var7) {
            File folder = var9[var7];
            File[] fList = folder.listFiles();
            String chemin = folder.getPath();
            int j = n;
            File[] var16 = fList;
            int var15 = fList.length;
            //System.out.println(var15);
            for(int var14 = 0; var14 < var15; ++var14) {
                File file = var16[var14];
                String s = "fold" + j % n;
                Path h = create_fold(chemin, s, file, i);

                if (!folds.contains(h)) {
                    folds.add(h);
                }

                ++j;
            }

            ++i;
        }

        return folds;
    }

    public static Path create_fold(String origin, String destination,File file,int classe) throws IOException {
        Path path = Paths.get(OUTPUT_PATH+destination);
        if (!Files.exists(path)) {

            try {
                Files.createDirectories(path);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        File from = new File(file.getPath());
        File to = new File(OUTPUT_PATH+destination+"/"+file.getName()+"_"+classe);

        FileUtils.copyFile(from, to);
        return path;
    }

//    public static void main(String[] args) throws IOException {
//        System.out.println(split(3,"/Users/AvyaTiK/Desktop/M2/Text mining/Text Mining Project/20_newsgroups/"));
//    }
}