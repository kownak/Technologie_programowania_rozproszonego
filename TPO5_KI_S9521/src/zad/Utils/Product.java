package zad.Utils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;

/**
 * Created by ikownacki on 12.05.2017.
 */
public class Product extends UnicastRemoteObject implements ProductInterface {
    String name;
    double price;

    public Product(String name, double price) throws RemoteException {
        super();
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof ProductInterface)) return false;

        Product product = (Product) other;
        if (this.name.equals(product.name) && this.price == product.price) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    @Override
    public String toString() {
        return this.getName()+"    "+this.getPrice()+"zł";
    }
}
