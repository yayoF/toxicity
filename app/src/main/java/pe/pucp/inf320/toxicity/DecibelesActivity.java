package pe.pucp.inf320.toxicity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Formatter;

/**
 * Created by yayo on 6/1/15.
 */
public class DecibelesActivity extends Activity implements
        SeekBar.OnSeekBarChangeListener {

    private MediaRecorder mRecorder = null;
    private boolean listening;
    private TextView decibelsTx;
    private RelativeLayout barDB;
    private TextView decibeliosSeekbar;
    private TextView decibeliosVerdad;
    private int verdadDB;
    private SeekBar seekbarDB;
    Button btnVideo;

    public TextView verif1;
    public TextView verif2;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decibeles_layout);

        decibelsTx = (TextView) findViewById(R.id.decibels);
        barDB = (RelativeLayout) findViewById(R.id.bardb);
        seekbarDB = (SeekBar) findViewById(R.id.seekbar_db);
        decibeliosSeekbar = (TextView) findViewById(R.id.decibelios_seekbar);
        decibeliosVerdad = (TextView) findViewById(R.id.decibelios_verdad);
        seekbarDB.setOnSeekBarChangeListener(this);
        btnVideo = (Button) findViewById(R.id.btnvideo);

        verif1 = (TextView) findViewById(R.id.txtVerif1);
        verif2 = (TextView) findViewById(R.id.txtVerif2);
        // verdadDB = 0;
        btnVideo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                verif1.setVisibility(View.VISIBLE);
                verif2.setVisibility(View.VISIBLE);
                String parts[] =((String)decibelsTx.getText()).split(" ");
                double valorX = Double.valueOf(parts[0]);
                if ( (valorX >= 0-80) && (valorX <= 10-80) ) {
                    verif2.setText("apenas audible");
                } else if ( (valorX >= 11-80) && (valorX <= 20-80) ) {
                    verif2.setText("apenas audible");
                } else if ( (valorX >= 21-80) && (valorX <= 30-80) ) {
                    verif2.setText("muy silencioso");
                } else if ( (valorX >= 31-80) && (valorX <= 40-80) ) {
                    verif2.setText("muy silencioso");
                }else if ( (valorX >= 41-80) && (valorX <= 50-80) ) {
                    verif2.setText("silencioso");
                }else if ( (valorX >= 51-80) && (valorX <= 60-80) ) {
                    verif2.setText("intrusivo");
                }else if ( (valorX >= 61-80) && (valorX <= 70-80) ) {
                    verif2.setText("intrusivo");
                }else if ( (valorX >= 71-80) && (valorX <= 80-80) ) {
                    verif2.setText("molesto");
                }

            }
        });
    }

    @Override
    protected void onResume() {
        listening = true;
        new Ear().execute();
        super.onResume();
    }

    @Override
    protected void onPause() {
        listening = false;
        super.onPause();
    }

    // Metodo que inicializa la escucha
    public void start() {
        if (mRecorder == null) {
            // Inicializamos los parametros del grabador
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            // No indicamos ningun archivo ya que solo queremos escuchar
            mRecorder.setOutputFile("/dev/null");

            try {
                mRecorder.prepare();
            } catch (IllegalStateException e) {
                Log.e("error", "IllegalStateException");
            } catch (IOException e) {
                Log.e("error", "IOException");
                ;
            }

            mRecorder.start();
        }
    }

    // Para la escucha
    public void stop() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    // Devuelve la mayor amplitud del sonido captado desde la ultima vez que se
    // llamo al metodo
    public double getAmplitude() {
        if (mRecorder != null) {
            verdadDB = mRecorder.getMaxAmplitude();
            return (verdadDB);
        } else
            return 0;
    }

    public class Ear extends AsyncTask<Void, Double, Void> {

        protected void onPreExecute() {
            start();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            while (listening) {
                SystemClock.sleep(200); // Si es menor casi siempre da 0
                Double amplitude = 20 * Math.log10(getAmplitude() / 32768.0);
                publishProgress(amplitude);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Double... values) {
            Double value = values[0];

            if (value < -80) {
                value = new Double(-80);
            } else if (value > 0) {
                value = new Double(0);
            }

            String db = new Formatter().format("%03.1f", value).toString();

            decibelsTx.setText(db + " dB");
            decibeliosVerdad.setText(verdadDB + " u");

            updateBar(value);

        }

        @Override
        protected void onPostExecute(Void result) {
            stop();
        }

        public void updateBar(Double db) {
            Double width;

            // Factor de escala para convertir a Dips
            final float scale = getResources().getDisplayMetrics().density;

            width = (db * 250 * scale) / -80; // Anchura en pixeles

            RelativeLayout.LayoutParams lyParams = new RelativeLayout.LayoutParams(
                    width.intValue(), barDB.getHeight());
            lyParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            barDB.setLayoutParams(lyParams);

        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
                                  boolean fromUser) {
        decibeliosSeekbar.setText((-80 + progress) + " dB");
        // decibeliosVerdad.setText(verdadDB+ " u");
    }

    @Override
    public void onStartTrackingTouch(SeekBar arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar arg0) {
        // TODO Auto-generated method stub

    }

}
