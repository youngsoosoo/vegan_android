package com.inhatc.vegan_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class SignupActivity extends AppCompatActivity{
    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private EditText editTextPhone;
    private Button buttonJoin;
    private Button buttonCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("vegan");

        editTextEmail = (EditText) findViewById(R.id.join_email);
        editTextPassword = (EditText) findViewById(R.id.join_password);
        editTextName = (EditText) findViewById(R.id.join_name);
        editTextPhone = (EditText) findViewById(R.id.join_phone);

        buttonJoin = (Button) findViewById(R.id.join_button);
        buttonJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextEmail.getText().toString().equals("") && !editTextPassword.getText().toString().equals("")) {
                    // 이메일과 비밀번호가 공백이 아닌 경우
                    createUser(editTextName.getText().toString(), editTextPhone.getText().toString() ,editTextPassword.getText().toString(), editTextEmail.getText().toString());
                } else {
                    // 이메일과 비밀번호가 공백인 경우
                    Toast.makeText(SignupActivity.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonCancle = (Button) findViewById(R.id.delete);
        buttonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SignUpActivity 연결
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createUser(String name, String phone, String password, String email) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공시
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            account.setIdToken(user.getUid());
                            account.setEmail(user.getEmail());
                            account.setPassword(password);
                            account.setName(name);
                            account.setPhone(phone);

                            //setValue : database에 삽입.
                            reference.child("UserAccount").child(user.getUid()).setValue(account);
                            Toast.makeText(SignupActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            // 계정이 중복된 경우
                            Toast.makeText(SignupActivity.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
