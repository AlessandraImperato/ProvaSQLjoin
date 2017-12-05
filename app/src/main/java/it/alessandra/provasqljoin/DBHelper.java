package it.alessandra.provasqljoin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by utente7.academy on 05/12/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Genitori.db";
    public static final String MOTHER_TABLE_NAMEM = "maternita";
    public static final String FATHER_TABLE_NAMEP = "paternita";
    public static final String MOTHER_COLUMN_IDM = "idM";
    public static final String FATHER_COLUMN_IDP = "idP";
    public static final String MATHER_COLUMN_NAMEM = "madre";
    public static final String FATHER_COLUMN_NAMEP = "padre";
    public static final String BABY_COLUMN_NAME = "figlio";



    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    } //costruttore in cui chiamiamo solo la super

    @Override
    public void onCreate(SQLiteDatabase db) { //descriviamo tutte le caratteristiche della nostra tabella
        db.execSQL(
                "create table maternita " + "(id integer primary key, madre text,figlio text)" //all'inizio scriviamo così
                                                                        //realmente conviene usare i nomi definiti sopra,
                                                                        //CONTACT_COLUMN_ ...
        );
        db.execSQL(
                "create table paternita" + "(id integer primary key, padre text,figlio text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    } //Questo metodo fa la drop della table

    public boolean insertMom (String madre, String figlio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("madre", madre);
        contentValues.put("figlio", figlio);
        db.insert("maternita", null, contentValues);
        return true;
    }

    public boolean insertDad (String padre, String figlio) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("padre", padre);
        contentValues.put("figlio", figlio);
        db.insert("paternita", null, contentValues);
        return true;
    }

    public Cursor getData(int id) { //prende il dato che ha quell'id nel nostro db
        SQLiteDatabase db = this.getReadableDatabase(); //istanza di lettura
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null ); /*rawquery va richiamato ogni volta che vogliamo
                                                                                        fare una query. null è per i paramtri aggiuntivi*/
        return res; //ritorna un oggetto di tipo cursor. contiene tutte le info a partire da quella query
    }

    public int numberOfRowsM(){
        SQLiteDatabase db = this.getReadableDatabase(); //istanza di lettura
        int numRows = (int) DatabaseUtils.queryNumEntries(db, MOTHER_TABLE_NAMEM);
        return numRows;
    }
    public int numberOfRowsP(){
        SQLiteDatabase db = this.getReadableDatabase(); //istanza di lettura
        int numRows = (int) DatabaseUtils.queryNumEntries(db, FATHER_TABLE_NAMEP);
        return numRows;
    }

   /* public boolean updateContact (Integer id, String name, String phone, String email, String street,String place) {
        SQLiteDatabase db = this.getWritableDatabase(); //istanza di scrittura
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }*/

    public Integer deleteContact (Integer id) { //cancella tutta la riga
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts", "id = ? ",new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllBaby() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase(); //istanza di lettura
        Cursor res =  db.rawQuery( "select figlio from maternita", null ); //seleziona tutto dai contatti
        res.moveToFirst(); //muoviti alla prima istanza. il cursore è un iteratore

        while(res.isAfterLast() == false){ //finchè non sono all'ultimo elemento
            array_list.add(res.getString(res.getColumnIndex(BABY_COLUMN_NAME))); /* aggiungo all'arraylist.
                                                                                        (prendo il valore che si trova sotto la
                                                                                        colonna nome => prendo tutti i nomi)
                                                                                        */
            res.moveToNext(); //alla fine vai al successivo
        }
        return array_list; //potremmo ad es passarlo ad una recycler view
    }

    public Cursor join(String nomefiglio) { //prende il dato che ha quell'id nel nostro db
        SQLiteDatabase db = this.getReadableDatabase(); //istanza di lettura
        String query = "select paternita.figlio, padre, madre from maternita, paternita where paternita.figlio = maternita.figlio";
        Cursor res =  db.rawQuery( query, null );
        return res;
    }
}



