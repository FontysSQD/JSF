import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Environment {
    public static void main(String[] args) {
        Properties p = new Properties();
        for (Integer i = 0; i < args.length; i += 2){
            String env = args[i];
            String value = System.getenv(env);
            if (value != null) {
                System.out.format("%s=%s%n", env, value);
                p.setProperty(env, value);
            } else {
                System.out.format("%s is not assigned.%n", env);
            }
        }
        try {
            FileOutputStream propFile = new FileOutputStream("myProperties.txt");
            p.store(propFile, "test");
        } catch (Exception ex) {
            System.out.println(ex);
        }

        try {
            FileInputStream propFile2 = new FileInputStream( "myProperties.txt");
            Properties p2 = new Properties();
            p2.load(propFile2);
            System.out.println(p2);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
