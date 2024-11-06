package com.irctc;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.TestNGException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.utils.driverSetup;
import com.utils.excelUtils;
public class irctcTestng {
public  WebDriver driver;
String fileName = System.getProperty("user.dir") + "/src/test/resources/input.xlsx";
@BeforeClass
public WebDriver createDriver() {
// Invoke getWebDriver method from DriverSetup and return it
driver = driverSetup.getWebDriver();
driver.manage().window().maximize();// window maximize
driver.get(" https://www.irctc.co.in");// to get the irctc website
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50)); // to provide timeout
return driver;
}
// verify method for the given page is correct or not by comparing title of the page
@Test(priority=1)
public void verify() {
String expResult = "IRCTC Next Generation eTicketing System";
String actResult = driver.getTitle(); // webdriver method to get title of page and verify with required page
if (actResult.equalsIgnoreCase(expResult))
System.out.println("Website launch is IRCTC Train");
else
System.out.println("Website launch is incorrect");
System.out.println(actResult);
}
// select from method to suggest the correct place
@Test(priority=2, dependsOnMethods={"verify"})
public void selectFrom() throws InterruptedException, IOException, TestNGException
{
WebElement from = driver.findElement(By.xpath("//*[@id='origin']/span/input")); // webElement of from place
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("arguments[0].click();", from);
// reading data from excel file i.e. from station
String fromPlace = excelUtils.getCellData(fileName, "TestCases", 1, 0);
from.sendKeys(fromPlace); // WebElement method to send keys
// auto suggestions list
List<WebElement> from_suggestion = driver.findElements(By.xpath("//ul[@id='pr_id_1_list']//span"));
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
for (WebElement s : from_suggestion) // Traversing webElements text to get the required from station
{
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
if (s.getText().contains("HYDERABAD DECAN - HYB")) {
s.click();
break;
}
}
}
// select 'to' method to suggest the correct place
@Test(priority=3, dependsOnMethods= {"verify"})
public void selectTo() throws InterruptedException, IOException, TestNGException
{
WebElement to = driver.findElement(By.xpath("//*[@id='destination']/span/input")); // webElement of to place
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("arguments[0].click();", to);
// reading data from excel file i.e. to station
String toPlace = excelUtils.getCellData(fileName, "TestCases", 1, 1);
to.sendKeys(toPlace);
List<WebElement> to_suggestion = driver.findElements(By.xpath("//ul[@id='pr_id_2_list']//span"));
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
for (WebElement s : to_suggestion) // Traversing webElements text to get the required to station
{
// driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
if (s.getText().contains("PUNE JN - PUNE")) {
s.click();
break;
}
}
}
// Choose date method for future 4 days from current date
@Test(priority=4,dependsOnMethods= {"verify"})
public void choosedate() throws InterruptedException
{
Date currentDate = new Date();
Calendar calendar = Calendar.getInstance();
calendar.setTime(currentDate);
calendar.add(Calendar.DAY_OF_MONTH, 4); // adding 4 days more from current date
Date futureDate = calendar.getTime();
SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // date format in "dd/MM/yyyy
String futureDateString = dateFormat.format(futureDate);
System.out.println(futureDateString);
WebElement date = driver.findElement(By.xpath("//*[@id='jDate']/span/input"));
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("arguments[0].click();", date);
Thread.sleep(1000);
date.sendKeys(Keys.chord(Keys.CONTROL, "a")); // Key actions i.e "ctrl+a"
Thread.sleep(2000);
date.sendKeys(Keys.BACK_SPACE); // Key actions i.e. "Backspace"
Thread.sleep(2000);
date.sendKeys(futureDateString);
Thread.sleep(1000);
}
// method to click on Divyannag checkbox(disabled person checkbox)
@Test(priority=5,dependsOnMethods= {"verify"})
public void Divyaangcheckbox() throws InterruptedException
{
WebElement divyangCheckbox = driver.findElement(By.xpath("//label[@class='css-label_c t_c']"));
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("arguments[0].click();", divyangCheckbox);
Thread.sleep(3000);
}
// method click on ok button
@Test(priority=6, dependsOnMethods= {"verify","Divyaangcheckbox"})
public void okbutton() throws InterruptedException
{
WebElement okButton = driver.findElement(By.xpath("//span[@class='ui-button-text ui-clickable']"));
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("arguments[0].click();", okButton);
Thread.sleep(3000);
}
// method to select sleeper class
@Test(priority=7, dependsOnMethods= {"verify"})
public void selectClass() throws InterruptedException
{
WebElement allClass = driver.findElement(By.xpath(
"//span[@class='ng-tns-c65-11 ui-dropdown-label ui-inputtext ui-corner-all ng-star-inserted']"));
Thread.sleep(2000);
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("arguments[0].click();", allClass);
// select class sleeper
WebElement sleeperClass = driver.findElement(By.xpath("//span[contains(text(),'Sleeper (SL)')]"));
js.executeScript("arguments[0].click();", sleeperClass);
Thread.sleep(3000);
}
// method to click on search button
@Test(priority=8,dependsOnMethods= {"verify"})
public void search() throws InterruptedException, IOException, TestNGException
{
WebElement searchButton = driver.findElement(By.xpath(
"//*[@id='divMain']/div/app-main-page/div/div/div[1]/div[1]/div[1]/app-jp-input/div/form/div[5]/div/button"));
JavascriptExecutor js = (JavascriptExecutor) driver;
js.executeScript("arguments[0].click();", searchButton);
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // wait time to load the trains list
String beforeXpath = "//div[@class='col-sm-5 col-xs-11 train-heading']//strong";
List<WebElement> _divs = driver.findElements(By.xpath(beforeXpath));
int NoOfDivs = _divs.size();
for (int i = 1; i <= NoOfDivs; i++) {
String actualXpath = (beforeXpath) + "[" + i + "]"; //dynamic xpath
List<WebElement> results = driver.findElements(By.xpath(actualXpath));
int j = 1;
// printing the trains searched
for (WebElement we : results) {
System.out.println("Name and Number of the available train is:-" + we.getText());
// writing data into excel file i.e. Trains list.
excelUtils.setCellData(fileName, "TestCases", j, 2, we.getText());
j++;
}
}
System.out.println("Total number of Results-" + NoOfDivs);
}
// method to take screenshot
@Test(priority=9,dependsOnMethods= {"search"})
public void captureScreenshot()
{
TakesScreenshot srcShot = ((TakesScreenshot) driver);
File Src = srcShot.getScreenshotAs(OutputType.FILE);
Date d = Calendar.getInstance().getTime();
SimpleDateFormat dat = new SimpleDateFormat("yyyy-MM-dd h-m-s");
String strd = dat.format(d);
String filePath = System.getProperty("user.dir") + "/src/test/resources/screenshots/" + strd + ".png";
File Dest = new File(filePath);
try {
FileHandler.copy(Src, Dest);
} catch (IOException e) {
e.printStackTrace();
}
System.out.println("Screenshot successfully");
}
// method to close the browser
@AfterClass
public void closeBrowser()
{
driver.quit();
}
}