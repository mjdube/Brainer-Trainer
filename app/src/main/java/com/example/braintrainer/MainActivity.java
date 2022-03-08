package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.*;

class BrainQuestions {

    int numberQuestion;

    String questions [][] = {
            {"1 + 2 =", "1", "4", "3", "9", "3"},
            {"3 + 3 =", "5", "9", "6", "12", "6"},
            {"8 / 2 =", "4", "2", "9", "3", "4"},
            {"4 x 0 =", "4", "2", "0", "1", "0"},
            {"10 x 10 =", "100", "1000", "10", "1000", "100"},
            {"(3 + 2) - 6 =", "2", "1", "0", "7", "1"},
            {"9 / 3 =", "2", "6", "3", "12", "3"},
            {"(100 - 1) x 1 =", "99", "100", "101", "98", "99"},
            {"(1000 * 2) - 1000 =", "1000", "2000", "3000", "100", "1000"},
            {"40 * 4 =", "160", "800", "44", "88", "160"},
            {"(200 - 100) * 2 =", "200", "202", "199", "400", "200"},
            {"(100 - 1) - (100 + 1) =", "-2", "1", "2", "0", "-2"},
            {"600 x 10 =", "200", "6000", "600", "6001", "6000"},
            {"(8 / 4) * 10 - 2 =", "18", "20", "80", "9", "18"},
            {"6 + 6 * 12 =", "24", "62", "78", "88", "78"},
            {"(1 x 1) + 20 - 10 =", "11", "12", "22", "21", "11"},
            {"(93 + 7) + 100 =", "150", "200", "111", "100", "200"},
            {"(0 x 1) + (0 + 1) + (0 - 1) =", "-1", "0", "1", "2", "0"},
            {"1 x 4 =", "4", "5", "3", "14", "4"},
            {"0 x 1 x 2 x 3 =", "2", "0", "1", "4", "0"}
    };

    // ArrayList question Handler
    ArrayList<String[]> questionsAndAnswers = new ArrayList<String[]>();

    // Initialize All the Questions
    public void initializeQuestions(){
        for (int j = 0; j < questions.length; j++){
            questionsAndAnswers.add(questions[j]);
        }
    }

    // Choosing a random question from the index
    public int randomlyChooseQuestion(ArrayList questions){
        Random random = new Random();
        numberQuestion = random.nextInt(questions.size());
        return numberQuestion;
    }

    //Gets the hidden answer
    public String getHiddenAnswer(int indexNumber) {
        return questions[indexNumber][5];
    }

    public int getNumberQuestion(){
        return numberQuestion;
    }
}


public class MainActivity extends AppCompatActivity {

    TextView goTextView;
    CountDownTimer countDownTimer;
    TextView countDownTime;
    TextView scoreTextView;
    TextView correctWrongTextView;
    TextView questionsTextView;
    TextView answersTextView;
    Button startAgainBtn;
    int score = 0;
    int level = 0;

    int textViewAnswers [] = {R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4};
    int textViewsAppearance [] = {};

    BrainQuestions game = new BrainQuestions();

    // Changing Colours in the block
    public void changeColours(){
        Random randomColour = new Random();
        int colours;
        for (int i = 0; i < textViewAnswers.length; i++){
            colours = Color.argb(255, randomColour.nextInt(256), randomColour.nextInt(256), randomColour.nextInt(256));
            TextView answersTextView = (TextView) findViewById(textViewAnswers[i]);
            answersTextView.setBackgroundColor(colours);
        }
    }

    // Start the timer
    public void startGameTimer(){

        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long l) {
                int secs = (int) (l / 1000) % 60;
                countDownTime.setText(String.valueOf(secs) + "'s");
            }

            @Override
            public void onFinish() {
                questionsTextView.setText("Game Over!");
                startAgainBtn = (Button) findViewById(R.id.startButton);
                startAgainBtn.setVisibility(View.VISIBLE);
                correctWrongTextView.setVisibility(View.INVISIBLE);

                for (int i = 0; i < textViewAnswers.length; i++){
                    answersTextView = (TextView) findViewById(textViewAnswers[i]);
                    answersTextView.setEnabled(false);
                }
            }
        }.start();
    }

    // GO TextView
    public void goTextViewBtn(View view){

        game.initializeQuestions();

        int numberQuestion = game.randomlyChooseQuestion(game.questionsAndAnswers);

        String question[] = game.questionsAndAnswers.get(numberQuestion);

        goTextView = (TextView) findViewById(R.id.goTextView);
        goTextView.setVisibility(View.INVISIBLE);

        questionsTextView = (TextView) findViewById(R.id.questionsTextView);
        questionsTextView.setVisibility(View.VISIBLE);
        questionsTextView.setText(question[0]);

        countDownTime = (TextView) findViewById(R.id.countDownTextView);
        countDownTime.setVisibility(View.VISIBLE);

        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        scoreTextView.setVisibility(View.VISIBLE);

        for (int i = 0; i < textViewAnswers.length ; i++){
            TextView answersTextView = (TextView) findViewById(textViewAnswers[i]);
            answersTextView.setVisibility(View.VISIBLE);
        }

        getAnswersTextView(question);
        startGameTimer();
    }

    public void startAgainBtn(View view){
        level = 0;
        score = 0;
        game.initializeQuestions();

        int numberQuestion = game.randomlyChooseQuestion(game.questionsAndAnswers);

        String question[] = game.questionsAndAnswers.get(numberQuestion);

        for (int i = 0; i < textViewAnswers.length; i++){
            answersTextView = (TextView) findViewById(textViewAnswers[i]);
            answersTextView.setEnabled(true);
        }
        scoreTextView.setText("0/0");
        questionsTextView.setText(question[0]);
        getAnswersTextView(question);
        startGameTimer();
    }

    // Get Next random questions and answers on to the grid View
    public void goToNewQuestion(View view){
        int numberQuestion = game.getNumberQuestion();
        int nextNumberQuestion;
        String nextQuestion[];
        String userAnswer = view.getTag().toString();
        correctWrongTextView = (TextView) findViewById(R.id.correctWrongTextView);
        correctWrongTextView.setVisibility(View.VISIBLE);

        if (nextQuestion(numberQuestion, userAnswer)){
            correctWrongTextView.setText("CORRECT :)");
            nextNumberQuestion = game.randomlyChooseQuestion(game.questionsAndAnswers);
            score++;
        }else {
            correctWrongTextView.setText("WRONG :(");
            nextNumberQuestion = game.randomlyChooseQuestion(game.questionsAndAnswers);
        }

        nextQuestion = game.questionsAndAnswers.get(nextNumberQuestion);
        getAnswersTextView(nextQuestion);
        questionsTextView.setText(nextQuestion[0]);

        level++;
        scoreTextView.setText(String.valueOf(score) + "/" + level);
        changeColours();
    }

    // Sets answers randomly on the grid
    public void getAnswersTextView(String question[]){
        for (int i = 0, l = 1; i < textViewAnswers.length ; i++, l++){
            answersTextView = (TextView) findViewById(textViewAnswers[i]);
            answersTextView.setText(question[l]);
            answersTextView.setTag(question[l]);
        }
    }

    // Checking if right or wrong answers, and then deleting index question
    public boolean nextQuestion(int numberQuestion, String answer){
        if (answer.equals(game.getHiddenAnswer(numberQuestion))){
            game.questionsAndAnswers.remove(numberQuestion);
            return true;
        } else {
            game.questionsAndAnswers.remove(numberQuestion);
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}