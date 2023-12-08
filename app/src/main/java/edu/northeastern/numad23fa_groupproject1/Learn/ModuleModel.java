package edu.northeastern.numad23fa_groupproject1.Learn;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class ModuleModel implements Parcelable {

    private String title;

    private List<ModuleContentModel> translations;

    public ModuleModel(String title, List<ModuleContentModel> translations) {
        this.title = title;
        this.translations = translations;
    }

    protected ModuleModel(Parcel in) {
        title = in.readString();
        translations = in.createTypedArrayList(ModuleContentModel.CREATOR);
    }

    public static final Creator<ModuleModel> CREATOR = new Creator<ModuleModel>() {
        @Override
        public ModuleModel createFromParcel(Parcel in) {
            return new ModuleModel(in);
        }

        @Override
        public ModuleModel[] newArray(int size) {
            return new ModuleModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public List<ModuleContentModel> getTranslations() {
        return translations;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeTypedList(translations);
//        translations.forEach(translation -> dest.writeParcelable(translation, 0));
    }
}
