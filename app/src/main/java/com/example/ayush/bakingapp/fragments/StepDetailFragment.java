package com.example.ayush.bakingapp.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ayush.bakingapp.utils.NetworkState;
import com.example.ayush.bakingapp.R;
import com.example.ayush.bakingapp.appConstants.AppConstants;
import com.example.ayush.bakingapp.utils.Recipe;
import com.example.ayush.bakingapp.utils.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
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

import static android.view.View.GONE;

public class StepDetailFragment extends Fragment {


    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.playerView)
    PlayerView playerView;
    @BindView(R.id.play_no_video)
    TextView noVideo;
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
    public Integer stepId;
    private String videoUrl;
    private Recipe recipe;
    private SimpleExoPlayer player;
    private Boolean playeWhenReady = true;
    private Integer currentWindow;
    private Long playbackPosition;
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

        NetworkState networkState = new NetworkState(getContext());
        if (!networkState.checkInternet()) {
            showNoInternet();
        } else {
            shortDesc.setText(recipe.getSteps().get(stepId).getShortDescription());
            longDesc.setText(recipe.getSteps().get(stepId).getDescription());

            if (stepId == (recipe.getStepsSize() - 1)) {
                next.setEnabled(false);
                next.setVisibility(View.GONE);
            } else {
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        buttonClick.onButtonClick(1, stepId);
                    }
                });
            }
            if (stepId == 0) {
                prev.setEnabled(false);
                prev.setVisibility(View.GONE);
            } else {
                prev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        buttonClick.onButtonClick(0, stepId);
                    }
                });
            }
        }

        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            currentWindow = savedInstanceState.getInt(AppConstants.CURRENT_WINDOW);
            playbackPosition = savedInstanceState.getLong(AppConstants.PLAYBACK_POSITION);
            videoUrl = savedInstanceState.getString(AppConstants.VIDEO_URL);
            if (videoUrl == null) {
                noVideo.setVisibility(View.VISIBLE);
            } else {
                playerView.setVisibility(View.VISIBLE);
            }
        } else {
            currentWindow = 0;
            playbackPosition = 0L;
            if (!step.get(stepId).getVideoURL().isEmpty()) {
                videoUrl = recipe.getSteps().get(stepId).getVideoURL();
                playerView.setVisibility(View.VISIBLE);
            } else {
                videoUrl = null;
                noVideo.setVisibility(View.VISIBLE);
            }

        }


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(AppConstants.CURRENT_WINDOW, currentWindow);
        outState.putLong(AppConstants.PLAYBACK_POSITION, playbackPosition);
        outState.putString(AppConstants.VIDEO_URL, videoUrl);
    }

    public void initialisePlayer() {

        if (videoUrl != null) {
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            playerView.setPlayer(player);
            player.setPlayWhenReady(playeWhenReady);


            Uri uri = Uri.parse(videoUrl);
            MediaSource mediaSource = buildMediaSource(uri);
            player.seekTo(currentWindow, playbackPosition);
            player.prepare(mediaSource, false, false);

        }

    }


    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(AppConstants.USER_AGENT)).
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
                    + AppConstants.ERROR_LISTNER);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();

    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        buttonClick = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();


    }

    public interface OnButtonClick {

        void onButtonClick(Integer val, Integer x);
    }

    public void showNoInternet() {
        progressBar.setVisibility(GONE);
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().onBackPressed();
                    }
                });
        builder.create();
        builder.show();
    }
}
