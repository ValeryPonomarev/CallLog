package com.easysales.calllog.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.easysales.calllog.Data.CallAdapter;
import com.easysales.calllog.Entities.Call;
import com.easysales.calllog.R;
import com.easysales.calllog.Repository.RepositoryFactory;

import java.util.ArrayList;
import java.util.List;

public class CallListActivity extends Activity {

    CallAdapter adapter;
    List<Call> calls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_list);

        GridView gridView = (GridView)findViewById(R.id.callGrid);
        Button buttonRefresh = (Button)findViewById(R.id.buttonRefresh);

        calls = new ArrayList<Call>();
        RefreshCalls();
        adapter = new CallAdapter(this, calls);
        gridView.setAdapter(adapter);

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshCalls();
                adapter.notifyDataSetChanged();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowCallActivity((Call)parent.getItemAtPosition(position));
            }
        });
    }

    private void ShowCallActivity(Call call)
    {
        Intent intent = new Intent(this, CallActivity.class);
        intent.putExtra("Call", call);
        startActivity(intent);
    }

    private void RefreshCalls()
    {
        calls.clear();
        calls.addAll(RepositoryFactory.GetRepository(this, RepositoryFactory.RepositoryNames.CALL_REPOSITORY).FindAllList());
    }
}
