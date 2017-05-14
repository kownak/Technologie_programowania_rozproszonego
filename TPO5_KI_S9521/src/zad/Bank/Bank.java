package zad.Bank;

import zad.Utils.BankAccount;
import zad.Utils.Bill;
import zad.Utils.BillInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ikownacki on 10.05.2017.
 */
public class Bank extends UnicastRemoteObject implements BankOperationsInterface {
    private Map<String, BankAccount> accountMap;

    public Bank() throws RemoteException {
        super();
        this.accountMap = new HashMap<>();
    }


    @Override
    public void doBill(BillInterface bill) throws RemoteException {
        String clientAccount = bill.getClientBankAccount();
        BankAccount bankAccount = accountMap.get(clientAccount);
        bankAccount.addBill(Bill.buidBill(bill));
    }

    @Override
    public void registerNewClient(String clientAccountNumber, double accountBalance) throws RemoteException {
        accountMap.put(clientAccountNumber, new BankAccount(accountBalance));
    }

    @Override
    public BankAccount getAccountSummary(String clientAccount) throws RemoteException{
        System.out.println(accountMap.get(clientAccount));
        return accountMap.get(clientAccount);
    }

    public static void main(String[] args) {
        try {
            Bank bank = new Bank();
            Naming.rebind("//localhost/Bank", bank);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
