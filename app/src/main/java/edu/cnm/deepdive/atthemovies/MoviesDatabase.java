package edu.cnm.deepdive.atthemovies;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import edu.cnm.deepdive.atthemovies.model.Actor;
import edu.cnm.deepdive.atthemovies.model.ActorMovieJoin;
import edu.cnm.deepdive.atthemovies.model.Movie;
import edu.cnm.deepdive.atthemovies.model.Movie.Genre;
import edu.cnm.deepdive.atthemovies.model.dao.ActorDao;
import edu.cnm.deepdive.atthemovies.model.dao.ActorMovieJoinDao;
import edu.cnm.deepdive.atthemovies.model.dao.MovieDao;

@Database(entities = {Movie.class, Actor.class, ActorMovieJoin.class}, version = 2)
public abstract class MoviesDatabase extends RoomDatabase {

  public abstract MovieDao movieDao();

  public abstract ActorDao actorDao();

  public abstract ActorMovieJoinDao actorMovieJoinDao();

  private static MoviesDatabase INSTANCE;

  public static MoviesDatabase getInstance(Context context) {
    if (INSTANCE == null) {
      synchronized (MoviesDatabase.class) {
        if (INSTANCE == null) {
          INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
              MoviesDatabase.class, "movies_room").fallbackToDestructiveMigration().addCallback(
              new Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                  super.onCreate(db);
                  new PopulateDbTask(INSTANCE).execute();
                }
              }).build();
        }
      }
    }
    return INSTANCE;
  }

  private static class PopulateDbTask extends AsyncTask<Void, Void, Void> {

    private final MoviesDatabase db;

    PopulateDbTask(MoviesDatabase db) {
      this.db = db;
    }

    @Override
    protected Void doInBackground(Void... voids) {
      Movie avengers = new Movie();
      avengers.setTitle("Avengers");
      avengers.setGenre(Genre.ACTION);
      avengers.setScreenwriter("Joss Whedon");
      db.movieDao().insert(avengers);
      return null;
    }
  }
}
