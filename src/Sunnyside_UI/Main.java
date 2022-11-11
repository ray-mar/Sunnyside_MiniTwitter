package Sunnyside_UI;

import javax.swing.*;
import java.awt.*;


public class Main extends JFrame {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Admin frame = Admin.getInstance();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
