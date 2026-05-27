package ui;

import javax.swing.SwingUtilities;

/**
 * Launches the chess application.
 */
public class Main {

  /**
   * Starts the application UI.
   */
  public static void main(String[] args) {
    SwingUtilities.invokeLater(
        () -> new WelcomeView().setVisible(true));
  }
}