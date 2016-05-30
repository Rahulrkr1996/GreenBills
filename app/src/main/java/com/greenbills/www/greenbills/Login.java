package com.greenbills.www.greenbills;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.greenbills.www.greenbills.Models.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Login extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private final int PROFILE_PIC_SIZE = 50;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private SignInButton google_SignINButton;
    private final int SAVE_PROFILE_PICTURE = 0;
    private Bitmap profilePicture = null;
    private User currentUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        google_SignINButton = (SignInButton) findViewById(R.id.google_SignINButton);
        google_SignINButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // [END build_client]

        /* [START customize_button]
           Customize sign-in button. The sign-in button can be displayed in
           multiple sizes and color schemes. It can also be contextually
           rendered based on the requested scopes. For example. a red button may
           be displayed when Google+ scopes are requested, but a white button
           may be displayed when only basic profile is requested. Try adding the
           Scopes.PLUS_LOGIN scope to the GoogleSignInOptions to see the
            difference.*/
        google_SignINButton.setSize(SignInButton.SIZE_STANDARD);
        google_SignINButton.setScopes(gso.getScopeArray());
        // [END customize_button]


    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            if (acct.getPhotoUrl() != null) {
                // Downloading image and saving it to ext storage
                if(currentUser==null) {
                    currentUser = new User(1, acct.getDisplayName(), acct.getEmail(), acct.getPhotoUrl().toString());
                    new LoadImage().execute(acct.getPhotoUrl().toString());
                    currentUser.save();
                }else{
                    currentUser.user_id = 1;
                    currentUser.user_name = acct.getDisplayName();
                    currentUser.user_email = acct.getEmail();
                    currentUser.user_photoURL = acct.getPhotoUrl().toString();
                    new LoadImage().execute(acct.getPhotoUrl().toString());
                    currentUser.save();
                }
            } else {
                if (currentUser == null) {
                    currentUser = new User(1, acct.getDisplayName(), acct.getEmail(),null);
                    currentUser.save();
                } else {
                    currentUser.user_id = 1;
                    currentUser.user_name = acct.getDisplayName();
                    currentUser.user_email = acct.getEmail();
                    currentUser.user_photoURL = null;
                    currentUser.save();
                }
            }

            Toast.makeText(Login.this, "User details saved for " + currentUser.user_name, Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent i = new Intent(Login.this, HomePage.class);
                    startActivity(i);
                }
            }, 1500);
        }
    }

    // [END handleSignInResult]

    // [START signIn]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signIn]

    // [START signOut]
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        /** To--Do */
                    }
                });
    }
    // [END signOut]

    // [START revokeAccess]
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]

                        /** To--Do */
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Toast.makeText(Login.this, "Login Failed!! Check internet connectivity...", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Saving Profile Picture ....");
            pDialog.show();

        }

        protected Bitmap doInBackground(String... args) {
            try {
                profilePicture = BitmapFactory.decodeStream((InputStream) new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return profilePicture;
        }

        protected void onPostExecute(Bitmap image) {

            if (image != null) {
                saveInGallery(image, SAVE_PROFILE_PICTURE);
                pDialog.dismiss();
            } else {
                pDialog.dismiss();
                Toast.makeText(Login.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void saveInGallery(Bitmap img, int location) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir;
        if (location == SAVE_PROFILE_PICTURE) {
            myDir = new File(root + "/Green Bills/Profile");
        } else {
            myDir = new File(root + "/Green Bills/CompanyLogo");
        }
        myDir.mkdirs();
        User user = new User();
        List<User> list = new ArrayList<User>();
        list = user.getAllUsers();

        String fname = "profile_picture"+ list.size() +".jpg";
        File file = new File(myDir, fname);

        //Setting the photo path in ext. hard disk to the current user details
        currentUser = new User();
        currentUser.user_photoPath = myDir + fname;
        /////////////////////////////////////

        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            img.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
