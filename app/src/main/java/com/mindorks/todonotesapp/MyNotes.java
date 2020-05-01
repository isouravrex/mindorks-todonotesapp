package com.mindorks.todonotesapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mindorks.todonotesapp.adapter.NoteAdapter;
import com.mindorks.todonotesapp.clicklisteners.ItemClickListener;
import com.mindorks.todonotesapp.model.Notes;

import java.util.ArrayList;

public class MyNotes extends AppCompatActivity {

    String fullname;
    FloatingActionButton fabAddNotes;
//    TextView textViewTitle,textViewDescription;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerViewNotes;
    ArrayList<Notes> notesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);
        setupSharedPreference();
        bindView();
        getIntentData();


        fabAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpDialogbox();

            }
        });

        getSupportActionBar().setTitle(fullname);
    }

    private void setupSharedPreference() {
        sharedPreferences=getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        fullname=intent.getStringExtra(AppConstant.FULL_NAME);
        if(TextUtils.isEmpty(fullname))
        {
            fullname = sharedPreferences.getString(PrefConstant.FULL_NAME,"");
        }
    }

    private void bindView() {
        fabAddNotes=findViewById(R.id.fabAddNotes);
//        textViewTitle=findViewById(R.id.textViewTitle);
//        textViewDescription=findViewById(R.id.textViewDescription);
        recyclerViewNotes=findViewById(R.id.recyclerViewNotes);
    }

    private void setUpDialogbox() {

        View view= LayoutInflater.from(MyNotes.this).inflate(R.layout.add_notes_dialog_layout,null);
        final EditText editTextTitle=view.findViewById(R.id.editTextTitle);
        final EditText editTextDescription=view.findViewById(R.id.editTextDescription);
        Button buttonsubmit=view.findViewById(R.id.buttonSubmit);
        final AlertDialog dialog=new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create();
        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title= editTextTitle.getText().toString();
                String description= editTextDescription.getText().toString();
//                textViewTitle.setText(title);
//                textViewDescription.setText(description);

                if(!TextUtils.isEmpty(title)&&!TextUtils.isEmpty(description)) {


                    Notes notes = new Notes();
                    notes.setTitle(title);
                    notes.setDescription(description);
                    notesList.add(notes);
                }
                else
                {
                    Toast.makeText(MyNotes.this,"Title or Description can't be empty",Toast.LENGTH_SHORT).show();
                }
               setupRecyclerView();
                dialog.hide();

            }
        });
        dialog.show();

    }

    private void setupRecyclerView() {
        ItemClickListener itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(Notes notes) {

                Intent intent = new Intent(MyNotes.this,DetailActivity.class);
                intent.putExtra(AppConstant.TITLE,notes.getTitle());
                intent.putExtra(AppConstant.DESCRIPTION,notes.getDescription());

                startActivity(intent);
            }

        };

        NoteAdapter noteAdapter=new NoteAdapter(notesList,itemClickListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyNotes.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewNotes.setLayoutManager(linearLayoutManager);
        recyclerViewNotes.setAdapter(noteAdapter);
    }
}
