import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import org.junit.experimental.theories.Theories;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.*;

import java.util.logging.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class StepImplementation extends  BaseTest {
    private static final Logger logger = Logger.getLogger(StepImplementation.class.getName());

    private double productPrice;
    @Step("Ürün sayfasındaki <productPriceElementP> fiyat alınır")
    public void productPagePrice(String productPriceElementP)
    {
        WebElement productPriceElement = driver.findElement(By.xpath(productPriceElementP));
        String productPriceText = productPriceElement.getText();
        productPrice = Double.parseDouble(productPriceText.replaceAll("[^0-9.]", ""));
        System.out.println(productPriceText);
    }

    @Step("<productPriceElement> Ürün fiyatı sepet fiyatıyla karşılaştırılır")
    public void comparePrice(String productPriceElement) {
        System.out.println("karşılaştır metoduna girildi");
        List<WebElement> cartItemPriceElements = driver.findElements(By.xpath(productPriceElement));
        // Her bir ürün fiyatını sepet fiyatlarıyla karşılaştırın
        for (WebElement cartItemPriceElement : cartItemPriceElements) {
            System.out.println("for icerisine girildi");
            String cartItemPriceText = cartItemPriceElement.getText();
            if (!cartItemPriceText.isEmpty()) {
                double cartItemPrice = Double.parseDouble(cartItemPriceText.replaceAll("[^0-9.]", ""));
                // Fiyatları karşılaştırın ve eşitlik kontrolü yapın
                Assertions.assertEquals(productPrice, cartItemPrice, 0.01); //0.01 tölerans değeri
                System.out.println("ProductPrice: " +productPrice +"ve"+ "CartItemPrice:" +cartItemPriceText);
            }
        }
    }






    @Step("<firstProduct> ürüne tıklanır")
    public void selectFirstProduct(String firstProduct)
    {
        WebElement firstProductElement=driver.findElement(LocatorHelper.getBy(firstProduct));
        firstProductElement.click();

    }





    @Step("Eğer Buton Görünüyorsa Tıkla")
    public void clickButtonIfVisible() {

            WebElement button = driver.findElement(LocatorHelper.getBy("understandButton"));
            if (button.isDisplayed()) {
                button.click();
                // Butonun locator'ını ve tıklama kodunu burada ekleyin
                // Örnek: driver.findElement(buttonLocator).click();
        }
            else
            {
                System.out.println("Button bulunamadı");
            }
    }






    @Step("<randomProductElement> Rastgele bir ürünü seç ve tıkla")
    public void selectRandomProduct(String randomProductElement) {
        // Ürünleri temsil eden locator'ı bul
        List<WebElement> productElements =driver.findElements(LocatorHelper.getBy(randomProductElement));

        // Ürünlerin sayısını al
        int productCount = productElements.size();
        System.out.println("Ürün sayısı: "+productCount);
        // Rastgele bir ürün seçmek için bir rastgele sayı oluştur
        Random random = new Random();
        int randomProductIndex = random.nextInt(productCount);
        System.out.println("Random Product Index"+randomProductIndex);

        // Rastgele seçilen ürünü tıkla
        WebElement randomProduct = productElements.get(randomProductIndex);
        randomProduct.click();
    }




    @Step("Yeni pencereye geçiş yap")
    public static void switchToNewWindow() {
        // Tüm sekme veya pencerelerin handles'ını alın
        Set<String> windowHandles = driver.getWindowHandles();

        // Yeni sekmenin handle'ını bulun
        for (String handle : windowHandles) {
            driver.switchTo().window(handle);
        }
    }


    @Step("Sayfada <locatorName> öğesine scroll yapılır")
    public void scrollToElement(String locatorName) {
        WebElement element = driver.findElement(LocatorHelper.getBy(locatorName));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
     //   int yOffset = -300; // Yukarıda ne kadar durmasını ayarladık
     //   ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, " + yOffset + ");");
    }

    @Step("Sayfanın yüklenmesi beklenir")
    public void waitForPageToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30L)); // Max bekleme süresi 30 saniye
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));
    }
    @Step("<locator> varlığı kontrol edilir <errorMessage>")
    public void verifyElementIsDisplayed(String locator,String errorMessage) {
        WebElement element = driver.findElement(LocatorHelper.getBy(locator));
        Assert.assertTrue(element.isDisplayed(), errorMessage);
       logger.info(element.isDisplayed()+ "verifyElementIsDisplayed metoduna girildi.");
    }
    @Step("<targetURL> URL'sinin geldiği kontrol edilir")
    public void verifyURL(String targetURL) {
        String currentURL = driver.getCurrentUrl(); // Mevcut sayfanın URL'sini alın
        System.out.println("CurrentURL: " +currentURL);
        // URL'nin beklendiği şekilde olduğunu doğrulayın veya bir şartı kontrol edin
        if (currentURL.equals(targetURL)) {
            logger.info("URL Doğru." +currentURL);
            System.out.println("URL dogru: " + currentURL);
        } else {
            logger.info("URL Yanlış." +currentURL);
        }
    }
    @Step("<locatorName> alanına <keyWord> yazılır")
    public void enterText(String locatorName,String keyWord)
    {
        WebElement textbox=driver.findElement(LocatorHelper.getBy(locatorName));
        textbox.sendKeys(keyWord);
    }

    @Step("Enter tuşuna basılır")
    public void PressEnter() {

        // Enter tuşuna basmak için Actions sınıfını kullanın
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ENTER).build().perform();
    }
    @Step("<locatorName> elementine tıklanır")
    public void clickElementByLocator(String locatorName) {
        WebElement element = driver.findElement(LocatorHelper.getBy(locatorName));
        element.click();
    }
    @Step("<locatorValue> yüklenmesi beklenir")
    public void WaitUntilLoad(String locatorName) {

        By elementLocator=LocatorHelper.getBy(locatorName);
        try {
            int timeoutSeconds = 30; // Bekleme süresi (örneğin, 30 saniye)
            Duration timeoutDuration = Duration.ofSeconds(timeoutSeconds); // Duration tipine çevirin
            WebDriverWait wait = new WebDriverWait(driver, timeoutDuration);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
            logger.info("Wait until load metodu çalışıyor");
        } catch (Exception e) {


            System.out.println("Element belirtilen süre içinde yüklenmedi.");
            // İsterseniz burada hata işleme veya raporlama yapabilirsiniz.
        }
    }
    @Step("Sayfa kullanıcının CSV dosyasını açar")
    public void openCSVFile() {
        String csvFilePath = "UserInfo/userInfos.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Her satırı işleme koyma kodu
                System.out.println(line); // Örnek olarak satırı ekrana yazdırır
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String userEmail; // userEmail tanımlandı
    private String userPassword; // userPassword tanımlandı
    @Step("Sayfa CSV dosyasından kullanıcı bilgilerini okur")
    public void readUserDataFromCSV() {
        String csvFilePath = "UserInfo/userInfos.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                userEmail = data[0].trim();
                userPassword = data[1].trim();
                //Logger.error("Data okundu");

                // Kullanıcı bilgilerini kullanmak için
            }
        } catch (IOException e) {
            e.printStackTrace();
            //Logger.error("Data okunurken hata oluştu");
        }
    }
    @Step(" <emailField> ve <passwordField> doldurulur")
    public void performLogin(String emailField,String passwordField) {
          driver.findElement(LocatorHelper.getBy(emailField)).sendKeys(userEmail);
          driver.findElement(LocatorHelper.getBy(passwordField)).sendKeys(userPassword);

       // enterEmail(userEmail);
       // enterPassword(userPassword);
    }
    public void enterEmail(String email) {
        driver.findElement(LocatorHelper.getBy("emailField")).sendKeys(email);

    }
    public void enterPassword(String password) {
        driver.findElement(LocatorHelper.getBy("passwordField")).sendKeys(password);
    }

    @Step("<second> Saniye kadar bekle")
    public void waitElement(int second) throws InterruptedException {
        Thread.sleep(1000*second);
    }



}

