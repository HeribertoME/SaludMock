package com.hmelizarraraz.saludmock;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Credenciales de pruebas
     * TODO: Remover cuando se implemente una autenticación real
     */
    private static final String DUMMY_USER_ID = "0000000000";
    private static final String DUMMY_PASSWORD = "secret";

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private ImageView mLogoView;
    private EditText mUserIdView;
    private EditText mPasswordView;
    private TextInputLayout mFloatLabelUserId;
    private TextInputLayout mFloatLabelPassword;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mLogoView = (ImageView) findViewById(R.id.image_logo);
        mUserIdView = (EditText) findViewById(R.id.user_id);
        mPasswordView = (EditText) findViewById(R.id.password);
        mFloatLabelUserId = (TextInputLayout) findViewById(R.id.float_label_user_id);
        mFloatLabelPassword = (TextInputLayout) findViewById(R.id.float_label_password);
        Button mSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        // Setup
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == R.id.login || actionId == EditorInfo.IME_NULL) {
                    if (!isOnline()) {
                        showLoginError(getString(R.string.error_network));
                        return false;
                    }
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOnline()) {
                    showLoginError(getString(R.string.error_network));
                    return;
                }
                attemptLogin();
            }
        });

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid userId, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mFloatLabelUserId.setError(null);
        mFloatLabelPassword.setError(null);

        // Store values at the time of the login attempt.
        String userId = mUserIdView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mFloatLabelPassword.setError(getString(R.string.error_field_required));
            focusView = mFloatLabelPassword;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mFloatLabelPassword.setError(getString(R.string.error_invalid_password));
            focusView = mFloatLabelPassword;
            cancel = true;
        }

        // Verificar si el ID tiene contenido.
        if (TextUtils.isEmpty(userId)) {
            mFloatLabelUserId.setError(getString(R.string.error_field_required));
            focusView = mFloatLabelUserId;
            cancel = true;
        } else if (!isUserIdValid(userId)) {
            mFloatLabelUserId.setError(getString(R.string.error_invalid_user_id));
            focusView = mFloatLabelUserId;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(userId, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isUserIdValid(String userId) {
        return userId.length() == 10;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

        int visibility = show ? View.GONE : View.VISIBLE;
        mLogoView.setVisibility(visibility);
        mLoginFormView.setVisibility(visibility);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Integer> {

        private final String mUserId;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mUserId = email;
            mPassword = password;
        }

        @Override
        protected Integer doInBackground(Void... params) {

            /*
            1:Petición exitosa
            2:Número de identificación no registrado
            3:Password incorrecto
            4:Error del servidor
            */

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return 4;
            }

            if (!mUserId.equals(DUMMY_USER_ID)) {
                return 2;
            }

            if (!mPassword.equals(DUMMY_PASSWORD)) {
                return 3;
            }

            return 1;
        }

        @Override
        protected void onPostExecute(final Integer success) {
            mAuthTask = null;
            showProgress(false);

            switch (success) {
                case 1:
                    showAppointmentsScreen();
                    break;
                case 2:
                case 3:
                    showLoginError("Número de identificación o contraseña inválidos");
                    break;
                case 4:
                    showLoginError(getString(R.string.error_server));
                    break;
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    private void showAppointmentsScreen() {
        startActivity(new Intent(this, AppointmentsActivity.class));
        finish();
    }

    private void showLoginError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

}

