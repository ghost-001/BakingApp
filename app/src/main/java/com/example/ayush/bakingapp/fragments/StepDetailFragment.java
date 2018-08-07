package com.example.ayush.bakingapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayush.bakingapp.R;
import com.example.ayush.bakingapp.utils.Recipe;
import com.example.ayush.bakingapp.utils.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment {


    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.playerView)
    PlayerView playerView;
    @BindView(R.id.step_short_des)
    TextView shortDesc;
    @BindView(R.id.step_long_des)
    TextView longDesc;
    @BindView(R.id.step_progressBar)
    ProgressBar progressBar;
    @BindView(R.id.step_next_button)
    Button next;
    @BindView(R.id.step_prev_button)
    Button prev;

    private List<Step> step;
    private Integer stepId;
    private String videoUrl;
    private Recipe recipe;
    private SimpleExoPlayer player;
    private Boolean playeWhenReady = true;
    private Integer currentWindow;
    private Long playbackPosition;
    private ComponentListener componentListener;
    private OnButtonClick buttonClick;

    public StepDetailFragment() {
        // Required empty public constructor
    }

    public void setStep(List<Step> step) {
        this.step = step;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        setStep(recipe.getSteps());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, root);

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(recipe.getName());

        if(recipe!=null) {
            if (step != null) {
                shortDesc.setText(step.get(stepId).getShortDescription());
                longDesc.setText(step.get(stepId).getDescription());
            }
            componentListener = new ComponentListener();
        }

        if(stepId == (recipe.getStepsSize()-1)){
            next.setEnabled(false);
            next.setVisibility(View.GONE);
        }else {
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonClick.onButtonClick(1,stepId);
                }
            });
        }
        if(stepId==0){
            prev.setEnabled(false);
            prev.setVisibility(View.GONE);
        }else{
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClick.onButtonClick(0,stepId);
            }
        });}
        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            currentWindow = savedInstanceState.getInt("currentWindow");
            playbackPosition = savedInstanceState.getLong("playbackPosition");
            videoUrl = savedInstanceState.getString("playVideoURL");
            Log.i("PLAYP Fragment","Got VALUES");
            Log.i("PLAYP Fragment window","Got VALUES" + currentWindow);
            Log.i("PLAYP Fragment position","Got VALUES" + playbackPosition);
        }else{
            currentWindow = 0;
            playbackPosition = 0L;
            videoUrl = step.get(stepId).getVideoURL();
            Log.i("PLAYP Fragment","Initialised VALUES");
        }


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentWindow",currentWindow);
        outState.putLong("playbackPosition",playbackPosition);
        outState.putString("playVideoURL",videoUrl);
        Log.i("PLAYP Fragment","VALUES SAVED");
        Log.i("PLAYP Fragment window","VALUES SAVED" + currentWindow);
        Log.i("PLAYP Fragment position","VALUES SAVED" + playbackPosition);

    }

    public void initialisePlayer() {
        Log.i("PLAYP FRAGMENT","Player INITIALISED");

            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            playerView.setPlayer(player);
            player.addListener(componentListener);
            player.setPlayWhenReady(playeWhenReady);


        Uri uri = Uri.parse(videoUrl);
        MediaSource mediaSource = buildMediaSource(uri);
        player.seekTo(currentWindow, playbackPosition);
        Log.i("PLAYP Fragment window","Got VALUES" + currentWindow);
        Log.i("PLAYP Fragment position","Got VALUES" + playbackPosition);


        player.prepare(mediaSource,false,false);



    }

  
    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playeWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initialisePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initialisePlayer();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnButtonClick) {
            buttonClick = (OnButtonClick) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnButtonClick");
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
        Log.i("PLAYP", "ONPAUSE");
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
        Log.i("PLAYP", "ONSTOP");
    }

    @Override
    public void onDetach() {
        super.onDetach();
       // releasePlayer();
        Log.i("PLAYP", "ONDETACH");
        buttonClick = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
                    releasePlayer();
            Log.i("PLAYP","RELEASED DESTROYVIEW");

    }

    private class ComponentListener extends Player.DefaultEventListener {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            String stateString;
            switch (playbackState) {
                case Player.STATE_IDLE:
                    stateString = "ExoPlayer.STATE_IDLE      -";
                    break;
                case Player.STATE_BUFFERING:
                    stateString = "ExoPlayer.STATE_BUFFERING -";
                    break;
                case Player.STATE_READY:
                    stateString = "ExoPlayer.STATE_READY     -";
                    break;
                case Player.STATE_ENDED:
                    stateString = "ExoPlayer.STATE_ENDED     -";
                    break;
                default:
                    stateString = "UNKNOWN_STATE             -";
                    break;
            }
            Log.d("PLAYP", "changed state to " + stateString
                    + " playWhenReady: " + playWhenReady);
        }
    }

    public interface OnButtonClick {
        // TODO: Update argument type and name
        void onButtonClick(Integer val, Integer stepId);
    }
}
