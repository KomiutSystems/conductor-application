package com.komiut.conductor.ui.qr;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.text.Layout;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.google.android.material.snackbar.Snackbar;
import com.komiut.conductor.MainActivityViewModel;
import com.komiut.conductor.R;
import com.komiut.conductor.databinding.FragmentQRPayBinding;
import com.komiut.conductor.model.OfflineUser;
import com.komiut.conductor.model.Receipt;
import com.komiut.conductor.retrofit.SaccoInfo;
import com.komiut.conductor.retrofit.WalletPaymentRequest;
import com.zcs.sdk.DriverManager;
import com.zcs.sdk.Printer;
import com.zcs.sdk.SdkResult;
import com.zcs.sdk.print.PrnStrFormat;
import com.zcs.sdk.print.PrnTextStyle;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class QRPayFragment extends Fragment implements QRCodeReaderView.OnQRCodeReadListener {

    private static final int PERMISSION_REQUEST_CODE = 200;
    FragmentQRPayBinding binding;
    NavController navController;
    private SharedPreferences sharedPreferences;
    private String acccess;
    SaccoInfo saccoInfo;
    DriverManager mDriverManager = DriverManager.getInstance();
    Printer mPrinter = mDriverManager.getPrinter();
    WalletPaymentRequest request=new WalletPaymentRequest();
    private MainActivityViewModel mainActivityViewModel;
    OfflineUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout fo
        // r this fragment
        binding= FragmentQRPayBinding.inflate(inflater,container,false);
        navController= Navigation.findNavController(container);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = requireActivity().getSharedPreferences(getString(R.string.preference_key), MODE_PRIVATE);
        acccess = sharedPreferences.getString(getString(R.string.access_token), "null");
        String sacco_motto = sharedPreferences.getString(getString(R.string.sacco_motto), "null");
        String sacco = sharedPreferences.getString(getString(R.string.sacco), "null");
        String customer_no = sharedPreferences.getString(getString(R.string.customer_care_no), "null");
        String logo = sharedPreferences.getString(getString(R.string.logo), "null");

        user=mainActivityViewModel.getUserMutableLiveData().getValue();
        saccoInfo=new SaccoInfo();
        saccoInfo.setCustomerCareNo(customer_no);
        saccoInfo.setLogo(logo);
        saccoInfo.setSacco(sacco);
        saccoInfo.setSaccoMotto(sacco_motto);

        if (checkPermission()) {
//            Toast.makeText(getContext(), "helloo", Toast.LENGTH_SHORT).show();
            binding.qrdecoderview.setOnQRCodeReadListener(this);

            // Use this function to enable/disable decoding
            binding.qrdecoderview.setQRDecodingEnabled(true);

            //Use this to set the image to display in the background_green.
            //qrCodeReaderView.setBackgroundResource(R.drawable.qr_background);

            // Use this function to change the autofocus interval (default is 5 secs)
            binding.qrdecoderview.setAutofocusInterval(1000L);

            // Use this function to enable/disable Torch
            binding.qrdecoderview.setTorchEnabled(true);

            // Use this function to set front camera preview
            binding.qrdecoderview.setPreviewCameraId(0);

            binding.qrdecoderview.setFrontCamera();

            // Use this function to set back camera preview
            binding.qrdecoderview.setBackCamera();

        } else {
//            Toast.makeText(getContext(), "nothing", Toast.LENGTH_SHORT).show();
            requestPermission();
        }
        binding.PrintReceipt.setOnClickListener(view1 -> {
            String amountString=binding.amount.getText().toString();
            if(amountString.isEmpty()||amountString==null){
                Snackbar.make(getView(),"Amount needed to continue deductions",Snackbar.LENGTH_LONG).show();
                return;
            }
            request.setAmount(amountString);
            if(request.getSerialNumber()==null)
            {
                binding.status.setTextColor(getResources().getColor(R.color.design_default_color_error));
                binding.status.setText("SCAN QR CODE TO CONTINUE");
                return;
            }
            prepareRetrofit(request,amountString);
        });
    }

    @Override
    public void onQRCodeRead(String serial, PointF[] points) {
       /*
       * Request details from the server for the specific qr code
       *
       * Then deduct the amount if it is there in the requested payload
       *
       * update the new balance after deduct then print receipt
       * */
        binding.status.setTextColor(getResources().getColor(R.color.green));
        binding.status.setText("QR READ SUCCESSFULLY");
        request.setSerialNumber(serial);

    }


    private void prepareRetrofit(WalletPaymentRequest walletPaymentRequest, String amountToDeduct)
    {

        Receipt receipt=new Receipt();
        receipt.setAmount(amountToDeduct);
        printReceipt(receipt);


       /* printReceipt()



        TokenInterceptor tokenInterceptor = new TokenInterceptor(acccess);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(tokenInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(COMMONRETROFIT.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofit.create(APIService.class).RequestUserWalletInformation(acccess,walletPaymentRequest).enqueue(new Callback<WalletPaymentResponse>() {
            @Override
            public void onResponse(Call<WalletPaymentResponse> call, Response<WalletPaymentResponse> response) {
                if(response.isSuccessful())
                {


                    int finalAccountBalance=Integer.getInteger(response.body().getBalance());

                    if(finalAccountBalance<amountToDeduct){
                        Snackbar.make(getView(),"Account Balance is Insufficient to complete the request",Snackbar.LENGTH_LONG).show();
                        return;
                    }
                    walletPaymentRequest.setAmount(String.valueOf(finalAccountBalance-amountToDeduct));
                    retrofit.create(APIService.class).UpdateUserWalletInformation(acccess,walletPaymentRequest).enqueue(new Callback<WalletUpdatePaymentResponse>() {
                        @Override
                        public void onResponse(Call<WalletUpdatePaymentResponse> call, Response<WalletUpdatePaymentResponse> response) {
                            if(response.isSuccessful())
                            {
                                Snackbar.make(getView(),"Amount deducted successfully",Snackbar.LENGTH_LONG).show();
                                return;
                            }
                            Snackbar.make(getView(),response.errorBody().toString(),Snackbar.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<WalletUpdatePaymentResponse> call, Throwable t) {
                            Snackbar.make(getView(),"Resource not found",Snackbar.LENGTH_LONG).show();
                        }
                    });
                    return;
                }
                Snackbar.make(getView(),"Something went wrong try again later.",Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<WalletPaymentResponse> call, Throwable t) {
                Snackbar.make(getView(),"Couldn't find the resource.",Snackbar.LENGTH_LONG).show();
            }
        });

*/
    }
    private void printReceipt(Receipt receipt) {

        int printStatus = mPrinter.getPrinterStatus();
        if (printStatus == SdkResult.SDK_PRN_STATUS_PAPEROUT) {
           Snackbar.make(getView(),"Paper Out",Snackbar.LENGTH_LONG).show();
            return;

        } else {


            Date date=new Date();
//            SimpleDateFormat newPattern=new SimpleDateFormat("yyyyMMddHHmm");
            SimpleDateFormat offlineDataDate=new SimpleDateFormat("dd/MM/yy");

            String paymentDate2=offlineDataDate.format(date);



            receipt.setUsername("John Doe");
            receipt.setPhonenumber("0703127101");
            receipt.setMatatu(user.getUser_plate());

            receipt.setDate(paymentDate2+" at "+ DateUtils.formatDateTime(getContext(),date.getTime(),DateUtils.FORMAT_SHOW_TIME));
            receipt.setStatus("PAID");
            receipt.setTime("heleo hello");
            receipt.setMatatu(user.getUser_plate());


            PrnStrFormat format = new PrnStrFormat();
            format.setTextSize(30);
            format.setAli(Layout.Alignment.ALIGN_CENTER);
            format.setStyle(PrnTextStyle.BOLD);

            mPrinter.setPrintAppendString(saccoInfo.getSacco().toUpperCase(),format);
            format.setTextSize(23);

            mPrinter.setPrintAppendString("CUSTOMER  CARE : 0"+saccoInfo.getCustomerCareNo(),format);
            mPrinter.setPrintAppendString("PASSENGER  TICKET",format);
            format.setTextSize(20);
            format.setStyle(PrnTextStyle.NORMAL);
            format.setAli(Layout.Alignment.ALIGN_NORMAL);
            mPrinter.setPrintAppendString(" ", format);
            mPrinter.setPrintAppendString(receipt.getMatatu(), format);
            mPrinter.setPrintAppendString("AMOUNT PAID : Ksh. " + receipt.getAmount(), format);

            mPrinter.setPrintAppendString("DATE PAID : " + receipt.getDate(), format);
            mPrinter.setPrintAppendString("CHANGE : " + "Ksh. 0", format);

            format.setAli(Layout.Alignment.ALIGN_CENTER);
            mPrinter.setPrintAppendString("*** "+saccoInfo.getSaccoMotto()+" *** ",format);
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
            request.setSerialNumber(null);
            binding.status.setTextColor(getResources().getColor(R.color.design_default_color_error));
            binding.status.setText("SCAN QR CODE TO CONTINUE");
        }
    }



    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }
    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},PERMISSION_REQUEST_CODE);
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }
}