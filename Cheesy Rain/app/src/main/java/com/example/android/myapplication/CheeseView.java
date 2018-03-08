package com.example.android.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Hazem El Ahwany on 30-May-16.
 */
public class CheeseView extends View {

    Bitmap cheese;
    Bitmap cheeseScaled;
    int[] cheeseXPositions;
    int[] cheeseYPositions;
    int[] cheeseSpeeds;
    final static int MAXSPEEDCHEESE = 10;

    Bitmap shield;
    Bitmap shieldScaled;

    final static int MINPOS = 10;
    final static int MAXPOS = 900;
    final static int MINSPPED = 1;

    //Man Attributes
    Bitmap man;
    Bitmap manScaled;
    int[] menPositions;
    int[] menSpeeds;
    boolean[] menStates;
    boolean[] manSaleem;
    final static int MAXSPEEDMEN = 20;

    Random r = new Random();

    float x;


    //Constructor
    public CheeseView(Context context) {
        super(context);

        //Converting images to Bitmap matrices
        cheese = BitmapFactory.decodeResource(getResources(), R.drawable.cheese);
        man = BitmapFactory.decodeResource(getResources(), R.drawable.man);
        shield = BitmapFactory.decodeResource(getResources(), R.drawable.shield);

        //Scaling Bitmaps to smaller sizes
        cheeseScaled = Bitmap.createScaledBitmap(cheese, 60, 50, false);
        manScaled = Bitmap.createScaledBitmap(man, 80, 150, false);
        shieldScaled = Bitmap.createScaledBitmap(shield, 300, 300, false);

        //Generate random number of cheese
        generateCheese();

        //Generate random number of Men
        generateMen();

        x = 0;

    }

    private void generateCheese() {
        int numberOfCheese = 5;
        cheeseXPositions = new int[numberOfCheese];
        for(int i =0; i < cheeseXPositions.length; i++) {
            cheeseXPositions[i] = r.nextInt(MAXPOS - MINPOS) + MINPOS;
        }

        cheeseYPositions = new int[numberOfCheese];
        for(int i =0; i < cheeseYPositions.length; i++) {
            cheeseYPositions[i] = 0;
        }

        cheeseSpeeds = new int[numberOfCheese];
        for(int i =0; i < cheeseSpeeds.length; i++) {
            cheeseSpeeds[i] = r.nextInt(MAXSPEEDCHEESE - MINSPPED) + MINSPPED;
        }

    }

    private void generateMen() {
        int numberOfMen = r.nextInt(10 - 5) + 5;
        menPositions = new int[numberOfMen];
        manSaleem = new boolean[numberOfMen];
        for(int i =0; i < menPositions.length; i++) {
            menPositions[i] = r.nextInt(MAXPOS - MINPOS) + MINPOS;
        }

        menSpeeds = new int[numberOfMen];
        for(int i =0; i < menSpeeds.length; i++) {
            menSpeeds[i] = r.nextInt(MAXSPEEDMEN - MINSPPED) + MINSPPED;
        }

        menStates = new boolean[numberOfMen];
        for(int i =0; i < menStates.length; i++) {
            menStates[i] = true;
        }

    }

int winCounter = 50;
    int loseCounter = 3;
boolean clash = false;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(shieldScaled, x, canvas.getHeight()-500, null);

        for(int i = 0; i < cheeseXPositions.length; i++) {
            if(cheeseYPositions[i] >= canvas.getHeight() - 500 && cheeseYPositions[i] < canvas.getHeight() - 200) {
                if(cheeseXPositions[i] >= x && cheeseXPositions[i] < x + 500) {
                    cheeseYPositions[i] = canvas.getHeight()+100;
                    winCounter--;
//                    if(winCounter <= 0 && loseCounter > 0)
//                        Toast.makeText(getContext(), "You Win", Toast.LENGTH_LONG).show();
                }
            }
            for(int j = 0; j < menPositions.length; j++) {
                if (cheeseYPositions[i] >= canvas.getHeight() - 150 && cheeseYPositions[i] < canvas.getHeight()) {
                    if (cheeseXPositions[i] >= menPositions[j] && cheeseXPositions[i] < menPositions[j] + 80) {
                        clash = true;
                        manSaleem[j] = true;
                        loseCounter--;

                    }
                }
            }

            if (clash) {
                clash = false;
            }
            else {

                canvas.drawBitmap(cheeseScaled, cheeseXPositions[i], cheeseYPositions[i], null);
            }
            clash = false;
        }

        /*for(int i = 0; i <cheeseXPositions.length; i++) {
            canvas.drawBitmap(cheeseScaled, cheeseXPositions[i], cheeseYPositions[i], null);
        }
*/
        // Cheese Movement
        for(int i = 0; i <cheeseYPositions.length; i++) {
            if (cheeseYPositions[i] < canvas.getHeight())
                cheeseYPositions[i] += cheeseSpeeds[i];
            else {
                cheeseXPositions[i] = r.nextInt(MAXPOS - MINPOS) + MINPOS;
                cheeseYPositions[i] = 0;
                cheeseSpeeds[i] = r.nextInt(MAXSPEEDCHEESE - MINSPPED) + MINSPPED;
            }
        }

        /*for(int i = 0; i < menPositions.length; i++) {
            canvas.drawBitmap(manScaled, menPositions[i], canvas.getHeight() - 150, null);
        }*/
        for(int k = 0; k < menPositions.length; k++) {
            if(!manSaleem[k])
                canvas.drawBitmap(manScaled, menPositions[k], canvas.getHeight() - 150, null);
//            if(loseCounter <= 0 && winCounter > 0)
//                Toast.makeText(getContext(), "You Lose", Toast.LENGTH_SHORT).show();
        }
        //Men movement
        for(int i = 0; i < menPositions.length; i++) {
            if(menPositions[i] < canvas.getWidth() - 80 && menStates[i])
                menPositions[i] += menSpeeds[i];
            else if(menPositions[i] > 0 && !menStates[i])
                menPositions[i] -= menSpeeds[i];
            else if (menPositions[i] >= canvas.getWidth() - 80 && menStates[i]) {
                menStates[i] = false;
                menPositions[i] -= menSpeeds[i];
            }
            else if (menPositions[i] <= 0 && !menStates[i]) {
                menStates[i] = true;
                menPositions[i] += menSpeeds[i];
            }
        }

        invalidate();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN: {
            }
            break;

            case MotionEvent.ACTION_MOVE:
            {
                x=(int)event.getX();
                invalidate();
            }

            break;
            case MotionEvent.ACTION_UP:

                x=(int)event.getX();
                Log.i("X equals", "onTouchEvent: "+x);
                invalidate();
                break;
        }
        return true;
    }
}
