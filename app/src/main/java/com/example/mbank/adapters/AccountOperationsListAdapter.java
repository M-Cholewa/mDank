package com.example.mbank.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mbank.R;
import com.example.mbank.pojos.AccountOperation;

import java.util.List;

public class AccountOperationsListAdapter extends ArrayAdapter<AccountOperation> {

    private static final String TAG = "AccountOperationsListAd";
    private Context mContext;
    private int resource;

    private static class ViewHolder {
        TextView operationName;
        TextView operationBalance;
        ImageView operationImage;
    }


    AccountOperationsListAdapter(@NonNull Context context, int resource, @NonNull List<AccountOperation> objects) {
        super(context, resource, objects);
        this.resource = resource;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String operationName = getItem(position).getOperationName();
        double operationBalance = getItem(position).getOperationBalance();
        int operationImage = getItem(position).getOperationImage();
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.operationBalance = convertView.findViewById(R.id.operationBalance);
            holder.operationName = convertView.findViewById(R.id.operationName);
            holder.operationImage = convertView.findViewById(R.id.operationImage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (operationImage == R.drawable.ic_fab_payments) {
            holder.operationImage.setColorFilter(getColor(R.color.mBankGreen));
        } else if (operationImage == R.drawable.ic_fab_card) {
            holder.operationImage.setColorFilter(getColor(R.color.mBankBlue));
        }

        holder.operationBalance.setTextColor(operationBalance > 0
                ? getColor(R.color.mBankGreen)
                : getColor(R.color.lightBlack));

        holder.operationImage.setImageResource(operationImage);
        holder.operationBalance.setText(operationBalance + " PLN");
        holder.operationName.setText(operationName);

        return convertView;
    }

    private int getColor(int res) {
        return mContext.getResources().getColor(res, null);
    }
}
