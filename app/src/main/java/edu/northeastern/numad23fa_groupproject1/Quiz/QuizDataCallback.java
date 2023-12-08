package edu.northeastern.numad23fa_groupproject1.Quiz;

import java.util.List;

import edu.northeastern.numad23fa_groupproject1.Quiz.QuestionModel;

public interface QuizDataCallback {
    void onQuizDataLoaded(List<QuestionModel> questions);
}
