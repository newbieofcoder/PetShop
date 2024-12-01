package hoanglv.fpoly.petshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import hoanglv.fpoly.petshop.R;

public class ProfileFragment extends Fragment {
    private Button btnChangePassword;
    private Button btnLogout;
    private ImageView imgProfile;
    private TextView txtName;
    private TextView txtGender;
    private TextView txtEmail;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseUser user = auth.getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        btnChangePassword = view.findViewById(R.id.btn_change_password);
        btnLogout = view.findViewById(R.id.btn_logout);
        imgProfile = view.findViewById(R.id.imgProfile);
        txtName = view.findViewById(R.id.txt_name);
        txtGender = view.findViewById(R.id.txt_gender);
        txtEmail = view.findViewById(R.id.txt_email);
        assert user != null;
        txtEmail.setText(user.getEmail());
        btnLogout.setOnClickListener(v -> {
            requireActivity().finish();
        });
        return view;
    }
}