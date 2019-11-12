package id.ac.polinema.tcttcakron;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import id.ac.polinema.tcttcakron.models.Upload;

public class UpdateMenuAdmin extends AppCompatActivity {
    ImageView updateImage;
    EditText nama, harga;
    Button update;
    ProgressDialog progressDialog;
    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;
    final int PICK_IMAGE_REQUEST = 1;
    Uri FilePathUri;
    StorageTask mUploadTask;
    UploadTask uploadTask;

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

        updateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhotoFromGallery();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null && mUploadTask.isInProgress()){
                    Toast.makeText(UpdateMenuAdmin.this, "Upload In Progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });
    }

    private void choosePhotoFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (data != null) {
                FilePathUri = data.getData();
                Picasso.with(this).load(FilePathUri).into(updateImage);

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                    updateImage.setImageBitmap(bitmap);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getFileExtension (Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void uploadFile() {
        if (FilePathUri != null) {
            StorageReference oldImage = FirebaseStorage.getInstance().getReferenceFromUrl(imageSelected);
            oldImage.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(UpdateMenuAdmin.this, "Remove old image success", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UpdateMenuAdmin.this, "Remove old image failed!!", Toast.LENGTH_SHORT).show();
                }
            });

            progressDialog.setTitle("Image is Uploading...");
            progressDialog.show();
            final StorageReference storageReference2 = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(FilePathUri));
            uploadTask = storageReference2.putFile(FilePathUri);

            storageReference2.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                            final String TempImageName = nama.getText().toString().trim();
                            final int hargamenu = Integer.parseInt(harga.getText().toString());
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();

                            if (TempImageName != namaSelected){
                                mDatabaseRef.child(namaSelected).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(), "Remove old database success", Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Remove old database failed!!!", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                            Upload upload = new Upload(TempImageName,downloadUrl.toString(), hargamenu);
                            mDatabaseRef.child(TempImageName).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateMenuAdmin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress =  (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setTitle("Image is Uploading... (" + progress + "%)");
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            final String TempImageName = nama.getText().toString().trim();
            final int hargamenu = Integer.parseInt(harga.getText().toString());
            if (TempImageName != namaSelected){
                mDatabaseRef.child(namaSelected).setValue(null).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Remove old database success", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Remove old database failed!!!", Toast.LENGTH_LONG).show();
                    }
                });
            }
            Upload upload = new Upload(TempImageName, imageSelected, hargamenu);
            mDatabaseRef.child(TempImageName).setValue(upload);
        }
    }

    public void handlerOnClickBatalUpdate(View view) {
        Intent intent = new Intent(this, UpdateAdmin.class);
        startActivity(intent);
    }

    public void handlerOnClickSubmitUpdate(View view) {

    }
}
