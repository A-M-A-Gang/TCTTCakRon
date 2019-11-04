package id.ac.polinema.tcttcakron;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UpdateMenuAdmin extends AppCompatActivity {
    ImageView updateImage;
    EditText nama, harga;
    Button update;
    ProgressDialog progressDialog;
    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;

    private String namaSelected, hargaSelected, imageSelected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu_admin);
        namaSelected = getIntent().getStringExtra("nama");
        hargaSelected = getIntent().getStringExtra("harga");
        imageSelected = getIntent().getStringExtra("image");

        nama = findViewById(R.id.nama_update);
        nama.setText(namaSelected);
        harga = findViewById(R.id.harga_update);
        harga.setText(hargaSelected);
        updateImage = findViewById(R.id.image_update);
        Glide.with(this).load(imageSelected).apply(new RequestOptions().centerCrop().override(500, 500)).into(updateImage);

        update = findViewById(R.id.update_button);
        progressDialog = new ProgressDialog(UpdateMenuAdmin.this);
        mStorageRef = FirebaseStorage.getInstance().getReference("Menu");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Menu");
    }

    public void handlerOnClickBatalUpdate(View view) {
        Intent intent = new Intent(this, UpdateAdmin.class);
        startActivity(intent);

    }
}
