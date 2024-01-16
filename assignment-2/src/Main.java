import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Main {
    public static LinkedHashMap<String,SmartDevices> devicesMap = new LinkedHashMap<>();
    public static LinkedHashMap<String,SmartLampWithColor> smartLampWithColorMap = new LinkedHashMap<>();
    public static LinkedHashMap<String,SmartLamp> smartLampMap = new LinkedHashMap<>();
    public static LinkedHashMap<String,SmartCamera> smartCameraMap = new LinkedHashMap<>();
    public static LinkedHashMap<String,SmartPlug> smartPlugMap = new LinkedHashMap<>();
    public static LinkedHashMap<String,String> switchMap = new LinkedHashMap<>();
    public static ArrayList<String> switchDate = new ArrayList<>();

    public static LocalDate date;

    public static void setTime(LocalTime time) {
        Main.time = time;
    }

    public static LocalTime time;

    public static void timeSetting(String date,String time){
        Main.time = LocalTime.parse(time);
        Main.date = LocalDate.parse(date);
    }

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