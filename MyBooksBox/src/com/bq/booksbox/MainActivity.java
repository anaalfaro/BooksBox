package com.bq.booksbox;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxFileInfo;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static final String appKey = "6z1pcoxm5xbguhv";
    private static final String appSecret = "kos3067r4j6kl6f";

    private static final int REQUEST_LINK_TO_DBX = 0;

    private DbxAccountManager dM;

    
   private  ListaLibros listaLibros = new ListaLibros();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

        View conectarButton = findViewById(R.id.conectar1);
        conectarButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                enlazarADropbox();
            }
        });
        dM = DbxAccountManager.getInstance(getApplicationContext(), appKey, appSecret);
	}
	
    private void enlazarADropbox() {
    	if(!dM.hasLinkedAccount()){
    		dM.startLink((Activity)this, REQUEST_LINK_TO_DBX);
    	}
    	try {
			listaEpub(DbxPath.ROOT.toString());
			lanzarListarContenido();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    protected void onResume() {
		super.onResume();
		if (dM.hasLinkedAccount()) {
		    try {
				listaEpub(DbxPath.ROOT.toString());
				lanzarListarContenido();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			dM.startLink((Activity)this, REQUEST_LINK_TO_DBX);
		}
	}

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_LINK_TO_DBX) {
            if (resultCode == Activity.RESULT_OK) {
            	// Ya tenemos acceso a ficheros de dropbox
            	try {
					listaEpub(DbxPath.ROOT.toString());
					
					lanzarListarContenido();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
            } else {
            	 // La conexión con dropbox ha fallado o la ha cancelado el usuario
            	Toast toastCancelacion = Toast.makeText(getApplicationContext(),
                                "Fallo en conexión", Toast.LENGTH_SHORT);      
            	toastCancelacion.show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    
    private void lanzarListarContenido() {
    	if(listaLibros!=null){
        	Intent intentListar = new Intent(this, ListarContenido.class);  
        	Bundle contenedor = new Bundle();
        	contenedor.putParcelable("lista", listaLibros); 
        	intentListar.putExtras(contenedor);
        	startActivity(intentListar); 
        	this.finish();
        }
        else{
        	//si no hay contenido que mostrar, aviso al usuario
        	Toast avisoNoHayLibros = Toast.makeText(getApplicationContext(),"No hay contenido que mostrar", Toast.LENGTH_SHORT);
        	avisoNoHayLibros.show();
        } 
		
	}


	private void listaEpub(String path) throws IOException {
    	try {		
            DbxFileSystem FileSystem = DbxFileSystem.forAccount(dM.getLinkedAccount());

            // Obtenemos la lista de archivos almacenada en el directorio raíz
            List<DbxFileInfo> FileList = FileSystem.listFolder(new DbxPath(DbxPath.ROOT+path));
            
            String titulo;
            Date fecha;
            // Recorro la lista de ficheros obtenida de dropbox
            for (DbxFileInfo file : FileList) {
            	// Si el archivo es una carpeta, hago llamada recursiva para obtener los ficheros que contiene
            	if(file.isFolder){
            		listaEpub(path+file.path);
            	}else{
            		// Para archivos .epub miro que los últimos 5 caracteres de la ruta coincidan con la extensión
            		String formato = file.path.toString();
                	formato = formato.substring(formato.length()-5, formato.length());
                	if(formato.equals(".epub")){
               		
                		titulo = file.path.toString();
                		//quito la ruta y el formato del archivo para quedarme solo con el nombre
                		titulo = titulo.substring(titulo.lastIndexOf("/")+1, titulo.length()-5);
                		
                		fecha =file.modifiedTime; 
                		
                		Libro l = new Libro(titulo, fecha);
                		listaLibros.add(l);          		       		
                	}
                	// En otro caso paso al siguiente archivo 
            	}	        
            }
        } catch (IOException e) {
        	throw new IOException(e.toString());
        }
    }
	}

