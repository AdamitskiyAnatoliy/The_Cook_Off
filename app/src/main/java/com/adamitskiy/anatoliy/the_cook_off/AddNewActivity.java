package com.adamitskiy.anatoliy.the_cook_off;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.SnackbarManager;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

public class AddNewActivity extends AppCompatActivity {

    EditText title, content;
    ImageView imageView;
    byte[] byteArray;
    boolean challenge = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        challenge = getIntent().getBooleanExtra("Challenge", false);

        imageView = (ImageView) findViewById(R.id.foodImage);
        title = (EditText) findViewById(R.id.titleTextFieldImage);
        content = (EditText) findViewById(R.id.contentTextFieldImage);

        Button takePicButton = (Button) findViewById(R.id.takeImageButton);
        takePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        FloatingActionButton doneButton = new FloatingActionButton.Builder(this)
                .withDrawable(getResources().getDrawable(R.mipmap.ic_action_done))
                .withButtonColor(Color.rgb(30,149,140))
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(0, 0, 16, 16)
                .create();

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (title.getText().toString().equals("") || content.getText().toString()
                        .equals("") || imageView.getDrawable() == null) {
                    simpleSnackBar("Please Fill Out All Fields");
                } else {

                    PointSystem pointSystem = new PointSystem(getApplicationContext());

                    if (challenge == false) {
                        ParseUser currentUser = ParseUser.getCurrentUser();

                        int points = Integer.parseInt(currentUser.getString("Points"));
                        points += 1000;

                        currentUser.put("Points", Integer.toString(points));
                        currentUser.saveInBackground();

                        ParseObject mainFeedEntry = new ParseObject("Main_Feed_Entry");
                        mainFeedEntry.put("User", ParseUser.getCurrentUser());
                        mainFeedEntry.put("Message", currentUser.getUsername() + " has prepared the " + title.getText().toString() + " and earned 1000 points.");
                        mainFeedEntry.saveInBackground();

                        simpleSnackBar("Saving New Entry...");
                        Toast.makeText(getApplicationContext(), "1000 Points Achieved", Toast.LENGTH_LONG).show();
                        finish();

                        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), intent1, 0);

                        Notification n = new Notification.Builder(getApplicationContext())
                                .setContentTitle("Health Champion")
                                .setContentText("You are making this look easy!!!")
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentIntent(pIntent)
                                .setAutoCancel(true).build();


                        NotificationManager notificationManager =
                                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                        notificationManager.notify(0, n);
//                    ParseObject food = new ParseObject("Food");
//                    note.put("title", foodTitle.getText().toString());
//                    note.put("content", foodContent.getText().toString());
//                    note.put("hours", "0");
//                    ParseFile file = new ParseFile("image.jpg", byteArray);
//                    file.saveInBackground();
//                    note.put("image",file);
//                    note.put("foodType", "image");
//                    note.setACL(new ParseACL(ParseUser.getCurrentUser()));
//                    note.saveInBackground(new SaveCallback() {
//                        @Override
//                        public void done(ParseException e) {
//                            if (e == null) {
//                                finish();
//                                Intent intent = new Intent(MainActivity.NEW_POST);
//                                intent.putExtra("foodType", "image");
//                                intent.putExtra("completed", "yes");
//                                sendBroadcast(intent);
//                            } else {
//                                finish();
//                                Intent intent = new Intent(MainActivity.NEW_POST);
//                                intent.putExtra("foodType", "image");
//                                intent.putExtra("completed", "no");
//                                sendBroadcast(intent);
//                            }
//                        }
//                    });

                    } else {

                        ParseUser currentUser = ParseUser.getCurrentUser();

                        int points = Integer.parseInt(currentUser.getString("Points"));
                        points += 5000;

                        currentUser.put("Points", Integer.toString(points));
                        currentUser.saveInBackground();

                        ParseObject mainFeedEntry = new ParseObject("Main_Feed_Entry");
                        mainFeedEntry.put("User", ParseUser.getCurrentUser());
                        mainFeedEntry.put("Message", currentUser.getUsername() + " has prepared the " + title.getText().toString() + " and earned 5000 points for completing the challenge.");
                        mainFeedEntry.saveInBackground();

                        simpleSnackBar("Saving New Entry...");
                        Toast.makeText(getApplicationContext(), "5000 Points Achieved", Toast.LENGTH_LONG).show();
                        finish();

                        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), (int) System.currentTimeMillis(), intent1, 0);

                        Notification n = new Notification.Builder(getApplicationContext())
                                .setContentTitle("Challenge Complete")
                                .setContentText("Congratulations on completing the challenge.")
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentIntent(pIntent)
                                .setAutoCancel(true).build();


                        NotificationManager notificationManager =
                                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                        notificationManager.notify(0, n);

                    }
                }
            }
        });
    }

    private void selectImage() {

        final CharSequence[] options = { "Take Image", "Choose from Gallery","Cancel" };

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
                        //pic = photo;
                        break;
                    }
                }

                try {

                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
                            bitmapOptions);
                    imageView.setImageBitmap(bitmap);

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
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
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
                imageView.setImageBitmap(thumbnail);
            }
        }
    }

    public void simpleSnackBar(String text) {
        SnackbarManager.show(
                Snackbar.with(this)
                        .text(text)
                        .textColor(Color.rgb(255, 153, 51))
                        .color(Color.DKGRAY)
                        .duration(Snackbar.SnackbarDuration.LENGTH_SHORT));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}

