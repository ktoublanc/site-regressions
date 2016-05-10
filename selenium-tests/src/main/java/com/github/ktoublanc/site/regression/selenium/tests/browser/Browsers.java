package com.github.ktoublanc.site.regression.selenium.tests.browser;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kevin on 09/05/2016.
 */
public class Browsers {

    public static Browser iphone5() throws MalformedURLException {
        return new IPhone5ChromeEmulation();
    }

    public static Browser iphone6Plus() throws MalformedURLException {
        return new IPhone6PlusChromeEmulation();
    }

    public static Browser chrome() throws MalformedURLException {
        return new Chrome();
    }

    public static Browser firefox() throws MalformedURLException {
        return new FireFox();
    }

    public static List<Browser> all() throws MalformedURLException {
        return Arrays.asList(iphone5(), chrome(), firefox(), iphone6Plus());
    }

}
