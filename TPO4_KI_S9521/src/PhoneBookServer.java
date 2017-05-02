import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.RemoteException;

/**
 * Created by ikownacki on 30.04.2017.
 */
public class PhoneBookServer {


    public static void main(String[] args) {
        String filePath = "bin/zad/PhoneBook";

        try {
            PhoneDirectory phoneDirectory = new PhoneDirectory(filePath);

            Context ctx = new InitialContext();
            ctx.rebind("PhoneDirectory", phoneDirectory );

            System.out.println(phoneDirectory.getAll());


        } catch (RemoteException e) {
            e.printStackTrace();
        }  catch (NamingException e) {
            e.printStackTrace();
        }

    }
}
