package id.ac.polinema.tcttcakron;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MenuList extends ArrayAdapter<Upload> {
    private Activity context;
    private List<Upload> menuList;

    public MenuList(Activity context, List<Upload> menuList){
        super(context, R.layout.activity_delete_admin, menuList);
        this.context = context;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.menu_list_delete, null, true);

        TextView namaMakanan = (TextView) listViewItem.findViewById(R.id.textMakananItem);
        TextView harga = (TextView) listViewItem.findViewById(R.id.textHargaItem);
        ImageView gambar = listViewItem.findViewById(R.id.imageViewItem);

        Upload menu = menuList.get(position);

        namaMakanan.setText(menu.getNameImage());
        harga.setText(String.valueOf(menu.getHarga()));

        return listViewItem;
    }
}
