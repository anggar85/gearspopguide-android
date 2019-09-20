package com.mxlapps.app.gearspopguide.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mxlapps.app.gearspopguide.Model.SynergiModel;
import com.mxlapps.app.gearspopguide.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SynergiesAdapter extends RecyclerView.Adapter<SynergiesAdapter.SynergiViewHolder>{

    private ArrayList<SynergiModel> synergi;

    public SynergiesAdapter(ArrayList<SynergiModel> synergi) {
        this.synergi = synergi;
    }

    @NonNull
    @Override
    public SynergiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_items_synergies, parent, false);
        return new SynergiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SynergiViewHolder holder, int position) {
        final SynergiModel synergiModel = synergi.get(position);
//        holder.textview_synergi_hero_name.setText(synergiModel.getName());
        Picasso.get().load(synergiModel.getIcon()).into(holder.imageView_synergi_icon);
    }

    @Override
    public int getItemCount() {
        return synergi.size();
    }

    class SynergiViewHolder extends RecyclerView.ViewHolder {

//        TextView textview_synergi_hero_name;
        ImageView imageView_synergi_icon;

        SynergiViewHolder(@NonNull View itemView) {
            super(itemView);
//            textview_synergi_hero_name    = itemView.findViewById(R.id.textview_synergi_hero_name);
            imageView_synergi_icon = itemView.findViewById(R.id.imageView_artifact_icon);
        }
    }
}
