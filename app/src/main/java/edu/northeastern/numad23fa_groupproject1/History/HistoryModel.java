package edu.northeastern.numad23fa_groupproject1.History;

import android.os.Parcel;
import android.os.Parcelable;

public class HistoryModel implements Parcelable {
    private int imageId;
    private String date;
    private String eventName;

    private String description;

    private boolean visibility;

    // constructor
    public HistoryModel() {

    }
    public HistoryModel(int imageId, String date, String eventName, String description, boolean visibility) {
        this.imageId = imageId;
        this.date = date;
        this.eventName = eventName;
        this.description = description;
        this.visibility = visibility;
    }

    // getters and setters
    public int getImageId() { return imageId; }

    public void setImageId(int imageId) { this.imageId = imageId; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getEventName() { return eventName; }

    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public boolean isVisibility() { return visibility; }

    public void setVisibility(boolean visibility) { this.visibility = visibility; }



    // methods inherited from Parcelable
    protected HistoryModel(Parcel in) {
        imageId = in.readInt();
        date = in.readString();
        eventName = in.readString();
        description = in.readString();
        visibility = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imageId);
        dest.writeString(date);
        dest.writeString(eventName);
        dest.writeString(description);
        dest.writeByte((byte) (visibility ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HistoryModel> CREATOR = new Creator<HistoryModel>() {
        @Override
        public HistoryModel createFromParcel(Parcel in) {
            return new HistoryModel(in);
        }

        @Override
        public HistoryModel[] newArray(int size) {
            return new HistoryModel[size];
        }
    };
}
