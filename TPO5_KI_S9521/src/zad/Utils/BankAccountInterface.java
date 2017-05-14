package zad.Utils;

import zad.Utils.BillInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by ikownacki on 13.05.2017.
 */
public interface BankAccountInterface extends Remote {
    List<BillInterface> getBillHistory() throws RemoteException;

    double getAllSpendMoney() throws RemoteException;

    double getAccountBalance() throws RemoteException;

    void addBill(BillInterface bill) throws RemoteException;
}
