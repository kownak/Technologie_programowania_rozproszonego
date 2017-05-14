package zad.Utils;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by ikownacki on 13.05.2017.
 */
public interface ProductInterface extends Remote {

    String getName() throws RemoteException;

    double getPrice() throws RemoteException;


}
