package com.example.inlab.calculadora;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inlab.calculadora.Retrofit.APIService;
import com.example.inlab.calculadora.Retrofit.ApiUtils;
import com.example.inlab.calculadora.Retrofit.GetPuntuacion;
import com.example.inlab.calculadora.Retrofit.Puntuacione;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPuntuaciones extends Fragment implements RecyclerViewTouchDelegate{

    private RecyclerView recyclerView;
    private Realm realm;
    private RealmResults<User> userResults;

    public FragmentPuntuaciones() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_puntuaciones, container, false);

        initRealm();
        loadRealmData();

        recyclerView = view.findViewById(R.id.recyclerView);
        setRecyclerView();

        return view;
    }

    void setRecyclerView() {

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()); //getapplicationcontext
        recyclerView.setLayoutManager(linearLayoutManager);

        MyCustomAdapter adapter = new MyCustomAdapter();
        ArrayList<String> dataSet = new ArrayList<>();

        for (int i = 0; i < userResults.size(); i++) {
            User u = userResults.get(i);
            if (u.getRecord() != 1000000) {
                dataSet.add("\n" + u.getUsername() + "\n\nBest: " + u.getRecord() + " moves in " + u.getRecord_time() + " seconds"
                + " at " + u.getFecha_record());
            }
        }

        adapter.dataSet = dataSet;
        adapter.delegate = this;

        recyclerView.setAdapter(adapter);
    }

    void initRealm(){
        Realm.init(getContext());
        realm = Realm.getDefaultInstance();
    }

    private void loadRealmData() {
        userResults = realm.where(User.class).findAll().sort("record", Sort.ASCENDING, "record_time", Sort.ASCENDING);
    }

        /**Conectamos con la API que nos proporciona las puntuaciones subidas
        APIService apiService = ApiUtils.getAPIService();
        Call<GetPuntuacion> call = apiService.getPuntuaciones();

        final MyCustomAdapter adapter = new MyCustomAdapter();
        //adapter.dataSet = new ArrayList<>();

        call.enqueue(new Callback<GetPuntuacion>() {
            @Override
            public void onResponse(Call<GetPuntuacion> call, Response<GetPuntuacion> response) {
                adapter.dataSet = response.body().getPuntuaciones();
            }

            @Override
            public void onFailure(Call<GetPuntuacion> call, Throwable t) {

            }
        });

        adapter.delegate = this;
        recyclerView.setAdapter(adapter);

         **/


        /**
        MyCustomAdapter adapter = new MyCustomAdapter();
        ArrayList<String> dataSet = new ArrayList<>();

        dataSet.add("1");
        dataSet.add("2");
        dataSet.add("3");
        dataSet.add("4");

        adapter.dataSet = dataSet;
        adapter.delegate = this;

        recyclerView.setAdapter(adapter);

         **/



    @Override
    public void didSelectedItemAtRow(int row) {

    }

    @Override
    public void longPressSelectedItemAtRow(int row) {

    }

}
