package me.rooshi.podcastapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements PhotoChooserDialogFragment.PhotoChooserDialogListener {

    private static final int TAKE_PICTURE_CODE = 1;
    private static final int GALLERY_CODE = 2;

    private FirebaseAuth firebaseAuth;

    ImageView profilePicture;
    TextInputLayout nameTextInputLayout;
    TextInputLayout emailTextInputLayout;
    TextInputLayout passwordTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        profilePicture = findViewById(R.id.profile_image);
        nameTextInputLayout = findViewById(R.id.nameTextInputLayout);
        emailTextInputLayout = findViewById(R.id.emailTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);

        setEmailValueFromLoginActivity();
    }

    public void registerUser(View view) {
        String email = WaveformUtils.getStringFromTextInputLayout(emailTextInputLayout);
        String password = WaveformUtils.getStringFromTextInputLayout(passwordTextInputLayout);
        boolean valid = validateFields(email, password);
        if (valid) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //user created

                                //TODO: upload photo to firebase

                                //TODO: link name and profile pic with the new account
                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                user.updateProfile(new UserProfileChangeRequest.Builder()
                                        .setDisplayName(WaveformUtils.getStringFromTextInputLayout(nameTextInputLayout))
                                        .setPhotoUri()
                                        .build());
                                //exit activity

                            } else {
                                Toast.makeText(RegisterActivity.this, "Registration failed. Please try again", Toast.LENGTH_SHORT).show();
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
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, TAKE_PICTURE_CODE);
        }
    }

    public void chooseFromGalleryAndSave() {
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent, "Choose profile picture"), GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap imageBitmap;
        if (requestCode == TAKE_PICTURE_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Bundle extras = data.getExtras();
                assert extras != null;
                imageBitmap = (Bitmap) extras.get("data");
                //set thumbnail
                profilePicture.setImageBitmap(imageBitmap);
                //TODO save to firebase
            } else {
                Toast.makeText(this, "Unable to take picture", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == GALLERY_CODE) {
            if (resultCode == RESULT_OK) {
                assert data != null;
                Uri selectedImageUri = data.getData();
                Picasso.get().load(selectedImageUri).into(profilePicture);
                //TODO store into firebase
            } else {
                Toast.makeText(this, "Unable to obtain picture from gallery", Toast.LENGTH_SHORT).show();
            }
        }
    }
}