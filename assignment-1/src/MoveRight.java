import java.util.HashMap;
public class MoveRight {
    public static void move_right(int number,int row,HashMap<Integer,String> dict,int total,HashMap<String,Integer> dictForLocation) {
        if ((number) % row == row) {
            int temporay = number;
            temporay -= row;
            if (dict.get(temporay).equals("H")) {
                dict.put(number, " ");
            }
            else if (dict.get(temporay).equals("W")) {
                String value = dict.get(temporay-1);
                if (value.equals("H")) {
                    dict.put(number, " ");
                    System.out.println("The Ball Collision Game has finished");
                }
                else if (value.equals("R")){
                    total += 10;
                    dict.put(temporay - 1,"X");
                }else if (value.equals("Y")){
                    total +=5;
                    dict.put(temporay - 1,"X");
                }else if (value.equals("B")){
                    total -=5;
                    dict.put(temporay - 1,"X");
                }
                temporay -= row;
                dict.put(number  ,dict.get(temporay - 1));
                dict.put(temporay - 1 ,"*");
                dictForLocation.put("*",temporay - 1);

            }
            else {
                String value = dict.get(temporay+1);
                if (value.equals("R")){
                    total += 10;
                    dict.put(temporay + 1,"X");
                }else if (value.equals("Y")){
                    total +=5;
                    dict.put(temporay + 1,"X");
                }else if (value.equals("B")){
                    total -=5;
                    dict.put(temporay + 1,"X");
                }
                dict.put(number, dict.get(temporay + 1));
                dict.put(temporay + 1, "*");
                dictForLocation.put("*", temporay + 1);
            }
        } else {
            if (number % row == 1 && dict.get(number + 1).equals("W")) {
                int temp = 0;
                temp += number +row - 1;
                if (dict.get(temp).equals("H")) {
                    dict.put(number, " ");
                }
                else if (dict.get(temp).equals("R")){
                    total += 10;
                    dict.put(number + 1,"X");
                }else if (dict.get(temp).equals("Y")){
                    total +=5;
                    dict.put(number + 1,"X");
                }else if (dict.get(temp).equals("B")){
                    total -=5;
                    dict.put(number + 1,"X");
                }
                dict.put(number ,dict.get(temp));
                dict.put(temp,"*");
                dictForLocation.put("*",temp);
            }

            else if (dict.get(number +1 ).equals("H")) {
                dict.put(number, " ");
            }
            else if (dict.get(number + 1).equals("W")) {
                String value = dict.get(number-1);
                if (value.equals("H")) {
                    dict.put(number, " ");
                }
                else if (value.equals("R")){
                    total += 10;
                    dict.put(number -1,"X");
                }else if (value.equals("Y")){
                    total +=5;
                    dict.put(number -1,"X");
                }else if (value.equals("B")){
                    total -=5;
                    dict.put(number -1,"X");
                }
                dict.put(number  ,dict.get(number - 1));
                dict.put(number -1  ,"*");
                dictForLocation.put("*",number - 1);

            }
            else {
                String value = dict.get(number+1);
                if (value.equals("R")){
                    total += 10;
                    dict.put(number + 1,"X");
                }else if (value.equals("Y")){
                    total +=5;
                    dict.put(number + 1,"X");
                }else if (value.equals("B")){
                    total -=5;
                    dict.put(number + 1,"X");
                }
                dict.put(number, dict.get(number + 1));
                dict.put(number + 1, "*");
                dictForLocation.put("*", number + 1);
            }
        }
    }
}
