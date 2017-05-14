package zad.Utils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ikownacki on 10.05.2017.
 */
public class Order extends UnicastRemoteObject implements OrderInterface {
    private int ClientId;
    private String ClientBankAccount;
    private Map<ProductInterface, Integer> orderedProducts;

    public Order(int clientId, String bankAccount) throws RemoteException {
        super();
        ClientId = clientId;
        ClientBankAccount = bankAccount;
        orderedProducts = new HashMap<>();
    }

    @Override
    public int getClientId() {
        return ClientId;
    }

    @Override
    public String getBankAccount() {
        return ClientBankAccount;
    }

    @Override
    public Map<ProductInterface, Integer> getOrderedProducts() {
        return orderedProducts;
    }

    @Override
    public void addProduct(ProductInterface product, int amount) {
        if (orderedProducts.containsKey(product)) {
            orderedProducts.put(product, orderedProducts.get(product) + amount);
        } else {
            orderedProducts.put(product, amount);
        }
    }


}
