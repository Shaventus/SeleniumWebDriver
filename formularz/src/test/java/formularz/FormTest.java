package formularz;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FormTest {
	
	private static WebDriver driver;
	private WebDriverWait waitDriver;
	private WebElement button, name, surname, birthDate, parentsCheckbox, doctorCheckbox, returnString;
	
	@Before
	public void init() throws Exception{
		System.setProperty("webdriver.chrome.driver", "D:/chromedriver.exe");
		driver = new ChromeDriver();
		waitDriver = new WebDriverWait(driver, 10);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://lamp.ii.us.edu.pl/~mtdyd/zawody/");
		
		name = driver.findElement(By.id("inputEmail3"));
		surname = driver.findElement(By.id("inputPassword3"));
		birthDate = driver.findElement(By.id("dataU"));
		parentsCheckbox = driver.findElement(By.id("rodzice"));
		doctorCheckbox = driver.findElement(By.id("lekarz"));
		button = driver.findElement(By.className("btn-default"));
		returnString = driver.findElement(By.id("returnSt"));
	}
	
	@After
	public void tearDown() {
		driver.close();
	}
	
	public void fillForm(int year, boolean parent, boolean doctor) {
		this.name.clear();
		this.name.sendKeys("Imie");
		this.surname.clear();
		this.surname.sendKeys("Nazwisko");
		birthDate.clear();
		birthDate.sendKeys("01-01-".concat(Integer.toString(year)));
		if(parentsCheckbox.isSelected() != parent) {
			parentsCheckbox.click();
		}
		if(doctorCheckbox.isSelected() != doctor) {
			doctorCheckbox.click();
		}
		button.click();
		closeAlert();
	}
	
	@Test
	public void under10() throws Exception {
		fillForm(2018, true, false);
		assertEquals("Imie Nazwisko zostal zakwalifikowany do kategorii Brak kwalifikacji",
				returnString.getText());
	}
	
	@Test
	public void gnome() throws Exception {
		fillForm(2009, true, true);
		assertEquals("Imie Nazwisko zostal zakwalifikowany do kategorii Skrzat",
				returnString.getText());
	}
	
	@Test
	public void youngster() throws Exception {
		fillForm(2006, true, true);
		assertEquals("Imie Nazwisko zostal zakwalifikowany do kategorii Mlodzik",
				returnString.getText());
	}
	
	@Test
	public void youngsterwithout() throws Exception {
		fillForm(2006, false, false);
		assertEquals("Imie Nazwisko zostal zakwalifikowany do kategorii Blad danych",
				returnString.getText());
	}
	
	@Test
	public void junior() throws Exception {
		fillForm(2005, true, true);
		assertEquals("Imie Nazwisko zostal zakwalifikowany do kategorii Junior",
				returnString.getText());
	}
	
	@Test
	public void juniorwithout() throws Exception {
		fillForm(2006, false, false);
		assertEquals("Imie Nazwisko zostal zakwalifikowany do kategorii Blad danych",
				returnString.getText());
	}
	
	@Test
	public void adult1() throws Exception {
		fillForm(1999, true, true);
		assertEquals("Imie Nazwisko zostal zakwalifikowany do kategorii Dorosly",
				returnString.getText());
	}
	
	@Test
	public void adult2() throws Exception {
		fillForm(1999, false, true);
		assertEquals("Imie Nazwisko zostal zakwalifikowany do kategorii Dorosly",
				returnString.getText());
	}
	
	@Test
	public void adult3() throws Exception {
		fillForm(1999, false, false);
		assertEquals("Imie Nazwisko zostal zakwalifikowany do kategorii Dorosly",
				returnString.getText());
	}
	
	@Test
	public void senior() throws Exception {
		fillForm(1949, false, true);
		assertEquals("Imie Nazwisko zostal zakwalifikowany do kategorii Senior",
				returnString.getText());
	}
	
	@Test
	public void seniorWithout() throws Exception {
		fillForm(1949, false, false);
		assertEquals("Imie Nazwisko zostal zakwalifikowany do kategorii Blad danych",
				returnString.getText());
	}
	
	public void closeAlert() {
        Alert alert = waitDriver.until(ExpectedConditions.alertIsPresent());
        alert.accept();
        alert.dismiss();
	}

}
