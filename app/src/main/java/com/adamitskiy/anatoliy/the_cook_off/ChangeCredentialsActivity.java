package com.adamitskiy.anatoliy.the_cook_off;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class ChangeCredentialsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_credentials);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        setTitle("Change Credentials");

        final EditText emailField = (EditText) findViewById(R.id.emailChangeTextField);
        final EditText usernameField = (EditText) findViewById(R.id.usernameChangeTextField);
        final EditText passwordField = (EditText) findViewById(R.id.passwordChangeTextField);
        final EditText passwordConfirmField = (EditText) findViewById(R.id.passwordConfirmChangeTextField);

        emailField.setText(ParseUser.getCurrentUser().getEmail());
        usernameField.setText(ParseUser.getCurrentUser().getUsername());


        Button updateButton = (Button) findViewById(R.id.updateInfoButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (passwordField.getText().toString().equals(passwordConfirmField.getText().toString()) && emailField.getText().toString().length() != 0 && usernameField.getText().toString().length() != 0
                        && passwordField.getText().toString().length() != 0 && passwordConfirmField.getText().toString().length() != 0) {
                    ParseUser parseUser = ParseUser.getCurrentUser();
                    parseUser.setEmail(emailField.getText().toString());
                    parseUser.setUsername(usernameField.getText().toString());
                    parseUser.setPassword(passwordField.getText().toString());
                    parseUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (null == e) {
                                // report about success
                                finish();
                                Toast.makeText(getApplicationContext(), "Information Updated", Toast.LENGTH_SHORT).show();
                            } else {
                                // report about error
                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                Log.i("ERROR", e.toString());
                            }
                        }
                    });
                } else if (emailField.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter email address.", Toast.LENGTH_SHORT).show();
                } else if (usernameField.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter a username.", Toast.LENGTH_SHORT).show();
                } else if (passwordField.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter a password.", Toast.LENGTH_SHORT).show();
                } else if (passwordConfirmField.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please confirm password.", Toast.LENGTH_SHORT).show();
                } else if (!passwordField.getText().equals(passwordConfirmField.getText())) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
                    passwordField.setText("");
                    passwordConfirmField.setText("");
                }

                /*
                if (intent.getStringExtra("Type").equals("email")) {

                    if (!newField.getText().toString().equals(confirmField.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Fields do not match.", Toast.LENGTH_SHORT).show();
                    } else {
                        ParseUser parseUser = ParseUser.getCurrentUser();
                        parseUser.setEmail(confirmField.getText().toString());
                        parseUser.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (null == e) {
                                    // report about success
                                    finish();
                                    Toast.makeText(getApplicationContext(), "Email Updated", Toast.LENGTH_SHORT).show();
                                } else {
                                    // report about error
                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                                    Log.i("ERROR", e.toString());
                                }
                            }
                        });

                    }
                } else if (intent.getStringExtra("Type").equals("username")) {

                    if (!newField.getText().toString().equals(confirmField.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Fields do not match.", Toast.LENGTH_SHORT).show();
                    } else {
                        ParseUser parseUser = ParseUser.getCurrentUser();
                        parseUser.setUsername(confirmField.getText().toString());
                        parseUser.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (null == e) {
                                    // report about success
                                    finish();
                                    Toast.makeText(getApplicationContext(), "Username Updated", Toast.LENGTH_SHORT).show();
                                } else {
                                    // report about error
                                }
                            }
                        });

                    }
                } else if (intent.getStringExtra("Type").equals("password")) {

                    if (!newField.getText().toString().equals(confirmField.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Fields do not match.", Toast.LENGTH_SHORT).show();
                    } else {
                        ParseUser parseUser = ParseUser.getCurrentUser();
                        parseUser.setPassword(confirmField.getText().toString());
                        parseUser.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (null == e) {
                                    // report about success
                                    finish();
                                    Toast.makeText(getApplicationContext(), "Password Updated", Toast.LENGTH_SHORT).show();
                                } else {
                                    // report about error
                                }
                            }
                        });

                    }
                }
                */

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_change_credentials, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
