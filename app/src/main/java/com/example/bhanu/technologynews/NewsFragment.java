package com.example.bhanu.technologynews;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by bhanu kiran on 10/08/2016.
 */
public  class NewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    NewsAdapter adapter;
    public NewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView= (ListView) rootView.findViewById(R.id.list_view);
        //getLoaderManager().restartLoader(0,null,this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent=new Intent(getActivity(),DetailActivity.class);
                Uri uri= ContentUris.withAppendedId(NewsContract.NewsEntry.CONTENT_URI, l);
                intent.putExtra(DetailActivity.DEATIL,uri);
                startActivity(intent);
            }
        });
        adapter=new NewsAdapter(getActivity(),null,0);
        listView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        FetchNewsTask task=new FetchNewsTask();
        task.execute();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        CursorLoader loader=new CursorLoader(getActivity(), NewsContract.NewsEntry.CONTENT_URI,null,null,null,null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        adapter.swapCursor(null);
    }

    class FetchNewsTask extends AsyncTask<Void,Void,Void>
    {

        HttpURLConnection connection=null;
        BufferedReader reader=null;
        String Json;
        @Override
        protected Void doInBackground(Void... voids) {
//            String baseurl="https://newsapi.org/v1/articles?source=techcrunch&sortBy=popular";
//            String apiKey = "apiKey=" + BuildConfig.OPEN_WEATHER_MAP_API_KEY;

            String baseUrl ="https://newsapi.org/v1/articles?";
            Uri builturi=Uri.parse(baseUrl).buildUpon().appendQueryParameter("source","techcrunch").appendQueryParameter("sortBy", "popular").appendQueryParameter("apiKey", BuildConfig.OPEN_WEATHER_MAP_API_KEY).build();
            try {
                URL url=new URL(builturi.toString());
                connection= (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream inputStream=connection.getInputStream();
                if(inputStream==null)
                {
                    return null;
                }
                reader=new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuffer buffer=new StringBuffer();
                while((line=reader.readLine())!=null)
                {
                    buffer.append(line);
                }
                if(buffer.length()==0)
                    return null;
                Json=buffer.toString();
                //Log.d("NewsFeed",Json);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                makenews(Json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        private void makenews(String json) throws JSONException {
            JSONObject jsonObject=new JSONObject(json);
            JSONArray article=jsonObject.getJSONArray("articles");
            //String[] result=new String[article.length()];
            for(int i=0;i<article.length();i++)
            {
                JSONObject object=article.getJSONObject(i);
                // String title=object.getString("title");
                String description=object.getString("description");
                String url=object.getString("url");
                String img=object.getString("urlToImage");
                // addnews(description,url,img);
                ContentValues values=new ContentValues();
                values.put(NewsContract.NewsEntry.DESC,description);
                values.put(NewsContract.NewsEntry.URL,url);
                values.put(NewsContract.NewsEntry.IMAGE,img);
                getActivity().getContentResolver().insert(NewsContract.NewsEntry.CONTENT_URI,values);
                //result[i]= "Desc: "+description;

            }
//            for(String res:result)
//            {
//                Log.d("News",res);
//            }


        }



    }
}