package com.bittle.telegram;

import com.bittle.telegram.Main.MainClass;
import org.jetbrains.annotations.Contract;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;
import javax.imageio.ImageIO;

public class ImageModifier {

    public static BufferedImage mockImage(String str) {
        // 30 chars fit across image
        str = mockString(str);
        try {

            final BufferedImage image = ImageIO.read(new File(MainClass.directory.images_dir()+"sponge.jpg"));

            String[] splitString = com.bittle.telegram.Text.StringUtils.splitString(str, 30);
            int row = splitString.length;

            for (String aSplitString : splitString) {
                row--;

                drawOnImage(aSplitString, row, image);
            }

            return image;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void drawOnImage(String str, int rows, BufferedImage image) throws Exception {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(
                MainClass.directory.fonts_dir()+"impact.ttf")));

        Graphics g = image.getGraphics();
        Font f = new Font("impact", Font.PLAIN, 40);

        FontMetrics metrics = g.getFontMetrics();
        Rectangle rect = new Rectangle(0, 0, image.getWidth(), image.getHeight());

        g.setFont(f);
        g.setColor(Color.black);

        FontRenderContext frc =
                new FontRenderContext(null, true, true);

        Rectangle2D r2D = f.getStringBounds(str, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rX = (int) Math.round(r2D.getX());

        int a = (rect.width / 2) - (rWidth / 2) - rX;

        int x = rect.x + a;

        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        y = (int) (y + (y / 1.1));
        y -= (metrics.getHeight()) * (rows) * 2.8;

        // black outline with white inside
        int offset = 1;
        g.drawString(str, shift_west(x, offset), shift_north(y, offset));
        g.drawString(str, shift_west(x, offset), shift_south(y, offset));
        g.drawString(str, shift_east(x, offset), shift_north(y, offset));
        g.drawString(str, shift_east(x, offset), shift_south(y, offset));

        g.setColor(Color.white);
        g.drawString(str, x, y);
    }

    public static void printToDir(BufferedImage image, String name) {
        try {
            ImageIO.write(image, "png", new File(name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static InputStream bufferedImageToInputStream(BufferedImage img) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(img, "png", os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String mockString(String str) {
        char[] arr = str.toUpperCase().toCharArray();

        for (int x = 0; x < arr.length; x++) {
            if (new Random().nextBoolean())
                arr[x] = (arr[x] + "").toLowerCase().charAt(0);
        }

        String a = "";
        for (int x = 0; x < arr.length; x++) {
            a += arr[x];
        }
        return a;
    }


    @Contract(pure = true)
    private static int shift_north(int p, int d) {
        return p - d;
    }

    @Contract(pure = true)
    private static int shift_south(int p, int d) {
        return p + d;
    }

    @Contract(pure = true)
    private static int shift_east(int p, int d) {
        return p + d;
    }

    @Contract(pure = true)
    private static int shift_west(int p, int d) {
        return p - d;
    }
}