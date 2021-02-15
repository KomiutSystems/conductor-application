package com.komiut.conductor.ui.tag;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.komiut.conductor.R;
import com.komiut.conductor.databinding.TopUpFragmentBinding;
import com.komiut.conductor.ui.tag.TopUpViewModel;
import com.zcs.sdk.DriverManager;
import com.zcs.sdk.card.CardInfoEntity;
import com.zcs.sdk.card.CardReaderManager;
import com.zcs.sdk.card.CardReaderTypeEnum;
import com.zcs.sdk.card.RfCard;
import com.zcs.sdk.listener.OnSearchCardListener;

public class TopUpFragment extends Fragment {

    DriverManager mDriverManager = DriverManager.getInstance();
    CardReaderManager mCardReadManager = mDriverManager.getCardReadManager();
    RfCard mRfCard = mCardReadManager.getRFCard();
    private TopUpViewModel mViewModel;
    boolean isM1 = false;
    byte mRfCardType = 0;
    private static final int READ_TIMEOUT = 60 * 1000;


    TopUpFragmentBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRfCard = mCardReadManager.getRFCard();

    }

    OnSearchCardListener listener = new OnSearchCardListener() {
        @Override
        public void onCardInfo(CardInfoEntity cardInfoEntity) {

            CardReaderTypeEnum cardReaderTypeEnum = cardInfoEntity.getCardExistslot();
            if (cardReaderTypeEnum == CardReaderTypeEnum.RF_CARD) {
                byte rfCard = cardInfoEntity.getRfCardType();
                if(isM1)
                {
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "we found a card", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }

        }

        @Override
        public void onError(int i) {
            isM1 = false;
        }

        @Override
        public void onNoCard(CardReaderTypeEnum cardReaderTypeEnum, boolean b) {
/*
                requireActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "No card found", Toast.LENGTH_SHORT).show();
                    }
                });*/

        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = TopUpFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TopUpViewModel.class);
        // TODO: Use the ViewModel

        binding.button7.setOnClickListener(view -> {
            mCardReadManager.cancelSearchCard();
            mCardReadManager.searchCard(CardReaderTypeEnum.RF_CARD, READ_TIMEOUT, listener);
        });
    }

}