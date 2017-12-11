package windwail.ru.calctrainer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="database.db";
    private static final int SCHEMA=4;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE quiz (" +

                "x INT, " +
                "y INT " +
                ");"
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

    /*
     "correct INT, " +
             "dstart INT, " +
             "dend INT," +
             "dtime INT, " +
             "quiz_id INT, " +

    */

    public void saveAnswer(SQLiteDatabase db, Answer ans) {
        Object[] values = null;
        values = new Object[] {
                ans.getD1().getTime(),
                ans.getD2().getTime(),
                ans.getDtime(),
                ans.getQuizId(),
                ans.isCorrect() ? 1 : 0
        };

        db.execSQL("insert into answer (dstart,dend,dtime,quiz_id,correct) VALUES (?,?,?,?,?)", values);
    }

    public int getQuizCount(SQLiteDatabase db, String article) {
        Cursor result = db.rawQuery("SELECT count(*) " +
                "from quiz ", null);

        while(result.moveToNext()) {
            return result.getInt(0);
        }

        return 0;
    }


    /*
     "correct INT, " +
             "dstart INT, " +
             "dend INT," +
             "dtime INT, " +
             "quiz_id INT, " +

    */

    public int getAnswersCount(SQLiteDatabase db, int id) {
        Cursor result = db.rawQuery("SELECT count(*) " +
                "from answer where quiz_id = ?", new  String[]{""+id});


        while(result.moveToNext()) {
            return result.getInt(0);
        }

        return 0;
    }

    public List<Answer> getAnswers(SQLiteDatabase db, int id) {
        Cursor result = db.rawQuery("SELECT dstart, dend, dtime, correct " +
                "from answer where quiz_id = ?", new  String[]{""+id});

        ArrayList<Answer> list = new ArrayList<>();

        while(result.moveToNext()) {
           Answer ans = new Answer();

           int d1 = result.getInt(0);
           int d2 = result.getInt(1);

           ans.setD1(new Date(d1));
           ans.setD2(new Date(d2));
           ans.setDtime(result.getInt(2));
           ans.setQuizId(id);
           ans.setCorrect(result.getInt(3)>0);

           list.add(ans);


        }

        return list;
    }


    public Quiz getQuiz(SQLiteDatabase db, int id) {
        Cursor result = db.rawQuery("SELECT x,y,rowid " +
                "from quiz where rowid = ?", new  String[]{""+id});

        while(result.moveToNext()) {
            Quiz quiz = new Quiz();

            quiz.setX(result.getInt(0));
            quiz.setY(result.getInt(1));
            quiz.setId(result.getInt(2));

            return quiz;
        }

        return null;
    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS answer");
        db.execSQL("DROP TABLE IF EXISTS quiz");
        onCreate(db);
    }
}