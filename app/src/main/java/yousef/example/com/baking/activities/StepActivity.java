package yousef.example.com.baking.activities;

import yousef.example.com.baking.R;
import yousef.example.com.baking.objects.Steps;

import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepActivity extends AppCompatActivity {
    @BindView(R.id.exo_player)
    SimpleExoPlayerView exoPlayerView;

    public static final String STEP_EXTRA = "steps_extra";
    SimpleExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);

        if (!getIntent().hasExtra(STEP_EXTRA)) {
            return;
        }
        TextView descriptionTextView = findViewById(R.id.tv_step_description);
        ImageView thumbnailImageView = findViewById(R.id.iv_thumbnail_image);

        if (descriptionTextView == null) {
            hideAppBar();
        } else {
            showAppBar();
        }

        final Steps step = getIntent().getParcelableExtra(STEP_EXTRA);
        setTitle(step.getShortDescription());

        showVideo(step);
        if (descriptionTextView != null) {
            descriptionTextView.setText(step.getDescription());
        }
        if (thumbnailImageView != null) {
            String thumbnailUrl = step.getThumbnailURL();
            if (thumbnailUrl != null && !thumbnailUrl.equals("")) {
                Picasso.get().
                        load(thumbnailUrl).
                        placeholder(R.drawable.ic_image_black_24dp).
                        error(R.drawable.ic_image_black_24dp).
                        into(thumbnailImageView);
            } else {
                thumbnailImageView.setVisibility(View.INVISIBLE);
            }
        }


        if (savedInstanceState != null) {
            boolean playing = savedInstanceState.getBoolean("playing");
            long position = savedInstanceState.getLong("position");
            player.seekTo(position);
            player.setPlayWhenReady(playing);
        }
    }

    private void showVideo(Steps step) {
        TrackSelector trackSelector = new DefaultTrackSelector();
        LoadControl loadControl = new DefaultLoadControl();
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);

        exoPlayerView.setPlayer(player);
        String videoUrl = step.getVideoURL();
        String thumbnailUrl = step.getThumbnailURL();

        if (videoUrl == null || videoUrl.equals("")) {
            exoPlayerView.setVisibility(View.GONE);
        }
        if (thumbnailUrl == null || thumbnailUrl.equals("")) {

        }
        Uri uri = Uri.parse(videoUrl);

        String userAgent = Util.getUserAgent(this, "Baking");
        MediaSource mediaSource = new ExtractorMediaSource(uri,
                new DefaultDataSourceFactory(this, userAgent),
                new DefaultExtractorsFactory(), null, null);

        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
    }

    private void showAppBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.show();
        }
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void hideAppBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
        player.release();
    }

    @Override
    protected void onStop() {
        super.onStop();
        player.stop();
        player.release();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        long position = player.getCurrentPosition();
        boolean playing = player.getPlayWhenReady();

        outState.putBoolean("playing", playing);
        outState.putLong("position", position);
    }

}
