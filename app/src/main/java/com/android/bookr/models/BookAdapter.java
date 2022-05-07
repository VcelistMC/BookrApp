package com.android.bookr.models;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.bookr.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    List<Book> bookList;

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.setItem(book);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView thumbnail;
        private Book item;
        private TextView titleView;
        private TextView authorView;
        private ImageButton storeBtn;
        private TextView descView;
        private boolean descVisible = false;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.title_view);
            authorView = itemView.findViewById(R.id.author_view);
            storeBtn = itemView.findViewById(R.id.store_btn);
            descView = itemView.findViewById(R.id.desc_view);

            itemView.setOnClickListener(view -> {
                descVisible = !descVisible;
                descView.setVisibility(descVisible? View.VISIBLE: View.GONE);
            });

            storeBtn.setOnClickListener(view -> {
                String url = item.getStoreLink();
                if(url.equals("")){
                    Toast.makeText(itemView.getContext(), "No Store URL Found", Toast.LENGTH_SHORT).show();
                    return;
                };

                Uri webpage = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                itemView.getContext().startActivity(intent);
            });
        }

        public void setItem(Book book) {
            item = book;
            titleView.setText(book.getTitle());
            authorView.setText(book.getAuthor());
            descView.setText(book.getDescription());
        }
    }

    public void setBookList(List<Book> books){
        bookList.clear();
        bookList.addAll(books);
        bookList = books;
        notifyDataSetChanged();
    }
}
