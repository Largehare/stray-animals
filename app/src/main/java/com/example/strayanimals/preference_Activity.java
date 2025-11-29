package com.example.strayanimals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.strayanimals.ui.login.UserSQLHelper;

public class preference_Activity extends AppCompatActivity implements View.OnClickListener{
    private Button yes;
    private Button no;
    private Button dog;
    private Button cat;
    private Button know_a;
    private Button know_b;
    private Button know_c;
    private Button entry;
    private String username;
    private UserSQLHelper sqlHelper;

    private  void initSQL(){
        sqlHelper=new UserSQLHelper(this);
        sqlHelper.getReadableDatabase().query("user",null,null,null,null,null,null);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_preference);
        username=getIntent().getStringExtra("username");
        yes=findViewById(R.id.yes);
        no=findViewById(R.id.no);
        dog=findViewById(R.id.dog);
        cat=findViewById(R.id.cat);
        know_a=findViewById(R.id.know_a);
        know_b=findViewById(R.id.know_b);
        know_c=findViewById(R.id.know_c);
        entry=findViewById(R.id.entry);

        initSQL();

        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        dog.setOnClickListener(this);
        cat.setOnClickListener(this);
        know_a.setOnClickListener(this);
        know_b.setOnClickListener(this);
        know_c.setOnClickListener(this);
        entry.setOnClickListener(this);

    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.yes:
                yes.setEnabled(false);
                no.setEnabled(true);

                break;
            case R.id.no:
                no.setEnabled(false);
                yes.setEnabled(true);

                break;
            case R.id.dog:
                dog.setEnabled(false);
                cat.setEnabled(true);

                break;
            case R.id.cat:
                dog.setEnabled(true);
                cat.setEnabled(false);

                break;
            case R.id.know_a:
                know_a.setEnabled(false);
                know_b.setEnabled(true);
                know_c.setEnabled(true);

                break;
            case R.id.know_b:
                know_b.setEnabled(false);
                know_a.setEnabled(true);
                know_c.setEnabled(true);

                break;
            case R.id.know_c:
                know_c.setEnabled(false);
                know_b.setEnabled(true);
                know_a.setEnabled(true);

                break;
            case R.id.entry:
                Intent intent =new Intent(preference_Activity.this,MainActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
        }

    }
}