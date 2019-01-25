package com.example.user.lr3_sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Database db;
    SQLiteDatabase dbInstance;
    Button button;
    TextView textView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new Database(this);
        dbInstance = db.getWritableDatabase();

        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);

        dbInstance.delete("cars", null, null);

        Cursor cursor = dbInstance.query("cars", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int textIndex = cursor.getColumnIndex("model");
            do {
                String text = cursor.getString(textIndex);
                textView.append("\n" + text);
            } while (cursor.moveToNext());
        }
        cursor.close();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                ContentValues values = new ContentValues();
                values.put("text", text);
                dbInstance.insert("cars", null, values);
                textView.append("\n" + text);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}

