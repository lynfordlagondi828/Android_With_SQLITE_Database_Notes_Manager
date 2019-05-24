package com.example.lynford.personal_notes_app.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lynford.personal_notes_app.R;
import com.example.lynford.personal_notes_app.adapter.NotesAdapter;
import com.example.lynford.personal_notes_app.model.ModelNotes;
import com.example.lynford.personal_notes_app.pref.MySharedPreferences;

import java.util.ArrayList;

public class MainActivity  extends AppCompatActivity{

   private String username;

   private RecyclerView recyclerView;
   private NotesAdapter adapter;
   private RecyclerView.LayoutManager layoutManager;
   private ArrayList<ModelNotes>list = new ArrayList<ModelNotes>();



    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
       final MySharedPreferences mySharedPreferences = new MySharedPreferences(MainActivity.this);
        username = mySharedPreferences.getUser().getUsername();
        setContentView(R.layout.activity_main_page);

        recyclerView = findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        //use a linear layout manager
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new NotesAdapter(this,list);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_logout){

            MySharedPreferences mySharedPreferences = new MySharedPreferences(MainActivity.this);
            mySharedPreferences.clearAll();

            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }
}
