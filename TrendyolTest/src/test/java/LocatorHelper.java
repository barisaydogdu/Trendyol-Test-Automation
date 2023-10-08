//import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LocatorHelper {
    private static final String LOCATOR_JSON_FILE_PATH = "LocatorsJson/locators.json"; // JSON dosyanızın yolu

    public static By getBy(String locatorName) {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(LOCATOR_JSON_FILE_PATH)));
            JSONObject jsonObject = new JSONObject(jsonContent);

            // İstenen öğenin bilgilerini al
            JSONObject locator = jsonObject.getJSONObject(locatorName);
            String type = locator.getString("type");
            String value = locator.getString("value");

            // By nesnesini oluştur ve döndür
            switch (type) {
                case "id":
                    return By.id(value);
                    case "classname":
                    return By.className(value);
                case "name":
                    return By.name(value);
                case "xpath":
                    return By.xpath(value);
                case "css":
                    return By.cssSelector(value);
                // Diğer locator türleri için gerekli işlemleri ekleyebilirsiniz.
                default:
                    throw new IllegalArgumentException("Geçersiz locator türü: " + type);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}