package zad.Utils;

import zad.Utils.ProductInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * Created by ikownacki on 13.05.2017.
 */
public interface BillInterface extends Remote {
    String getClientBankAccount() throws RemoteException;

    String getStoreBankAccount() throws RemoteException;

    double getCharge() throws RemoteException;

    Map<ProductInterface, Integer> getOrderedProducts() throws RemoteException;
}
