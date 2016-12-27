package com.easysales.calllog.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.easysales.calllog.Data.CallAdapter;
import com.easysales.calllog.Entities.Call;
import com.easysales.calllog.R;
import com.easysales.calllog.Repository.RepositoryFactory;

import java.util.ArrayList;
import java.util.List;

public class CallListFragment extends Fragment {

    CallAdapter adapter;
    List<Call> calls;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_call_list, null);

        GridView gridView = (GridView)view.findViewById(R.id.callGrid);
//        Button buttonRefresh = (Button)view.findViewById(R.id.buttonRefresh);

        calls = new ArrayList<Call>();
        adapter = new CallAdapter(getActivity(), calls);
        gridView.setAdapter(adapter);

//        buttonRefresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RefreshCalls();
//            }
//        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowCallActivity((Call) parent.getItemAtPosition(position));
            }
        });

        RefreshCalls();
        return view;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_call_list);
//
//        GridView gridView = (GridView)findViewById(R.id.callGrid);
//        Button buttonRefresh = (Button)findViewById(R.id.buttonRefresh);
//
//        calls = new ArrayList<Call>();
//        RefreshCalls();
//        adapter = new CallAdapter(this, calls);
//        gridView.setAdapter(adapter);
//
//        buttonRefresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RefreshCalls();
//                adapter.notifyDataSetChanged();
//            }
//        });
//
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ShowCallActivity((Call)parent.getItemAtPosition(position));
//            }
//        });
//    }

    private void ShowCallActivity(Call call)
    {
        Intent intent = new Intent(getActivity(), CallActivity.class);
        intent.putExtra("Call", call);
        startActivity(intent);
    }

    public void RefreshCalls()
    {
        calls.clear();
        calls.addAll(RepositoryFactory.GetRepository(getActivity(), RepositoryFactory.RepositoryNames.CALL_REPOSITORY).FindAllList());
        adapter.notifyDataSetChanged();
    }
}
