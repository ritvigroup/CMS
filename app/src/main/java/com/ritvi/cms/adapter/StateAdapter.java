package com.ritvi.cms.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ritvi.cms.R;
import com.ritvi.cms.Util.TagUtils;
import com.ritvi.cms.activity.StateSelectActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunil on 03-11-2017.
 */

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.ViewHolder> implements Filterable {
    private List<String> items;
    public List<String> countryFiltered;
    Activity activity;
    Fragment fragment;

    public StateAdapter(Activity activity, Fragment fragment, List<String> items) {
        this.items = items;
        this.countryFiltered=new ArrayList<>(this.items);
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_state, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_state_name.setText(countryFiltered.get(position));

        holder.ll_state_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activity instanceof StateSelectActivity){
                    StateSelectActivity stateSelectActivity= (StateSelectActivity) activity;
                    stateSelectActivity.showSelectedState(countryFiltered.get(position));
                }
            }
        });

        holder.itemView.setTag(countryFiltered.get(position));
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                Log.d(TagUtils.getTag(),"search sequence:-"+charSequence);
                String charString = charSequence.toString();
                List<String> filteredList=new ArrayList<>();
                if (charString.isEmpty()||charString.equals(" ")) {
                    Log.d(TagUtils.getTag(),"empty search:-"+items.size());
                    filteredList.addAll(items);
                } else {
                    for (String row : items) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                }
                Log.d(TagUtils.getTag(),"filtered size:-"+filteredList.size());
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                countryFiltered.clear();
                countryFiltered.addAll((ArrayList<String>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return countryFiltered.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_state_name;
        public LinearLayout ll_state_name;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_state_name = (TextView) itemView.findViewById(R.id.tv_state_name);
            ll_state_name = (LinearLayout) itemView.findViewById(R.id.ll_state_name);
        }
    }
}
