package com.example.belajar_android_pengenalan_material_design.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.belajar_android_pengenalan_material_design.R;
import com.example.belajar_android_pengenalan_material_design.model.UsersData;
import com.example.belajar_android_pengenalan_material_design.model.ImagesList;
import com.example.belajar_android_pengenalan_material_design.adapter.ImagesRecyclerAdapter;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class StartActivity extends AppCompatActivity {
    private TextView userName;
    private CircleImageView circleImageView;
    private FirebaseUser firebaseUser;
    private ImagesRecyclerAdapter imagesRecyclerAdapter;
    private DatabaseReference databaseReference;
    private List<ImagesList> imagesList;
    private static final int IMAGE_REQUEST = 1;
    private StorageTask<UploadTask.TaskSnapshot> storageTask;
    private Uri imageUri;
    private StorageReference storageReference;
    private UsersData usersData;
    private FirebaseFirestore fStore;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Profile");
        /*Initialize imageList in the onCreate StartActivity */

        /*init FirebaseFireStore*/
        fStore = FirebaseFirestore.getInstance();

        imagesList = new ArrayList<>();
        userName = findViewById(R.id.username);
        circleImageView = findViewById(R.id.profileImage);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        storageReference = FirebaseStorage.getInstance().getReference("profile_images");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersData = dataSnapshot.getValue(UsersData.class);
                assert usersData != null;
                userName.setText(usersData.getUsername());
                /*Conditional If*/
                if (usersData.getImageURL().equals("default")){
                    circleImageView.setImageResource(R.drawable.ic_launcher_background);
                } else{
                    Glide.with(getApplicationContext()).load(usersData.getImageURL()).into(circleImageView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(StartActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new  AlertDialog.Builder(StartActivity.this);
                builder.setCancelable(true);
                View mView = LayoutInflater.from(StartActivity.this).inflate(R.layout.select_image_layout, null);
                RecyclerView recyclerView = mView.findViewById(R.id.recyclerView);
                collectOldImages();
                recyclerView.setLayoutManager(new GridLayoutManager(StartActivity.this, 3));
                recyclerView.setHasFixedSize(true);

                /*Define Constructor ImageRecyclerAdapter*/
                imagesRecyclerAdapter = new ImagesRecyclerAdapter(imagesList, StartActivity.this);
                recyclerView.setAdapter(imagesRecyclerAdapter);
                imagesRecyclerAdapter.notifyDataSetChanged();

                /*Define Button to Open Image*/
                Button openImage = mView.findViewById(R.id.openImages);
                openImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openImage();
                    }
                });

                builder.setView(mView);
                AlertDialog alertDialog =  builder.create();
                alertDialog.show();
            }
        });
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            if (storageTask != null && storageTask.isInProgress()){
                Toast.makeText(this, "Uploading is in progress", Toast.LENGTH_SHORT).show();
            } else{
                uploadImage();
            }
        }
    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading Image");
        progressDialog.show();

        if (imageUri != null){
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e){
                e.printStackTrace();
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            assert bitmap != null;
            bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
            byte[] imageFileToByte = byteArrayOutputStream.toByteArray();
            /*Define StorageReference make be final*/
            final StorageReference imageReference = storageReference.child(usersData.getUsername()+System.currentTimeMillis()+".jpg");
            storageTask = imageReference.putBytes(imageFileToByte);
            storageTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    /*Conditional IF. jika nilainya true maka dijalankan, jika false aka lewatkan*/
                    if (!task.isSuccessful()){
                        throw Objects.requireNonNull(task.getException());
                    }
                    return imageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downLoadUri = task.getResult();
                        String sDownloadUri = downLoadUri.toString();
                        Map<String, Object> hashMap = new HashMap<>();
                        hashMap.put("imageUrl", sDownloadUri);
                        /*create DocumentReference to instance FirebaseFirestore*/
                        DocumentReference df = fStore.collection("Users").document(firebaseUser.getUid());
                        df.update(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(StartActivity.this,
                                        "Update profile image Cloud FireStore successfully",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(StartActivity.this,
                                        "Update profile image Cloud FireStore Failed",
                                        Toast.LENGTH_SHORT).show();
                                Log.e(StartActivity.class.getSimpleName(),
                                        "Failed to update profile image Cloud FireStore at line 205");
                            }
                        });
                        databaseReference.updateChildren(hashMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(StartActivity.this,
                                                    "Update profile image successfully",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(StartActivity.this,
                                        "Update profile image failed",
                                        Toast.LENGTH_SHORT).show();
                                Log.e(StartActivity.class.getSimpleName(),
                                        "Failed to update profile image at line 197");
                            }
                        });
                        final DatabaseReference profileImageReference = FirebaseDatabase.getInstance().getReference("profile_images").child(firebaseUser.getUid());
                        profileImageReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                } else{
                                    progressDialog.dismiss();
                                    Toast.makeText(StartActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }else{
                        Toast.makeText(StartActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(StartActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void collectOldImages() {
        DatabaseReference imageListReference = FirebaseDatabase.getInstance().getReference("profile_images").child(firebaseUser.getUid());
        imageListReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                imagesList.clear();
                /*Looping Foreach*/
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    imagesList.add(snapshot.getValue(ImagesList.class));
                }
                imagesRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(StartActivity.this, databaseError.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }
}