package br.com.senai;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Alterar_Dados_Activity extends AppCompatActivity {
    EditText edt_nome, edt_email, edt_telefone;
    TextView txt_status_alterar;
    Button bt_alterar;
    SQLiteDatabase db;
    ImageView img_primReg_alt, img_regAnt_alt, img_regAtual_alt, img_proxReg_alt, img_ultimoReg_alt;
    int indice;
    int numregistro;
    Cursor c;
    DialogInterface.OnClickListener diAlteraInformacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alterar_dados);
        //aqui vai o link dos componentes com a classe R
        edt_nome = (EditText) findViewById(R.id.txt_name_alterar);
        edt_email = (EditText) findViewById(R.id.edt_email_alterar);
        edt_telefone = (EditText) findViewById(R.id.edt_phone_alterar);
        bt_alterar = (Button) findViewById(R.id.btn_alterar);
        txt_status_alterar = (TextView) findViewById(R.id.txt_status_alterar);
        img_primReg_alt = findViewById(R.id.img_capAnterior_alterar);
        img_regAnt_alt = findViewById(R.id.img_voltar_consulta);
        img_proxReg_alt = findViewById(R.id.img_avancar_alterar);
        img_ultimoReg_alt = findViewById(R.id.img_proximoCap_alterar);

        try {
            //Abre o banco de dados
            db = openOrCreateDatabase("banco_dados",
                    Context.MODE_PRIVATE, null);
            c = db.query("usuarios", new String[]{"numreg", "nome", "telefone", "email"}, null, null, null, null, null);

            if (c.getCount() > 0) {
                //Move para o primeiro registro
                c.moveToFirst();
                indice = 1;
                numregistro = c.getInt(0); //Obtem o número de registro
                edt_nome.setText(c.getString(1));//Obtem o nome
                edt_telefone.setText(c.getString(2));//Obtem o telefone
                edt_email.setText(c.getString(3));//Obtem o e-mail
                txt_status_alterar.setText(indice + " / " + c.getCount());
            } else {
                txt_status_alterar.setText("Não existe nenhum registro");
            }
        } catch (Exception e) {
        }

        //Move para o primeiro registro
        img_primReg_alt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    indice = 1;
                    //Obtem o número de registro
                    numregistro = c.getInt(0);
                    //Obtem o nome
                    edt_nome.setText(c.getString(1));
                    //Obtém o telefone
                    edt_telefone.setText(c.getString(2));
                    //Obtém o e-mail
                    edt_email.setText(c.getString(3));
                    txt_status_alterar.setText(indice + " / " +
                            c.getCount());
                }
            }
        });

        //Move para o registro anterior
        img_regAnt_alt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (c.getCount() > 0) {
                    if (indice > 1) {
                        indice--;
                        //Move para o registro anterior
                        c.moveToPrevious();
                        numregistro = c.getInt(0);
                        //Obtem o número de registro
                        edt_nome.setText(c.getString(1));
                        //Obtem o nome
                        edt_telefone.setText(c.getString(2));
                        //Obtém o telefone
                        edt_telefone.setText(c.getString(3));
                        //Obtém o e-mail
                        txt_status_alterar.setText(indice + " / " + c.getCount());
                    }
                }
            }
        });

        //Move para o proximo registro
        img_proxReg_alt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (c.getCount() > 0) {
                    if (indice != c.getCount()) {
                        indice++;
                        c.moveToNext();
                        numregistro = c.getInt(0);
                        //Obtem o número de registro
                        edt_nome.setText(c.getString(1));
                        //Obtem o nome
                        edt_telefone.setText(c.getString(2));
                        //Obtém o telefone
                        edt_email.setText(c.getString(3));
                        //Obtém o e-mail
                        txt_status_alterar.setText(indice + " / " +
                                c.getCount());
                    }
                }
            }
        });

        //Move para o último registro
        img_ultimoReg_alt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c.getCount() > 0) {
                    c.moveToLast();
                    indice = c.getCount();
                    numregistro = c.getInt(0);
                    //Obtem o número de registro
                    edt_nome.setText(c.getString(1));
                    //Obtem o nome
                    edt_telefone.setText(c.getString(2));
                    //Obtém o telefone
                    edt_email.setText(c.getString(3));
                    //Obtém o e-mail
                    txt_status_alterar.setText(indice + " / " +
                            c.getCount());
                }
            }
        });

        diAlteraInformacoes = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//Altera as informações do registro na tabela
                String nome = edt_nome.getText().toString();
                String telefone = edt_telefone.getText().toString();
                String email = edt_telefone.getText().toString();
                try {
                    db.execSQL("update usuarios set nome = '" + nome + "', " + "telefone = '" + telefone + "', email = '" + email + "' where numreg = " + numregistro);
                    MostraMensagem("Dados alterados com sucesso.");
                } catch (Exception e) {
                    MostraMensagem("Erro: " + e.toString());
                }
            }
        };
        bt_alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogo = new
                        AlertDialog.Builder(Alterar_Dados_Activity.this);
                dialogo.setTitle("Confirma");
                dialogo.setMessage("Deseja alterar as informações");
                dialogo.setNegativeButton("Não", null);
                dialogo.setPositiveButton("Sim", diAlteraInformacoes);
                dialogo.show();
            }
        });
    }

    public void MostraMensagem(String str) {
        AlertDialog.Builder dialogo = new
                AlertDialog.Builder(Alterar_Dados_Activity.this);
        dialogo.setTitle("Aviso");
        dialogo.setMessage(str);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();
    }
}

