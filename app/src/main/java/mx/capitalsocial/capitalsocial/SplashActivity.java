package mx.capitalsocial.capitalsocial;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import mx.capitalsocial.capitalsocial.utils.Utils;
import mx.capitalsocial.capitalsocial.view.ContenedorActivity;
import mx.capitalsocial.capitalsocial.view.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                if( Utils.getTokenUsuario(getApplicationContext()).isEmpty()) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, ContenedorActivity.class));
                }

                finish();
            }

        }, secondsDelayed * 5000);
    }

}
