package py.com.desapy.appcrediplus;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import me.drakeet.materialdialog.MaterialDialog;

public class FormIngresoDocumentoActivity extends AppCompatActivity {
    // Formato para SQL date time
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Toolbar mToolbar;
    private EditText etNumeroDocumento;
    private EditText etNumeroCelular;
    private Button btnEnviar;
    // click listners para los componentes
    private View.OnClickListener buttonEnviarListener;
    private View.OnClickListener ocultarTecladoListener;
    private View.OnTouchListener ocultarTecladoTouchListener;

    private MaterialDialog mMaterialDialog;

    private String numeroDocumento;
    private String numeroCelular;




    private RelativeLayout relativeLayoutPrincipal;
    private LinearLayout linearLayoutDatosPersonales; //linear_layout_datos_personales

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_form_ingreso_documento);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        //   mToolbar.setTitle("Banca");
        //   mToolbar.setTitleTextAppearance(getApplication(), R.style.AppTheme);
        //   mToolbar.setSubtitle("Sub titulo");
        //  mToolbar.setLogo(R.drawable.ic_logo);
        setSupportActionBar(mToolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


        asignaciones();
        asignacionOcultarTecladoListener();
    }

    @Override
    protected void onRestoreInstanceState(Bundle recuperaEstado) {
        super.onRestoreInstanceState(recuperaEstado);
        /*editTextNombre1Integrante.setText(recuperaEstado.getString("editTextNombre1Integrante"));
        editTextNombre2Integrante.setText(recuperaEstado.getString("editTextNombre2Integrante"));
        editTextApellido1Integrante.setText(recuperaEstado.getString("editTextApellido1Integrante"));
        editTextApellido2Integrante.setText(recuperaEstado.getString("editTextApellido2Integrante"));
        editTextNroDocumentoIntegrante.setText(recuperaEstado.getString("editTextNroDocumentoIntegrante"));
        editTextTelefonoIntegrante.setText(recuperaEstado.getString("editTextTelefonoIntegrante"));*/
    }

    public void guardaEstado(Bundle savedInstanceState) {
        /*savedInstanceState.putString("editTextNombre1Integrante", editTextNombre1Integrante.getText().toString());
        savedInstanceState.putString("editTextNombre2Integrante", editTextNombre2Integrante.getText().toString());
        savedInstanceState.putString("editTextApellido1Integrante", editTextApellido1Integrante.getText().toString());
        savedInstanceState.putString("editTextApellido2Integrante", editTextApellido2Integrante.getText().toString());
        savedInstanceState.putString("editTextNroDocumentoIntegrante", editTextNroDocumentoIntegrante.getText().toString());
        savedInstanceState.putString("editTextNombre1Integrante", editTextTelefonoIntegrante.getText().toString());*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_alta_reducida, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Vuelve a la pantalla anterior
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void asignaciones() {
        etNumeroDocumento = (EditText) findViewById(R.id.et_numero_documento);
        etNumeroCelular = (EditText) findViewById(R.id.et_numero_celular);
        btnEnviar = (Button) findViewById(R.id.btn_enviar);
        relativeLayoutPrincipal = (RelativeLayout) findViewById(R.id.relative_layout_principal);
        linearLayoutDatosPersonales = (LinearLayout) findViewById(R.id.linear_layout_datos_personales);
        asignacionListenerButtonEnviar();
    }

    private void asignacionListenerButtonEnviar() {
        buttonEnviarListener = new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    buttonEnviarClicado(v);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        btnEnviar.setOnClickListener(buttonEnviarListener);
    }

    protected void buttonEnviarClicado(View v) throws ParseException {
        mMaterialDialog = new MaterialDialog(v.getContext());
        mMaterialDialog.setTitle(R.string.txt_atencion);
        mMaterialDialog.setPositiveButton(R.string.txt_aceptar, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();
            }
        });

        if (etNumeroDocumento.getText().toString().trim().equals("")) {
            mMaterialDialog.setMessage(R.string.txt_mensaje_alerta_campo_nro_documento_vacio);
            mMaterialDialog.show();
        } else {
            if (etNumeroCelular.getText().toString().trim().equals("")) {
                mMaterialDialog.setMessage(R.string.txt_mensaje_alerta_campo_nro_celular_vacio);
                mMaterialDialog.show();
            } else {
                numeroDocumento = etNumeroDocumento.getText().toString().trim();
                numeroCelular = etNumeroCelular.getText().toString().trim();
                Intent intent = new Intent(FormIngresoDocumentoActivity.this, FormIngresoPinActivity.class);
                //intent.putExtra("bancaSeleccionada", banca);
                intent.putExtra("numeroDocumento", numeroDocumento);
                intent.putExtra("numeroCelular", numeroCelular);
                startActivity(intent);
            }
        }
    }


    protected void cancelarIntegranteClicado(View v) throws ParseException {
        onBackPressed();
    }


    public void asignacionListenerSpinnerTipoDucumentoIntegrante() {
        //Metodo necesario si se necesita realizar alguna accion cuando se selecciona un item de la lista

      /*  spinnerTipoDocumentoIntegrante.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {




             // CI = 2, RUC = 20, PAS = 6, CRP = 4, VAR = 5
                String sTipoDocumento = parent.getItemAtPosition(position).toString();
                if (sTipoDocumento.equals("CI")) {
                    tipoDocumento = 2;
                } else if (sTipoDocumento.equals("RUC")) {
                    tipoDocumento = 20;
                } else if (sTipoDocumento.equals("PAS")) {
                    tipoDocumento = 6;
                } else if (sTipoDocumento.equals("CRP")) {
                    tipoDocumento = 4;
                } else if (sTipoDocumento.equals("VAR")) {
                    tipoDocumento = 5;
                } else {
                    tipoDocumento = 0;
                }
                Toast.makeText(parent.getContext(),
                        "OnItemSelectedListener : " + parent.getItemAtPosition(position) + " " + tipoDocumento,
                        Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */
    }




    private void asignacionOcultarTecladoListener() {
        ocultarTecladoListener = new View.OnClickListener() {
            public void onClick(View v) {
                ocultarTeclado();
            }
        };

        ocultarTecladoTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ocultarTeclado();
                return false;
            }
        };

        relativeLayoutPrincipal.setOnClickListener(ocultarTecladoListener);
        linearLayoutDatosPersonales.setOnClickListener(ocultarTecladoListener);

    }

    private void ocultarTeclado() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etNumeroDocumento.getWindowToken(), 0);
    }

    private void controlFormatoTelefono(final EditText campoTelefono) {
        PhoneNumberFormattingTextWatcher phoneNumberFormattingTextWatcher = new PhoneNumberFormattingTextWatcher() {
            //we need to know if the user is erasing or inputing some new character
            private boolean backspacingFlag = false;
            //we need to block the :afterTextChanges method to be called again after we just replaced the EditText text
            private boolean editedFlag = false;
            //we need to mark the cursor position and restore it after the edition
            private int cursorComplement;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //we store the cursor local relative to the end of the string in the EditText before the edition
                cursorComplement = s.length() - campoTelefono.getSelectionStart();
                //we check if the user ir inputing or erasing a character
                if (count > after) {
                    backspacingFlag = true;
                } else {
                    backspacingFlag = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // nothing to do here =D
            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                //what matters are the phone digits beneath the mask, so we always work with a raw string with only digits
                String phone = string.replaceAll("[^\\d]", "");

                if (phone.length() == 1) {
                    campoTelefono.setText("(09");
                    campoTelefono.setSelection(campoTelefono.getText().length() - cursorComplement);
                }

                //if the text was just edited, :afterTextChanged is called another time... so we need to verify the flag of edition
                //if the flag is false, this is a original user-typed entry. so we go on and do some magic
                if (!editedFlag) {

                    //we start verifying the worst case, many characters mask need to be added
                    //example: 999999999 <- 6+ digits already typed
                    // masked: (999) 999-999
                    if (phone.length() >= 7 && !backspacingFlag) {
                        //we will edit. next call on this textWatcher will be ignored
                        editedFlag = true;
                        //here is the core. we substring the raw digits and add the mask as convenient
                        String ans = "(" + phone.substring(0, 4) + ") " + phone.substring(4, 7) + "-" + phone.substring(7);
                        campoTelefono.setText(ans);
                        //we deliver the cursor to its original position relative to the end of the string
                        campoTelefono.setSelection(campoTelefono.getText().length() - cursorComplement);

                        //we end at the most simple case, when just one character mask is needed
                        //example: 99999 <- 3+ digits already typed
                        // masked: (999) 99
                    } else if (phone.length() >= 4 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "(" + phone.substring(0, 4) + ") " + phone.substring(4);
                        campoTelefono.setText(ans);
                        campoTelefono.setSelection(campoTelefono.getText().length() - cursorComplement);
                    }
                    // We just edited the field, ignoring this cicle of the watcher and getting ready for the next
                } else {
                    editedFlag = false;
                }
            }
        };

        campoTelefono.addTextChangedListener(phoneNumberFormattingTextWatcher);
    }


}
