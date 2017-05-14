package zad.Bank;

import zad.Utils.BankAccount;
import zad.Utils.BankAccountInterface;
import zad.Utils.BillInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by ikownacki on 12.05.2017.
 */
public interface BankOperationsInterface extends Remote {

    void doBill(BillInterface bill) throws RemoteException;

    void registerNewClient(String clientAccountNumber, double accountBalance) throws RemoteException;

    BankAccountInterface getAccountSummary(String clientAccount) throws RemoteException;
}
