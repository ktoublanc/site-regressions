package com.github.ktoublanc.site.regression.selenium.tests;


import com.github.ktoublanc.site.regression.selenium.tests.browser.Browser;
import com.github.ktoublanc.site.regression.selenium.tests.browser.Browsers;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import org.assertj.core.api.Assertions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kevin on 06/05/2016.
 */
public class CommonSteps implements En {

    private String url;
    private List<Browser> browsers;


    @Before
    public void beforeScenario() throws MalformedURLException {
        browsers = Browsers.all();
    }

    @After
    public void afterScenario(final Scenario scenario) throws IOException {
        browsers.forEach(browser -> {
            try {
                if (!browser.hasReferenceImage()) {
                    scenario.write("No reference file found for browser: " + browser.getDeviceName() + "\n" +
                            "Please find bellow the snapshot which was taken with " + browser.getDeviceName());
                    if (browser.wasSnapshotTaken()) {
                        scenario.embed(browser.getPngSnapshot(), "image/png");
                    }

                } else if (browser.hasDifferences()) {
                    scenario.write("Differences found for browser: " + browser.getDeviceName());
                    scenario.embed(browser.getPngDifferenceImage(), "image/png");

                } else {
                    scenario.write("No differences found for browser: " + browser.getDeviceName());

                }
            } catch (IOException e) {
                Assertions.fail("Exception occurred while processing reports", e);
            }
        });

        browsers.forEach(Browser::close);
    }

    public CommonSteps() throws IOException {

        Given("^I want to take snapshots? of (.*)", (String url) -> this.url = url);

        When("^I take snapshot with each available browsers$", () -> browsers.forEach(
                browser -> browser.captureSnapshot(url))
        );

        Then("^I assert that there are no differences between snapshots$", () -> {
            final List<Throwable> exceptions = new ArrayList<>();
            browsers.forEach(browser -> {
                try {
                    browser.checkDifferences();
                } catch (RuntimeException | AssertionError e) {
                    exceptions.add(e);
                }

            });

            exceptions.forEach((Throwable throwable) -> {
                if (throwable instanceof AssertionError) {
                    throw ((AssertionError) throwable);
                } else {
                    throw ((RuntimeException) throwable);
                }
            });
        });
    }
}
