package com.example.com.suamycarritocompras;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference ref;
    private StorageReference storage;
    private EditText etCodigo;
    private EditText etCaracteristica;
    private EditText etCodigoBarra;
    private EditText etPreciox12;
    private EditText etPrecioxU;
    private ImageView ivproducto;
    private Button btnGuardar;
    private Button btnEliminar, btntomarfoto;
    private Producto producto;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivproducto = findViewById(R.id.ivproducto);
        pd = new ProgressDialog(this);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        storage = FirebaseStorage.getInstance().getReference();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            pd.setMessage("Subiendo imagen");
            pd.show();

            String key = etCodigo.getText().toString().replaceAll(" ", "");

            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            StorageReference imagepath = storage.child("Productos").child(key);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] datas = baos.toByteArray();
            UploadTask uploadTask = imagepath.putBytes(datas);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.dismiss();
                    Uri download = taskSnapshot.getDownloadUrl();
                    Picasso.with(MainActivity.this).load(download).fit().centerCrop().into(ivproducto);
                    Toast.makeText(MainActivity.this, "Imagen Subida", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        ivproducto = findViewById(R.id.ivproducto);
        etCodigo = findViewById(R.id.etCodigo);
        etCaracteristica = findViewById(R.id.etCaracteristica);
        etCodigoBarra = findViewById(R.id.etCodigoBarra);
        etPreciox12 = findViewById(R.id.etPrecio);
        etPrecioxU = findViewById(R.id.etPrecioU);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btntomarfoto = findViewById(R.id.btntomarfoto);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarFirebase();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();

            }
        });
        btntomarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        });

        getIntentData();
    }

    private void ir() {
        Intent intent = new Intent(this, ConsultarActivity.class);
        startActivity(intent);
    }

    private void eliminar() {
        ref.child("Productos_Chocolate").child(etCodigo.getText().toString()).removeValue();
    }

    private void getIntentData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            producto = (Producto) extras.getSerializable("PRODUCTO");
            etCodigo.setText(producto.getCodigo());
            etCodigoBarra.setText(producto.getCodigodeBarra());
            etCaracteristica.setText(producto.getCaracteristica());
            etPrecioxU.setText(producto.getPrecioxUnidad() + "");
            etPreciox12.setText(producto.getPrecioxCaja12Unidades() + "");
        }
    }

    public void guardarFirebase() {
        storage.child("Productos/" + etCodigo.getText().toString()).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        producto = new Producto(etCodigo.getText().toString(), etCaracteristica.getText().toString(),
                                etCodigoBarra.getText().toString(), Double.valueOf(etPreciox12.getText().toString()),
                                Double.valueOf(etPrecioxU.getText().toString()),
                                uri.toString());
                        ref.child("Productos_Chocolate").child(producto.getCodigo()).setValue(producto);
                        limpiar();
                        ir();
                    }
                });
    }

    public void limpiar() {
        etCodigo.setText("");
        etCaracteristica.setText("");
        etCodigoBarra.setText("");
        etPreciox12.setText("");
        etPrecioxU.setText("");
    }
}
