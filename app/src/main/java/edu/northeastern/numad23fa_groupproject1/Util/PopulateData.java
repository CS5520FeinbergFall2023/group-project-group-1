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
    public void populateQuizDataStub() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String collectionName = "countries";
        String documentId = "malaysia";

        // Create a list to store quiz questions
        List<Map<String, Object>> newQuestions = new ArrayList<>();
        newQuestions.add(createQuestion("Which of the following is the Malay word for \"water\"?", Arrays.asList("Udara", "Tanah", "Api", "Air"), "3"));

        newQuestions.add(createQuestion("How do you say \"Thank you\" in Malay?", Arrays.asList("Terima kasih", "Maaf", "Selamat malam", "Sama-sama"), "0"));

        newQuestions.add(createQuestion("Which of these means \"How are you?\" in Malay?", Arrays.asList("Apa khabar?", "Selamat tinggal", "Tolong", "Nama saya"), "0"));

        newQuestions.add(createQuestion("What is the Malay word for \"food\"?", Arrays.asList("Makan", "Minum", "Roti", "Makanan"), "3"));

        newQuestions.add(createQuestion("Which number is \"lima\" in Malay?", Arrays.asList("Two", "Four", "Five", "Seven"), "2"));

        newQuestions.add(createQuestion("What is the Malay term for \"I love you\"?", Arrays.asList("Saya suka kamu", "Saya rindu kamu", "Saya cinta kamu", "Saya sayang kamu"), "2"));

        newQuestions.add(createQuestion("How do you say \"Excuse me\" or \"I'm sorry\" in Malay?", Arrays.asList("Maaf", "Terima kasih", "Tolong", "Selamat jalan"), "0"));

        newQuestions.add(createQuestion("Which of these means \"Yes\" in Malay?", Arrays.asList("Tidak", "Ya", "Boleh", "Mungkin"), "1"));

        newQuestions.add(createQuestion("What is the Malay word for \"book\"?", Arrays.asList("Buku", "Kertas", "Pensel", "Meja"), "0"));

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
