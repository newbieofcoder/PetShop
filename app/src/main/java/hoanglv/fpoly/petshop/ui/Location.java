package hoanglv.fpoly.petshop.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import hoanglv.fpoly.petshop.R;
import hoanglv.fpoly.petshop.services.GHNRequest;

public class Location extends AppCompatActivity {
    GHNRequest request;
    Spinner spinnerProvince, spinnerDistrict, spinnerWard;
    EditText edtAddress;
    Button next;
    String provinceID, districtID, wardCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

    }
}