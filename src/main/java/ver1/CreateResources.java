package ver1;

import java.io.*;
import java.nio.charset.Charset;

/**
 * This class is created a niece resource file
 */
public class CreateResources {
    public static void main(String[] args) {

        StringBuilder content = new StringBuilder();
        File file = new File("D:\\practice\\AppConcat\\src\\main\\resources\\ресурсы.txt");
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("Cp1251")));
            bw = new BufferedWriter(new FileWriter("D:\\practice\\AppConcat\\src\\main\\resources\\ресурсы3.txt"));
            String s;
            while ((s = br.readLine()) != null) {
                if (s.contains("№") | s.length() < 1) {
                    continue;
                }
                content.append(s);
                content.append(System.lineSeparator());
            }
            bw.write(content.toString());
            System.out.println(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (bw != null)
                    bw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}