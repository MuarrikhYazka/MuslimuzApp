package com.muslimuz.muslimuzapp.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.muslimuz.muslimuzapp.R;
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

import static com.muslimuz.muslimuzapp.fragment.HomeFragment.WEB_URL;


public class SearchFragment extends Fragment {
    View myView;
    private List<News> lstSearch;
    public LinearLayout textLayout;
    private RecyclerView recyclerView;
    public ImageView headlineImg;
    SearchView sv;
    private Handler mHandler = new Handler();
    private String key1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = myView.findViewById(R.id.recyclerViewSearch);
        // Inflate the layout for this fragment

        sv = myView.findViewById(R.id.searchView);
        lstSearch = new ArrayList<>();

        jsonRequest("");

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {


            @Override
            public boolean onQueryTextChange(String newText) {
                mHandler.removeCallbacks(mFilterTask);
                mHandler.postDelayed(mFilterTask, 200);

                key1 = newText;


                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }


        });


        return myView;
    }

    Runnable mFilterTask = new Runnable() {

        @Override
        public void run() {
            recyclerView.setAdapter(null);
            lstSearch.clear();

            jsonRequest(key1);

        }
    };


    private void jsonRequest(String key) {
        String URL;
        if (key == null || key.equals("")) {
            URL = WEB_URL + "/API/index.php/News/cari/key/a";
        } else {

             URL = WEB_URL + "/API/index.php/News/cari/key/" + key;
        }

        System.out.println(URL);

        JsonArrayRequest request = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                JSONObject jsonObject;
                String ketDate;
                Date date = new Date();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String today = df.format(date);
                String pecahToday[] = today.split("-");
                int tahun = Integer.valueOf(pecahToday[0]);
                int bulan = Integer.valueOf(pecahToday[1]);
                int hari = Integer.valueOf(pecahToday[2]);


                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        News search = new News();
                        search.setTitle(jsonObject.getString("title"));
                        search.setSumber(jsonObject.getString("Sumber"));

                        search.setImg(jsonObject.getString("img"));
                        search.setLink(jsonObject.getString("link"));
                        search.setTeks(jsonObject.getString("teks"));
                        search.setSlug(jsonObject.getString("slug"));

                        String tanggal = jsonObject.getString("tanggal");

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

                        search.setTanggal(ketDate);


                        lstSearch.add(search);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                setupRecyclerView(lstSearch);
            }
        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("slh");

            }

        });


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void setupRecyclerView(List<News> lstSearch) {
        RecyclerViewAdapter myadaptersearch = new RecyclerViewAdapter(getActivity(), lstSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(myadaptersearch);
    }


}
