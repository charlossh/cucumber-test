package stepDefinition;

import static converter.UnixTime.getConvertedDate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.LinkedHashMap;
import java.util.List;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestScenario {

    WebDriver driver;

    @Given("^Open chrome$")
    public void Open_chrome() throws Throwable {
        System.setProperty("webdriver.chrome.driver", "C:\\tmp\\chromedriver.exe");
        driver=new ChromeDriver();
        driver.manage().window().maximize();
    }
    @Given("^Navigate to anz$")
    public void NavigateToAnz() throws Throwable {
        driver.get("https://www.anz.com.au/personal/home-loans/calculators-tools/much-borrow/");
    }
    @Given("^Navigate to melia$")
    public void NavigateToMelia() throws Throwable {
        driver.get("https://www.melia.com/es/home.htm");
    }
    @Given("^Close melia cookies$")
    public void Close_melia_cookies() throws Throwable {
    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//button[contains(@id,'didomi-notice-agree-button')]")).click();
    }

    @When("^User enters valid details$")
    public void User_enters_valid_details() throws Throwable {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS) ;
        driver.findElement(By.id("application_type_single")).click();
        driver.findElement(By.xpath("//label[contains(@for,'home')]")).click();//input[contains(@aria-labelledby,'q2q2')]
        driver.findElement(By.xpath("//input[@aria-labelledby='q2q1']")).sendKeys("80000");
        driver.findElement(By.xpath("//input[@aria-labelledby='q2q2']")).sendKeys("10000");
        driver.findElement(By.xpath("//input[contains(@id,'expenses')]")).sendKeys("500");
        driver.findElement(By.xpath("//input[contains(@id,'otherloans')]")).sendKeys("100");
        driver.findElement(By.xpath("//input[@id='credit']")).sendKeys("10000");
        driver.findElement(By.xpath("//button[contains(@class,'calculate')]")).click();
    }

    @Then("^borrowing estimate is displayed correctly$")
    public void borrowing_estimate_is_displayed_correctly() throws Throwable {
        Thread.sleep(1000);
        //String actualamount = "$467,000";
        String actualamount = "$508,000";
        String item = driver.findElement(By.xpath("//span[@class='borrow__result__text__amount']")).getText();
        Assert.assertTrue(item.contains(actualamount));
    }

    @When("^User clicks start over button$")
    public void User_clicks_start_over_button() throws Throwable {
        driver.findElement(By.xpath("(//span[contains(@class,'icon icon_restart')])[1]")).click();
    }

    @Then("^Form is cleared$")
    public void Form_is_cleared() throws Throwable {
        String resetvalue = "0";
        String item1 = driver.findElement(By.xpath("//input[@aria-labelledby='q2q1']")).getAttribute("value");
        String item2 = driver.findElement(By.xpath("//input[@aria-labelledby='q2q2']")).getAttribute("value");
        String item3 = driver.findElement(By.xpath("//input[contains(@id,'expenses')]")).getAttribute("value");
        String item4 = driver.findElement(By.xpath("//input[@id='homeloans']")).getAttribute("value");
        String item5 = driver.findElement(By.xpath("//input[@id='otherloans']")).getAttribute("value");
        String item6 = driver.findElement(By.xpath("//input[contains(@aria-labelledby,'q3q4')]")).getAttribute("value");
        String item7 = driver.findElement(By.xpath("//input[@id='credit']")).getAttribute("value");

        Assert.assertTrue(item1.contains(resetvalue));
        Assert.assertTrue(item2.contains(resetvalue));
        Assert.assertTrue(item3.contains(resetvalue));
        Assert.assertTrue(item4.contains(resetvalue));
        Assert.assertTrue(item5.contains(resetvalue));
        Assert.assertTrue(item6.contains(resetvalue));
        Assert.assertTrue(item7.contains(resetvalue));
    }

    @When("^User does not enter all details$")
    public void User_does_not_enter_all_details() throws Throwable {
        driver.findElement(By.xpath("//input[@id='expenses']")).sendKeys("1");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[contains(@class,'calculate')]")).click();
    }

    @Then("^Correct error is displayed$")
    public void Correct_error_is_displayed() throws Throwable {
        //String errorMessage = "Based on the details you've entered, we're unable to give you an estimate of your borrowing power with this calculator. For questions, call us on 1800 100 641.";
        String errorMessage = "Based on the details you've entered, we're unable to give you an estimate of your borrowing power with this calculator. For questions, call us on 1800 035 500.";
        String item = driver.findElement(By.xpath("//span[contains(@class,'borrow__error__text')]")).getText();
        Assert.assertTrue(item.contains(errorMessage));
    }
    
    
    // Check that, when entering a not recognized hotel name (in this case, numbers)
    // A message is shown that the hotel has not been found
    @When("^Enter not recognized hotel name$")
    public void Enter_not_available_hotel_name() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("too-be")));
        driver.findElement(By.xpath("//input[@id='mbe-destination-input']")).click();
        driver.findElement(By.xpath("//input[@id='mbe-destination-input']")).sendKeys("123456789");
    }
    @Then ("^Unrecognized hotel message is displayed$")
    public void Unrecognized_hotel_message_is_displayed() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@class='mbe-results-not-found']/li")));
        String message = "Lo sentimos, nuestro sistema no reconoce este nombre";
        String item = driver.findElement(By.xpath("//ul[@class='mbe-results-not-found']/li")).getText();
        Assert.assertTrue(item.equalsIgnoreCase(message));
    }
    
    // User inputs hotel name MELIÁ CASTILLA for searching
    @When("^Enter valid name of a hotel$")
    public void Enter_valid_name_hotel() throws Throwable {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS) ;
        driver.findElement(By.xpath("//input[@id='mbe-destination-input']")).click();
        driver.findElement(By.xpath("//input[@id='mbe-destination-input']")).clear();
        driver.findElement(By.xpath("//input[@id='mbe-destination-input']")).sendKeys("MELIÁ CASTILLA");
    }
    
    // For the purpose of this test will be selected 25/02/2022 as checkin date
    @When("^Enter Checkin and Checkout dates$")
    public void Enter_checkin_checkout_dates() throws Throwable {
        long entrada = 1645775747;   //User will look for checkin date 25/02/2022
        
        // Method to convert unix date into YYYY/mm/dd date format
        LinkedHashMap<String, String> checkin = getConvertedDate(entrada);
        String fechaCheckin = checkin.get("fecha");
        String mes = checkin.get("mes");
        // fechaCheckin obtained = 2022/02/25
        // mes obtained = Febrero 2022
        
        driver.findElement(By.xpath("//div[@id='mbe-dates-select']")).click();
        WebDriverWait wait = new WebDriverWait(driver,10);
        List<WebElement> months = driver.findElements(By.xpath("//div[@class='mbe-month']/div[@class='month-name']"));
        
        for (WebElement m:months) {
            String nombreMes = m.getText();
            
            // In each loop will be checked if month name received does not match with element in website
            // and press the next month arrow until desired month is reached, then the loop will be finished
            if (!nombreMes.equalsIgnoreCase(mes)) {
                driver.findElement(By.xpath("//div[@class='mbe-controls']/div[@class='ico-arrow next-month active']")).click();
                Thread.sleep(500);
            }
            else {
                break;
            }
        }
        // Wait for date to be visible before clicking on it, available dates are shown in class attribute of web element
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[contains(@class, 'available') and @d='"+fechaCheckin+"']")));
        driver.findElement(By.xpath("//li[@d='"+fechaCheckin+"']/a/span")).click();
        Thread.sleep(500);
        
        long salida = 1646121347;   //User will look for checkout date 1/03/2022

        // Date is converted and checkout date is clicked in calendar
        LinkedHashMap<String, String> checkout = getConvertedDate(salida);
        String fechaCheckout = checkout.get("fecha");
        // fechaCheckout obtained = 2022/03/1
        
        // According to the calendar module in website, once the user has selected a chekin date, 
        // the allowed dates to be selected as checkout are maximum 2 months + 1 day after the checkin
        // Therefore, by clicking once on the arrow for next month, all available dates for checkout will be 
        // visible in calendar to be clicked
        driver.findElement(By.xpath("//div[@class='mbe-controls']/div[@class='ico-arrow next-month active']")).click();
        Thread.sleep(500);
                
        // Wait for date to be visible before clicking on it, available dates are shown in class attribute of web element
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[contains(@class, 'checkOut') and @d='"+fechaCheckout+"']")));
        driver.findElement(By.xpath("//li[@d='"+fechaCheckout+"']/a/span")).click();
        
    }
    
        
    // For the purpose of this test will be selected 1 room with 1 adult + 1 child
    // By changing from the default search of 2 adults + o children
    // Finally, child's age is setted to 10
    @When("^Select rooms and guests$")
    public void Select_rooms_and_guests() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver,10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("mbe-room-item")));
        // Select 1 adult less in form
        driver.findElement(By.xpath("//div[@class='mbe-room-item']/div[contains(@class,'adults')]/span[@class='mbe-btn-less']")).click();
        // Select 1 child more in form
        driver.findElement(By.xpath("//div[@class='mbe-room-item']/div[contains(@class,'children')]/span[@class='mbe-btn-more']")).click();
        
        WebElement ageField = driver.findElement(By.xpath("//div[@class='mbe-room-item']/div[@class='mbe-box-children-age']/div/div/input"));
        ageField.click();
        ageField.clear();
        ageField.sendKeys("10");
    }
    
    @When("^Press Search button$")
    public void Press_search_button() throws Throwable {
        WebDriverWait wait = new WebDriverWait(driver,10);
        WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mbe-search-button")));
        button.click();
    }
    
    // For the purpose of this test, the expected result of rooms listed is 9
    // for checkin = 25/02/2020, checkout = 1/03/2022, 1 room with 1 adult + 1 child
    @Then("^Available rooms are displayed$")
    public void Available_rooms_displayed() throws Throwable {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String url = driver.getCurrentUrl();
        String expectedUrl = "https://booking.melia.com/es/new/buscar/habitaciones-y-tarifas.htm";
        
        // In the results' page, some of the rooms received have the title like HABITACIÓN & HABITACION
        // And some Suites are also expected between the results
        List<WebElement> rooms = driver.findElements(By.partialLinkText("HABITAC"));
        List<WebElement> suites = driver.findElements(By.partialLinkText("SUITE"));
                
        int availableRooms = rooms.size() + suites.size();
        int expectedRooms = 9;
        Assert.assertTrue(url.equals(expectedUrl));
        Assert.assertEquals(availableRooms, expectedRooms);
        // Check results
    	Thread.sleep(3000);
    }
    
    
    
    
    @Then("^Check link ios app$")
    public void Check_link_app() throws Throwable {
    	String linkApp = "https://app.melia.com";
    	String item = driver.findElement(By.xpath("//div[contains(@class,'u-download-apps')]//a[1]")).getAttribute("href");
        Assert.assertTrue(item.contains(linkApp));
    }
    @Then("^Click link ios app$")
    public void Click_link_ios_app() throws Throwable {
    	driver.findElement(By.xpath("//div[contains(@class,'u-download-apps')]//a[1]")).click();
    	//Ver visualmente el resultado
    	Thread.sleep(4000);
    }
    @Then("^Application should be closed$")
    public void Application_should_be_closed() throws Throwable {
        driver.quit();
    }
}