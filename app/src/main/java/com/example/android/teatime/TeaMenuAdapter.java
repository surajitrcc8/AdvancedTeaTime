/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.teatime;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.teatime.ViewModel.TeaViewModel;
import com.example.android.teatime.model.Tea;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * TeaMenuAdapter is backed by an ArrayList of {@link Tea} objects which populate
 * the GridView in MenuActivity
 */

public class TeaMenuAdapter extends RecyclerView.Adapter<TeaMenuAdapter.TeaViewHolder> {

    private int layoutResourceId;
    private ArrayList<Tea> teas = new ArrayList<Tea>();
    private OnItemClickListener onItemClickListener;

    interface OnItemClickListener {
        public void onItemClick(Tea tea);
    }

    public TeaMenuAdapter(int layoutResourceId, ArrayList<Tea> teas, OnItemClickListener onItemClickListener) {
        this.layoutResourceId = layoutResourceId;
        this.teas = teas;
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public TeaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(layoutInflater, layoutResourceId, parent, false);
        return new TeaViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(TeaViewHolder holder, int position) {
        holder.bindView(position);
    }


    @Override
    public int getItemCount() {
        return (teas == null || teas.size() == 0) ? 0 : teas.size();
    }

    public void setTeas(ArrayList<Tea> teas) {
        this.teas = teas;
        notifyDataSetChanged();
    }

    @BindingAdapter(value = {"imageUrl"}, requireAll = true)
    public static void loadImage(ImageView imageView, int resID){
        Picasso.with(imageView.getContext()).load(resID).into(imageView);
    }

    public class TeaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ViewDataBinding itemView;
        public TeaViewHolder(ViewDataBinding itemView) {
            super(itemView.getRoot());
            this.itemView = itemView;
            itemView.getRoot().setOnClickListener(this);
        }
        public void bindView(int position) {
            itemView.setVariable(BR.teaViewModel, new TeaViewModel(teas.get(position)));
            itemView.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(teas.get(getAdapterPosition()));
        }
    }
 }