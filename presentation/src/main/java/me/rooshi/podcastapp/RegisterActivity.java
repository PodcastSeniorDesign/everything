package me.rooshi.podcastapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

import me.rooshi.podcastapp.model.UserModel;

public class RegisterActivity extends AppCompatActivity implements PhotoChooserDialogFragment.PhotoChooserDialogListener {

    private static final int TAKE_PICTURE_CODE = 1;
    private static final int GALLERY_CODE = 2;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;

    ImageView profilePicture;
    final Bitmap[] imageBitmap = {null};

    TextInputLayout nameTextInputLayout;
    TextInputLayout emailTextInputLayout;
    TextInputLayout passwordTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();

        profilePicture = findViewById(R.id.profile_image);
        nameTextInputLayout = findViewById(R.id.nameTextInputLayout);
        emailTextInputLayout = findViewById(R.id.emailTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);

        setEmailValueFromLoginActivity();
    }

    public void registerUser(View view) {
        final String name = WaveformUtils.getStringFromTextInputLayout(nameTextInputLayout);
        final String email = WaveformUtils.getStringFromTextInputLayout(emailTextInputLayout);
        final String password = WaveformUtils.getStringFromTextInputLayout(passwordTextInputLayout);
        boolean valid = validateFields(email, password);
        if (valid) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //user created

                                FirebaseUser user = firebaseAuth.getCurrentUser();

                                //adding field in userInfo table for new user
                                assert user != null;
                                String uid = firebaseAuth.getCurrentUser().getUid();
                                UserModel newUser = new UserModel(uid, name, email);
                                //TODO: move hardcoded "users" and other values to a constants file
                                databaseReference.child("users").child(uid).setValue(newUser);

                                StorageReference photoRef = null;
                                if (imageBitmap[0] != null) {
                                    StorageReference storageReference = firebaseStorage.getReference();
                                    //TODO: create global properties file storing "users" db entry and "profilePicture" storage name
                                    //store in images/profilePicture or users/{uid}/profilePicture?
                                    photoRef = storageReference.child("users/" + uid + "/profilePicture.bmp");

                                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                    imageBitmap[0].compress(Bitmap.CompressFormat.PNG, 90, stream);
                                    imageBitmap[0].recycle();
                                    //TODO make async so it actually works
                                    photoRef.putBytes(stream.toByteArray());
                                    // bytes -> bmp: Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length)
                                }

                                //TODO: link name and profile pic with the new account
                                UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
                                        builder.setDisplayName(WaveformUtils.getStringFromTextInputLayout(nameTextInputLayout));
                                        if (photoRef != null) {
                                            //im not sure if this will wait for the result
                                            builder.setPhotoUri(photoRef.getDownloadUrl().getResult());
                                        }
                                user.updateProfile(builder.build());
                                //exit activity
                                finish();

                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration failed. " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private boolean validateFields(String email, String password) {
        return WaveformUtils.isValidEmailAddress(email) && WaveformUtils.isValidPassword(password);
    }

    private void setEmailValueFromLoginActivity() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String email = extras.getString("email");
            Objects.requireNonNull(emailTextInputLayout.getEditText()).setText(email);
        }
    }

    public void onProfilePictureClicked(View view) {
        DialogFragment photoChooseDialogFragment = new PhotoChooserDialogFragment();
        photoChooseDialogFragment.show(getSupportFragmentManager(), "PhotoChooserDialogFragment");
    }

    @Override
    public void onOptionClick(DialogFragment dialog, int i) {
        //TODO add option for removing/deleting profile picture
        if (i == 0) {
            //Take photo
            takePictureAndSave();
        } else if (i == 1) {
            //Choose from gallery
            chooseFromGalleryAndSave();
        } else {
            Toast.makeText(this, "Option with index " + i + "not understood", Toast.LENGTH_SHORT).show();
        }
    }

    public void takePictureAndSave() {
        //TODO fix incorrect intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, TAKE_PICTURE_CODE);
        }
    }

    public void chooseFromGalleryAndSave() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(galleryIntent, "Choose profile picture"), GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Bundle extras = data.getExtras();
                assert extras != null;
                imageBitmap[0] = (Bitmap) extras.get("data");
                //set thumbnail
                profilePicture.setImageBitmap(imageBitmap[0]);
                //TODO save to firebase
            } else {
                Toast.makeText(this, "Unable to take picture", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == GALLERY_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Uri selectedImageUri = data.getData();
                Picasso.get().load(selectedImageUri).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        imageBitmap[0] = bitmap;
                        profilePicture.setImageBitmap(bitmap);
                    }
                    @Override public void onBitmapFailed(Exception e, Drawable errorDrawable) { Log.w("Gallery Load", "FAILED" + e.getMessage()); }
                    @Override public void onPrepareLoad(Drawable placeHolderDrawable) {}
                });
            } else {
                Toast.makeText(this, "Unable to obtain picture from gallery", Toast.LENGTH_SHORT).show();
            }
        }
    }
}