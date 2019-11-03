package id.ac.polinema.tcttcakron;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private MenuAdapter mMenuAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Upload> menus, List<String> keys){
        mContext = context;
        mMenuAdapter = new MenuAdapter(menus, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mMenuAdapter);
    }

    class MenuItemView extends RecyclerView.ViewHolder{
        private TextView namaMenu;
        private TextView harga;
        private String key;

        public MenuItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.menu_list_delete, parent, false));

            namaMenu = (TextView) itemView.findViewById(R.id.textMakananItem);
            harga = (TextView) itemView.findViewById(R.id.textHargaItem);
        }

        public void Bind(Upload upload, String key){
            namaMenu.setText(upload.getNameImage());
            harga.setText(upload.getHarga());
            this.key = key;
        }
    }

    class MenuAdapter extends RecyclerView.Adapter<MenuItemView>{
        private List<Upload> mUploadList;
        private List<String> mkeys;

        public MenuAdapter(List<Upload> mUploadList, List<String> mkeys) {
            this.mUploadList = mUploadList;
            this.mkeys = mkeys;
        }

        @NonNull
        @Override
        public MenuItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MenuItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MenuItemView holder, int position) {
            holder.Bind(mUploadList.get(position), mkeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mUploadList.size();
        }
    }
}
