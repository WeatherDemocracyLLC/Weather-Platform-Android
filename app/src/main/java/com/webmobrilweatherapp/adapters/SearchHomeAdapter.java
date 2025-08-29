package com.webmobrilweatherapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.webmobrilweatherapp.model.SearchHomeModel;
import com.webmobrilweatherapp.R;
import com.webmobrilweatherapp.activities.SearchMainActivity;
import com.webmobrilweatherapp.activities.ViewPagerActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchHomeAdapter extends RecyclerView.Adapter<SearchHomeAdapter.Myviewholder> {
    private Context context;
    private List<SearchHomeModel> SearchHomeModel;

    public SearchHomeAdapter(Context context, List<SearchHomeModel> SearchHomeModel) {
        this.context = context;
        this.SearchHomeModel = SearchHomeModel;
    }

    @NotNull
    @Override
    public SearchHomeAdapter.Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchrecycleview, parent, false);
        SearchHomeAdapter.Myviewholder myviewholder = new SearchHomeAdapter.Myviewholder(view);
        return myviewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHomeAdapter.Myviewholder holder, int position) {
        holder.imguseraddlinehome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ViewPagerActivity.class));
            }
        });

        holder.imgsearchlogopichome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, SearchMainActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return SearchHomeModel.size();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        ImageView imgsearchlogopichome, imguseraddlinehome;
        TextView Christianhome, Liveinneapolishome;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            imgsearchlogopichome = itemView.findViewById(R.id.imgsearchlogopichome);
            imguseraddlinehome = itemView.findViewById(R.id.imguseraddlinehome);
            Christianhome = itemView.findViewById(R.id.Christianhome);
            Liveinneapolishome = itemView.findViewById(R.id.Liveinneapolishome);

        }
    }
}



