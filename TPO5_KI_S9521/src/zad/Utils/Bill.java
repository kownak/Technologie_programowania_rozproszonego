package zad.Utils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

/**
 * Created by ikownacki on 10.05.2017.
 */
public class Bill extends UnicastRemoteObject implements BillInterface {
    private String ClientBankAccount;
    private String StoreBankAccount;
    private double charge;
    private Map<ProductInterface, Integer> orderedProducts;

    public Bill(String clientBankAccount, String storeBankAccount, double charge, Map<ProductInterface, Integer> orderedProducts) throws RemoteException {
        super();
        ClientBankAccount = clientBankAccount;
        StoreBankAccount = storeBankAccount;
        this.charge = charge;
        this.orderedProducts = orderedProducts;
    }

    @Override
    public String getClientBankAccount() throws RemoteException {
        return ClientBankAccount;
    }

    @Override
    public String getStoreBankAccount() throws RemoteException {
        return StoreBankAccount;
    }

    @Override
    public double getCharge() throws RemoteException {
        return charge;
    }

    @Override
    public Map<ProductInterface, Integer> getOrderedProducts() throws RemoteException {
        return orderedProducts;
    }

    public static Bill buidBill(BillInterface billInterface) throws RemoteException {
        return new Bill(billInterface.getClientBankAccount(),
                billInterface.getStoreBankAccount(),
                billInterface.getCharge(),
                billInterface.getOrderedProducts());
    }
}
