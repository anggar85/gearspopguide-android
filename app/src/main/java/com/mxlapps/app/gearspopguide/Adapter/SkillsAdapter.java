package com.mxlapps.app.gearspopguide.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mxlapps.app.gearspopguide.Model.SkillModel;
import com.mxlapps.app.gearspopguide.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.HeroViewHolder>{

    private ArrayList<SkillModel> skillModels;
    private OnItemClickListener mlistener;
    Context ctx;


    public void SetOnItemClickListener (OnItemClickListener mlistener) {
        this.mlistener = mlistener;
    }

    public interface OnItemClickListener {
        void onHeroCardClick(int position);
    }



    public SkillsAdapter(ArrayList<SkillModel> skillModels) {
        this.skillModels = skillModels;
    }

    @NonNull
    @Override
    public SkillsAdapter.HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_skill, parent, false);
        return new HeroViewHolder(view, mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull final SkillsAdapter.HeroViewHolder holder, int position) {

        SkillModel skill = skillModels.get(position);
        holder.name.setText(skill.getSkill());
        holder.desc.setText(skill.getDesc());
        holder.lvl.setText(skill.getLvlUpgrades());
//            holder.desc.setText(skill.getRace_name());

        Picasso.get().load(skill.getSkillIcon())
                .into(holder.smallImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    private void loadHeroImage(String name, HeroViewHolder holder) {


    }

    @Override
    public int getItemCount() {
        return skillModels.size();
    }

    public class HeroViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView desc;
        TextView lvl;
        ProgressBar progressBar;
        ImageView smallImage;
        ImageView imageView_strong, imageView_weak;

        CardView cardView_hero_item;

        public HeroViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.homeprogress_skill);
            name    = itemView.findViewById(R.id.textView_skill_name);
            desc    = itemView.findViewById(R.id.textView_skill_desc);
            lvl    = itemView.findViewById(R.id.textView_skill_lvl);
            smallImage = itemView.findViewById(R.id.imageView_skill_image);


//            cardView_hero_item = itemView.findViewById(R.id.cardView_hero_item);
//
//            cardView_hero_item.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null){
//                        listener.onHeroCardClick(getAdapterPosition());
//                    }
//                }
//            });



        }
    }

    public void stringWeakto(Integer type, SkillsAdapter.HeroViewHolder holder){
//        switch (type){
//            case 1:
//                Picasso.get().load(R.drawable.orc_i).into(holder.imageView_strong);
//                Picasso.get().load(R.drawable.undead_i).into(holder.imageView_weak);
//                break;
//            case 2:
//                Picasso.get().load(R.drawable.elf_i).into(holder.imageView_strong);
//                Picasso.get().load(R.drawable.humen_i).into(holder.imageView_weak);
//                break;
//            case 3:
//                Picasso.get().load(R.drawable.undead_i).into(holder.imageView_strong);
//                Picasso.get().load(R.drawable.orc_i).into(holder.imageView_weak);
//                break;
//            case 4:
//                Picasso.get().load(R.drawable.humen_i).into(holder.imageView_strong);
//                Picasso.get().load(R.drawable.elf_i).into(holder.imageView_weak);
//                break;
//        }
    }


    public static Drawable findHeroImage(String hero_name){


        return null;

    }
}
