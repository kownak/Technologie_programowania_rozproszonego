package zad.Utils;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * Created by ikownacki on 13.05.2017.
 */
public interface OrderInterface extends Remote{

    int getClientId() throws RemoteException;

    String getBankAccount() throws RemoteException;

    Map<ProductInterface, Integer> getOrderedProducts() throws RemoteException;

    void addProduct(ProductInterface product, int amount) throws RemoteException;
}
