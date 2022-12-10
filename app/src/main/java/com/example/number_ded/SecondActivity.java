package com.example.number_ded;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.number_ded.Adapter.RecyclerAdapter;
import com.example.number_ded.Model.Tests;
import com.example.number_ded.databinding.ActivitySecondBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    ActivitySecondBinding binding;
    RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Tests> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setTitle("Second Activity");
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList =new ArrayList<>();
        getData();
    }

    private void getData() {
        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonArrayObject = new JsonObjectRequest(Request.Method.GET, "https://dl.dropboxusercontent.com/s/qioibzcb5dlf7il/number_test.txt", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject1 = new JSONObject(response.toString());
                    JSONArray jsonArray = jsonObject1.getJSONArray("result");
                    Log.d("Json response", "onResponse: " + jsonObject1.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.d("my-api", "==== " + jsonObject.getString("id"));
                        Log.d("my-api", "==== " + jsonObject.getString("name"));
                        Log.d("my-api", "==== " + jsonObject.getString("number"));
                        Log.d("my-api", "==== " + jsonObject.getString("image"));
                        arrayList.add(new Tests(jsonObject.getString("name"),jsonObject.getString("number"),jsonObject.getString("image")));
//                        arrayList.add(new DBHelper(Integer.parseInt(jsonObject.getString("userId")),Integer.parseInt(jsonObject.getString("id")),jsonObject.getString("title")));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mAdapter = new RecyclerAdapter(arrayList);
                recyclerView.setAdapter(mAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("my-api", "went Wrong" + error);
            }
        });
        requestQueue.add(jsonArrayObject);
    }
}