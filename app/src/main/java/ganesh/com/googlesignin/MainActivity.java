package ganesh.com.googlesignin;

import android.content.Intent;
import android.service.carrier.CarrierMessagingService;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.signin.SignInOptions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.xml.datatype.Duration;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener
{
    Button sign_in,ss;
    GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 007;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sign_in=(Button)findViewById(R.id.sign_in);
        ss=(Button)findViewById(R.id.ss);
        sign_in.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                onButtonClicks();
            }
        });
        GoogleSignInOptions so=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient=new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,so).build();
        ss.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String startDate = "2018-10-10T10:00:06";

                //code for reading the date from system
                DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T' HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                String edate=dateFormat1.format(cal.getTime());//here ends

                DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss");

                try {
                    Date d=dateFormat.parse(startDate);
                    Date d2=dateFormat.parse(edate);

                    long diff=(d2.getTime()-d.getTime())/(24 * 60 * 60 * 1000);
                    Toast.makeText(MainActivity.this, "dates"+diff, Toast.LENGTH_SHORT).show();
                } catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onButtonClicks()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture)
    {

    }

    private void handleSignInResult(GoogleSignInResult result)
    {
        if (result.isSuccess())
        {
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e("simple", "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();

            Log.e("simple", "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);
            Toast.makeText(this, "person name"+personName+" mail id"+email, Toast.LENGTH_SHORT).show();

//            txtName.setText(personName);
//            txtEmail.setText(email);
//            Glide.with(getApplicationContext()).load(personPhotoUrl)

//                    .thumbnail(0.5f)
//                    .crossFade()
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(imgProfilePic);
        }
        else
        {
            //Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d("simple", "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            //showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>()
            {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult)
                {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

}
