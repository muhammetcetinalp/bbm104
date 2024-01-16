import java.io.BufferedWriter;
import java.io.IOException;

public class SmartLamp extends SmartDevices{
    public int getKelvin() {
        return kelvin;

    }
    public SmartLamp() {
        this.kelvin = 4000;
        this.brightness = 100;
    }

    public void setKelvin(int kelvin) {
        this.kelvin = kelvin;
    }

    private int kelvin;

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    private int brightness;

    public boolean isBrightness(int brightness){
        if (brightness <=100 && brightness>=0){return true;}
        else {return false;}
    }
    public boolean isKelvin(int kelvin1){
        if (kelvin1 <=6500 && kelvin1>=2000){return true;}
        else {return false;}

    }
    public void ZReport(String string, BufferedWriter writer) throws IOException {
        writer.write("Smart Lamp "+ string + " is " +Main.smartLampMap.get(string).getStatu().toLowerCase() +
                " and its kelvin value is "+Main.smartLampMap.get(string).getKelvin() +
                "K with "+Main.smartLampMap.get(string).getBrightness()+
                "% brightness, and its time to switch its status is "+
                Main.smartLampMap.get(string).getSwitchDate()+"_"+
                Main.smartLampMap.get(string).getSwitchTime()+ "\n");
    }
    public void ZReport2(String string, BufferedWriter writer) throws IOException {
        writer.write("Smart Lamp "+ string + " is " +Main.smartLampMap.get(string).getStatu().toLowerCase() +
                " and its kelvin value is "+Main.smartLampMap.get(string).getKelvin() +
                "K with "+Main.smartLampMap.get(string).getBrightness()+
                "% brightness, and its time to switch its status is null.\n");
    }



}
