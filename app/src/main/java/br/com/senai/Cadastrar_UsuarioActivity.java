package br.com.senai;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Cadastrar_UsuarioActivity extends AppCompatActivity {
    EditText edt_nome,edt_email,edt_telefone;
     Button bt_cadastrar;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_usuarios);
        edt_nome = (EditText) findViewById(R.id.txt_name_cadastro);
        edt_email = (EditText) findViewById(R.id.edt_e_mail_cadastro);
        edt_telefone = (EditText) findViewById(R.id.txt_phone_cadastro);
        bt_cadastrar = (Button) findViewById(R.id.btn_cadastrar_cadastro);

        try {
            db = openOrCreateDatabase("bancoDeDados",Context.MODE_PRIVATE, null);

        } catch (Exception e) {
            MostraMensagem("Erro: " + e.toString());
        }
        bt_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String nome = edt_nome.getText().toString();
                String telefone = edt_telefone.getText().toString();
                String email = edt_email.getText().toString();
                try {
                    db.execSQL("insert into usuarios(nome,telefone,email) values('" + nome + "','" + telefone + "','" + email + "')");
                    MostraMensagem("Dados Cadastrados com sucesso");
                } catch (Exception e) {
                    MostraMensagem("Erro: " + e.toString());
                }
            }
        });
    }

    public void MostraMensagem(String msg) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(Cadastrar_UsuarioActivity.this);
        dialogo.setTitle("Aviso");
        dialogo.setMessage(msg);
        dialogo.setNeutralButton("Ok",null);
        dialogo.show();
    }
}
