package br.com.fiap.projetoam.Salesforce;

import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Authentication  {

    public static final String URL_AUTH = "https://login.salesforce.com/services/oauth2/token?grant_type=password&client_id=3MVG9zlTNB8o8BA1vnA.l3P6yAGVnvA6BATXMY7.rdcViOFvHm5xblAW293k82WD4XjgL.nPxiXs1.SgFzN.J\n" +
            "&client_secret=4918710697361579918&username=gabriel_abs@hotmail.com&password=Gg15443656VSksM7MX4u9p1aUKLqjxO6uBc";
    private StringRequest request;
    private RequestQueue requestQueue;
    private JSONObject authUser;
    private String url;
    private String token = "00Df4000002499j!AQ0AQPu4OGkzcFq1Uisywmt1i2xSbxftKW0BocCEm4kDmSFUIA6UtINOT6jFOuVQfYjNvqtUPB0ABOhdrlFbsvXPM3221c6A";
            //= "00Df4000002499j!AQ0AQPu4OGkzcFq1Uisywmt1i2xSbxftKW0BocCEm4kDmSFUIA6UtINOT6jFOuVQfYjNvqtUPB0ABOhdrlFbsvXPM3221c6A";

    public Authentication() {
    }

    public String getUrl() {
        return url;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "Authentication{" +
                "request=" + request +
                ", requestQueue=" + requestQueue +
                ", authUser=" + authUser +
                ", url='" + url + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public void getAuth(final Context context) {
        requestQueue = Volley.newRequestQueue(context);
        request = new StringRequest(Request.Method.POST, URL_AUTH, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    // Caso exista alguma politica de acesso ou restrição de dados em HTTPS é necessário incluir as duas linhas abaixo para ter um acesso total ao conteúdo da resposta do RESTSErvice
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    authUser = new JSONObject(response);
                    url = authUser.getString("instance_url");
                    token = authUser.getString("access_token");
                    Log.i("Authentication", authUser.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Você tem problemas com sua conexão. Teste novamente", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();

                return hashMap;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                headers.put("Content-Type", "application/json");
                headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }

}
