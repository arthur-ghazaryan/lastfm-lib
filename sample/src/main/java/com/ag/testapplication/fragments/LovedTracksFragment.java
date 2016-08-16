package com.ag.testapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ag.lfm.LfmError;
import com.ag.lfm.LfmParameters;
import com.ag.lfm.LfmRequest;
import com.ag.lfm.Session;
import com.ag.lfm.api.LfmApi;
import com.ag.testapplication.LovedTrack;
import com.ag.testapplication.LovedTracksAdapter;
import com.ag.testapplication.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class LovedTracksFragment extends Fragment {

    private static final int LAYOUT = R.layout.lovedtracks_fragment;
    private View mainView;
    private RecyclerView mRecyclerView;
    private LovedTracksAdapter adapter;
    private List<LovedTrack> lovedTracks = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(LAYOUT,container,false);
        initRecyclerView();
        getLovedTracks();
        return mainView;
    }


    private void initRecyclerView(){
        mRecyclerView = (RecyclerView) mainView.findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void getLovedTracks(){
        LfmParameters params = new LfmParameters();
        LfmRequest request = LfmApi.user().getLovedTracks(params);
        params.put("user", Session.username);
        request.executeWithListener(new LfmRequest.LfmRequestListener() {
            @Override
            public void onComplete(JSONObject response) {
                JSONArray tracks = response.optJSONObject("lovedtracks").optJSONArray("track");
                for(int i=0;i<tracks.length();i++){
                    lovedTracks.add(new LovedTrack(
                            tracks.optJSONObject(i).optJSONObject("artist").optString("name"),
                            tracks.optJSONObject(i).optString("name"),
                            tracks.optJSONObject(i).optJSONArray("image").optJSONObject(1).optString("#text")));
                }
                adapter = new LovedTracksAdapter(getActivity(),lovedTracks);
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(LfmError error) {
                Toast.makeText(getActivity(),error.errorMessage,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
