package com.algonquinlive.lu000094.hilogame;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;
import java.util.regex.Pattern;

/**
 * This is an android app for HiLow game also referred as Guess number game.
 *
 * @author lu000094@algonquinlive.com
 */
public class MainActivity extends AppCompatActivity {

    private static final String REGEX_NUMBER_RANGE;
    private static int gameNumber;
    private static int userAttemptCount = 0;
    private static String LOG_TAG;
    private static final String ABOUT_DIALOG_TAG;

    static {
        REGEX_NUMBER_RANGE = "^([1-9]|[1-9][0-9]|[1-9][0-9][0-9]|1000)$";
        LOG_TAG = "HiLow";
        ABOUT_DIALOG_TAG = "About Dialog";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Generate a game number.
        Random r = new Random();
        gameNumber = getRandomNumber();
        //Toast.makeText(getApplicationContext(), gameNumber , Toast.LENGTH_LONG).show();

        Button guessButton = (Button) findViewById(R.id.guessBtn);
        Button resetButton = (Button) findViewById(R.id.resetBtn);

        guessButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Toast theNumber on long click.
                Toast.makeText(getApplicationContext(), "theNumber = " + gameNumber, Toast.LENGTH_LONG).show();
                return true;
            }
        });

        guessButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText userGuessText = (EditText) findViewById(R.id.editText);

                int userGuessNumber;
                try {
                    userGuessNumber = Integer.parseInt(userGuessText.getText().toString());
                } catch (NumberFormatException e) {
                    userGuessText.setError("Enter a number between 1 and 1000.");
                    userGuessText.requestFocus();
                    return;
                }


                //Validate the user guess is number between 1 to 1000.
                if (!Pattern.matches(REGEX_NUMBER_RANGE, String.valueOf(userGuessNumber))) {
                    userGuessText.setError("Enter a number between 1 and 1000.");
                    userGuessText.requestFocus();
                }

                // Increase the attempt count by 1
                userAttemptCount = userAttemptCount + 1;

                //Validate not more than 10 attempts.
                if (userAttemptCount > 10) {
                    Toast.makeText(getApplicationContext(), "No more guesses, Please reset!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (userGuessNumber > gameNumber) {
                    Toast.makeText(getApplicationContext(), "Your guess was too high!", Toast.LENGTH_LONG).show();
                } else if (userGuessNumber < gameNumber) {
                    Toast.makeText(getApplicationContext(), "Your guess was too low!", Toast.LENGTH_LONG).show();
                } else {
                    if (userAttemptCount < 6) {
                        Toast.makeText(getApplicationContext(), "Superior win!", Toast.LENGTH_LONG).show();
                    }

                    if (userAttemptCount > 5 && userAttemptCount < 11) {
                        Toast.makeText(getApplicationContext(), "Excellent win!", Toast.LENGTH_LONG).show();
                    }


                }

            }

        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userGuessText = (EditText) findViewById(R.id.editText);
                String userGuessNumber = userGuessText.getText().toString();
                //reset user input.
                userGuessText.setText("");
                userGuessText.requestFocus();

                ///generate random number.
                gameNumber = getRandomNumber();

                //Reset user count
                userAttemptCount = 0;

            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // TODO: add this method to handle when the user selects a menu item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private int getRandomNumber() {
        Random r = new Random();
        int number = r.nextInt(1000) + 1;
        Log.i(LOG_TAG, "Random number generated = " + number);
        return number;
    }
}



