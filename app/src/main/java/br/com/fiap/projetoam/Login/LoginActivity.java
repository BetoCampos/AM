package br.com.fiap.projetoam.Login;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.projetoam.MainActivity;
import br.com.fiap.projetoam.R;

public class LoginActivity extends AppCompatActivity {

    private EditText txtLogin;
    private EditText txtPassword;
    private Button Login;
    private Button Cadastrar;
    RecyclerView recycler;
    Login login;
    private List<Login> logins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);setContentView(R.layout.activity_login);

        txtLogin = findViewById(R.id.txtLogin);
        txtPassword = findViewById(R.id.txtPassword);
        Login = findViewById(R.id.btnEntrar);
        Cadastrar = findViewById(R.id.btnCadastrar);

/*

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = Room.databaseBuilder(getApplicationContext(), MyDataBase.class, "MyDatabase").allowMainThreadQueries().build();
                logins = new ArrayList<Login>() ;
                logins = db.loginDao().getAll();
                for (Login l : logins){
                    if(txtLogin.getText().equals(l.getLogin())&& txtPassword.getText().equals(l.getSenha())){
                        if (l.getTipo().equals("Clube")){
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }


                db = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, "MyDatabase").allowMainThreadQueries().build();
                clubes = new ArrayList<Clube>();
                clubes = db.clubeDao().getAll();
                for (Clube c : clubes){

                    if(){
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }

            }
        });
        Cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CadastroLoginActivity.class);
                startActivity(intent);
            }
        });
*/
    }

}
