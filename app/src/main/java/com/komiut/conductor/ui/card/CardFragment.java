package com.komiut.conductor.ui.card;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.komiut.conductor.R;
import com.komiut.conductor.databinding.FragmentCardNewBinding;
import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.zcs.sdk.DriverManager;
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
import com.zcs.sdk.util.StringUtils;

public class CardFragment extends Fragment {

    DriverManager mDriverManager = DriverManager.getInstance();
    CardReaderManager mCardReadManager = mDriverManager.getCardReadManager();
    ICCard mICCard = mCardReadManager.getICCard();
    MagCard mMagCard = mCardReadManager.getMAGCard();


    //use the following for the contactless card reading
    RfCard mRfCard = mCardReadManager.getRFCard();
    SLE4428Card mSLE4428Card = mCardReadManager.getSLE4428Card();
    SLE4442Card mSLE4442Card = mCardReadManager.getSLE4442Card();
    IDCard idCard = mCardReadManager.getIDCard();


    //icCard variables below

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCardNewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OnSearchCardListener mListener = new OnSearchCardListener() {
            @Override
            public void onCardInfo(CardInfoEntity cardInfoEntity) {
                CardReaderTypeEnum cardType = cardInfoEntity.getCardExistslot();

                switch (cardType) {
                    case RF_CARD:
// only can get SdkData.RF_TYPE_A / SdkData.RF_TYPE_B /
//                        SdkData.RF_TYPE_FELICA / SdkData.RF_TYPE_MEMORY_A / SdkData.RF_TYPE_MEMORY_B
                        byte rfCardType = cardInfoEntity.getRfCardType();
                        Log.e("TG", "rfCardType: " + rfCardType+cardInfoEntity.getCardNo());
                        if (rfCardType==SdkData.RF_TYPE_A) {
//                            readM1Card();
                            Toast.makeText(getContext(), "CARD 1 card here", Toast.LENGTH_SHORT).show();
                        } else if (rfCardType==SdkData.RF_TYPE_B) {
//                            readMFPlusCard();
                            Toast.makeText(getContext(), "CARD 2 card here", Toast.LENGTH_SHORT).show();
                        } else {
                            if (rfCardType == SdkData.RF_TYPE_FELICA) { // felica card
//                                readFelica();
                                Toast.makeText(getContext(), "Felica", Toast.LENGTH_SHORT).show();
                            } else if (rfCardType == SdkData.RF_TYPE_A || rfCardType ==
                                    SdkData.RF_TYPE_B) {
                                readCpuCard(rfCardType);
                            }
                        }
                        break;
                    case MAG_CARD:
                        readMagCard();
                        break;
                    case IC_CARD:
                        readICCard(CardSlotNoEnum.SDK_ICC_USERCARD);
                        break;
                    case PSIM1:
                        readICCard(CardSlotNoEnum.SDK_ICC_SAM1);
                        break;
                    case PSIM2:
                        readICCard(CardSlotNoEnum.SDK_ICC_SAM2);
                        break;
                }
            }

            @Override
            public void onError(int i) {
                isM1 = false;
                isMfPlus = false;
                Snackbar.make(getActivity().getCurrentFocus(),"No data found",Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onNoCard(CardReaderTypeEnum cardReaderTypeEnum, boolean b) {
//                Snackbar.make(getActivity().getCurrentFocus(),"No card found",Snackbar.LENGTH_LONG).show();
            }
        };
       binding.txtCard.setOnClickListener(v->{
           mCardReadManager.cancelSearchCard();
           mCardReadManager.searchCard(CardReaderTypeEnum.MAG_IC_RF_CARD, 60000, mListener);
       });


    }

    private void readCpuCard(byte realRfType) {

            int rfReset = mRfCard.rfReset();
            if (rfReset == SdkResult.SDK_OK) {
                byte[] apduSend;
                if (realRfType == SdkData.RF_TYPE_FELICA) { // felica card
                    apduSend = APDU_SEND_FELICA;
                } else {
                    apduSend = APDU_SEND_RF;
                }byte[] recvData = new byte[300];
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
            String tk1 = cardInfo.getTk1();String tk2 = cardInfo.getTk2();
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