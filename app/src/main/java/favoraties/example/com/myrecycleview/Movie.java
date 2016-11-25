package favoraties.example.com.myrecycleview;

import android.graphics.Bitmap;

/**
 * Created by Zahoor on 10/31/2016.
 */

public class Movie {
    private String title, year;
    Bitmap genre;

    public Movie() {
    }

    public Movie(String title, Bitmap genre, String year) {
        this.title = title;
        this.genre = genre;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Bitmap getGenre() {
        return genre;
    }

    public void setGenre(Bitmap genre) {
        this.genre = genre;
    }

}
