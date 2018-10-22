package br.com.fiap.projetoam.Clube;

import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.fiap.projetoam.Login.Login;
import br.com.fiap.projetoam.Patrocinador.Patrocinador;
import br.com.fiap.projetoam.R;
import br.com.fiap.projetoam.Salesforce.Authentication;

public class CadastroClubeActivity extends AppCompatActivity {

    private String login;
    private String senha;
    private EditText nome;
    private EditText divisao;
    private EditText estadio;
    private EditText escudo;
    private Spinner tipo;
    private Button cadastrar;
    private ProgressBar barra;
    private RequestQueue requestQueue;
    private ArrayList<Patrocinador> itensp;
    private Map<Integer, String> posts = new HashMap<Integer,String>();
    private JsonObjectRequest jsonRequest;
    private View view;
    private Authentication salesforceAuth = new Authentication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_clube);
        salesforceAuth.getAuth(getApplicationContext());
        view = findViewById(R.id.CadastroClube);
        login = getIntent().getStringExtra("login");
        senha = getIntent().getStringExtra("senha");
        nome = findViewById(R.id.txtNomeClube);
        divisao = findViewById(R.id.txtDivisaoClube);
        estadio = findViewById(R.id.txtEstadioClube);
        cadastrar = findViewById(R.id.Finalizar);
        //escudo = findViewById(R.id.);



        itensp = new ArrayList<Patrocinador>();

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Login> logins = new ArrayList<>();

                // Reset errors.
                nome.setError(null);
                divisao.setError(null);
                estadio.setError(null);

                boolean cancel = false;
                View focusView = null;

                if (TextUtils.isEmpty(nome.getText())) {
                    nome.setError("Campo necessário!");
                    focusView = nome;
                    cancel = true;
                }

                if (TextUtils.isEmpty(divisao.getText())) {
                    divisao.setError("Opções validas: Série A, Série B, Série C ou Série D ");
                    focusView = divisao;
                    cancel = true;
                }

                if (TextUtils.isEmpty(estadio.getText())) {
                    estadio.setError("Campo necessário!");
                    focusView = estadio;
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {
                    Clube c = new Clube(login.toString(),senha.toString(),nome.getText().toString(),divisao.getText().toString(), estadio.getText().toString(),"lala");
                    RegistrarC(c);

                }


                if(!login.toString().equals("") || !senha.toString().equals("") || !tipo.getSelectedItem().toString().equals("")){
                }

            }
        });

    }
    public void RegistrarC(final Clube c) {

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject jsonObject = new JSONObject();
        try{

            jsonObject.put("Name", c.getNome());
            jsonObject.put("Email__c",c.getEmail());
            jsonObject.put("Senha_Clube__c",c.getSenha());
            jsonObject.put("Divisao__c",c.getDivisao());
            jsonObject.put("Estadio__c",c.getEstadio());

            jsonRequest = new JsonObjectRequest(Request.Method.POST, salesforceAuth.getUrl() +"/services/data/v43.0/sobjects/Clube__c", jsonObject, new Response.Listener<JSONObject>() {
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
                public Map<String,String> getHeaders() throws AuthFailureError {
                    Map<String,String> header = new HashMap<String,String>();
                    header.put("Authorization", "Bearer "+salesforceAuth.getToken());
                    header.put("Content-Typer","application/json");
                    return header;
                }

            };
            requestQueue.add(jsonRequest);
        }catch(Exception ex){

        }

    }
}
