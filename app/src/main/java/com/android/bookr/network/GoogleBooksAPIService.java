package com.android.bookr.network;

import com.android.bookr.models.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GoogleBooksAPIService extends BookAPIService{
    final private String REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=%s&maxResults=%s";
    @Override
    List<Book> fromJson(String json) {
        List<Book> books = new ArrayList<>();
        try{
            JSONObject object = new JSONObject(json);
            JSONArray results = object.getJSONArray("items");

            for (int i = 0; i < results.length(); i++) {
                JSONObject res = results.getJSONObject(i);
                JSONObject volumeInfo = res.getJSONObject("volumeInfo");
                JSONObject accessInfo = res.getJSONObject("accessInfo");

                String title = "";
                try {
                    title = volumeInfo.getString("title");
                }catch (Exception ignored){}

                String author = "";
                try {
                    JSONArray authorsJsonArr = volumeInfo.getJSONArray("authors");
                    StringBuilder authors = new StringBuilder();
                    int n = authorsJsonArr.length();
                    ;
                    for (int j = 0; j < authorsJsonArr.length(); j++) {
                        authors.append(authorsJsonArr.getString(j));
                        if (j != n - 1)
                            authors.append(", ");
                    }
                    author = authors.toString();
                }catch (Exception ignored){}

                String smallthumbnailUrl = "";
                String thumbnailUrl = "";
                try{
                    JSONObject links = volumeInfo.getJSONObject("imageLinks");
                    smallthumbnailUrl = links.getString("smallThumbnail");
                    thumbnailUrl = links.getString("thumbnail");
                }catch (Exception ignored){}

                String description = "";
                String storeLink = "";
                try {
                    description = volumeInfo.getString("description");
                    storeLink = accessInfo.getString("webReaderLink");
                }catch (Exception ignored){}


                books.add(new Book(title, author, smallthumbnailUrl, thumbnailUrl, description, storeLink));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> getBooks(String... params){
        if(params.length != 2)
            throw new IllegalArgumentException("Expected 2 args, got " + params.length +" instead");
        String requestUrl = String.format(REQUEST_URL, params[0], params[1]);
        String jsonRes = request(requestUrl);
        return fromJson(jsonRes);
    }

//    public static void main(String[] args) {
//        BookAPIService bookAPIService = new GoogleBooksAPIService();
//        List<Book> books = bookAPIService.getBooks("android", "2");
//        for(Book book: books){
//            System.out.println(book);
//        }
//    }
}
