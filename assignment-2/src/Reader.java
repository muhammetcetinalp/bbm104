import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Reader {
    public static void main(BufferedReader reader,BufferedWriter writer) throws IOException {
        String line;
        String lastLine="";
        int timer = 0,timerForInitialTime=0;
        while ((line = reader.readLine()) != null) {
            if (!line.isEmpty()) {
                lastLine = line;
                if (timer > 0 || line.startsWith("SetInitialTime")) {
                    writer.write("COMMAND: " + line + "\n");
                    timer ++;
                    if (line.equals("ZReport")) {
                        writer.write("Time is:\t" + Main.date +"_"+ Main.time+ "\n");
                        for (String string:Main.switchMap.keySet()){
                            if (Main.smartLampWithColorMap.containsKey(string)){
                                Main.smartLampWithColorMap.get(string).ZReport(string,writer);
                            }
                            else if (Main.smartCameraMap.containsKey(string)){
                                Main.smartCameraMap.get(string).ZReport(string,writer);
                            }
                            else if (Main.smartLampMap.containsKey(string)){
                                Main.smartLampMap.get(string).ZReport(string,writer);
                            }
                            else if (Main.smartPlugMap.containsKey(string)){
                                Main.smartPlugMap.get(string).ZReport(string,writer);
                            }
                        }
                        for (String string:Main.devicesMap.keySet()){
                            if (!Main.switchMap.containsKey(string)){
                                if (Main.smartLampWithColorMap.containsKey(string)){
                                    Main.smartLampWithColorMap.get(string).ZReport2(string,writer);
                                }
                                else if (Main.smartCameraMap.containsKey(string)){
                                    Main.smartCameraMap.get(string).ZReport2(string,writer);
                                }
                                else if (Main.smartLampMap.containsKey(string)){
                                    Main.smartLampMap.get(string).ZReport2(string,writer);
                                }
                                else if (Main.smartPlugMap.containsKey(string)){
                                    Main.smartPlugMap.get(string).ZReport2(string,writer);
                                }
                            }
                        }
                    }
                    else if (line.equals("Nop")) {
                        if (Main.switchMap.size() > 0){
                            List<String> keys = new ArrayList<>(Main.switchMap.keySet());
                            String[] date = Main.switchDate.get(0).split("_");
                            try {
                                LocalDateTime futureDate = LocalDateTime.of(LocalDate.parse(date[0]), LocalTime.parse(date[1]));
                                Main.timeSetting(date[0],date[1]);
                                while (futureDate.isEqual(LocalDateTime.of(Main.devicesMap.get(keys.get(0)).getSwitchDate(),
                                        Main.devicesMap.get(keys.get(0)).getSwitchTime()))) {
                                        if (Main.devicesMap.get(keys.get(0)).getStatu().equals("On")) {
                                            if (Main.smartPlugMap.containsKey(keys.get(0))) {
                                                Main.smartPlugMap.get(keys.get(0)).setOffTime(Main.smartPlugMap.get(keys.get(0)).getSwitchTime());
                                                Main.smartPlugMap.get(keys.get(0)).calculatetime();
                                                double totalEnergy;
                                                totalEnergy = Main.smartPlugMap.get(keys.get(0)).getVolt() *
                                                        Main.smartPlugMap.get(keys.get(0)).getAmpere() *
                                                        Main.smartPlugMap.get(keys.get(0)).getTime() * 1 / 60;
                                                Main.smartPlugMap.get(keys.get(0)).setTotalEnergy(Math.round(
                                                        totalEnergy * 100.0) / 100.0);
                                            } else if (Main.smartCameraMap.containsKey(keys.get(0))) {
                                                Main.smartCameraMap.get(keys.get(0)).setOffTime(Main.smartCameraMap.get(keys.get(0)).getSwitchTime());
                                                Main.smartCameraMap.get(keys.get(0)).calculatetime();
                                                double totalMB;
                                                totalMB = Main.smartCameraMap.get(keys.get(0)).getMbPerMunite() *
                                                        Main.smartCameraMap.get(keys.get(0)).getTime();
                                                Main.smartCameraMap.get(keys.get(0)).setTotalMB(Math.round(totalMB * 100.0) / 100.0);
                                            }
                                            Main.devicesMap.get(keys.get(0)).setStatu("Off");
                                        } else {
                                            if (Main.smartPlugMap.containsKey(keys.get(0))) {
                                                Main.smartPlugMap.get(keys.get(0)).setOpenTime(Main.smartPlugMap.get(keys.get(0)).getSwitchTime());
                                            } else if (Main.smartCameraMap.containsKey(keys.get(0))) {
                                                Main.smartCameraMap.get(keys.get(0)).setOpenTime(Main.smartCameraMap.get(keys.get(0)).getSwitchTime());
                                            }
                                            Main.devicesMap.get(keys.get(0)).setStatu("On");
                                        }
                                        Main.devicesMap.get(keys.get(0)).setSwitchDate(null);
                                        Main.devicesMap.get(keys.get(0)).setSwitchTime(null);
                                        LinkedHashMap<String, SmartDevices> devicesMap2 = new LinkedHashMap<>();
                                        devicesMap2.put(keys.get(0), Main.devicesMap.get(keys.get(0)));
                                        Main.devicesMap.remove(keys.get(0));
                                        devicesMap2.putAll(Main.devicesMap);
                                        Main.devicesMap = devicesMap2;
                                        Main.switchDate.remove(0);
                                        Main.switchMap.remove(keys.get(0));
                                        keys.remove(0);
                                }
                            }catch (DateTimeParseException e) {
                                writer.write("ERROR: Time format is not correct!\n");
                            }catch (IndexOutOfBoundsException e) {
                                Main.timeSetting(date[0], date[1]);
                            }
                        }else {
                            writer.write("ERROR: There is nothing to switch!\n");
                        }
                    }
                    else {
                        String[] part = line.split("\t");
                        if (part[0].equals("SetInitialTime")){
                            if (part.length == 2){
                                if (timerForInitialTime == 0){
                                    try{
                                        String[] timeSet = part[1].split("_");
                                        Main.timeSetting(timeSet[0],timeSet[1]);
                                        writer.write("SUCCESS: Time has been set to " + part[1] + "!\n");
                                        timerForInitialTime++;
                                    }catch (DateTimeParseException e){
                                        writer.write("ERROR: Format of the initial date is wrong! " +
                                                "Program is going to terminate!\n");
                                        lastLine = "ZReport";
                                        break;
                                    }

                                }else {
                                    writer.write("ERROR: Erroneous command!\n");
                                }
                            }else {
                                writer.write("ERROR: First command must be set initial time! Program is going to terminate!\n");
                                lastLine = "ZReport";
                                break;
                            }
                        }
                        else if (part[0].equals("SetTime")) {
                            if (part.length == 2){
                                List<String> keys = new ArrayList<>(Main.switchMap.keySet());
                                String[] part2 = part[1].split("_");
                                try {
                                    LocalDateTime futureDate = LocalDateTime.of(LocalDate.parse(part2[0]), LocalTime.parse(part2[1]));
                                    if (futureDate.isAfter(LocalDateTime.of(Main.date, Main.time))) {
                                        try {
                                            LocalTime futureTime = LocalTime.parse(part2[1]);
                                            while (!futureDate.isBefore(LocalDateTime.of(Main.devicesMap.get(keys.get(0)).getSwitchDate(),
                                                    Main.devicesMap.get(keys.get(0)).getSwitchTime()))) {
                                                if (futureTime.isAfter(Main.devicesMap.get(keys.get(0)).getSwitchTime())) {
                                                    Main.timeSetting(part2[0], part2[1]);
                                                    if (Main.devicesMap.get(keys.get(0)).getStatu().equals("On")) {
                                                        if (Main.smartPlugMap.containsKey(keys.get(0))) {
                                                            Main.smartPlugMap.get(keys.get(0)).setOffTime(Main.smartPlugMap.get(keys.get(0)).getSwitchTime());
                                                            Main.smartPlugMap.get(keys.get(0)).calculatetime();
                                                            double totalEnergy;
                                                            totalEnergy = Main.smartPlugMap.get(keys.get(0)).getVolt()*
                                                                    Main.smartPlugMap.get(keys.get(0)).getAmpere()*
                                                                    Main.smartPlugMap.get(keys.get(0)).getTime()*1/60;
                                                            Main.smartPlugMap.get(keys.get(0)).setTotalEnergy(Math.round(totalEnergy * 100.0) / 100.0);
                                                        }
                                                        else if (Main.smartCameraMap.containsKey(keys.get(0))) {
                                                            Main.smartCameraMap.get(keys.get(0)).setOffTime(Main.smartCameraMap.get(keys.get(0)).getSwitchTime());
                                                            Main.smartCameraMap.get(keys.get(0)).calculatetime();
                                                            double totalMB;
                                                            totalMB = Main.smartCameraMap.get(keys.get(0)).getTime()*
                                                                    Main.smartCameraMap.get(keys.get(0)).getMbPerMunite();
                                                            Main.smartCameraMap.get(keys.get(0)).setTotalMB(Math.round(totalMB * 100.0) / 100.0);
                                                        }
                                                        Main.devicesMap.get(keys.get(0)).setStatu("Off");
                                                    } else {
                                                        if (Main.smartPlugMap.containsKey(keys.get(0))) {
                                                            Main.smartPlugMap.get(keys.get(0)).setOpenTime(Main.smartPlugMap.get(keys.get(0)).getSwitchTime());
                                                        }
                                                        else if (Main.smartCameraMap.containsKey(keys.get(0))) {
                                                            Main.smartCameraMap.get(keys.get(0)).setOpenTime(Main.smartCameraMap.get(keys.get(0)).getSwitchTime());
                                                        }
                                                        Main.devicesMap.get(keys.get(0)).setStatu("On");
                                                    }
                                                    Main.devicesMap.get(keys.get(0)).setSwitchDate(null);
                                                    Main.devicesMap.get(keys.get(0)).setSwitchTime(null);
                                                    LinkedHashMap<String, SmartDevices> devicesMap2 = new LinkedHashMap<>();
                                                    devicesMap2 .put(keys.get(0), Main.devicesMap.get(keys.get(0)));
                                                    Main.devicesMap.remove(keys.get(0));
                                                    devicesMap2 .putAll(Main.devicesMap);
                                                    Main.devicesMap = devicesMap2 ;
                                                    Main.switchDate.remove(0);
                                                    Main.switchMap.remove(keys.get(0));
                                                    keys.remove(0);
                                                }else {
                                                    Main.timeSetting(part2[0], part2[1]);
                                                }
                                            }
                                        } catch (IndexOutOfBoundsException e) {
                                            Main.timeSetting(part2[0], part2[1]);}
                                    }else{
                                        LocalTime futureTime = LocalTime.parse(part2[1]);
                                        if (Main.time.equals(futureTime)){
                                            writer.write("ERROR: There is nothing to change!\n");
                                            Main.timeSetting(part2[0], part2[1]);
                                        }
                                        else {writer.write("ERROR: Time cannot be reversed!\n");
                                        }
                                    }
                                }catch (DateTimeParseException e) {
                                    writer.write("ERROR: Time format is not correct!\n");
                                    }
                            }else {
                                writer.write("ERROR: Erroneous command!\n");
                            }
                        } else if (part[0].equals("SkipMinutes")) {
                            SmartDevices smartDevices = new SmartDevices();
                            if (part.length == 2){
                                if (smartDevices.isStringAnInteger(part[1])){
                                    if(smartDevices.isPositive(part[1])){
                                        List<String> keys = new ArrayList<>(Main.switchMap.keySet());
                                        try {
                                            LocalTime futureTime = Main.time.plusMinutes(Integer.parseInt(part[1]));
                                            LocalDateTime futureDate = LocalDateTime.of(Main.date, Main.time).plusMinutes(
                                                    Integer.parseInt(part[1]));
                                            if (futureDate.isAfter(LocalDateTime.of(Main.date, Main.time))) {
                                                try {
                                                    while (!futureDate.isBefore(LocalDateTime.of(Main.devicesMap.get(keys.get(0)).getSwitchDate(),
                                                            Main.devicesMap.get(keys.get(0)).getSwitchTime()))) {
                                                        if (futureTime.isAfter(Main.devicesMap.get(keys.get(0)).getSwitchTime())) {
                                                            Main.devicesMap.get(keys.get(0)).setSwitchDate(null);
                                                            Main.devicesMap.get(keys.get(0)).setSwitchTime(null);
                                                            if (Main.devicesMap.get(keys.get(0)).getStatu().equals("On")) {
                                                                if (Main.smartPlugMap.containsKey(keys.get(0))) {
                                                                    Main.smartPlugMap.get(keys.get(0)).setOffTime(Main.smartPlugMap.get(keys.get(0)).getSwitchTime());
                                                                    Main.smartPlugMap.get(keys.get(0)).calculatetime();
                                                                    double totalEnergy = Main.smartPlugMap.get(keys.get(0)).getVolt()*
                                                                    Main.smartPlugMap.get(keys.get(0)).getAmpere()*
                                                                    Main.smartPlugMap.get(keys.get(0)).getTime()*1/60;
                                                                    Main.smartPlugMap.get(keys.get(0)).setTotalEnergy(
                                                                            Math.round(totalEnergy * 100.0) / 100.0);
                                                                }
                                                                else if (Main.smartCameraMap.containsKey(keys.get(0))) {
                                                                    Main.smartCameraMap.get(keys.get(0)).setOffTime(Main.smartCameraMap.get(keys.get(0)).getSwitchTime());
                                                                    Main.smartCameraMap.get(keys.get(0)).calculatetime();
                                                                    double totalMB;
                                                                    totalMB = Main.smartCameraMap.get(keys.get(0)).getTime()*
                                                                            Main.smartCameraMap.get(keys.get(0)).getMbPerMunite();
                                                                    Main.smartCameraMap.get(keys.get(0)).setTotalMB(
                                                                            Math.round(totalMB * 100.0) / 100.0);
                                                                }
                                                                Main.devicesMap.get(keys.get(0)).setStatu("Off");
                                                            } else {
                                                                if (Main.smartPlugMap.containsKey(keys.get(0))) {
                                                                    Main.smartPlugMap.get(keys.get(0)).setOpenTime(Main.smartPlugMap.get(keys.get(0)).getSwitchTime());
                                                                }
                                                                else if (Main.smartCameraMap.containsKey(keys.get(0))) {
                                                                    Main.smartCameraMap.get(keys.get(0)).setOpenTime(Main.smartCameraMap.get(keys.get(0)).getSwitchTime());
                                                                }
                                                                Main.devicesMap.get(keys.get(0)).setStatu("On");
                                                            }
                                                            LinkedHashMap<String, SmartDevices> devicesMap2 = new LinkedHashMap<>();
                                                            devicesMap2.put(keys.get(0), Main.devicesMap.get(keys.get(0)));
                                                            Main.devicesMap.remove(keys.get(0));
                                                            devicesMap2.putAll(Main.devicesMap);
                                                            Main.devicesMap = devicesMap2;
                                                            Main.switchDate.remove(0);
                                                            Main.switchMap.remove(keys.get(0));
                                                            keys.remove(0);
                                                        }else {
                                                            Main.time = futureTime;
                                                            Main.date = futureDate.toLocalDate();
                                                            break;
                                                        }
                                                    }
                                                    Main.time = futureTime;
                                                    Main.date = futureDate.toLocalDate();

                                                }catch (Exception e) {
                                                    Main.time = futureTime;
                                                    Main.date = futureDate.toLocalDate();}
                                            }
                                        }catch (Exception e) {
                                            writer.write("ERROR: Erroneous command!\n");
                                        }
                                    }
                                    else {
                                        if (Integer.parseInt(part[1])==0){
                                            writer.write("ERROR: There is nothing to skip!\n");
                                        }
                                        else {
                                            writer.write("ERROR: Time cannot be reversed!\n");
                                        }
                                    }
                                }else {
                                    writer.write("ERROR: Erroneous command!\n");
                                }
                            }else {
                                writer.write("ERROR: Erroneous command!\n");
                            }
                        }
                        else if (part[0].equals("SetSwitchTime")) {
                            if (part.length == 3){
                                if (Main.devicesMap.containsKey(part[1])){
                                    String [] date = part[2].split("_");
                                    if(Main.time.isBefore(LocalTime.parse(date[1]))){
                                        Main.devicesMap.get(part[1]).setSwitchDate(LocalDate.parse(date[0]));
                                        Main.devicesMap.get(part[1]).setSwitchTime(LocalTime.parse(date[1]));
                                        Main.switchMap.put(part[1],part[2]);
                                        Main.switchDate.add(part[2]);
                                        Collections.sort(Main.switchDate);
                                        Main.switchMap = Main.switchMap.entrySet().stream().sorted(Map.Entry
                                                .comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey,
                                                Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
                                    }
                                    else {
                                        if (Main.time.equals(LocalTime.parse(date[1]))){
                                            if(Main.devicesMap.get(part[1]).getStatu().equals("On")){
                                                Main.devicesMap.get(part[1]).setStatu("Off");
                                            }
                                            else {Main.devicesMap.get(part[1]).setStatu("On");}
                                        }
                                        else {
                                        writer.write("ERROR: Switch time cannot be in the past!\n");
                                        }
                                    }
                                }else {
                                    writer.write("ERROR: There is not such a device!\n");
                                }
                            }else {
                                writer.write("ERROR: Erroneous command!\n");
                            }
                        }else if (part[0].equals("Add") && part[1].equals("SmartColorLamp")) {
                            SmartLampWithColor smartLampWithColor = new SmartLampWithColor();
                            if (!Main.devicesMap.containsKey(part[2])) {
                                if (part.length == 3) {
                                    smartLampWithColor.setName(part[2]);
                                    Main.devicesMap.put(smartLampWithColor.getName(), smartLampWithColor);
                                    Main.smartLampWithColorMap.put(smartLampWithColor.getName(), smartLampWithColor);
                                } else if (part.length == 4) {
                                    if (smartLampWithColor.isOrderOnOrOff(part[3],writer)) {
                                        if (smartLampWithColor.isDeviceOnOrOff(smartLampWithColor.getStatu(), part[3],writer)){
                                            smartLampWithColor.setName(part[2]);
                                            smartLampWithColor.setStatu(part[3]);
                                            Main.devicesMap.put(smartLampWithColor.getName(), smartLampWithColor);
                                            Main.smartLampWithColorMap.put(smartLampWithColor.getName(),
                                                    smartLampWithColor);
                                        }
                                    }
                                } else if (part.length == 5) {
                                    if (smartLampWithColor.isOrderOnOrOff(part[3],writer)) {
                                        if (smartLampWithColor.isDeviceOnOrOff(smartLampWithColor.getStatu(), part[3],writer)){
                                            if (smartLampWithColor.isStringAnInteger(part[4])) {
                                                if (smartLampWithColor.isKelvin(Integer.parseInt(part[4]))) {
                                                    smartLampWithColor.setName(part[2]);
                                                    smartLampWithColor.setStatu(part[3]);
                                                    smartLampWithColor.setKelvin(Integer.parseInt(part[4]));
                                                    Main.devicesMap.put(smartLampWithColor.getName(),
                                                            smartLampWithColor);
                                                    Main.smartLampWithColorMap.put(smartLampWithColor.getName(),
                                                            smartLampWithColor);
                                                } else
                                                    writer.write("ERROR: Kelvin value must be in " +
                                                            "range of 2000K-6500K!\n");
                                            } else if (smartLampWithColor.isHexadecimal(part[4])) {
                                                if (smartLampWithColor.isHexaInRange(part[4])) {
                                                    smartLampWithColor.setName(part[2]);
                                                    smartLampWithColor.setStatu(part[3]);
                                                    smartLampWithColor.setHexadecimal(part[4]);
                                                    Main.devicesMap.put(smartLampWithColor.getName(), smartLampWithColor);
                                                    Main.smartLampWithColorMap.put(smartLampWithColor.getName(),
                                                            smartLampWithColor);
                                                } else
                                                    writer.write("ERROR: Color code value must be in " +
                                                            "range of 0x0-0xFFFFFF!\n");
                                            } else writer.write("ERROR: Erroneous command!\n");
                                        }
                                    }
                                } else if (part.length == 6) {
                                    if (smartLampWithColor.isOrderOnOrOff(part[3],writer)) {
                                        if (smartLampWithColor.isDeviceOnOrOff(smartLampWithColor.getStatu(), part[3],writer)) {
                                            if (smartLampWithColor.isStringAnInteger(part[4])) {
                                                if (smartLampWithColor.isKelvin(Integer.parseInt(part[4]))) {
                                                    if (smartLampWithColor.isStringAnInteger(part[5])) {
                                                        if (smartLampWithColor.isBrightness(Integer.parseInt(part[5]))) {
                                                            smartLampWithColor.setName(part[2]);
                                                            smartLampWithColor.setStatu(part[3]);
                                                            smartLampWithColor.setKelvin(Integer.parseInt(part[4]));
                                                            smartLampWithColor.setBrightness(Integer.parseInt(part[5]));
                                                            Main.devicesMap.put(smartLampWithColor.getName(),
                                                                    smartLampWithColor);
                                                            Main.smartLampWithColorMap.put(smartLampWithColor.getName(),
                                                                    smartLampWithColor);
                                                        } else
                                                            writer.write("ERROR: Brightness must be in " +
                                                                    "range of 0%-100%!\n");
                                                    } else writer.write("ERROR: Erroneous command!\n");
                                                } else
                                                    writer.write("ERROR: Kelvin value must be in " +
                                                            "range of 2000K-6500K!\n");
                                            } else if (smartLampWithColor.isHexadecimal(part[4])) {
                                                if (smartLampWithColor.isHexaInRange(part[4])) {
                                                    if (smartLampWithColor.isStringAnInteger(part[5])) {
                                                        if (smartLampWithColor.isBrightness(Integer.parseInt(part[5]))){
                                                            smartLampWithColor.setName(part[2]);
                                                            smartLampWithColor.setStatu(part[3]);
                                                            smartLampWithColor.setHexadecimal(part[4]);
                                                            smartLampWithColor.setBrightness(Integer.parseInt(part[5]));
                                                            Main.devicesMap.put(smartLampWithColor.getName(),
                                                                    smartLampWithColor);
                                                            Main.smartLampWithColorMap.put(smartLampWithColor.getName(),
                                                                    smartLampWithColor);
                                                        } else
                                                            writer.write("ERROR: Brightness must be in " +
                                                                    "range of 0%-100%!\n");
                                                    } else writer.write("ERROR: Erroneous command!\n");
                                                } else
                                                    writer.write("ERROR: Color code value must be in " +
                                                            "range of 0x0-0xFFFFFF!\n");
                                            } else writer.write("ERROR: Erroneous command!\n");
                                        }
                                    }
                                }


                            } else writer.write("ERROR: There is already a smart device with same name!\n");
                        } else if (part[0].equals("Add") && part[1].equals("SmartLamp")) {
                            SmartLamp lamp = new SmartLamp();
                            if (!Main.devicesMap.containsKey(part[2])) {
                                if (part.length == 3) {
                                    lamp.setName(part[2]);
                                    Main.devicesMap.put(lamp.getName(), lamp);
                                    Main.smartLampMap.put(lamp.getName(), lamp);
                                } else if (part.length == 4) {
                                    if (lamp.isOrderOnOrOff(part[3],writer)) {
                                        if (lamp.isDeviceOnOrOff(lamp.getStatu(), part[3],writer)) {
                                            lamp.setName(part[2]);
                                            lamp.setStatu(part[3]);
                                            Main.devicesMap.put(lamp.getName(), lamp);
                                            Main.smartLampMap.put(lamp.getName(), lamp);
                                        }
                                    }
                                } else if (part.length == 5) {
                                    if (lamp.isOrderOnOrOff(part[3],writer)) {
                                        if (lamp.isDeviceOnOrOff(lamp.getStatu(), part[3],writer)) {
                                            if (lamp.isStringAnInteger(part[4])) {
                                                if (lamp.isKelvin(Integer.parseInt(part[4]))) {
                                                    lamp.setName(part[2]);
                                                    lamp.setStatu(part[3]);
                                                    lamp.setKelvin(Integer.parseInt(part[4]));
                                                    Main.devicesMap.put(lamp.getName(), lamp);
                                                    Main.smartLampMap.put(lamp.getName(), lamp);
                                                } else
                                                    writer.write("ERROR: Kelvin value must be in " +
                                                            "range of 2000K-6500K!\n");
                                            } else writer.write("ERROR: Erroneous command!\n");
                                        }
                                    }
                                } else if (part.length == 6) {
                                    if (lamp.isOrderOnOrOff(part[3],writer)) {
                                        if (lamp.isDeviceOnOrOff(lamp.getStatu(), part[3],writer)) {
                                            if (lamp.isStringAnInteger(part[4])) {
                                                if (lamp.isKelvin(Integer.parseInt(part[4]))) {
                                                    if (lamp.isStringAnInteger(part[5])) {
                                                        if (lamp.isBrightness(Integer.parseInt(part[5]))) {
                                                            lamp.setName(part[2]);
                                                            lamp.setStatu(part[3]);
                                                            lamp.setKelvin(Integer.parseInt(part[4]));
                                                            lamp.setBrightness(Integer.parseInt(part[5]));
                                                            Main.devicesMap.put(lamp.getName(), lamp);
                                                            Main.smartLampMap.put(lamp.getName(), lamp);
                                                        } else
                                                            writer.write("ERROR: Brightness must " +
                                                                    "be in range of 0%-100%!\n");
                                                    } else writer.write("ERROR: Erroneous command!\n");
                                                } else
                                                    writer.write("ERROR: Kelvin value must be in " +
                                                            "range of 2000K-6500K!\n");
                                            } else writer.write("ERROR: Erroneous command!\n");
                                        }
                                    }
                                }
                            } else writer.write("ERROR: There is already a smart device with same name!\n");
                        }
                        if (part[0].equals("Add") && part[1].equals("SmartCamera")) {
                            SmartCamera smartCamera = new SmartCamera();
                            if (part.length == 3) writer.write("ERROR: Erroneous command!\n");
                            else if (!Main.devicesMap.containsKey(part[2])) {
                                if (part.length == 4) {
                                    if (smartCamera.isStringIntOrFloat(part[3])) {
                                        if (smartCamera.isMbPerMinPositive(part[3])) {
                                            smartCamera.setName(part[2]);
                                            smartCamera.setMbPerMunite(part[3]);
                                            Main.devicesMap.put(smartCamera.getName(), smartCamera);
                                            Main.smartCameraMap.put(smartCamera.getName(), smartCamera);
                                        } else writer.write("ERROR: Megabyte value must be a " +
                                                "positive number!\n");
                                    } else writer.write("ERROR: Erroneous command!\n");
                                } else if (part.length == 5) {
                                    if (smartCamera.isStringIntOrFloat(part[3])) {
                                        if (smartCamera.isMbPerMinPositive(part[3])) {
                                            if (smartCamera.isOrderOnOrOff(part[4],writer)) {
                                                if (smartCamera.isDeviceOnOrOff(smartCamera.getStatu(), part[4],writer)) {
                                                    smartCamera.setName(part[2]);
                                                    smartCamera.setMbPerMunite(part[3]);
                                                    smartCamera.setStatu(part[4]);
                                                    Main.devicesMap.put(smartCamera.getName(), smartCamera);
                                                    Main.smartCameraMap.put(smartCamera.getName(), smartCamera);
                                                    if (part[4].equals("On")){
                                                        Main.smartCameraMap.get(part[2]).setOpenTime(Main.time);
                                                    }
                                                }
                                            }
                                        } else writer.write("ERROR: Megabyte value has to be a " +
                                                "positive number!\n");
                                    } else writer.write("ERROR: Erroneous command!\n");
                                }
                            } else writer.write("ERROR: There is already a smart device with same name!\n");
                        } else if (part[0].equals("Add") && part[1].equals("SmartPlug")) {
                            SmartPlug smartPlug = new SmartPlug();
                            if (!Main.devicesMap.containsKey(part[2])) {
                                if (part.length == 3) {
                                    smartPlug.setName(part[2]);
                                    Main.devicesMap.put(smartPlug.getName(), smartPlug);
                                    Main.smartPlugMap.put(smartPlug.getName(), smartPlug);

                                }
                                if (part.length == 4) {
                                    if (smartPlug.isOrderOnOrOff(part[3],writer)) {
                                        if (smartPlug.isDeviceOnOrOff(smartPlug.getStatu(), part[3],writer)) {
                                            smartPlug.setName(part[2]);
                                            smartPlug.setStatu(part[3]);
                                            Main.devicesMap.put(smartPlug.getName(), smartPlug);
                                            Main.smartPlugMap.put(smartPlug.getName(), smartPlug);
                                            if (part[3].equals("On")){
                                                Main.smartPlugMap.get(part[2]).setOpenTime(Main.time);
                                            }
                                        }
                                    }
                                }
                                if (part.length == 5) {
                                    if (smartPlug.isOrderOnOrOff(part[3],writer)) {
                                        if (smartPlug.isDeviceOnOrOff(smartPlug.getStatu(), part[3],writer)) {
                                            if (smartPlug.isAmpereStringAnInteger(part[4])) {
                                                if (smartPlug.isAmperePositive(part[4])) {
                                                    smartPlug.setName(part[2]);
                                                    smartPlug.setStatu(part[3]);
                                                    smartPlug.setAmpere(part[4]);
                                                    Main.devicesMap.put(smartPlug.getName(), smartPlug);
                                                    Main.smartPlugMap.put(smartPlug.getName(), smartPlug);
                                                    if (part[3].equals("On")){
                                                        smartPlug.setPlugIn(true);
                                                        Main.smartPlugMap.get(part[2]).setOpenTime(Main.time);
                                                    }
                                                } else
                                                    writer.write("ERROR: Ampere value must be a " +
                                                            "positive number!\n");
                                            } else writer.write("ERROR: Erroneous command!\n");
                                        }
                                    }
                                }
                            } else writer.write("ERROR: There is already a smart device with same name!\n");
                        } else if (part[0].equals("Remove")) {
                            if (part.length == 2) {
                                if (Main.devicesMap.containsKey(part[1])) {
                                    writer.write("SUCCESS: Information about removed smart device is as follows:\n");
                                    if (Main.smartLampWithColorMap.containsKey(part[1])) {
                                        Main.smartLampWithColorMap.get(part[1]).setStatu("0ff");
                                        Main.smartLampWithColorMap.get(part[1]).ZReport2(part[1],writer);
                                        Main.smartLampWithColorMap.remove(part[1]);
                                    } else if (Main.smartLampMap.containsKey(part[1])) {
                                        Main.smartLampMap.get(part[1]).setStatu("0ff");
                                        Main.smartLampMap.get(part[1]).ZReport2(part[1],writer);
                                        Main.smartLampMap.remove(part[1]);
                                    } else if (Main.smartCameraMap.containsKey(part[1])) {
                                        if (Main.smartCameraMap.get(part[1]).getStatu().equals("On")){
                                            Main.smartCameraMap.get(part[1]).setOffTime(Main.time);
                                            Main.smartCameraMap.get(part[1]).calculatetime();
                                            double totalMB;
                                            totalMB = Main.smartCameraMap.get(part[1]).getMbPerMunite()*
                                                    Main.smartCameraMap.get(part[1]).getTime();
                                            Main.smartCameraMap.get(part[1]).setTotalMB(Math.round(
                                                    totalMB * 100.0) / 100.0);
                                        }
                                        Main.smartCameraMap.get(part[1]).setStatu("0ff");
                                        Main.smartCameraMap.get(part[1]).ZReport2(part[1],writer);
                                        Main.smartCameraMap.remove(part[1]);
                                    } else if (Main.smartPlugMap.containsKey(part[1])){
                                        if (Main.smartPlugMap.get(part[1]).getStatu().equals("On")){
                                            Main.smartPlugMap.get(part[1]).setOffTime(Main.time);
                                            Main.smartPlugMap.get(part[1]).calculatetime();
                                            double totalenergy = Main.smartPlugMap.get(part[1]).getVolt()*
                                                    Main.smartPlugMap.get(part[1]).getAmpere()*
                                                    Main.smartPlugMap.get(part[1]).getTime()*1/60;
                                            Main.smartPlugMap.get(part[1]).setTotalEnergy(Math.round(
                                                    totalenergy * 100.0) / 100.0);

                                        }
                                        Main.smartPlugMap.get(part[1]).setStatu("0ff");
                                        Main.smartPlugMap.get(part[1]).ZReport2(part[1],writer);
                                        Main.smartPlugMap.remove(part[1]);
                                    }
                                    Main.devicesMap.get(part[1]).setStatu("Off");
                                    Main.devicesMap.remove(part[1]);
                                } else {
                                    writer.write("ERROR: There is not such a device!\n");
                                }
                            } else {
                                writer.write("ERROR: Erroneous command!\n");
                            }
                        } else if (part[0].equals("Switch")) {
                            if (part.length == 3) {
                                if (Main.devicesMap.containsKey(part[1])) {
                                    if (Main.devicesMap.get(part[1]).isOrderOnOrOff(part[2],writer)) {
                                        if (Main.devicesMap.get(part[1]).isDeviceOnOrOff(part[2], Main.devicesMap.
                                                get(part[1]).getStatu(),writer)) {
                                            if (Main.smartLampWithColorMap.containsKey(part[1])) {
                                                Main.smartLampWithColorMap.get(part[1]).setStatu(part[2]);
                                            } else if (Main.smartLampMap.containsKey(part[1])) {
                                                Main.smartLampMap.get(part[1]).setStatu(part[2]);
                                            } else if (Main.smartCameraMap.containsKey(part[1])) {
                                                if (Main.smartCameraMap.get(part[1]).getStatu().equals("On")){
                                                    Main.smartCameraMap.get(part[1]).setOffTime(Main.time);
                                                    Main.smartCameraMap.get(part[1]).calculatetime();
                                                    double totalMB;
                                                    totalMB = Main.smartCameraMap.get(part[1]).getTime()*
                                                            Main.smartCameraMap.get(part[1]).getMbPerMunite();
                                                    Main.smartCameraMap.get(part[1]).setTotalMB(Math.round(
                                                            totalMB * 100.0) / 100.0);
                                                }else {
                                                    Main.smartCameraMap.get(part[1]).setOpenTime(Main.time);
                                                }
                                                Main.smartCameraMap.get(part[1]).setStatu(part[2]);
                                            } else if (Main.smartPlugMap.containsKey(part[1])){
                                                if (Main.smartPlugMap.get(part[1]).getStatu().equals("On")){
                                                    Main.smartPlugMap.get(part[1]).setOffTime(Main.time);
                                                    Main.smartPlugMap.get(part[1]).calculatetime();
                                                    double totalenergy = Main.smartPlugMap.get(part[1]).getVolt()*
                                                            Main.smartPlugMap.get(part[1]).getAmpere()*
                                                            Main.smartPlugMap.get(part[1]).getTime()*1/60;
                                                    Main.smartPlugMap.get(part[1]).setTotalEnergy(Math.round
                                                            (totalenergy * 100.0) / 100.0);
                                                }else {
                                                    Main.smartPlugMap.get(part[1]).setOpenTime(Main.time);
                                                }
                                                Main.smartPlugMap.get(part[1]).setStatu(part[2]);
                                            }
                                            Main.devicesMap.get(part[1]).setStatu(part[2]);
                                        }
                                    }
                                } else {
                                    writer.write("ERROR: There is not such a device!\n");
                                }
                            } else {
                                writer.write("ERROR: Erroneous command!\n");
                            }
                        } else if (part[0].equals("ChangeName")) {
                            if (part.length == 3) {
                                if (part[1].equals(part[2])) {
                                    writer.write("ERROR: Both of the names are the same, nothing changed!\n");
                                } else if (Main.devicesMap.containsKey(part[1])) {
                                    if (!Main.devicesMap.containsKey(part[2])) {
                                        LinkedHashMap<String, SmartDevices> devicesMap2 = new LinkedHashMap<>();
                                        devicesMap2.put(part[2],Main.devicesMap.get(part[1]));
                                        Main.devicesMap.remove(part[1]);
                                        devicesMap2.putAll(Main.devicesMap);
                                        Main.devicesMap = devicesMap2 ;
                                        if (Main.smartLampWithColorMap.containsKey(part[1])) {
                                            Main.smartLampWithColorMap.get(part[1]).setName(part[2]);
                                            Main.smartLampWithColorMap.put(part[2],Main.smartLampWithColorMap.get(part[1]));
                                            Main.smartLampWithColorMap.remove(part[1]);
                                        } else if (Main.smartLampMap.containsKey(part[1])) {
                                            Main.smartLampMap.get(part[1]).setName(part[2]);
                                            Main.smartLampMap.put(part[2],Main.smartLampMap.get(part[1]));
                                            Main.smartLampMap.remove(part[1]);
                                        } else if (Main.smartCameraMap.containsKey(part[1])) {
                                            Main.smartCameraMap.get(part[1]).setName(part[2]);
                                            Main.smartCameraMap.put(part[2],Main.smartCameraMap.get(part[1]));
                                            Main.smartCameraMap.remove(part[1]);
                                        } else if (Main.smartPlugMap.containsKey(part[1])) {
                                            Main.smartPlugMap.get(part[1]).setName(part[2]);
                                            Main.smartPlugMap.put(part[2],Main.smartPlugMap.get(part[1]));
                                            Main.smartPlugMap.remove(part[1]);

                                        }
                                    } else {
                                        writer.write("ERROR: There is already a smart device with same name!\n");

                                    }
                                } else {
                                    writer.write("ERROR: There is not such a device!\n");
                                }

                            } else {
                                writer.write("ERROR: Erroneous command!\n");
                            }
                        } else if (part[0].equals("PlugIn")) {
                            if (part.length == 3) {
                                if (Main.smartPlugMap.containsKey(part[1])) {
                                    if (!Main.smartPlugMap.get(part[1]).isPlugIn()) {
                                        if (Main.smartPlugMap.get(part[1]).isAmpereStringAnInteger(part[2])) {
                                            if (Main.smartPlugMap.get(part[1]).isAmperePositive(part[2])) {
                                                Main.smartPlugMap.get(part[1]).setAmpere(part[2]);
                                                Main.smartPlugMap.get(part[1]).setPlugIn(true);
                                                if (Main.smartPlugMap.get(part[1]).getStatu().equals("On")){
                                                    Main.smartPlugMap.get(part[1]).setOpenTime(Main.time);
                                                }

                                            } else {
                                                writer.write("ERROR: Ampere value must be a positive number!\n");
                                            }
                                        } else {
                                            writer.write("ERROR: Erroneous command!\n");
                                        }
                                    } else {
                                        writer.write("ERROR: There is already an item plugged in to that plug!\n");
                                    }
                                } else {
                                    writer.write("ERROR: This device is not a smart plug!\n");
                                }
                            } else {
                                writer.write("ERROR: Erroneous command!\n");
                            }
                        } else if (part[0].equals("PlugOut")) {
                            if (part.length == 2) {
                                if (Main.smartPlugMap.containsKey(part[1])) {
                                    if (Main.smartPlugMap.get(part[1]).isPlugIn()) {
                                        Main.smartPlugMap.get(part[1]).setPlugIn(false);
                                        if (Main.smartPlugMap.get(part[1]).getStatu().equals("On")){
                                            Main.smartPlugMap.get(part[1]).setOffTime(Main.time);
                                            Main.smartPlugMap.get(part[1]).calculatetime();
                                            double totalenergy;
                                            totalenergy = Main.smartPlugMap.get(part[1]).getVolt()*
                                                    Main.smartPlugMap.get(part[1]).getAmpere()*Main.smartPlugMap.get(part[1]).getTime()*1/60;
                                            Main.smartPlugMap.get(part[1]).setTotalEnergy(Math.round(totalenergy * 100.0) / 100.0);
                                        }
                                    } else {
                                        writer.write("ERROR: This plug has no item to plug out from that plug!\n");
                                    }
                                } else {
                                    writer.write("ERROR: This device is not a smart plug!\n");
                                }
                            } else {
                                writer.write("ERROR: Erroneous command!\n");
                            }
                        } else if (part[0].equals("SetKelvin")) {
                            if (part.length == 3) {
                                if (Main.smartLampMap.containsKey(part[1])) {
                                    if (Main.smartLampMap.get(part[1]).isKelvin(Integer.parseInt(part[2]))) {
                                        Main.smartLampMap.get(part[1]).setKelvin(Integer.parseInt(part[2]));
                                    } else {
                                        writer.write("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                                    }
                                } else if (Main.smartLampWithColorMap.containsKey(part[1])) {
                                    if (Main.smartLampWithColorMap.get(part[1]).isKelvin(Integer.parseInt(part[2]))) {
                                        Main.smartLampWithColorMap.get(part[1]).setKelvin(Integer.parseInt(part[2]));
                                    } else {
                                        writer.write("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                                    }
                                } else {
                                    writer.write("ERROR: This device is not a smart lamp!\n");
                                }
                            } else {
                                writer.write("ERROR: Erroneous command!\n");
                            }
                        } else if (part[0].equals("SetBrightness")) {
                            if (part.length == 3) {
                                if (Main.smartLampMap.containsKey(part[1])) {
                                    if (Main.smartLampMap.get(part[1]).isBrightness(Integer.parseInt(part[2]))) {
                                        Main.smartLampMap.get(part[1]).setBrightness(Integer.parseInt(part[2]));
                                    } else writer.write("ERROR: Brightness must be in range of 0%-100%!\n");
                                } else if (Main.smartLampWithColorMap.containsKey(part[1])) {
                                    if (Main.smartLampWithColorMap.get(part[1]).isBrightness(Integer.parseInt(part[2]))) {
                                        Main.smartLampWithColorMap.get(part[1]).setBrightness(Integer.parseInt(part[2]));
                                    } else writer.write("ERROR: Brightness must be in range of 0%-100%!\n");
                                } else {
                                    writer.write("ERROR: This device is not a smart lamp!\n");
                                }
                            } else {
                                writer.write("ERROR: Erroneous command!\n");
                            }

                        } else if (part[0].equals("SetColorCode")) {
                            if (part.length == 3) {
                                if (Main.smartLampWithColorMap.containsKey(part[1])) {
                                    if (Main.smartLampWithColorMap.get(part[1]).isStringAnInteger(part[2])) {
                                        if (Main.smartLampWithColorMap.get(part[1]).isKelvin(Integer.parseInt(part[2]))) {
                                            Main.smartLampWithColorMap.get(part[1]).setKelvin(Integer.parseInt(part[2]));
                                        }
                                    } else if (Main.smartLampWithColorMap.get(part[1]).isHexadecimal(part[2])) {
                                        if (Main.smartLampWithColorMap.get(part[1]).isHexaInRange(part[2])) {
                                            Main.smartLampWithColorMap.get(part[1]).setHexadecimal(part[2]);
                                        }

                                    } else {
                                        writer.write("ERROR: Erroneous command!\n");
                                    }
                                } else {
                                    writer.write("ERROR: This device is not a smart color lamp!\n");
                                }

                            } else {
                                writer.write("ERROR: Erroneous command!\n");
                            }
                        } else if (part[0].equals("SetWhite")) {
                            if (part.length == 4) {
                                if (Main.smartLampWithColorMap.containsKey(part[1])) {
                                    if (Main.smartLampWithColorMap.get(part[1]).isStringAnInteger(part[2])) {
                                        if (Main.smartLampWithColorMap.get(part[1]).isKelvin(Integer.parseInt(part[2]))) {
                                            if (Main.smartLampWithColorMap.get(part[1]).isStringAnInteger(part[3])) {
                                                if (Main.smartLampWithColorMap.get(part[1]).isBrightness(Integer.parseInt(part[3]))) {
                                                    Main.smartLampWithColorMap.get(part[1]).setKelvin(Integer.parseInt(part[2]));
                                                    Main.smartLampWithColorMap.get(part[1]).setBrightness(Integer.parseInt(part[3]));
                                                } else {
                                                    writer.write("ERROR: Brightness must be in range of 0%-100%!\n");
                                                }
                                            } else {
                                                writer.write("ERROR: Erroneous command!\n");
                                            }
                                        } else {
                                            writer.write("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                                        }
                                    } else if (Main.smartLampWithColorMap.get(part[1]).isHexadecimal(part[2])) {
                                        if (Main.smartLampWithColorMap.get(part[1]).isHexaInRange(part[2])) {
                                            if (Main.smartLampWithColorMap.get(part[1]).isStringAnInteger(part[3])) {
                                                if (Main.smartLampWithColorMap.get(part[1]).isBrightness(Integer.parseInt(part[3]))) {
                                                    Main.smartLampWithColorMap.get(part[1]).setHexadecimal(part[2]);
                                                    Main.smartLampWithColorMap.get(part[1]).setBrightness(Integer.parseInt(part[3]));
                                                } else {
                                                    writer.write("ERROR: Brightness must be in range of 0%-100%!\n");
                                                }
                                            } else {
                                                writer.write("ERROR: Erroneous command!\n");
                                            }
                                        } else {
                                            writer.write("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                                        }
                                    } else {
                                        writer.write("ERROR: Erroneous command!\n");
                                    }
                                } else if (Main.smartLampMap.containsKey(part[1])) {
                                    if (Main.smartLampMap.get(part[1]).isStringAnInteger(part[2])) {
                                        if (Main.smartLampMap.get(part[1]).isKelvin(Integer.parseInt(part[2]))) {
                                            if (Main.smartLampMap.get(part[1]).isStringAnInteger(part[3])) {
                                                if (Main.smartLampMap.get(part[1]).isBrightness(Integer.parseInt(part[3]))) {
                                                    Main.smartLampMap.get(part[1]).setKelvin(Integer.parseInt(part[2]));
                                                    Main.smartLampMap.get(part[1]).setBrightness(Integer.parseInt(part[3]));
                                                } else {
                                                    writer.write("ERROR: Brightness must be in range of 0%-100%!\n");
                                                }
                                            } else {
                                                writer.write("ERROR: Erroneous command!\n");
                                            }
                                        } else {
                                            writer.write("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                                        }
                                    } else {
                                        writer.write("ERROR: Erroneous command!\n");
                                    }
                                } else {
                                    writer.write("ERROR: This device is not a smart lamp!\n");
                                }
                            } else {
                                writer.write("ERROR: Erroneous command!\n");
                            }
                        } else if (part[0].equals("SetColor")) {
                            if (part.length == 4) {
                                if (Main.smartLampWithColorMap.containsKey(part[1])) {
                                    if (Main.smartLampWithColorMap.get(part[1]).isStringAnInteger(part[2])) {
                                        if (Main.smartLampWithColorMap.get(part[1]).isKelvin(Integer.parseInt(part[2]))) {
                                            if (Main.smartLampWithColorMap.get(part[1]).isStringAnInteger(part[3])) {
                                                if (Main.smartLampWithColorMap.get(part[1]).isBrightness(Integer.parseInt(part[3]))) {
                                                    Main.smartLampWithColorMap.get(part[1]).setKelvin(Integer.parseInt(part[2]));
                                                    Main.smartLampWithColorMap.get(part[1]).setBrightness(Integer.parseInt(part[3]));
                                                } else {
                                                    writer.write("ERROR: Brightness must be in range of 0%-100%!\n");
                                                }
                                            } else {
                                                writer.write("ERROR: Erroneous command!\n");
                                            }
                                        } else {
                                            writer.write("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                                        }
                                    } else if (Main.smartLampWithColorMap.get(part[1]).isHexadecimal(part[2])) {
                                        if (Main.smartLampWithColorMap.get(part[1]).isHexaInRange(part[2])) {
                                            if (Main.smartLampWithColorMap.get(part[1]).isStringAnInteger(part[3])) {
                                                if (Main.smartLampWithColorMap.get(part[1]).isBrightness(Integer.parseInt(part[3]))) {
                                                    Main.smartLampWithColorMap.get(part[1]).setHexadecimal(part[2]);
                                                    Main.smartLampWithColorMap.get(part[1]).setBrightness(Integer.parseInt(part[3]));
                                                } else {
                                                    writer.write("ERROR: Brightness must be in range of 0%-100%!\n");
                                                }
                                            } else {
                                                writer.write("ERROR: Erroneous command!\n");
                                            }
                                        } else {
                                            writer.write("ERROR: Kelvin value must be in range of 2000K-6500K!\n");
                                        }
                                    } else {
                                        writer.write("ERROR: Erroneous command!\n");
                                    }
                                } else {
                                    writer.write("ERROR: This device is not a smart color lamp!\n");
                                }
                            } else {
                                writer.write("ERROR: Erroneous command!\n");
                            }
                        }
                        else if (part[0].equals("ILLEGAL_COMMAND")){
                            writer.write("ERROR: Erroneous command!\n");
                        }
                    }
                }else if (timer == 0 && !line.startsWith("SetInitialTime")) {
                    writer.write("COMMAND: " + line + "\n");
                    writer.write("ERROR: First command must be set initial time! Program is going to terminate!\n");
                    lastLine = "ZReport";
                    break;
                }
            }
        }
        if (!lastLine.equals("ZReport")) {
            writer.write("ZReport:\n");
            writer.write("Time is:\t" + Main.date + "_" + Main.time + "\n");
            for (String string : Main.switchMap.keySet()) {
                if (Main.smartLampWithColorMap.containsKey(string)) {
                    Main.smartLampWithColorMap.get(string).ZReport(string,writer);
                } else if (Main.smartCameraMap.containsKey(string)) {
                    Main.smartCameraMap.get(string).ZReport(string,writer);
                } else if (Main.smartLampMap.containsKey(string)) {
                    Main.smartLampMap.get(string).ZReport(string,writer);
                } else if (Main.smartPlugMap.containsKey(string)) {
                    Main.smartPlugMap.get(string).ZReport(string,writer);
                }
            }
            for (String string : Main.devicesMap.keySet()) {
                if (Main.smartLampWithColorMap.containsKey(string)) {
                    Main.smartLampWithColorMap.get(string).ZReport2(string,writer);
                } else if (Main.smartCameraMap.containsKey(string)) {
                    Main.smartCameraMap.get(string).ZReport2(string,writer);
                } else if (Main.smartLampMap.containsKey(string)) {
                    Main.smartLampMap.get(string).ZReport2(string,writer);
                } else if (Main.smartPlugMap.containsKey(string)) {
                    Main.smartPlugMap.get(string).ZReport2(string,writer);
                }

            }
        }writer.close();
    }
}
