package main.java.tools;

import org.apache.logging.log4j.LogManager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigurationParser {
    private static ConfigurationParser instance;

    private InputStream inputStream;
    private Properties prop;
    private String path;
    private String propertiesFileName;
    private String linkAddress;
    private int implicitlyWaitTime;


    private int pageLoadTime;
    private String searchLinkAddress;

    private String loginEmail;
    private String loginPassword;

    private String receiverEmail;
    private String senderEmail;
    private String emailLogin;
    private String emailPassword;
    private String sendReport;

    private String conUrl;
    private String sqlLogin;
    private String sqlPassword;

    private ConfigurationParser() {
        LogManager.getLogger(this).info("Config created.");
        this.propertiesFileName = "config.properties";
        prop = new Properties();
        path = Paths.get("src", "main", "resources").toAbsolutePath().toString().concat("/config.properties");
        try {
            inputStream = new FileInputStream(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error when processing config file " + propertiesFileName, e);
        }

        this.linkAddress = getParameterValue("linkAddress");
        this.implicitlyWaitTime = Integer.parseInt(getParameterValue("implicitlyWaitTime"));
        this.pageLoadTime = Integer.parseInt(getParameterValue("pageLoadTime"));
        this.searchLinkAddress = getParameterValue("searchLinkAddress");

        this.loginEmail = getParameterValue("loginEmail");
        this.loginPassword = getParameterValue("loginPassword");

        this.receiverEmail = getParameterValue("receiverEmail");
        this.senderEmail = getParameterValue("senderEmail");
        this.emailLogin = getParameterValue("emailLogin");
        this.emailPassword = getParameterValue("emailPassword");
        this.sendReport = getParameterValue("sendReport");

        this.conUrl = getParameterValue("conUrl");
        this.sqlLogin = getParameterValue("sqlLogin");
        this.sqlPassword = getParameterValue("sqlPassword");
    }

    public static ConfigurationParser getInstance() {
        if (instance == null) {
            instance = new ConfigurationParser();
        }
        return instance;
    }

    private String getParameterValue(String name) {
        return prop.getProperty(name);
    }

    public String getLinkAddress() {
        return this.linkAddress;
    }

    public int getImplicitlyWaitTime() {
        return this.implicitlyWaitTime;
    }

    public int getPageLoadTime() {
        return this.pageLoadTime;
    }

    public String getSearchLinkAddress() {
        return this.searchLinkAddress;
    }

    public String getLoginEmail() {
        return this.loginEmail;
    }

    public String getLoginPassword() {
        return this.loginPassword;
    }

    public String getReceiverEmail() {
        return this.receiverEmail;
    }

    public String getSenderEmail() {
        return this.senderEmail;
    }

    public String getEmailLogin() {
        return this.emailLogin;
    }

    public String getEmailPassword() {
        return this.emailPassword;
    }

    public boolean getSendReport() {
        return Boolean.parseBoolean(this.sendReport);
    }

    public String getConUrl() {
        return this.conUrl;
    }

    public String getSqlLogin() {
        return this.sqlLogin;
    }

    public String getSqlPassword() {
        return this.sqlPassword;
    }
}