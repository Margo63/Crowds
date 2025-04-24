package utils;

import java.io.*;
import java.util.ArrayList;

public class Files {


    public ArrayList<ArrayList<Integer>> readFile(String filename) {
        ArrayList<ArrayList<Integer>> tmp = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(filename)))
        {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                ArrayList<Integer> row = new ArrayList<>();
                for (String value : values) {
                    try {
                        row.add(Integer.parseInt(value.replaceAll(" ","")));

                    }
                    catch (NumberFormatException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                }
                tmp.add(row);
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        return tmp;
    }

    public static void writeToFile(String filename, String line) {
        try {
            FileWriter csvOutputFile = new FileWriter(filename,true);
            PrintWriter printWriter = new PrintWriter(csvOutputFile);

            printWriter.write(line);
            printWriter.flush();
            printWriter.close();

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static void createFile(String filename) {
        try {
            new PrintWriter(filename).close();
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }

    }
}
