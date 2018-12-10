package com.muslimuz.muslimuzapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.muslimuz.muslimuzapp.R;
import com.muslimuz.muslimuzapp.activities.NewsActivity;
import com.muslimuz.muslimuzapp.model.News;


import java.util.List;

import static com.muslimuz.muslimuzapp.fragment.HomeFragment.WEB_URL;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private RequestOptions opsi;
    private Context mContext;
    private List<News> mData;

    public RecyclerViewAdapter(Context mContext, List<News> mData) {
        this.mContext = mContext;
        this.mData = mData;

        opsi = new RequestOptions().centerCrop().placeholder(R.drawable.bnw).error(R.drawable.bnw);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.news_row_item, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, NewsActivity.class);
                i.putExtra("news_title", mData.get(viewHolder.getAdapterPosition()).getTitle());
                i.putExtra("news_img", mData.get(viewHolder.getAdapterPosition()).getImg());
                i.putExtra("news_sumber", mData.get(viewHolder.getAdapterPosition()).getSumber());
                i.putExtra("news_tanggal", mData.get(viewHolder.getAdapterPosition()).getTanggal());
                i.putExtra("news_teks", mData.get(viewHolder.getAdapterPosition()).getTeks());
                i.putExtra("news_link", mData.get(viewHolder.getAdapterPosition()).getLink());
                mContext.startActivity(i);

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
                mContext.startActivity(Intent.createChooser(share, "Bagikan Berita"));
            }
        });

        viewHolder.tv_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.tv_bookmark.setImageDrawable(mContext.getDrawable(R.drawable.ic_bookmark_ok));
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        holder.tv_title.setText(mData.get(position).getTitle());
        holder.tv_sumber.setText(mData.get(position).getSumber());
        holder.tv_tanggal.setText(mData.get(position).getTanggal());
        Glide.with(mContext).load(mData.get(position).getImg()).apply(opsi).into(holder.tv_img);


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

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

