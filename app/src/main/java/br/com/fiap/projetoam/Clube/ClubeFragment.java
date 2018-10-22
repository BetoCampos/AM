package br.com.fiap.projetoam.Clube;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.com.fiap.projetoam.R;
import br.com.fiap.projetoam.Salesforce.Authentication;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClubeFragment extends Fragment {

    private View view;
    ArrayList<Clube> clubeArrayList = new ArrayList<>();
    private ListView listView;
    ClubeAdapter ClubeAdapter;

    private RequestQueue requestQueue;
    private StringRequest request;
    private Authentication salesforceAuth = new Authentication();

    public ClubeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        salesforceAuth.getAuth(getActivity());
        view = inflater.inflate(R.layout.fragment_clube, container, false);
        getActivity().setTitle("Clube");

        listView = view.findViewById(R.id.listClube);
        procurarTodos();

        return view;
    }



    public void procurarTodos(){
        requestQueue = Volley.newRequestQueue(getActivity());
        JSONObject jsonBody = new JSONObject();
        try {
            final String requestBody = jsonBody.toString();

            request = new StringRequest(Request.Method.GET, "https://na59.salesforce.com/services/data/v43.0/query?q=SELECT+Email__c,Senha_Clube__c,Name,Divisao__c,Estadio__c+FROM+Clube__c", new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        try {
                            clubeArrayList.clear();
                            JSONObject someObject = new JSONObject(response);
                            JSONArray Jarray = someObject.getJSONArray("records");
                            for (int i = 0; i < Jarray.length(); i++) {

                                JSONObject l = Jarray.getJSONObject(i);
                                String email = l.getString("Email__c");
                                String senha = l.getString("Senha_Clube__c");
                                String nome = l.getString("Name");
                                String divisao = l.getString("Divisao__c");
                                String estadio = l.getString("Estadio__c");
                                //String escudo = l.getString("Escudo__c");
                                String escudo_fix = "escudo";


                                Clube c = new Clube(email,senha,nome,divisao,estadio,escudo_fix);
                                clubeArrayList.add(c);

                            }

                        }catch (Exception e){
                            Toast.makeText(getContext(), "Usuário não existe", Toast.LENGTH_SHORT).show();
                            Log.i("Erro Login: ", e.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ClubeAdapter = new ClubeAdapter(clubeArrayList, view.getContext());
                    listView.setAdapter(ClubeAdapter);
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Error: ", new String(error.networkResponse.data));
                    Toast.makeText(getContext(), "Você tem problemas com sua conexão. Teste novamente", Toast.LENGTH_LONG).show();
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();

                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", "Bearer " + salesforceAuth.getToken());
                    return headers;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(request);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
