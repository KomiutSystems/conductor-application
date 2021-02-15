package com.komiut.conductor.ui.fingerprint;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.komiut.conductor.R;
import com.komiut.conductor.databinding.FragmentFingerprintVerificationBinding;
import com.komiut.conductor.ui.login.LoginFragmentDirections;
import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.zcs.sdk.DriverManager;
import com.zcs.sdk.Sys;
import com.zcs.sdk.fingerprint.FingerprintListener;
import com.zcs.sdk.fingerprint.FingerprintManager;

public class FingerprintVerificationFragment extends Fragment implements FingerprintListener {

   FragmentFingerprintVerificationBinding binding;
   FingerprintViewModel viewModel;
    DriverManager mDriverManager = DriverManager.getInstance();
    Sys mSys = mDriverManager.getBaseSysDevice();
    NavController navController;
    FingerprintManager mFingerprintManager = mDriverManager.getFingerprintManager();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentFingerprintVerificationBinding.inflate(inflater,container,false);
        viewModel= new ViewModelProvider(this).get(FingerprintViewModel.class);

        navController= Navigation.findNavController(container);
        return binding.getRoot();
    }


    @Override
    public void onStart() {
        super.onStart();
        mFingerprintManager.addFignerprintListener(this);
        mFingerprintManager.init();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.button4.setOnClickListener(view1 -> mFingerprintManager.authenticate(0,5000));


        /*viewModel.getOfflineUsers().observe(getViewLifecycleOwner(),offlineUsers -> {
            if(offlineUsers.size()==0)
            {
                Toast.makeText(getContext(), "no fingerprints found", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(getContext(), "fingerprints found", Toast.LENGTH_SHORT).show();
        });*/

    }

    @Override
    public void onEnrollmentProgress(int i, int i1, int i2) {

    }

    @Override
    public void onAuthenticationFailed(int i) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Snackbar.make(requireActivity().getCurrentFocus(),"Verification Failed",Snackbar.LENGTH_LONG).show();

                Toast.makeText(getContext(), "Verification Failed", Toast.LENGTH_SHORT).show();
                navController.navigateUp();
            }
        });
    }

    @Override
    public void onAuthenticationSucceeded(int i, Object o) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(getContext(), "Verification Succeeded", Toast.LENGTH_SHORT).show();
                navController.navigate(FingerprintVerificationFragmentDirections.actionFingerprintVerificationFragmentToMainActivity2());
                requireActivity().finish();
            }
        });
    }

    @Override
    public void onGetImageComplete(int i, byte[] bytes) {

    }

    @Override
    public void onGetImageFeature(int i, byte[] bytes) {

    }

    @Override
    public void onGetImageISOFeature(int i, byte[] bytes) {

    }
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return MoveAnimation.create(MoveAnimation.UP, enter, 700);
        } else {
            return MoveAnimation.create(MoveAnimation.RIGHT, enter, 700);
        }
    }
}