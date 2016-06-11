package com.example.riddhi.githubassignmentloktra;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import static com.example.riddhi.githubassignmentloktra.R.*;

public class RecentCommitsActivity extends AppCompatActivity {

    LinearLayout  searchBarLayout;
    JsonArray filteredJsonArray=new JsonArray();
    JsonArray commitHistoryJsonArray=new JsonArray();
    TextView searchButton;
    EditText searchQueryEditText;
    CommitsAdapter commitsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_recent_commits);
         searchBarLayout= (LinearLayout)findViewById(R.id.searchBarLayout);
         searchButton=(TextView)findViewById(id.searchButton);
         searchQueryEditText=(EditText)findViewById(id.searchQueryEditText);
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
                        commitHistoryJsonArray=result;
                        filteredJsonArray=result;

                        commitsAdapter=new CommitsAdapter(RecentCommitsActivity.this,filteredJsonArray);
                        commitsRecyclerView.setAdapter(commitsAdapter);

                    }
                });
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!searchQueryEditText.getText().toString().isEmpty()){
                    for(int i=0;i<commitHistoryJsonArray.size();i++){
                        JsonObject commitInfoJsonObj= (JsonObject) commitHistoryJsonArray.get(i);
                        JsonObject committerInfoJsonObject=(JsonObject)commitInfoJsonObj.get("commit");
                         String  commitMessage=(String) committerInfoJsonObject.get("message").getAsString();
                        if(!commitMessage.contains(searchQueryEditText.getText().toString())){
                            filteredJsonArray.remove(i);
                        }

                    }
                   commitsAdapter.notifyDataSetChanged();

                }

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_search) {
            if(searchBarLayout.getVisibility()==View.GONE) {
                searchBarLayout.setVisibility(View.VISIBLE);
            }
            else {
                searchBarLayout.setVisibility(View.GONE);
            }

        }
        return super.onOptionsItemSelected(item);
    }



}
