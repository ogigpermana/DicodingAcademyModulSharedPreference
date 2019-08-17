package com.ogi.mysharedpreferences;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ogi.mysharedpreferences.model.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvName, tvAge, tvPhoneNo, tvEmail, tvIsLoveMU;
    private Button btnSave;
    private UserPreference mUserPreference;

    private boolean isPreferenceEmpty = false;
    private User mUser;

    private final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = findViewById(R.id.tv_name);
        tvAge = findViewById(R.id.tv_age);
        tvPhoneNo = findViewById(R.id.tv_phone);
        tvEmail = findViewById(R.id.tv_email);
        tvIsLoveMU = findViewById(R.id.tv_is_love_mu);
        btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("My User Preference");
        }
        mUserPreference = new UserPreference(this);
        showExistingPreference();
    }

    private void showExistingPreference() {
        mUser = mUserPreference.getUser();
        populateView(mUser);
        checkForm(mUser);
    }

    private void populateView(User mUser) {
        tvName.setText(mUser.getName().isEmpty() ? "Tidak Ada" : mUser.getName());
        tvAge.setText(String.valueOf(mUser.getAge()).isEmpty() ? "Tidak Ada" : String.valueOf(mUser.getAge()));
        tvIsLoveMU.setText(mUser.isLove() ? "Ya" : "Tidak");
        tvEmail.setText(mUser.getEmail().isEmpty() ? "Tidak Ada" : mUser.getEmail());
        tvPhoneNo.setText(mUser.getPhoneNumber().isEmpty() ? "Tidak Ada" : mUser.getPhoneNumber());
    }

    private void checkForm(User mUser) {
        if (!mUser.getName().isEmpty()) {
            btnSave.setText(getString(R.string.change));
            isPreferenceEmpty = false;
        } else {
            btnSave.setText(getString(R.string.save));
            isPreferenceEmpty = true;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save) {
            Intent intent = new Intent(MainActivity.this, FormUserPreferenceActivity.class);
            if (isPreferenceEmpty) {
                intent.putExtra(FormUserPreferenceActivity.EXTRA_TYPE_FORM, FormUserPreferenceActivity.TYPE_ADD);
                intent.putExtra("USER", mUser);
            } else {
                intent.putExtra(FormUserPreferenceActivity.EXTRA_TYPE_FORM, FormUserPreferenceActivity.TYPE_EDIT);
                intent.putExtra("USER", mUser);
            }
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == FormUserPreferenceActivity.RESULT_CODE) {
               if (data != null) {
                   mUser = data.getParcelableExtra(FormUserPreferenceActivity.EXTRA_RESULT);
               }
                populateView(mUser);
                checkForm(mUser);
            }
        }
    }
}
