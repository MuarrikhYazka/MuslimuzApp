package com.muslimuz.muslimuzapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.muslimuz.muslimuzapp.R;
import com.muslimuz.muslimuzapp.activities.BerandaActivity;
import com.muslimuz.muslimuzapp.activities.BrowserActivity;
import com.muslimuz.muslimuzapp.activities.NewsActivity;
import com.muslimuz.muslimuzapp.adapters.RecyclerViewAdapter;
import com.muslimuz.muslimuzapp.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HomeFragment extends Fragment {


    View myView;
    // public static final String WEB_URL = "http://192.168.43.61:80";
    //public static final String WEB_URL = "http://10.100.203.85:80";
    public static final String WEB_URL = "http://muslimuz.com";
    private List<News> lstNews;
    public LinearLayout textLayout;
    private RecyclerView recyclerView;
    public ImageView headlineImg;
    private TextView headlineTitle, headlineSumber, headlineTanggal;
    private int tahun, bulan, hari;

    private String titleBaru;
    private String imgBaru;
    private String tanggalBaru;
    private String sumberBaru;
    private String teksBaru;
    private String linkBaru;
    private String ketDateBaru;
    private String slugBaru;
    private JSONArray hasil;

    LinearLayoutManager manager;
    RecyclerViewAdapter myadapter;

    Boolean isScrolling = false;

    int current, total, scroll;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        myView = inflater.inflate(R.layout.fragment_home, container, false);

        headlineImg = myView.findViewById(R.id.headlineImg);
        headlineTitle = myView.findViewById(R.id.headlineTitle);
        headlineSumber = myView.findViewById(R.id.headlineSumber);
        headlineTanggal = myView.findViewById(R.id.headlineTanggal);
        textLayout = myView.findViewById(R.id.textLayout);

        if (getActivity() != null) {
            headlineImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = getLinkBaru();
                    Intent intentReadMore = new Intent(getActivity(), BrowserActivity.class);
                    intentReadMore.putExtra("url", url);
                    startActivity(intentReadMore);
                }
            });


            lstNews = new ArrayList<>();

            recyclerView = myView.findViewById(R.id.recyclerViewid);
            jsonRequest();


        }


        return myView;
    }


    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void jsonRequest() {

        JsonArrayRequest request = new JsonArrayRequest(WEB_URL + "/API/index.php/News/all", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                RequestOptions opsi = new RequestOptions().centerCrop().placeholder(R.drawable.bnw).error(R.drawable.bnw);


                JSONObject jsonObject1;
                JSONObject jsonObject;
                String ketDate, ketDate1;

                Date date = new Date();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String today = df.format(date);
                String pecahToday[] = today.split("-");
                tahun = Integer.valueOf(pecahToday[0]);
                bulan = Integer.valueOf(pecahToday[1]);
                hari = Integer.valueOf(pecahToday[2]);

                try {
                    jsonObject = response.getJSONObject(0);
                    headlineTitle.setText(jsonObject.getString("title"));
                    headlineSumber.setText(jsonObject.getString("Sumber"));

                    Glide.with(getActivity()).load(jsonObject.getString("img")).apply(opsi).into(headlineImg);

                    String tanggal = jsonObject.getString("tanggal");

                    if (!tanggal.equals("error")) {
                        String pecahTanggal[] = tanggal.split("-");


                        int tahunJson = Integer.valueOf(pecahTanggal[0]);
                        int bulanJson = Integer.valueOf(pecahTanggal[1]);
                        int hariJson = Integer.valueOf(pecahTanggal[2]);
                        if (tahun - tahunJson == 0) {
                            if (bulan - bulanJson == 0) {
                                if (hari - hariJson == 0) {
                                    ketDate = "Hari ini";
                                } else {
                                    ketDate = String.valueOf(hari - hariJson) + " hari yang lalu";
                                }
                            } else {
                                ketDate = String.valueOf(bulan - bulanJson) + " bulan yang lalu";
                            }
                        } else {
                            ketDate = String.valueOf(tahun - tahunJson) + " tahun yang lalu";
                        }

                        headlineTanggal.setText(ketDate);
                        textLayout.setVisibility(View.VISIBLE);

                        setTitleBaru(jsonObject.getString("title"));
                        setSumberBaru(jsonObject.getString("Sumber"));
                        setKetDateBaru(ketDate);
                        setTeksBaru(jsonObject.getString("teks"));
                        setImgBaru(jsonObject.getString("img"));
                        setLinkBaru(jsonObject.getString("link"));
                        setSlugBaru(jsonObject.getString("slug"));

                    }


                    for (int i = 1; i < 6; i++) {

                        jsonObject1 = response.getJSONObject(i);

                        News news = new News();

                        hasil = response;

                        if (!jsonObject1.getString("tanggal").equals("error")) {
                            news.setTitle(jsonObject1.getString("title"));
                            news.setSumber(jsonObject1.getString("Sumber"));
                            news.setImg(jsonObject1.getString("img"));
                            news.setLink(jsonObject1.getString("link"));
                            news.setTeks(jsonObject1.getString("teks"));
                            news.setSlug(jsonObject1.getString("slug"));


                            String tanggal1 = jsonObject1.getString("tanggal");


                            String pecahTanggal1[] = tanggal1.split("-");


                            int tahunJson1 = Integer.valueOf(pecahTanggal1[0]);
                            int bulanJson1 = Integer.valueOf(pecahTanggal1[1]);
                            int hariJson1 = Integer.valueOf(pecahTanggal1[2]);


                            if (tahun - tahunJson1 == 0) {
                                if (bulan - bulanJson1 == 0) {
                                    if (hari - hariJson1 == 0) {
                                        ketDate1 = "Hari ini";
                                    } else {
                                        ketDate1 = String.valueOf(hari - hariJson1) + " hari yang lalu";
                                    }
                                } else {
                                    ketDate1 = String.valueOf(bulan - bulanJson1) + " bulan yang lalu";
                                }
                            } else {
                                ketDate1 = String.valueOf(tahun - tahunJson1) + " tahun yang lalu";
                            }


                            news.setTanggal(ketDate1);

                            lstNews.add(news);
                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                setupRecyclerView(lstNews);
                headlineImg.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

    private void setupRecyclerView(final List<News> lstNews) {


        myadapter = new RecyclerViewAdapter(lstNews, getActivity());

        manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);

        recyclerView.setAdapter(myadapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    System.out.println(isScrolling);
                    isScrolling = true;
                    System.out.println(isScrolling);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                current = manager.getChildCount();
                total = manager.getItemCount();
                scroll = manager.findFirstVisibleItemPosition();

                System.out.println(isScrolling);
                System.out.println(current);
                System.out.println(total);
                System.out.println(scroll);

                if (isScrolling && (current + scroll == total)) {
                    isScrolling = false;

                    System.out.println("msk");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            int awal = total+1;
                            int akhir = total + 5;
                            JSONObject jsonObject2;
                            String ketDate3;

                            try {

                                for (int i = awal; i < akhir; i++) {

                                    jsonObject2 = hasil.getJSONObject(i);

                                    News news = new News();


                                    if (!jsonObject2.getString("tanggal").equals("error")) {
                                        news.setTitle(jsonObject2.getString("title"));
                                        news.setSumber(jsonObject2.getString("Sumber"));
                                        news.setImg(jsonObject2.getString("img"));
                                        news.setLink(jsonObject2.getString("link"));
                                        news.setTeks(jsonObject2.getString("teks"));
                                        news.setSlug(jsonObject2.getString("slug"));


                                        String tanggal1 = jsonObject2.getString("tanggal");


                                        String pecahTanggal1[] = tanggal1.split("-");


                                        int tahunJson1 = Integer.valueOf(pecahTanggal1[0]);
                                        int bulanJson1 = Integer.valueOf(pecahTanggal1[1]);
                                        int hariJson1 = Integer.valueOf(pecahTanggal1[2]);


                                        if (tahun - tahunJson1 == 0) {
                                            if (bulan - bulanJson1 == 0) {
                                                if (hari - hariJson1 == 0) {
                                                    ketDate3 = "Hari ini";
                                                } else {
                                                    ketDate3 = String.valueOf(hari - hariJson1) + " hari yang lalu";
                                                }
                                            } else {
                                                ketDate3 = String.valueOf(bulan - bulanJson1) + " bulan yang lalu";
                                            }
                                        } else {
                                            ketDate3 = String.valueOf(tahun - tahunJson1) + " tahun yang lalu";
                                        }


                                        news.setTanggal(ketDate3);

                                        lstNews.add(news);
                                        myadapter.notifyDataSetChanged();
                                    }

                                }
                            } catch (JSONException e){
                                e.printStackTrace();
                            }

                        }
                    }, 5000);


                }

            }
        });

    }


    public String getImgBaru() {
        return imgBaru;
    }

    public void setImgBaru(String imgBaru) {
        this.imgBaru = imgBaru;
    }

    public String getSumberBaru() {
        return sumberBaru;
    }

    public void setSumberBaru(String sumberBaru) {
        this.sumberBaru = sumberBaru;
    }

    public String getTeksBaru() {
        return teksBaru;
    }

    public void setTeksBaru(String teksBaru) {
        this.teksBaru = teksBaru;
    }

    public String getLinkBaru() {
        return linkBaru;
    }

    public void setLinkBaru(String linkBaru) {
        this.linkBaru = linkBaru;
    }

    public String getKetDateBaru() {
        return ketDateBaru;
    }

    public void setKetDateBaru(String ketDateBaru) {
        this.ketDateBaru = ketDateBaru;
    }

    public void setTitleBaru(String titleBaru) {
        this.titleBaru = titleBaru;
    }

    public String getTitleBaru() {
        return titleBaru;
    }


    public String getSlugBaru() {
        return slugBaru;
    }

    public void setSlugBaru(String slugBaru) {
        this.slugBaru = slugBaru;
    }


}



