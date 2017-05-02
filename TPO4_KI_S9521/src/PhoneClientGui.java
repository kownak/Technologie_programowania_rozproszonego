import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;

/**
 * Created by ikownacki on 30.04.2017.
 */
public class PhoneClientGui {
    PhoneDirectoryInterface phoneDirectoryInterface;

    private JFrame mainFrame;
    private JTextField nameTF;
    private JTextField phoneNumberTF;
    Button addUpdateButton;
    Button searchButton;

    public PhoneClientGui(PhoneDirectoryInterface phoneDirectoryInterface) {
        this.phoneDirectoryInterface = phoneDirectoryInterface;
        mainFrame = new JFrame();
        nameTF = new JTextField();
        phoneNumberTF = new JTextField();
        addUpdateButton = new Button("Dodaj/Aktualizuj");
        searchButton = new Button("Szukaj");

        addUpdateButton.addActionListener(e -> {
            String name = nameTF.getText();
            String phoneNumber = phoneNumberTF.getText();

            if ((name != null && !name.equals("")) &&
                    (phoneNumber != null && !phoneNumber.equals(""))) {

                try {
                    String numberFromPD = phoneDirectoryInterface.getPhoneNumber(name);
                    if (numberFromPD != null){
                        phoneDirectoryInterface.replacePhoneNumber(name,phoneNumber);
                    }else{
                        phoneDirectoryInterface.addPhoneNumber(name,phoneNumber);
                    }
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }

            }

        });

        searchButton.addActionListener(e -> {
            String name = nameTF.getText();
            String phoneNumber;
            if (name != null && !name.equals("")){
                try {
                    phoneNumber = phoneDirectoryInterface.getPhoneNumber(name);
                    if(phoneNumber != null){
                        phoneNumberTF.setText(phoneNumber);
                    }else{
                        JOptionPane.showMessageDialog(mainFrame, "Brak wpisu w książce");
                    }
                } catch (RemoteException e1) {
                    e1.printStackTrace();
                }
            }

        });

        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLayout(new GridLayout(4,0));
        mainFrame.add(nameTF);
        mainFrame.add(phoneNumberTF);
        mainFrame.add(addUpdateButton);
        mainFrame.add(searchButton);
        mainFrame.setSize(250,250);
        mainFrame.setVisible(true);
    }


}
