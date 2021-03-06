package cz.janakdom.backend.UITesting

import cz.janakdom.backend.Creator
import cz.janakdom.backend.Randomizer
import cz.janakdom.backend.model.database.User
import geb.Browser
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
class LoginCreatorTest {

    @Autowired
    private Creator creator;

    @Autowired
    private Randomizer randomizer;

    @Autowired
    private BCryptPasswordEncoder encoder;


    @Test
    void loginTest() {
        System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\geckodriver.exe")

        String generatedAuthenticationString = randomizer.randomString(15)
        creator.saveEntity(new User(username: generatedAuthenticationString, password: encoder.encode(generatedAuthenticationString)))

        Browser.drive {
            go 'http://127.0.0.1:3000/'
            assert title == "Přihlášení | Citáty"

            driver.findElement(By.name("username")).sendKeys(generatedAuthenticationString)
            driver.findElement(By.name("password")).sendKeys(generatedAuthenticationString)
            driver.findElement(By.xpath("//input[@value='Přihlásit se']")).click();
            WebDriverWait wait = new WebDriverWait(driver, 10)

            wait.until(ExpectedConditions.titleIs("Seznam citátů | Citáty"))
        }
    }


}