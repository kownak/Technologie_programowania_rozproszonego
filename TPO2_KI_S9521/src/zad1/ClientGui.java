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
import java.util.List;

public class ClientGui{

    public ClientGui(int outPort, String[] languages, String message, List<Integer> usedPorts) {
        ClientLogic clientLogic =  new ClientLogic(outPort);

        JFrame guiFrame = new JFrame();

        guiFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        guiFrame.setTitle("Słownik");
        guiFrame.setSize(360, 250);
        guiFrame.setLocationRelativeTo(null);

        JPanel generalPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(generalPanel,BoxLayout.Y_AXIS);
        generalPanel.setLayout(boxLayout);


        final JPanel jPanel1 = new JPanel();
        JComboBox<String> languageCode = new JComboBox<>(languages);
        JTextField tf_wordToTranslate = new JTextField(20);
        jPanel1.add(tf_wordToTranslate);
        jPanel1.add(languageCode);

        final JPanel jPanel2 = new JPanel();
        JLabel portLabel = new JLabel("Port:");
        JTextField tf_port = new JTextField(6);
        jPanel2.add(portLabel);
        jPanel2.add(tf_port);

        final JPanel jPanel3 = new JPanel();
        JLabel label = new JLabel("Tłumaczenie:");
        JLabel translatedWord = new JLabel(message);
        jPanel3.add(label);
        jPanel3.add(translatedWord);

        JButton translate = new JButton("Tłumacz");
        translate.addActionListener(event -> {
            String wordToTranslate = tf_wordToTranslate.getText();
            String lCode = (String) languageCode.getSelectedItem();
            try{

                int clientInPort = Integer.parseInt(tf_port.getText());
                String translatedWordString =  !usedPorts.contains(clientInPort) ?
                        clientLogic.translateWord(wordToTranslate,lCode,clientInPort) : "Port jest już zajęty";
                translatedWord.setText(translatedWordString);

            }catch (NumberFormatException e){
                translatedWord.setText("Błędny numer portu");
            }

        });

        generalPanel.add(jPanel1);
        generalPanel.add(jPanel2);
        generalPanel.add(jPanel3);
        generalPanel.add(translate);

        guiFrame.add(generalPanel);
        guiFrame.setVisible(true);
    }

}