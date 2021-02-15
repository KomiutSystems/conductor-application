package com.komiut.conductor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.komiut.conductor.model.MpesaMessage;
import com.komiut.conductor.repository.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.komiut.conductor.db.AppDatabase.dbExecutor;

public class IncomingSms extends BroadcastReceiver {


    Repository repository;

    // Get the object of SmsManager
    final SmsManager sms = SmsManager.getDefault();

    public void onReceive(Context context, Intent intent) {
        repository = new Repository(context);
// Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage
                            .createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage
                            .getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
                    String[] split_one = message.split(":");
                    String ss = split_one[1].substring(0, 4);

                    if (senderNum.contains("MPESA") && message.contains("received")) {
                        SimpleDateFormat newPattern=new SimpleDateFormat("dd/MM/yy");
                        String[] receivedMessage = message.split("\\s+");
                        MpesaMessage mpesaMessage = new MpesaMessage();
                        mpesaMessage.setPhonenumber(receivedMessage[8]);
                        mpesaMessage.setTransactionId(receivedMessage[0]);
                        mpesaMessage.setFirstname(receivedMessage[9]);
                        mpesaMessage.setSecondname(receivedMessage[10]);
                        mpesaMessage.setAmount(receivedMessage[5].substring(2));
                        mpesaMessage.setTime(receivedMessage[4] + " " + receivedMessage[5].substring(0, 2));
                        mpesaMessage.setDate(newPattern.format(new Date()));
                        mpesaMessage.setPrintStatus(false);

//                        Toast.makeText(context, mpesaMessage.getTime(), Toast.LENGTH_SHORT).show();
                        dbExecutor.execute(() -> {
                            long a=repository.mpesaMessagesDao.insertAll(mpesaMessage);
                        });

                        return;
                    }

                }
            }

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }
    }


}