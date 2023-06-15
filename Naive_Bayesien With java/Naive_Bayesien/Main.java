package Naive_Bayesien;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Main {
    public Main() {
    }

    public static void main(String[] args) throws IOException {
        //Clear.clear();
        ArrayList<Path> folds = CrossValidation.split(5, CrossValidation.DATA_PATH);
        new Naive_Bayesien();
        new TermFrequency();
        int i = 0;
        Iterator var6 = folds.iterator();
        //System.out.println(folds);
        while(var6.hasNext()) {
            Path p = (Path)var6.next();
            Naive_Bayesien nb = new Naive_Bayesien();
            TermFrequency tf = new TermFrequency();
            System.out.println("---------Fold " + i++ + " ---------");
            File directory = new File(p.toString());
            File[] filesTrain = directory.listFiles();
            File[] filesTest = (File[])Arrays.copyOfRange(filesTrain, 0, 4 * filesTrain.length / 5);
            filesTrain = (File[])Arrays.copyOfRange(filesTrain, filesTrain.length - 4 * filesTrain.length / 5, filesTrain.length - 1);
            File[] var13 = filesTrain;
            int j = filesTrain.length;

            for(int var11 = 0; var11 < j; ++var11) {
                File file = var13[var11];
                Document d = new Document(file.getPath());
                d.load();
                tf.occurence(d);
            }

            System.out.println("Training");
            nb.training(tf.tfOccurence);
            Integer[] predictClass = new Integer[filesTest.length];
            Integer[] realClass = new Integer[filesTest.length];


            j = 0;
            System.out.println("Predict");
            File[] var16 = filesTest;
            int var15 = filesTest.length;


            for(int var21 = 0; var21 < var15; ++var21) {
                File file = var16[var21];
                Document d = new Document(file.getPath());
                d.load();
                predictClass[j] = nb.predict(d.words);
                realClass[j++] = Document.getClasse(file.getPath().toString());

            }


            System.out.println("Accuracy : " + Mesure.accuracy(realClass, predictClass) + "%");
            System.out.println("Precision : " + 100.0D * Mesure.precision(realClass, predictClass) + "%");
            System.out.println("Recall : " + 100.0D * Mesure.recall(realClass, predictClass) + "%");
            System.out.println("F1 mesure : " + Mesure.f1mesure(realClass, predictClass) + "%");
        }

    }
}
