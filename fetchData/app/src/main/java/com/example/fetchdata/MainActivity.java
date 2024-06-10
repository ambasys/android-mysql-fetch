package com.example.fetchdata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private static final String path="http://10.0.2.2/fetch.php";
ListView lv;
ArrayList<String> holder=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv=findViewById(R.id.lv);
        fetchData();
    }

    private void fetchData() {
        class manageDb extends AsyncTask<String,Void,String>{

            @Override
            protected String doInBackground(String... strings) {
                try{
                    URL url=new URL(strings[0]);
                    HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String data="";
                    String line;
                    while((line=br.readLine())!=null){
                        data+=line;
                    }
                    br.close();
                    return data;

                }catch(Exception e){
                    return e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String s) {
                try{
                    JSONArray ja=new JSONArray(s);
                    JSONObject jo=null;
                    for(int i=0;i<ja.length();i++){
                        jo=ja.getJSONObject(i);
                        String id=jo.getString("id");
                        String username=jo.getString("username");
                        String password=jo.getString("password");
                        holder.add(id+" "+username+" "+password);
                        ArrayAdapter<String> at=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,holder);
                        lv.setAdapter(at);

                    }
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                }
            }
        }
        manageDb db=new manageDb();
        db.execute(path);
    }
}