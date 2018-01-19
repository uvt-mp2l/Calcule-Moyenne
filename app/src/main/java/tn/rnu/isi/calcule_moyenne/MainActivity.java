package tn.rnu.isi.calcule_moyenne;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import tn.rnu.isi.calcule_moyenne.adapter.MatiereListAdapter;
import tn.rnu.isi.calcule_moyenne.db.SQLiteDB;
import tn.rnu.isi.calcule_moyenne.listener.RecyclerItemClickListener;
import tn.rnu.isi.calcule_moyenne.model.Matiere;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerItemClickListener {

    private RecyclerView lvMatiere;
    private FloatingActionButton btnAdd;

    private MatiereListAdapter matiereListAdapter;
    private LinearLayoutManager linearLayoutManager;

    private SQLiteDB sqLiteDB;
    private List<Matiere> matiereList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMatiere = (RecyclerView) findViewById(R.id.lvMatiere);
        btnAdd = (FloatingActionButton) findViewById(R.id.add);

        linearLayoutManager = new LinearLayoutManager(this);
        matiereListAdapter = new MatiereListAdapter(this);
        matiereListAdapter.setOnItemClickListener(this);

        lvMatiere.setLayoutManager(linearLayoutManager);
        lvMatiere.setAdapter(matiereListAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActActivity.start(MainActivity.this);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    void loadData(){
        sqLiteDB = new SQLiteDB(this);

        matiereList = new ArrayList<>();

        Cursor cursor = sqLiteDB.retrieve();
        Matiere matiere;

        if (cursor.moveToFirst()) {
            do {

                matiere = new Matiere();

                matiere.setId(cursor.getInt(0));
                matiere.setName(cursor.getString(1));
                matiere.setDs_note(cursor.getString(2));
                matiere.setDs_coef(cursor.getString(3));
                matiere.setTp_note(cursor.getString(4));
                matiere.setTp_coef(cursor.getString(5));
                matiere.setExm_note(cursor.getString(6));
                matiere.setExm_coef(cursor.getString(7));

                matiereList.add(matiere);
            }while (cursor.moveToNext());
        }

        matiereListAdapter.clear();
        matiereListAdapter.addAll(matiereList);
    }

    @Override
    public void onItemClick(int position, View view) {
        Intent i = new Intent(getApplicationContext(),ActEditActivity.class);
        i.putExtra("selected",matiereList.get(position));
        startActivity(i);
    }
}
