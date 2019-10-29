package id.ac.polinema.tcttcakron;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UpdateAdminAdapter extends RecyclerView.Adapter<UpdateAdminAdapter.MyViewHolder> {
    List<Upload> menuListUpdate;

    public UpdateAdminAdapter(List<Upload> menuList) {
        this.menuListUpdate = menuList;
    }

    @NonNull
    @Override
    public UpdateAdminAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View superHeroView = layoutInflater.inflate(R.layout.activity_update_admin,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(superHeroView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateAdminAdapter.MyViewHolder holder, int position) {
        //ambil satu item hero
        Upload hero = menuListUpdate.get(position);
        //set text heroName berdasarkan data dari model hero
        holder.name.setText(hero.getNameImage());

    }
    @Override
    public int getItemCount() {
        return (menuListUpdate != null) ? menuListUpdate.size() : 0;
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
