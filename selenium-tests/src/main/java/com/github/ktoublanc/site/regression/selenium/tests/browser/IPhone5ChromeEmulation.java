package com.github.ktoublanc.site.regression.selenium.tests.browser;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevin on 09/05/2016.
 */
public class IPhone5ChromeEmulation extends Chrome {

    /**
     * Default constructor for building an iPhone5 browser
     */
    IPhone5ChromeEmulation() throws MalformedURLException {
        super("iphone5", iPhone5Capabilities());
    }

    /**
     * @return The iPhone 5 chrome emulated capabilities
     */
    private static DesiredCapabilities iPhone5Capabilities() {
        final Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "Apple iPhone 5");

        final Map<String, Object> chromeOptions = new HashMap<>();
        chromeOptions.put("mobileEmulation", mobileEmulation);

        final DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        return capabilities;
    }
}
