package com.example.user.projectbringback.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.user.projectbringback.R;

public class Music {
    public Uri uri;
    public String name;
    public String singer;
    public String album;
    public String genre;
    public String date;
    public int track_num;
    public int bitmapResource;
    private static Bitmap albumCover;

    public Music() {
    }

    public Music(String name, String singer, String album, String genre, String date, int track_num) {
        this.name = name;
        this.singer = singer;
        this.album = album;
        this.genre = genre;
        this.date = date;
        this.track_num = track_num;
    }


    public Music(String name, String singer, String album, String genre, String date, int track_num, int bitmapResource) {
        this.name = name;
        this.singer = singer;
        this.album = album;
        this.genre = genre;
        this.date = date;
        this.track_num = track_num;
        this.bitmapResource = bitmapResource;
    }


    public Music(Uri uri, String title, String singer, String album, String genre, String date, int track_num, int bitmapResource) {
        this.uri = uri;
        this.name = title;
        this.singer = singer;
        this.album = album;
        this.genre = genre;
        this.date = date;
        this.track_num = track_num;
        this.bitmapResource = bitmapResource;
    }

    public static final Music[] MUSICS = new Music[]{
            new Music(Uri.parse("file:///android_asset/after_you.mp3"),
            "After You",
            "Dan Lebowitz",
            "After You",
            "락",
            "2020",
            1,
            R.drawable.rock_metal),
            new Music(Uri.parse("file:///android_asset/barnyard_surprise.mp3"),
                    "Barnyard Surprise",
                    "The Whole Other",
                    "Barnyard Surprise",
                    "포크/어쿠스틱",
                    "2020",
                    1,
                    R.drawable.fork),
            new Music(Uri.parse("file:///android_asset/catch_up.mp3"),
                    "Catch Up",
                    "Dan Lebowitz",
                    "Catch Up",
                    "포크/어쿠스틱",
                    "2020",
                    1,
                    R.drawable.fork),
            new Music(Uri.parse("file:///android_asset/dance_of_the_mammoths.mp3"),
                    "Dance of the Mammoths",
                    "The Whole Other",
                    "Dance of the Mammoths",
                    "기타",
                    "2020",
                    1,
                    R.drawable.etc),
            new Music(Uri.parse("file:///android_asset/flight_to_tunisia.mp3"),
                    "Flight to Tunisia",
                    "Causmic",
                    "Flight to Tunisia",
                    "랩/힙합",
                    "2020",
                    1,
                    R.drawable.hiphop),
            new Music(Uri.parse("file:///android_asset/glitchin_a_ride.mp3"),
                    "Glitchin’ a Ride",
                    "The Whole Other",
                    "Glitchin’ a Ride",
                    "락",
                    "2020",
                    1,
                    R.drawable.rock_metal),
            new Music(Uri.parse("file:///android_asset/luxery.mp3"),
                    "Luxery",
                    "Causmic",
                    "Luxery",
                    "랩/힙합",
                    "2020",
                    1,
                    R.drawable.hiphop),
            new Music(Uri.parse("file:///android_asset/remember_september.mp3"),
                    "Remember September",
                    "Freedom Trail Studio",
                    "Remember September",
                    "재즈, 블루스",
                    "2020",
                    1,
                    R.drawable.jazz),
            new Music(Uri.parse("file:///android_asset/take_the_hill.mp3"),
                    "Take the Hill",
                    "The Whole Other",
                    "Take the Hill",
                    "일레트로닉, 댄스",
                    "2020",
                    1,
                    R.drawable.electronic),
            new Music(Uri.parse("file:///android_asset/the_anunnaki_return.mp3"),
                    "The Anunnaki Return",
                    "Jesse Gallagher",
                    "The Anunnaki Return",
                    "기타",
                    "2020",
                    1,
                    R.drawable.etc),
            new Music(Uri.parse("file:///android_asset/the_fur_purrrade.mp3"),
                    "The Fur Purrrade",
                    "The Whole Other",
                    "The Fur Purrrade",
                    "팝",
                    "2020",
                    1,
                    R.drawable.pop),
            new Music(Uri.parse("file:///android_asset/the_high_line.mp3"),
                    "The High Line",
                    "Causmic",
                    "The High Line",
                    "랩/힙합",
                    "2020",
                    1,
                    R.drawable.hiphop),
            new Music(Uri.parse("file:///android_asset/unavailable.mp3"),
                    "Unavailable",
                    "Causmic",
                    "Unavailable",
                    "알앤비/소울",
                    "2020",
                    1,
                    R.drawable.rnbsoul),
            new Music(Uri.parse("file:///android_asset/valley_of_spies.mp3"),
                    "Valley of Spies",
                    "The Whole Other",
                    "Valley of Spied",
                    "락",
                    "2020",
                    1,
                    R.drawable.rock_metal)
    };

    public static MediaDescriptionCompat getMediaDescription(Context context, Music music) {
        Bundle extras = new Bundle();
        Bitmap albumCover = getBitmap(context, music.bitmapResource);
        extras.putParcelable(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, albumCover);
        extras.putParcelable(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON, albumCover);
        return new MediaDescriptionCompat.Builder()
                .setMediaId(String.valueOf(music.track_num))
                .setIconBitmap(albumCover)
                .setTitle(music.name)
                .setDescription(music.album)
                .setExtras(extras)
                .build();
    }

    public static Bitmap getBitmap(Context context, @DrawableRes int bitmapResource) {
        Thread thread = new Thread(() -> {
            try {
                albumCover = Glide.with(context)
                        .asBitmap()
                        .load(bitmapResource)
                        .submit(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        });

        thread.start();
        return albumCover;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getBitmapResource() {
        return bitmapResource;
    }

    public void setBitmapResource(int bitmapResource) {
        this.bitmapResource = bitmapResource;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTrack_num() {
        return track_num;
    }

    public void setTrack_num(int track_num) {
        this.track_num = track_num;
    }
}
