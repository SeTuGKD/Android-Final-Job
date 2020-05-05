package com.pad.androidfinaljob;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<VideoInfo> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView author;
        public TextView like;
        public ImageButton cover;

        public MyViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.tv_title);
            author = v.findViewById(R.id.tv_author);
            like = v.findViewById(R.id.tv_like);
            cover = v.findViewById(R.id.Ibtn_cover);
        }
    }


    public void setData(List<VideoInfo> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    public static Bitmap getHttpBitmap(String url) {
        Log.d("bitmap", "start");
        URL myFileURL;
        Bitmap bitmap = null;
        try {
            myFileURL = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
            conn.setConnectTimeout(6000);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
            Log.d("bitmap", "fi");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("bitmap", "ret");
        return bitmap;
    }

        // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.title.setText(mDataset.get(position).description);
        holder.author.setText(mDataset.get(position).nickname);
        //holder.cover.setImageBitmap(getHttpBitmap(mDataset.get(position).avatarUrl));
        holder.like.setText(Integer.toString(mDataset.get(position).likeCount));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }
}