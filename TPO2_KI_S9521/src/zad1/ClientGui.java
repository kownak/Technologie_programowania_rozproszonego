package zad1;

import javax.swing.*;

/**
 * Created by ikownacki on 18.03.2017.
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;

public class ClientGui{

    public ClientGui() {
        JFrame guiFrame = new JFrame();


        guiFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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