package com.pk.socialmedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Objects;

public class signup extends AppCompatActivity {
    private static final String EMAIL = "email";
    Button gsign;
    FirebaseAuth mAuth;
    private static final String TAG = "GoogleActivity";
    private static final int GC_SIGN_IN = 9001;
    GoogleSignInClient mGoogleSignInClient;
    static String namet, photoUri, accountt;
    LoginButton fbsign;
    AccessTokenTracker mAccessTokenTracker;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        callbackManager = CallbackManager.Factory.create();
        gsign = findViewById(R.id.gsign);
        fbsign = findViewById(R.id.fbsign);
        mAuth = FirebaseAuth.getInstance();
        gsignin();
        gsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gsignin();
                gcccsignIn(GC_SIGN_IN);
            }
        });
        fbsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbsignin();
            }
        });


    }

    private void fbsignin() {
        fbsign.setPermissions(Arrays.asList("email"));
        fbsign.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Demo", "Sucessfull");
                handelFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("Demo", "Cancelled");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Demo", error.toString());
            }
        });


    }
    private void gsignin() {
// Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }


    private void gcccsignIn(int code) {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, code);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        callbackManager.onActivityResult(requestCode, resultCode, data);
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("Demooo", object.toString());
                try {
                    String fbName = object.getString("name");
                    String fbId = object.getString("id");
                    Intent intent = new Intent(signup.this, FbHome.class);
                    intent.putExtra("fbName", fbName);
                    intent.putExtra("fbId", fbId);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("fields", "email,name,id,first_name,last_name");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
//                Intent intent= new Intent(getApplicationContext(),GcHome.class);
                String keyIdentifer = null;
//                i.putExtra(strName, keyIdentifer );
                photoUri = Objects.requireNonNull(account.getPhotoUrl()).toString();
                namet = account.getDisplayName();
                accountt = account.getEmail();
//get ddata from here from google
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }

        mAccessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null) {
                    LoginManager.getInstance().logOut();
                }
            }
        };


    }

    /* @Override
     protected void onDestroy() {
         super.onDestroy();
         mAccessTokenTracker.stopTracking();
     }*/
    public void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), GcHome.class);
                            intent.putExtra("gcName", namet);
                            intent.putExtra("gcEmail", accountt);
                            intent.putExtra("gcPic", photoUri);
                            startActivity(intent);
                        } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            updateUI(null);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(signup.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void handelFacebookToken(AccessToken accessToken) {
        Log.d("Demo", "Handel the token" + accessToken);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("Demo", "Sign in with fb sucessfull");
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }

 /*   @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, GcHome.class));
            finish();

        }
    }*/
}

