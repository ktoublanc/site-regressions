# Site Regression project

The purpose here is to make a POC of how automated non regression test can be performed on a website GUI.

The main idea is to use an browser automation tool (here Selenium) and compare snapshots of a given web page to a previously stored reference.

As Selenium allows multiple device emulation through its WebDriver classes I have decided to test GUI for the following ones:

* Firefox
* Chrome
* iPhone 5 (Chrome Web Driver emulation)
* iPhone 6 Plus (Chrome Web Driver emulation)

Tests are made on the [github](http://www.github.com) web page and are already failing because of some changes in the GUI of Github.

Please not that the project build is failing and that's wanted!

# Project structure

You will find in the source code a proof of concept on image automation which doesn't intend to be deployed to any repository (at least for the moment).
I have pushed a multi project Gradle project containing two subprojects:

* image-tools
* selenium-tests

I'm sure that you will easily understand what is doing what ;)

# Run the project

The project is based on the Selenium RemoteWebDriver tech so to run it you need to have at least two selenium servers running onto your laptop. 
Docker might be a good place to look for to launch these two servers:
```bash
docker run -d -p 4444:4444 --name selenium-hub selenium/hub;
for i in $(seq 1 4);
do 
    docker run -d --link selenium-hub:hub selenium/node-firefox; 
    docker run -d --link selenium-hub:hub selenium/node-chrome; 
done
```

Note that the URL should be changed in the Java files
```java
    public abstract class Browser {
    
        private static final String URL = "http://192.168.99.100:4444/wd/hub";
        ...
```