import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cos {
    // La fonction qui calcul la similarité entre deux documents
    public static double cosSim(Map<String, Double> query, Map<String, Double> doc){

        HashMap<String, Double> temp = new HashMap();
        for (String S: query.keySet()) {
            if (doc.containsKey(S)) {
                temp.put(S, query.get(S));
            }
            else {
                temp.put(S, 0.0);
            }
        }

        List<Double> data= new ArrayList<>(temp.values());
        List<Double> requete= new ArrayList<>(query.values());

        if(data.isEmpty() || requete.isEmpty()){
            return 0.0;
        }
        Double nor_temp = N(data);
        Double nor_requete = N(requete);

        double sum = 0;
        for (int i=0; i<data.size(); i++) {
            sum += data.get(i)*requete.get(i);
        }
        if(nor_requete==0 || nor_temp==0) return 0;
        return sum / (nor_temp * nor_requete);
    }
    // La fonction qui calcul la norm
    public static double N(List<Double> doc){

        Double nortemp = 0.0;
        for (int i = 0; i < doc.size(); i++) {

            nortemp += doc.get(i)*doc.get(i);

        }
        return Math.sqrt(nortemp);
    }

}

