package com.example.mbank.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mbank.R;
import com.example.mbank.pojos.AccountOperation;
import com.example.mbank.utils.UIUtils;
import com.example.mbank.views.FullListView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private ArrayList<ArrayList<AccountOperation>> operationArrayList;
    private ArrayList<String> operationDates;
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> operationDates, ArrayList<ArrayList<AccountOperation>> operationArrayList, Context context) {
        this.operationDates = operationDates;
        this.operationArrayList = operationArrayList;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_layout, parent, false);
        return new RecyclerViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.dateTV.setText(operationDates.get(position));
        AccountOperationsListAdapter listAdapter = new AccountOperationsListAdapter(mContext, R.layout.listview_item_layout, operationArrayList.get(position));
        holder.operationsList.setAdapter(listAdapter);
        UIUtils.setListViewHeightBasedOnItems(holder.operationsList);
    }

    @Override
    public int getItemCount() {
        return operationDates.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView dateTV;
        FullListView operationsList;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTV = itemView.findViewById(R.id.dateTV);
            operationsList = itemView.findViewById(R.id.operationsList);
        }
    }
}
