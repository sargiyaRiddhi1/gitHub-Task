package com.example.riddhi.githubassignmentloktra;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by riddhi on 10/6/16.
 */
public class CommitsAdapter extends RecyclerView.Adapter {
   private JsonArray recentCommitsJsonArray;
    private Context mContext;

    public CommitsAdapter(Context context, JsonArray result) {
        mContext=context;
        recentCommitsJsonArray=result;
    }

    public class RecentCommitsViewHolder extends RecyclerView.ViewHolder{
      ImageView committerImageView;
        TextView committerNameTextView;
        TextView commitMessageTextView;
        TextView commitTimeTextView;

        public RecentCommitsViewHolder(View itemView) {
            super(itemView);
            committerImageView=(ImageView) itemView.findViewById(R.id.committerImageView);
            committerNameTextView=(TextView)itemView.findViewById(R.id.committerNameTextView);
            commitMessageTextView=(TextView)itemView.findViewById(R.id.commitMessageTextView);
            commitTimeTextView=(TextView)itemView.findViewById(R.id.timeCommittedTextView);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout
                .committer_cardview_layout, parent, false);
        RecentCommitsViewHolder recentCommitsViewHolder = new RecentCommitsViewHolder(v);
        return recentCommitsViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecentCommitsViewHolder recentCommitsViewHolder=(RecentCommitsViewHolder)holder;

        JsonObject commitInfoJsonObj= (JsonObject) recentCommitsJsonArray.get(position);
        JsonObject committerInfoJsonObject=(JsonObject)commitInfoJsonObj.get("commit");
        JsonObject committerNameJsonObject=(JsonObject)committerInfoJsonObject.get("author");

        recentCommitsViewHolder.committerNameTextView.setText(committerNameJsonObject.get("name").getAsString());
        recentCommitsViewHolder.commitMessageTextView.setText(committerInfoJsonObject.get("message").getAsString());
        JsonObject committerUserNameAndImageJsonObj=(JsonObject)commitInfoJsonObj.get("author");
        Ion.with(recentCommitsViewHolder.committerImageView)
                .load((committerUserNameAndImageJsonObj.get("avatar_url").getAsString()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(committerNameJsonObject.get("date").getAsString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
        recentCommitsViewHolder.commitTimeTextView.setText(committerUserNameAndImageJsonObj.get("login").getAsString()+" committed on "+ DateUtils.getRelativeTimeSpanString(mContext, parsedDate.getTime()));
    }

    @Override
    public int getItemCount() {
        return recentCommitsJsonArray.size();
    }
}
