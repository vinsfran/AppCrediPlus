package py.com.desapy.appcrediplus;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class InicioActivity extends Activity {

    // Duración en milisegundos que se mostrará el splash
    private final int DURACION_SPLASH = 3000; // 3 segundos

    // Definine variable para objeto de tipo configuración
    // private Configuracion conf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_inicio);

        // Se instancia el objeto configuración
        //   conf = new Configuracion(this);

        // Starting a new thread to wait 3 seconds before initializing system
    /*    Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(3000);
                        setupSystem();
                    }
                } catch (InterruptedException ex) {
                    Toast.makeText(getApplicationContext(), "La app no puede iniciar.", Toast.LENGTH_LONG).show();
                }
            }
        };
        thread.start();
*/
        new Handler().postDelayed(new Runnable() {
            public void run() {
                // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación
                setupSystem();
                finish();
            }
        }, DURACION_SPLASH);

    }

	/*
     *
	 * @see android.app.Activity#onRestart()
	 */

    @Override
    protected void onRestart() {
        // calling back setup procedure on restart
        super.onRestart();
        setupSystem();
    }

    /*
     * This method intializes the system storage.Tries to retrieve saved system
     * settings from database. Initializes system setup on failure
     */
    private void setupSystem() {

        // Redirecciona a la pantalla de Login
        Intent itemintent = new Intent(InicioActivity.this, LoginActivity.class);
        startActivity(itemintent);
        finish();
    }
}
