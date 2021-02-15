package com.komiut.conductor.ui.fingerprint;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.komiut.conductor.R;
import com.komiut.conductor.databinding.FragmentEnrollFingerprintBinding;
import com.komiut.conductor.model.OfflineUser;
import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.zcs.sdk.DriverManager;
import com.zcs.sdk.Sys;
import com.zcs.sdk.fingerprint.FingerprintListener;
import com.zcs.sdk.fingerprint.FingerprintManager;

import java.util.Arrays;


public class EnrollFingerprintFragment extends Fragment implements FingerprintListener {

    FragmentEnrollFingerprintBinding binding;
    DriverManager mDriverManager = DriverManager.getInstance();
    Sys mSys = mDriverManager.getBaseSysDevice();
    NavController navController;
    FingerprintManager mFingerprintManager = mDriverManager.getFingerprintManager();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        navController= Navigation.findNavController(container);
        binding=FragmentEnrollFingerprintBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        OfflineUser user=EnrollFingerprintFragmentArgs.fromBundle(getArguments()).getOfflineUser();

//        Toast.makeText(getContext(), user.getUser_email(), Toast.LENGTH_SHORT).show();
       binding.button3.setOnClickListener(view1 -> {
           getFingerprint();
       });

       binding.button34.setOnClickListener(view1 -> {
           mFingerprintManager.authenticate(0,5000);
       });



    }

    @Override
    public void onStart() {
        super.onStart();
        mFingerprintManager.addFignerprintListener(this);
        mFingerprintManager.init();
    }

    private void getFingerprint() {

        mFingerprintManager.enrollment(5000);

    }

    @Override
    public void onEnrollmentProgress(int fingerid, int remaining, int reason) {

        if(reason==0&&remaining==0)
        {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    Toast.makeText(getContext(), "enrollment success", Toast.LENGTH_SHORT).show();

                    Snackbar.make(requireActivity().getCurrentFocus(),"Fingerprint successfully saved",Snackbar.LENGTH_LONG).show();
                    navController.navigateUp();
                }
            });
        }

    }

    @Override
    public void onAuthenticationFailed(int i) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "auth faileds", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAuthenticationSucceeded(int i, Object o) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "auth success", Toast.LENGTH_SHORT).show();
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
    public void onGetImageISOFeature(int result, byte[] bytes) {

        if(result==0)
        {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), "fingerprint detected"+ Arrays.toString(bytes), Toast.LENGTH_SHORT).show();
                }
            });
        }


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