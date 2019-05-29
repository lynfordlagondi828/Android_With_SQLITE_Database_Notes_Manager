package com.example.lynford.personal_notes_app.activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.lynford.personal_notes_app.R;
import com.example.lynford.personal_notes_app.adapter.NotesAdapter;
import com.example.lynford.personal_notes_app.helper.DbHandler;
import com.example.lynford.personal_notes_app.model.ModelNotes;
import com.example.lynford.personal_notes_app.pref.MySharedPreferences;
import java.util.ArrayList;
public class MainActivity  extends AppCompatActivity{

   private String username;
   private RecyclerView recyclerView;
   private NotesAdapter adapter;
   private RecyclerView.LayoutManager layoutManager;
   private ArrayList<ModelNotes>list = new ArrayList<ModelNotes>();

   private FloatingActionButton floatingActionButton;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
       final MySharedPreferences mySharedPreferences = new MySharedPreferences(MainActivity.this);
        username = mySharedPreferences.getUser().getUsername();
        setContentView(R.layout.activity_main_page);

        recyclerView = findViewById(R.id.recycler_view);
        floatingActionButton = findViewById(R.id.button_add);


        adapter = new NotesAdapter(this,list);

        //use a linear layout manager
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        fetch_records();


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflater = getLayoutInflater();
                View v = layoutInflater.inflate(R.layout.activity_add_notes,null);

                final EditText editTextTitle = v.findViewById(R.id.input_title);
                final EditText editTextDescription = v.findViewById(R.id.input_description);

                Button btn_save = v.findViewById(R.id.btnSave);

                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String title = editTextTitle.getText().toString();
                        String description = editTextDescription.getText().toString();

                        if (title.equals("")){

                            editTextTitle.setError("Please input title");

                        } else if (description.equals("")){

                            editTextDescription.setError("Please input Description");

                        } else{

                            ModelNotes notes = new ModelNotes();

                            notes.setUsername(username);
                            notes.setTitle(title);
                            notes.setDescription(description);

                            DbHandler dbHandler = new DbHandler(MainActivity.this);
                            dbHandler.add_notes(notes);
                            Toast.makeText(MainActivity.this, ""+title + " added.", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            adapter.notifyDataSetChanged();
                        }
                    }
                });


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(v).setTitle("Add")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                dialog.dismiss();

                            }
                        });

                dialog = builder.create();
                dialog.show();

            }
        });


        recyclerView.addOnItemTouchListener(new NotesAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new NotesAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                String title = list.get(position).getTitle();
                Toast.makeText(MainActivity.this, ""+title, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

                String title = list.get(position).getTitle();
                Toast.makeText(MainActivity.this, ""+title, Toast.LENGTH_SHORT).show();
            }
        }));

    }

    /**
     * fetch records
     */
    private void fetch_records() {

        DbHandler dbHandler = new DbHandler(MainActivity.this);
        ArrayList<ModelNotes>data = dbHandler.getAllNotes(username);

        if (data.size()>0){

            for (int i=0; i<data.size();i++){

                int id = data.get(i).getId();
                String title = data.get(i).getTitle();
                String desc = data.get(i).getDescription();
                String date = data.get(i).getDate_created();

                ModelNotes notes = new ModelNotes();

                notes.setId(id);
                notes.setTitle(title);
                notes.setDescription(desc);
                notes.setDate_created(date);

                list.add(notes);

            }
            adapter.notifyDataSetChanged();


        } else {

            Toast.makeText(this, "No records found.", Toast.LENGTH_SHORT).show();
        }

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
