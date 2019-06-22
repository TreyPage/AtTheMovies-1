package edu.cnm.deepdive.atthemovies.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.atthemovies.R;
import edu.cnm.deepdive.atthemovies.fragment.MoviesFragmentDirections.ActionMoviesFragmentToActorsFragment2;
import edu.cnm.deepdive.atthemovies.model.Movie;
import edu.cnm.deepdive.atthemovies.viewmodel.MoviesViewModel;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {


    private Context context;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_movies, container, false);

        final ListView moviesListView = view.findViewById(R.id.movies_list);

        final MoviesViewModel viewModel = ViewModelProviders.of(getActivity()).get(MoviesViewModel.class);

        viewModel.getMoviesLiveData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movieList) {
                final ArrayAdapter<Movie> adapter = new ArrayAdapter<Movie>(context, android.R.layout.simple_list_item_1,
                        movieList);
                moviesListView.setAdapter(adapter);
                moviesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //This code opens the actor fragment and passes the movie id that was clicked
                        ActionMoviesFragmentToActorsFragment2 action =
                                MoviesFragmentDirections.actionMoviesFragmentToActorsFragment2()
                                        .setMovieId(adapter.getItem(position).getId());
                        Navigation.findNavController(view).navigate(action);
                    }
                });
            }
        });


        final Spinner genreSpinner = view.findViewById(R.id.new_movie_genre);
        ArrayAdapter<Movie.Genre> genreAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, Movie.Genre.values());
        genreSpinner.setAdapter(genreAdapter);

        Button newMovieButton = view.findViewById(R.id.new_movie_button);
        newMovieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newMovieNameEditText = view.findViewById(R.id.new_movie_name);
                EditText newMovieScreenwriter = view.findViewById(R.id.new_movie_screenwriter);
                Movie newMovie = new Movie();
                newMovie.setTitle(newMovieNameEditText.getText().toString());
                newMovie.setScreenwriter(newMovieScreenwriter.getText().toString());
                newMovie.setGenre((Movie.Genre) genreSpinner.getSelectedItem());
                viewModel.addMovie(newMovie);
                newMovieNameEditText.setText("");
            }
        });


        // Inflate the layout for this fragment

        return view;
    }

}
