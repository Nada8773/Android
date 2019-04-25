package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhraseActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;


    AudioManager.OnAudioFocusChangeListener onaudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                        // Pause playback because your Audio Focus was
                        // temporarily stolen, but will be back soon.
                        // i.e. for a phone call
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);

                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Stop playback, because you lost the Audio Focus.
                        // i.e. the user started some other playback app
                        // Remember to unregister your controls/buttons here.
                        // And release the kra — Audio Focus!
                        // You’re done.
                        ReleaseMediaPlayer();

                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Lower the volume, because something else is also
                        // playing audio over you.
                        // i.e. for notifications or navigation directions
                        // Depending on your audio playback, you may prefer to
                        // pause playback here instead. You do you.
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);

                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Resume playback, because you hold the Audio Focus
                        // again!
                        // i.e. the phone call ended or the nav directions
                        // are finished
                        // If you implement ducking and lower the volume, be
                        // sure to return it to normal here, as well.
                        mediaPlayer.start();
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        //** display a series of items into a list using a custom representation of the items,

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...","oyaaset..",R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I’m feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "әәnәs'aa?",R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I’m coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        words.add(new Word("I’m coming.", "әәnәm",R.raw.phrase_im_coming));
        words.add(new Word("Let’s go", "yoowutis",R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "әnni'nem",R.raw.phrase_come_here));





        WordAdapter itemsAdapter = new WordAdapter(this,words,R.color.colorPhrase);

        //****connect this adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.List);

        listView.setAdapter(itemsAdapter);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        /* Handle the audio voice */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                ReleaseMediaPlayer();
                Word w= words.get(position);




                // Request audio focus for playback
                /*
                OnAudioFocusChangeListener implementation  This is the part of your code
                 that will deal with audio focus changes
                 which are driven by events happening in the system

                 requestAudioFocus(…). This will return an integer value representing whether your audio focus request was granted or not.
                 Only if that value is AUDIOFOCUS_REQUEST_GRANTED should you start playback immediately.

                 */
                int result = audioManager.requestAudioFocus(onaudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mediaPlayer = MediaPlayer.create(PhraseActivity.this, w.GetSound());

                    // Start the audio file
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mPlayer) {
                            ReleaseMediaPlayer();

                        }
                    });
                }

            }
        });



    }



    protected void onStop(){
        super.onStop();
        ReleaseMediaPlayer();
    }

    private void ReleaseMediaPlayer()
    {
        if(mediaPlayer !=null)
        {
            mediaPlayer.release();
            mediaPlayer=null;
            audioManager.abandonAudioFocus(onaudioFocusChangeListener);
        }
    }
}