package zad.Utils;

import java.rmi.RemoteException;
import java.util.Objects;

/**
 * Created by ikownacki on 14.05.2017.
 */
public class ProductWrapper {
    private ProductInterface productInterface;

    public ProductWrapper(ProductInterface productInterface) {
        this.productInterface = productInterface;
    }

    @Override
    public String toString() {
        try {
            return productInterface.getName() + "    " + productInterface.getPrice() + "zł";
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ProductInterface getProductInterface() {
        return productInterface;
    }

    @Override
    public int hashCode() {

        try {
            return Objects.hash(productInterface.getName(), productInterface.getPrice());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;

    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
