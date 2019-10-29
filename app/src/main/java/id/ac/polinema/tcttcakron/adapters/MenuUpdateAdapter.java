package id.ac.polinema.tcttcakron.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import id.ac.polinema.tcttcakron.R;
import id.ac.polinema.tcttcakron.Upload;
import id.ac.polinema.tcttcakron.models.MenuUpdate;

public class MenuUpdateAdapter extends RecyclerView.Adapter<MenuUpdateAdapter.MyViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;


    public MenuUpdateAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public MenuUpdateAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View superHeroView = layoutInflater.inflate(R.layout.activity_update_admin,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(superHeroView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuUpdateAdapter.MyViewHolder holder, int position) {
        //ambil satu item hero
        Upload upload = mUploads.get(position);
        holder.name.setText(upload.getNameImage());
        holder.harga.setText(String.valueOf(upload.getHarga()));
        Picasso.with(mContext).load(upload.getImageUrl()).fit().centerCrop().into(holder.imageView);
        //set text heroName berdasarkan data dari model hero


    }
    @Override
    public int getItemCount() {
        return (mUploads != null) ? mUploads.size() : 0;
//        return 0;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, harga;
        public ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemList);
        }
    }
}