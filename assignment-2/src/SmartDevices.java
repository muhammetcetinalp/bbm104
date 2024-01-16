import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class SmartDevices {
    private String name;
    private String statu;

    public SmartDevices() {
        this.statu = "off";
        this.switchTime = null;
        this.switchDate = null;

    }

    public LocalDate getSwitchDate() {
        return switchDate;
    }

    public void setSwitchDate(LocalDate switchDate) {
        this.switchDate = switchDate;
    }

    private LocalDate switchDate;


    public LocalTime getSwitchTime() {
        return switchTime;
    }

    public void setSwitchTime(LocalTime switchTime) {
        this.switchTime = switchTime;
    }

    private LocalTime switchTime;

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public boolean isDeviceOnOrOff(String value, String status, BufferedWriter writer) throws IOException {
        if (value.equals("On") && status.equals("On")){
            writer.write("ERROR: This device is already switched on!\n");
            return false;
        }
        else if (value.equals("Off") && status.equals("Off")){
            writer.write("ERROR: This device is already switched off!\n");
            return false;
        }
        else {
            return true;
        }
    }
    public boolean isOrderOnOrOff(String value,BufferedWriter writer) throws IOException {
        if (value.equals("On") || value.equals("Off")) {return true;}
        else {writer.write("ERROR: Erroneous command!\n");return false;}
    }
    public  boolean isStringAnInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public boolean isPositive(String value){
        try{
            if (Integer.parseInt(value)>0){
                return true;
            }
        }catch (NumberFormatException e){
            return false;
        }
        return false;
    }
    public  boolean isStringIntOrFloat(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            if (value.contains(".")){
                try{
                    Float.parseFloat(value);
                    return true;
                }catch (NumberFormatException a){
                    return false;
                }
            }
            return false;
        }
    }

}
