package edu.cnm.deepdive.atthemovies.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import edu.cnm.deepdive.atthemovies.MoviesDatabase;
import edu.cnm.deepdive.atthemovies.model.Actor;
import edu.cnm.deepdive.atthemovies.model.ActorMovieJoin;
import java.util.List;

public class ActorsViewModel extends AndroidViewModel {

    private LiveData<List<Actor>> actors;

    public ActorsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Actor>> getActors(long movieId) {
        actors = MoviesDatabase.getInstance(getApplication()).actorMovieJoinDao()
                .getActorsForMovie(movieId);
        return actors;
    }

    public void addNewActor(final Long movieId, final Actor newActor) {
        new Thread(() -> {
            long actorId = MoviesDatabase.getInstance(getApplication()).actorDao().insert(newActor);
            ActorMovieJoin actorMovieJoin = new ActorMovieJoin();
            actorMovieJoin.setActorId(actorId);
            actorMovieJoin.setMovieId(movieId);
            MoviesDatabase.getInstance(getApplication()).actorMovieJoinDao().insert(actorMovieJoin);
        }).start();
    }
}
