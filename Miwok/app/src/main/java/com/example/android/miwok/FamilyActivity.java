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

public class FamilyActivity extends AppCompatActivity {

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
        words.add(new Word("father", "әpә", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("son", "angsi", R.drawable.family_son, R.raw.family_son));
        words.add(new Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));


        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.colorFamily);

        //****connect this adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.List);

        listView.setAdapter(itemsAdapter);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        /* Handle the audio voice */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                ReleaseMediaPlayer();
                Word w = words.get(position);


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

                    mediaPlayer = MediaPlayer.create(FamilyActivity.this, w.GetSound());

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