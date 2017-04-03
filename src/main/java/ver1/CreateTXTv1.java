package ver1;

import org.apache.poi.util.StringUtil;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by GetFire on 23.01.2017.
 */
public class CreateTXTv1 {
    public static void main(String[] args) {
        Map<Integer, String> excelFile = new HashMap<>();
        Map<Integer, String> resourcesFile = new HashMap<>();
        List<String> neededLines = new ArrayList<>();
        BufferedReader excelReader = null;
        BufferedReader resourceReader = null;
        BufferedWriter fileWriter = null;
        String line;
        StringBuilder sB = new StringBuilder();
        StringBuilder content = new StringBuilder();
        try {
            excelReader = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\practice\\AppConcat\\src\\main\\resources\\ресурсы1.txt"), Charset.forName("UTF-8")));
            resourceReader = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\practice\\AppConcat\\src\\main\\resources\\ресурсы1.txt"), Charset.forName("UTF-8")));
            Integer i = 0;
            while ((line = excelReader.readLine()) != null) {
                excelFile.put(i, line);
                i++;
            }
            Integer j = 0;

            while ((line = resourceReader.readLine()) != null) {
                resourcesFile.put(j, line);
                j++;
            }
            for (String s : excelFile.values()) {
                List<String> d = Arrays.asList(s.split("\t"));
                if (!d.contains("name")) {
                    for (String s1 : resourcesFile.values()) {
                        List<String> b = Arrays.asList(s1.split("\\|"));
                        if (b.contains(d.get(1))) {
                            List<String> c = b.stream().filter(a -> !a.equals(d.get(1))).collect(Collectors.toList());
// Remove duplicates
                            Set<String> set = new HashSet<>(c);
                            c.clear();
                            c.addAll(set);

                            String[] v = c.toArray(new String[c.size()]);
                            String g = StringUtil.join(v, ", "); //готовая строка, что мне нужна
                            neededLines.add(g);
                            sB.append(d.get(0));
                            sB.append("\t");
                            sB.append(d.get(1));
                            sB.append("\t");
                            sB.append(d.get(2));
                            sB.append("\t");
//                            sB.append("||");
                            sB.append(g);
                            sB.append("\n");
                            String cov = sB.toString();
                            content.append(cov);
                            sB.delete(0, sB.length());
                        }
                    }
                }
            }
            fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\practice\\AppConcat\\src\\main\\resources\\Final.txt"), Charset.forName("UTF-8")));
            fileWriter.write(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (excelReader != null)
                    excelReader.close();
                if (resourceReader != null)
                    excelReader.close();
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException ex) {
                System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
            }
        }
    }
}