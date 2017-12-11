package windwail.ru.calctrainer;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by icetusk on 07.11.17.
 */

public class CApplication extends Application {

    DatabaseHelper db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = new DatabaseHelper(this);
        Stetho.initializeWithDefaults(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        db.close();
    }

    public DatabaseHelper getDb() {
        return db;
    }
}
