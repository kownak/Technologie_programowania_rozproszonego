package zad.Store;

import zad.Utils.BillInterface;
import zad.Utils.OrderInterface;
import zad.Utils.Product;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * Created by ikownacki on 12.05.2017.
 */
public interface StoreOperationsInterface extends Remote {
    BillInterface sell(OrderInterface order) throws RemoteException;

    Map<Product, Integer> getAvailableProducts() throws RemoteException;


}
