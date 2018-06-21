package yousef.example.com.baking.activities;


import yousef.example.com.baking.objects.Steps;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yousef.example.com.baking.R;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */

public class RecipeStepDetailFragment extends Fragment {
    @BindView(R.id.fragment_exo_player)
    SimpleExoPlayerView exoPlayerView;
    @BindView(R.id.fragment_tv_step_description)
    TextView descriptionTextView;

    public static final String STEP_BUNDLE = "step_bundle";
    SimpleExoPlayer player;
    Steps step;
    public RecipeStepDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(STEP_BUNDLE)){
            ButterKnife.bind(this, view);

            step = bundle.getParcelable(STEP_BUNDLE);

            showVideo(step);

            if (descriptionTextView != null) {
                descriptionTextView.setText(step.getDescription());
            }

            return view;
        }
        return null;
    }

    private void showVideo(Steps step) {
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);

        exoPlayerView.setPlayer(player);
        String videoUriString = step.getVideoURL();

        String uriString;
        if (videoUriString == null || videoUriString.equals("")) {
            uriString = step.getThumbnailURL();
        } else {
            uriString = videoUriString;
        }
        Uri uri = Uri.parse(uriString);

        String userAgent = Util.getUserAgent(getContext(), "Baking");
        MediaSource mediaSource = new ExtractorMediaSource(uri,
                new DefaultDataSourceFactory(getContext(), userAgent),
                new DefaultExtractorsFactory(), null, null);

        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        player.stop();
        player.release();
    }

    @Override
    public void onStop() {
        super.onStop();
        player.stop();
        player.release();
    }
}
