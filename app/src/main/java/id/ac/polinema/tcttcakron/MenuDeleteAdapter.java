package id.ac.polinema.tcttcakron;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuDeleteAdapter extends RecyclerView.Adapter<MenuDeleteAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;

    public MenuDeleteAdapter(Context context, List<Upload> uploads){
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.menu_list_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.nama.setText(uploadCurrent.getNameImage());
        holder.harga.setText(String.valueOf(uploadCurrent.getHarga()));
        Picasso.with(mContext).load(Uri.parse(uploadCurrent.getImageUrl())).fit().centerCrop().into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView nama, harga;
        public ImageView image;

        public ImageViewHolder(View itemView){
            super(itemView);

            nama = itemView.findViewById(R.id.textMakananItem);
            harga = itemView.findViewById(R.id.textHargaItem);
            image = itemView.findViewById(R.id.imageViewItem);
        }
    }
}
