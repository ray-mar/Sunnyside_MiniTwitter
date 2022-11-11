package Sunnyside_UI;
import javax.swing.*;

public class errorMessage {

    public errorMessage(String title, String message, int messageType) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }

}
