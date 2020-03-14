package br.com.senai;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    // declaração e criação dos objetos
    Button bt_criarBanco, bt_cadastrarUsuario, bt_consultar, bt_alterarDados, bt_excluirDados;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ligação com a classe R que faz a comunicação com o arquivo Java
        bt_criarBanco = (Button) findViewById(R.id.btnCriarBanco_principal);
        bt_cadastrarUsuario = (Button) findViewById(R.id.btnCadastrar_principal);
        bt_consultar = (Button) findViewById(R.id.btnConsultar_principal);
        bt_alterarDados = (Button) findViewById(R.id.btnAlterar_principal);
        bt_excluirDados = (Button) findViewById(R.id.btnExcluir_principal);

        //código do botão criar banco
        bt_criarBanco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try { //tratamento de erros
                    //comando para criar o banco de dados
                    db = openOrCreateDatabase("bancoDeDados", Context.MODE_PRIVATE, null);
                    // comando para criar a tabela no banco de dados
                    db.execSQL("create table if not exists usuarios(id integer primary key autoincrement, nome text not null, telefone text not null, email text not null)");
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("Aviso");
                    dialog.setMessage("Banco de dados criado com sucesso");
                    dialog.setNeutralButton("Ok", null);
                    dialog.show();
                } catch (Exception e) {
                }
            }
        });

        bt_cadastrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent cadastrar_usuario = new Intent(MainActivity.this, Cadastrar_UsuarioActivity.class);
                startActivity(cadastrar_usuario);
            }
        });

        bt_consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent consultarusuario = new Intent(MainActivity.this, Consultar_usuariosActivity.class);
                startActivity(consultarusuario);
            }
        });
    }
}

