package uaspraktek.ej.com.uaspraktek;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnSignIn;

    String getUsername;
    String getPassword;

    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //init
        etUsername = findViewById(R.id.tv_username);
        etPassword = findViewById(R.id.tv_password);

        btnSignIn = findViewById(R.id.btn_sign);


        etUsername.setText("siroj");
        etPassword.setText("bucin1234");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get text username & password
                getUsername = etUsername.getText().toString();
                getPassword = etPassword.getText().toString();

                if (getUsername.equals("siroj") && getPassword.equals("bucin1234")){
                    //pesan("berhasil login");

                    //Memulai Activity Baru
                    Intent intent = new Intent(context, ListActivity.class);
                    startActivity(intent);
                    //finish();
                }else {
                    pesan("gagal login");
                }

            }
        });
    }

    public void pesan(String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
