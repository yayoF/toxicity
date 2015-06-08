package pe.pucp.inf320.toxicity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import pe.pucp.inf320.toxicity.conector.HTTPConnector;
import pe.pucp.inf320.toxicity.constants.FrameworkConstans;
import pe.pucp.inf320.toxicity.settings.LoadViewTask;
import pe.pucp.inf320.toxicity.settings.Loadingable;

/**
 * Created by yayo on 2/06/15.
 */
public class UltravioletaActivity extends Activity implements Loadingable {

    public String ciudad = "";
    public String estado = "";
    public int uvindex =0;
    public int uvalert = 0;

    public TextView txtciudad;
    public TextView txtestado;
    public TextView txtuvalert;
    public TextView txtuvindex;

    public TextView txtVerif1;
    public TextView txtVerif2;
    public TextView uvi;

    public Button btnVerifica;

    private RelativeLayout barDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ultravioleta_layout);

        txtciudad = (TextView)findViewById(R.id.txtCiudad);
        txtestado = (TextView)findViewById(R.id.txtEstado);
        txtuvindex = (TextView)findViewById(R.id.txtUvAlert);
        txtuvalert = (TextView)findViewById(R.id.txtUvIndex);

        txtVerif1 = (TextView)findViewById(R.id.txtVerif1);
        txtVerif2 = (TextView)findViewById(R.id.txtVerif2);
        uvi = (TextView)findViewById(R.id.uvi);
        btnVerifica = (Button)findViewById(R.id.btnvideo);

        barDB = (RelativeLayout) findViewById(R.id.bardb);

        new LoadViewTask(UltravioletaActivity.this).execute();
    }

    @Override
    public void heavyTask() {

        HTTPConnector poster = new HTTPConnector();
        String result = "";

        try {
            //String req = gs.toJson();
            String path = FrameworkConstans.SERVER_DOMAIN+ FrameworkConstans.CONSULTA_PATH;
            result = poster.getREST(path);
            JSONArray jsonResponse = new JSONArray(result);
            JSONObject jsonObject = (JSONObject)jsonResponse.get(0);
            ciudad = jsonObject.getString("CITY");
            estado = jsonObject.getString("STATE");
            uvindex = jsonObject.getInt("UV_INDEX");
            uvalert  = jsonObject.getInt("UV_ALERT");

        } catch (Exception d) {
            d.printStackTrace();
        }

    }

    @Override
    public void afterTask() {
        txtciudad.setText("Ciudad : " + ciudad);
        //txtestado.setText(estado);
        //txtuvalert.setText(String.valueOf(uvalert));
        uvi.setText(String.valueOf(uvindex + " uvi"));
        final float scale = getResources().getDisplayMetrics().density;
        Double width;
        width= (Double.valueOf(10-uvindex) * 250 * scale)/10;

        RelativeLayout.LayoutParams lyParams = new RelativeLayout.LayoutParams(
                width.intValue(), barDB.getHeight());
        lyParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        barDB.setLayoutParams(lyParams);

        btnVerifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtVerif1.setVisibility(View.VISIBLE);
                txtVerif2.setVisibility(View.VISIBLE);
                //String parts[] =((String)decibelsTx.getText()).split(" ");
                //double valorX = Double.valueOf(parts[0]);
                if ( (uvindex >= 0) && (uvindex <= 2) ) {
                    txtVerif2.setText("bajo");
                } else if ( (uvindex >= 3) && (uvindex <= 5) ) {
                    txtVerif2.setText("moderado");
                } else if ( (uvindex >= 6) && (uvindex <= 7) ) {
                    txtVerif2.setText("alto");
                } else if ( (uvindex >= 8) && (uvindex <= 10) ) {
                    txtVerif2.setText("muy alto");
                }else if ( (uvindex >= 11) && (uvindex <= 15) ) {
                    txtVerif2.setText("extremadamente alta");
                }


            }
        });

    }
}
