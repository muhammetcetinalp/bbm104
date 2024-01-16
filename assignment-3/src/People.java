import java.time.LocalDateTime;

public class People {

    public boolean getHasPenalty() {
        return hasPenalty;
    }

    public void setHasPenalty(boolean hasPenalty) {
        this.hasPenalty = hasPenalty;
    }

    private boolean hasPenalty;


    public int getExtendLimit() {
        return extendLimit;
    }

    public void setExtendLimit(int extendLimit) {
        this.extendLimit = extendLimit;
    }

    private int extendLimit = 0;

    public String getPersonKind() {
        return personKind;
    }

    public void setPersonKind(String personKind) {
        this.personKind = personKind;
    }

    private String personKind;


    public int getHowManyBookYouHave() {
        return howManyBookYouHave;
    }

    public void setHowManyBookYouHave(int a) {
        this.howManyBookYouHave = this.howManyBookYouHave + a;
    }

    private int howManyBookYouHave = 0;


}
