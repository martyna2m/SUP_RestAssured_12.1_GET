package configuration;

import java.util.Map;

public class Config {
    private static YamlReader yamlReader = new YamlReader();
    private static Map<String, Object> properties = yamlReader.readYamlFile("src/main/resources/config.yaml");

    public static Config getInstance() {
        initConfig();
        return Config.ConfigSingleton.INSTANCE;
    }

    private static void initConfig() {
        setActiveEnvironment(properties);
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
