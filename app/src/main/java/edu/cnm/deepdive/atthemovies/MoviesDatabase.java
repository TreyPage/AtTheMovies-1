package edu.cnm.deepdive.atthemovies;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import edu.cnm.deepdive.atthemovies.model.Actor;
import edu.cnm.deepdive.atthemovies.model.ActorMovieJoin;
import edu.cnm.deepdive.atthemovies.model.Movie;
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
      INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
          MoviesDatabase.class, "movies_room").fallbackToDestructiveMigration().build();
    }
    return INSTANCE;
  }

}
