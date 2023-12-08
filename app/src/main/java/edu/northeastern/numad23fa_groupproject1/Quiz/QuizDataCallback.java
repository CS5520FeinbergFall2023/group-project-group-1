package edu.northeastern.numad23fa_groupproject1.Quiz;

import java.util.List;

import edu.northeastern.numad23fa_groupproject1.Quiz.Question;

public interface QuizDataCallback {
    void onQuizDataLoaded(List<Question> questions);
}
