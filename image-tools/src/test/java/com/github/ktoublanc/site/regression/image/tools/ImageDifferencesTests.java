package com.github.ktoublanc.site.regression.image.tools;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by kevin on 07/05/2016.
 */
public class ImageDifferencesTests {

    @Test
    public void referenceImageIsDifferent() throws Exception {
        ImageDifferences imageDifferences = new ImageDifferences(
                ImageTools.imageFromPath(Paths.get("src/test/resources/reference.png")),
                ImageTools.imageFromPath(Paths.get("src/test/resources/compared.png"))
        );

        assertThat(imageDifferences.hasDifferences()).isTrue();
        assertThat(imageDifferences.differenceHighlightedReferenceImage()).isNotNull();
        assertThat(imageDifferences.differenceHighlightedComparedImage()).isNotNull();
        assertThat(imageDifferences.joinDifferenceHighlightedImages()).isNotNull();
    }

    @Test
    public void referenceImageIsTheSameAsComparedImage() throws IOException, ImageComparisonException {
        ImageDifferences imageDifferences = new ImageDifferences(
                ImageTools.imageFromPath(Paths.get("src/test/resources/reference.png")),
                ImageTools.imageFromPath(Paths.get("src/test/resources/reference.png"))
        );

        assertThat(imageDifferences.hasDifferences()).isFalse();
        assertThat(imageDifferences.differenceHighlightedReferenceImage()).isNotNull();
        assertThat(imageDifferences.differenceHighlightedComparedImage()).isNotNull();
        assertThat(imageDifferences.joinDifferenceHighlightedImages()).isNotNull();
    }

    @Test(expected = ImageComparisonException.class)
    public void imageSizeMismatch() throws IOException, ImageComparisonException {
        new ImageDifferences(
                ImageTools.imageFromPath(Paths.get("src/test/resources/reference.png")),
                new BufferedImage(10, 10, BufferedImage.TYPE_BYTE_BINARY)
        );
    }

}
