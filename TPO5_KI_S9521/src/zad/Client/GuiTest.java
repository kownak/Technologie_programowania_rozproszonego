package zad.Client;

import zad.Bank.BankOperationsInterface;
import zad.Utils.*;
import zad.Store.StoreOperationsInterface;

import javax.swing.*;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * Created by ikownacki on 13.05.2017.
 */
public class GuiTest extends JFrame {
    private JTabbedPane tabbedPane1;
    private JPanel mainPanel;
    private JList<ProductWrapper> productsJList;
    private JButton addButton;
    private JList<ProductWrapper> cartJList;
    private JButton buyButton;
    private JList<BillWrapper> billsJList;
    private JTextArea textArea1;
    private JButton refreshBillsButton;
    private JButton refreshProductsButton;
    private JButton deleteButton;

    private DefaultListModel<ProductWrapper> productListModel;
    private DefaultListModel<ProductWrapper> cartListModel;
    private DefaultListModel<BillWrapper> billModelList;

    public GuiTest(StoreOperationsInterface store, BankOperationsInterface bank, int clientId, String account) {
        super();
        setContentPane(mainPanel);


        setStoreListModel(store);
        productsJList.setModel(productListModel);

        cartListModel = new DefaultListModel<>();
        cartJList.setModel(cartListModel);

        billModelList = new DefaultListModel<>();
        billsJList.setModel(billModelList);

        addButton.addActionListener(a -> {
            if (!(productsJList.getSelectedValue() == null)) {
                cartListModel.addElement(productsJList.getSelectedValue());
            }
        });

        deleteButton.addActionListener(a -> {
            if (!(cartJList.getSelectedValue() == null)) {
                cartListModel.removeElement(cartJList.getSelectedValue());
            }
        });

        refreshProductsButton.addActionListener(a -> setStoreListModel(store));

        buyButton.addActionListener(a -> {
            if (productListModel.size() > 0) {
                try {
                    System.out.println(productListModel.toArray());
                    Order order = new Order(clientId, account);

                    for (int i = 0; i < cartListModel.size(); i++) {
                        order.addProduct(cartListModel.get(i).getProductInterface(), 1);
                    }

                    BillInterface billInterface = store.sell(order);
                    bank.doBill(billInterface);
                    cartListModel.clear();

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        refreshBillsButton.addActionListener(a -> setBillListModel(bank, account));


        billsJList.addListSelectionListener(e -> {
            if (billsJList.getSelectedValue() != null) {
                textArea1.setText(billsJList.getSelectedValue().stringToTextArea());
            }
        });

        pack();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }


    private void setStoreListModel(StoreOperationsInterface store) {
        DefaultListModel<ProductWrapper> listModel = new DefaultListModel<>();

        try {
            Map<Product, Integer> productsMap = store.getAvailableProducts();
            for (ProductInterface product : productsMap.keySet()) {
                listModel.addElement(new ProductWrapper(product));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.productListModel = listModel;
    }

    private void setBillListModel(BankOperationsInterface bank, String account) {
        this.billModelList.clear();
        try {
            BankAccountInterface bankAccount = bank.getAccountSummary(account);
            for (BillInterface billInterface : bankAccount.getBillHistory()) {
                billModelList.addElement(new BillWrapper(billInterface));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
