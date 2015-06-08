package pe.pucp.inf320.toxicity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;

/**
 * Created by yayo on 17/05/15.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Handler handler = new Handler();
        handler.postDelayed(getRunnableStartApp(),3000);

    }

    private Runnable getRunnableStartApp(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        };

        return runnable;

    }
}
