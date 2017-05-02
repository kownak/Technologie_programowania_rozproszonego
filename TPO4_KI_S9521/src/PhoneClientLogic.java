import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

/**
 * Created by ikownacki on 30.04.2017.
 */
public class PhoneClientLogic {
    public static void main(String[] args) {
        try {
            Context ctx = new InitialContext();

            Object objref = ctx.lookup("PhoneDirectory");

            PhoneDirectoryInterface phoneDirectoryInterface; // uwaga: zawsze interfejs!
            phoneDirectoryInterface = (PhoneDirectoryInterface) PortableRemoteObject.narrow(
                    objref, PhoneDirectoryInterface.class);

            PhoneClientGui phoneClientGui = new PhoneClientGui(phoneDirectoryInterface);

            System.out.println(phoneDirectoryInterface.toString());

        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}