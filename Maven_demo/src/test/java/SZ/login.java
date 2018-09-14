package SZ;

import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class login {
	WebDriver driver;
	@BeforeSuite
	
	public void chrome()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\hp\\Desktop\\Testing\\chromedriver.exe");
	    driver=new ChromeDriver();
	    driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	    driver.manage().deleteAllCookies();
	  
	    
	}
	/*@AfterSuite
	public void chrome_close()
	{
		driver.quit();
	}*/
	
	
	@BeforeTest
	@Parameters({"url"})
	public void url(String url)
	{
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	@Test
	@Parameters({"email" , "pw"})
	public void login_mtd(String email , String pw)
	{
		driver.findElement(By.className("login-label")).click();
		driver.findElement(By.xpath(" //*[@id=\'mobile\']")).sendKeys(email); //username
    	driver.findElement(By.xpath("//*[@id=\'pass\']")).sendKeys(pw);  //password
    	driver.findElement(By.xpath("//*[@id=\'send2\']")).click();  //click login
    	
	}
	
	@Test(dependsOnMethods = "login_mtd")
	@Parameters({"old" , "new1", "confirm"})
	public void pw_change(String old, String new1 ,String confirm ) throws InterruptedException
	{
		/*System.out.println("cpw:" +old);
		System.out.println("newpw:" +new1);
		System.out.println("confirmpw:" +confirm);*/
		driver.findElement(By.linkText("Change Password")).click();
    	driver.findElement(By.xpath("//*[@id=\'current_password\']")).sendKeys(old);
    	driver.findElement(By.xpath("//*[@id=\'password\']")).sendKeys(new1);
    	driver.findElement(By.xpath("//*[@id=\'confirmation\']")).sendKeys(confirm);
    	driver.findElement(By.xpath("//*[@id=\'form-validate\']/div[3]/button")).click();
    	
    	Thread.sleep(5000);
	}
	
	@Test(dependsOnMethods = "pw_change")
	public void screenshot()
	{
		String a = "Please make sure your passwords match.";
		if (a.equalsIgnoreCase("Please make sure your passwords match.")) {
			File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE); // To Take screenshot of failure
																					// case
			try {
				
				FileUtils.copyFile(src,new File("C:\\Users\\hp\\Pictures\\Saved Pictures\\screen1.jpg"));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	@Test(priority=1)
	public void mouse_hover()
	{
		WebElement e = driver.findElement(By.xpath("//*[@id=\'nav\']/ol/li[3]/a"));
		Actions a= new Actions(driver);
		a.moveToElement(e).build().perform();
		driver.findElement(By.linkText("Cotton Silk")).click();
		
	}
	@Test(priority=2)
	public void sort()
	{
		Select s = new Select(driver.findElement(By.xpath("//*[@id=\'fme_layered_container\']/div[1]/div[1]/div[3]/div/select")));
		s.selectByVisibleText("Bottom Colour");
	}
	/*@Test(priority = 3)
	public void slider()
	{
		WebDriverWait wait=new WebDriverWait(driver, 20);
		JavascriptExecutor jse = (JavascriptExecutor) driver;      //use java script executor to scroll
		jse.executeScript("window.scrollBy(0,4000)");              //to scroll by execute script keyword
		WebElement e;
		e = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\'footer\']/div/div[1]/div[2]/div/ul/li[1]/a")));  
		e.click();//to click on terms and conditions

	}*/
	
}
