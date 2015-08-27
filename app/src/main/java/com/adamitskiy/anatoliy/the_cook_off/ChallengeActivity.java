package com.adamitskiy.anatoliy.the_cook_off;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

public class ChallengeActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("http://m.allrecipes.com/recipe/222585/baked-chicken-and-zucchini/?" +
                "internalSource=popular&referringContentType=home%20page");

        FloatingActionButton acceptButton = new FloatingActionButton.Builder(this)
                .withDrawable(getResources().getDrawable(R.mipmap.ic_action_done))
                .withButtonColor(Color.rgb(30, 149, 140))
                .withGravity(Gravity.BOTTOM | Gravity.RIGHT)
                .withMargins(0, 0, 16, 16)
                .create();

        FloatingActionButton denyButton = new FloatingActionButton.Builder(this)
                .withDrawable(getResources().getDrawable(R.mipmap.ic_action_cancel))
                .withButtonColor(Color.rgb(30, 149, 140))
                .withGravity(Gravity.BOTTOM | Gravity.LEFT)
                .withMargins(16, 0, 0, 16)
                .create();

        denyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Challenge Accepted", Toast.LENGTH_LONG).show();
                finish();

                Intent intent = new Intent(getApplicationContext(), AddNewActivity.class);
                intent.putExtra("Challenge", true);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_challenge, menu);
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
