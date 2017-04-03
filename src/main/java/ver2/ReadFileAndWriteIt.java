package ver2;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * this class help me to prepare txt resource file
 */
public class ReadFileAndWriteIt {
    public static void main(String[] args) {
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader resourceReader = null;
        List<String> content1 = new LinkedList<>();
        BufferedWriter resourceWriter = null;

        try {
            resourceReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("D:\\practice\\AppConcat\\src\\main\\resources\\ресурсы1.txt")));
            resourceWriter = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("D:\\practice\\AppConcat\\src\\main\\resources\\ресурсы2.txt")));
            // читаем заранее подготовлнный файл
            while ((line = resourceReader.readLine()) != null) {
                content1.add(line);
            }
            //сортируем его и сразу записываем в новый .txt файл
            for (String strings : content1) {
                List<String> some = Arrays.asList(strings.split(", "));
                for (String key1 : some) {
                    List<String> lines = some.stream().filter(a -> !a.equals(key1)).collect(Collectors.toList());
                    stringBuilder.append(key1).append("\t");
                    for (String s : lines) {
                        stringBuilder.append(s).append("\t");
                    }
                    stringBuilder.append("\n");
                    resourceWriter.write(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resourceReader != null)
                    resourceReader.close();
                if (resourceWriter != null)
                    resourceWriter.close();
            } catch (IOException ex) {
                System.out.println(ex.getClass().getSimpleName() + ": " + ex.getMessage());
            }

        }
    }
}