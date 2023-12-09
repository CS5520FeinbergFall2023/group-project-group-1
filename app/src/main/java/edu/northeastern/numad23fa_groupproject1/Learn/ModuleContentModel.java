package edu.northeastern.numad23fa_groupproject1.Learn;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ModuleContentModel implements Parcelable {

    private String nativePhrase;

    private String translation;

    private String identifier;


    public ModuleContentModel(String nativePhrase, String translation, String identifier) {
        this.nativePhrase = nativePhrase;
        this.translation = translation;
        this.identifier = identifier;
    }

    protected ModuleContentModel(Parcel in) {
        nativePhrase = in.readString();
        translation = in.readString();
        identifier = in.readString();
    }

    public static final Creator<ModuleContentModel> CREATOR = new Creator<ModuleContentModel>() {
        @Override
        public ModuleContentModel createFromParcel(Parcel in) {
            return new ModuleContentModel(in);
        }

        @Override
        public ModuleContentModel[] newArray(int size) {
            return new ModuleContentModel[size];
        }
    };

    public String getNativePhrase() {
        return nativePhrase;
    }

    public String getTranslation() {
        return translation;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(nativePhrase);
        dest.writeString(translation);
        dest.writeString(identifier);
    }
}
