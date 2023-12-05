package edu.northeastern.numad23fa_groupproject1;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Question implements Parcelable {
    int id;
    String question;
    String optionOne;
    String optionTwo;
    String optionThree;
    String optionFour;

    int correctAnswer;

    public Question() {

    }


    public Question(int id, String question, String optionOne, String optionTwo, String optionThree, String optionFour, int correctAnswer) {
        this.id = id;
        this.question = question;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.optionThree = optionThree;
        this.optionFour = optionFour;
        this.correctAnswer = correctAnswer;
    }

    protected Question(Parcel in) {
        id = in.readInt();
        question = in.readString();
        optionOne = in.readString();
        optionTwo = in.readString();
        optionThree = in.readString();
        optionFour = in.readString();
        correctAnswer = in.readInt();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionOne() {
        return optionOne;
    }

    public void setOptionOne(String optionOne) {
        this.optionOne = optionOne;
    }

    public String getOptionTwo() {
        return optionTwo;
    }

    public void setOptionTwo(String optionTwo) {
        this.optionTwo = optionTwo;
    }

    public String getOptionThree() {
        return optionThree;
    }

    public void setOptionThree(String optionThree) {
        this.optionThree = optionThree;
    }

    public String getOptionFour() {
        return optionFour;
    }

    public void setOptionFour(String optionFour) {
        this.optionFour = optionFour;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(question);
        dest.writeString(optionOne);
        dest.writeString(optionTwo);
        dest.writeString(optionThree);
        dest.writeString(optionFour);
        dest.writeInt(correctAnswer);
    }
}
