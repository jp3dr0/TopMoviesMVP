package com.joaop.mvpdemonstration.top_movies.mvp.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joaop.mvpdemonstration.R;
import com.joaop.mvpdemonstration.top_movies.mvp.model.models.ViewModel;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListItemVH>{

    private List<ViewModel> list;

    public ListAdapter(List<ViewModel> list) {
        this.list = list;
    }


    @Override
    public ListItemVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new ListItemVH(view);
    }

    @Override
    public void onBindViewHolder(ListItemVH holder, int position) {
        holder.itemName.setText(list.get(position).getName());
        holder.countryName.setText(list.get(position).getCountry());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ListItemVH extends RecyclerView.ViewHolder {
        private TextView itemName, countryName;

        public ListItemVH(View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.textView_fragmentlist_task_name);
            countryName = itemView.findViewById(R.id.textView_fragmentlist_country_name);
        }
    }
}
