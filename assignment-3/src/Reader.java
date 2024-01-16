import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;

public class Reader {
    public static void main(BufferedReader reader, BufferedWriter writer) throws IOException {
        String line;
        int booksID = 1;
        int membersID = 1;
        while ((line = reader.readLine()) != null) {
            String[] part = line.split("\t");
            switch (part[0]){
                case "addBook":
                    if (part.length == 2){
                        if (part[1].equals("P") || part[1].equals("H")){
                            switch (part[1]){
                                case "H":
                                    HandwrittenBooks handwrittenBooks = new HandwrittenBooks();
                                    handwrittenBooks.setBookID(booksID);
                                    handwrittenBooks.setIsBookInLibrary(true);
                                    Main.books.put(booksID,handwrittenBooks);
                                    writer.write("Created new book: Handwritten [id: "+booksID+"]\n");
                                    booksID ++;
                                    break;
                                case "P":
                                    PrintedBooks printedBooks = new PrintedBooks();
                                    printedBooks.setBookID(booksID);
                                    printedBooks.setIsBookInLibrary(true);
                                    Main.books.put(booksID,printedBooks);
                                    writer.write("Created new book: Printed [id: "+booksID+"]\n");
                                    booksID ++;
                                    break;

                            }
                        }
                    }
                    break;

                case "addMember":
                    if (part[1].equals("A") || part[1].equals("S")){
                        switch (part[1]){
                            case "A":
                                Academics academicians = new Academics();
                                Main.members.put(membersID,academicians);
                                writer.write("Created new member: Academic [id: "+membersID+"]\n");
                                membersID ++;
                                break;
                            case "S":
                                Students students = new Students();
                                Main.members.put(membersID,students);
                                writer.write("Created new member: Student [id: "+membersID+"]\n");
                                membersID ++;
                                break;

                        }
                    }
                    break;
                case "borrowBook":
                    if(Main.books.get(Integer.valueOf(part[1])).isIsBookInLibrary()){
                        if(Main.members.containsKey(Integer.parseInt(part[2]))){
                            switch (Main.books.get(Integer.parseInt(part[1])).getBookKind()){
                                case "P":
                                    PrintedBooks printedBooks = new PrintedBooks();
                                    switch (Main.members.get(Integer.parseInt(part[2])).getPersonKind()){
                                        case "A":
                                            if(Main.members.get(Integer.valueOf(part[2])).getHowManyBookYouHave()<4){
                                                printedBooks.setBarrowDate(LocalDate.parse(part[3]));
                                                LocalDate returningDate = LocalDate.parse(part[3]).plusWeeks(4);
                                                printedBooks.setDeadlineOfBook(returningDate);
                                                printedBooks.setWhoHasBook(Integer.parseInt(part[2]));
                                                Main.borrowedBooks.put(Integer.parseInt(part[1]),printedBooks);
                                                writer.write("The book ["+part[1]+"] was borrowed by member ["+part[2]+"] at "+part[3]+"\n");
                                                Main.books.get(Integer.valueOf(part[1])).setIsBookInLibrary(false);
                                                Main.members.get(Integer.valueOf(part[2])).setHowManyBookYouHave(1);
                                            }
                                            else writer.write("You have exceeded the borrowing limit!\n");
                                            break;
                                        case "S":
                                            if(Main.members.get(Integer.valueOf(part[2])).getHowManyBookYouHave()<2){
                                                printedBooks.setBarrowDate(LocalDate.parse(part[3]));
                                                LocalDate returningDate2 = LocalDate.parse(part[3]).plusWeeks(2);
                                                printedBooks.setDeadlineOfBook(returningDate2);
                                                printedBooks.setWhoHasBook(Integer.parseInt(part[2]));
                                                Main.borrowedBooks.put(Integer.valueOf(part[1]),printedBooks);
                                                writer.write("The book ["+part[1]+"] was borrowed by member ["+part[2]+"] at "+part[3]+"\n");
                                                Main.books.get(Integer.valueOf(part[1])).setIsBookInLibrary(false);
                                                Main.members.get(Integer.valueOf(part[2])).setHowManyBookYouHave(1);
                                            }
                                            else writer.write("You have exceeded the borrowing limit!\n");
                                            break;
                                    }
                                    break;
                                case "H":
                                    writer.write("You cannot borrow this book!"+"\n");
                                    break;
                            }
                        }
                    }
                    else writer.write("You cannot borrow this book!"+"\n");
                    break;
                case "returnBook":
                    if(!Main.books.get(Integer.valueOf(part[1])).isIsBookInLibrary()){
                        if(Main.members.containsKey(Integer.parseInt(part[2]))){
                            try{
                                if(Main.borrowedBooks.get(Integer.valueOf(part[1])).
                                    getDeadlineOfBook().isBefore(LocalDate.parse(part[3]))){
                                    writer.write("The book ["+part[1]+"] was returned by member ["+part[2]+"] at "+ part[3]+" Fee: 0"+"\n");
                                    Main.members.get(Integer.valueOf(part[2])).setHowManyBookYouHave(-1);
                            }
                            else {
                                writer.write("The book ["+part[1]+"] was returned by member ["+part[2]+"] at "+ part[3]+" Fee: 1"+"\n");
                                Main.members.get(Integer.parseInt(part[2])).setHasPenalty(true);}
                            }catch (NullPointerException e){
                                writer.write("The book ["+part[1]+"] was returned by member ["+part[2]+"] at "+ part[3]+" Fee: 0"+"\n");
                                Main.members.get(Integer.valueOf(part[2])).setHowManyBookYouHave(-1);
                                if (Main.readBookInLibrary.containsKey(Integer.parseInt(part[1]))){
                                    Main.readBookInLibrary.remove(Integer.valueOf(part[1]));
                                }
                            }
                            Main.books.get(Integer.parseInt(part[1])).setIsBookInLibrary(true);
                            Main.borrowedBooks.remove(Integer.valueOf(part[1]));
                            if (Main.readBookInLibrary.containsKey(Integer.parseInt(part[1]))){
                                Main.readBookInLibrary.remove(Integer.valueOf(part[1]));
                            }
                        }
                    }
                    break;
                case "extendBook":
                    if(Main.books.containsKey(Integer.valueOf(part[1]))){
                        if(Main.members.containsKey(Integer.valueOf(part[2]))) {
                            if(Main.members.get(Integer.valueOf(part[2])).getExtendLimit()<1){
                                switch (Main.members.get(Integer.parseInt(part[2])).getPersonKind()) {
                                    case "A":
                                        LocalDate newDeadline = LocalDate.parse(part[3]).plusWeeks(2);
                                        Main.borrowedBooks.get(Integer.parseInt(part[1])).setDeadlineOfBook(newDeadline);
                                        writer.write("The deadline of book [" + part[1] + "] was extended by member [" + part[2] + "] at " + part[3] + "\n");
                                        writer.write("New deadline of book [" + part[1] + "] is " + newDeadline + "\n");
                                        Main.members.get(Integer.parseInt(part[2])).setExtendLimit(1);
                                        break;
                                    case "S":
                                        LocalDate newDeadline2 = LocalDate.parse(part[3]).plusWeeks(1);
                                        Main.borrowedBooks.get(Integer.parseInt(part[1])).setDeadlineOfBook(newDeadline2);
                                        writer.write("The deadline of book [" + part[1] + "] was extended by member [" + part[2] + "] at " + part[3] + "\n");
                                        writer.write("New deadline of book [" + part[1] + "] is " + newDeadline2 + "\n");
                                        Main.members.get(Integer.parseInt(part[2])).setExtendLimit(1);
                                        break;
                                }
                                }else writer.write("You cannot extend the deadline!\n");
                            }
                        }
                    break;
                case "readInLibrary":
                    if(Main.books.get(Integer.valueOf(part[1])).isIsBookInLibrary()){
                        if(Main.members.containsKey(Integer.parseInt(part[2]))){
                            PrintedBooks printedBooks = new PrintedBooks();
                            HandwrittenBooks handwrittenBooks = new HandwrittenBooks();
                            if (Main.members.get(Integer.parseInt(part[2])).getPersonKind().equals("S")) {
                                if (Main.books.get(Integer.valueOf(part[1])).getBookKind().equals("H")){
                                    writer.write("Students can not read handwritten books!"+"\n");
                                }
                                else {
                                    handwrittenBooks.setReadeDate(LocalDate.parse(part[3]));
                                    handwrittenBooks.setBookID(Integer.parseInt(part[1]));
                                    Main.books.get(Integer.valueOf(part[1])).setIsBookInLibrary(false);
                                    handwrittenBooks.setWhoHasBook(Integer.parseInt(part[2]));
                                    Main.readBookInLibrary.put(Integer.valueOf(part[1]),handwrittenBooks);
                                    writer.write("The book ["+part[1]+"] was read in library by member ["+part[2]+"] at "+part[3]+"\n");}

                            }
                            else {
                                printedBooks.setReadeDate(LocalDate.parse(part[3]));
                                printedBooks.setBookID(Integer.parseInt(part[1]));
                                Main.books.get(Integer.valueOf(part[1])).setIsBookInLibrary(false);
                                printedBooks.setWhoHasBook(Integer.parseInt(part[2]));
                                Main.readBookInLibrary.put(Integer.valueOf(part[1]),printedBooks);
                                writer.write("The book ["+part[1]+"] was read in library by member ["+part[2]+"] at "+part[3]+"\n");}

                        }
                    }else writer.write("You can not read this book!"+"\n");
                    break;
                case "getTheHistory":
                    writer.write("History of library:\n\n");
                    int numberOfStudents =0;
                    int numberOfAcademics =0;
                    int numberOfPrintedBooks=0;
                    int numberOfHandWrittenBooks=0;
                    for (Integer i : Main.members.keySet()){
                        if(Main.members.get(i).getPersonKind().equals("S")){
                            numberOfStudents++;
                        }
                        else if(Main.members.get(i).getPersonKind().equals("A")){
                            numberOfAcademics++;
                        }
                    }
                    for (Integer i : Main.books.keySet()){
                        if(Main.books.get(i).getBookKind().equals("H")){
                            numberOfHandWrittenBooks++;
                        }
                        else if(Main.books.get(i).getBookKind().equals("P")){
                            numberOfPrintedBooks++;
                        }
                    }
                    writer.write("Number of students: "+numberOfStudents+"\n");
                    for (Integer i : Main.members.keySet()){
                        if(Main.members.get(i).getPersonKind().equals("S")){
                            writer.write("Student [id: "+i+"]"+"\n");
                        }
                    }

                    writer.write("\nNumber of academics: "+numberOfAcademics+"\n");
                    for (Integer i : Main.members.keySet()){
                        if(Main.members.get(i).getPersonKind().equals("A")){
                            writer.write("Academic [id: "+i+"]"+"\n");
                        }
                    }
                    writer.write("\nNumber of printed books: "+numberOfPrintedBooks+"\n");
                    for (Integer i : Main.books.keySet()) {
                        if (Main.books.get(i).getBookKind().equals("P")) {
                            writer.write("Printed [id: "+i+"]"+"\n");
                        }
                    }
                    writer.write("\nNumber of handwritten books: "+numberOfHandWrittenBooks+"\n");
                    for (Integer i : Main.books.keySet()) {
                        if (Main.books.get(i).getBookKind().equals("H")) {
                            writer.write("Handwritten [id: "+i+"]"+"\n");
                        }
                    }
                    writer.write("\nNumber of borrowed books: "+Main.borrowedBooks.size()+"\n");
                    for (Integer i : Main.borrowedBooks.keySet()) {
                        writer.write("The book ["+i+"] was borrowed by member" +
                                " ["+Main.borrowedBooks.get(i).getWhoHasBook()+"] at "+Main.borrowedBooks.get(i).getBarrowDate()+"\n");
                    }
                    writer.write("\nNumber of books read in library: "+Main.readBookInLibrary.size()+"\n");
                    for (Integer i : Main.readBookInLibrary.keySet()) {
                        writer.write("The book ["+i+"] was read in library by member" +
                                " ["+Main.readBookInLibrary.get(i).getWhoHasBook()+"] at "+Main.readBookInLibrary.get(i).getReadeDate()+"\n");
                    }
                    break;
            }
        }
    reader.close();
    writer.close();
    }
}
