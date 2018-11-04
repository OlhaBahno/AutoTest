package com.softserve;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class FirstTest {

    private static WebDriver driver;
    private static String nameOfGroup = "DP-500-UX";

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "D:\\Driver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://146.148.17.49/");

        log();

    }

    public static void log() {
        WebElement element = driver.findElement(By.name("login"));
        element.sendKeys("Dmytro");
        element = driver.findElement(By.name("password"));
        element.sendKeys("1234");
        element = driver.findElement(By.className("submit"));
        element.click();

        //Assert.assertTrue((new WebDriverWait(driver, 10))
        //.until(ExpectedConditions.invisibilityOf(element)));
    }

    public static void hover() {
        Actions ac = new Actions(driver);
        ac.moveToElement(driver.findElement(By.xpath("//*[@id=\"left-menu\"]"))).build().perform();

        WebElement element;
        element = driver.findElement(By.xpath("//*[@id=\"left-menu\"]/div/div[1]/button"));
        element.click();
    }

    @Test
    public static void createGroup() {

        hover();

        //element.click();
        WebElement el = (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.visibilityOf(driver.findElement(By.name("name"))));

        //name
        el.sendKeys(nameOfGroup);

        //group
        Select select = new Select(driver.findElement(By.name("direction")));
        select.selectByVisibleText("UX");

        //return name
        driver.findElement(By.className("return-name")).click();

        //date
        driver.findElement(By.xpath("//*[@id=\"modal-window\"]/section/section/section/div[2]/div[2]/span")).click();
        driver.findElement(By.xpath("//*[@id=\"ui-datepicker-div\"]/table/tbody/tr[4]/td[2]/a")).click();

        //add teacher
        driver.findElement(By.className("add-teacher-btn")).click();
        driver.findElement(By.id("acceptSelect")).click();

        //add expert
        driver.findElement(By.className("add-expert-btn")).click();
        driver.findElement(By.name("expert")).sendKeys("NewExpert");
        driver.findElement(By.id("acceptInput")).click();

        driver.findElement(By.id("save")).click();

        driver.findElement(By.xpath("//*[@id=\"left-side-bar\"]/div/div[3]/div/label[3]")).click();
        List<WebElement> items = driver.findElements(By.xpath("//*[@id=\"left-side-bar\"]/div/div[2]/div"));
        boolean check = false;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getText().equals(nameOfGroup)) {
                check = true;
            }
        }
        Assert.assertTrue(check);
    }

    @Test
    public static void createWithoutDirection(){

        hover();
        //element.click();
        WebElement el = (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.visibilityOf(driver.findElement(By.name("name"))));

        //name
        el.sendKeys(nameOfGroup);
        //date
        driver.findElement(By.xpath("//*[@id=\"modal-window\"]/section/section/section/div[2]/div[2]/span")).click();
        driver.findElement(By.xpath("//*[@id=\"ui-datepicker-div\"]/table/tbody/tr[4]/td[2]/a")).click();
        //add teacher
        driver.findElement(By.className("add-teacher-btn")).click();
        driver.findElement(By.id("acceptSelect")).click();
        //add expert
        driver.findElement(By.className("add-expert-btn")).click();
        driver.findElement(By.name("expert")).sendKeys("NewExpert");
        driver.findElement(By.id("acceptInput")).click();

        driver.findElement(By.id("save")).click();

        Assert.assertEquals(driver.findElement(By.className("hint")).getText(),"Please, select direction!");
    }

    @Test
    public static void delete() {
        boolean check = false;
        driver.findElement(By.xpath("//*[@id=\"left-side-bar\"]/div/div[3]/div/label[3]")).click();
        List<WebElement> items = driver.findElements(By.xpath("//*[@id=\"left-side-bar\"]/div/div[2]/div"));
        for(int i=0; i<items.size();i++){
            if(items.get(i).getText().equals(nameOfGroup)){
                driver.findElement(By.xpath("//*[@id=\"left-side-bar\"]/div/div[2]/div[" + (i+1) + "]")).click();
                Actions ac = new Actions(driver);
                ac.moveToElement(driver.findElement(By.xpath("//*[@id=\"left-menu\"]"))).build().perform();

                WebElement element;
                element = driver.findElement(By.xpath("//*[@id=\"left-menu\"]/div/div[4]/button"));
                element.click();

                driver.findElement(By.className("btn-delete")).click();
                check = true;
                break;
            }
        }
        Assert.assertTrue(check);
    }

    @Test
    public static void createWithoutStartDate(){
        hover();

        WebElement el = (new WebDriverWait(driver, 30))
                .until(ExpectedConditions.visibilityOf(driver.findElement(By.name("name"))));

        //name
        el.sendKeys(nameOfGroup);
        //group
        Select select = new Select(driver.findElement(By.name("direction")));
        select.selectByVisibleText("UX");
        //return name

        driver.findElement(By.className("return-name")).click();
        //add teacher
        driver.findElement(By.className("add-teacher-btn")).click();
        driver.findElement(By.id("acceptSelect")).click();
        //add expert
        driver.findElement(By.className("add-expert-btn")).click();
        driver.findElement(By.name("expert")).sendKeys("NewExpert");
        driver.findElement(By.id("acceptInput")).click();

        driver.findElement(By.id("save")).click();

        Assert.assertEquals(driver.findElement(By.className("hint")).getText(),"Start date is required!");
    }

    @AfterClass
    public static void teardown(){
        driver.quit();
    }
}
