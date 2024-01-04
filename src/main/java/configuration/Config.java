package configuration;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.Map;

public class Config {
    private WebDriver driver;
    private static YamlReader yamlReader = new YamlReader();
    private static Map<String, Object> properties = yamlReader.readYamlFile("src/main/resources/config.yaml");

    public static Config getInstance() {
        initConfig();
        return Config.ConfigSingleton.INSTANCE;
    }

    private static void initConfig() {
        setActiveEnvironment(properties);
        setActiveBrowser(properties);
    }


    public static void setActiveBrowser(Map<String, Object> data) {
        String browserValue = (String) data.get("browser");
        System.setProperty("browser", browserValue);
    }


    public WebDriver getDriver() {

        if (System.getProperty("browser") == null) {
            System.out.println("Active browser information is missing. Switching to default.");

            ChromeOptions chromeOptions = new ChromeOptions();
            WebDriverManager.chromedriver().setup();
            chromeOptions.addArguments("start-maximized");
            driver = new ChromeDriver(chromeOptions);


        } else {

            switch (System.getProperty("browser").toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    WebDriverManager.chromedriver().setup();
                    chromeOptions.addArguments("start-maximized");
                    driver = new ChromeDriver(chromeOptions);
                    break;

                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    WebDriverManager.edgedriver().setup();
                    edgeOptions.addArguments("start-maximized");
                    driver = new EdgeDriver(edgeOptions);
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    WebDriverManager.firefoxdriver().setup();
                    firefoxOptions.addArguments("start-maximized");
                    driver = new FirefoxDriver(firefoxOptions);
            }
        }
        return this.driver;
    }

    public static void setActiveEnvironment(Map<String, Object> data) {

        Map<String, Object> environments = (Map<String, Object>) data.get("environment");

        for (Map.Entry<String, Object> entry : environments.entrySet()) {
            Map<String, Object> envProperties = (Map<String, Object>) entry.getValue();
            Boolean isActive = (Boolean) envProperties.get("active");
            if (isActive != null && isActive) {
                envProperties.forEach((key, value) -> {
                    if (!key.equals("active")) {
                        System.setProperty(key, value.toString());
                    }
                });
                break;
            }
        }

    }


    private static class ConfigSingleton {
        private static final Config INSTANCE = new Config();

    }
}
