package com.github.ktoublanc.site.regression.selenium.tests.browser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;

/**
 * Created by kevin on 09/05/2016.
 */
public class Chrome extends Browser {

    private static final String CHROME_URL = "http://192.168.99.100:4444/wd/hub";

    /**
     * Default constructor for building an Chrome browser
     */
    Chrome() throws MalformedURLException {
        super("chrome", CHROME_URL, DesiredCapabilities.chrome());
    }

    /**
     * Constructor for building a emulated chrome browser
     *
     * @param deviceName   The device name
     * @param capabilities the chrome emulated capabilities
     */
    Chrome(final String deviceName, final Capabilities capabilities) throws MalformedURLException {
        super(deviceName, CHROME_URL, capabilities);
    }
}
