package it.alessandra.provasqljoin;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Cursor itemCursor;
    private DBHelper dbh;
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private ArrayList<String> nomiFigli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbh = new DBHelper(this);
        listView = (ListView) findViewById(R.id.listview);
        nomiFigli = new ArrayList<>();

       /* dbh.insertDad("Giuseppe","Francesca");
        dbh.insertDad("Giuseppe","Roberta");
        dbh.insertDad("Alessio","Mario");
        dbh.insertDad("Arturo","Alberto");
        dbh.insertDad("Mirko","Simona");

        dbh.insertMom("Federica","Francesca");
        dbh.insertMom("Federica","Roberta");
        dbh.insertMom("Alessandra","Mario");
        dbh.insertMom("Anna","Alberto");
        dbh.insertMom("Anna", "Simona");

        nomiFigli = dbh.getAllBaby();*/
        arrayAdapter = new ArrayAdapter(this, R.layout.item, R.id.itemlist, nomiFigli);
        listView.setAdapter(arrayAdapter);


    }
}
