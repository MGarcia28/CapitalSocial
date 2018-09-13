package mx.capitalsocial.capitalsocial.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import mx.capitalsocial.capitalsocial.R;
import mx.capitalsocial.capitalsocial.model.Servicio;
import mx.capitalsocial.capitalsocial.view.DetalleServicioActivity;

public class ServicioAdapterReciclerView extends RecyclerView.Adapter<ServicioAdapterReciclerView.ServicioViewHolder> implements Filterable {

    private ArrayList<Servicio> servicios;
    private int resourse;
    private Activity activity;

    private ArrayList<Servicio> filtroServicio;
    private ArrayList<Servicio> serviciosAll;

    public ServicioAdapterReciclerView(ArrayList<Servicio> servicios, int resourse, Activity activity) {
        this.serviciosAll = servicios;
        this.servicios = servicios;
        this.resourse = resourse;
        this.activity = activity;
        filtroServicio = new ArrayList<>();
    }

    @Override
    public ServicioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resourse,parent,false);
        return new ServicioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ServicioViewHolder holder, final int position) {

        final Servicio servicio = servicios.get(position);

        holder.nombreServicio.setText(servicio.getNombre());
        holder.descripcionServicio.setText(servicio.getDescripcion());
        Picasso.with(activity).load(servicio.getImagen()).into(holder.logoServicio);

        holder.logoServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(activity, DetalleServicioActivity.class);

                intent.putExtra("imagen_servicio", servicio.getImagen());
                intent.putExtra("logo_servicio", servicio.getLogo());
                intent.putExtra("nombre_servicio", servicio.getNombre());
                intent.putExtra("promocion_servicio", servicio.getPromocion());
                intent.putExtra("descripcion_servicio", servicio.getDescripcion());
                intent.putExtra("facebook_servicio", servicio.getFacebook());
                intent.putExtra("twitter_servicio", servicio.getTwitter());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Explode explode = new Explode();
                    explode.setDuration(100);
                    activity.getWindow().setExitTransition(explode);
                    activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, activity.getString(R.string.transitionname_servicio)).toBundle());
                }  else {
                    activity.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return servicios.size();
    }

    public class ServicioViewHolder extends RecyclerView.ViewHolder {

        private ImageView logoServicio;
        private TextView nombreServicio;
        private TextView descripcionServicio;

        public ServicioViewHolder(View itemView) {
            super(itemView);
            logoServicio   =  itemView.findViewById(R.id.logo_servicio_card);
            nombreServicio  =  itemView.findViewById(R.id.tv_nombre_servicio_card);
            descripcionServicio  =  itemView.findViewById(R.id.tv_descripcion_servicio_card);
        }
    }

    private Filter filtrarServicio;
      @Override
      public Filter getFilter() {
          if (filtrarServicio == null) {
              filtrarServicio = new FiltrarServicio();
          }
          return filtrarServicio;
    }

    private class FiltrarServicio extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            filtroServicio.clear();
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                results.values = serviciosAll;
                results.count = serviciosAll.size();

            } else {

                for (final Servicio s : serviciosAll) {
                    if (s.getNombre().toUpperCase().trim().contains(constraint.toString().toUpperCase())) {
                        filtroServicio.add(s);
                    }
                }

                results.values = filtroServicio;
                results.count = filtroServicio.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            servicios = (ArrayList<Servicio>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
