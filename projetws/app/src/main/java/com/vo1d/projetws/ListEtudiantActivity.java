// ListEtudiantActivity.java

package com.vo1d.projetws;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vo1d.projetws.beans.Etudiant;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ListEtudiantActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private com.vo1d.projetws.StudentAdapter studentAdapter;
    private OkHttpClient client = new OkHttpClient();
    private String serverUrl = "http://192.168.1.158/phpws/ws/loadEtudiant.php"; // Replace with your server URL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_etudiant);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView = findViewById(R.id.recyclerView);
        registerForContextMenu(recyclerView);


        // Add Etudiant Button
        Button addEtudiantButton = findViewById(R.id.addEtudiantButton);
        addEtudiantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start AddEtudiantActivity with startActivityForResult
                Intent intent = new Intent(ListEtudiantActivity.this, AddEtudiant.class);
                startActivityForResult(intent, 1); // You can use any request code (e.g., 1)
            }
        });

        RequestBody postRequestBody = new FormBody.Builder()
                .build(); // This creates an empty POST request body

        Request request = new Request.Builder()
                .url(serverUrl)
                .post(postRequestBody)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();

                    Log.d("ListEtudiantActivity", "Response Data: " + responseData);                    Type type = new TypeToken<Collection<Etudiant>>() {}.getType();
                    final Collection<Etudiant> etudiants = new Gson().fromJson(responseData, type);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            studentAdapter = new com.vo1d.projetws.StudentAdapter(new ArrayList<>(etudiants));
                            recyclerView.setAdapter(studentAdapter);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("ListEtudiantActivity", "Error: " + e.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                // Handle the result, e.g., refresh the student list
                refreshStudentList(); // Implement this method to reload the student data
            }
        }
    }

    private void refreshStudentList() {
        RequestBody postRequestBody = new FormBody.Builder()
                .build(); // Create a request body if needed

        Request request = new Request.Builder()
                .url(serverUrl)
                .post(postRequestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();

                    Type type = new TypeToken<Collection<Etudiant>>() {}.getType();
                    final Collection<Etudiant> etudiants = new Gson().fromJson(responseData, type);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Clear the existing list and add the updated student data
                            studentAdapter.clear();
                            studentAdapter.addAll(etudiants);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("ListEtudiantActivity", "Error: " + e.getMessage());
            }
        });
    }

}