package mx.capitalsocial.capitalsocial.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import com.squareup.picasso.Picasso;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;

import mx.capitalsocial.capitalsocial.R;

public class DetalleServicioActivity extends AppCompatActivity {

    private TextView textDescripcionServicio;
    private TextView nombreServicioDetalle;
    private ImageView imageHeader;
    private ImageView imageLogo;
    private TextView promocionDetalle;

    private FloatingActionMenu menuFlotante;

    private String imagenServicio;
    private String logoServicio;
    private String nomnreServicio;
    private String promocionServicio;
    private String descServicio;

    private String facebookServicio;
    private String twitterServicio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_servicio);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(new Fade());
        }

        Intent intent = getIntent();

        imagenServicio = intent.getStringExtra("imagen_servicio");
        logoServicio = intent.getStringExtra("logo_servicio");
        nomnreServicio = intent.getStringExtra("nombre_servicio");
        promocionServicio = intent.getStringExtra("promocion_servicio");
        descServicio = intent.getStringExtra("descripcion_servicio");
        facebookServicio = intent.getStringExtra("facebook_servicio");
        twitterServicio = intent.getStringExtra("twitter_servicio");

        imageHeader = findViewById(R.id.imageHeader);
        imageLogo = findViewById(R.id.imageLogo);
        nombreServicioDetalle = findViewById(R.id.nombreServicioDetalle);
        promocionDetalle = findViewById(R.id.promocionServicioDetalle);
        textDescripcionServicio = findViewById(R.id.textDescripcionServicio);

        Picasso.with(getApplication()).load(imagenServicio).into(imageHeader);
        Picasso.with(getApplication()).load(logoServicio).into(imageLogo);
        nombreServicioDetalle.setText(nomnreServicio);
        promocionDetalle.setText(promocionServicio);

        menuFlotante = findViewById(R.id.menu_flotante);
        menuFlotante.setClosedOnTouchOutside(true);

        Lorem lorem = LoremIpsum.getInstance();  // https://github.com/mdeanda/lorem

        String descripcion = String.valueOf(Html.fromHtml("<![CDATA[<body style=\"text-align:justify;color:#222222;\">"
                + lorem.getHtmlParagraphs(4, 6)
                + "</body>]]>"));

        textDescripcionServicio.setText(Html.fromHtml(descripcion));

        showToolbar("Capital Social",true);
    }

    public void showToolbar(String tittle, boolean upButton) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    public void floatActionButtonOnClick(View v) {
        switch (v.getId()) {

            case R.id.fab_email:
                shareEmail();
                break;

            case R.id.fab_whatsapp:
                shareWhatsApp();
                break;

            case R.id.fab_facebook:
                shareFacebook();
                break;

            case R.id.fab_twitter:
                shareTwitter();
                break;

        }
        menuFlotante.close(true);
    }

    private void shareEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                Uri.parse("mailto:" + Uri.encode(nomnreServicio.replace(" ","") + "@hotmail.com")));

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, nomnreServicio);
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Hola");
        startActivity(Intent.createChooser(emailIntent, "Enviar mail a " + nomnreServicio));
    }

    private void shareWhatsApp() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, nomnreServicio + " " + promocionServicio + " \n" +  imagenServicio);
        intent.setPackage("com.whatsapp");
        startActivity(intent);
    }

    private void shareFacebook() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, facebookServicio);
        intent.setPackage("com.facebook.katana");
        startActivity(intent);
    }

    private void shareTwitter() {
        String twitterUri = twitterServicio;
        Intent shareOnTwitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(twitterUri));
        startActivity(shareOnTwitterIntent);
    }
}
