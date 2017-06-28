package com.pluralsight.flink;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by shifengyuan.
 * Date: 2017/6/25
 * Time: 15:43
 */
public class FilterMovies {

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        ParameterTool parameters = ParameterTool.fromArgs(args);
        String inputPath = parameters.getRequired("inputPath");
        String outputPath = parameters.getRequired("outputPath");

        DataSet<Tuple3<Long, String, String>> lines = env.readCsvFile(inputPath) // "myweb/doc/movie-rating/movies.csv"
                .ignoreFirstLine()
                .parseQuotedStrings('"')
                .ignoreInvalidLines()
                .types(Long.class, String.class, String.class);

        // step 1 : map each line to movie object
        DataSet<Movie> movies = lines.map(new MapFunction<Tuple3<Long, String, String>, Movie>() {
            @Override
            public Movie map(Tuple3<Long, String, String> csvLine) throws Exception {
                String movieName = csvLine.f1;
                String[] genres = csvLine.f2.split("\\|");
                return new Movie(movieName, new HashSet<>(Arrays.asList(genres)));
            }
        });

        // step 2 : filter
        DataSet<Movie> filteredMovies = movies.filter(new FilterFunction<Movie>() {
            @Override
            public boolean filter(Movie movie) throws Exception {
                return movie.getGenres().contains("Drama");
            }
        });

        // step 3 : output
        //filteredMovies.print();
        filteredMovies.writeAsText(outputPath); // myweb/doc/movie-rating/filter-output
        env.execute();
    }
}

class Movie {

    private String name;
    private Set<String> genres;

    public Movie(String name, Set<String> genres) {
        this.name = name;
        this.genres = genres;
    }

    public String getName() {
        return name;
    }

    public Set<String> getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        return "Movie {name='" + name + '\'' + ", genres=" + genres + '}';
    }
}
