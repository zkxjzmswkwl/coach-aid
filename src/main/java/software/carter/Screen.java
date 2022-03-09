package software.carter;

import java.awt.Robot;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * This serves more as a proof of concept than anything else in its' current state.
 * Notably, the inclusion of the `Sector` record in this class is hacky.
 * If I continue this project I promise I will fix this :)
 */

public class Screen {
    public record Sector(int x, int y, int width, int height, int scaledW, int scaledH) {}
    private Robot robot;
    private Sector timeStampSector;
    private Sector chatSector;
    private static final int THRESHOLD_RANGE = 25;

    public Screen() {
        try {
            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }

        timeStampSector = new Sector(4240, 1176, 112, 38, 600, 100);
        chatSector = new Sector(2235, 625, 581, 290, 600, 300);
    }

    private BufferedImage screenshot(Sector s) {
        Rectangle sectRect = new Rectangle(s.x, s.y, s.width, s.height);
        return this.robot.createScreenCapture(sectRect);
    }

    /**
     * Threshold out all pixels in image that do not match the given r,g,b values.
     * @param image 
     * @param r
     * @param g
     * @param b
     * @return 
     *  Matching pixels Black, rest Green.
     */
    // TODO: Replace int r,g,b with Color c
    private static BufferedImage threshold(BufferedImage image, int r, int g, int b) {
        int pixel;
        Color color;

        for (int x = 0; x <= image.getWidth() - 1; x++)
        {
            for (int y = 0; y <= image.getHeight() - 1; y++)
            {
                pixel = image.getRGB(x, y);
                color = new Color(pixel, true);

                // If the current pixel matches, set it to black.
                // If not, set it to green. This produces **great** ocr results.
                if (color.getRed() >= r - THRESHOLD_RANGE && color.getRed() <= r + THRESHOLD_RANGE &&
                    color.getGreen() >= g - THRESHOLD_RANGE && color.getGreen() <= g + THRESHOLD_RANGE &&
                    color.getBlue() >= b - THRESHOLD_RANGE && color.getBlue() <= b + THRESHOLD_RANGE)
                {
                    image.setRGB(x, y, 0x000000);
                } else {
                    image.setRGB(x, y, 0x00FF00);
                }
            }
        }
        return image;
    }
    
    public BufferedImage testTimeStampCapture() {
        return threshold(screenshot(timeStampSector), 255, 255, 255);
    }

    public BufferedImage testChatCapture() {
        return threshold(screenshot(chatSector), 249, 149, 67);
    }
}