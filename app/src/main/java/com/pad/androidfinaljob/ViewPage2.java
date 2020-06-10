package com.pad.androidfinaljob;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewPage2 extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MyAdapter2 mAdapter;
    private List<VideoInfo> mVideoInfos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_page2);

        Log.d("url", "koko");
        findViewById(R.id.btn_VP2to1).setOnClickListener(this);
        recyclerView = (RecyclerView)findViewById(R.id.RV_covers2);
        layoutManager = new MyLinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mVideoInfos = new ArrayList<>();
        mAdapter = new MyAdapter2();
        recyclerView.setAdapter(mAdapter);
        getData();
    }

    private void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://beiyou.bytedance.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getVideos().enqueue(new Callback<List<VideoInfo>>() {
            @Override
            public void onResponse(Call<List<VideoInfo>> call, Response<List<VideoInfo>> response) {
                if (response.body() != null) {
                    Log.d("retrofit", Integer.toString(response.body().size()));
                    List<VideoInfo> videoInfos = response.body();
                    Log.d("retrofit", videoInfos.toString());
                    if (videoInfos.size() != 0) {
                        mAdapter.setContext(getApplicationContext());
                        mAdapter.setData(videoInfos);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<VideoInfo>> call, Throwable t) {
                Log.d("retrofit Fail", t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_VP2to1:
                Log.d("URL", "2to1");
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
