package com.kuri.alphariddlesv2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{

    Bitmap[] bitArray = new Bitmap[26];
    int random;
    float randomPickFloat;
    int randomPick;
    GestureDetector gestures = new GestureDetector(MainActivity.this);
    int height;
    int width;
    TextView testText;
    TextView dbText;
    TextView timeText;
    TextView scoreText;
    //TextView countIt;
    int seconds = 5;
    int secs;
    int minutes;
    int hours;
    boolean running = true;
    StringBuilder stringGuess;
    String show;
    Riddles riddles_;
    int score = 0;
    int countClick = 0;
    Boolean go = true;
    int stopCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout frame = (FrameLayout)findViewById(R.id.graphics_holder);
        Alphabets alphabets = new Alphabets(this);
        testText = (TextView)findViewById(R.id.testText);
        dbText = (TextView)findViewById(R.id.dbText);
        timeText = (TextView)findViewById(R.id.timeText);
        scoreText = (TextView)findViewById(R.id.score);
        //countIt = (TextView)findViewById(R.id.countIt);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
        stringGuess = new StringBuilder();
        riddles_ = new Riddles();
        frame.addView(alphabets);
        riddleFunction();
        runRandom();
        timeCalculator();

    }

    public class Alphabets extends View {

        public Alphabets(Context context) {
            super(context);
            bitArray[0] = BitmapFactory.decodeResource(getResources(), R.drawable.a_new); //a
            bitArray[1] = BitmapFactory.decodeResource(getResources(), R.drawable.b_new); //b
            bitArray[2] = BitmapFactory.decodeResource(getResources(), R.drawable.c_new); //c
            bitArray[3] = BitmapFactory.decodeResource(getResources(), R.drawable.d_new); //d
            bitArray[4] = BitmapFactory.decodeResource(getResources(), R.drawable.e_new); //e
            bitArray[5] = BitmapFactory.decodeResource(getResources(), R.drawable.f_new); //f
            bitArray[6] = BitmapFactory.decodeResource(getResources(), R.drawable.g_new); //g
            bitArray[7] = BitmapFactory.decodeResource(getResources(), R.drawable.h_new); //h
            bitArray[8] = BitmapFactory.decodeResource(getResources(), R.drawable.i_new); //i
            bitArray[9] = BitmapFactory.decodeResource(getResources(), R.drawable.j_new); //j
            bitArray[10] = BitmapFactory.decodeResource(getResources(), R.drawable.k_new); //k
            bitArray[11] = BitmapFactory.decodeResource(getResources(), R.drawable.l_new); //l
            bitArray[12] = BitmapFactory.decodeResource(getResources(), R.drawable.m_new); //m
            bitArray[13] = BitmapFactory.decodeResource(getResources(), R.drawable.n_new); //n
            bitArray[14] = BitmapFactory.decodeResource(getResources(), R.drawable.o_new); //o
            bitArray[15] = BitmapFactory.decodeResource(getResources(), R.drawable.p_new); //p
            bitArray[16] = BitmapFactory.decodeResource(getResources(), R.drawable.q_new); //q
            bitArray[17] = BitmapFactory.decodeResource(getResources(), R.drawable.r_new); //r
            bitArray[18] = BitmapFactory.decodeResource(getResources(), R.drawable.s_new); //s
            bitArray[19] = BitmapFactory.decodeResource(getResources(), R.drawable.t_new); //t
            bitArray[20] = BitmapFactory.decodeResource(getResources(), R.drawable.u_new); //u
            bitArray[21] = BitmapFactory.decodeResource(getResources(), R.drawable.v_new); //v
            bitArray[22] = BitmapFactory.decodeResource(getResources(), R.drawable.w_new); //w
            bitArray[23] = BitmapFactory.decodeResource(getResources(), R.drawable.x_new); //x
            bitArray[24] = BitmapFactory.decodeResource(getResources(), R.drawable.y_new); //y
            bitArray[25] =  BitmapFactory.decodeResource(getResources(), R.drawable.z_new); //z

        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawBitmap(bitArray[random],430,130,null);
            invalidate();
        }
    }

    public void runRandom(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            float randomFloat;
            @Override
            public void run() {
                randomFloat = (float) (Math.random());
                random = (int) (randomFloat * 26);
                handler.postDelayed(this, 900);
            }
        });
    }

    public void timeCalculator(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                    secs = seconds%60;
                    minutes = (seconds%3600)/60;
                    hours = seconds/3600;
                    String time = String.format("%d:%02d:%02d",hours,minutes,secs);
                    timeText.setText(time);
                    if(running){
                        seconds--;
                    }

                    if(seconds == 0) {
                        countClick = 0;
                        //countIt.setText(Integer.toString(countClick));
                        riddleFunction();
                        if(stringGuess.length() != 0){
                            int sizeString = stringGuess.length();
                            int i = 0;
                            while(sizeString != 0) {
                                stringGuess.deleteCharAt(i);
                                sizeString = stringGuess.length();
                            }
                            show = stringGuess.toString();
                        }
                        testText.setText(show);
                        seconds = 5;
                    }


                    handler.postDelayed(this,1000);
                }
        });
    }

    public void riddleFunction(){
        randomPickFloat = (float) (Math.random());
        randomPick = (int) (randomPickFloat * 5);
        String selectRiddle = riddles_.riddles.get(randomPick);
        dbText.setText(selectRiddle);
        //riddles_.visited.put(randomPick,true); //key visited
    }

    public void checkTouch(MotionEvent event, int random){
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){
            switch (random) {
                case 0:
                    stringGuess.append("A");
                    break;
                case 1:
                    stringGuess.append("B");
                    break;
                case 2:
                    stringGuess.append("C");
                    break;
                case 3:
                    stringGuess.append("D");
                    break;
                case 4:
                    stringGuess.append("E");
                    break;
                case 5:
                    stringGuess.append("F");
                    break;
                case 6:
                    stringGuess.append("G");
                    break;
                case 7:
                    stringGuess.append("H");
                    break;
                case 8:
                    stringGuess.append("I");
                    break;
                case 9:
                    stringGuess.append("J");
                    break;
                case 10:
                    stringGuess.append("K");
                    break;
                case 11:
                    stringGuess.append("L");
                    break;
                case 12:
                    stringGuess.append("M");
                    break;
                case 13:
                    stringGuess.append("N");
                    break;
                case 14:
                    stringGuess.append("O");
                    break;
                case 15:
                    stringGuess.append("P");
                    break;
                case 16:
                    stringGuess.append("Q");
                    break;
                case 17:
                    stringGuess.append("R");
                    break;
                case 18:
                    stringGuess.append("S");
                    break;
                case 19:
                    stringGuess.append("T");
                    break;
                case 20:
                    stringGuess.append("U");
                    break;
                case 21:
                    stringGuess.append("V");
                    break;
                case 22:
                    stringGuess.append("W");
                    break;
                case 23:
                    stringGuess.append("X");
                    break;
                case 24:
                    stringGuess.append("Y");
                    break;
                case 25:
                    stringGuess.append("Z");
                    break;
            }
            show = stringGuess.toString();
            testText.setText(show);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.isButtonPressed(R.id.enter) || event.isButtonPressed(R.id.delete)){
            return false;
        }else {
            return gestures.onTouchEvent(event);
        }
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        checkTouch(e,random);
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    public void enterIt(View view){
        String riddleAnswer = riddles_.answers.get(randomPick);
        countClick++;
        //countIt.setText(Integer.toString(countClick));
        if(countClick == 1){
            if(show.equals(riddleAnswer)){
                score += 2;
                scoreText.setText("SCORE: " + Integer.toString(score));
            }
        }if(!(show.equals(riddleAnswer))){
            score -= 2;
            scoreText.setText("SCORE: " + Integer.toString(score));
        }

        /*
        == tests for reference equality (whether they are the same object).

        .equals() tests for value equality (whether they are logically "equal").
        */
    }

    public void deleteIt(View view){
        int size = stringGuess.length();
        if(size !=0) {
            stringGuess.deleteCharAt(size - 1);
            show = stringGuess.toString();
            testText.setText(show);
        }
    }
}