package com.pad.androidfinaljob;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pad.androidfinaljob.MainActivity;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<VideoInfo> mDataset;
    Context mcontext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView author;
        public TextView like;
        public ImageButton cover;
        public ImageView start;


        public MyViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.tv_title);
            author = v.findViewById(R.id.tv_author);
            like = v.findViewById(R.id.tv_like);
            cover = v.findViewById(R.id.ibtn_cover);
            start = v.findViewById(R.id.iv_start);
        }
    }


    public void setData(List<VideoInfo> myDataset) {
        mDataset = myDataset;
    }
    public void setContext(Context context) {
        mcontext = context;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(mDataset.get(position).description);
        holder.author.setText(mDataset.get(position).nickname + "的小视频");
        holder.start.setAlpha(150);
        Glide.with(mcontext)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(1000000)
                                .centerCrop()
                )
                .load(mDataset.get(position).feedUrl)
                .into(holder.cover);
        holder.like.setText("点赞：" + Integer.toString(mDataset.get(position).likeCount));
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "position" + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mcontext,videoplayer.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
            }
        };
        holder.cover.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }
}