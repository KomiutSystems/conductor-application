package com.komiut.conductor.ui.cash;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.Layout;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.komiut.conductor.MainActivityViewModel;
import com.komiut.conductor.R;
import com.komiut.conductor.databinding.FragmentCashBinding;
import com.komiut.conductor.model.CashTransaction;
import com.komiut.conductor.model.OfflineUser;
import com.komiut.conductor.model.Receipt;
import com.komiut.conductor.model.SubRoute;
import com.komiut.conductor.retrofit.SaccoInfo;
import com.komiut.conductor.retrofit.Stage;
import com.komiut.conductor.retrofit.SubRoutesResponse;
import com.komiut.conductor.retrofit.UserIn;
import com.komiut.conductor.ui.dashboard.DashboardViewModel;
import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.zcs.sdk.DriverManager;
import com.zcs.sdk.Printer;
import com.zcs.sdk.SdkResult;
import com.zcs.sdk.print.PrnStrFormat;
import com.zcs.sdk.print.PrnTextFont;
import com.zcs.sdk.print.PrnTextStyle;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CashFragment extends Fragment {

    private static final String[] stages = new String[]{
            "Afghanistan", "Albania", "Algeria", "Andorra", "Angola"
    };


    private CashViewModel cashViewModel;
    FragmentCashBinding binding;
    DriverManager mDriverManager = DriverManager.getInstance();
    Printer mPrinter = mDriverManager.getPrinter();
    private MainActivityViewModel mainActivityViewModel;
    private SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cashViewModel = new
                ViewModelProvider(getActivity()).get(CashViewModel.class);

        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        binding = FragmentCashBinding.inflate(inflater, container, false);


        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE);
        String sacco_motto = sharedPreferences.getString(getString(R.string.sacco_motto), "null");
        String sacco = sharedPreferences.getString(getString(R.string.sacco), "null");
        String customer_no = sharedPreferences.getString(getString(R.string.customer_care_no), "null");
        String logo = sharedPreferences.getString(getString(R.string.logo), "null");


        SaccoInfo saccoInfo = new SaccoInfo();
        saccoInfo.setCustomerCareNo(customer_no);
        saccoInfo.setLogo(logo);
        saccoInfo.setSacco(sacco);
        saccoInfo.setSaccoMotto(sacco_motto);
        cashViewModel.getSubroutes().observe(getViewLifecycleOwner(), subRoutesResponses ->

        {

            List<String> subRoutes = new ArrayList<>();


            for (SubRoutesResponse sub :
                    subRoutesResponses) {
                subRoutes.add(sub.getSubstage());
            }
            ArrayAdapter routesAdapter = new ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, subRoutes);
            binding.from.setAdapter(routesAdapter);
            binding.to.setAdapter(routesAdapter);


        });

       /* cashViewModel.getStage().observe(getViewLifecycleOwner(), stages ->

        {

            List<String> stageNames = new ArrayList<>();


            for (Stage stageName : stages) {
                stageNames.add(stageName.getStage());
            }
            ArrayAdapter stageAdapter = new ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, stageNames);
            binding.from.setAdapter(stageAdapter);
            binding.to.setAdapter(stageAdapter);


        });*/


        binding.testwwe.setOnClickListener(view1 -> {

            cashViewModel.getCashTransaction().observe(getViewLifecycleOwner(), cashTransactions -> {
                String transactions = "";
                for (CashTransaction cashTransaction :
                        cashTransactions) {
                    transactions += "\n" + cashTransaction.getPassname();
                }

                binding.textView9.setText(transactions);
            });
        });


        OfflineUser userDetails = mainActivityViewModel.getUserMutableLiveData().getValue();

        List<String> amounts = new ArrayList<>();

        amounts.add("50");
        amounts.add("100");
        amounts.add("200");
        amounts.add("500");
        amounts.add("1000");
        ArrayAdapter adapter = new ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, amounts);
        binding.autoComplete.setAdapter(adapter);


        binding.button.setOnClickListener(view1 -> {

            String firstname = binding.firstname.getEditText().getText().toString();
            String lastname = binding.lastname.getEditText().getText().toString();
            String from = binding.from.getText().toString();
            String to = binding.to.getText().toString();
            String ticketNo = receiptNumber();

            Log.d("ReceiptNo", ticketNo);


            String baseFare = binding.basefare.getEditText().getText().toString();
            String fare = binding.amountt.getEditText().getText().toString();

            if (baseFare.isEmpty() || fare.isEmpty()) {
                Snackbar.make(requireActivity().getCurrentFocus(), "All fields needed to proceed", Snackbar.LENGTH_LONG).show();
                return;
            }
            if (firstname.isEmpty()) {
                firstname = "John";
            }
            if (lastname.isEmpty()) {
                lastname = "Doe";
            }
            if (from.isEmpty()) {
                from = "Boarding Terminal not provided";
            }
            if (to.isEmpty()) {
                to = "Destination not given";
            }


            int bFare = Integer.parseInt(baseFare);
            int initialAmount = Integer.parseInt(fare);


            if (bFare > initialAmount) {
                Snackbar.make(requireActivity().getCurrentFocus(), "Amount paid should be higher than base fare", Snackbar.LENGTH_LONG).show();
                return;
            }


            CashTransaction cashTransaction = new CashTransaction();

            Date date = new Date();


            int change = initialAmount - bFare;
            cashTransaction.setAmount(baseFare);
            Log.i("ReceiptNo", ticketNo);
            cashTransaction.setAmtGiven(String.valueOf(initialAmount));
            cashTransaction.setLuggage("0");
            cashTransaction.setNopass("1");
            cashTransaction.setPassname(firstname + " " + lastname);
            cashTransaction.setRegno(userDetails.getUser_plate());
            cashTransaction.setPassphone(userDetails.getUser_phone());
            cashTransaction.setSelectedDepart(from);
            cashTransaction.setStringChange(String.valueOf(change));
            cashTransaction.setSelectedDest(to);
            cashTransaction.setUniqueID(userDetails.getUser_plate() + date.toString());
            cashTransaction.setCashId(ticketNo);
            Log.i("ReceiptNo", ticketNo);
//          202012141150  yyyymmddHHMM
            cashTransaction.setStatus(false);

            SimpleDateFormat newPattern = new SimpleDateFormat("yyyyMMddHHmm");
            SimpleDateFormat offlineDataDate = new SimpleDateFormat("dd/MM/yy");
            cashTransaction.setDate(newPattern.format(date));
            cashTransaction.setOfflineDataAccessDate(offlineDataDate.format(date));
            cashTransaction.setStringTotal(String.valueOf(bFare));
            cashViewModel.saveCashTransaction(cashTransaction).observe(getViewLifecycleOwner(), aBoolean -> Snackbar.make(requireActivity().getCurrentFocus(), "Save successfull", Snackbar.LENGTH_LONG).show());

            Receipt receipt = new Receipt();
            receipt.setStatus("Pending Printing");
            receipt.setUsername(binding.firstname.getEditText().getText().toString());
            receipt.setPhonenumber(binding.lastname.getEditText().getText().toString());
            receipt.setMatatu(userDetails.getUser_plate());
            receipt.setAmount(binding.amountt.getEditText().getText().toString());

            receipt.setDate(cashTransaction.getOfflineDataAccessDate() + " at " + DateUtils.formatDateTime(getContext(), date.getTime(), DateUtils.FORMAT_SHOW_TIME));
            printReceipt(mPrinter, receipt, change, saccoInfo, ticketNo);
            binding.lastname.getEditText().setText("");
            binding.firstname.getEditText().setText("");
            binding.amountt.getEditText().setText("");
            cashViewModel.resetCounter(0);

        });



               /* String test="";

                for (SubRoute sub :
                        subRoutesResponses) {
                    test+="\n"+sub.getSubRoutename();
                }*/
//        Toast.makeText(getContext(), String.valueOf(subRoutesResponses.size()), Toast.LENGTH_SHORT).show();

    }


    private void printReceipt(Printer mPrinter, Receipt receipt, int change, SaccoInfo saccoInfo, String ticketNo) {

        String plate = sharedPreferences.getString(getString(R.string.user_plate), "null");
        String sacco = sharedPreferences.getString(getString(R.string.sacco), "null");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String saa = String.valueOf(timestamp.getTime());

        int printStatus = mPrinter.getPrinterStatus();
        if (printStatus == SdkResult.SDK_PRN_STATUS_PAPEROUT) {
            Snackbar.make(requireActivity().getCurrentFocus(), "Load paper to continue", Snackbar.LENGTH_LONG).show();
            return;
        } else {


            PrnStrFormat format = new PrnStrFormat();
            format.setTextSize(30);
            format.setAli(Layout.Alignment.ALIGN_CENTER);
            format.setStyle(PrnTextStyle.BOLD);

            mPrinter.setPrintAppendString(saccoInfo.getSacco().toUpperCase(), format);
            format.setTextSize(23);

            mPrinter.setPrintAppendString("CUSTOMER  CARE : 0" + saccoInfo.getCustomerCareNo(), format);
            mPrinter.setPrintAppendString("PASSENGER  TICKET", format);
//            mPrinter.setPrintAppendString("Ticket No : " + receiptNumber().substring(1, 10) + saccoInfo.getSacco().charAt(0) + saccoInfo.getSacco().charAt(saccoInfo.getSacco().toUpperCase().length()-1)  , format);
            mPrinter.setPrintAppendString("Ticket No : " + ticketNo.substring(1, 6) + sacco.charAt(0) + sacco.charAt(sacco.length() - 1) + ticketNo.substring(13, 20), format);
            Log.i("ReceiptNo", ticketNo);
            format.setTextSize(20);
            format.setStyle(PrnTextStyle.NORMAL);
            format.setAli(Layout.Alignment.ALIGN_NORMAL);
            mPrinter.setPrintAppendString(" ", format);
            mPrinter.setPrintAppendString(receipt.getMatatu(), format);
            mPrinter.setPrintAppendString("AMOUNT PAID : Ksh. " + receipt.getAmount(), format);

            mPrinter.setPrintAppendString("DATE PAID : " + receipt.getDate(), format);
            mPrinter.setPrintAppendString("CHANGE : " + change, format);
//            receiptNumber().toUpperCase().substring(1, 10) + saccoInfo.getSacco().charAt(0) + saccoInfo.getSacco().charAt(saccoInfo.getSacco().length()-1)
            format.setAli(Layout.Alignment.ALIGN_CENTER);
            mPrinter.setPrintAppendString("*** " + saccoInfo.getSaccoMotto() + " *** ", format);
            format.setAli(Layout.Alignment.ALIGN_CENTER);
            mPrinter.setPrintAppendString(" ", format);
//            Bitmap image= BitmapFactory.decodeResource(getContext().getResources(), R.drawable.loogo);
//            mPrinter.setPrintAppendBitmap(image, Layout.Alignment.ALIGN_CENTER);
            format.setTextSize(22);
            mPrinter.setPrintAppendString("POWERED BY KOMIUT.CO.KE", format);
            mPrinter.setPrintAppendString(" IN PARTNERSHIP WITH", format);
            mPrinter.setPrintAppendString("NCBA BANK ", format);
            mPrinter.setPrintAppendString(" ", format);
            mPrinter.setPrintAppendString(" ", format);
            mPrinter.setPrintAppendString(" ", format);

            printStatus = mPrinter.setPrintStart();
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

    public String receiptNumber() {

        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        String plate = sharedPreferences.getString(getString(R.string.user_plate), "null");
        String sacco = sharedPreferences.getString(getString(R.string.sacco), "null");
        String saa = String.valueOf(currentTimestamp.getTime());

        String ticketNumbers = plate.replaceAll("\\s+", "") + saa + sacco;

        return ticketNumbers.substring(1).toUpperCase();

    }

}