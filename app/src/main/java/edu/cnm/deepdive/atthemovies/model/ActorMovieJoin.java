package edu.cnm.deepdive.atthemovies.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(primaryKeys = {"movieId", "actorId"},
    indices = {@Index("movieId"), @Index("actorId")},
    foreignKeys = {
        @ForeignKey(entity = Movie.class,
            parentColumns = "id",
            childColumns = "movieId"),
        @ForeignKey(entity = Actor.class,
            parentColumns = "id",
            childColumns = "actorId")
    })
public class ActorMovieJoin {

  private long movieId;
  private long actorId;
  private String characterName;

  public long getMovieId() {
    return movieId;
  }

  public void setMovieId(long movieId) {
    this.movieId = movieId;
  }

  public long getActorId() {
    return actorId;
  }

  public void setActorId(long actorId) {
    this.actorId = actorId;
  }

  public String getCharacterName() {
    return characterName;
  }

  public void setCharacterName(String characterName) {
    this.characterName = characterName;
  }
}
