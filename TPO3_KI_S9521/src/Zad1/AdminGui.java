package Zad1;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ikownacki on 07.04.2017.
 */
public class AdminGui {

    public AdminGui() {
        JFrame guiFrame = new JFrame();

        guiFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        guiFrame.setTitle("Administrator");
        guiFrame.setSize(400,300);
        guiFrame.setLocationRelativeTo(null);

        JPanel mainFramePanel = new JPanel(new GridLayout(0,2));
        JTabbedPane jTabbedPane = new JTabbedPane();




        String[] vegOptions = {"Asparagus", "Beans", "Broccoli", "Cabbage"
                , "Carrot", "Celery", "Cucumber", "Leek", "Mushroom"
                , "Pepper", "Radish", "Shallot", "Spinach", "Swede"
                , "Turnip"};

        JScrollPane scrollPane = new JScrollPane();
        JList<String> subjectsJList = new JList<>(vegOptions);
        scrollPane.setViewportView(subjectsJList);




        JPanel subjectsOptionJPanel = new JPanel(new GridLayout(6,1));
        JTextField subjectTextField = new JTextField();
        JButton addButon = new JButton("Dodaj");
        JButton deleteButon = new JButton("Usuń");
        subjectsOptionJPanel.add(subjectTextField);
        subjectsOptionJPanel.add(addButon);
        subjectsOptionJPanel.add(deleteButon);



        JPanel sendingOptionPannel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        JButton sendButton = new JButton("Wyślij");
        sendingOptionPannel.add(textArea, BorderLayout.CENTER);
        sendingOptionPannel.add(sendButton, BorderLayout.SOUTH);



        jTabbedPane.addTab("Tematy",subjectsOptionJPanel);
        jTabbedPane.addTab("Wysyłanie",sendingOptionPannel);

        mainFramePanel.add(scrollPane);
        mainFramePanel.add(jTabbedPane);

        guiFrame.add(mainFramePanel);

        guiFrame.setVisible(true);
    }



    public static void main(String[] args) {
        new AdminGui();
    }
}
