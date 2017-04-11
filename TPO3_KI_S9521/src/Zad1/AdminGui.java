package Zad1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created by ikownacki on 07.04.2017.
 */
public class AdminGui {

    public AdminGui() {
        JFrame mainFrame = new JFrame();

        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setTitle("Administrator");
        mainFrame.setSize(400,300);
        mainFrame.setLocationRelativeTo(null);

        JPanel mainFramePanel = new JPanel(new GridLayout(0,2));
        JTabbedPane jTabbedPane = new JTabbedPane();




        JScrollPane scrollPane = new JScrollPane();
        DefaultListModel<String> model= new DefaultListModel<>();
        JList<String> subjectsJList = new JList<>(model);
        subjectsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scrollPane.setViewportView(subjectsJList);



        JPanel subjectsOptionJPanel = new JPanel(new GridLayout(6,1));
        JTextField subjectTextField = new JTextField();
        JButton addButon = new JButton("Dodaj");
        addButon.addActionListener(e -> {
            String string = subjectTextField.getText();
            if(string == null || string.equals("") || model.contains(string)){
                return;
            }
            model.addElement(string);
            subjectTextField.setText("");
        });
        JButton deleteButon = new JButton("Usuń");
        deleteButon.addActionListener(e -> {
            String string = subjectTextField.getText();
            if (string ==null || string.equals("")){
                return;
            }
            model.removeElement(string);

        });
        subjectsOptionJPanel.add(subjectTextField);
        subjectsOptionJPanel.add(addButon);
        subjectsOptionJPanel.add(deleteButon);

        final String[] selectedSubject = new String[1];

        JPanel sendingOptionPannel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        JButton sendButton = new JButton("Wyślij");
        sendingOptionPannel.add(textArea, BorderLayout.CENTER);
        sendingOptionPannel.add(sendButton, BorderLayout.SOUTH);


        subjectsJList.addListSelectionListener(e -> {
            String string = subjectsJList.getSelectedValue();
            subjectTextField.setText(string);
            selectedSubject[0] = string;
        });


        jTabbedPane.addTab("Tematy",subjectsOptionJPanel);
        jTabbedPane.addTab("Wysyłanie",sendingOptionPannel);

        mainFramePanel.add(scrollPane);
        mainFramePanel.add(jTabbedPane);

        mainFrame.add(mainFramePanel);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new AdminGui();
    }
}
