package com.komiut.conductor.ui.card;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.text.Layout;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.komiut.conductor.MainActivityViewModel;
import com.komiut.conductor.R;
import com.komiut.conductor.databinding.FragmentCardNewBinding;
import com.komiut.conductor.model.OfflineUser;
import com.komiut.conductor.model.Receipt;
import com.komiut.conductor.retrofit.SaccoInfo;
import com.komiut.conductor.retrofit.WalletPaymentRequest;
import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.zcs.sdk.DriverManager;
import com.zcs.sdk.Printer;
import com.zcs.sdk.SdkData;
import com.zcs.sdk.SdkResult;
import com.zcs.sdk.card.CardInfoEntity;
import com.zcs.sdk.card.CardReaderManager;
import com.zcs.sdk.card.CardReaderTypeEnum;
import com.zcs.sdk.card.CardSlotNoEnum;
import com.zcs.sdk.card.ICCard;
import com.zcs.sdk.card.IDCard;
import com.zcs.sdk.card.MagCard;
import com.zcs.sdk.card.RfCard;
import com.zcs.sdk.card.SLE4428Card;
import com.zcs.sdk.card.SLE4442Card;
import com.zcs.sdk.listener.OnSearchCardListener;
import com.zcs.sdk.print.PrnStrFormat;
import com.zcs.sdk.print.PrnTextStyle;
import com.zcs.sdk.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class CardFragment extends Fragment {

    DriverManager mDriverManager = DriverManager.getInstance();
    CardReaderManager mCardReadManager = mDriverManager.getCardReadManager();
    ICCard mICCard = mCardReadManager.getICCard();
    MagCard mMagCard = mCardReadManager.getMAGCard();
    Printer mPrinter = mDriverManager.getPrinter();
    //use the following for the contactless card reading
    RfCard mRfCard = mCardReadManager.getRFCard();
    SLE4428Card mSLE4428Card = mCardReadManager.getSLE4428Card();
    SLE4442Card mSLE4442Card = mCardReadManager.getSLE4442Card();
    IDCard idCard = mCardReadManager.getIDCard();
    OfflineUser user;
    private MainActivityViewModel mainActivityViewModel;
    private SharedPreferences sharedPreferences;
    private String acccess;
    SaccoInfo saccoInfo;

    WalletPaymentRequest request=new WalletPaymentRequest();
    //icCard variables below

    private static final int MSG_CARD_M1 = 2004;

    public static final byte[] APDU_SEND_IC = {0x00, (byte) 0xA4, 0x04, 0x00, 0x0E,
            0x31, 0x50, 0x41, 0x59, 0x2E, 0x53, 0x59, 0x53, 0x2E, 0x44, 0x44, 0x46, 0x30,
            0x31, 0X00};
    public static final byte[] APDU_SEND_RF = {0x00, (byte) 0xA4, 0x04, 0x00, 0x0E,
            0x32, 0x50, 0x41, 0x59, 0x2E, 0x53, 0x59, 0x53, 0x2E, 0x44, 0x44, 0x46, 0x30,
            0x31, 0x00};
    public static final byte[] APDU_SEND_RANDOM = {0x00, (byte) 0x84, 0x00, 0x00,
            0x08};
    public static final byte[] APDU_SEND_FELICA = {0x10, 0x06, 0x01, 0x2E, 0x45,
            0x76, (byte) 0xBA, (byte) 0xC5, 0x45, 0x2B, 0x01, 0x09, 0x00, 0x01, (byte) 0x80,
            0x00};


    boolean isM1 = false;
    boolean isMfPlus = false;

    FragmentCardNewBinding binding;
    String keyM1 = "FFFFFFFFFFFF";
    byte keyType = 0x00; // 0x00 typeA, 0x01 typeB

    String keyMfPlus = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
    byte[] addressMfPlus = {0x40, 0x00};
    boolean hasSetMf = false;

    byte mRfCardType = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCardNewBinding.inflate(inflater, container, false);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        return binding.getRoot();
    }


    OnSearchCardListener mListener = new OnSearchCardListener() {
        @Override
        public void onCardInfo(CardInfoEntity cardInfoEntity) {
            CardReaderTypeEnum cardType = cardInfoEntity.getCardExistslot();

            switch (cardType) {
                case RF_CARD:
// only can get SdkData.RF_TYPE_A / SdkData.RF_TYPE_B /
//                        SdkData.RF_TYPE_FELICA / SdkData.RF_TYPE_MEMORY_A / SdkData.RF_TYPE_MEMORY_B

                    byte rfCardType = cardInfoEntity.getRfCardType();
                    if (rfCardType == SdkData.RF_TYPE_MEMORY_A) {



                        getActivity().runOnUiThread(() ->{
//                            Toast.makeText(getActivity(), cardInfoEntity.getRFuid().toString(), Toast.LENGTH_SHORT).show();
                            Receipt receipt=new Receipt();
                            receipt.setAmount(binding.amount2.getText().toString());
                            prepareRetrofit(request,cardInfoEntity.getRFuid().toString());
//                            printReceipt(receipt);
                                    return;

                        });
//                                getActivity().runOnUiThread(() -> {
//                                    Toast.makeText(getActivity(),StringUtils.convertByteToHex(rfCardType), Toast.LENGTH_SHORT).show();
//                                });
//                            writeM1();
//                            readM1Card();


                    }
                        /*getActivity().runOnUiThread(() -> {
                            binding.txtCard.setText(StringUtils.convertByteToHex(cardInfoEntity.getCardSlot()) +
                                    " \n    " + StringUtils.convertBytesToHex(cardInfoEntity.getRFuid()) +
                                    " \n " + cardInfoEntity.getCardNo() +
                                    " \n  " + StringUtils.convertByteToHex(cardInfoEntity.getRfCardType())
                                    +
                                    " \n  " + (cardInfoEntity.getServiceCode())
                                    +
                                    " \n  " + StringUtils.convertByteToHex(cardInfoEntity.getCardSlot())
                                    +
                                    " \n  " + (cardInfoEntity.getCardNo())

                            );
                            if (rfCardType == SdkData.RF_TYPE_MEMORY_A) {

//                                getActivity().runOnUiThread(() -> {
//                                    Toast.makeText(getActivity(),StringUtils.convertByteToHex(rfCardType), Toast.LENGTH_SHORT).show();
//                                });
                                writeM1();
//                                readM1Card();

                                return;
                            }

                        });*/
//                        Log.e("TG", "rfCardType: " + rfCardType+cardInfoEntity.getCardNo());
                       /* if (rfCardType==SdkData.RF_TYPE_A)
                        {
//                            readM1Card();
                            getActivity().runOnUiThread(() -> {
                                Toast.makeText(getActivity(),"is card one", Toast.LENGTH_SHORT).show();
                            });
                            return;
                        }
                        else if (rfCardType==SdkData.RF_TYPE_B)
                        {
//                            readMFPlusCard();
                            getActivity().runOnUiThread(() -> {
                                Toast.makeText(getActivity(),"is card twog", Toast.LENGTH_SHORT).show();
                            });
                        }
                        else if (rfCardType == SdkData.RF_TYPE_FELICA)
                            { // felica card
//                                readFelica();
                                getActivity().runOnUiThread(() -> {
                                    Toast.makeText(getActivity(),"is felica", Toast.LENGTH_SHORT).show();
                                });
                            }*/

                    break;
                case MAG_CARD:
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getActivity(), "is mag", Toast.LENGTH_SHORT).show();
                    });
//                        readMagCard();
                    break;
                case IC_CARD:
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getActivity(), "IC CARED", Toast.LENGTH_SHORT).show();
                    });
//                        readICCard(CardSlotNoEnum.SDK_ICC_USERCARD);
                    break;
                case PSIM1:
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getActivity(), "PSIM1", Toast.LENGTH_SHORT).show();
                    });
//                        readICCard(CardSlotNoEnum.SDK_ICC_SAM1);
                    break;
                case PSIM2:
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getActivity(), "PSIM2", Toast.LENGTH_SHORT).show();
                    });
//                        readICCard(CardSlotNoEnum.SDK_ICC_SAM2);
                    break;
            }
        }

        @Override
        public void onError(int i) {
            isM1 = false;
            isMfPlus = false;
            Snackbar.make(getActivity().getCurrentFocus(), "No data found", Snackbar.LENGTH_LONG).show();
        }

        @Override
        public void onNoCard(CardReaderTypeEnum cardReaderTypeEnum, boolean b) {
//                Snackbar.make(getActivity().getCurrentFocus(),"No card found",Snackbar.LENGTH_LONG).show();
        }
    };

    private void prepareRetrofit(WalletPaymentRequest request, String serial) {

        Receipt receipt=new Receipt();
        receipt.setAmount(request.getAmount());
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
        user=mainActivityViewModel.getUserMutableLiveData().getValue();

        binding.printReceipt.setOnClickListener(v -> {
            String amountString=binding.amount2.getText().toString();
            if(amountString.isEmpty()||amountString==null){
                Snackbar.make(getView(),"Amount needed to continue deductions",Snackbar.LENGTH_LONG).show();
                return;
            }
            request.setAmount(amountString);

            mCardReadManager.cancelSearchCard();
            mCardReadManager.searchCard(CardReaderTypeEnum.IC_RF_CARD, 6000, mListener);
        });


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
//            binding.status.setTextColor(getResources().getColor(R.color.design_default_color_error));
//            binding.status.setText("SCAN QR CODE TO CONTINUE");
        }
    }

    private void readMFPlusCard() {
        StringBuilder m1_mf_puls = new StringBuilder();
        byte[] key = StringUtils.convertHexToBytes(keyMfPlus);
        int status = mRfCard.mFPlusFirstAuthen(addressMfPlus, key);
        if (status == SdkResult.SDK_OK) {
            m1_mf_puls.append("Read sector 0:");
            byte[] outdata = new byte[64];
            if (mRfCard.mFPlusL3Read(StringUtils.convertHexToBytes("0000"), (byte) 4, outdata) == SdkResult.SDK_OK) {
                m1_mf_puls.append(StringUtils.convertBytesToHex(outdata));
            }
        }

        if (status == SdkResult.SDK_OK) {
            Message msg = Message.obtain();
//            msg.what = MSG_CARD_MF_PLUS;
            msg.obj = m1_mf_puls.toString();
//            mHandler.sendMessage(msg);

        } else {
//            mHandler.sendEmptyMessage(status);
        }
//        researchRfCard();
    }

    void writeM1() {
        // 1. verify the sector key
        // 2. write it
        byte[] key = StringUtils.convertHexToBytes(keyM1);
        int status = mRfCard.m1VerifyKey((byte) (4 * 10), keyType, key);
        if (status == SdkResult.SDK_OK) {
            for (int i = 0; i < 1; i++) {
                byte[] input = StringUtils.convertHexToBytes("123456789");
                mRfCard.m1WirteBlock((byte) (4 * 10 + i), input);
            }
            getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "Data written successfully", Toast.LENGTH_SHORT).show());

            return;
        }
        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), "status", Toast.LENGTH_SHORT).show());
    }

    private void readM1Card() {
        StringBuilder m1_message = new StringBuilder();
        byte[] key = StringUtils.convertHexToBytes(keyM1);
        int status;
        do {
            // sector 10 = 4 * 10
            status = mRfCard.m1VerifyKey((byte) (4 * 10), keyType, key);
            if (status != SdkResult.SDK_OK) {
                int finalStatus = status;
                getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), String.valueOf(finalStatus), Toast.LENGTH_SHORT).show());
                break;
            }

            for (int i = 0; i < 1; i++) {
                byte[] out = new byte[16];

                status = mRfCard.m1ReadBlock((byte) (4 * 10 + i), out);
                if (status == SdkResult.SDK_OK) {
                    m1_message
                            .append(StringUtils.convertBytesToHex(out));
                } else {
                    break;
                }
            }
        } while (false);


        if (status == SdkResult.SDK_OK) {
            Message msg = Message.obtain();
            msg.what = MSG_CARD_M1;
            msg.obj = m1_message.toString();
            getActivity().runOnUiThread(() -> {
                binding.status2.setText(m1_message.toString());
            });

        } else {
            getActivity().runOnUiThread(() -> {
                Toast.makeText(getActivity(), "m1_message.toString()", Toast.LENGTH_SHORT).show();
            });
        }
//        researchRfCard();
    }


    private void readCpuCard(byte realRfType) {

        int rfReset = mRfCard.rfReset();
        if (rfReset == SdkResult.SDK_OK) {
            byte[] apduSend;
            if (realRfType == SdkData.RF_TYPE_FELICA) { // felica card
                apduSend = APDU_SEND_FELICA;
            } else {
                apduSend = APDU_SEND_RF;
            }
            byte[] recvData = new byte[300];
            int[] recvLen = new int[1];
            int rfRes = mRfCard.rfExchangeAPDU(apduSend, recvData, recvLen);
            int powerDownRes = mRfCard.rfCardPowerDown();
            if (rfRes == SdkResult.SDK_OK) {
//                    mHandler.sendEmptyMessage(rfRes);
                String recv = StringUtils.convertBytesToHex(recvData).substring(0,
                        recvLen[0] * 2);
            }
        }
    }

    private void readICCard(CardSlotNoEnum slotNo) {
        int icCardReset = mICCard.icCardReset(slotNo);
        int[] recvLen = new int[1];
        byte[] recvData = new byte[300];
        if (icCardReset == SdkResult.SDK_OK) {
            int icRes;
            byte[] apdu;
            if (slotNo != CardSlotNoEnum.SDK_ICC_USERCARD) {
                apdu = APDU_SEND_RANDOM;
            } else {
                apdu = APDU_SEND_IC;
            }
            icRes = mICCard.icExchangeAPDU(slotNo, apdu, recvData, recvLen);
            if (icRes == SdkResult.SDK_OK) {
                String apduRecv =
                        StringUtils.convertBytesToHex(recvData).substring(0, recvLen[0] * 2);

                Toast.makeText(getContext(), apduRecv, Toast.LENGTH_SHORT).show();
            }
        }
        int icCardPowerDown =
                mICCard.icCardPowerDown(CardSlotNoEnum.SDK_ICC_USERCARD);
    }

    private void readMagCard() {
        CardInfoEntity cardInfo = mMagCard.getMagTrackData();
        Log.d("Magnetic Card", "cardInfo.getResultcode():" + cardInfo.getResultcode());
        if (cardInfo.getResultcode() == SdkResult.SDK_OK) {
//String exp = cardInfo.getExpiredDate();
//String cardNo = cardInfo.getCardNo();
            String tk1 = cardInfo.getTk1();
            String tk2 = cardInfo.getTk2();
            String tk3 = cardInfo.getTk3();
        }
        mMagCard.magCardClose();
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