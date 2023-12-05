package edu.northeastern.numad23fa_groupproject1.Util;

import android.util.Log;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class to populate data into Firebase Firestore for testing purposes.
 */
public class PopulateData {

    /**
     * Populates historical data into Firestore.
     */
    private void populateHistoryDataStub() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String collectionName = "countries";
        String documentId = "india"; // Check if you have to change this

        // Create a list to store historical events
        List<Map<String, Object>> historyEvents = new ArrayList<>();
        historyEvents.add(createHistoryEvent("3300–1300 BCE", "Indus Valley Civilization", "Advanced urban planning, trade, and a system of writing.", 1));
        // Add more historical events...

        // Update Firestore with historical events
        db.collection(collectionName)
                .document(documentId)
                .update("historyData", historyEvents)
                .addOnSuccessListener(aVoid -> {
                    // Handle success if needed
                })
                .addOnFailureListener(e -> {
                    // Handle errors
                    Log.e("Firestore", "Error adding historical events", e);
                });
    }

    /**
     * Creates a map representing a historical event.
     */
    private Map<String, Object> createHistoryEvent(String date, String desc, String event, int imageId) {
        Map<String, Object> eventMap = new HashMap<>();
        eventMap.put("date", date);
        eventMap.put("description", desc);
        eventMap.put("details", event);
        eventMap.put("imageId", imageId);
        return eventMap;
    }

    /**
     * Populates quiz data into Firestore.
     */
    private void populateQuizDataStub() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String collectionName = "countries";
        String documentId = "india";

        // Create a list to store quiz questions
        List<Map<String, Object>> newQuestions = new ArrayList<>();
        newQuestions.add(createQuestion("What script is used to write Hindi?", Arrays.asList("Cyrillic", "Arabic", "Devanagari", "Latin"), "Devanagari"));
        // Add more quiz questions...

        // Update Firestore with quiz questions
        db.collection(collectionName)
                .document(documentId)
                .update("quizData", FieldValue.arrayUnion(newQuestions.toArray()))
                .addOnSuccessListener(aVoid -> {
                    // Handle success if needed
                })
                .addOnFailureListener(e -> {
                    // Handle errors
                    Log.e("Firestore", "Error adding quiz questions", e);
                });
    }

    /**
     * Creates a map representing a quiz question.
     */
    private Map<String, Object> createQuestion(String question, List<String> options, String correctAnswer) {
        Map<String, Object> questionMap = new HashMap<>();
        questionMap.put("question", question);
        questionMap.put("options", options);
        questionMap.put("correct_answer", correctAnswer);
        return questionMap;
    }
}
