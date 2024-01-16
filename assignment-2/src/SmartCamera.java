import java.io.BufferedWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Locale;

public class SmartCamera extends SmartDevices{
    private Float mbPerMunite;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    private int time;

    public double getTotalMB() {
        return TotalMB;
    }

    public void setTotalMB(double totalMB) {
        formatDouble(totalMB);
    }

    private double TotalMB=0.00;

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    private LocalTime openTime;

    public void setOffTime(LocalTime offTime) {
        this.offTime = offTime;
    }
    public void calculatetime() {
        Duration duration = Duration.between(openTime, offTime);
        this.time = (int) duration.toMinutes();

    }

    private LocalTime offTime;


    public SmartCamera(){

        this.mbPerMunite = (float) 0;
    }
    public void formatDouble(double number) {
        Locale.setDefault(Locale.forLanguageTag("pt-BR"));
        String abc = String.format("%.2f", number);
        this.TotalMB += Double.parseDouble(abc.replace(",", "."));
    }


    public Float getMbPerMunite() {
        return mbPerMunite;
    }

    public void setMbPerMunite(String mbPerMunite) {
        this.mbPerMunite = Float.parseFloat(mbPerMunite);
    }

    public boolean isMbPerMinPositive(String value){
            try {
                float floatValue = Float.parseFloat(value);
                return floatValue > 0;
            } catch (NumberFormatException e) {
                return false;
            }
    }
    public void ZReport(String string, BufferedWriter writer) throws IOException {
        writer.write("Smart Camera "+ string +" is " +
                Main.smartCameraMap.get(string).getStatu().toLowerCase() + " and used "+ Main.smartCameraMap.get(string).getTotalMB()+
                " MB of storage so far " +
                "(excluding current status), and its time to switch its status is "+
                Main.smartCameraMap.get(string).getSwitchDate()+"_"+
                Main.smartCameraMap.get(string).getSwitchTime()+ "\n");
    }
    public void ZReport2(String string, BufferedWriter writer) throws IOException {
        writer.write("Smart Camera "+ string +" is " +
                Main.smartCameraMap.get(string).getStatu().toLowerCase() + " and used "+ Main.smartCameraMap.get(string).getTotalMB()+
                " MB of storage so far " +
                "(excluding current status), and its time to switch its status is null.\n");
    }
}
