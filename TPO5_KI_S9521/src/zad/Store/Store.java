package zad.Store;

import zad.Bank.BankOperationsInterface;
import zad.Utils.Bill;
import zad.Utils.BillInterface;
import zad.Utils.OrderInterface;
import zad.Utils.Product;
import zad.Utils.ProductInterface;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by ikownacki on 10.05.2017.
 */
public class Store extends UnicastRemoteObject implements StoreOperationsInterface {

    private Map<Product, Integer> availableProducts;
    private final String storeBankAccount;

    public Store(Map<Product, Integer> availableProducts, String storeBankAccount) throws RemoteException {
        super();
        this.availableProducts = availableProducts;
        this.storeBankAccount = storeBankAccount;
    }

    @Override
    public BillInterface sell(OrderInterface order) throws RemoteException {
        Map<ProductInterface, Integer> orderedProducts = order.getOrderedProducts();
        double charge = 0;

        for (ProductInterface product : orderedProducts.keySet()) {
            int orderedAmountOfProduct = orderedProducts.get(product);

            Product p1 = new Product(product.getName(), product.getPrice()); //Nie wiem dlaczego ale inaczej nie działa
            System.out.println(p1.getPrice()+ " "+ p1.getName());
            int availableAmountOfProduct = availableProducts.get(p1);
            double priceOfProduct = product.getPrice();

            if (orderedAmountOfProduct <= availableAmountOfProduct) {
                charge = charge + priceOfProduct * orderedAmountOfProduct;
                availableProducts.put(p1, availableAmountOfProduct - orderedAmountOfProduct);
            } else {
                charge = charge + priceOfProduct * availableAmountOfProduct;
                availableProducts.put(p1, 0);
                orderedProducts.put(p1, availableAmountOfProduct);
            }
            System.out.println(charge);
        }

        return new Bill(order.getBankAccount(), storeBankAccount, charge, orderedProducts);
    }

    @Override
    public Map<Product, Integer> getAvailableProducts() throws RemoteException {
        return availableProducts;
    }



    public static void main(String[] args) {
        Map<Product, Integer> productsMap = new HashMap<>();
        try {
            Product p1 = new Product("Produkt1", 10);
            productsMap.put(p1, 100);
            productsMap.put(new Product("Produkt2", 5), 200);
            productsMap.put(new Product("Produkt3", 11.2), 50);
            productsMap.put(new Product("Produkt4", 25.5), 50);
            productsMap.put(new Product("Produkt5", 15), 10);


            Store store = new Store(productsMap, "PL252490541789423710133611781584");
            Naming.rebind("//localhost/Store", store);
            BankOperationsInterface bank = (BankOperationsInterface) Naming.lookup("//localhost/Bank");
            bank.registerNewClient(store.storeBankAccount,0);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }
}
