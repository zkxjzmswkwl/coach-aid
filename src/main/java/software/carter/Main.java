package software.carter;

public class Main {
    public static void main(String[] args) {
        // String a = "[DadRimmer420]: I am gay so very gay I am very very gay holy [DadDicker13]: me too";
        // int t = a.indexOf("]:");
        // if (t > -1 ) {
        //     int c = a.indexOf("]:", t+2);
        //     System.out.println(a.substring(c + 2, a.length()));
        // }
        Watch watch = new Watch();
        Thread watchThread = new Thread(watch);
        watchThread.start();
    }
}