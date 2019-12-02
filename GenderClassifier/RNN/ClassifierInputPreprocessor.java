import java.io.*;

public class ClassifierInputPreprocessor {

    private final static String path =  "run_demo.py";

    public static void main(String[] args) {

        try {
            Process p = Runtime.getRuntime().exec("python " +path);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String ret = in.readLine();
            System.out.println("value is : "+ret);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
