package br.com.fiap.projetoam.Login;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.fiap.projetoam.Clube.CadastroClubeActivity;
import br.com.fiap.projetoam.Patrocinador.CadastroPatrocinadorActivity;
import br.com.fiap.projetoam.Patrocinador.Patrocinador;
import br.com.fiap.projetoam.R;
import br.com.fiap.projetoam.Salesforce.Authentication;

public class CadastroLoginActivity extends AppCompatActivity {

    private EditText login;
    private EditText senha;
    private Spinner tipo;
    private Button cadastrar;


    private ProgressBar barra;
    private RequestQueue requestQueue;
    private static final String URL = "https://blog.deepwebbrasil.com/wp-json/wp/v2/posts/?per_page=10";
    private StringRequest request;
    public String videoID = "";
    private ArrayList<Patrocinador> itensp;
    private Context context;
    private Patrocinador p;
    private ListView listaPost;
    private Map<Integer, String> posts = new HashMap<Integer,String>();
    private JsonObjectRequest jsonRequest;
    private View view;
    Authentication a = new Authentication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_login);

       // view = findViewById(R.id.ConstraintLayout);
        login = findViewById(R.id.txtCadastrarLogin);
        senha = findViewById(R.id.txtCadastrarSenha);
        tipo = findViewById(R.id.spnTipoConta);
cadastrar = findViewById(R.id.btnCadastrarLogin);


        itensp = new ArrayList<Patrocinador>();

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Login> logins = new ArrayList<>();

                boolean cancel = false;
                View focusView = null;

                // Reset errors.
                login.setError(null);
                senha.setError(null);


                String email = String.valueOf(login.getText());

                if (TextUtils.isEmpty(login.getText())) {
                    login.setError("Campo necessário!!");
                    focusView = login;
                    cancel = true;
                }
                if (TextUtils.isEmpty(senha.getText())) {
                    senha.setError("Campo necessário!!");
                    focusView = senha;
                    cancel = true;
                }

                if (!isEmailValid(email)) {
                    login.setError(getString(R.string.error_invalid_email));
                    focusView = login;
                    cancel = true;
                }


                if (cancel) {
                    focusView.requestFocus();

                } else {

                    if(!login.getText().toString().equals("") || !senha.getText().toString().equals("") || !tipo.getSelectedItem().toString().equals("")){
                        if(tipo.getSelectedItem().toString().equals("Clube")){
                            Login l = new Login(login.getText().toString(), senha.getText().toString(),tipo.getSelectedItem().toString());
                            Intent intent = new Intent(getApplicationContext(), CadastroClubeActivity.class);
                            intent.putExtra("login",login.getText().toString());
                            intent.putExtra("senha",senha.getText().toString());
                            startActivity(intent);

                        }else if(tipo.getSelectedItem().toString().equals("Patrocinador")){
                            Login l = new Login(login.getText().toString(), senha.getText().toString(),tipo.getSelectedItem().toString());
                            Intent intent = new Intent(getApplicationContext(), CadastroPatrocinadorActivity.class);
                            intent.putExtra("login",login.getText().toString());
                            intent.putExtra("senha",senha.getText().toString());
                            startActivity(intent);
                        }
                    }
                    login.setText("");
                    senha.setText("");

                }




            }
        });
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    /*public void RegistrarP(final Patrocinador p) {

        requestQueue = Volley.newRequestQueue(context);
        JSONObject jsonObject = new JSONObject();
        try{

            jsonObject.put("Nome", p.getNome());
            jsonObject.put("Email",p.getEmail());
            jsonObject.put("Senha",p.getSenha());
            jsonObject.put("Data",p.getData());
            jsonObject.put("Ramo",p.getRamo());
            jsonObject.put("Valor",p.getValor());
            jsonObject.put("Porte",p.getPorte());

            jsonRequest = new JsonObjectRequest(Request.Method.POST, Authentication.URL_AUTH +, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    Snackbar.make(view, "Registro concluido!!!", Snackbar.LENGTH_LONG).show();
                    Log.d("Response", response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String body = null;
                    String statusCode = String.valueOf(error.networkResponse.statusCode);
                    Log.d("Status_Code",statusCode.toString());
                    NetworkResponse networkResponse = error.networkResponse;
                    if(networkResponse != null && networkResponse.data !=null){

                        String jsonError = new String(networkResponse.data);
                        Log.d("json_error",jsonError);

                    }
                }
            }){
              @Override
              public Map<String,String> getHeaders() throws AuthFailureError{
                  Map<String,String> header = new HashMap<String,String>();
                    header.put("Authorization", "Bearer "+a.getToken());
                    header.put("Content-Typer","application/json");
                    return header;
              }

            };
    requestQueue.add(jsonRequest);
        }catch(Exception ex){

        }

    }*/
}
