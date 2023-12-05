package edu.northeastern.numad23fa_groupproject1.Learn;

import java.util.List;

public class ModuleModel {

    private String title;

    private List<TranslationsModel> translations;

    public ModuleModel(String title, List<TranslationsModel> translations) {
        this.title = title;
        this.translations = translations;
    }

    public String getTitle() {
        return title;
    }

    public List<TranslationsModel> getTranslations() {
        return translations;
    }
}
