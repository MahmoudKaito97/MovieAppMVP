package com.example.mahmoud.movieapp;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.mahmoud.movieapp.Models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by MAHMOUD on 7/15/2018.
 */



 public class FetchMovieCover extends AsyncTask< String,Void,ArrayList<Movie> > {

    final String MOVIE_BASE_URL ="https://api.themoviedb.org/3/movie/";
    final String MOVIE_OVERVIEW="overview";
    final String MOVIE_RELEASE_DATE = "release_date";
    final String MOVIE_ORIGINAL_TITLE = "original_title";
    final String MOVIE_POSTER_PATH="poster_path";
    final String MOVIE_VOTE_AVERAGE = "vote_average";
    final String API_KEY = "api_key";



    HttpURLConnection urlConnection = null;

    String movieDescJsonStr = null;
    private ArrayList<Movie> listOfAllData;

    public FetchMovieCover(){
        listOfAllData = new ArrayList<>();
    }
    @Override
    protected void onPreExecute() {

        super.onPreExecute();

      //  gridView.setVisibility(View.GONE);
        // Pb.setVisibility(View.VISIBLE);
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {


        try{

            connectToMovieDb(buildUri(params[0]));

            movieDescJsonStr=writeMovieDescription(urlConnection.getInputStream());

          //  Log.d(TAG,movieDescJsonStr);
            if(movieDescJsonStr==null){
                return null;
            }
            listOfAllData= getDataFromJson(movieDescJsonStr);
        }
        catch (IOException e){
           // Log.e(TAG,"IO exception",e);
            //Toast.makeText(getActivity(),"Something went wrong, Please! check your connection",Toast.LENGTH_LONG).show();

        }
        catch (JSONException e){
           // Log.e(TAG,"JSON exception",e);
        }
        finally{
            if(urlConnection !=null)
                urlConnection.disconnect();
        }

        return   listOfAllData;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);

       /* Pb.setVisibility(View.GONE);
        gridView.setVisibility(View.VISIBLE);
        if(listOfAllData.isEmpty()){
            gridView.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }else{
            gridView.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
        }
        imageAdapter.notifyDataSetChanged(movies);

      */
        MoviesPresenter presenter = new MoviesPresenter();
        presenter.setMovies(movies);

    }

    public Uri buildUri(String sortType ){
        Uri uri = Uri.parse(MOVIE_BASE_URL+sortType+"?").buildUpon()
                .appendQueryParameter(API_KEY,BuildConfig.MOVIE_DATABASE_API_KEY).build();
        return uri;
    }
    public void connectToMovieDb(Uri uri) throws IOException{


        URL url = new URL(uri.toString());

        urlConnection =(HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");

        urlConnection.connect();


    }

    public String writeMovieDescription(InputStream inputStream) throws IOException{

        String movieDescJsonStr;
        StringBuffer buffer = new StringBuffer();
        if(inputStream ==null){
            return null;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine())!=null){
            buffer.append(line+'\n');
        }
        if(buffer.length()==0){
            return null ;
        }

        movieDescJsonStr = buffer.toString();

        return movieDescJsonStr;
    }

    public ArrayList<Movie> getDataFromJson(String str) throws JSONException{


        JSONObject movieDescJson = new JSONObject(str);
        JSONArray results = movieDescJson.getJSONArray("results");

        ArrayList<Movie> sd = new ArrayList<>();

        for(int i=0;i<results.length();i++) {
            String posterPath = results.getJSONObject(i).getString(MOVIE_POSTER_PATH); //index = 0
            String originalTitle= results.getJSONObject(i).getString(MOVIE_ORIGINAL_TITLE); //index = 1
            String voteAverage= results.getJSONObject(i).getString(MOVIE_VOTE_AVERAGE); //index = 2
            String releaseDate= results.getJSONObject(i).getString(MOVIE_RELEASE_DATE); //index = 3
            String overview= results.getJSONObject(i).getString(MOVIE_OVERVIEW); //index = 4
            String movieId=results.getJSONObject(i).getString("id");
            Movie listOfSingleMovie=new Movie(posterPath,originalTitle,voteAverage,releaseDate,overview,movieId);

            sd.add(listOfSingleMovie);

        }

        return sd;
    }


}


