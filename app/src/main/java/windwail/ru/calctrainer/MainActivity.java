package windwail.ru.calctrainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private CApplication application;

    private DatabaseHelper db;

    private long start;

    private long end;

    private int quizCount = 0;

    private TextView qText;

    private TextView dTime;

    private Quiz q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qText = findViewById(R.id.qText);

        dTime = findViewById(R.id.dTime);


        application = (CApplication) getApplication();

        application.getDb().getWritableDatabase();

        db = application.getDb();

        quizCount = db.getQuizCount(db.getReadableDatabase(), null);

        setNewQuiz();

        Timer T=new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if(end==0) {
                            dTime.setText("" + ((System.currentTimeMillis() - start) / 1000) + " sec");
                        }
                    }
                });
            }
        }, 1000, 1000);

    }


    private void saveAnswer(boolean correct) {
        Answer ans = new Answer();
        ans.setD1(new Date(start));
        ans.setD2(new Date(end));
        ans.setDtime((int) (end-start));
        ans.setCorrect(correct);
        ans.setQuizId(q.getId());

        db.saveAnswer(db.getWritableDatabase(), ans);
    }

    public void correct(View v) {
        showAnswer(v);
        saveAnswer(true);
        setNewQuiz();
    }

    public void incorrect(View v) {
        showAnswer(v);
        saveAnswer(false);
        setNewQuiz();
    }

    public void showAnswer(View v)  {
        if(end == 0) {
            end = System.currentTimeMillis();
        }
        dTime.setText(""+((end-start)/1000)+" sec");
        qText.setText(""+q.getX()+" x "+q.getY()+ " = "+(q.getX()*q.getY()));
    }

    public void reset(View v) {
        setNewQuiz();
    }

    private void setNewQuiz() {
        end = 0;
        start = System.currentTimeMillis();
        q = getUnansweredQuiz();

        qText.setText(""+q.getX()+" x "+ q.getY());
    }

    private Quiz getUnansweredQuiz() {

        for(int minAns = 0; minAns < 1000; minAns++) {
            for (int x = 0; x < quizCount; x++) {
                Random r = new Random();
                int il = r.nextInt(quizCount);

                int ac = db.getAnswersCount(db.getReadableDatabase(), il);

                if (ac == minAns) {
                    return db.getQuiz(db.getReadableDatabase(), il);
                }
            }
        }

        return null;
    }
}
