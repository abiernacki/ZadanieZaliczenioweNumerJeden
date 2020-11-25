package ZadanieWarsztatoweJeden;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.eo.Se;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CreateNewAddress {

    private WebDriver driver;

    private String generatedAlias;

    @Given("wlaczamy strone sklepu PrestaShop, mamy zarejestrowanego uzytkownika")
    public void userIsLoggedInPrestaShop() {

        System.setProperty("webdriver.chrome.driver",
                "src/main/resources/drivers/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.get("https://prod-kurs.coderslab.pl/");
    }

    @When("logowanie na stworzonego uzytkownika")
    public void logowanieNaStworzonegoUzytkownikaIWcisniecieKafelkaAdresses() {

        WebElement signIn = driver.findElement(By.xpath("//*[@id=\"_desktop_user_info\"]/div/a"));
        signIn.click();

        WebElement emailInput = driver.findElement(By.name("email"));
        emailInput.clear();
        emailInput.sendKeys("jankowalski@o.pl");

        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.clear();
        passwordInput.sendKeys("kotek777");

        WebElement signInButton = driver.findElement(By.id("submit-login"));
        signInButton.click();
    }

    @And("klikniecie kafelka adresses i Create new address i wypełnienie (.*), (.*), (.*), (.*)")
    public void klikniecieWCreateNewAddressIWypełnienieFormularza(String address, String city, String postalCode, String phone) {

        boolean isAnyAddressExist;

        try {
            WebElement addressesWindow = driver.findElement(By.id("addresses-link"));
            isAnyAddressExist = true;
        } catch (NoSuchElementException e) {
            isAnyAddressExist = false;
        }

        if (isAnyAddressExist)
        {
            WebElement addressesWindow = driver.findElement(By.id("addresses-link"));
            addressesWindow.click();
            WebElement createNewAddress = driver.findElement(By.cssSelector("#content > div.addresses-footer > a > span"));
            createNewAddress.click();
        } else {
            WebElement addressesWindow = driver.findElement(By.id("address-link"));
            addressesWindow.click();
        }

        Random random = new Random();
        generatedAlias = String.valueOf(random.nextInt(1000000));

        WebElement alaisInput = driver.findElement(By.name("alias"));
        alaisInput.clear();
        alaisInput.sendKeys(generatedAlias);

        WebElement addressInput = driver.findElement(By.name("address1"));
        addressInput.clear();
        addressInput.sendKeys(address);

        WebElement cityInput = driver.findElement(By.name("city"));
        cityInput.clear();
        cityInput.sendKeys(city);

        WebElement postalCodeInput = driver.findElement(By.name("postcode"));
        postalCodeInput.clear();
        postalCodeInput.sendKeys(postalCode);

        WebElement countryInput = driver.findElement(By.xpath("//select/option[@value ='17']"));
        countryInput.click();

        WebElement phoneInput = driver.findElement(By.name("phone"));
        phoneInput.clear();
        phoneInput.sendKeys(phone);

        WebElement saveButton = driver.findElement(By.xpath("/html/body/main/section/div/div/section/section/div/div/form/footer/button"));
        saveButton.click();
    }

    @Then("sprawdzenie czy dane w podanym adresie sa poprawne")
    public void sprawdzenieCzyDaneWPodanymAdresieSaPoprawne() {

        List<WebElement> addressElements = driver.findElements(By.tagName("article"));
//        WebElement element = null;


//        for (WebElement addressElement : addressElements) {
//            //System.out.println(addressElement.getText());
//            if (addressElement.getText().contains(generatedAlias)) {
//                System.out.println(addressElement.getText());
//                element = addressElement;
//            }
//        }

        String addressText = addressElements.get(addressElements.size()-1).getText();
        Assert.assertTrue(addressText.contains("ul. Rakowiecka 15"));
        Assert.assertTrue(addressText.contains("Warszawa"));
        Assert.assertTrue(addressText.contains("02-432"));
        Assert.assertTrue(addressText.contains("111222333"));
    }
}