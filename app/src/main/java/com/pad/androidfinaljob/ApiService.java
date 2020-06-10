package com.pad.androidfinaljob;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("invoke/video/invoke/video")
    Call<List<VideoInfo>> getVideos();
}