package br.com.fiap.projetoam.Patrocinador;

import android.content.Context;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

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

import br.com.fiap.projetoam.R;
import br.com.fiap.projetoam.Salesforce.Authentication;

public class CadastroPatrocinadorActivity extends AppCompatActivity {
    private String login;
    private String senha;
    private EditText nome;
    private EditText data;
    private EditText ramo;
    private EditText valor;
    private Spinner porte;


    private Spinner tipo;
    private Button cadastrar;


    private ProgressBar barra;
    private RequestQueue requestQueue;
    private StringRequest request;
    public String videoID = "";
    private ArrayList<Patrocinador> itensp;
    private Context context;
    private Patrocinador p;
    private ListView listaPost;
    private Map<Integer, String> posts = new HashMap<Integer,String>();
    private JsonObjectRequest jsonRequest;
    private View view;
    private Authentication salesforceAuth = new Authentication();

    Authentication a = new Authentication();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_patrocinador);
        salesforceAuth.getAuth(getApplicationContext());
        view = findViewById(R.id.CadastroPatrocinador);
        login = getIntent().getStringExtra("login");
        senha = getIntent().getStringExtra("senha");
        nome = findViewById(R.id.txtNomeC);
        data = findViewById(R.id.txtData);
        ramo = findViewById(R.id.txtRamo);
        valor = findViewById(R.id.txtValorMercado);
        cadastrar = findViewById(R.id.btnCadastrarPatrocinador);
        porte = findViewById(R.id.spnPortePatrocinador);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Patrocinador p = null;

                boolean cancel = false;
                View focusView = null;

                // Check for a valid password, if the user entered one.
                if (TextUtils.isEmpty(nome.getText())) {
                    nome.setError("Campo necessário!");
                    focusView = nome;
                    cancel = true;
                }

                // Check for a valid email address.
                if (TextUtils.isEmpty(data.getText())) {
                    data.setError("Campo necessário!");
                    focusView = data;
                    cancel = true;
                }

                if (TextUtils.isEmpty(ramo.getText())) {
                    ramo.setError("Campo necessário!");
                    focusView = ramo;
                    cancel = true;
                }

                if (TextUtils.isEmpty(valor.getText())) {
                    valor.setError("Campo necessário!");
                    focusView = valor;
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {


                    try {
                        p = new Patrocinador(login.toString(), senha.toString(), nome.getText().toString(), data.getText().toString(), ramo.getText().toString(),
                                Double.parseDouble(valor.getText().toString()), porte.getSelectedItem().toString());
                        RegistrarP(p);
                    }catch(Exception ex){
                        Toast.makeText(getApplicationContext(), "Erro ao incluir", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    public void RegistrarP(final Patrocinador p) {

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject jsonObject = new JSONObject();
        try{

            jsonObject.put("Nome_da_Empresa__c", p.getNome());
            jsonObject.put("Email__c",p.getEmail());
            jsonObject.put("Senha__c",p.getSenha());
            jsonObject.put("Ano_Fundacao__c",p.getData());
            jsonObject.put("Ramo_da_Empresa__c",p.getRamo());
            jsonObject.put("Valor_Empresa__c",p.getValor());
            jsonObject.put("Porte__c",p.getPorte());

            jsonRequest = new JsonObjectRequest(Request.Method.POST, salesforceAuth.getUrl() +"/services/data/v43.0/sobjects/Empresa__c", jsonObject, new Response.Listener<JSONObject>() {
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
