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



public class NumberActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;


    /* Handle Media Player */
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






        /************************************  method 3 ***************************/
        /*** Using Array Adapter for two list of words Miwok & English words */


        //** display a series of items into a list using a custom representation of the items,

       final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("one","Lutti",R.drawable.number_one,R.raw.number_one));
        words.add(new Word("two","otiiko",R.drawable.number_two,R.raw.number_two));
        words.add(new Word("three","tolookosu",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("four","oyyisa",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("five","massakka",R.drawable.number_five,R.raw.number_five));
        words.add(new Word("six","temmakka",R.drawable.number_six,R.raw.number_six));
        words.add(new Word("seven","kenekaku",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("eight","kawinta",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("nine","wo’e",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("ten","na’aacha",R.drawable.number_ten,R.raw.number_ten));




        WordAdapter itemsAdapter = new WordAdapter(this,words,R.color.colorNumbers);

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

                    mediaPlayer = MediaPlayer.create(NumberActivity.this, w.GetSound());

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


        /******************************************************************************/


        /************************************  method 2 ***************************/
        /*** Using Array Adapter for one list of words */

        /*
        ArrayList<String> numbers = new ArrayList<String>();
        numbers.add("one");
        numbers.add("two");
        numbers.add("three");
        numbers.add("four");
        numbers.add("five");
        numbers.add("six");
        numbers.add("seven");
        numbers.add("eight");
        numbers.add("nine");
        numbers.add("ten");

        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this,
                                                     android.R.layout.simple_list_item_1, numbers);

        //****connect this adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.List);

        listView.setAdapter(itemsAdapter);*/

        /*********************************************************************************/


         /************************************  method 1 ***************************/
         /*** Using Linear Layout for one list of words */
       /*
        ArrayList<String> numbers = new ArrayList<String>();
        numbers.add("one");
        numbers.add("two");
        numbers.add("three");
        numbers.add("four");
        numbers.add("five");
        numbers.add("six");
        numbers.add("seven");
        numbers.add("eight");
        numbers.add("nine");
        numbers.add("ten");

       LinearLayout Number_linearlayout =(LinearLayout) findViewById(R.id.ActivityNumber_layout);

        for(int index=0;index<numbers.size();index++) {
            TextView Number_TextView = new TextView(this);
            Number_TextView.setText(numbers.get(index));
            Number_linearlayout.addView(Number_TextView);}*/
       /**********************************************************************************/




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
