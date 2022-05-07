    package com.android.bookr;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.DividerItemDecoration;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ProgressBar;
    import android.widget.TextView;

    import com.android.bookr.models.Book;
    import com.android.bookr.models.BookAdapter;
    import com.android.bookr.network.BookAPIService;
    import com.android.bookr.network.GoogleBooksAPIService;

    import java.util.ArrayList;
    import java.util.List;

    //
public class MainActivity extends AppCompatActivity {
    List<Book> books = new ArrayList<>();
    private static final int MAX_RESULTS = 20;
    BookAdapter adapter = new BookAdapter(books);
    TextView query;
    Button searchBtn;
    RecyclerView recyclerView;
    TextView emptyState;
    ProgressBar bar;
    private class Fetcher extends AsyncTask<String, Void, List<Book>>{
        @Override
        protected List<Book> doInBackground(String... strings) {
            BookAPIService service = new GoogleBooksAPIService();
            return service.getBooks(strings);
        }

        @Override
        protected void onPostExecute(List<Book> booksRes) {
            adapter.setBookList(booksRes);
            if(booksRes.size() > 0){
                recyclerView.setVisibility(View.VISIBLE);
                emptyState.setVisibility(View.GONE);
                bar.setVisibility(View.GONE);
            }
            else{
                recyclerView.setVisibility(View.GONE);
                emptyState.setText("No Results found");
                emptyState.setVisibility(View.VISIBLE);
                bar.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BookAPIService bookAPIService = new GoogleBooksAPIService();
        query = findViewById(R.id.category_input_view);
        searchBtn = findViewById(R.id.search_button);
        emptyState = findViewById(R.id.empty_state);
        bar = findViewById(R.id.loading);

        searchBtn.setOnClickListener(view -> {
            CharSequence queryStr = query.getText();
            if(queryStr.equals("")) return;
            query.setText("");
            bar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            emptyState.setVisibility(View.GONE);
            new Fetcher().execute(queryStr.toString(), String.valueOf(MAX_RESULTS));
        });

        recyclerView = findViewById(R.id.books_listview);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}