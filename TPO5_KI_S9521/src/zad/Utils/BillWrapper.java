package zad.Utils;

import java.rmi.RemoteException;
import java.util.Objects;

/**
 * Created by ikownacki on 14.05.2017.
 */
public class BillWrapper {
    private BillInterface billInterface;

    public BillWrapper(BillInterface billInterface) {
        this.billInterface = billInterface;
    }

    @Override
    public int hashCode() {
        try {
            return Objects.hash(billInterface.getClientBankAccount(),
                    billInterface.getCharge(),
                    billInterface.getOrderedProducts(),
                    billInterface.getStoreBankAccount());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        try {
            return "-" + String.valueOf(billInterface.getCharge());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String stringToTextArea(){
        try {
            return "Przelew na konto " + billInterface.getStoreBankAccount()+" \n"+
                    "Kwota :"+ billInterface.getCharge()+" \n";
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
