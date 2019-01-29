package main.java.utils;

import main.java.tools.ConfigurationParser;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

public class Gui extends JPanel {
    private static Gui instance;

    private final Path thumbs = Paths.get("src", "main", "resources", "thumbs");
    private String sampleMail;
    private String samplePassword;
    private String sampleLink;
    private boolean headless;
    private String fileName;
    private int sampleInt;

    private Gui() {}

    public static Gui getInstance() {
        if (instance == null)
            instance = new Gui();
        return instance;
    }

    public void openJPanel() {
        // <== buttons ==>
        JTextField emailField = new JTextField(ConfigurationParser.getInstance().getLoginEmail(), 15);
        emailField.selectAll();

        JTextField passwordField = new JTextField(ConfigurationParser.getInstance().getLoginPassword(), 12);
        passwordField.selectAll();

        JTextField searchLinkField = new JTextField(ConfigurationParser.getInstance().getSearchLinkAddress(), 80);
        searchLinkField.selectAll();

        JTextField fileNameField = new JTextField("name", 80);
        fileNameField.selectAll();

        JTextField limitField = new JTextField("10", 5);
        JCheckBox headlessCheckBox = new JCheckBox("headless", true);
        headlessCheckBox.setFont(new Font("Arial", Font.BOLD, 12));
        JPanel myPanel = new JPanel();
        myPanel.setPreferredSize(new Dimension(350, 250));
        myPanel.setFont(new Font("Arial", Font.BOLD, 14));
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
        headlessCheckBox.addActionListener(e -> {
            if (!headlessCheckBox.isSelected()){
                headlessCheckBox.setForeground(Color.red);
                headlessCheckBox.setText("headless (suggested only for debugging!)");
            }
            else{
                headlessCheckBox.setForeground(Color.black);
                headlessCheckBox.setText("headless");
            }

        });
        myPanel.add(headlessCheckBox);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter values", 2, 1, new ImageIcon(thumbs.toAbsolutePath().toString() + "/sample.png"));

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
        myPanel.setForeground(Color.black);
        myPanel.setLocation(220, 10);
        JTextArea textArea = new JTextArea(getTextFromFile(Paths.get("logs").toAbsolutePath().toString().concat("/short_log.log")), 28, 75);
        textArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        textArea.setBackground(Color.black);
        textArea.setForeground(new Color(80, 80, 255));
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);

        JScrollPane jScrollPane = new JScrollPane(textArea);
        jScrollPane.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(jScrollPane);
        setVisible(true);
        myPanel.add(jScrollPane);
        add(myPanel);

        JOptionPane.showMessageDialog(null, myPanel, "Log", JOptionPane.PLAIN_MESSAGE, null);
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

    public int getSampleInt() {
        return sampleInt;
    }

    private String getTextFromFile(String fullFilePath) {
        String content = "";
        try {
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
