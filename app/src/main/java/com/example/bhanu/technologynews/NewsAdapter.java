package com.example.bhanu.technologynews;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by bhanu kiran on 10/08/2016.
 */
public class NewsAdapter extends CursorAdapter {
    Context mcontext;

    public NewsAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        mcontext=context;
    }

    public static class ViewHolder
    {
        public final TextView desc;
        public final ImageView url;
        public ViewHolder(View view)
        {
            desc= (TextView) view.findViewById(R.id.text_view_desc);
            url= (ImageView) view.findViewById(R.id.image_view);
        }


    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_item_view,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //TextView desc= (TextView) view.findViewById(R.id.text_view_desc);
        //TextView url= (TextView) view.findViewById(R.id.text_view_url);
        //ImageView url= (ImageView) view.findViewById(R.id.image_view);
        ViewHolder viewHolder= (ViewHolder) view.getTag();

        String d=cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.DESC));
        viewHolder.desc.setText(d);
        String u=cursor.getString(cursor.getColumnIndex(NewsContract.NewsEntry.IMAGE));
        ImageView url=viewHolder.url;
        //desc.setText(d);
        Picasso.with(context).load(u).into(url);

    }
}