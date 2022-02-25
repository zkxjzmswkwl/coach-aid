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

    public Screen() {
        try {
            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }

        timeStampSector = new Sector(2111, 1177, 121, 39, 600, 100);
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
    private static BufferedImage threshold(BufferedImage image, int r, int g, int b) {
        int range = 45;

        for (int x = 0; x <= image.getWidth() - 1; x++)
        {
            for (int y = 0; y <= image.getHeight() - 1; y++)
            {
                int pixel = image.getRGB(x, y);
                Color color = new Color(pixel, true);

                // If the current pixel matches, set it to black.
                // If not, set it to green. This produces **great** ocr results.
                if (color.getRed() >= r - range && color.getRed() <= r + range &&
                    color.getGreen() >= g - range && color.getGreen() <= g + range &&
                    color.getBlue() >= b - range && color.getBlue() <= b + range)
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
}