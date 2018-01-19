package tn.rnu.isi.calcule_moyenne;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

import tn.rnu.isi.calcule_moyenne.db.SQLiteDB;
import tn.rnu.isi.calcule_moyenne.model.Matiere;

public class ActEditActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView matiereName;
    private TextView dsCoef;
    private TextView tpCoef;
    private TextView exmCoef;
    private EditText dsNote;
    private EditText tpNote;
    private EditText exmNote;
    private TextView moyenne;

    private Button btnEdit, btnDelete;

    private SQLiteDB sqLiteDB;
    private Matiere matiere;

    private AlertDialog.Builder build;

    public static void start(Context context){
        Intent intent = new Intent(context, ActEditActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, Matiere matiere){
        Intent intent = new Intent(context, ActEditActivity.class);
        intent.putExtra(ActEditActivity.class.getSimpleName(), matiere);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_edit);

        matiereName = (TextView) findViewById(R.id.matiereTextS);
        dsCoef = (TextView) findViewById(R.id.dsCoefTextS);
        tpCoef = (TextView) findViewById(R.id.tpCoefTextS);
        exmCoef = (TextView) findViewById(R.id.exmCoefTextS);

        dsNote = (EditText) findViewById(R.id.dsNoteText);
        tpNote = (EditText) findViewById(R.id.tpNoteText);
        exmNote = (EditText) findViewById(R.id.exmNoteText);

        moyenne = (TextView) findViewById(R.id.moyenne);

        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnEdit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        matiere = (Matiere) getIntent().getParcelableExtra("selected");


        double cal_moyenne =  0.0;

        if (matiere.getDs_note().length() > 0 || matiere.getTp_note().length() > 0 || matiere.getExm_note().length() > 0){
            if(matiere.getDs_note().length() > 0){
                cal_moyenne += (new BigDecimal(matiere.getDs_note())).doubleValue() * (new BigDecimal(matiere.getDs_coef())).doubleValue();
            }

            if(matiere.getTp_note().length() > 0){
                cal_moyenne += (new BigDecimal(matiere.getTp_note())).doubleValue() * (new BigDecimal(matiere.getTp_coef())).doubleValue();
            }

            if(matiere.getExm_note().length() > 0){
                cal_moyenne += (new BigDecimal(matiere.getExm_note())).doubleValue() * (new BigDecimal(matiere.getExm_coef())).doubleValue();
            }

        }

        if (matiere.getDs_note().length() > 0 && matiere.getTp_note().length() > 0 && matiere.getExm_note().length() > 0){
            btnEdit.setVisibility(View.GONE);
        }

        matiereName.setText(matiere.getName());
        matiereName.setEnabled(false);
        dsCoef.setText("DS: "+matiere.getDs_coef());
        tpCoef.setText("TP: "+matiere.getTp_coef());
        exmCoef.setText("EXAM: "+matiere.getExm_coef());
        dsNote.setText(""+matiere.getDs_note());
        tpNote.setText(""+matiere.getTp_note());
        exmNote.setText(""+matiere.getExm_note());
        moyenne.setText("La moyenne est: "+cal_moyenne);

        sqLiteDB = new SQLiteDB(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnEdit){
            if (dsNote.getText().toString().length() > 0 || tpNote.getText().toString().length() > 0 || exmNote.getText().toString().length() > 0) {

                double cal_moyenne =  0.0;

                if(dsNote.getText().toString().length() > 0){
                    if ((new BigDecimal(dsNote.getText().toString())).doubleValue() > 20 || (new BigDecimal(dsNote.getText().toString())).doubleValue() < 0){
                        showAlert("Veuillez verifier les notes avant de valider!"); return;
                    }
                    cal_moyenne += (new BigDecimal(dsNote.getText().toString())).doubleValue() * (new BigDecimal(matiere.getDs_coef())).doubleValue();
                }

                if(tpNote.getText().toString().length() > 0){
                    if ((new BigDecimal(tpNote.getText().toString())).doubleValue() > 20 || (new BigDecimal(tpNote.getText().toString())).doubleValue() < 0){
                        showAlert("Veuillez verifier les notes avant de valider!"); return;
                    }
                    cal_moyenne += (new BigDecimal(tpNote.getText().toString())).doubleValue() * (new BigDecimal(matiere.getTp_coef())).doubleValue();
                }

                if(exmNote.getText().toString().length() > 0){
                    if ((new BigDecimal(exmNote.getText().toString())).doubleValue() > 20 || (new BigDecimal(exmNote.getText().toString())).doubleValue() < 0){
                        showAlert("Veuillez verifier les notes avant de valider!"); return;
                    }
                    cal_moyenne += (new BigDecimal(exmNote.getText().toString())).doubleValue() * (new BigDecimal(matiere.getExm_coef())).doubleValue();
                }

                if (cal_moyenne > 20 || cal_moyenne < 0){
                    showAlert("Veuillez verifier les notes avant de valider!"); return;
                }
            }
            matiere.setName(matiereName.getText().toString());
            matiere.setDs_note(dsNote.getText().toString());
            matiere.setTp_note(tpNote.getText().toString());
            matiere.setExm_note(exmNote.getText().toString());
            sqLiteDB.update(matiere);

            Toast.makeText(this, "Edited!", Toast.LENGTH_SHORT).show();
            finish();
        }else if(v == btnDelete){
            sqLiteDB.delete(matiere.getId());

            Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void showAlert(String msg) {
        build = new AlertDialog.Builder(ActEditActivity.this);
        build.setMessage(msg);

        AlertDialog alert = build.create();
        alert.setTitle("Attention");
        alert.show();

    }
}
