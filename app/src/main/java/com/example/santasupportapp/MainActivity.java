package com.example.santasupportapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.model.VisitorInfo;
import com.zopim.android.sdk.prechat.PreChatForm;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import zendesk.answerbot.AnswerBot;
import zendesk.answerbot.AnswerBotActivity;
import zendesk.core.AnonymousIdentity;
import zendesk.core.Identity;
import zendesk.core.Zendesk;
import zendesk.support.Support;
import zendesk.support.guide.HelpCenterActivity;
import zendesk.support.request.RequestActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Zendesk Start */
        Zendesk.INSTANCE.init(this, "https://michaelodonnell.zendesk.com", "45c872ec411ce4d454a0fe69fdc12065acaad9ec839a27f0", "mobile_sdk_client_9ab55ce6c4a8c2379707");
        Identity identity = new AnonymousIdentity();
        Zendesk.INSTANCE.setIdentity(identity);
        Support.INSTANCE.init(Zendesk.INSTANCE);

        // Enable the Answer Bot SDK
        AnswerBot.INSTANCE.init(Zendesk.INSTANCE, Support.INSTANCE);

        final Intent helpCenterIntent = HelpCenterActivity.builder()
                .intent(this);
        final Intent requestIntent = AnswerBotActivity.builder()
                .intent(this);

        // build pre chat form config
        PreChatForm defaultPreChat = new PreChatForm.Builder()
                .name(PreChatForm.Field.REQUIRED)
                .build();

        // initialize the chat with global configuration
        ZopimChat.init("rukyvvcOsFeMp60pvMs2yLGdOsAbPxYg").preChatForm(defaultPreChat);
        VisitorInfo visitorInfo = new VisitorInfo.Builder().build();
        ZopimChat.setVisitorInfo(visitorInfo);
        /* Zendesk End */

        FloatingActionButton fab1 = findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(helpCenterIntent);
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ZopimChatActivity.class));
            }
        });

        FloatingActionButton fab3 = findViewById(R.id.fab3);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(requestIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
