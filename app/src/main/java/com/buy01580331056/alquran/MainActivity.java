package com.buy01580331056.alquran;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;


import com.buy01580331056.alquran.adapter.ListProductAdapter;
import com.buy01580331056.alquran.database.DatabaseHelper;
import com.buy01580331056.alquran.model.Product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class MainActivity extends Activity {
    private ListView lvProduct;
    private ListProductAdapter adapter;
    private List<Product> mProductList;
    private DatabaseHelper mDBHelper;

    private SQLiteDatabase ourdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvProduct = (ListView)findViewById(R.id.listview_product);
        mDBHelper = new DatabaseHelper(this);


        //try
      //  File dbFile = getApplicationContext().getDatabasePath("sample.sqlite");
      //  Toast.makeText(this, ""+dbFile, Toast.LENGTH_SHORT).show();

        //Check exists database
        File database = getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        if(false == database.exists()) {
            mDBHelper.getReadableDatabase();
            //Copy db
            if(copyDatabase(this)) {
                Toast.makeText(this, "Copy database succes", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Copy data error", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        //Get product list in db when db exists
        mProductList = mDBHelper.getListProduct();
        //Init adapter
        adapter = new ListProductAdapter(this, mProductList);
        //Set adapter for listview
        lvProduct.setAdapter(adapter);

    }

    private boolean copyDatabase(Context context) {
        try {
            //Open your local db as the input stream
            InputStream myInput = getApplicationContext().getAssets().open("sample.sqlite");
            Toast.makeText(context, ""+myInput, Toast.LENGTH_SHORT).show();

            //Open the empty db as the output stream
            File dbFile = getApplicationContext().getDatabasePath("sample.sqlite");
            Toast.makeText(context, ""+dbFile, Toast.LENGTH_SHORT).show();

            OutputStream myOutput = new FileOutputStream(dbFile);

            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer))>0)
            {
                myOutput.write(buffer, 0, length);
                Log.d("buf", "" + length);
            }

            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();




            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }


    }
}
