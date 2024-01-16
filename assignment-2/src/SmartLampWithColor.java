import java.io.BufferedWriter;
import java.io.IOException;

public class SmartLampWithColor extends SmartLamp{
    public SmartLampWithColor(){
        this.hexadecimal = "";
    }
    public String getHexadecimal() {
        return hexadecimal;
    }

    public void setHexadecimal(String hexadecimal) {
        this.hexadecimal = hexadecimal;
    }

    private String  hexadecimal;

    public boolean isHexadecimal(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        if (value.startsWith("0x") || value.startsWith("0X")) {
            value = value.substring(2);
        }
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (!Character.isDigit(c) && !(c >= 'a' && c <= 'f') && !(c >= 'A' && c <= 'F')) {
                return false;
            }
        }
        return true;
    }
    public  boolean isHexaInRange(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        if (value.startsWith("0x") || value.startsWith("0X")) {
            value = value.substring(2);
        }
        int intValue = 0;
        try {
            intValue = Integer.parseInt(value, 16);
        } catch (NumberFormatException e) {
            return false;
        }
        return intValue >= 0 && intValue <= 0xFFFFFF;
    }
    public void ZReport(String string, BufferedWriter writer) throws IOException {
        if (Main.smartLampWithColorMap.get(string).getHexadecimal().contains("0x")){
            writer.write("Smart Color Lamp " + string +" is " +
                    Main.smartLampWithColorMap.get(string).getStatu().toLowerCase() + " and its color value is "+
                    Main.smartLampWithColorMap.get(string).getHexadecimal()+ " with "+
                    Main.smartLampWithColorMap.get(string).getBrightness()+
                    "% brightness, and its time to switch its status is "+
                    Main.smartLampWithColorMap.get(string).getSwitchDate()+"_"+
                    Main.smartLampWithColorMap.get(string).getSwitchTime()+ "\n");
        }
        else {
            writer.write("Smart Color Lamp " + string +" is " +
                    Main.smartLampWithColorMap.get(string).getStatu().toLowerCase() + " and its color value is "+
                    Main.smartLampWithColorMap.get(string).getKelvin()+ " K with "+
                    Main.smartLampWithColorMap.get(string).getBrightness()+
                    "% brightness, and its time to switch its status is "+
                    Main.smartLampWithColorMap.get(string).getSwitchDate()+"_"+
                    Main.smartLampWithColorMap.get(string).getSwitchTime()+ "\n");
        }
    }
    public void ZReport2(String string,BufferedWriter writer) throws IOException {
        if (Main.smartLampWithColorMap.get(string).getHexadecimal().contains("0x")){
            writer.write("Smart Color Lamp " + string +" is " +
                    Main.smartLampWithColorMap.get(string).getStatu().toLowerCase() + " and its color value is "+
                    Main.smartLampWithColorMap.get(string).getHexadecimal()+ " with "+
                    Main.smartLampWithColorMap.get(string).getBrightness()+
                    "% brightness, and its time to switch its status is null\n");
        }
        else {
            writer.write("Smart Color Lamp " + string +" is " +
                    Main.smartLampWithColorMap.get(string).getStatu().toLowerCase() + " and its color value is "+
                    Main.smartLampWithColorMap.get(string).getKelvin()+ "K with "+
                    Main.smartLampWithColorMap.get(string).getBrightness()+
                    "% brightness, and its time to switch its status is null\n");
        }
    }


}
