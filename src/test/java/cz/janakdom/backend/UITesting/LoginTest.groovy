package cz.janakdom.backend.UITesting
import geb.Browser
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.boot.test.context.SpringBootTest
import static org.junit.jupiter.api.Assertions.assertEquals


class LoginTest {

    @Test
    void loginTest() {
        System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\geckodriver.exe")

        Browser.drive {
            go 'https://nnpia.dominikjanak.cz/'
            assert title == "Přihlášení | Citáty"

            driver.findElement(By.name("username")).sendKeys("janakdom")
            driver.findElement(By.name("password")).sendKeys("739143789")
            driver.findElement(By.xpath("//input[@value='Přihlásit se']")).click();
            WebDriverWait wait = new WebDriverWait(driver, 60);

            wait.until(ExpectedConditions.titleIs("Seznam citátů | Citáty"))
        }
    }


}