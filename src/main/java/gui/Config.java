package gui;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Config {

    public static void save(String endpoint, String jmeno, String heslo) {

        PropertiesConfiguration prop;
        try {
            prop = new PropertiesConfiguration("config.properties");
            prop.setProperty("endpoint", endpoint);
            prop.setProperty("userName", jmeno);
            prop.setProperty("password", heslo);
            prop.save();

        } catch (ConfigurationException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public static String loadEndpoint() {

        PropertiesConfiguration prop;
        try {
            prop = new PropertiesConfiguration("config.properties");
            return (String) prop.getProperty("endpoint");

        } catch (ConfigurationException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public static String loadUserName() {
        PropertiesConfiguration prop;
        try {
            prop = new PropertiesConfiguration("config.properties");
            return (String) prop.getProperty("userName");

        } catch (ConfigurationException ex) {
            System.err.println(ex.getMessage());
        }
        return null;

    }

    public static String loadPassword() {
        PropertiesConfiguration prop;
        try {
            prop = new PropertiesConfiguration("config.properties");
            return (String) prop.getProperty("password");

        } catch (ConfigurationException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public static void saveInputPath(String inputPath) {
        PropertiesConfiguration prop;
        try {
            prop = new PropertiesConfiguration("config.properties");
            prop.setProperty("inputPath", inputPath);
            prop.save();

        } catch (ConfigurationException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static String loadInputPath() {
        PropertiesConfiguration prop;
        try {
            prop = new PropertiesConfiguration("config.properties");
            return (String) prop.getProperty("inputPath");

        } catch (ConfigurationException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public static void saveOutputPath(String outputPath) {
        PropertiesConfiguration prop;
        try {
            prop = new PropertiesConfiguration("config.properties");
            prop.setProperty("outputPath", outputPath);
            prop.save();

        } catch (ConfigurationException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static String loadOutputPath() {
        PropertiesConfiguration prop;
        try {
            prop = new PropertiesConfiguration("config.properties");
            return (String) prop.getProperty("outputPath");

        } catch (ConfigurationException ex) {
            System.err.println(ex.getMessage());
        }
        return null;

    }

}
