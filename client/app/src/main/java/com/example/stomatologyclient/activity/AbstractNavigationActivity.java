package com.example.stomatologyclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Абстрактная активити, которая настраивает магию, необходимую для
 * корректной работы кнопки "НАЗАД"
 */
public class AbstractNavigationActivity extends AppCompatActivity
{

    protected int id;       //Какой-то айдишник, используемый в запросах

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(intent != null)
        {
            Bundle bundle = intent.getExtras();
            if(bundle!=null)
            {
                id = bundle.getInt("id",-1);
            }
        }
    }

    protected void setup_actionbar()
    {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Запускает активити и передает 1 аргумент - id
     * @param activity
     * @param id
     */
    protected void start_activity_and_send_id(Class<? extends AppCompatActivity> activity, int id)
    {
        Intent intent = new Intent(this, activity);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    //Специальная магия, чтобы при нажатии на кнопку вверх
    //в акшн баре нас не кидало на пустую активити
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                NavUtils.navigateUpTo(this, intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
