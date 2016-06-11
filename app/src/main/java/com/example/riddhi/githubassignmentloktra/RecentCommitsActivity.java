package com.example.riddhi.githubassignmentloktra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import static com.example.riddhi.githubassignmentloktra.R.*;

public class RecentCommitsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_recent_commits);
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

       final RecyclerView commitsRecyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        commitsRecyclerView.setLayoutManager(linearLayoutManager);

        Ion.with(this)
                .load("https://api.github.com/repos/rails/rails/commits")
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        Log.i("result", result.toString());

                        commitsRecyclerView.setAdapter(new CommitsAdapter(RecentCommitsActivity.this,result));

                    }
                });

    }

}