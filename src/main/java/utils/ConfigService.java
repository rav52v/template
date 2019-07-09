package main.java.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.context.environment.ImmutableEnvironment;
import org.cfg4j.source.context.filesprovider.ConfigFilesProvider;
import org.cfg4j.source.files.FilesConfigurationSource;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class ConfigService {
  private static final ConfigurationProvider configProvider = configProvider();
  private static ConfigService instance = null;
  private static Logger log = LogManager.getLogger();

  private ConfigService() {
  }

  public static ConfigService getConfigService() {
    if (instance == null) {
      instance = new ConfigService();
      log.debug("Config created.");
    }
    return instance;
  }

  private static ConfigurationProvider configProvider() {
    final String PATH_TO_CONFIG_PACKAGE = "./src/main/resources/config";
    ConfigFilesProvider configFilesProvider = () -> {
      File[] files = new File(System.getProperty("user.dir"), PATH_TO_CONFIG_PACKAGE).listFiles();
      ArrayList<Path> paths = new ArrayList<>();
      Arrays.asList(files).forEach(file -> paths.add(file.toPath()));
      return paths;
    };

    return new ConfigurationProviderBuilder()
            .withConfigurationSource(new FilesConfigurationSource(configFilesProvider))
            .withEnvironment(new ImmutableEnvironment(PATH_TO_CONFIG_PACKAGE))
            .build();
  }

  public String getStringProperty(String property) {
    return (String) getProperty(property, String.class);
  }

  public Boolean getBooleanProperty(String property) {
    return (Boolean) getProperty(property, Boolean.class);
  }

  public Long getLongProperty(String property) {
    return (Long) getProperty(property, Long.class);
  }

  public <T> Object getProperty(String property, Class<T> tClass) {
    Object result = System.getProperty(property);
    if (result == null) result = configProvider.getProperty(property, tClass);
    else {
      switch (tClass.getCanonicalName()) {
        case "java.lang.Boolean":
          result = Boolean.parseBoolean(System.getProperty(property));
          break;
        case "java.lang.Long":
          result = Long.parseLong(System.getProperty(property));
          break;
        default:
          result = System.getProperty(property);
      }
    }

    return result;
  }
}