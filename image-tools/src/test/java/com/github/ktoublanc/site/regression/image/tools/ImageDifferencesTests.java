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

    @Test
    public void imageSizeMismatch() throws IOException, ImageComparisonException {
        final ImageDifferences differences = new ImageDifferences(
                ImageTools.imageFromPath(Paths.get("src/test/resources/github-chrome.png")),
                ImageTools.imageFromPath(Paths.get("src/test/resources/github-firefox.png"))
        );

        assertThat(differences.hasDifferences()).isTrue();

        final BufferedImage image = differences.joinDifferenceHighlightedImages();
        assertThat(image.getWidth()).isEqualTo(2105);
        assertThat(image.getHeight()).isEqualTo(2118);
    }

}
