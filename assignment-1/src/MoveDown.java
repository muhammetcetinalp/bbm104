import java.util.HashMap;

public class MoveDown {
    public static void move_down(int number,int row,HashMap<Integer,String> dict,int total,HashMap<String,Integer> dictForLocation,int column){
        if (number >= row * (column - 1)) {
            int temporay = number;
            temporay -= (column - 1) * row;
            if (dict.get(temporay).equals("H")) {
                dict.put(number, " ");
            } else if (dict.get(temporay).equals("W")) {
                String value = dict.get(number - row);
                if (value.equals("H")) {
                    dict.put(number, " ");
                } else if (value.equals("R")) {
                    total += 10;
                    dict.put(number - row, "X");
                } else if (value.equals("Y")) {
                    total += 5;
                    dict.put(number - row, "X");
                } else if (value.equals("B")) {
                    total -= 5;
                    dict.put(number - row, "X");
                }
                dict.put(number, dict.get(number - row));
                dict.put(number - row, "*");
                dictForLocation.put("*", number - row);
            } else {
                String value = dict.get(temporay);
                if (value.equals("R")) {
                    total += 10;
                    dict.put(temporay, "X");
                } else if (value.equals("Y")) {
                    total += 5;
                    dict.put(temporay, "X");
                } else if (value.equals("B")) {
                    total -= 5;
                    dict.put(temporay, "X");
                }
                dict.put(number, dict.get(temporay));
                dict.put(temporay, "*");
                dictForLocation.put("*", temporay);
            }
        } else {
            if (number <= row && dict.get(number + row).equals("W")) {
                int temp = 0;
                temp += number + (column - 1) * row;
                if (dict.get(temp).equals("H")) {
                    dict.put(number, " ");
                } else if (dict.get(temp).equals("R")) {
                    total += 10;
                    dict.put(number + 1, "X");
                } else if (dict.get(temp).equals("Y")) {
                    total += 5;
                    dict.put(number + 1, "X");
                } else if (dict.get(temp).equals("B")) {
                    total -= 5;
                    dict.put(number + 1, "X");
                }
                dict.put(number, dict.get(temp));
                dict.put(temp, "*");
                dictForLocation.put("*", temp);

            } else if (dict.get(number + row).equals("H")) {
                dict.put(number, " ");
            } else if (dict.get(number + row).equals("W")) {
                String value = dict.get(number - row);
                if (value.equals("H")) {
                    dict.put(number, " ");
                } else if (value.equals("R")) {
                    total += 10;
                    dict.put(number - row, "X");
                } else if (value.equals("Y")) {
                    total += 5;
                    dict.put(number - row, "X");
                } else if (value.equals("B")) {
                    total -= 5;
                    dict.put(number - row, "X");
                }
                dict.put(number, dict.get(number - row));
                dict.put(number - row, "*");
                dictForLocation.put("*", number - row);
            } else {
                String value = dict.get(number + row);
                if (value.equals("R")) {
                    total += 10;
                    dict.put(number + row, "X");
                } else if (value.equals("Y")) {
                    total += 5;
                    dict.put(number + row, "X");
                } else if (value.equals("B")) {
                    total -= 5;
                    dict.put(number + row, "X");
                }
                dict.put(number, dict.get(number + row));
                dict.put(number + row, "*");
                dictForLocation.put("*", number + row);
            }
        }
    }
}
