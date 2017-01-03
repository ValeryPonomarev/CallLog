package com.easysales.calllog.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.easysales.calllog.Data.CallAdapter;
import com.easysales.calllog.Entities.Call;
import com.easysales.calllog.Entities.EntityFactoryBuilder;
import com.easysales.calllog.R;
import com.easysales.calllog.Repository.Call.ICallRepository;
import com.easysales.calllog.Repository.RepositoryFactory;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class CallListFragment extends Fragment {

    CallAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_call_list, null);

        GridView gridView = (GridView)view.findViewById(R.id.callGrid);

        adapter = new CallAdapter(getActivity(), ((ICallRepository)RepositoryFactory.GetRepository(getActivity(), RepositoryFactory.RepositoryNames.CALL_REPOSITORY)).FindAllOrderedByDateDesc());
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowCallActivity((Call) parent.getItemAtPosition(position));
            }
        });

        //RefreshCalls();
        return view;
    }

    private void ShowCallActivity(Call call)
    {
        Intent intent = new Intent(getActivity(), CallActivity.class);
        intent.putExtra("Call", call);
        startActivity(intent);
    }

    public void RefreshCalls()
    {
        adapter.changeCursor(((ICallRepository)RepositoryFactory.GetRepository(getActivity(), RepositoryFactory.RepositoryNames.CALL_REPOSITORY)).FindAllOrderedByDateDesc());
    }
}
