package mx.capitalsocial.capitalsocial.model;

import com.google.gson.annotations.SerializedName;

public class Usuario {

    @SerializedName("agente")
    public String agente;

    @SerializedName("error")
    public Error error;

    @SerializedName("id_user")
    public String id_user;

    @SerializedName("token")
    public String token;

    public Usuario() {
    }

    public Usuario(String agente, Error error, String id_user, String token) {
        this.agente = agente;
        this.error = error;
        this.id_user = id_user;
        this.token = token;
    }

    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "agente='" + agente + '\'' +
                ", error='" + error + '\'' +
                ", id_user='" + id_user + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public class Error {

        @SerializedName("id")
        public String id;

        @SerializedName("message")
        public String message;

        @SerializedName("title")
        public String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
