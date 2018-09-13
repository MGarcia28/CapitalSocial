package mx.capitalsocial.capitalsocial.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import mx.capitalsocial.capitalsocial.R;
import mx.capitalsocial.capitalsocial.adapter.ServicioAdapterReciclerView;
import mx.capitalsocial.capitalsocial.model.Servicio;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiciosFragment extends Fragment {

    private GridLayoutManager lLayout;
    private EditText inputSearch;

    public ServiciosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_servicios, container, false);

        inputSearch = view.findViewById(R.id.inputSearch);
        inputSearch.setText("");

        showToolbar(getResources().getString(R.string.titulo_toolbar), view);

        lLayout = new GridLayoutManager(getContext(), 2);

        RecyclerView rView =  view.findViewById(R.id.serviciosRecycle);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        final ServicioAdapterReciclerView serviciosAdapter = new ServicioAdapterReciclerView(buildPicture(),R.layout.cardview_servicio, getActivity());
        rView.setAdapter(serviciosAdapter);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                serviciosAdapter.getFilter().filter(s.toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return view;
    }

    public void showToolbar(String tittle, View view) {

        android.support.v7.widget.Toolbar actionBarToolBar = view.findViewById(R.id.toolbar);

        ((AppCompatActivity) getActivity()).setSupportActionBar(actionBarToolBar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");

        TextView tvTitulo = view.findViewById(R.id.toolbar_title);
        tvTitulo.setText(tittle);
    }


    public ArrayList<Servicio> buildPicture(){
        ArrayList<Servicio> servicio = new ArrayList<>();

        servicio.add(new Servicio("Nutrisa", "Mejora tu vida", "http://www.industria-lactea.com.mx/wp-content/uploads/2017/03/nota4marzo2017.jpg","https://static02.ofertia.com.mx/comercios/nutrisa/profile-157457765.v23.png","Jueves 2x1" , "https://www.facebook.com/NutrisaMx/", "https://twitter.com/NutrisaMX"));
        servicio.add(new Servicio("Garabatos", "Es un placer", "http://www.mexicoinforma.mx/images/2017/ABRIL/28/Garabatos4.jpg","http://megustadisfrutar.mx/wp-content/uploads/2017/09/Logo-Garabatos-es-un-placer.png","Café gratis en la compra de un micropastel", "https://www.facebook.com/garabatosmx/?hc_ref=ARR9LWhr8827wUq7DY2KR6oIIAoqdC_RkJjT0EXy74T9N_igTVygogafw6jY1r1_fk4&fref=nf", "https://twitter.com/garabatosmx"));
        servicio.add(new Servicio("Pizza Hut", "Más sabor para todos", "http://static5.businessinsider.com/image/53908351ecad04ca746ba577-480/pizza-hut-cmo-sp.jpg","http://www.sureewoong.com/wp-content/uploads/2014/03/Pizza-hut-logo-official.jpg","Gran Hut Mix $199", "https://www.facebook.com/pizzahutmexico/", "https://twitter.com/PizzaHut_Mexico"));
        servicio.add(new Servicio("Hooters", "Lunch specials", "http://www.hooterscancun.com/imagenes/slider/2.jpg","https://seeklogo.com/images/H/hooters-logo-80B7001A52-seeklogo.com.png","Miercoles Big Daddy $59", "https://www.facebook.com/hootersdemexico/", "https://twitter.com/HootersdeMexico"));
        servicio.add(new Servicio("Mamma Bella", "ITALO-MEXICANA", "http://mammabella.com.mx/wp-content/uploads/2017/07/Captura-de-pantalla-2017-07-28-a-las-17.40.27-e1501626548771.png","http://revistapersonae.com/wp-content/uploads/2017/04/restaurante_abril_01.jpg","Navidad con tus amigos", "https://www.facebook.com/Mamma-Bella-388989147786174/", "https://twitter.com/mammabella_"));
        servicio.add(new Servicio("La Cerveceria", "del Barrio", "http://bangbangbang.com.mx/blog/wp-content/uploads/2016/08/LCADBO3.jpg","http://mallsmexico.com/imagenes//2016/02/23190_La-Cervecer%C3%ADa-de-Barrio.png","15% de descuento", "https://www.facebook.com/lacerveceriadelbarriocoacalco/?ref=br_rs", "https://twitter.com/lacerveceriaslp"));
        servicio.add(new Servicio("Potzollcalli", "Restaurante Méxicano", "https://media-cdn.tripadvisor.com/media/photo-s/09/1f/73/b4/potzollcalli.jpg","http://aulavirtual.potzollcalli.com/pluginfile.php/44/mod_forum/post/53/Logo%20Potzolllcalli%20Sep%2016.png","Jueves pozoleros 2x1", "https://www.facebook.com/PotzollcalliOficial/", "https://twitter.com/potzollcalli"));
        servicio.add(new Servicio("Hot Dog Ramirez", "Original", "https://i.ytimg.com/vi/uAfPixdSld8/hqdefault.jpg","http://www.hotdogramirez.com/mobile/logo.png","Combo Chela, tu paquete + $20", "https://www.facebook.com/hotdogramirez/", "https://twitter.com/dog_ramirez"));

        return servicio;
    }
}
