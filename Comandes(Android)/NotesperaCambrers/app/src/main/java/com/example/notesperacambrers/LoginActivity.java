package com.example.notesperacambrers;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import model.*;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText password;
    private EditText username;

    private Button btnLogin;

    private TextView txvLoginError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkUserLogged();

        username = findViewById(R.id.edtUser);
        password = findViewById(R.id.edtPass);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        txvLoginError = findViewById(R.id.txvLoginError);
        txvLoginError.setVisibility(View.INVISIBLE);
    }

    private void checkUserLogged() {
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        String user = sp.getString("user","");
        String pass = sp.getString("pass","");
        if (user != "") {
            try{
                // Crida assíncrona per loguejar
                Observable.fromCallable(() -> {
                    //---------------- START OF THREAD ------------------------------------
                    // Això és el codi que s'executarà en un fil
                    Login l = BDUtils.login(user,pass);
                    return l;
                    //--------------- END OF THREAD-------------------------------------
                })
                        .subscribeOn(Schedulers.io())
                        .onErrorReturnItem(new Login())
                        .doOnError(throwable -> errorHandler(true))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe((login) -> {
                            //-------------  UI THREAD ---------------------------------------
                            // El codi que tenim aquí s'executa només quan el fil
                            // ha acabat !! A més, aquest codi s'executa en el fil
                            // d'interfície gràfica.
                            Log.d("login",login.toString());
                            if (login != null && login.session_id > 0){
                                Login l = (Login)login;
                                Log.d("Login", String.valueOf(l.session_id));
                                saveLoginSpecsToSP(l.session_id);
                                moveToMain();
                            }
                            else{
                                errorHandler(true);
                                Log.d("error", "null");
                            }
                            //-------------  END OF UI THREAD ---------------------------------------
                        });
            }
            catch(Exception ex){
                Log.e("error","ex.toString()");
            }
        }
    }

    private void saveLoginSpecsToSP(int session_id) {
        SharedPreferences sp = getSharedPreferences("TAULES",MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putInt("session", session_id);
        ed.commit();
    }


    @Override
    public void onClick(View v) {
        String user = username.getText().toString();
        String pass = password.getText().toString();

        try{
            // Crida assíncrona per descarregar el JSON
            Observable.fromCallable(() -> {
                //---------------- START OF THREAD ------------------------------------
                // Això és el codi que s'executarà en un fil
                Login l = BDUtils.login(user,pass);
                return l;
                //--------------- END OF THREAD-------------------------------------
            })
            .subscribeOn(Schedulers.io())
            .onErrorReturnItem(new Login())
            .doOnError(throwable -> errorHandler(true))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe((login) -> {
                //-------------  UI THREAD ---------------------------------------
                // El codi que tenim aquí s'executa només quan el fil
                // ha acabat !! A més, aquest codi s'executa en el fil
                // d'interfície gràfica.
                Log.d("login",login.toString());
                if (login != null && login.session_id > 0){
                    Login l = (Login)login;
                    Log.d("Login", String.valueOf(l.session_id));
                    saveLoginSpecsToSP(user, pass);
                    saveLoginSpecsToSP(l.session_id);
                    moveToMain();
                }
                else{
                    errorHandler(true);
                    Log.d("error", "null");
                }
                //-------------  END OF UI THREAD ---------------------------------------
            });
        }
        catch(Exception ex){
            Log.e("error","ex.toString()");
        }



    }

    private void saveLoginSpecsToSP(String user, String pass) {
        SharedPreferences sp = getSharedPreferences("TAULES",MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("user", user);
        ed.putString("pass", pass);
        ed.commit();
    }

    private void moveToMain() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    private void errorHandler(boolean error){
        if (error){
            txvLoginError.setVisibility(View.VISIBLE);
        }
        else{
            txvLoginError.setVisibility(View.INVISIBLE);
        }
    }
}