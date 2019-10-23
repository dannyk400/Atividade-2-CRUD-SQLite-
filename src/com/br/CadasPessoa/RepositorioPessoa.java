package com.br.CadasPessoa;

import java.util.ArrayList;
import java.util.List;

import com.br.CadasPessoa.Pessoa.Pessoas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class RepositorioPessoa {

   private static final String CATEGORIA = "dados";


   private static final String NOME_BANCO = "dados_android";

   public static final String NOME_TABELA = "pessoa";

   protected SQLiteDatabase db;

   public RepositorioPessoa(Context ctx) {

       db = ctx.openOrCreateDatabase(NOME_BANCO, Context.MODE_PRIVATE, null);
   }

   protected RepositorioPessoa() {

   }

 
   public long salvar(Pessoa pessoa) {
       long id = pessoa.id;

       if (id != 0) {
           atualizar(pessoa);
       } else {
          
           id = inserir(pessoa);
       }

       return id;
   }


   public long inserir(Pessoa pessoa) {
       ContentValues values = new ContentValues();
       values.put(Pessoas.NOME, pessoa.nome);
       values.put(Pessoas.CPF, pessoa.cpf);
       values.put(Pessoas.IDADE, pessoa.idade);

       long id = inserir(values);
       return id;
   }


   public long inserir(ContentValues valores) {
       long id = db.insert(NOME_TABELA, "", valores);
       return id;
   }


   public int atualizar(Pessoa pessoa) {
       ContentValues values = new ContentValues();
       values.put(Pessoas.NOME, pessoa.nome);
       values.put(Pessoas.CPF, pessoa.cpf);
       values.put(Pessoas.IDADE, pessoa.idade);

       String _id = String.valueOf(pessoa.id);

       String where = Pessoas._ID + "=?";
       String[] whereArgs = new String[] { _id };

       int count = atualizar(values, where, whereArgs);

       return count;
   }

   
   public int atualizar(ContentValues valores, String where, String[] whereArgs) {
       int count = db.update(NOME_TABELA, valores, where, whereArgs);
       Log.i(CATEGORIA, "Atualizou [" + count + "] registros");
       return count;
   }


   public int deletar(long id) {
       String where = Pessoas._ID + "=?";

       String _id = String.valueOf(id);
       String[] whereArgs = new String[] { _id };

       int count = deletar(where, whereArgs);

       return count;
   }


   public int deletar(String where, String[] whereArgs) {
       int count = db.delete(NOME_TABELA, where, whereArgs);
       Log.i(CATEGORIA, "Deletou [" + count + "] registros");
       return count;
   }

   public Pessoa buscarPessoa(long id) {
      
       Cursor c = db.query(true, NOME_TABELA, Pessoa.colunas, Pessoas._ID + "=" + id, 
       null, null, null, null, null);

       if (c.getCount() > 0) {

          
           c.moveToFirst();

           Pessoa pessoa = new Pessoa();

           pessoa.id = c.getLong(0);
           pessoa.nome = c.getString(1);
           pessoa.cpf = c.getString(2);
           pessoa.idade = c.getInt(3);

           return pessoa;
       }

       return null;
   }

  
   public Cursor getCursor() {
       try {
          
           return db.query(NOME_TABELA, Pessoa.colunas, 
           null, null, null, null, null, null);
       } catch (SQLException e) {
           Log.e(CATEGORIA, "Erro ao buscar as pessoas: " + e.toString());
           return null;
       }
   }

   
   public List<Pessoa> listarPessoas() {
       Cursor c = getCursor();

       List<Pessoa> pessoas = new ArrayList<Pessoa>();

       if (c.moveToFirst()) {

         
           int idxId = c.getColumnIndex(Pessoas._ID);
           int idxNome = c.getColumnIndex(Pessoas.NOME);
           int idxCpf = c.getColumnIndex(Pessoas.CPF);
           int idxidade = c.getColumnIndex(Pessoas.IDADE);

         
           do {
               Pessoa pessoa = new Pessoa();
               pessoas.add(pessoa);

               
               pessoa.id = c.getLong(idxId);
               pessoa.nome = c.getString(idxNome);
               pessoa.cpf = c.getString(idxCpf);
               pessoa.idade = c.getInt(idxidade);

           } while (c.moveToNext());
       }

       return pessoas;
   }

   public Pessoa buscarPessoaPorNome(String nome) {
       Pessoa pessoa = null;

       try {
           Cursor c = db.query(NOME_TABELA, Pessoa.colunas, Pessoas.NOME + "='"
           + nome + "'", null, null, null, null);

           if (c.moveToNext()) {

               pessoa = new Pessoa();

               
               pessoa.id = c.getLong(0);
               pessoa.nome = c.getString(1);
               pessoa.cpf = c.getString(2);
               pessoa.idade = c.getInt(3);
           }
       } catch (SQLException e) {
           Log.e(CATEGORIA, "Erro ao buscar a pessoa pelo nome: "
           + e.toString());
           return null;
       }

       return pessoa;
   }

 
   public Cursor query(SQLiteQueryBuilder queryBuilder, String[] projection, 
   String selection, String[] selectionArgs,
           String groupBy, String having, String orderBy) {
       Cursor c = queryBuilder.query(this.db, projection, selection, 
       selectionArgs, groupBy, having, orderBy);
       return c;
   }

  
   public void fechar() {
     
       if (db != null) {
           db.close();
       }
   }
    
}