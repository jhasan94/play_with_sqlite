package com.example.mysqlitedatabasedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MyDatabaseHelper myDatabaseHelper;
    private EditText nameEditText,ageEditText,genderEditText,idEditText;
    private Button addButton , showButton ,updateButton ,deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabaseHelper = new MyDatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = myDatabaseHelper.getWritableDatabase();

        nameEditText = findViewById(R.id.nmaeEditTextId);
        ageEditText = findViewById(R.id.ageEditTextId);
        genderEditText = findViewById(R.id.genderEditTextId);
        addButton = findViewById(R.id.addButtonId);
        showButton = findViewById(R.id.showButtonId);
        updateButton = findViewById(R.id.updateButtonId);
        idEditText = findViewById(R.id.idEditTextId);
        deleteButton = findViewById(R.id.deleteButtonId);


        updateButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        showButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String name = nameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        String gender = genderEditText.getText().toString();
        String id = idEditText.getText().toString();

        if (v.getId()==R.id.addButtonId )
        {
            long rowId=myDatabaseHelper.insertData(name,age,gender);
            if (rowId==-1)
            {
                Toast.makeText(getApplicationContext(),"row is not successfully inserted : ",Toast.LENGTH_LONG).show();

            }
            else
            {
                Toast.makeText(getApplicationContext(),"row "+rowId+" is successfully inserted : ",Toast.LENGTH_LONG).show();
            }
        }
        else if (v.getId()==R.id.showButtonId)
        {
           Cursor result  = myDatabaseHelper.DisplayData();
           if (result.getCount()==0)
           {
               // if there is no data
               showData("error","no data found ");
               return;
           }
           else
           {
               StringBuffer stringBuffer = new StringBuffer();
               while (result.moveToNext())
               {
                   stringBuffer.append("ID : "+result.getString(0)+"\n");
                   stringBuffer.append("NAME : "+result.getString(1)+"\n");
                   stringBuffer.append("AGE : "+result.getString(2)+"\n");
                   stringBuffer.append("GENDER : "+result.getString(3)+"\n\n\n");
               }
               showData("resultSet",stringBuffer.toString());

           }

        }
        else if (v.getId()==R.id.updateButtonId)
        {

           Boolean isUpdated =  myDatabaseHelper.updateData(id,name,age,gender);
           if(isUpdated==true)
           {
               Toast.makeText(getApplicationContext(),"data is updated ",Toast.LENGTH_LONG).show();
           }
           else
           {
               Toast.makeText(getApplicationContext(),"data is not updated ",Toast.LENGTH_LONG).show();

           }

        }
        else if (v.getId()==R.id.deleteButtonId)
        {
            int value = myDatabaseHelper.deletData(id);
            if (value>0)
            {
                Toast.makeText(getApplicationContext(),"data is deleted ",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"data is not deleted ",Toast.LENGTH_LONG).show();
            }

        }
    }

    public void showData(String title,String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();
    }
}
