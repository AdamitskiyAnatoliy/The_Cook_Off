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
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class ChallengeActivity extends AppCompatActivity {

    WebView webView;
    ArrayList<String> healthyArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);
        supportTime();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(healthyArray.get(randInt(0, healthyArray.size())));

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

    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min)) + min;
        return randomNum;
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

    private void supportTime() {

        healthyArray.add("http://allrecipes.com/recipe/213211/turkey-and-quinoa-meatloaf/?internalSource=hn_carousel%2001_Turkey%20and%20Quinoa%20Meatloaf&referringId=84&referringContentType=recipe%20hub&referringPosition=carousel%2001");
        healthyArray.add("http://allrecipes.com/recipe/15084/vegetarian-chili/?internalSource=staff%20pick&referringId=17700&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/13206/grandmas-chicken-noodle-soup/?internalSource=recipe%20hub&referringId=17700&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/13933/black-bean-and-corn-salad-ii/?internalSource=staff%20pick&referringId=1346&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/8870/bbq-chicken-salad/?internalSource=staff%20pick&referringId=1346&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/14069/vegan-lasagna-i/?internalSource=rotd&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/43719/baked-halibut-steaks/?internalSource=staff%20pick&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/48449/slow-cooker-chili-ii/?internalSource=staff%20pick&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/90089/penne-with-chicken-and-asparagus/?internalSource=recipe%20hub&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/142614/grilled-fish-tacos-with-chipotle-lime-dressing/?internalSource=recipe%20hub&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/79521/slow-cooker-honey-garlic-chicken/?internalSource=recipe%20hub&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/11915/alfredo-light/?internalSource=recipe%20hub&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/13999/quick-black-beans-and-rice/?internalSource=recipe%20hub&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/85158/grilled-cilantro-salmon/?internalSource=recipe%20hub&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/218929/slow-cooker-oats/?internalSource=recipe%20hub&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/91894/winter-vegetable-hash/?internalSource=recipe%20hub&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/17868/leftover-chicken-croquettes/?internalSource=recipe%20hub&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/17670/chicken-and-broccoli-pasta/?internalSource=recipe%20hub&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/8780/quick-chicken-marsala/?internalSource=recipe%20hub&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/48930/chicken-marinade/?internalSource=recipe%20hub&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/11691/tomato-and-garlic-pasta/?internalSource=recipe%20hub&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/13885/lentil-loaf/?internalSource=recipe%20hub&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/102518/sweet-pepper-pasta-toss-with-kale/?internalSource=recipe%20hub&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/8733/italian-chicken-with-garlic-and-lemon/?internalSource=recipe%20hub&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/25454/doreens-ham-slices-on-the-grill/?internalSource=recipe%20hub&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/201964/eggplant-with-garlic-sauce/?internalSource=recipe%20hub&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/17454/pork-with-peach-and-black-bean-salsa/?internalSource=recipe%20hub&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/28196/quick-chick/?internalSource=recipe%20hub&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/8624/chicken-with-couscous/?internalSource=recipe%20hub&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/15321/herb-and-chicken-pasta/?internalSource=recipe%20hub&referringId=1320&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/199598/quinoa-with-asian-flavors/?internalSource=staff%20pick&referringId=84&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/14091/stir-fried-kale/?internalSource=staff%20pick&referringId=84&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/72381/orange-roasted-salmon/?internalSource=staff%20pick&referringId=84&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/75861/amazing-pork-tenderloin-in-the-slow-cooker/?internalSource=recipe%20hub&referringId=84&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/14132/black-bean-and-salsa-soup/?internalSource=recipe%20hub&referringId=84&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/144346/roasted-garlic-lemon-broccoli/?internalSource=recipe%20hub&referringId=84&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/16715/vegetarian-chickpea-sandwich-filling/?internalSource=recipe%20hub&referringId=84&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/15559/black-beans-and-rice/?internalSource=recipe%20hub&referringId=84&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/73757/penne-pasta-with-spinach-and-bacon/?internalSource=recipe%20hub&referringId=84&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/57783/emilys-famous-hash-browns/?internalSource=recipe%20hub&referringId=84&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/93666/spinach-and-feta-pita-bake/?internalSource=recipe%20hub&referringId=84&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/74337/fiery-fish-tacos-with-crunchy-corn-salsa/?internalSource=recipe%20hub&referringId=84&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/17048/garlic-shrimp-linguine/?internalSource=recipe%20hub&referringId=84&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/82503/spicy-baked-sweet-potato-fries/?internalSource=recipe%20hub&referringId=84&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/21256/cod-with-italian-crumb-topping/?internalSource=recipe%20hub&referringId=84&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/9025/bourbon-chicken/?internalSource=recipe%20hub&referringId=84&referringContentType=recipe%20hub");
        healthyArray.add("http://allrecipes.com/recipe/78454/black-eyed-pea-gumbo/?internalSource=recipe%20hub&referringId=84&referringContentType=recipe%20hub");
    }
}
