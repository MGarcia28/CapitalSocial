package mx.capitalsocial.capitalsocial.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;

import mx.capitalsocial.capitalsocial.R;
import mx.capitalsocial.capitalsocial.model.Usuario;
import mx.capitalsocial.capitalsocial.service.ServiceHandler;
import mx.capitalsocial.capitalsocial.utils.Utils;

public class LoginActivity extends AppCompatActivity {

    private final static String URL_SERVICIOS = "https://agentemovil.pagatodo.com/AgenteMovil.svc/agMov/login";
    private AutoCompleteTextView mUsuarioView;
    private EditText mContraseniaView;
    private LoginButton loginfacebook;
    private View mProgressView;
    private Button fb;
    private CallbackManager facebookCallbackManager;
    static InputStream json;
    private String msjError = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        mUsuarioView = findViewById(R.id.usuario);
        mContraseniaView = findViewById(R.id.contrasenia);
        mProgressView = findViewById(R.id.login_progress);

        facebookCallbackManager = CallbackManager.Factory.create();

        fb = findViewById(R.id.fb);
        loginfacebook = findViewById(R.id.facebook_sign_in_button);
        loginfacebook.setReadPermissions(Arrays.asList("public_profile", "user_friends", "email"));

        loginfacebook.registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(final LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String token = "";
                                    String name = "";
                                    String email = "";
                                    String uriPicture = "";

                                    if (loginResult.getAccessToken().getApplicationId() != null) {
                                        token = loginResult.getAccessToken().getApplicationId();
                                    }
                                    if (object.getString("name") != null) {
                                        name = object.getString("name");
                                    }
                                    if (object.getString("picture") != null) {
                                        JSONObject imagen = new JSONObject(object.getString("picture"));
                                        JSONObject imagen2 = new JSONObject(imagen.getString("data"));
                                        uriPicture = imagen2.getString("url");
                                    }

                                    Utils.guardarTokenUsuario(getApplicationContext(), name, token);

                                    startActivity(new Intent(LoginActivity.this, ContenedorActivity.class));
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "name, email,  picture");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.w("LogIn Facebook ","CANCELADO");
            }

            @Override
            public void onError(FacebookException error) {
                Log.w("ERROR ", error.toString());
                error.printStackTrace();
            }
        });


        /**
         * BOTON INICIO DE SESION LOGIN
         *
         */
        Button mBotonInicioSesion = findViewById(R.id.boton_iniciar_sesion);
        mBotonInicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              if (Utils.comprobarConexion(LoginActivity.this)) {
                  iniciarSesion();
              } else {
                  Snackbar.make(LoginActivity.this.mUsuarioView, getResources().getString(R.string.sat_sin_internet_revisa) , Snackbar.LENGTH_LONG).show();
              }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void iniciarSesion() {

        mUsuarioView.setError(null);
        mContraseniaView.setError(null);

        String usuario = mUsuarioView.getText().toString();
        String contrasenia = mContraseniaView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(contrasenia)) {
            mContraseniaView.setError(getString(R.string.error_contrasenia_invalida));
            focusView = mContraseniaView;
            cancel = true;
        }

        if (TextUtils.isEmpty(usuario)) {
            mUsuarioView.setError(getString(R.string.error_usuario_requerido));
            focusView = mUsuarioView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
            return;
        } else {

            mProgressView.setVisibility(View.VISIBLE);

            Login task = new Login(usuario, contrasenia);
            task.execute();
        }
    }

    public class Login extends AsyncTask<Void, Void, Boolean> {

        private final String usuario;
        private final String password;

        Login(String usuario, String password) {
            this.usuario = usuario;
            this.password = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            JsonObject jsonObject = new JsonObject();
            JsonObject jObject = new JsonObject();

            jObject.addProperty("user", usuario);
            jObject.addProperty("pass", password);

            jsonObject.add("data",jObject);

            json = ServiceHandler.makeServiceCall(URL_SERVICIOS, 2, jsonObject.toString());

            //boolean valido = validarUsuario(json);

            return validarUsuario(json);
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mProgressView.setVisibility(View.GONE);

            if (success) {
                Intent intent = new Intent(LoginActivity.this, ContenedorActivity.class);
                startActivity(intent);
                finish();
            } else {
                Snackbar.make(LoginActivity.this.mUsuarioView, msjError , Snackbar.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mProgressView.setVisibility(View.GONE);
        }
    }

    public void onClick(View v) {
        if (v == fb) {
            loginfacebook.performClick();
        }
    }

    public boolean validarUsuario(InputStream inputStreamUsuario) {

        Gson gson = new Gson();
        Reader reader = null;
        boolean status = false;

        try {

            if (inputStreamUsuario != null) {

                reader = new InputStreamReader(inputStreamUsuario, "UTF-8");
                JsonParser parser = new JsonParser();
                JsonObject rootObejct = parser.parse(reader).getAsJsonObject();

                Usuario usuario = gson.fromJson(rootObejct, Usuario.class);

                if (usuario.getError() == null) {
                    if (usuario.getToken() != null) {
                        Utils.guardarTokenUsuario(getApplicationContext(), usuario.getAgente(), usuario.getToken());
                        status = true;
                    }
                } else {
                    msjError = usuario.getError().getMessage();
                    status = false;
                }
            } else {
                msjError = "El servicio no está disponible, intenta más tarde";
            }
        } catch (IOException e) {
            e.printStackTrace();
            status = false;
        }
        return status;
    }
}
