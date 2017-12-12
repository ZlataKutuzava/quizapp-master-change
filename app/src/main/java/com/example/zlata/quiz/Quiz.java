package com.example.zlata.quiz;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.zlata.quiz.Server.ServerGetAnswersAT;


import java.util.ArrayList;
import java.util.Random;



public class Quiz implements Parcelable {
    private Context context;
    private static final String photoDirectory = "trains/";
    private ArrayList<Question> questions;
    private int guessesCount = 0;
    private int correctGuesses = 0;
    private String[] rowAnswers;
    private String[] rowQuestions;
    private String[] rowPlayers;


    public Quiz(Context ctx, int numAnswers) {
        context = ctx;
        ServerGetAnswersAT getAnswersAT=new ServerGetAnswersAT(this);
        getAnswersAT.execute();
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){

        }
        String[] questions = this.getRowQuestions();
        String[] answers = this.getRowQuestions();
        String[] photos = context.getResources().getStringArray(R.array.trains_photos);
        this.questions = new ArrayList<>();
        for (int i = 0; i < questions.length; i++) {
            String photoPath = photoDirectory + photos[i];
            String answer = questions[i];

            ArrayList<String> questionAnswers = new ArrayList<>();
            questionAnswers.add(answer);
            Random random = new Random();
            while (questionAnswers.size() < numAnswers) {
                int randomAnswer = random.nextInt(answers.length);
                if (!questionAnswers.contains(answers[randomAnswer])) {
                    questionAnswers.add(answers[randomAnswer]);
                }
            }
            Question question = new Question(photoPath, answer, questionAnswers);
            this.questions.add(question);
        }
    }

    public void registerGuess(boolean isCorrect) {
        guessesCount++;
        if (isCorrect) {
            correctGuesses++;
        }

        if (guessesCount == questions.size()) {
            Intent intent = new Intent(context, ResultActivity.class);
            intent.putExtra(ResultActivity.GUESSES, correctGuesses);
            context.startActivity(intent);
        }
    }

    public Question getQuestion(int index) {
        return questions.get(index);
    }

    public int getQuestionsNum() {
        return questions.size();
    }

    public int getResult() {
        return correctGuesses;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(guessesCount);
        parcel.writeInt(correctGuesses);
        parcel.writeList(questions);
    }

    public static final Parcelable.Creator<Quiz> CREATOR
            = new Parcelable.Creator<Quiz>() {
        public Quiz createFromParcel(Parcel in) {
            return new Quiz(in);
        }

        public Quiz[] newArray(int size) {
            return new Quiz[size];
        }
    };

    private Quiz(Parcel in) {
        guessesCount = in.readInt();
        correctGuesses = in.readInt();
        questions = in.readArrayList(Question.class.getClassLoader());
    }

    public String toString() {
        return "Quiz: " + guessesCount + " " + correctGuesses;
    }

    public String[] getRowAnswers() {
        return rowAnswers;
    }

    public void setRowAnswers(String[] rowAnswers) {
        this.rowAnswers = rowAnswers;
    }

    public String[] getRowPlayers() {
        return rowPlayers;
    }

    public void setRowPlayers(String[] rowPlayers) {
        this.rowPlayers = rowPlayers;
    }

    public String[] getRowQuestions() {
        return rowQuestions;
    }

    public void setRowQuestions(String[] rowQuestions) {
        this.rowQuestions = rowQuestions;
    }

    class Question implements Parcelable {
        String photoPath;
        String correctAnswer;
        ArrayList<String> answers;

        public Question(String photoPath, String correctAnswer, ArrayList<String> answers) {
            this.photoPath = photoPath;
            this.correctAnswer = correctAnswer;
            this.answers = answers;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(photoPath);
            parcel.writeString(correctAnswer);
            parcel.writeList(answers);
        }

        public  final Parcelable.Creator<Question> CREATOR
                = new Parcelable.Creator<Question>() {
            public Question createFromParcel(Parcel in) {
                return new Question(in);
            }

            public Question[] newArray(int size) {
                return new Question[size];
            }
        };

        private Question(Parcel in) {
            photoPath = in.readString();
            correctAnswer = in.readString();
            answers = in.readArrayList(Question.class.getClassLoader());
        }
    }
}
