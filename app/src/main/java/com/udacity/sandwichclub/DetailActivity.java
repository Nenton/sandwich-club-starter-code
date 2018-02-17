package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mAlsoKnowAsTv;
    private TextView mIngredients;
    private TextView mPlaceOfOrigin;
    private TextView mDescription;
    private ImageView mImageSandwichIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mImageSandwichIv = findViewById(R.id.image_iv);
        mAlsoKnowAsTv = findViewById(R.id.also_known_tv);
        mIngredients = findViewById(R.id.ingredients_tv);
        mPlaceOfOrigin = findViewById(R.id.origin_tv);
        mDescription = findViewById(R.id.description_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mImageSandwichIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        StringBuilder alsoKnowAs = new StringBuilder();
        for (String name : sandwich.getAlsoKnownAs()) {
            if (!alsoKnowAs.toString().isEmpty()) {
                alsoKnowAs.append(", ");
            }
            alsoKnowAs.append(name);
        }

        StringBuilder ingredients = new StringBuilder();
        for (String ingredient : sandwich.getIngredients()) {
            if (!ingredients.toString().isEmpty()) {
                ingredients.append(", ");
            }
            ingredients.append(ingredient);
        }

        mAlsoKnowAsTv.setText(alsoKnowAs.toString().isEmpty() ? "Empty" : alsoKnowAs.toString());
        mIngredients.setText(ingredients.toString().isEmpty() ? "Empty" : ingredients.toString());
        mPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin().isEmpty() ? "Empty" : sandwich.getPlaceOfOrigin());
        mDescription.setText(sandwich.getDescription().isEmpty() ? "Empty" : sandwich.getDescription());
    }
}
