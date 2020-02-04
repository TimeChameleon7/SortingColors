import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

public class Main {
    private static Graphics graphics;
    private static Color[] colors;
    private static JFrame frame;
    private static long start;

    public static void main(String[] args) {
        randomColors();
        setUpFrame(colors.length);
        repaintColors();
        selectionSort();

        randomColors();
        repaintColors();
        insertionSort();

        randomColors();
        repaintColors();
        bubbleSort();
    }

    private static void randomColors() {
        Random r = new Random();
        final int length = 1000;
        final int lengthP1 = length + 1;
        colors = new Color[length];
        for (int i = 0; i < length; i++) {
            colors[i] = new Color(Color.HSBtoRGB(r.nextInt(lengthP1) / (float) length, 1, 1));
        }
    }

    private static void setUpFrame(int width) {
        frame = new JFrame();
        BufferedImage image = new BufferedImage(width, 100, BufferedImage.TYPE_INT_RGB);
        JLabel label = new JLabel();
        label.setIcon(new ImageIcon(image));
        frame.add(label);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
        graphics = image.getGraphics();
    }

    private static void repaintColors() {
        for (int i = 0; i < colors.length; i++) {
            graphics.setColor(colors[i]);
            graphics.drawLine(i, 0, i, frame.getHeight());
        }
        frame.repaint();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void startTimer() {
        start = currentTimeMillis();
    }

    private static void printTimer(String name) {
        out.printf("%s finished in %.3f%n", name, (currentTimeMillis() - start) / 1000d);
    }

    private static float compare(Color c1, Color c2) {
        float[] c1HSB = Color.RGBtoHSB(c1.getRed(), c1.getGreen(), c1.getBlue(), null);
        float[] c2HSB = Color.RGBtoHSB(c2.getRed(), c2.getGreen(), c2.getBlue(), null);
        float hDelta = c2HSB[0] - c1HSB[0];
        if (hDelta != 0) return hDelta;
        float sDelta = c2HSB[1] - c1HSB[1];
        if (sDelta != 0) return sDelta;
        return (c2HSB[2] - c1HSB[2]);
    }

    private static void selectionSort() {
        frame.setTitle("Selection Sort");
        startTimer();
        for (int sorted = 0; sorted < colors.length - 1; sorted++) {
            float lowestVal = compare(colors[sorted], colors[sorted]);
            int lowestIndex = sorted;
            for (int i = sorted + 1; i < colors.length; i++) {
                float compare = compare(colors[i], colors[sorted]);
                if (compare < lowestVal) {
                    lowestVal = compare;
                    lowestIndex = i;
                }
            }
            Color color = colors[sorted];
            colors[sorted] = colors[lowestIndex];
            colors[lowestIndex] = color;
            repaintColors();
        }
        printTimer("Selection Sort");
    }

    private static void insertionSort() {
        frame.setTitle("Insertion Sort");
        startTimer();
        for (int i = 0; i < colors.length; i++) {
            for (int j = i - 1; j >= 0 && compare(colors[j + 1], colors[j]) < 0; j--) {
                Color color = colors[j + 1];
                colors[j + 1] = colors[j];
                colors[j] = color;
            }
            repaintColors();
        }
        printTimer("Insertion Sort");
    }

    private static void bubbleSort() {
        frame.setTitle("Bubble Sort");
        startTimer();
        int lastSorted;
        do {
            lastSorted = 0;
            for (int i = 1; i < colors.length; i++) {
                if (compare(colors[i], colors[i - 1]) < 0) {
                    Color color = colors[i];
                    colors[i] = colors[i - 1];
                    colors[i - 1] = color;
                    lastSorted++;
                }
            }
            repaintColors();
        } while (lastSorted != 0);
        printTimer("Bubble Sort");
    }
}

