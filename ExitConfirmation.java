package com.mycompany.smartecommercesystem.ui;

import javax.swing.*;

public class ExitConfirmation {

    private ExitConfirmation() {
    }

    public static void showExitConfirmation(JFrame parent) {
        int choice = JOptionPane.showConfirmDialog(
            parent,
            "Are you sure you want to exit?",
            "Exit Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
