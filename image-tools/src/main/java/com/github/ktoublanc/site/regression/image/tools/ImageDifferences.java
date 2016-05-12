package com.github.ktoublanc.site.regression.image.tools;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Process image differences.
 * The difference is processed by analysing a size parameterizable rect of pixels.
 * On each rectangles the mean of each pixel is calculated and compared to the reference image.
 * Created by kevin on 07/05/2016.
 */
public class ImageDifferences {

    /**
     * Default rect size
     */
    private static final int DEFAULT_RECT_SIZE = 10;

    /**
     * RED transparent color
     */
    private static final Color RED_TRANSPARENT_COLOR = new Color(1f, 0f, 0f, .5f);

    /**
     * No Color constant
     */
    private static final Color NO_COLOR = new Color(0f, 0f, 0f, 0f);

    /**
     * the analyser rect size
     */
    private final int size;

    /**
     * The reference image
     */
    private final BufferedImage referenceImage;

    /**
     * The compared image
     */
    private final BufferedImage comparedImage;

    /**
     * The difference map
     */
    private final Map<Integer, Map<Integer, ImageDifference>> differenceMap = new HashMap<>();

    /**
     * Image Difference wrapping class
     */
    private class ImageDifference {

        /**
         * X position
         */
        private final int x;

        /**
         * Y Position
         */
        private final int y;

        /**
         * The rect size
         */
        private final int size;

        /**
         * Builds a {@link ImageDifference} instance
         *
         * @param x     the x parameter
         * @param y     the y parameter
         * @param width the rectangle width
         */
        ImageDifference(int x, int y, int width) {
            this.x = x;
            this.y = y;
            this.size = width;
        }
    }

    /**
     * Builds a {@link ImageDifferences} class instances specifying the compared images and the analysing rectangle size
     *
     * @param referenceImage The reference image
     * @param comparedImage  The compared image
     * @param size           The analysing rectangle size
     * @throws ImageComparisonException if images size mismatch
     */
    public ImageDifferences(final BufferedImage referenceImage, final BufferedImage comparedImage, final int size) throws ImageComparisonException {
        this.referenceImage = referenceImage;
        this.comparedImage = comparedImage;
        this.size = size < 0 ? DEFAULT_RECT_SIZE : size;

        this.findDifferences();
    }

    /**
     * Builds a {@link ImageDifferences} class instances specifying the compared images.
     * The analysing rectangle size is set to the default value: {@link ImageDifferences#DEFAULT_RECT_SIZE}
     *
     * @param referenceImage The reference image
     * @param comparedImage  The compared image
     * @throws ImageComparisonException if images size mismatch
     */
    public ImageDifferences(final BufferedImage referenceImage, final BufferedImage comparedImage) throws ImageComparisonException {
        this(referenceImage, comparedImage, DEFAULT_RECT_SIZE);
    }

    /**
     * Creates a copy of the compared image with differences highlighted
     */
    public BufferedImage differenceHighlightedComparedImage() {
        int height = Math.max(referenceImage.getHeight(), comparedImage.getHeight());
        int width = Math.max(referenceImage.getWidth(), comparedImage.getWidth());
        return this.createDifferenceHighlightedImage(comparedImage, width, height);
    }

    /**
     * Creates a copy of the reference image with differences highlighted
     */
    public BufferedImage differenceHighlightedReferenceImage() {
        int height = Math.max(referenceImage.getHeight(), comparedImage.getHeight());
        int width = Math.max(referenceImage.getWidth(), comparedImage.getWidth());
        return this.createDifferenceHighlightedImage(referenceImage, width, height);
    }

    /**
     * Join the two difference highlighted images into a single one
     *
     * @return The joined difference images
     */
    public BufferedImage joinDifferenceHighlightedImages() {
        int height = Math.max(referenceImage.getHeight(), comparedImage.getHeight());
        int width = Math.max(referenceImage.getWidth(), comparedImage.getWidth());
        final BufferedImage referenceImage = createDifferenceHighlightedImage(this.referenceImage, width, height);
        final BufferedImage comparedImage = createDifferenceHighlightedImage(this.comparedImage, width, height);

        int offset = 5;
        int newWidth = referenceImage.getWidth() + comparedImage.getWidth() + offset;
        int newHeight = Math.max(referenceImage.getHeight(), comparedImage.getHeight()) + offset;

        //create a new buffer and draw two image into the new image
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = newImage.createGraphics();
        final Color oldColor = g2.getColor();

        //fill background
        g2.setPaint(NO_COLOR);
        g2.fillRect(0, 0, newWidth, newHeight);

        //draw image
        g2.setColor(oldColor);
        g2.drawImage(referenceImage, null, 0, 0);
        g2.drawImage(comparedImage, null, comparedImage.getWidth() + offset, 0);

        g2.dispose();
        return newImage;
    }

    /**
     * Has the two compared images differences ?
     *
     * @return true if the two difference images has differences
     */
    public boolean hasDifferences() {
        return !this.differenceMap.isEmpty();
    }

    /**
     * Find differences between the two compared images
     */
    private void findDifferences() {
        int maxImageWidth = Math.max(referenceImage.getWidth(), comparedImage.getWidth());
        int minImageWidth = Math.min(referenceImage.getWidth(), comparedImage.getWidth());
        int maxImageHeight = Math.max(referenceImage.getHeight(), comparedImage.getHeight());
        int minImageHeight = Math.min(referenceImage.getHeight(), comparedImage.getHeight());

        for (int x = 0; x < maxImageWidth; x += size) {
            for (int y = 0; y < maxImageHeight; y += size) {

                if (y >= minImageHeight || x >= minImageWidth) {
                    this.addDifference(x, y);
                    continue;
                }

                double referenceMean = 0;
                double comparedMean = 0;
                int pixelCount = 0;
                for (int xx = x; xx < x + size; xx++) {
                    for (int yy = y; yy < y + size; yy++) {
                        if (xx >= minImageWidth || yy >= minImageHeight) {
                            break;
                        }
                        referenceMean += referenceImage.getRGB(xx, yy);
                        comparedMean += comparedImage.getRGB(xx, yy);
                        pixelCount++;
                    }
                }

                referenceMean = referenceMean / (double) pixelCount;
                comparedMean = comparedMean / (double) pixelCount;
                if (comparedMean != referenceMean) {
                    this.addDifference(x, y);
                }
            }
        }
    }

    /**
     * Add difference into map
     *
     * @param x the x position
     * @param y the y position
     */
    private void addDifference(int x, int y) {
        final ImageDifference imageDifference = new ImageDifference(x, y, size);
        if (differenceMap.containsKey(x)) {
            final Map<Integer, ImageDifference> yMap = differenceMap.get(x);
            yMap.put(y, imageDifference);
        } else {
            final Map<Integer, ImageDifference> yMap = new HashMap<>();
            yMap.put(y, imageDifference);
            differenceMap.put(x, yMap);
        }
    }

    /**
     * Create difference highlighted image
     *
     * @param image  The target image
     * @param height the new height
     * @param width  the new width
     */
    private BufferedImage createDifferenceHighlightedImage(final BufferedImage image, final int width, final int height) {
        final BufferedImage scaledImage = new BufferedImage(width, height, image.getType());
        final Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);

        // Set color to red with 50% of transparency
        g2d.setColor(RED_TRANSPARENT_COLOR);

        differenceMap.forEach((final Integer x, final Map<Integer, ImageDifference> yMap) -> yMap.forEach((final Integer y, final ImageDifference diff) -> {

            final boolean hasDifferenceAbove = differenceMap.containsKey(diff.x) && differenceMap.get(diff.x).containsKey(diff.y - diff.size);
            if (!hasDifferenceAbove) {
                g2d.drawLine(diff.x, diff.y, diff.x + diff.size, diff.y);
            }

            final boolean hasDifferenceAtRight = differenceMap.containsKey(diff.x + diff.size) && differenceMap.get(diff.x + diff.size).containsKey(diff.y);
            if (!hasDifferenceAtRight) {
                g2d.drawLine(diff.x + diff.size, diff.y, diff.x + diff.size, diff.y + diff.size);
            }

            final boolean hasDifferenceAtLeft = differenceMap.containsKey(diff.x - diff.size) && differenceMap.get(diff.x - diff.size).containsKey(diff.y);
            if (!hasDifferenceAtLeft) {
                g2d.drawLine(diff.x, diff.y, diff.x, diff.y + diff.size);
            }

            final boolean hasDifferenceUnder = differenceMap.containsKey(diff.x) && differenceMap.get(diff.x).containsKey(diff.y + diff.size);
            if (!hasDifferenceUnder) {
                g2d.drawLine(diff.x, diff.y + diff.size, diff.x + diff.size, diff.y + diff.size);
            }

            g2d.fillRect(diff.x, diff.y, diff.size, diff.size);
        }));

        return scaledImage;
    }
}
