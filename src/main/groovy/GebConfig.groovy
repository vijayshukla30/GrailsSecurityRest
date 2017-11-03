import io.github.bonigarcia.wdm.ChromeDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.DesiredCapabilities

List<String> cliArgsCap = []
cliArgsCap.add("--web-security=no")
cliArgsCap.add("--ssl-protocol=any")
cliArgsCap.add("--ignore-ssl-errors=yes")
cliArgsCap.add("--webdriver-logfile=/tmp/phantomjsdriver.log")
cliArgsCap.add("--webdriver-loglevel=ERROR")

driver = {
    DesiredCapabilities caps = new DesiredCapabilities()
    caps.setJavascriptEnabled(true);
    caps.setCapability("takesScreenshot", true)

    caps.setCapability(
            PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
            "/usr/local/share/phantomjs/bin/phantomjs"
    )
    caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap)

    new PhantomJSDriver(caps)

    /*ChromeDriverManager.getInstance().setup()
    def chromeDriver = new ChromeDriver()
    chromeDriver.manage().window().maximize()
    chromeDriver.switchTo().window(chromeDriver.getWindowHandle())*/
}

environments {
    chrome {
        ChromeDriverManager.getInstance().setup()
        def chromeDriver = new ChromeDriver()
        chromeDriver.manage().window().maximize()
        chromeDriver.switchTo().window(chromeDriver.getWindowHandle())
    }

    phantomJs {
        DesiredCapabilities caps = new DesiredCapabilities()
        caps.setJavascriptEnabled(true);
        caps.setCapability("takesScreenshot", true)
        caps.setCapability(
                PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                "/usr/local/share/phantomjs/bin/phantomjs"
        )
        caps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap)
        WebDriver driver = new PhantomJSDriver(caps)
    }
    firefox {
        WebDriver driver = new FirefoxDriver()
        driver.manage().window().maximize()
    }
}