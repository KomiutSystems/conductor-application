package com.komiut.conductor.ui.qr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.komiut.conductor.databinding.FragmentQRBinding;

public class QRFragment extends Fragment {

    FragmentQRBinding binding;
    NavController navController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentQRBinding.inflate(inflater,container,false);
        navController= Navigation.findNavController(container);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.pay.setOnClickListener(view1 -> navController.navigate(QRFragmentDirections.actionQRFragmentToQRPayFragment()));
        binding.topUp.setOnClickListener(view1 -> navController.navigate(QRFragmentDirections.actionQRFragmentToQRTopUPFragment()));
    }
}