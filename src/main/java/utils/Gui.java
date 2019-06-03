package main.java.utils;

import org.apache.logging.log4j.LogManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
import static main.java.utils.ConfigService.getConfigService;

public class Gui extends JPanel {
  private static Gui instance;

  private File[] thumbs;
  private String sampleMail;
  private String samplePassword;
  private String sampleLink;
  private boolean headless;
  private String fileName;
  private int sampleInt;

  private Gui() {
    LogManager.getLogger().info("Opening Gui.");
    try {
      thumbs = new File(new File("").getCanonicalFile().toPath().toAbsolutePath().toString()
              + "/thumbs").listFiles();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static boolean isGuiCreated() {
    return instance != null;
  }

  public static Gui getInstance() {
    if (instance == null) instance = new Gui();
    return instance;
  }

  public void openJPanel() {
    // <== buttons ==>
    JTextField emailField = new JTextField(getConfigService().getStringProperty("linkedin.loginEmail"));
    emailField.addMouseListener(addMouseListenerForTextField(emailField));

    JPasswordField passwordField = new JPasswordField(getConfigService().getStringProperty("linkedin.loginPassword"));
    passwordField.addMouseListener(addMouseListenerForTextField(passwordField));

    JTextField searchLinkField = new JTextField(getConfigService().getStringProperty("general.searchLinkAddress"));
    searchLinkField.addMouseListener(addMouseListenerForTextField(searchLinkField));

    JTextField fileNameField = new JTextField("name");
    fileNameField.addMouseListener(addMouseListenerForTextField(fileNameField));

    JTextField limitField = new JTextField("10");
    limitField.addMouseListener(addMouseListenerForTextField(limitField));

    JCheckBox headlessCheckBox = new JCheckBox("headless", true);
    headlessCheckBox.setFont(new Font("Arial", Font.BOLD, 12));
    headlessCheckBox.setForeground(new Color(50, 50, 50));

    JPanel myPanel = new JPanel();
    myPanel.setPreferredSize(new Dimension(350, 250));
    myPanel.setFont(new Font("Arial", Font.PLAIN, 14));
    myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

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
      if (!headlessCheckBox.isSelected()) headlessCheckBox.setText("headless (suggested only for debugging!)");
      else headlessCheckBox.setText("headless");
    });
    headlessCheckBox.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseEntered(MouseEvent e) {
        headlessCheckBox.setForeground(Color.black);
        headlessCheckBox.setFont(new Font("Arial", Font.BOLD, 12));
      }

      @Override
      public void mouseExited(MouseEvent e) {
        headlessCheckBox.setForeground(new Color(50, 50, 50));
        headlessCheckBox.setFont(new Font("Arial", Font.PLAIN, 12));
      }
    });
    myPanel.add(headlessCheckBox);

    int result = JOptionPane.showConfirmDialog(null, myPanel,
            "Please enter values", 2, 1, getRandomThumbIcon());


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
    JTextArea textArea = new JTextArea(getTextFromFile(Paths.get("logs").toAbsolutePath().toString().concat("/last_test_log.log")), 28, 75);
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

  private ImageIcon getRandomThumbIcon() {
//        TODO: compile /thumbs/ in jar file
    return new ImageIcon(thumbs[new Random().nextInt(thumbs.length)].getAbsolutePath());
  }

  private MouseAdapter addMouseListenerForTextField(JTextField component) {
    component.setForeground(new Color(50, 50, 50));
    component.setEditable(false);
    final int[] maxClickToSelect = {0};
    return new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        component.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        maxClickToSelect[0]++;
        if (maxClickToSelect[0] == 1)
          component.selectAll();
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        component.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        component.setForeground(Color.BLACK);
        component.setEditable(true);
        component.setFont(new Font("Arial", Font.BOLD, 14));
      }


      @Override
      public void mouseExited(MouseEvent e) {
        component.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        component.setForeground(new Color(50, 50, 50));
        component.setEditable(false);
        maxClickToSelect[0] = 0;
        component.select(0, 0);
        component.setFont(new Font("Arial", Font.PLAIN, 12));
      }
    };
  }
}