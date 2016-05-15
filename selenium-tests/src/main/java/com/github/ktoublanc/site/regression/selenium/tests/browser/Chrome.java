package com.github.ktoublanc.site.regression.selenium.tests.browser;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;

/**
 * Created by kevin on 09/05/2016.
 */
public class Chrome extends Browser {

    /**
     * Default constructor for building an Chrome browser
     */
    Chrome() throws MalformedURLException {
        super("chrome", DesiredCapabilities.chrome());
    }

    /**
     * Constructor for building a emulated chrome browser
     *
     * @param deviceName   The device name
     * @param capabilities the chrome emulated capabilities
     */
    Chrome(final String deviceName, final Capabilities capabilities) throws MalformedURLException {
        super(deviceName, capabilities);
    }
}
