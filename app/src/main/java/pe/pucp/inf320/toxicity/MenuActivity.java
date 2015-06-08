package pe.pucp.inf320.toxicity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by yayo on 17/05/15.
 */
public class MenuActivity extends Activity {

    Button btnDecibeles;
    Button btnToxicidad;
    Button btnComingSoon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_layout);

        btnDecibeles = (Button)findViewById(R.id.btnDecibeles);
        btnToxicidad = (Button)findViewById(R.id.btnToxicidad);
        btnComingSoon = (Button)findViewById(R.id.btnComingSoon);

        btnDecibeles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, DecibelesActivity.class);
                startActivity(intent);
            }
        });

        btnToxicidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, UltravioletaActivity.class);
                startActivity(intent);
            }
        });

        btnComingSoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ComingSoonActivity.class);
                startActivity(intent);
            }
        });

    }
}
