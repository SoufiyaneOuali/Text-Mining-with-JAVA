
package Naive_Bayesien;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Mesure {

    public static String  accuracy(Integer[] v,Integer[] p){
        DecimalFormat df = new DecimalFormat("#.00");

        double score = 0.0;

        for (int i=0;i<v.length;i++) {

            if(v[i].equals(p[i])) score++;

        }

        String s = String.format( "%.2f", (score/v.length)*100 );
        return s;

    }

    public static double  precision(Integer[] v,Integer[] p){

        Set<Integer> classes = new HashSet<Integer>(Arrays.asList(v));
        double score = 0.0;

        for(Integer i : classes){
            double t = 0;
            double f = 0;

            for (int j = 0; j < v.length; j++) {
                if(v[j].equals(i) && p[j].equals(i)){
                    t++;

                }
                else if(p[j].equals(i)){
                    f++;

                }

            }
            if(f!=0 && t!=0){
            score += t/(f+t);
            }


        }

        score = score/classes.size();
        return score;
    }

    public static double  recall(Integer[] v,Integer[] p){

        Set<Integer> classes = new HashSet<Integer>(Arrays.asList(v));
        double score = 0.0;

        for(Integer i : classes){
            double t = 0;
            double f = 0;

            for (int j = 0; j < v.length; j++) {

                if(v[j] == i && p[j] == i){
                    t++;


                }else if(v[j] == i){
                    f++;

                }
            }
            score += t/(f+t);
        }

        score = score/classes.size();

        return score;
    }

    public static double  f1mesure(Integer[] v,Integer[] p){

        return  100*((2*precision(v,p)*recall(v,p))/(precision(v,p)+recall(v,p))) ;
    }


}