package com.mxlapps.app.gearspopguide.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mxlapps.app.gearspopguide.Model.DeckCommentsModel;
import com.mxlapps.app.gearspopguide.Model.DeckCommentsModel;
import com.mxlapps.app.gearspopguide.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DeckCommentsAdapter extends RecyclerView.Adapter<DeckCommentsAdapter.HeroViewHolder> {

    private ArrayList<DeckCommentsModel> deckCommentsModel;
    private OnItemClickListener mlistener;
    Context ctx;
    int modo = 1;


    public void SetOnItemClickListener (OnItemClickListener mlistener) {
        this.mlistener = mlistener;
    }


    public interface OnItemClickListener {
        void onHeroCardClick(int position);
    }


    public DeckCommentsAdapter(ArrayList<DeckCommentsModel> deckCommentsModel, Context context, int modo) {
        this.deckCommentsModel = deckCommentsModel;
        this.ctx = context;
        this.modo = modo;
    }

    @NonNull
    @Override
    public DeckCommentsAdapter.HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_deck_comment, parent, false);
        return new HeroViewHolder(view, mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull final DeckCommentsAdapter.HeroViewHolder holder, int position) {

        DeckCommentsModel comment = deckCommentsModel.get(position);
        holder.user.setText(String.valueOf(comment.getUsuario()));
        holder.comment.setText(comment.getComment());
//        Picasso.get().load(comment.getSmallImage()).into(holder.smallImage);
    }


    @Override
    public int getItemCount() {
        return deckCommentsModel.size();
    }

    public class HeroViewHolder extends RecyclerView.ViewHolder {

        TextView user;
        TextView comment;
        CardView cardView_pin_item;

        public HeroViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            user = itemView.findViewById(R.id.textView_user);
            comment= itemView.findViewById(R.id.textView_coment);

//            cardView_pin_item.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null){
//                        listener.onHeroCardClick(getAdapterPosition());
//                    }
//                }
//            });
        }
    }
    
}
