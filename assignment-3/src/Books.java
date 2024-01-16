import java.time.LocalDate;

public class Books {

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    private int bookID;

    public int getWhoHasBook() {
        return whoHasBook;
    }

    public void setWhoHasBook(int whoHasBook) {
        this.whoHasBook = whoHasBook;
    }

    private int whoHasBook;

    public boolean isIsBookInLibrary() {
        return isBookInLibrary;
    }

    public void setIsBookInLibrary(boolean isBookInLibrary) {
        this.isBookInLibrary = isBookInLibrary;
    }

    private boolean isBookInLibrary = false;

    public String getBookKind() {
        return BookKind;
    }

    public void setBookKind(String bookKind) {
        BookKind = bookKind;
    }

    private String BookKind;

    public LocalDate getReadeDate() {
        return ReadeDate;
    }

    public void setReadeDate(LocalDate readeDate) {
        ReadeDate = readeDate;
    }

    private LocalDate ReadeDate;

    public LocalDate getDeadlineOfBook() {
        return deadlineOfBook;
    }

    public void setDeadlineOfBook(LocalDate deadlineOfBook) {
        this.deadlineOfBook = deadlineOfBook;
    }

    private LocalDate deadlineOfBook;

    public LocalDate getBarrowDate() {
        return barrowDate;
    }

    public void setBarrowDate(LocalDate barrowDate) {
        this.barrowDate = barrowDate;
    }

    private LocalDate barrowDate;



}
