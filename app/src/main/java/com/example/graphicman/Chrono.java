package com.example.graphicman;

public class Chrono {
    private long tempsDepart=0;
    private long tempsFin=0;
    private long duree=0;

    public void start(int milliSecondes) {
        tempsDepart = System.currentTimeMillis();
        tempsFin = milliSecondes;
        duree = 0;
    }

    public long getMilliTime(){
        long now = System.currentTimeMillis();

        return (now - tempsDepart);
    }

    public boolean isFinit() {
        long now = System.currentTimeMillis();

        return tempsFin - (now - tempsDepart) <= 0;
    }

    public String displayTime() {
        long now = System.currentTimeMillis();
        long time = tempsFin - (now - tempsDepart);
        return time/1000 + "." + time%1000/100 + " s";
    }

    public static String getDureeTxt(long input) {
        int m = (int) ((input %3600) / 60);
        int s = (int) (input % 60);

        String res = "";

        if (m > 0) {
            res += m + " min ";
        }
        if (s >= 0) {
            res += s + " s";
        }

        return res;
    }

    // Décompte start paramètre décrémente isPassed 13.3s
}
