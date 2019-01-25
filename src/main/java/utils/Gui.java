package main.java.utils;

import main.java.tools.ConfigurationParser;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

public class Gui extends JFrame {
    private static Gui instance;

    private String sampleMail;
    private String samplePassword;
    private String sampleLink;
    private boolean headless;
    private String fileName;
    private int sampleInt;

    private Gui() {

    }

    public static Gui getInstance() {
        if (instance == null) {
            instance = new Gui();
        }
        return instance;
    }

    public void openJPanel() {
        // <== buttons ==>
        JTextField emailField = new JTextField(ConfigurationParser.getInstance().getLoginEmail(), 15);
        JTextField passwordField = new JTextField(ConfigurationParser.getInstance().getLoginPassword(), 12);
        JTextField searchLinkField = new JTextField(ConfigurationParser.getInstance().getSearchLinkAddress(), 80);
        JTextField fileNameField = new JTextField("name", 80);
        JTextField limitField = new JTextField("10", 5);
        JCheckBox headlessCheckBox = new JCheckBox("start in headless mode", true);
        headlessCheckBox.setFont(new Font("Arial", Font.BOLD, 14));


        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
        myPanel.requestFocus();
        myPanel.add(new Label("email:"));
        myPanel.add(emailField);
        myPanel.add(new Label("password:"));
        myPanel.add(passwordField);
        myPanel.add(new Label("link:"));
        myPanel.add(searchLinkField);
        myPanel.add(new Label("file name:"));
        myPanel.add(fileNameField);
        myPanel.add(new Label("sampleInt"));
        myPanel.add(limitField);
        myPanel.add(headlessCheckBox);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter values", JOptionPane.OK_CANCEL_OPTION);

        // <== setter ==>
        if (result == JOptionPane.OK_OPTION) {
            sampleMail = emailField.getText();
            samplePassword = passwordField.getText();
            sampleLink = searchLinkField.getText();
            fileName = fileNameField.getText();
            sampleInt = Integer.valueOf(limitField.getText());
            headless = headlessCheckBox.isSelected();
        } else
            System.exit(0);
    }

    public void showLogInfo() {

        JPanel myPanel = new JPanel();
        myPanel.setBackground(Color.cyan);
        myPanel.setLocation(220, 10);
        JTextArea textArea = new JTextArea(getTextFromFile(Paths.get("logs").toAbsolutePath().toString().concat("/short_log.log")), 28, 70);
        textArea.setBackground(Color.cyan);

        JScrollPane jScrollPane = new JScrollPane(textArea);
        jScrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(jScrollPane);
        setTitle("Summary");
        setVisible(true);
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        myPanel.add(jScrollPane);

        add(myPanel);
    }

    public String getSampleMail() {
        return sampleMail;
    }

    public String getSamplePassword() {
        return samplePassword;
    }

    public String getSearchLinkAddress() {
        return sampleLink;
    }

    public boolean isHeadless() {
        return headless;
    }

    public String getFileName() {
        return fileName;
    }

    public int getSampleInt(){
        return sampleInt;
    }

    private String getTextFromFile(String fullFilePath){
        String content = "";
        try{
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(fullFilePath));
            StringBuilder sb = new StringBuilder();
            String line;
            String ls = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append(ls);
            }
            reader.close();
            content = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
