import java.io.BufferedWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;


public class SmartPlug extends SmartDevices{

    public void setTotalEnergy(double totalEnergy) {
        this.totalEnergy += totalEnergy;
    }

    private double totalEnergy;
    private int volt;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    private int time;




    private LocalTime openTime;


    private LocalTime offTime;



    public boolean isPlugIn() {
        return plugIn;
    }

    public void setPlugIn(boolean plugIn) {
        this.plugIn = plugIn;
    }

    private boolean plugIn;
    public int calculatetime() {
        Duration duration = Duration.between(openTime, offTime);
        return this.time = (int) duration.toMinutes();

    }

    public SmartPlug(){
        this.volt = 220;
        this.totalEnergy = 0.0;
        this.ampere = 0;
        this.plugIn = false;

    }

    public double getAmpere() {
        return ampere;
    }


    public double getTotalEnergy() {
        return totalEnergy;
    }

    public int getVolt() {
        return volt;
    }


    public void setAmpere(String ampere) {
        double v = Double.parseDouble(ampere);
        this.ampere = v;
    }

    private double ampere;

    public  boolean isAmpereStringAnInteger(String value) {
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
    public boolean isAmperePositive(String value) {
        try {
            return Integer.parseInt(value) > 0;
        } catch (NumberFormatException e) {
            if (value.contains(".")) {
                try {
                    return Float.parseFloat(value) > 0;
                } catch (NumberFormatException a) {
                    return false;
                }
            }
            return false;
        }
    }


    public void setOpenTime(LocalTime date) {
        this.openTime = date;
    }

    public void setOffTime(LocalTime date) {
        this.offTime = date;
    }

    public void ZReport(String string, BufferedWriter writer) throws IOException {
        writer.write("Smart Plug "+ string+" is "+Main.smartPlugMap.get(string).getStatu().toLowerCase() +
                " and consumed "+ Main.smartPlugMap.get(string).getTotalEnergy()+
                "W so far (excluding current device), and its time to switch its status is "+
                Main.smartPlugMap.get(string).getSwitchDate()+"_"+
                Main.smartPlugMap.get(string).getSwitchTime()+ "\n");
    }
    public void ZReport2(String string, BufferedWriter writer) throws IOException {
        writer.write("Smart Plug "+ string+" is "+Main.smartPlugMap.get(string).getStatu().toLowerCase()+
                " and consumed "+ Main.smartPlugMap.get(string).getTotalEnergy()+
                "W so far (excluding current device), and its time to switch its status is null.\n");
    }
}
