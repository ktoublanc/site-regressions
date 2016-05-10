package com.github.ktoublanc.site.regression.image.tools;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by kevin on 08/05/2016.
 */
public class ImageToolsTest {

    @Test
    public void imageFromFile() throws Exception {
        assertThat(ImageTools.imageFromFile(Paths.get("src/test/resources/reference.png").toFile())).isNotNull();
    }

    @Test
    public void imageFromPath() throws Exception {
        assertThat(ImageTools.imageFromPath(Paths.get("src/test/resources/reference.png"))).isNotNull();
    }

    @Test(expected = NullPointerException.class)
    public void imageFromPath_NullPath() throws Exception {
        ImageTools.imageFromPath(null);
    }

    @Test
    public void checkSizes_ImagesAreTheSameSize() throws Exception {
        assertThat(ImageTools.checkSizes(
                new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR),
                new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR),
                new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR),
                new BufferedImage(10, 10, BufferedImage.TYPE_4BYTE_ABGR_PRE),
                new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR),
                new BufferedImage(10, 10, BufferedImage.TYPE_4BYTE_ABGR)
        ));
    }

    @Test
    public void checkSizes_ImagesAreNotTheSameSize() throws Exception {
        assertThat(ImageTools.checkSizes(
                new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR),
                new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR),
                new BufferedImage(20, 10, BufferedImage.TYPE_3BYTE_BGR),
                new BufferedImage(10, 10, BufferedImage.TYPE_4BYTE_ABGR_PRE),
                new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR),
                new BufferedImage(10, 10, BufferedImage.TYPE_4BYTE_ABGR)
        ));
    }

    @Test
    public void pngToByteArray() throws Exception {
        final BufferedImage image = ImageTools.imageFromPath(Paths.get("src/test/resources/reference.png"));
        assertThat(ImageTools.pngToByteArray(image)).isNotNull();
    }

}