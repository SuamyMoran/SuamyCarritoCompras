package com.example.com.suamycarritocompras;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductosAdapter extends RecyclerView.Adapter<ProductosAdapter.ViewHolder> {



        List<Producto> productoList;
        Context context;

        public ProductosAdapter(Context context, List<Producto> productoList){
            this.productoList = productoList;
            this.context = context;

        }

        private Context getContext(){
            return context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Producto producto = productoList.get(position);

            holder.tvDescripcion.setText(producto.getCaracteristica());
            holder.tvPrecio.setText("$ " + producto.getPrecioxUnidad());

            Picasso.with(holder.itemView.getContext()).load(producto.getUrl()).into(holder.ivproducto);

        }

        @Override
        public int getItemCount() {
            return productoList.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            @BindView(R.id.tvDescripcion)
            TextView tvDescripcion;
            @BindView(R.id.tvPrecio)
            TextView tvPrecio;
            @BindView(R.id.ivproducto)
            ImageView ivproducto;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

                Producto producto = productoList.get(getAdapterPosition());

                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra("PRODUCTO", producto);
                getContext().startActivity(intent);

            }
        }


}
