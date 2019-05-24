package com.example.lynford.personal_notes_app.activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.lynford.personal_notes_app.R;
import com.example.lynford.personal_notes_app.helper.DbHandler;

public class LoginActivity extends AppCompatActivity {

    EditText txtUsername,txtPassword;
    Button button_login, button_register;
    DbHandler dbHandler;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       dbHandler = new DbHandler(LoginActivity.this);

        txtUsername = findViewById(R.id.input_usernmae);
        txtPassword = findViewById(R.id.input_password);
        button_login = findViewById(R.id.button_login);
        button_register = findViewById(R.id.button_register);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uname = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                if (dbHandler.user_authentication(uname,password)){

                    Toast.makeText(LoginActivity.this, "login success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);


                }else{
                    Toast.makeText(LoginActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = getLayoutInflater();
                View mView = inflater.inflate(R.layout.activity_create,null);

                final EditText txtFullname = mView.findViewById(R.id.fullname);
                final EditText txtUname = mView.findViewById(R.id.Username);
                final EditText txtPword = mView.findViewById(R.id.Password);
                final Button btnSave = mView.findViewById(R.id.button_save);

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String fname = txtFullname.getText().toString();
                        String uname = txtUname.getText().toString();
                        String pass = txtPword.getText().toString();

                        if (fname.equals("")){

                            txtFullname.setError("Please Input Full Name!");

                        } else if (uname.equals("")){

                            txtUname.setError("Please Input Username");

                        } else if (pass.equals("")){

                            txtPword.setError("Please input password");
                        } else {

                            if (dbHandler.check_username(uname)){
                                Toast.makeText(LoginActivity.this, "Username already taken! Please change your username.", Toast.LENGTH_SHORT).show();
                            }else{

                                dbHandler.user_registration(fname,uname,pass);
                                Toast.makeText(LoginActivity.this, "" + fname + " inserted.", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }

                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setView(mView);
                builder.setTitle("User Registration")

                        .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //code here
                                dialog.dismiss();
                            }
                        });

                dialog = builder.create();
                dialog.show();


            }
        });
    }
}
