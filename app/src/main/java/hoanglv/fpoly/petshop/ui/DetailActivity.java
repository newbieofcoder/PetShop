package hoanglv.fpoly.petshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import hoanglv.fpoly.petshop.R;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);


        ImageView imageView = findViewById(R.id.detail_image);
        TextView nameTextView = findViewById(R.id.detail_name);
        TextView descriptionTextView = findViewById(R.id.detail_description);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        int imageResId = intent.getIntExtra("image", -1);

        nameTextView.setText(name);
        descriptionTextView.setText(description);
        if (imageResId != -1) {
            imageView.setImageResource(imageResId);
        }


        ImageView iconBack = findViewById(R.id.back_to_home);
        iconBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}