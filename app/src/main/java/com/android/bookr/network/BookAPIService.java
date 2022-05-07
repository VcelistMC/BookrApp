package com.android.bookr.network;

import com.android.bookr.models.Book;
import com.android.bookr.utils.StreamReader;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public abstract class BookAPIService {

    private URL createUrl(String strUrl) {
        URL url = null;
        try{
            url = new URL(strUrl);
        }catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }

    private String makeHttpRequest(URL url){
        String jsonResponse = "";
        if(url == null)
            return jsonResponse;

        HttpURLConnection connection = null;
        InputStream stream = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.connect();

            if(connection.getResponseCode() == 200){
                stream = connection.getInputStream();
                jsonResponse = StreamReader.readFromStream(stream);
            }
            if(connection != null)
                connection.disconnect();
            if(stream != null)
                stream.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonResponse;
    }

    protected String request(String stringUrl){
        URL url = createUrl(stringUrl);

        return makeHttpRequest(url);
    }

    abstract List<Book> fromJson(String json);
    abstract public List<Book> getBooks(String... params);
}
