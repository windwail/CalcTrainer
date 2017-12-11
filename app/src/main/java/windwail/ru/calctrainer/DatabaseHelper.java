package windwail.ru.calctrainer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="orders.db";
    private static final int SCHEMA=1;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE quiz (" +
                "ROWID INTEGER NOT NULL," +
                "x INT, " +
                "y INT, " +
                "PRIMARY KEY(ROWID));"
        );


        db.execSQL("CREATE TABLE answer (" +
                "correct INT, " +
                "dstart INT, " +
                "dend INT," +
                "dtime INT, " +
                "quiz_id INT, " +
                "FOREIGN KEY(quiz_id) REFERENCES quiz(ROWID));"
        );

        for ( int x = 2; x < 100; x++) {
            for (int y = 2; y < 100; y++) {

                Object[] values = null;
                values = new Object[] {
                        x,
                        y
                };

                db.execSQL("insert into quiz (x,y) VALUES (?,?)", values);
            }
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS answer");
        db.execSQL("DROP TABLE IF EXISTS quiz");
        onCreate(db);
    }
}