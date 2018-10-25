package ganesh.com.googlesignin.youtubevideoplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import ganesh.com.googlesignin.R;

public class YoutubeVideoPlayer extends YouTubeBaseActivity
{
    YouTubePlayerView youtube_player_id;
    YouTubePlayer.OnInitializedListener mOnListener;
    Button clciks;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_video_player);
        youtube_player_id=(YouTubePlayerView)findViewById(R.id.youtube_player_id);
        clciks=findViewById(R.id.clciks);


        mOnListener=new YouTubePlayer.OnInitializedListener()
        {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b)
            {

                youTubePlayer.loadVideo("W4hTJybfU7s");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        clciks.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                youtube_player_id.initialize(YoutubeConfig.getApi_key(),mOnListener);
            }
        });
    }
}
