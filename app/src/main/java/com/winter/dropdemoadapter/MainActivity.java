package com.winter.dropdemoadapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.winter.dropdemoadapter.adapter.ConstellationAdapter;
import com.winter.dropdemoadapter.adapter.GirdDropDownAdapter;
import com.winter.dropdemoadapter.adapter.ListDropDownAdapter;
import com.winter.dropdemoadapter.databinding.ActivityMainBinding;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private List<View> popupViews = new ArrayList<>();
    private String headers[] = {"荤素","耗时","主材"};
    ConstellationAdapter constellationAdapter;
    ListDropDownAdapter listDropDownAdapter;
    ListDropDownAdapter meatOrVeAdapter;
    List<String> meatMainMaterial = new ArrayList<>();
    List<String> useTime = new ArrayList<>();
    List<String> meatOrVe = new ArrayList<>();

    private int constellationPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        meatMainMaterial = Arrays.asList(getResources().getStringArray(R.array.Meat_Main_Material));
        useTime =  Arrays.asList(getResources().getStringArray(R.array.food_use_time));
        meatOrVe =  Arrays.asList(getResources().getStringArray(R.array.is_vegetable));
        initMeatOrVegeView();
        initUseTimeView();
        initMaterialView();

        TextView contentView = new TextView(this);
        binding.dropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);
    }

    private void initMeatOrVegeView() {
        final ListView meatOrVeListView = new ListView(this);
        meatOrVeAdapter = new ListDropDownAdapter(this, meatOrVe);
        meatOrVeListView.setDividerHeight(0);
        meatOrVeListView.setAdapter(meatOrVeAdapter);
        popupViews.add(meatOrVeListView);
        meatOrVeListView.setOnItemClickListener((parent, view, position, id) -> {
            meatOrVeAdapter.setCheckItem(position);
            binding.dropDownMenu.setTabText(position == 0 ? headers[0] : meatOrVe.get(position));
            binding.dropDownMenu.closeMenu();
        });
    }

    private void initUseTimeView() {
        final ListView useTimeListView = new ListView(this);
        listDropDownAdapter = new ListDropDownAdapter(this, useTime);
        useTimeListView.setDividerHeight(0);
        useTimeListView.setAdapter(listDropDownAdapter);
        popupViews.add(useTimeListView);
        useTimeListView.setOnItemClickListener((parent, view, position, id) -> {
            listDropDownAdapter.setCheckItem(position);
            binding.dropDownMenu.setTabText(position == 0 ? headers[1] : useTime.get(position));
            binding.dropDownMenu.closeMenu();
        });
    }

    private void initMaterialView() {
        final View constellationView = getLayoutInflater().inflate(R.layout.custom_layout, null);
        GridView constellation = constellationView.findViewById(R.id.constellation);
        constellationAdapter = new ConstellationAdapter(this, meatMainMaterial);
        constellation.setAdapter(constellationAdapter);
        TextView ok = constellationView.findViewById(R.id.ok);
        ok.setOnClickListener(v -> {
            binding.dropDownMenu.setTabText(constellationPosition == 0 ? headers[2] : meatMainMaterial.get(constellationPosition));
            binding.dropDownMenu.closeMenu();
        });

        constellation.setOnItemClickListener((parent, view, position, id) -> {
            constellationAdapter.setCheckItem(position);
            constellationPosition = position;
        });

        popupViews.add(constellationView);
    }
}