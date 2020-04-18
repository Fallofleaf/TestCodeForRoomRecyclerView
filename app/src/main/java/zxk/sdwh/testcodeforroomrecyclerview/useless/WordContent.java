package zxk.sdwh.testcodeforroomrecyclerview.useless;

public class WordContent {
    public int number;
    public String english;
    public boolean isTitle;

    public WordContent(int number, String english, boolean isTitle) {
        this.number = number;
        this.english = english;
        this.isTitle = isTitle;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }
}
