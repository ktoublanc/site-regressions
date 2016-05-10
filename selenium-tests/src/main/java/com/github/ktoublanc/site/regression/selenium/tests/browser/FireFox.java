package com.github.ktoublanc.site.regression.selenium.tests.browser;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;

/**
 * Created by kevin on 09/05/2016.
 */
public class FireFox extends Browser {

    FireFox() throws MalformedURLException {
        super("firefox", "http://192.168.99.100:5555/wd/hub", DesiredCapabilities.firefox());
    }
}
