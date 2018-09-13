package mx.capitalsocial.capitalsocial.model;

public class Servicio {

    private String nombre;
    private String descripcion;
    private String imagen;
    private String logo;
    private String promocion;
    private String facebook;
    private String twitter;

    public Servicio(String nombre, String descripcion, String imagen, String logo, String promocion,
                    String facebook, String twitter) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.logo = logo;
        this.promocion = promocion;
        this.facebook = facebook;
        this.twitter = twitter;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPromocion() {
        return promocion;
    }

    public void setPromocion(String promocion) {
        this.promocion = promocion;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", imagen='" + imagen + '\'' +
                ", logo='" + logo + '\'' +
                ", promocion='" + promocion + '\'' +
                ", facebook='" + facebook + '\'' +
                ", twitter='" + twitter + '\'' +
                '}';
    }
}
