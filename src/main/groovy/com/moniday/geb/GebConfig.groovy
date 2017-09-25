package com.moniday.geb

import io.github.bonigarcia.wdm.ChromeDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.DesiredCapabilities

driver = {
    DesiredCapabilities caps = new DesiredCapabilities()
    caps.setJavascriptEnabled(true);
    caps.setCapability("takesScreenshot", true)
    caps.setCapability(
            PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
            "/usr/local/share/phantomjs/bin/phantomjs"
    )
    new PhantomJSDriver(caps)
}

environments {
    development {
        ChromeDriverManager.getInstance().setup()
        WebDriver driver = new ChromeDriver()
    }
    test {
        ChromeDriverManager.getInstance().setup()
        WebDriver driver = new ChromeDriver()
    }

    production {
        DesiredCapabilities caps = new DesiredCapabilities()
        caps.setJavascriptEnabled(true);
        caps.setCapability("takesScreenshot", true)
        caps.setCapability(
                PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                "/usr/local/share/phantomjs/bin/phantomjs"
        )
        WebDriver driver = new PhantomJSDriver(caps)
    }
}
