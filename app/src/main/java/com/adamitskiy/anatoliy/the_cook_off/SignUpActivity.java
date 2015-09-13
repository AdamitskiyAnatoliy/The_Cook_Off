package com.adamitskiy.anatoliy.the_cook_off;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.nispok.snackbar.listeners.EventListener;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.signpost.http.HttpResponse;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();
    EditText username, password, passConfirm, email;
    ImageButton avatarButton;
    byte[] byteArray;
    ParseFile file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        avatarButton = (ImageButton) findViewById(R.id.add_avatar_sign_up);
        email = (EditText) findViewById(R.id.emailTextFieldForm);
        username = (EditText) findViewById(R.id.usernameTextFieldForm);
        password = (EditText) findViewById(R.id.passwordTextFieldForm);
        passConfirm = (EditText) findViewById(R.id.verifyPasswordTextFieldForm);

        avatarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        findViewById(R.id.cancelFormButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.signUpFormButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pass1 = password.getText().toString();
                String pass2 = passConfirm.getText().toString();

                if (pass1.equals(pass2)) {

                    showSnackBar("Checking Username Availability", Snackbar.SnackbarDuration.LENGTH_INDEFINITE);
                    ParseUser user = new ParseUser();
                    user.setEmail(email.getText().toString());
                    user.setUsername(username.getText().toString());
                    user.setPassword(password.getText().toString());
                    user.put("Points", "0");
                    user.put("Type", "Normal");
                    if (file != null) {
                        user.put("image", file);
                        user.signUpInBackground(new SignUpCallback() {
                            public void done(ParseException e) {
                                if (e == null) {

                                    TheCookOff.updateParseInstallation(ParseUser.getCurrentUser());

                                    SnackbarManager.show(
                                            Snackbar.with(SignUpActivity.this)
                                                    .text("Sign Up Successful")
                                                    .color(Color.DKGRAY)
                                                    .textColor(Color.rgb(255, 153, 51))
                                                    .duration(Snackbar.SnackbarDuration.LENGTH_SHORT)
                                                    .eventListener(new EventListener() {
                                                        @Override
                                                        public void onShow(Snackbar snackbar) {
                                                            Log.i(TAG, String.format(
                                                                    "Snackbar will show. Width: %d Height: %d Offset: %d",
                                                                    snackbar.getWidth(), snackbar.getHeight(),
                                                                    snackbar.getOffset()));
                                                        }

                                                        @Override
                                                        public void onShowByReplace(Snackbar snackbar) {
                                                            Log.i(TAG, String.format(
                                                                    "Snackbar will show by replace. Width: %d Height: %d Offset: %d",
                                                                    snackbar.getWidth(), snackbar.getHeight(),
                                                                    snackbar.getOffset()));
                                                        }

                                                        @Override
                                                        public void onShown(Snackbar snackbar) {
                                                            Log.i(TAG, String.format(
                                                                    "Snackbar shown. Width: %d Height: %d Offset: %d",
                                                                    snackbar.getWidth(), snackbar.getHeight(),
                                                                    snackbar.getOffset()));
                                                        }

                                                        @Override
                                                        public void onDismiss(Snackbar snackbar) {
                                                            Log.i(TAG, String.format(
                                                                    "Snackbar will dismiss. Width: %d Height: %d Offset: %d",
                                                                    snackbar.getWidth(), snackbar.getHeight(),
                                                                    snackbar.getOffset()));
                                                        }

                                                        @Override
                                                        public void onDismissByReplace(Snackbar snackbar) {
                                                            Log.i(TAG, String.format(
                                                                    "Snackbar will dismiss by replace. Width: %d Height: %d Offset: %d",
                                                                    snackbar.getWidth(), snackbar.getHeight(),
                                                                    snackbar.getOffset()));
                                                            SnackbarManager.dismiss();
                                                        }

                                                        @Override
                                                        public void onDismissed(Snackbar snackbar) {

                                                            Intent intent = new Intent();
                                                            intent.putExtra("username", username.getText().toString());
                                                            intent.putExtra("password", username.getText().toString());
                                                            setResult(RESULT_OK, intent);
                                                            finish();
                                                        }
                                                    }));

                                    SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putBoolean("Achievement 1" + ParseUser.getCurrentUser().getUsername(), false);
                                    editor.putBoolean("Achievement 2" + ParseUser.getCurrentUser().getUsername(), false);
                                    editor.putBoolean("Achievement 3" + ParseUser.getCurrentUser().getUsername(), false);
                                    editor.putBoolean("Achievement 4" + ParseUser.getCurrentUser().getUsername(), false);
                                    editor.putBoolean("Achievement 5" + ParseUser.getCurrentUser().getUsername(), false);
                                    editor.commit();

                                } else {
                                    if (e.toString().equals("com.parse.ParseRequest$ParseRequestException: invalid email address")) {
                                        showSnackBar("Invalid Email Address", Snackbar.SnackbarDuration.LENGTH_LONG);
                                    } else if (e.toString().equals("com.parse.ParseRequest$ParseRequestException: username anato1iy already taken")) {
                                        showSnackBar("Username Not Available", Snackbar.SnackbarDuration.LENGTH_LONG);
                                    } else if (e.toString().equals("com.parse.ParseRequest$ParseRequestException: the email address " + email.getText().toString() + " has already been taken")) {
                                        showSnackBar("Email Already Registered", Snackbar.SnackbarDuration.LENGTH_LONG);
                                    }
                                }
                            }
                        });
                    } else {
                     showSnackBar("Please Add Avatar Image", Snackbar.SnackbarDuration.LENGTH_LONG);
                    }
                } else {
                    showSnackBar("Passwords Do Not Match", Snackbar.SnackbarDuration.LENGTH_SHORT);
                    password.setText("");
                    passConfirm.setText("");
                }
            }
        });

        findViewById(R.id.signUpTwitterFormButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseTwitterUtils.logIn(SignUpActivity.this, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException err) {
                        if (user == null) {
                            Log.d("MyApp", "Uh oh. The user cancelled the Twitter login.");
                        } else if (user.isNew()) {
                            Log.d("MyApp", "User signed up and logged in through Twitter!");

                            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putBoolean("Achievement 1" + ParseUser.getCurrentUser().getUsername(), false);
                            editor.putBoolean("Achievement 2" + ParseUser.getCurrentUser().getUsername(), false);
                            editor.putBoolean("Achievement 3" + ParseUser.getCurrentUser().getUsername(), false);
                            editor.putBoolean("Achievement 4" + ParseUser.getCurrentUser().getUsername(), false);
                            editor.putBoolean("Achievement 5" + ParseUser.getCurrentUser().getUsername(), false);
                            editor.commit();

                            TheCookOff.updateParseInstallation(ParseUser.getCurrentUser());

                            ParseUser parseUser = ParseUser.getCurrentUser();
                            parseUser.setUsername(ParseTwitterUtils.getTwitter().getScreenName());
                            parseUser.put("Points", "0");
                            parseUser.put("Type", "Twitter");
                            parseUser.saveInBackground();
                            Intent intent = new Intent();
                            setResult(10, intent);
                            finish();
                        } else {
                            Log.d("MyApp", "User logged in through Twitter!");
                        }
                    }
                });
            }
        });

        final List<String> permissions = Arrays.asList("user_friends", "email", "public_profile");

        findViewById(R.id.signUpFacebookFormButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackBar("Coming Soon", Snackbar.SnackbarDuration.LENGTH_LONG);
//                ParseFacebookUtils.logInWithReadPermissionsInBackground(SignUpActivity.this, permissions, new LogInCallback() {
//                    @Override
//                    public void done(ParseUser user, ParseException err) {
//                        if (user == null) {
//                            Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
//                            showSnackBar("User Cancelled Facebook", Snackbar.SnackbarDuration.LENGTH_LONG);
//                        } else if (user.isNew()) {
//                            Log.d("MyApp", "User signed up and logged in through Facebook!");
//                            showSnackBar("User Signed and Logged", Snackbar.SnackbarDuration.LENGTH_LONG);
//
//                            ParseUser parseUser = ParseUser.getCurrentUser();
//                            parseUser.put("Points", "0");
//                            parseUser.put("Type", "Facebook");
//                            parseUser.saveInBackground();
//                            Intent intent = new Intent();
//                            setResult(100, intent);
//                            finish();
//                        } else {
//                            Log.d("MyApp", "User logged in through Facebook!");
//                            showSnackBar("User Logged In", Snackbar.SnackbarDuration.LENGTH_LONG);
//                        }
//                    }
//                });
            }
    });

        ScrollView scrollView = (ScrollView) findViewById(R.id.signUpScroll);
        scrollView.setVerticalScrollBarEnabled(false);
        scrollView.setHorizontalScrollBarEnabled(false);

    }

    public void showSnackBar (String message, Snackbar.SnackbarDuration duration) {

        SnackbarManager.show(
                Snackbar.with(SignUpActivity.this)
                        .text(message)
                        .color(Color.DKGRAY)
                        .textColor(Color.rgb(255, 153, 51))
                        .duration(duration)
                        .eventListener(new EventListener() {
                            @Override
                            public void onShow(Snackbar snackbar) {
                                Log.i(TAG, String.format(
                                        "Snackbar will show. Width: %d Height: %d Offset: %d",
                                        snackbar.getWidth(), snackbar.getHeight(),
                                        snackbar.getOffset()));
                            }

                            @Override
                            public void onShowByReplace(Snackbar snackbar) {
                                Log.i(TAG, String.format(
                                        "Snackbar will show by replace. Width: %d Height: %d Offset: %d",
                                        snackbar.getWidth(), snackbar.getHeight(),
                                        snackbar.getOffset()));
                            }

                            @Override
                            public void onShown(Snackbar snackbar) {
                                Log.i(TAG, String.format(
                                        "Snackbar shown. Width: %d Height: %d Offset: %d",
                                        snackbar.getWidth(), snackbar.getHeight(),
                                        snackbar.getOffset()));
                            }

                            @Override
                            public void onDismiss(Snackbar snackbar) {
                                Log.i(TAG, String.format(
                                        "Snackbar will dismiss. Width: %d Height: %d Offset: %d",
                                        snackbar.getWidth(), snackbar.getHeight(),
                                        snackbar.getOffset()));
                            }

                            @Override
                            public void onDismissByReplace(Snackbar snackbar) {
                                Log.i(TAG, String.format(
                                        "Snackbar will dismiss by replace. Width: %d Height: %d Offset: %d",
                                        snackbar.getWidth(), snackbar.getHeight(),
                                        snackbar.getOffset()));
                            }

                            @Override
                            public void onDismissed(Snackbar snackbar) {

                            }
                        }));

    }

    private void selectImage() {

        final CharSequence[] options = {"Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Image")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);

                } else if (options[item].equals("Choose from Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {

                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {

                        f = temp;
                        File photo = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                        break;
                    }
                }

                try {

                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                    avatarButton.setBackgroundDrawable(bitmapDrawable);
                    avatarButton.setImageResource(android.R.color.transparent);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    //p = path;
                    f.delete();

                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");

                    try {

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, stream);
                        byteArray = stream.toByteArray();
                        Log.i("MY IMAGE BYTE ARRAY", byteArray + "");
                        outFile.flush();
                        outFile.close();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                file = new ParseFile("image.jpg", byteArray);
                file.saveInBackground();
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();

                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();

                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byteArray = stream.toByteArray();
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), thumbnail);
                avatarButton.setBackgroundDrawable(bitmapDrawable);
                avatarButton.setImageResource(android.R.color.transparent);

                file = new ParseFile("image.jpg", byteArray);
                file.saveInBackground();
            }
        } else {
            ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
        }
    }
}
