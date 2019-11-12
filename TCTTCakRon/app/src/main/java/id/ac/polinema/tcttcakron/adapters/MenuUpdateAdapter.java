package id.ac.polinema.tcttcakron.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import id.ac.polinema.tcttcakron.R;
import id.ac.polinema.tcttcakron.UpdateMenuAdmin;
import id.ac.polinema.tcttcakron.models.Upload;

public class MenuUpdateAdapter extends RecyclerView.Adapter<MenuUpdateAdapter.MyViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;

    public MenuUpdateAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.menu_list_update, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Upload upload = mUploads.get(position);
        holder.name.setText(upload.getNameImage());
        holder.harga.setText(String.valueOf(upload.getHarga()));
        Glide.with(mContext).load(upload.getImageUrl()).apply(new RequestOptions().centerCrop().override(500, 500)).into(holder.imageView);
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(mContext, UpdateMenuAdmin.class);
                 intent.putExtra("nama", upload.getNameImage());
                 intent.putExtra("harga", String.valueOf(upload.getHarga()));
                 intent.putExtra("image", upload.getImageUrl());
                 view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
         return mUploads.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, harga;
        public ImageView imageView;
        public Button update;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textMakananItem2);
            harga = itemView.findViewById(R.id.textHargaItem2);
            imageView = itemView.findViewById(R.id.imageViewItem2);
            update = itemView.findViewById(R.id.buttonUpdateItem);
        }
    }
}