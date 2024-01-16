import java.util.*;
import java.io.*;

public class Main {

    public static void main (String[] args) throws IOException {

        File file = new File("output.txt");

        FileWriter fWriter = new FileWriter(file, false);
        BufferedWriter bWriter = new BufferedWriter(fWriter);

        FileReader fReader = new FileReader(args[0]);
        String line;
        BufferedReader bReader = new BufferedReader(fReader);

        FileReader fReader2 = new FileReader(args[1]);
        String line2;
        BufferedReader bReader2 = new BufferedReader(fReader2);

        HashMap<Integer, String> dict = new HashMap<>();
        HashMap<Integer, String> dictForMove = new HashMap<>();
        int timer = 1, timer2 = 0, timerForMove = 1, row = 0;
        HashMap<String, Integer> dictForLocation = new HashMap<>();


        while ((line = bReader.readLine()) != null) {
            String[] parts = line.split(" ");
            row = parts.length;
            for (String part : parts) {
                dict.put(timer, part);
                if (part.equals("*")) {
                    dictForLocation.put(part, timer);
                }
                timer += 1;
            }
            timer2 += 1;
        }
        int column = timer2;

        while ((line2 = bReader2.readLine()) != null) {
            String[] parts2 = line2.split(" ");
            for (String s : parts2) {
                dictForMove.put(timerForMove, s);
                timerForMove += 1;
            }

        }
        int total = 0;
        System.out.println("Game board:");
        bWriter.write("Game board:\n");
        for (int a = 1; a <= dict.size(); a++) {
            System.out.print(dict.get(a) + " ");
            bWriter.write(dict.get(a) + " ");
            if (a % row == 0) {
                System.out.print("\n");
                bWriter.write("\n");
            }
        }
        System.out.println();
        System.out.println("Your movement is:");
        bWriter.write("\nYour movement is:\n");
        for (int a = 1; a <= dictForMove.size(); a++) {
            System.out.print(dictForMove.get(a) + " ");
            bWriter.write(dictForMove.get(a) + " ");
        }
        System.out.println("\n");
        bWriter.write("\n\n");

        for (int i = 1; i < timerForMove; i++) {
            int number = dictForLocation.get("*");

            if (dictForMove.get(i).equals("L")) {
                MoveLeft.move_left(number, row, dict, total, dictForLocation);
            }
            if (dictForMove.get(i).equals("R")) {       
                MoveRight.move_right(number, row, dict, total, dictForLocation);
            }

            if (dictForMove.get(i).equals("U")) {
                MoveUp.move_up(number, row, dict, total, dictForLocation,column);
            }

            if (dictForMove.get(i).equals("D")) {
                MoveDown.move_down(number, row, dict, total, dictForLocation,column);
            }
            System.out.println("Your output is:");
            bWriter.write("Your output is:\n");
            for (int a = 1; a <= dict.size(); a++) {
                System.out.print(dict.get(a) + " ");
                bWriter.write(dict.get(a) + " ");
                if (a % row == 0) {
                    System.out.print("\n");
                    bWriter.write("\n");
                }
            }
            System.out.println();
            bWriter.write("\n");
            System.out.println("Game Over!");
            bWriter.write("Game Over!\n");
            System.out.println("Score: " + total);
            bWriter.write("Score: " + total);

            bReader.close();
            bWriter.close();


        }

    }
}
