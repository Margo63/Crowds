package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile {


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
}
