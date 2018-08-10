package com.example.com.suamycarritocompras;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.com.suamycarritocompras.MainActivity;
import com.example.com.suamycarritocompras.Producto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ConsultarActivity extends AppCompatActivity {

    private Button btAgregar;
    private DatabaseReference ref;
    private RecyclerView rvProductos;
    private List<Producto> productoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);
        ref =  FirebaseDatabase.getInstance().getReference();
    }

    public void onStart(){
        super.onStart();
        rvProductos = findViewById(R.id.rvProductos);
        btAgregar = findViewById(R.id.btAñadir);
        btAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ir();
            }
        });
        consultar();
    }

    private void ir(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void llenarRecyclerView(){
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvProductos.setHasFixedSize(true);
        rvProductos.setLayoutManager(llm);
        ProductosAdapter adapter = new ProductosAdapter(this, productoList);
        rvProductos.setAdapter(adapter);
    }

    private void consultar(){
        ref.child("Productos_Chocolate").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productoList = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Producto producto = postSnapshot.getValue(Producto.class);
                    producto.setCodigo(postSnapshot.getKey());
                    System.out.println(producto.toString());
                    productoList.add(producto);
                }
                llenarRecyclerView();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }




}
