package com.komiut.conductor.ui.dashboard;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.komiut.conductor.IncomingSms;
import com.komiut.conductor.MainActivityViewModel;
import com.komiut.conductor.R;
import com.komiut.conductor.databinding.FragmentDashboardBinding;
import com.komiut.conductor.model.CashTransaction;
import com.komiut.conductor.model.MpesaHomer;
import com.komiut.conductor.model.MpesaMessage;
import com.komiut.conductor.model.Trips;
import com.komiut.conductor.retrofit.APIService;
import com.komiut.conductor.retrofit.ApiUtils;
import com.komiut.conductor.retrofit.LoginRequest;
import com.komiut.conductor.retrofit.TokenInterceptor;
import com.komiut.conductor.retrofit.TransactionResponse;
import com.komiut.conductor.retrofit.UserIn;
import com.komiut.conductor.sessionmanager.SessionManager;
import com.komiut.conductor.sessionmanager.Sessionmangementsharedpref;
import com.labo.kaji.fragmentanimations.CubeAnimation;
import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.zcs.sdk.DriverManager;
import com.zcs.sdk.SdkData;
import com.zcs.sdk.SdkResult;
import com.zcs.sdk.Sys;
import com.zcs.sdk.card.CardInfoEntity;
import com.zcs.sdk.card.CardReaderManager;
import com.zcs.sdk.card.CardReaderTypeEnum;
import com.zcs.sdk.card.RfCard;
import com.zcs.sdk.listener.OnSearchCardListener;
import com.zcs.sdk.util.StringUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardFragment extends Fragment {

    FragmentDashboardBinding binding;

    private DashboardViewModel dashboardViewModel;
    private MainActivityViewModel mainActivityViewModel;
    private SharedPreferences sharedPreferences;

    Trips trips;
    private SessionManager sessionManager;


    static int total = 0;
    DriverManager mDriverManager = DriverManager.getInstance();
    Sys mSys = mDriverManager.getBaseSysDevice();
    RfCard mRfCard = mDriverManager.getCardReadManager().getRFCard();
    CardReaderManager mCardReadManager = mDriverManager.getCardReadManager();
    CardReaderTypeEnum mCardType = CardReaderTypeEnum.RF_CARD;
    byte mRfCardType = SdkData.RF_TYPE_A;
    boolean isM1 = false;
    ArrayList<MpesaHomer> mpesaMessages = new ArrayList<>();

    NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
        trips = new Trips();
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        navController = Navigation.findNavController(container);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchM1();

        SimpleDateFormat offlineDataDate = new SimpleDateFormat("dd/MM/yy");

        String dateOfTransactions = offlineDataDate.format(new Date());

        dashboardViewModel.getMpesaPaymnetsForToday(dateOfTransactions).observe(getViewLifecycleOwner(), mpesaMessages1 -> {
            int totalToday = 0;
            if (mpesaMessages1 == null) return;

            for (MpesaMessage mpesa :
                    mpesaMessages1) {
                String beginAMount = mpesa.getAmount().substring(3);
                String endAmount = beginAMount.substring(0, beginAMount.length() - 3);
                totalToday += Integer.parseInt(endAmount);
            }

            binding.mpesa.setText("Kshs. " + totalToday);

        });

        dashboardViewModel.getCashPaymnetsByDate(dateOfTransactions).observe(getViewLifecycleOwner(), cashTransactions -> {
            int totalToday = 0;
            if (cashTransactions == null) return;

            String test = "";
            for (CashTransaction cash :
                    cashTransactions) {
                totalToday += Integer.parseInt(cash.getAmount());
//                test+=cash.getDate()+"\n";
            }

//            Toast.makeText(getContext(), test, Toast.LENGTH_SHORT).show();
            total += totalToday;
            binding.cash.setText("Kshs. " + totalToday);

        });


        sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE);
        String user_email = sharedPreferences.getString(getString(R.string.user_email), "null");
        String user_name = sharedPreferences.getString(getString(R.string.user_name), "null");
        String user_phone = sharedPreferences.getString(getString(R.string.user_phone), "null");
        String user_plate = sharedPreferences.getString(getString(R.string.user_plate), "null");


        binding.textDashboard.setText(user_name);
        binding.plate.setText(user_plate);
        binding.email.setText(user_email);
        binding.phone.setText(user_phone);

        binding.register.setOnClickListener(view1 -> {

            navController.navigate(DashboardFragmentDirections.actionNavigationDashboardToEnrollFingerprintFragment2());
        });

        binding.trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startTrip();
            }
        });

        binding.button5.setOnClickListener(view1 -> {

            readPage();
        });

    }


    void readPage() {

        mCardReadManager.cancelSearchCard();
        mCardReadManager.searchCard(CardReaderTypeEnum.RF_CARD, 5000, listener);


        /*
        StringBuilder sb = new StringBuilder();
        int ret;
        do {
            byte startPage = 0x00;
            byte endPage = 0x10;
            byte[] outData = new byte[(endPage - startPage + 1) * 4];
            ret = mRfCard.ntagFastRead(startPage, endPage, outData);
            if (ret != SdkResult.SDK_OK) {
                break;
            }
            sb.append(StringUtils.convertBytesToHex(outData));
            startPage = 0x11;
            endPage = 0x2c;
            outData = new byte[(endPage - startPage + 1) * 4];
            ret = mRfCard.ntagFastRead(startPage, endPage, outData);
            if (ret != SdkResult.SDK_OK) {
                break;
            }
            sb.append(StringUtils.convertBytesToHex(outData));
//            Toast.makeText(getContext(),"read return = " + ret + "  data = " + sb,Toast.LENGTH_LONG).show();
        } while (false);


        Toast.makeText(getContext(),"read return = " + ret + "  data = " + String.valueOf(sb.capacity()),Toast.LENGTH_LONG).show();*/
    }

    void searchM1() {
        isM1 = true;
        mCardType = CardReaderTypeEnum.RF_CARD;
        mRfCardType = SdkData.RF_TYPE_A;

    }

    OnSearchCardListener listener = new OnSearchCardListener() {
        @Override
        public void onCardInfo(CardInfoEntity cardInfoEntity) {
            CardReaderTypeEnum cardType = cardInfoEntity.getCardExistslot();
            switch (cardType) {
                case RF_CARD:
                    byte rfCardType = cardInfoEntity.getRfCardType();
                    if (isM1) {
                        Toast.makeText(getContext(), "is m1", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), "is deiffe", Toast.LENGTH_SHORT).show();
                    }
            }
        }

        @Override
        public void onError(int i) {

        }

        @Override
        public void onNoCard(CardReaderTypeEnum cardReaderTypeEnum, boolean b) {

        }
    };

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return MoveAnimation.create(MoveAnimation.UP, enter, 700);
        } else {
            return MoveAnimation.create(MoveAnimation.RIGHT, enter, 700);
        }
    }

    public void startTrip() {

        new AlertDialog.Builder(getContext())
                .setMessage("Are you sure you want to Create new Trip?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Random rand = new Random();

                        int salt = rand.nextInt(999);

                        DateFormat format = new SimpleDateFormat("yyyyMMdd");
                        Date date = new Date();
                        String dot = format.format(date);

                        Calendar cal = Calendar.getInstance();
                        cal.getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
                        String time = sdf.format(cal.getTime());

                        String timestamp = dot + time + Integer.toString(salt);

                        trips.id = timestamp;
                        trips.date = dateBuilder();

                        Sessionmangementsharedpref sessionmangementsharedpref = new Sessionmangementsharedpref(getContext());
                        String regno = sessionmangementsharedpref.getPlate();

                        trips.plate = regno;
                        trips.capacity = 0;

                       // sessionManager.logoutUser();
                        sessionManager.createLoginSession(trips);
                        Toast.makeText(getContext(), "New Trip Created Successfully, Add Passengers now!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();


    }

    public static String timeBuilder() {

        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime());
    }

    public static String dateBuilder() {
        /*DateFormat format = new SimpleDateFormat("dd/MM/yyyy ");
        Date date = new Date();
        String dot = format.format(date);
        return dot;*/

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dot = format.format(date);
        return dot;


    }

}