package edu.northeastern.numad23fa_groupproject1.Login;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class UserModel implements Parcelable {
    String userId, username, email;
    int score;

    // This is for the toObject method of the Document snapshot (related to Firestore)
    public UserModel() {}

    public UserModel(String mUserId, String mEmail, String mUsername, int mScore) {
        this.userId = mUserId;
        this.email = mEmail;
        this.username = mUsername;
        this.score = mScore;
    }

    public UserModel(String mUserId, String mEmail, int mScore) {
        this.userId = mUserId;
        this.email = mEmail;
        this.username = mEmail.split("@")[0];;
        this.score = mScore;
    }

    protected UserModel(Parcel in) {
        userId = in.readString();
        username = in.readString();
        email = in.readString();
        score = in.readInt();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeInt(score);
    }
}