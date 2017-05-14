package zad.Client;

import zad.Bank.BankOperationsInterface;
import zad.Utils.BillInterface;
import zad.Utils.Order;
import zad.Utils.Product;
import zad.Store.StoreOperationsInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by ikownacki on 10.05.2017.
 */
public class Client {
    public static void main(String[] args) {
        String clientBankAccountNumber = args[0];
        int clientId = Integer.parseInt(args[1]);
        try {
            StoreOperationsInterface store = (StoreOperationsInterface) Naming.lookup("//localhost/Store");
            BankOperationsInterface bank = (BankOperationsInterface) Naming.lookup("//localhost/Bank");
            bank.registerNewClient(clientBankAccountNumber, 2000);

            new GuiTest(store, bank, clientId,clientBankAccountNumber);

        } catch (NotBoundException e) {

            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
}
