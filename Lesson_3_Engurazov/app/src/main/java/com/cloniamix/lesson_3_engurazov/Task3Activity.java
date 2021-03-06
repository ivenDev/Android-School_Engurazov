package com.cloniamix.lesson_3_engurazov;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class Task3Activity extends AppCompatActivity {

    private TextView textViewAccountId;
    private TextView textViewName;
    private TextView textViewSurname;
    private TextView textViewEmail;
    private TextView textViewLogin;
    private TextView textViewRegion;

    public static Intent createStartIntent(Context context){
        return new Intent(context,Task3Activity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task3);

        init();
        updateUi(getUser());
    }

    private User getUser(){
        return new User("Ivan", "Ivanov" , "examle@mail.ru", "My Login",
                "Saransk", "Инженер");
    }


    private void init(){
        textViewAccountId = findViewById(R.id.textViewAccountId);
        textViewName = findViewById(R.id.textViewName);
        textViewSurname = findViewById(R.id.textViewSurname);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewLogin = findViewById(R.id.textViewLogin);
        textViewRegion = findViewById(R.id.textViewRegion);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        toolbar.getMenu().findItem(R.id.action_edit).setOnMenuItemClickListener(item -> {

            showToast(getResources().getString(R.string.menu_edit_item_toast_text));
            return true;
        });
        toolbar.setNavigationOnClickListener(v -> finish());

        TextView textViewExitButton = findViewById(R.id.textViewExitButton);
        textViewExitButton.setOnClickListener(v ->
                showToast(getResources().getString(R.string.exit_toast_text)));
    }

    private void updateUi(User user){
        textViewAccountId.setText(getString(R.string.account_id_and_position_text
                ,user.getId(),user.getPosition()));
        textViewName.setText(user.getName());
        textViewSurname.setText(user.getSurname());
        textViewEmail.setText(user.getEmail());
        textViewLogin.setText(user.getLogin());
        textViewRegion.setText(user.getRegion());
    }

    private void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
