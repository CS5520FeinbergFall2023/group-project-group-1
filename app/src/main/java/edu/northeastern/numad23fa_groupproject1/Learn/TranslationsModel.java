package edu.northeastern.numad23fa_groupproject1.Learn;

public class TranslationsModel {

    private String nativePhrase;

    private String translation;


    public TranslationsModel(String nativePhrase, String translation) {
        this.nativePhrase = nativePhrase;
        this.translation = translation;
    }

    public String getNativePhrase() {
        return nativePhrase;
    }

    public String getTranslation() {
        return translation;
    }
}
