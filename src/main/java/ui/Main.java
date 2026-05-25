package ui;

import java.util.Locale;
import javax.swing.SwingUtilities;

public class Main {

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      WelcomeView view = new WelcomeView(Locale.getDefault());
      view.setVisible(true);
    });
  }
}
