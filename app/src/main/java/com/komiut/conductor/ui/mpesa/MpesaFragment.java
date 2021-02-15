package com.komiut.conductor.ui.mpesa;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Environment;
import android.telephony.SmsManager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.komiut.conductor.IncomingSms;
import com.komiut.conductor.MainActivityViewModel;
import com.komiut.conductor.R;
import com.komiut.conductor.databinding.FragmentMpesaBinding;
import com.komiut.conductor.model.MpesaMessage;
import com.komiut.conductor.model.OfflineUser;
import com.komiut.conductor.model.Receipt;
import com.komiut.conductor.retrofit.SaccoInfo;
import com.komiut.conductor.retrofit.UserIn;
import com.komiut.conductor.ui.dashboard.DashboardViewModel;
import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.zcs.sdk.DriverManager;
import com.zcs.sdk.Printer;
import com.zcs.sdk.SdkResult;
import com.zcs.sdk.print.PrnStrFormat;
import com.zcs.sdk.print.PrnTextFont;
import com.zcs.sdk.print.PrnTextStyle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MpesaFragment extends Fragment implements MpesaAdapter.UpdateMpesaMessage {



    FragmentMpesaBinding binding;
    private ArrayList<String> arlist;
    //    private SimpleCursorAdapter adapter;
    private final static int REQ = 123;
    List<MpesaMessage> receiptArrayList = new ArrayList<>();
    MpesaViewModel mpesaViewModel;



    private MainActivityViewModel mainActivityViewModel;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMpesaBinding.inflate(inflater, container, false);

        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        mpesaViewModel=new ViewModelProvider(requireActivity()).get(MpesaViewModel.class);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE);
        String sacco_motto = sharedPreferences.getString(getString(R.string.sacco_motto), "null");
        String sacco = sharedPreferences.getString(getString(R.string.sacco), "null");
        String customer_no = sharedPreferences.getString(getString(R.string.customer_care_no), "null");
        String logo = sharedPreferences.getString(getString(R.string.logo), "null");

        SaccoInfo saccoInfo=new SaccoInfo();
        saccoInfo.setCustomerCareNo(customer_no);
        saccoInfo.setLogo(logo);
        saccoInfo.setSacco(sacco);
        saccoInfo.setSaccoMotto(sacco_motto);
        OfflineUser userDetails=mainActivityViewModel.getUserMutableLiveData().getValue();


        SimpleDateFormat newPattern=new SimpleDateFormat("dd/MM/yy");

        mpesaViewModel.getMpesaMessagesByDate(newPattern.format(new Date())).observe(getViewLifecycleOwner(),mpesaMessages1 -> {

            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MpesaAdapter mpesaAdapter=new MpesaAdapter(mpesaMessages1,userDetails.getUser_plate(),saccoInfo);
                    binding.mpesaRecycler.setHasFixedSize(true);
                    binding.mpesaRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.mpesaRecycler.setAdapter(mpesaAdapter);
                    mpesaAdapter.notifyDataSetChanged();
                }
            });
            if(mpesaMessages1==null)return;


        });


    }


    @Override
    public void SendMessageToRepository(MpesaMessage mpesaMessage) {

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