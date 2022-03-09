package software.carter;

import java.awt.image.BufferedImage;

public class Watch implements Runnable {

    @Override
    public void run() {
        Screen screen = new Screen();
        Log log = new Log();

        BufferedImage test;
        String ocr;

        for (;;)
        {
            test = screen.testChatCapture();
            ocr = Tess.getInstance().readImage(test);
            if (ocr.length() > 1) {
                log.processInput(ocr);
            }
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}