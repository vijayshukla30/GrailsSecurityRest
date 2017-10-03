import io.github.bonigarcia.wdm.ChromeDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.DesiredCapabilities

driver = {
    ChromeDriverManager.getInstance().setup()
    def chromeDriver = new ChromeDriver()
    chromeDriver.manage().window().maximize()
    chromeDriver.switchTo().window(chromeDriver.getWindowHandle())
}
//htmlunit { driver = { new HtmlUnitDriver(true) } }

//phantomJs {
//     driver = {new PhantomJSDriver(true)}
//}
environments {
    chrome {
        ChromeDriverManager.getInstance().setup()
        driver = { new ChromeDriver() }
    }

    phantomJs {
        DesiredCapabilities caps = new DesiredCapabilities()
        caps.setJavascriptEnabled(true);
        caps.setCapability("takesScreenshot", true)
        caps.setCapability(
                PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                "/usr/local/share/phantomjs/bin/phantomjs"
        )
        WebDriver driver = new PhantomJSDriver(caps)
    }
    firefox {
        WebDriver driver = new FirefoxDriver()
        driver.manage().window().maximize()
    }
}