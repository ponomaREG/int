package com.example.umiter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class SettingsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).binding.toolbar.setTitle("Настройки пользователя");
        //((MainActivity) getActivity()).hideOption(R.id.addTerr);
        setHasOptionsMenu(true);
        //Menu menu = null;
        //menu.findItem(R.id.addTerr).setVisible(false).setEnabled(false);

    }

    /*@Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //((MainActivity) getActivity()).hideOption(R.id.addTerr);
        MenuItem item = menu.findItem(R.id.addTerr);
        item.setVisible(false);
    }*/
}