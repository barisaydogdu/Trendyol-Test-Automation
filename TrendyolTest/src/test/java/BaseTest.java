import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseTest {
    public static WebDriver driver;

    private String baseUrl = "https://www.trendyol.com/"; // Projeye özgü URL'yi burada tanımlayın
    @BeforeScenario
    public void setUp() {
        // WebDriver başlatma işlemleri
        System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe"); // ChromeDriver'ın yolu
        //  ChromeOptions options = new ChromeOptions();
        // İsteğe bağlı olarak Chrome seçenekleri ayarlayabilirsiniz.
        // Örneğin, başlatma seçenekleri, proxy ayarları, başlatma sayfası vb. ekleyebilirsiniz.
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(baseUrl);
    }

    @AfterScenario
    public void tearDown() {
        // WebDriver kapatma işlemi
        if (driver != null) {
            driver.quit();
        }
    }
}



