package com.example.doanandroid02.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanandroid02.ItemClickListener;
import com.example.doanandroid02.R;
import com.example.doanandroid02.activity.ProductDetailsActivity;
import com.example.doanandroid02.models.Product;
import com.example.doanandroid02.repositories.ProductRepository;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.ItemHolder> {

    List<Product> products;
    Context context;

    public NewProductAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public ImageView imgSanPham;
        public TextView textTenSp;
        public TextView textGiaSp;
        public CardView cardView;
        public ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public ItemHolder(@NonNull View itemView){
            super(itemView);
            imgSanPham = itemView.findViewById(R.id.imgSanPhamDesc);
            textTenSp = itemView.findViewById(R.id.textTenSpDesc);
            textGiaSp = itemView.findViewById(R.id.textGiaSpDesc);

            cardView = itemView.findViewById(R.id.cardViewDesc);

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product_desc,null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Product product = products.get(position);
        holder.textTenSp.setText(product.getTen());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.textGiaSp.setText(decimalFormat.format(product.gia_sp) + "VND");
        Picasso.with(context).load("http://192.168.56.1/doan-laravel/public/upload/" + product.getAnh())
                .resize(1000,1000)
                .centerCrop()
                .into(holder.imgSanPham);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    Toast.makeText(context, " " + products.get(position), Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, ProductDetailsActivity.class);
                    int product_id = ProductRepository.products.get(position).getId();
                    String product_name = ProductRepository.products.get(position).getTen();
                    String product_img = ProductRepository.products.get(position).getAnh();
                    long product_price = ProductRepository.products.get(position).getGia_sp();
                    String product_inf = ProductRepository.products.get(position).getThong_tin_cu_the();

                    intent.putExtra("id",product_id);
                    intent.putExtra("name", product_name);
                    intent.putExtra("img", product_img);
                    intent.putExtra("price", product_price);
                    intent.putExtra("info", product_inf);
                    context.startActivity(intent);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return products.size();
    }
}
