// AddEtudiantActivity.java

package com.vo1d.projetws;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vo1d.projetws.beans.Etudiant;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddEtudiant extends AppCompatActivity implements View.OnClickListener {
    private EditText nom;
    private EditText prenom;
    private Spinner ville;
    private RadioButton m;
    private RadioButton f;
    private Button add;
    private OkHttpClient client = new OkHttpClient();
    private String serverUrl = "http://192.168.1.158/phpws/ws/createEtudiant.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_etudiant);

        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        ville = findViewById(R.id.ville);
        add = findViewById(R.id.add);
        m = findViewById(R.id.m);
        f = findViewById(R.id.f);

        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("AddEtudiant", "Button clicked");
        if (v == add) {
            String sexe = m.isChecked() ? "homme" : "femme";
            RequestBody body = new FormBody.Builder()
                    .add("nom", nom.getText().toString())
                    .add("prenom", prenom.getText().toString())
                    .add("ville", ville.getSelectedItem().toString())
                    .add("sexe", sexe)
                    .build();

            Request request = new Request.Builder()
                    .url(serverUrl)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        Type type = new TypeToken<Collection<Etudiant>>() {}.getType();
                        Collection<Etudiant> etudiants = new Gson().fromJson(responseData, type);
                        for (Etudiant e : etudiants) {
                            Log.d("AddEtudiant", e.toString());
                        }

                        // Set a result code to indicate success (you can define your custom codes)
                        setResult(RESULT_OK);

                        // Finish the activity to return to the ListEtudiantActivity
                        finish();
                    }
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("AddEtudiant", "Error: " + e.getMessage());
                }
            });
        }
    }
}
