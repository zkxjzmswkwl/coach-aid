package software.carter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Main {

    public static void run() {
        Screen screen = new Screen();
        BufferedImage test = screen.testTimeStampCapture();
        String ocr = Tess.getInstance().readImage(test);
        System.out.println(ocr);

        try {
            ImageIO.write(test, "jpg", new File("out.jpg"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Thread.sleep(1000);
            run();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}