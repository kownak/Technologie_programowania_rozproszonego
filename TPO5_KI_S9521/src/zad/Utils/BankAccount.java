package zad.Utils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ikownacki on 12.05.2017.
 */
public class BankAccount extends UnicastRemoteObject implements BankAccountInterface {
    private List<BillInterface> billHistory;
    private double allSpendMoney;
    private double accountBalance;

    public BankAccount(double accountBalance) throws RemoteException {
        super();
        this.billHistory = new ArrayList<>();
        this.allSpendMoney = 0;
        this.accountBalance = accountBalance;
    }

    @Override
    public List<BillInterface> getBillHistory()throws RemoteException {
        return billHistory;
    }

    @Override
    public double getAllSpendMoney()throws RemoteException {
        return allSpendMoney;
    }

    @Override
    public double getAccountBalance()throws RemoteException {
        return accountBalance;
    }

    @Override
    public void addBill(BillInterface bill) throws RemoteException {
        billHistory.add(bill);
        allSpendMoney = allSpendMoney + bill.getCharge();
        accountBalance = accountBalance - bill.getCharge();
    }
}
