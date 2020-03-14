package br.com.senai;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Consultar_usuariosActivity extends AppCompatActivity {
    TextView txt_status_consulta, img_statusConsulta, txt_email;
    EditText txt_nome, txt_telefone;
    ImageView img_primReg_consulta, img_regAnt_consultar, img_regAtual_consultar, img_proxReg_consultar, img_ultimoReg_consultar;
    SQLiteDatabase db;
    int indice;
    Cursor cursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consulta_dados);
        txt_nome = (EditText) findViewById(R.id.edt_name_consulta);
        txt_telefone = (EditText) findViewById(R.id.edt_phone_consulta);
        txt_email = (TextView) findViewById(R.id.edt_emailconsulta);
        img_primReg_consulta = (ImageView) findViewById(R.id.img_capAnterior_consulta);
        img_regAnt_consultar = (ImageView) findViewById(R.id.img_voltar_consulta);
        img_regAtual_consultar = (ImageView) findViewById(R.id.img_play_consulta);
        img_proxReg_consultar = (ImageView) findViewById(R.id.img_avancar_consulta);
        img_ultimoReg_consultar = (ImageView) findViewById(R.id.img_proximoCap_consulta);
        img_statusConsulta = (TextView) findViewById(R.id.txt_status_consulta);
        txt_nome.setText("");
        txt_telefone.setText("");
        txt_email.setText("");

        try {
            db = openOrCreateDatabase("bancoDeDados", Context.MODE_PRIVATE, null);
            cursor = db.query("usuarios", new String[]{"nome", "telefone", "email"}, null, null, null, null, null);
            //verifica se existe algum registro
            if (cursor.getCount() > 0) {
                //move para o primeiro registro
                cursor.moveToFirst();
                indice = 1;
                txt_nome.setText(cursor.getString(0));
                //obtém o nome
                txt_telefone.setText(cursor.getString(1));
                //obtém o telefone
                txt_email.setText(cursor.getString(2));
                //obtém o email
                txt_status_consulta.setText(indice + "/" + cursor.getCount());

            } else {
                txt_status_consulta.setText("Não existe nenhum registro");
            }

            //imagem para o primeiro registro
            img_primReg_consulta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        indice = 1;
                        //obtém o nome
                        txt_nome.setText(cursor.getString(0));
                        //obtém o telefone
                        txt_telefone.setText(cursor.getString(1));
                        //obtém o email
                        txt_email.setText(cursor.getString(2));
                        //obtém o numero do registro
                        txt_status_consulta.setText(indice + "/" + cursor.getCount());
                    }
                }
            });

            // código do botão anterior
            img_regAnt_consultar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cursor.getCount() > 0) {
                        //validação para saber se existe mais de um registro
                        if (indice > 1) {
                            cursor.moveToPrevious();
                            indice--;
                            //obtém o nome
                            txt_nome.setText(cursor.getString(0));
                            //obtém o telefone
                            txt_telefone.setText(cursor.getString(1));
                            //obtém o email
                            txt_email.setText(cursor.getString(2));
                            txt_status_consulta.setText(indice + "/" + cursor.getCount());
                        } else {
                            txt_status_consulta.setText("Impossivél voltar, só existe um registro");
                        }
                    }
                }
            });

            //código do botão próximo
            img_proxReg_consultar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cursor.getCount() > 0) {
                        //validação para saber se não está np último registro
                        if (indice != cursor.getCount()) {
                            cursor.moveToNext();
                            indice++;
                            //obtém o nome
                            txt_nome.setText(cursor.getString(0));
                            //obtém o telefone
                            txt_telefone.setText(cursor.getString(1));
                            //obtém o email
                            txt_email.setText(cursor.getString(2));
                            txt_status_consulta.setText(indice + "/" + cursor.getCount());
                        } else {
                            txt_status_consulta.setText("Impossivél avançar , já está no último registro");
                        }
                    }
                }
            });

            //código para o último registro
            img_ultimoReg_consultar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cursor.getCount() > 0) {
                        cursor.moveToLast();
                        indice = cursor.getCount();
                        //obtém o nome
                        txt_nome.setText(cursor.getString(0));
                        //obtém o telefone
                        txt_telefone.setText(cursor.getString(1));
                        //obtém o email
                        txt_email.setText(cursor.getString(2));
                        txt_status_consulta.setText(indice + "/" + cursor.getCount());
                    } else {
                        txt_status_consulta.setText("Impossivél avançar , já está no último registro");
                    }
                }
            });


        } catch (Exception e) {
            MostraMensagem("Erro" + e.toString());
        }
    }

    public void MostraMensagem(String msg) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(Consultar_usuariosActivity.this);
        dialogo.setTitle("Aviso");
        dialogo.setMessage(msg);
        dialogo.setNeutralButton("Ok", null);
        dialogo.show();
    }
}
