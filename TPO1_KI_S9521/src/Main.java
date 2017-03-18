/**
 * Created by ikownacki on 04.03.2017.
 */

public class Main {
    public static void main(String[] args) {
        String dirName = System.getProperty("user.home")+"/TPO1dir";
        System.out.println(dirName);
        String resultFileName = "TPO1res.txt";
        Futil.processDir(dirName, resultFileName);
    }
}
