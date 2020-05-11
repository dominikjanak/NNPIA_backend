package cz.janakdom.backend;

import geb.Browser
import org.junit.jupiter.api.Test
import org.openqa.selenium.By

import static org.junit.jupiter.api.Assertions.assertEquals

class UIHelloWorld {

  @Test
  void testUpce() {
    System.setProperty("webdriver.gecko.driver", "D:\\geckodriver.exe")


    Browser.drive {
      go 'https://www.upce.cz'
      assertEquals(title, "Univerzita Pardubice")
      driver.findElement(By.linkText("POPLATKY")).click()
    }
  }


}
