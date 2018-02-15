package it.unive.dais.bunnyteam.unfinitaly.app;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import it.unive.dais.bunnyteam.unfinitaly.app.marker.MapMarker;
import it.unive.dais.bunnyteam.unfinitaly.app.marker.MapMarkerList;
import it.unive.dais.bunnyteam.unfinitaly.app.memory.JsonIO;

public class CommentsActivity extends AppCompatActivity {
    ArrayList<Comment> comments;
    private MapMarker thisMapMarker;
    private Activity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        thisActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        thisMapMarker = (MapMarker)getIntent().getSerializableExtra("MapMarker");
        ((TextView)findViewById(R.id.comments_title_opera)).setText(thisMapMarker.getId());
        findViewById(R.id.buttonInsertComment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((TextView)findViewById(R.id.comments_title_opera)).getText().length()!=0){
                    String commentText =((EditText)findViewById(R.id.commentText)).getEditableText().toString();
                    Log.i("CIAO", commentText);
                    new InsertComments(thisActivity).execute(thisMapMarker.getId(), commentText);
                }
            }
        });
        new RequestComments(this).execute(thisMapMarker.getId());
    }
    class RequestComments extends AsyncTask<String, Void, String> {
        Activity requested;

        public RequestComments(Activity requested) {
            this.requested = requested;
        }

        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... type) {
            StringBuffer htmlCode = new StringBuffer();
            try {
                Log.i("CIAO","Reading comments from URL");
                URL url = new URL("http://unfinitaly.altervista.org/api/get_opera_comments.php?id="+type[0]);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                Log.i("CIAO", "url -> "+url.toString());
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    htmlCode.append(inputLine);
                    //Log.d("CIAO", "html: " + inputLine);
                }
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("CIAO", "Error: " + e.getMessage());
                Log.d("CIAO", "HTML CODE: " + htmlCode);
                Log.i("CIAO", "Errore: non posso ricevere il file!");
            }
            return htmlCode.toString();
        }
        protected void onPostExecute(String result) {
            ArrayList<MapMarker> mmList;
            if(!result.equals("")) {
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Comment>>() {}.getType();
                comments = gson.fromJson(result, type);
                Log.i("CIAO","Readed opere From URL, save to JSON");
                updateComments();
                super.onPostExecute(result);
            }
        }
    }
    class InsertComments extends AsyncTask<String, Void, String> {
        Activity requested;

        public InsertComments(Activity requested) {
            this.requested = requested;
        }

        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... type) {
            StringBuffer htmlCode = new StringBuffer();
            try {
                String urlParameters  = "text="+type[1];
                Log.i("CIAO", urlParameters);
                byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
                int    postDataLength = postData.length;
                String request        = "http://unfinitaly.altervista.org/api/insert_comments.php?id="+type[0];
                URL    url            = new URL( request );
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                conn.setDoOutput( true );
                conn.setInstanceFollowRedirects( false );
                conn.setRequestMethod( "POST" );
                conn.setRequestProperty( "charset", "utf-8");
                conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
                conn.setUseCaches( false );
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                dos.write(postData);
                conn.getInputStream();
                conn.getInputStream();
                conn.getInputStream();
                conn.getInputStream();
                conn.getInputStream();
                conn.getInputStream();


                conn.disconnect();
                //

                /*String reply;
                InputStream in = conn.getInputStream();
                StringBuffer sb = new StringBuffer();
                try {
                    int chr;
                    while ((chr = in.read()) != -1) {
                        sb.append((char) chr);
                    }
                    reply = sb.toString();
                    Log.i("CIAO", "REPLY -> "+reply);
                } finally {
                    in.close();
                }*/


            } catch (Exception e) {
                e.printStackTrace();
                Log.d("CIAO", "Error: " + e.getMessage());
                Log.d("CIAO", "HTML CODE: " + htmlCode);
                Log.i("CIAO", "Errore: non posso ricevere il file!");
            }
            return htmlCode.toString();
        }
        protected void onPostExecute(String result) {
            new RequestComments(thisActivity).execute(thisMapMarker.getId());
                super.onPostExecute(result);
            }
    }

    public void updateComments(){
        Log.i("CIAO", "Update comments");
        TextView comment;
        LinearLayout llay = (LinearLayout)findViewById(R.id.scrollViewLinear);
        llay.removeAllViews();
        for(Comment c : comments){
            comment = new TextView(this);
            comment.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            comment.setText(c.getText());
            llay.addView(comment);
        }
    }
}
