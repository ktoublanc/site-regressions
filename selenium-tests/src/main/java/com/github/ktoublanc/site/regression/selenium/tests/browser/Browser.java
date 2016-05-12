package com.github.ktoublanc.site.regression.selenium.tests.browser;

import com.github.ktoublanc.site.regression.image.tools.ImageDifferences;
import com.github.ktoublanc.site.regression.image.tools.ImageTools;
import cucumber.api.PendingException;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Paths;

/**
 * Abstract class defining common brother methods
 * Created by kevin on 09/05/2016.
 */
public abstract class Browser {

    private final String deviceName;
    private final WebDriver webDriver;
    private BufferedImage snapshot;
    private BufferedImage reference;
    private ImageDifferences imageDifferences;


    /**
     * Default constructor taking the {@link #deviceName} in argument
     *
     * @param deviceName The browser device name
     */
    Browser(final String deviceName, final String url, final Capabilities capabilities) throws MalformedURLException {
        this.deviceName = deviceName;
        this.webDriver = new RemoteWebDriver(URI.create(url).toURL(), capabilities);
    }

    /**
     * Close the selenium web driver
     */
    public void close() {
        webDriver.close();
    }


    /**
     * Goes to the specified URL and takes a snapshot
     *
     * @param url The URL where to take a snapshot
     * @throws AssertionError if an exception occurs when taking a snapshot
     */
    public void captureSnapshot(final String url) {
        webDriver.get(url);
        final File screen = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
        try {
            snapshot = ImageTools.imageFromFile(screen);
        } catch (IOException e) {
            Assertions.fail("Failed to take snapshot with browser: " + this, e);
        }
    }

    /**
     * Check differences between previously took snapshot and the reference
     *
     * @throws PendingException if no reference image exists
     * @throws AssertionError   if differences are found
     */
    public void checkDifferences() {
        try {
            this.reference = ImageTools.imageFromPath(Paths.get("src/main/resources/github-" + deviceName + ".png"));
        } catch (IOException e) {
            throw new PendingException("No reference found for test case");
        }

        imageDifferences = new ImageDifferences(reference, snapshot);
        Assertions.assertThat(imageDifferences.hasDifferences())
                .describedAs("Image comparison found differences between current snapshot and references")
                .isFalse();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return deviceName;
    }

    public boolean hasReferenceImage() {
        return reference != null;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public boolean wasSnapshotTaken() {
        return snapshot != null;
    }

    public byte[] getPngSnapshot() throws IOException {
        return wasSnapshotTaken() ? ImageTools.pngToByteArray(snapshot) : null;
    }

    public boolean hasDifferences() {
        return snapshot == null || reference == null || imageDifferences == null || imageDifferences.hasDifferences();
    }

    public byte[] getPngDifferenceImage() throws IOException {
        return hasDifferences() && imageDifferences != null ? ImageTools.pngToByteArray(imageDifferences.joinDifferenceHighlightedImages()) : null;
    }
}
