package com.inhatc.vegan_android;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore Firestore;
    private DatabaseReference reference;
    private static List<UserAccount> userList = new ArrayList<>();
    private EditText name;
    private EditText phone;
    private Button searchid;
    private EditText email;
    private EditText namepw;
    private EditText phonepw;
    private Button searchpw;
    UserAccount user = new UserAccount();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        firebaseAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("vegan");


        //아이디 찾기

        name = (EditText) findViewById(R.id.SearchName);
        phone = (EditText) findViewById(R.id.SearchPhone);

        searchid = (Button) findViewById(R.id.SearchIdbtn);
        searchid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!name.getText().toString().equals("") && !phone.getText().toString().equals("")) {
                    // 이름과 생일이 공백이 아닌 경우
                    if(user != null){
                        Toast.makeText(SearchActivity.this, "아이디 : " + user.getEmail(), Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        Toast.makeText(SearchActivity.this, "일치하는 회원이 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // 이름과 생일이 공백인 경우
                    Toast.makeText(SearchActivity.this, "이름과 생일을 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });


        //비밀번호 찾기
        email = (EditText) findViewById(R.id.SearchId);

        namepw = (EditText) findViewById(R.id.SearchNamePw);
        phonepw = (EditText) findViewById(R.id.SearchPhonePw);

        searchpw = (Button) findViewById(R.id.SearchPwbtn);


    }

    public void findUserByName(String name, String birth){
        firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //비밀번호 재설정 메일을 보내기가 성공했을때 이벤트
                    Toast.makeText(SearchActivity.this, "비밀번호 : " + user.getEmail(), Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
    public void findid(){
        Firestore.collection("UserAccount").whereEqualTo("phone", phone).whereEqualTo("userName", name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String email = "";

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("이메일", document.getId() + " => " + document.getData());
                                email = document.getId();
                            }

                            //아이디 정보 띄워주기
                            Toast.makeText(SearchActivity.this, "아이디 : " + email, Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("왜 안됌", "되는건가?");
            }
        });
    }


}
