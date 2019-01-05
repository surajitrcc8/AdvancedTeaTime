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

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.example.android.teatime.IdlingResource.SimpleIdlingResource;
import com.example.android.teatime.databinding.ActivityMenuBinding;
import com.example.android.teatime.model.Tea;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements ImageDownloader.DelayerCallback, TeaMenuAdapter.OnItemClickListener {

    Intent mTeaIntent;

    public final static String EXTRA_TEA_NAME = "com.example.android.teatime.EXTRA_TEA_NAME";
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    // The Idling Resource which will be null in production.
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMenuBinding activityMenuBinding = DataBindingUtil.setContentView(this, R.layout.activity_menu);
        //setContentView(R.layout.activity_menu);
        Toolbar menuToolbar = activityMenuBinding.menuToolbar;
        setSupportActionBar(menuToolbar);
        getSupportActionBar().setTitle(getString(R.string.menu_title));
        progressBar = activityMenuBinding.pbLoading;
        recyclerView = activityMenuBinding.rvTeaList;

        // Get the IdlingResource instance
        getIdlingResource();
    }

    /**
     * We call ImageDownloader.downloadImage from onStart or onResume instead of in onCreate
     * to ensure there is enougth time to register IdlingResource if the download is done
     * too early (i.e. in onCreate)
     */
    @Override
    protected void onStart() {
        super.onStart();
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        ImageDownloader.downloadImage(this, MenuActivity.this, mIdlingResource);
    }

    /**
     * When the thread in {@link ImageDownloader} is finished, it will return an ArrayList of Tea
     * objects via the callback's onDone().
     */
    @Override
    public void onDone(ArrayList<Tea> teas) {

        TeaMenuAdapter adapter = new TeaMenuAdapter(R.layout.grid_item_layout, teas, this);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        adapter.setTeas(teas);
    }

    @Override
    public void onItemClick(Tea tea) {
        mTeaIntent = new Intent(MenuActivity.this, OrderActivity.class);
        mTeaIntent.putExtra(EXTRA_TEA_NAME, tea.getTeaName());
        startActivity(mTeaIntent);
    }
}