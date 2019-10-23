package com.br.CadasPessoa;
 
import android.content.Context;
 
public class RepositorioPessoaScript extends RepositorioPessoa {
 

    private static final String SCRIPT_DATABASE_DELETE = "DROP TABLE IF EXISTS pessoa";
 
                  //Criando nossa Tabela Pessoa
    
    private static final String[] SCRIPT_DATABASE_CREATE = new String[] {
        "create table pessoa ( _id integer primary key autoincrement, nome text not null, cpf text not null,idade text not null);",
        "insert into pessoa(nome,cpf,idade) values('Tatiane Silva','8004848473993930',26);",
        "insert into pessoa(nome,cpf,idade) values('Daniel','90403887468',29);",
        "insert into pessoa(nome,cpf,idade) values('Cleber','07474848391',35);" };
 

    private static final String NOME_BANCO = "baco_dados";
 

    private static final int VERSAO_BANCO = 1;
 
  
    public static final String TABELA_PESSOA = "pessoa";
 
    private SQLiteHelper dbHelper;
 
    public RepositorioPessoaScript(Context ctx) {
        dbHelper = new SQLiteHelper(ctx, RepositorioPessoaScript.NOME_BANCO, RepositorioPessoaScript.VERSAO_BANCO, RepositorioPessoaScript.SCRIPT_DATABASE_CREATE, RepositorioPessoaScript.SCRIPT_DATABASE_DELETE);
 
        db = dbHelper.getWritableDatabase();
    }
 
 
    @Override
    public void fechar() {
        super.fechar();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
