package com.appsrui.movies.data.remote

import com.google.gson.annotations.SerializedName

data class TrailerResponse(
    val id: String,
    @SerializedName("tmdb_id")
    val tmdbId: Int,
    val language: String,
    val title: String,
    val url: String,
    val trailer: VideoDetails,
    val videos: List<VideoDetails>,
) {
    data class VideoDetails(
        val id: String,
        @SerializedName("youtube_video_id")
        val youtubeVideoId: String,
        @SerializedName("youtube_channel_id")
        val youtubeChannelId: String,
        @SerializedName("youtube_thumbnail")
        val youtubeThumbnail: String,
        val title: String,
        val thumbnail: String,
        val language: String,
        val categories: List<String>,
        val published: String,
        val views: Int,
    )
}