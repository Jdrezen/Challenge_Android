package com.example.graphicman;

public class Chrono {
    private long tempsDepart=0;
    private long tempsFin=0;
    private long duree=0;

    public void start()
    {
        tempsDepart=System.currentTimeMillis();
        tempsFin=0;
        duree=0;
    }


    public void stop()
    {
        if(tempsDepart==0){
            return;
        }

        tempsFin=System.currentTimeMillis();
        duree=(tempsFin-tempsDepart);
        tempsDepart=0;
        tempsFin=0;
    }

    public long getTime(){
        return (System.currentTimeMillis() - tempsDepart) / 100;
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
}
