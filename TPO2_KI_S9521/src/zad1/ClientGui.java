package zad1;

import javax.swing.*;

/**
 * Created by ikownacki on 18.03.2017.
 */
//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ClientGui {

    //Note: Typically the main method will be in a
    //separate class. As this is a simple one class
    //example it's all in the one class.
    public static void main(String[] args) {

        new ClientGui();
    }

    public ClientGui() {
        JFrame guiFrame = new JFrame();


        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Example GUI");
        guiFrame.setSize(360, 250);
        guiFrame.setLocationRelativeTo(null);


        String[] languages = {"ANG","FR"}; // TODO: 19.03.2017 wyjebać


        final JPanel jPanel1 = new JPanel();
        JComboBox<String> languageCode = new JComboBox<>(languages);
        JTextField textField = new JTextField(20);
        jPanel1.add(textField);
        jPanel1.add(languageCode);

        final JPanel jPanel2 = new JPanel();
        JLabel label = new JLabel("Tłumaczenie:");
        JLabel translatedWord = new JLabel("przetłumaczone gówno");
        jPanel2.add(label);
        jPanel2.add(translatedWord);

        JButton translate = new JButton("Tłumacz");
        translate.addActionListener(event -> {
            translatedWord.setText("dupa");

        });


        guiFrame.add(jPanel1, BorderLayout.NORTH);
        guiFrame.add(jPanel2, BorderLayout.CENTER);
        guiFrame.add(translate, BorderLayout.SOUTH);
        guiFrame.setVisible(true);
    }

}

