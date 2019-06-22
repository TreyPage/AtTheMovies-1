package edu.cnm.deepdive.atthemovies.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import edu.cnm.deepdive.atthemovies.model.Actor;
import edu.cnm.deepdive.atthemovies.model.ActorMovieJoin;
import java.util.List;

@Dao
public interface ActorMovieJoinDao {

    @Insert
    void insert(ActorMovieJoin actorMovieJoin);

    @Query("SELECT * FROM actor INNER JOIN actormoviejoin ON " +
            "actor.id = actormoviejoin.actorId WHERE " +
            "actormoviejoin.movieId = :movieId")
    LiveData<List<Actor>> getActorsForMovie(long movieId);

}
