package hoanglv.fpoly.petshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import hoanglv.fpoly.petshop.R;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, Login.class));
            finish();
        }, 3000);
    }
}