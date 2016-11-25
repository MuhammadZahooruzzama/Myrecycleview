package favoraties.example.com.myrecycleview;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    String url = "http://www.dawn.com/";
    ProgressDialog progressdialogue;
    Document document;
    Elements description,description1;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.content_main);

        progressdialogue = null;
        document=null;

        new Title().execute();

    }
    @Override
    public void onPause(){

        super.onPause();
        if(progressdialogue != null)
            progressdialogue.dismiss();
    }

    public class  Title extends AsyncTask<Void , Void ,Void>
    {

        String desc;
        Bitmap imag;
        String am;
        @Override
        protected Void doInBackground(Void... params) {
            try
            {
                document   = Jsoup.connect(url).get();
              description = document.select("h2[class=story__title    size-four]");
                for(Element link :description)
                {
                 am=link.text();
                    System.out.println("\ntext : " + am);
                }

                description1 = document.select("div[class=story__excerpt    size-three]");

                for (Element linkk : description1) {
                    desc = linkk.text();
                    System.out.println("\ntext : " + desc);
                    }
                Elements images = document.select("a[title=''] img[src]");
                for (Element link : images) {
                    String imgSrc = link.attr("src");

                    InputStream input = new java.net.URL(imgSrc).openStream();

                    bitmap = BitmapFactory.decodeStream(input);

                    imag = bitmap;
                }
                Movie movie = new Movie(am, imag, desc);
                movieList.add(movie);

            }
            catch (IOException ex)
            {
                    ex.printStackTrace();
            }
           return  null;
        }

        protected void onPostExecute(Void avoid) {
                  super.onPostExecute(avoid);

            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            mAdapter = new MoviesAdapter(movieList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            progressdialogue.dismiss();
        }

        protected void onPreExecute() {
               super.onPreExecute();
            progressdialogue = new ProgressDialog(MainActivity.this);
            progressdialogue.setTitle("Title");
            progressdialogue.setMessage("Loading");
            progressdialogue.setIndeterminate(false);
            progressdialogue.setCancelable(false);
            progressdialogue.show();
        }
    }

}
