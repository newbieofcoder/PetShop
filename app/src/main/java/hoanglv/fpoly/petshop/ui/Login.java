package hoanglv.fpoly.petshop.ui;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import hoanglv.fpoly.petshop.R;

public class Login extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);


        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);

        loadEmailPass();

        Button btnSignIn = findViewById(R.id.btn_login);
        TextView navToSignUp = findViewById(R.id.navigate_to_login);

        btnSignIn.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        user = auth.getCurrentUser();
                        startActivity(new Intent(Login.this, MainActivity.class));
                    } else {
                        Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        navToSignUp.setOnClickListener(v -> {
            // Gọi hàm controller xét logic
            startActivity(new Intent(this, SignUp.class));
        });

    }


    private void loadEmailPass() {
        SharedPreferences sharedPreferences = getSharedPreferences("remember", MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("email", "");
        String savedPassword = sharedPreferences.getString("password", "");
        emailEditText.setText(savedEmail);
        passwordEditText.setText(savedPassword);
    }
}