package com.example.alphamobilecolombia.mvp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

import com.example.alphamobilecolombia.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

public class YouTubeVideoActivity extends YouTubeBaseActivity {


    private final String API_KEY_YOUTUBE = "304209871211-qaof9ib2flvgud4cmj85nfjsakbelnsd.apps.googleusercontent.com";

    private static final String ID_VIDEO_PROPERTY = "idVideoProperty";
    private String video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_tube_video);

        if (getIntent().getExtras() != null) {
            video = getIntent().getExtras().getString(ID_VIDEO_PROPERTY);
        }

        //Initializing and adding YouTubePlayerFragment
        FragmentManager fm = getFragmentManager();
        String tag = YouTubePlayerFragment.class.getSimpleName();
        YouTubePlayerFragment playerFragment = (YouTubePlayerFragment) fm.findFragmentByTag(tag);
        if (playerFragment == null) {
            FragmentTransaction ft = fm.beginTransaction();
            playerFragment = YouTubePlayerFragment.newInstance();
            ft.add(android.R.id.content, playerFragment, tag);
            ft.commit();
        }


        playerFragment.initialize(API_KEY_YOUTUBE, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                String id_video = video;
                youTubePlayer.loadVideo(id_video);
                //youTubePlayer.setFullscreen(true);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(YouTubeVideoActivity.this, "Error inicializando YouTubePlayer.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
