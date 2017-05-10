import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;
import java.util.*;
import java.io.*;

public class PhoneDirectory extends PortableRemoteObject implements PhoneDirectoryInterface{

    private Map<String,String> pbMap = new HashMap<>();

    public PhoneDirectory(String fileName) throws RemoteException{
        super();
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] info = line.split(" +", 2);
                pbMap.put(info[0], info[1]);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public String getPhoneNumber(String name) throws RemoteException {
        return  pbMap.get(name);
    }

    @Override
    public boolean addPhoneNumber(String name, String num) throws RemoteException {
        if (pbMap.containsKey(name)) return false;
        pbMap.put(name, num);
        return true;
    }

    @Override
    public boolean replacePhoneNumber(String name, String num) throws RemoteException {
        if (!pbMap.containsKey(name)) return false;
        pbMap.put(name, num);
        return true;
    }

    @Override
    public String getAll() {
        StringBuilder stringBuilder = new StringBuilder();

        for (String key : pbMap.keySet()){
            stringBuilder.append("'"+key+"'");
            stringBuilder.append(" ");
            stringBuilder.append(pbMap.get(key));
            stringBuilder.append("\n");

        }

        return stringBuilder.toString();
    }
}