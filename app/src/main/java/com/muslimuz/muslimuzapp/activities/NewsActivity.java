package com.muslimuz.muslimuzapp.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.muslimuz.muslimuzapp.R;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;


public class NewsActivity extends AppCompatActivity {

    public String title;
    public String img_url;
    public String sumber;
    public String tanggal;
    public String teks;
    public String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        title = getIntent().getExtras().getString("news_title");
        img_url = getIntent().getExtras().getString("news_img");
        sumber = getIntent().getExtras().getString("news_sumber");
        tanggal = getIntent().getExtras().getString("news_tanggal");
        teks = getIntent().getExtras().getString("news_teks");
        link = getIntent().getExtras().getString("news_link");


        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);

        TextView tv_title = findViewById(R.id.aa_judul);

        TextView tv_desc = findViewById(R.id.aa_description);
        ImageView img = findViewById(R.id.aa_thumbnail);

        collapsingToolbarLayout.setTitle(title);

        tv_title.setText(title);

        tv_desc.setText(teks + "...");
        Glide.with(this).load(img_url).into(img);
    }

    public void clickReadMore(View v) {
        String url = link;
        Intent intentReadMore = new Intent(NewsActivity.this, BrowserActivity.class);
        intentReadMore.putExtra("url", url);
        startActivity(intentReadMore);


    }
}
