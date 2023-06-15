package Naive_Bayesien;
import email._parseaddr$py;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Clear {
    static String OUTPUT_PATH = "C:\\Users\\Anass AlJarroudi\\Desktop\\eclipse\\Text Mining Project\\dataEN";

    public Clear() {
    }

   public static void clear_folders(String path) throws IOException {
        File directory = new File(path);
        File[] folders = directory.listFiles();
        File[] var6 = folders;
        int var5 = var6.length;

        for(int var4 = 1; var4 < var5; ++var4) {
            File folder = var6[var4];
            File[] files = folder.listFiles();
            File[] var11 = files;
            int var10 = files.length;

            for(int var9 = 0; var9 < var10; ++var9) {
                File file = var11[var9];
                Files.deleteIfExists(file.toPath());
            }

            Files.deleteIfExists(folder.toPath());
        }

    }

    public static void clear() throws IOException {
        clear_folders(OUTPUT_PATH);
    }

//    public static void main(String[] args) throws IOException {
//        File directory = new File("/Users/AvyaTiK/Desktop/M2/Text mining/Text Mining Project/dataEN/");
//        File[] folders = directory.listFiles();
//        File[] var6 = folders;
//        int var5 = var6.length;
//        for(int var4 = 1; var4 < var5; ++var4) {
//            File folder = var6[var4];
//            File[] files = folder.listFiles();
//            File[] var11 = files;
////            int var10 = files.length;
//            System.out.println(files);
//        }
//
//    }
}
