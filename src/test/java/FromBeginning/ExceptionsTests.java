package FromBeginning;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class ExceptionsTests {

    private WebDriver driver;


    @BeforeMethod()
    private void setUp() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }


    @Test(priority = 1)
    public void notVisibleTest() {
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
        WebElement startButton = driver.findElement(By.xpath("//div[@id='start']/button"));
        startButton.click();

        WebElement finishElement = driver.findElement(By.xpath("//div[@id='finish']/h4"));

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(finishElement));

        String finishText = finishElement.getText();

        Assert.assertTrue(finishText.contains("Hello World!"), "Finish text: " + finishText);

    }

    @Test(priority = 2)
    public void timeOutTest() throws InterruptedException {
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
        WebElement startButton = driver.findElement(By.xpath("//div[@id='start']/button"));
        startButton.click();

        WebElement finishElement = driver.findElement(By.xpath("//div[@id='finish']/h4"));

        WebDriverWait wait = new WebDriverWait(driver, 2);
        try {
            wait.until(ExpectedConditions.visibilityOf(finishElement));
        } catch (TimeoutException e) {
            System.out.println("Exception is caught as " + e.getMessage());
            Thread.sleep(3000);
        }

        String finishText = finishElement.getText();

        Assert.assertTrue(finishText.contains("Hello World!"), "Finish text: " + finishText);

    }

    @Test(priority = 3)
    public void notSuchElementTest() {
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/2");
        WebElement startButton = driver.findElement(By.xpath("//div[@id='start']/button"));
        startButton.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        Assert.assertTrue(wait.until(ExpectedConditions.
                textToBePresentInElementLocated(By.id("finish"), "Hello World!")), "Couldn't verify expected text 'Hello world!'");
        /*WebElement finishElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("finish")));

        String finishText = finishElement.getText();

        Assert.assertTrue(finishText.contains("Hello World!"), "Finish text: " + finishText);*/

    }

    @Test
    public void staleElementTest() {
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");
        WebElement checkBox = driver.findElement(By.id("checkbox"));
        WebElement removeButton = driver.findElement(By.xpath("//button[contains(text(),'Remove')]"));

        WebDriverWait wait = new WebDriverWait(driver, 10);

        removeButton.click();
        /*wait.until(ExpectedConditions.invisibilityOf(checkBox));

        Assert.assertFalse(checkBox.isDisplayed());*/

        // Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOf(checkBox)), "Checkbox is still visivle but shouldn't");

        Assert.assertTrue(wait.until(ExpectedConditions.stalenessOf(checkBox)), "Checkbox is still visivle but shouldn't");

        WebElement addButton = driver.findElement(By.xpath("//button[contains(text(),'Add')]"));
        addButton.click();

        WebElement checkBox2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkbox")));
        Assert.assertTrue(checkBox2.isDisplayed(), "Checkbox is not visible but shouldn");

    }

    @Test
    public void disabledElementTest(){
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");
        WebElement enableButton = driver.findElement(By.xpath("//button[contains(text(),'Enable')]"));
        WebElement inputField = driver.findElement(By.xpath("//input[@type='text']"));
        WebDriverWait wait = new WebDriverWait(driver, 10);

        enableButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(inputField)).sendKeys("Eugene");

        String enteredText = inputField.getAttribute("value");
        Assert.assertEquals(enteredText,"Eugene","Something went wrong");




    }


    @AfterMethod
    private void tearDown() {
        driver.quit();
    }

}
