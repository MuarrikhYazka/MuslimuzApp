package com.muslimuz.muslimuzapp.adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.muslimuz.muslimuzapp.R;
import com.muslimuz.muslimuzapp.activities.BrowserActivity;
import com.muslimuz.muslimuzapp.activities.NewsActivity;
import com.muslimuz.muslimuzapp.model.News;


import java.util.List;

import static com.muslimuz.muslimuzapp.fragment.HomeFragment.WEB_URL;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private RequestOptions opsi;
    private Activity activity;
    private List<News> mData;


    public RecyclerViewAdapter(List<News> mData, Activity activity) {
        this.activity = activity;
        this.mData = mData;

        opsi = new RequestOptions().centerCrop().placeholder(R.drawable.bnw).error(R.drawable.bnw);


    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(activity).inflate(R.layout.news_row_item, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);

        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = mData.get(viewHolder.getAdapterPosition()).getLink();
                Intent intentReadMore = new Intent(activity, BrowserActivity.class);
                intentReadMore.putExtra("url", url);
                activity.startActivity(intentReadMore);



            }
        });

        viewHolder.tv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                String body = mData.get(viewHolder.getAdapterPosition()).getTitle() + "\n\n" + WEB_URL + "/prjk/index.php/news/" + mData.get(viewHolder.getAdapterPosition()).getSlug();
                String subjek = "Berita terbaru tentang Islam hanya di Muslimuz";
                share.putExtra(Intent.EXTRA_SUBJECT, subjek);
                share.putExtra(Intent.EXTRA_TEXT, body);
                activity.startActivity(Intent.createChooser(share, "Bagikan Berita"));
            }
        });

        viewHolder.tv_bookmark.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                viewHolder.tv_bookmark.setImageDrawable(activity.getDrawable(R.drawable.ic_bookmark_ok));
            }
        });

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.tv_title.setText(mData.get(position).getTitle());
        myViewHolder.tv_sumber.setText(mData.get(position).getSumber());
        myViewHolder.tv_tanggal.setText(mData.get(position).getTanggal());
        Glide.with(activity).load(mData.get(position).getImg()).apply(opsi).into(myViewHolder.tv_img);
    }


    @Override
    public int getItemCount() {

        return mData.size();
    }


private class MyViewHolder extends RecyclerView.ViewHolder {

    TextView tv_title;
    ImageView tv_img;
    TextView tv_sumber;
    TextView tv_tanggal;
    ImageView tv_share;
    ImageView tv_bookmark;
    LinearLayout view_container;


    public MyViewHolder(View itemView) {
        super(itemView);
        view_container = itemView.findViewById(R.id.container);
        tv_bookmark = itemView.findViewById(R.id.bookmark);
        tv_share = itemView.findViewById(R.id.share);
        tv_title = itemView.findViewById(R.id.news_title);
        tv_img = itemView.findViewById(R.id.thumbnail);
        tv_sumber = itemView.findViewById(R.id.sumber);
        tv_tanggal = itemView.findViewById(R.id.tanggal);


    }
}



}

