package com.mxlapps.app.gearspopguide.Adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mxlapps.app.gearspopguide.Model.RolDefinitionModel;
import com.mxlapps.app.gearspopguide.R;

import java.util.ArrayList;

public class RolDefinitionAdapter extends RecyclerView.Adapter<RolDefinitionAdapter.RolDefinitionViewHolder>{

    private ArrayList<RolDefinitionModel> rolDefinition;

    public RolDefinitionAdapter(ArrayList<RolDefinitionModel> rolDefinition) {
        this.rolDefinition = rolDefinition;
    }

    @NonNull
    @Override
    public RolDefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rol_definition, parent, false);
        return new RolDefinitionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RolDefinitionViewHolder holder, int position) {
        final RolDefinitionModel item = rolDefinition.get(position);
        holder.textView_rol_title.setText(item.getRol());
        holder.textView_rol_desc.setText(Html.fromHtml(item.getDesc()));
    }

    @Override
    public int getItemCount() {
        return rolDefinition.size();
    }

    class RolDefinitionViewHolder extends RecyclerView.ViewHolder {

        TextView textView_rol_desc;
        TextView textView_rol_title;

        RolDefinitionViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_rol_title    = itemView.findViewById(R.id.textView_rol_title);
            textView_rol_desc    = itemView.findViewById(R.id.textView_rol_desc);
        }
    }
}
