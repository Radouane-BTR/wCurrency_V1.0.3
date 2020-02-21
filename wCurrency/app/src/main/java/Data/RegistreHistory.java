package Data;

import java.util.ArrayList;

import model.History;

public class RegistreHistory {

    private static ArrayList<History> listHistory = new ArrayList<>();

    public static void addHistory(History h) {
        listHistory.add(h);
    }

    public static ArrayList<History> getListe() {
        return listHistory ;
    }

    public static void clearListe() {
        listHistory.clear();
    }
}
