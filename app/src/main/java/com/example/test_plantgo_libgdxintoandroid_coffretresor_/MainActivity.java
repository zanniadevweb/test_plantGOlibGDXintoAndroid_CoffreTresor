package com.example.test_plantgo_libgdxintoandroid_coffretresor_;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public Integer timeCountInMilliSeconds = 5000;
    public static boolean timerOn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt3 = findViewById(R.id.buttonSuite);
        bt3.setVisibility(View.INVISIBLE);
        Button bt2 = findViewById(R.id.buttonRemplir);
        bt2.setVisibility(View.INVISIBLE);
        ImageView imgv1 = findViewById(R.id.gifChest);
        imgv1.setVisibility(View.INVISIBLE);
        ImageView imgv2 = findViewById(R.id.gifShovel);
        imgv2.setVisibility(View.INVISIBLE);
        ImageView imgv3 = findViewById(R.id.idle);
        imgv3.setVisibility(View.INVISIBLE);
        ProgressBar pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
    }

    public void remplirBarre(View view) {
        ProgressBar pb = findViewById(R.id.progressBar);

        new Thread(() -> {
            try {
                Thread.sleep(500); // Faire l'action désirée après 5 secondes
                pb.incrementProgressBy(12); // Remplir la barre de progression
            }
            catch(InterruptedException ex){
                ex.printStackTrace();
            }
        }).start();
    }

    public void viderBarre(View view) {
        startCountDownTimer();
        Button bt2 = findViewById(R.id.buttonRemplir);
        bt2.setVisibility(View.VISIBLE);
    }

    private void setProgressBarValues() {
        ProgressBar pb = findViewById(R.id.progressBar);
        pb.setMax((int) timeCountInMilliSeconds / 50);
        pb.setProgress((int) timeCountInMilliSeconds / 1000);
    }

    private void startCountDownTimer() {
        ProgressBar pb = findViewById(R.id.progressBar);
        CountDownTimer countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 50) {


            @Override
            public void onTick(long millisUntilFinished) {
                //Log.e("seconds remaining : ", "seconds remaining : " + millisUntilFinished / 1000);
                Integer tempsRestant = Math.toIntExact(millisUntilFinished / 1000);
                if (timerOn == true) { // Quand le timer est actif
                    pb.incrementProgressBy(-1);
                    ImageView imgv1 = findViewById(R.id.gifShovel);
                    imgv1.setVisibility(View.VISIBLE);
                    TextView tv2 = findViewById(R.id.chrono);
                    tv2.setVisibility(View.VISIBLE);
                    tv2.setText(String.valueOf(tempsRestant));
                    pb.setVisibility(View.VISIBLE);
                }

                if ((millisUntilFinished / 1000) == 0 && pb.getProgress() < 100) { // Quand le timer est terminé
                    Button bt2 = findViewById(R.id.buttonRemplir);
                    bt2.setVisibility(View.INVISIBLE);
                    ImageView imgv1 = findViewById(R.id.gifShovel);
                    imgv1.setVisibility(View.INVISIBLE);
                    ImageView imgv3 = findViewById(R.id.idle);
                    imgv3.setVisibility(View.VISIBLE);
                    Button bt1 = findViewById(R.id.buttonVider);
                    bt1.setVisibility(View.VISIBLE);
                    TextView tv1 = findViewById(R.id.textView);
                    tv1.setText("Relancez le chronomètre");
                    TextView tv2 = findViewById(R.id.chrono);
                    tv2.setVisibility(View.INVISIBLE);
                    pb.setVisibility(View.INVISIBLE);
                }

               if (pb.getProgress() < 100 && (millisUntilFinished / 1000) != 0) { // Quand le timer est actif
                   TextView tv1 = findViewById(R.id.textView);
                   tv1.setText("Appuyez le plus vite possible avant la fin du temps imparti !!!");
                   Button bt1 = findViewById(R.id.buttonVider);
                   bt1.setVisibility(View.INVISIBLE);
                   ImageView imgv4 = findViewById(R.id.closedChest);
                   imgv4.setVisibility(View.INVISIBLE);
                   ImageView imgv3 = findViewById(R.id.idle);
                   imgv3.setVisibility(View.INVISIBLE);
                }
                if (pb.getProgress() >= 99) { // Quand le joueur a gagné (barre remplie)
                    Button bt1 = findViewById(R.id.buttonVider);
                    Button bt2 = findViewById(R.id.buttonRemplir);
                    Button bt3 = findViewById(R.id.buttonSuite);
                    TextView tv1 = findViewById(R.id.textView);
                    tv1.setText("C'est gagné !!!");
                    timerOn = false;
                    bt1.setVisibility(View.INVISIBLE);
                    bt2.setVisibility(View.INVISIBLE);
                    bt3.setVisibility(View.VISIBLE);
                    pb.setVisibility(View.INVISIBLE);
                    ImageView imgv1 = findViewById(R.id.gifShovel);
                    imgv1.setVisibility(View.INVISIBLE);
                    ImageView imgv2 = findViewById(R.id.gifChest);
                    imgv2.setVisibility(View.VISIBLE);
                    TextView tv2 = findViewById(R.id.chrono);
                    tv2.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFinish() {
                setProgressBarValues(); // call to initialize the progress bar values
            }
        }.start();
        countDownTimer.start();


        if (timerOn == false) { // Quand le joueur a gagné (barre remplie), on arrêter le timer (et donc de vider la barre)
            countDownTimer.cancel();
        }
    }

    public void quitterJeu(View view) {
        // Aucune action pour l'instant
    }
}