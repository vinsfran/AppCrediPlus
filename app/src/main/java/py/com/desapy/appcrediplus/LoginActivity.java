package py.com.desapy.appcrediplus;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import me.drakeet.materialdialog.MaterialDialog;

public class LoginActivity extends AppCompatActivity {

    //private Banca banca;
/*    private Pais pais;
    private TipoDocumento tipoDocumento;
    private PersonaManager personaManager;
    private PaisManager paisManager;
    private TipoDocumentoManager tipoDocumentoManager;
    private BancaManager bancaManagerManager;*/

    private TextView mensajes;
    private EditText editTextUsuario;
    private EditText editTextContrasena;
    private TextView textViewVersion;

    private MaterialDialog mMaterialDialog;
    private ProgressDialog pDialog;
    private Snackbar mSnackbar;

    private String crearBanca;

    private RelativeLayout relativeLayoutPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_login);
        // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //     personaManager = new PersonaManager(this);
        //     paisManager = new PaisManager(this);

        //banca = (Banca) getIntent().getExtras().getSerializable("bancaSeleccionada");
        //     crearBanca = getIntent().getExtras().getString("crearBanca");

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialogo_login, null);

        //     tipoDocumentoManager = new TipoDocumentoManager(this);
        //     bancaManagerManager = new BancaManager(this);
        editTextUsuario = (EditText) promptsView.findViewById(R.id.edit_text_usuario);
        editTextContrasena = (EditText) promptsView.findViewById(R.id.edit_text_contrasena);
        textViewVersion = (TextView) promptsView.findViewById(R.id.text_view_version);
        mensajes = (TextView) promptsView.findViewById(R.id.edit_text_mensajes);

        relativeLayoutPrincipal = (RelativeLayout) findViewById(R.id.relative_layout_principal);

        relativeLayoutPrincipal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextUsuario.getWindowToken(), 0);
            }
        });

        mMaterialDialog = new MaterialDialog(this);
        mMaterialDialog.setTitle(R.string.txt_acceso_sistema);
        mMaterialDialog.setContentView(promptsView);
        mMaterialDialog.setPositiveButton(R.string.txt_ingresar, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackbar = Snackbar.make(v, "", Snackbar.LENGTH_LONG);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mSnackbar.setActionTextColor(getResources().getColor(R.color.colorCeleste, v.getContext().getTheme()));
                } else {
                    mSnackbar.setActionTextColor(getResources().getColor(R.color.colorCeleste));
                }
                mSnackbar.setAction(R.string.txt_aceptar, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mSnackbar.dismiss();
                    }
                });

                if (editTextUsuario.getText().toString().equals("") && editTextContrasena.getText().toString().equals("")) {
                    mSnackbar.setText(R.string.txt_mensaje_alerta_campos_vacios);
                    mSnackbar.show();
                } else if (editTextUsuario.getText().toString().equals("")) {
                    mSnackbar.setText(R.string.txt_mensaje_alerta_campo_usuario_vacio);
                    mSnackbar.show();
                } else if (editTextContrasena.getText().toString().equals("")) {
                    mSnackbar.setText(R.string.txt_mensaje_alerta_campo_contrasena_vacio);
                    mSnackbar.show();
                } else {
                    validarUsuario();
                    //ingresarAlSistema("", 1, 30, 0);
                }
            }
        });
        mMaterialDialog.setNegativeButton(R.string.txt_cancelar, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
                finish();
            }
        });
        mMaterialDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //Metodo para obtener el IMEI del dispositivo
    private String getIMEI() {
        TelephonyManager mngr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        return mngr.getDeviceId();
    }

    //Metodo para obtener el Nombre de version de la app
    private String getVersionName() {
        PackageManager manager = this.getPackageManager();
        PackageInfo info;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void validarUsuario() {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Validando Usuario...");
        showpDialog();
        JSONObject dato = new JSONObject();
        ingresarSistema();
        /*try {
            dato.put("pUsuario", editTextUsuario.getText().toString().trim());
            dato.put("pPass", editTextContrasena.getText().toString().trim());
            //dato.put("pPass", "TemporaL753");
            dato.put("pImei", imeiDispositivo);
            dato.put("pVersion", nombreVersion);
            RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                    "http://192.168.30.35:8080/AppFielcoJE/rest/WsLogin/",
                    dato,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if (!response.isNull("Respuesta")) {
                                try {
                                    String respuesta = response.getString("Respuesta");
                                    String fechaBt = response.getString("FechaBT");
                                    Integer minIntegrantes = response.getInt("MinIntegrantes");
                                    Integer maxIntegrantes = response.getInt("MaxIntegrantes");
                                    Integer contadorBanca = response.getInt("ContadorBanca");
                                    if (respuesta.equals("OK")) {
                                        if (!fechaBt.equals("0000-00-00")) {
                                            hidePDialog();
                                            ingresarSistema(fechaBt, minIntegrantes, maxIntegrantes, contadorBanca);
                                        } else {
                                            hidePDialog();
                                            mMaterialDialog.setMessage("No se pudo obtener fecha de BT");
                                            mMaterialDialog.show();
                                        }
                                    } else {
                                        hidePDialog();
                                        mMaterialDialog.setMessage(respuesta);
                                        mMaterialDialog.show();
                                    }
                                } catch (JSONException e) {
                                    hidePDialog();
                                    mMaterialDialog.setTitle(R.string.txt_titulo_alerta_json_exception);
                                    mMaterialDialog.setMessage(e.getMessage());
                                    mMaterialDialog.show();
                                }
                            } else {
                                hidePDialog();
                                mMaterialDialog.setMessage("No hay respuesta del servidor");
                                mMaterialDialog.show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hidePDialog();
                    mMaterialDialog.setTitle(R.string.txt_titulo_alerta_conectividad_error);
                    String mensaje = "Error code: ";
                    if(error.networkResponse == null){
                        mensaje = "No se pudo realizar la operación intente de nuevo";
                    }else{
                        mensaje = mensaje + String.valueOf(error.networkResponse.statusCode);
                    }
                    mMaterialDialog.setMessage(mensaje);
                    mMaterialDialog.show();
                }
            });
            request.setShouldCache(false);
            request.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(request);
        } catch (JSONException e) {
            hidePDialog();
            mMaterialDialog.setTitle(R.string.txt_titulo_alerta_json_exception);
            mMaterialDialog.setMessage(e.getMessage());
            mMaterialDialog.show();
        }*/
    }

    public void ingresarSistema() {
   /*public void ingresarSistema(String fecha, Integer minIntegrantes, Integer maxIntegrantes, Integer contadorBanca) {
        conf.setLoginUsuario(editTextUsuario.getText().toString().trim().toUpperCase());
        conf.setImeiDispositivo(imeiDispositivo);
        conf.setMinIntegrantes(minIntegrantes);
        conf.setMaxIntegrantes(maxIntegrantes);
        conf.setContadorBanca(contadorBanca);
        conf.setFechaBT(fecha);*/
        Intent itemintent = new Intent(LoginActivity.this, FormIngresoDocumentoActivity.class);
        startActivity(itemintent);
        finish();
    }

    private void showpDialog() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    private void hidePDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

}
