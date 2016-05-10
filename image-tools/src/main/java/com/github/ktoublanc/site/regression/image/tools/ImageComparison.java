package com.github.ktoublanc.site.regression.image.tools;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by kevin on 06/05/2016.
 */
public class ImageComparison {


    /**
     * The reference image
     */
    private final BufferedImage referenceImage;

    /**
     * The compared image
     */
    private final BufferedImage comparedImage;

    /**
     * Builds a {@link ImageComparison} class instances specifying the compared images.
     * @param referenceImage The reference image
     * @param comparedImage  The compared image
     * @throws ImageComparisonException if images size mismatch
     */
    public ImageComparison(final BufferedImage referenceImage, final BufferedImage comparedImage) throws ImageComparisonException {
        this.referenceImage = referenceImage;
        this.comparedImage = comparedImage;

        if (!ImageTools.checkSizes(referenceImage, comparedImage)) {
            throw new ImageComparisonException("Images dimensions mismatch");
        }
    }

    /**
     * Process the difference between the {@link #referenceImage} and the {@link #comparedImage} images
     *
     * @return the difference percentage
     * @throws IOException              if images can't be read
     * @throws ImageComparisonException if images are not the same size
     */
    public double processDifferencePercent() {

        int referenceImageWidth = referenceImage.getWidth(null);
        int referenceImageHeight = referenceImage.getHeight(null);
        long difference = 0;
        for (int y = 0; y < referenceImageHeight; y++) {
            for (int x = 0; x < referenceImageWidth; x++) {
                int referenceImageRGB = referenceImage.getRGB(x, y);
                int referenceImageR = (referenceImageRGB >> 16) & 0xff;
                int referenceImageG = (referenceImageRGB >> 8) & 0xff;
                int referenceImageB = (referenceImageRGB) & 0xff;

                int comparedImageRGB = comparedImage.getRGB(x, y);
                int comparedImageR = (comparedImageRGB >> 16) & 0xff;
                int comparedImageG = (comparedImageRGB >> 8) & 0xff;
                int ComparedImageB = (comparedImageRGB) & 0xff;

                difference += Math.abs(referenceImageR - comparedImageR);
                difference += Math.abs(referenceImageG - comparedImageG);
                difference += Math.abs(referenceImageB - ComparedImageB);
            }
        }
        double pixelCount = referenceImageWidth * referenceImageHeight * 3;
        double p = difference / pixelCount / 255.0;
        return p * 100.0;
    }
}
