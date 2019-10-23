package com.br.CadasPessoa;
 
import android.content.Context;
 
public class RepositorioPessoaScript extends RepositorioPessoa {
 

    private static final String SCRIPT_DATABASE_DELETE = "DROP TABLE IF EXISTS pessoa";
 

    private static final String[] SCRIPT_DATABASE_CREATE = new String[] {
        "create table pessoa ( _id integer primary key autoincrement, nome text not null, cpf text not null,idade text not null);",
        "insert into pessoa(nome,cpf,idade) values('Jeferson Zonta','123412332332',21);",
        "insert into pessoa(nome,cpf,idade) values('Ambrozio silva','56784564564',60);",
        "insert into pessoa(nome,cpf,idade) values('Edinando A.','5465631565',19);" };
 

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
