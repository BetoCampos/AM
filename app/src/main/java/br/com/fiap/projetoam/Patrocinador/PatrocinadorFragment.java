package br.com.fiap.projetoam.Patrocinador;


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
public class PatrocinadorFragment extends Fragment {

    private View view;
    ArrayList<Patrocinador> patrocinadorArrayList = new ArrayList<Patrocinador>();
    private ListView listView;
    PatrocinadorAdapter patrocinadorAdapter;

    private RequestQueue requestQueue;
    private StringRequest request;
    private Authentication salesforceAuth = new Authentication();

    public PatrocinadorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        salesforceAuth.getAuth(getActivity());
        view = inflater.inflate(R.layout.fragment_patrocinador, container, false);
        getActivity().setTitle("Patrocinadores");

        listView = view.findViewById(R.id.listPatrocinador);
        procurarTodos();

        return view;
    }


    public void procurarTodos(){
        requestQueue = Volley.newRequestQueue(getActivity());
        JSONObject jsonBody = new JSONObject();
        try {
            final String requestBody = jsonBody.toString();

            request = new StringRequest(Request.Method.GET, "https://na59.salesforce.com/services/data/v43.0/query?q=SELECT+Name+,+Nome_da_Empresa__c+,+Email__c+,+Senha__c+,+Ano_Fundacao__c+,+Ramo_da_Empresa__c+,+Valor_Empresa__c+,+Porte__c+FROM+Empresa__c+ORDER BY CreatedDate ASC NULLS LAST", new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        try {
                            patrocinadorArrayList.clear();
                            JSONObject someObject = new JSONObject(response);
                            JSONArray Jarray = someObject.getJSONArray("records");
                            for (int i = 0; i < Jarray.length(); i++) {

                                JSONObject l = Jarray.getJSONObject(i);
                                String email = l.getString("Email__c").toString();
                                String senha = l.getString("Senha__c").toString();
                                String nome = l.getString("Nome_da_Empresa__c").toString();
                                String data = l.getString("Ano_Fundacao__c").toString();
                                String ramo = l.getString("Ramo_da_Empresa__c").toString();
                                Double valor = l.getDouble("Valor_Empresa__c");
                                String porte =  l.getString("Porte__c");


                                Patrocinador p = new Patrocinador(email,senha,nome,data,ramo,valor,porte);
                                patrocinadorArrayList.add(p);

                                /*
                                Toast.makeText(getContext(), patrocinadorArrayList.get(i).getNome(), Toast.LENGTH_LONG).show();
                                Toast.makeText(getContext(), patrocinadorArrayList.get(i).getEmail(), Toast.LENGTH_LONG).show();
                                Toast.makeText(getContext(), patrocinadorArrayList.get(i).getRamo(), Toast.LENGTH_LONG).show();
                                Toast.makeText(getContext(), patrocinadorArrayList.get(i).getData(), Toast.LENGTH_LONG).show();
                                Toast.makeText(getContext(), patrocinadorArrayList.get(i).getValor().toString(), Toast.LENGTH_LONG).show();
                                Toast.makeText(getContext(), patrocinadorArrayList.get(i).getPorte(), Toast.LENGTH_LONG).show();
                                Toast.makeText(getContext(), patrocinadorArrayList.get(i).getSenha(), Toast.LENGTH_LONG).show();
                                */
                            }
                           // populatePatrocinador(patrocinadorArrayList);


                        }catch (Exception e){
                            Toast.makeText(getContext(), "Usuário não existe", Toast.LENGTH_SHORT).show();
                            Log.i("Erro Login: ", e.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    patrocinadorAdapter = new PatrocinadorAdapter(patrocinadorArrayList, view.getContext());
                    listView.setAdapter(patrocinadorAdapter);
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



