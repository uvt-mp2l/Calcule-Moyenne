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
import android.widget.Toast;

import java.math.BigDecimal;

import tn.rnu.isi.calcule_moyenne.db.SQLiteDB;
import tn.rnu.isi.calcule_moyenne.model.Matiere;

/**
 * Created by Hakim .
 */

public class ActActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText matiereName;
    private EditText dsCoef;
    private EditText tpCoef;
    private EditText exmCoef;

    private Button btnAdd;

    private SQLiteDB sqLiteDB;
    private Matiere matiere;

    private AlertDialog.Builder build;

    public static void start(Context context){
        Intent intent = new Intent(context, ActActivity.class);
        context.startActivity(intent);
    }

    public static void start(Context context, Matiere matiere){
        Intent intent = new Intent(context, ActActivity.class);
        intent.putExtra(ActActivity.class.getSimpleName(), matiere);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act);

        matiereName = (EditText) findViewById(R.id.matiereText);
        dsCoef = (EditText) findViewById(R.id.dsCoefText);
        tpCoef = (EditText) findViewById(R.id.tpCoefText);
        exmCoef = (EditText) findViewById(R.id.exmCoefText);

        btnAdd = (Button) findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(this);

        sqLiteDB = new SQLiteDB(this);
    }

    @Override
    public void onClick(View v) {
        if(matiereName.getText().length() > 0 && dsCoef.getText().length() > 0 && tpCoef.getText().length() > 0 && exmCoef.getText().length() > 0){
            if(((new BigDecimal(dsCoef.getText().toString())).doubleValue() + (new BigDecimal(tpCoef.getText().toString())).doubleValue() + (new BigDecimal(exmCoef.getText().toString())).doubleValue() ) > 1 || ((new BigDecimal(dsCoef.getText().toString())).doubleValue() + (new BigDecimal(tpCoef.getText().toString())).doubleValue() + (new BigDecimal(exmCoef.getText().toString())).doubleValue() ) < 0)
                showAlert("Veuillez verifier les coeficiens!");
            else {
                matiere = new Matiere();
                matiere.setName(matiereName.getText().toString());
                matiere.setDs_coef(dsCoef.getText().toString());
                matiere.setTp_coef(tpCoef.getText().toString());
                matiere.setExm_coef(exmCoef.getText().toString());
                sqLiteDB.create(matiere);

                Toast.makeText(this, "Inserted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }else{
            showAlert("Veuillez remplire toutes les champs!");
        }
    }

    private void showAlert(String msg) {
        build = new AlertDialog.Builder(ActActivity.this);
        build.setMessage(msg);

        AlertDialog alert = build.create();
        alert.setTitle("Attention");
        alert.show();

    }
}
