import java.io.*;
import java.util.LinkedHashMap;

public class Main {
    public static LinkedHashMap<Integer,Books> readBookInLibrary = new LinkedHashMap<>();
    public static LinkedHashMap<Integer,Books> books = new LinkedHashMap<>();
    public static LinkedHashMap<Integer,People> members = new LinkedHashMap<>();
    public static LinkedHashMap<Integer,Books> borrowedBooks = new LinkedHashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(args[0]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(args[1]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Reader.main(reader,writer);
    }

}
