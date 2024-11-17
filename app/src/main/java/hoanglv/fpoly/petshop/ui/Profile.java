package hoanglv.fpoly.petshop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import hoanglv.fpoly.petshop.R;

public class Profile extends AppCompatActivity {
    private Button btnChangePassword;
    private Button btnLogout;
    private ImageView imgProfile;
    private TextView txtName;
    private TextView txtGender;
    private TextView txtEmail;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseUser user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        btnChangePassword = findViewById(R.id.btn_change_password);
        btnLogout = findViewById(R.id.btn_logout);
        imgProfile = findViewById(R.id.imgProfile);
        txtName = findViewById(R.id.txt_name);
        txtGender = findViewById(R.id.txt_gender);
        txtEmail = findViewById(R.id.txt_email);

        txtEmail.setText(user.getEmail());
        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(Profile.this, Login.class));
            finish();
        });
    }
}