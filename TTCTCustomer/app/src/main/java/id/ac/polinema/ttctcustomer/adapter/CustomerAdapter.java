package id.ac.polinema.ttctcustomer.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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

import id.ac.polinema.ttctcustomer.R;
import id.ac.polinema.ttctcustomer.Upload;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;

    public CustomerAdapter(Context context, List<Upload> uploads){
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.customer_delivery, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.nama.setText(uploadCurrent.getNameImage());
        holder.harga.setText(String.valueOf(uploadCurrent.getHarga()));
        Glide.with(mContext).load(uploadCurrent.getImageUrl()).apply(new RequestOptions().centerCrop().override(500, 500)).into(holder.image);
        holder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.counter++;
                holder.amount.setText(Integer.toString(holder.counter));
//                handlerOnClickIncrease(view);
            }
        });

        holder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.counter == 0){
//            Toast.makeText(TransactionOffline.this, "Tidak bisa kurang dari 0", Toast.LENGTH_SHORT).show();
                } else {
                    holder.counter--;
                    holder.amount.setText(Integer.toString(holder.counter));
                }

//                handlerOnClickDecrease(view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView nama, harga;
        public ImageView image;
        TextView amount;
        Button increase, decrease;
        int counter = 0;

        public ImageViewHolder(View itemView){
            super(itemView);

            nama = itemView.findViewById(R.id.nama_menu);
            harga = itemView.findViewById(R.id.harga_menu);
            image = itemView.findViewById(R.id.gambar_menu);
            amount = itemView.findViewById(R.id.quantity_menu);
            increase = itemView.findViewById(R.id.increase_button);
            decrease = itemView.findViewById(R.id.decrease_button);

            amount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        public void handlerOnClickDecrease(View view) {
            if (counter == 0){
//            Toast.makeText(TransactionOffline.this, "Tidak bisa kurang dari 0", Toast.LENGTH_SHORT).show();
            } else {
                counter--;
                amount.setText(Integer.toString(counter));
            }
        }

        public void handlerOnClickIncrease(View view) {
            counter++;
            amount.setText(Integer.toString(counter));
        }
    }


}
