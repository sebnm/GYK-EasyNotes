package com.erenutku.easynotes.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.erenutku.easynotes.R;
import com.erenutku.easynotes.model.NoteModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NoteDetailsActivity extends AppCompatActivity {

    private EditText etTitle, etBody;
    private Button btnSave;
    private DatabaseReference mDatabaseRoot;
    private DatabaseReference mDatabaseNotes;
    private String mKey;
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_BODY = "extra_body";
    public static final String EXTRA_KEY = "extra_key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        mDatabaseRoot = FirebaseDatabase.getInstance().getReference();
        mDatabaseNotes = mDatabaseRoot.child("notes");
        initViews();
        if (getIntent() != null && getIntent().hasExtra(EXTRA_TITLE) && getIntent().hasExtra(EXTRA_BODY)) {
            String title = getIntent().getStringExtra(EXTRA_TITLE);
            etTitle.setText(title);
            String body = getIntent().getStringExtra(EXTRA_BODY);
            etBody.setText(body);
            btnSave.setText("Düzenle");
            mKey = getIntent().getStringExtra(EXTRA_KEY);
        }
    }

    private void initViews() {
        etTitle = (EditText) findViewById(R.id.etTitle);
        etBody = (EditText) findViewById(R.id.etBody);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //NoteModel noteModel = new NoteModel(etTitle.getText().toString(),etBody.getText().toString(),false);
                if (mKey == null) {
                    String key = mDatabaseNotes.push().getKey();
                    mDatabaseNotes.child(key).setValue(
                            new NoteModel(etTitle.getText().toString(),
                                    etBody.getText().toString(), false, key));
                }else {
                    mDatabaseNotes.child(mKey).setValue(
                            new NoteModel(etTitle.getText().toString(),
                                    etBody.getText().toString(), false, mKey));
                }
                // mDatabaseNotes.push().setValue(noteModel);
                finish();
            }
        });
    }
}
