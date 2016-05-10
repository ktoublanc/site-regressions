package com.github.ktoublanc.site.regression.selenium.tests.browser;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kevin on 09/05/2016.
 */
public class IPhone6PlusChromeEmulation extends Chrome {

    /**
     * Default constructor for building an iPhone5 browser
     */
    IPhone6PlusChromeEmulation() throws MalformedURLException {
        super("iPhone 6 Plus", iPhone6PlusCapabilities());
    }

    /**
     * @return The iPhone 5 chrome emulated capabilities
     */
    private static DesiredCapabilities iPhone6PlusCapabilities() {
        final Map<String, String> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceName", "Apple iPhone 6 Plus");

        final Map<String, Object> chromeOptions = new HashMap<>();
        chromeOptions.put("mobileEmulation", mobileEmulation);

        final DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        return capabilities;
    }
}
