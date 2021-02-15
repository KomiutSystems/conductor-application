package com.komiut.conductor.ui.mpesa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.LocaleData;
import android.os.Environment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.komiut.conductor.R;
import com.komiut.conductor.model.MpesaMessage;
import com.komiut.conductor.model.Receipt;
import com.komiut.conductor.retrofit.SaccoInfo;
import com.zcs.sdk.DriverManager;
import com.zcs.sdk.Printer;
import com.zcs.sdk.SdkResult;
import com.zcs.sdk.print.PrnStrFormat;
import com.zcs.sdk.print.PrnTextFont;
import com.zcs.sdk.print.PrnTextStyle;

import java.util.ArrayList;
import java.util.List;

public class MpesaAdapter extends RecyclerView.Adapter<MpesaAdapter.MpesaViewHolder> {
    List<MpesaMessage> messages;
    DriverManager mDriverManager = DriverManager.getInstance();
    Printer mPrinter = mDriverManager.getPrinter();
    Context context;
    String matatu;
    SaccoInfo saccoInfo;

    public MpesaAdapter(List<MpesaMessage> messages, String matatu, SaccoInfo saccoInfo) {
        this.messages=messages;
        this.matatu=matatu;
        this.saccoInfo=saccoInfo;
    }

    @NonNull
    @Override
    public MpesaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.mpesa_list_item,parent,false);
        context=parent.getContext();

        return new MpesaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MpesaViewHolder holder, int position) {

        MpesaMessage receipt=messages.get(position);

        holder.messages.setText(receipt.getTransactionId()+" confirmed. On "
                +receipt.getDate()+" at "+receipt.getTime()+
                " you have received "+receipt.getAmount()+
                " from "+receipt.getPhonenumber()+" by the name "+receipt.getFirstname()+" ");

        holder.print.setOnClickListener(view -> {


//            String[] words = receipt.split("\\s+");
            Receipt receiptPrint=new Receipt();
            receiptPrint.setPhonenumber(receipt.getPhonenumber());
            receiptPrint.setStatus(receipt.getTransactionId());
            receiptPrint.setUsername(receipt.getFirstname());
            receiptPrint.setAmount(receipt.getAmount());
            receiptPrint.setMatatu(matatu);
            receiptPrint.setTime(receipt.getTime());
            receiptPrint.setDate(receipt.getDate());

            printReceipt(mPrinter,receiptPrint,saccoInfo);


        });
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
    public class MpesaViewHolder extends RecyclerView.ViewHolder {
        TextView messages;
        Button print;
        public MpesaViewHolder(@NonNull View itemView) {
            super(itemView);
            messages=itemView.findViewById(R.id.mpesa_message);
            print=itemView.findViewById(R.id.printNow);
        }
    }

    private void printReceipt(Printer mPrinter, Receipt receipt, SaccoInfo saccoInfo) {
        int printStatus = mPrinter.getPrinterStatus();
        if (printStatus == SdkResult.SDK_PRN_STATUS_PAPEROUT) {
//            Snackbar.make(context.getCurrentFocus(), "Load paper to continue", Snackbar.LENGTH_LONG).show();
            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
            return;

        } else {

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
            mPrinter.setPrintAppendString("TRANSACTION: " + receipt.getStatus(), format);
            mPrinter.setPrintAppendString("DATE: " + receipt.getDate()+" at "+receipt.getTime(), format);
            mPrinter.setPrintAppendString("PHONE NO: " + receipt.getPhonenumber().substring(0,6)+"***"+receipt.getPhonenumber().substring(receipt.getPhonenumber().length()-3), format);
            mPrinter.setPrintAppendString("MPESA AMOUNT: " + receipt.getAmount(), format);
            mPrinter.setPrintAppendString("CHANGE: " + "Ksh 0",format);
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



                  /* Bitmap image= BitmapFactory.decodeResource(context.getResources(),R.drawable.loogo);
            mPrinter.setPrintAppendBitmap(image, Layout.Alignment.ALIGN_CENTER);
*/


//            format.setPath(Environment.getExternalStorageDirectory() +
//                    "/fonts/simsun.ttf");
//            mPrinter.setPrintAppendString( receipt.getStatus()+"confirmed. Kshs"+receipt.getAmount()+" paid to CITY STAR SHUTTLE LTD-KCR 153R", format);
           /* mPrinter.setPrintAppendString("Thank you " + receipt.getUsername(), format);
            format.setTextSize(20);
            format.setStyle(PrnTextStyle.NORMAL);
            format.setAli(Layout.Alignment.ALIGN_NORMAL);
            mPrinter.setPrintAppendString(" ", format);
            mPrinter.setPrintAppendString("AMOUNT PAID:" + receipt.getAmount(), format);
            mPrinter.setPrintAppendString("DATE PAID:" + receipt.getDate(), format);
            mPrinter.setPrintAppendString("MATATU PLATE:" + receipt.getMatatu(), format);*/

            /*format.setAli(Layout.Alignment.ALIGN_CENTER);
            format.setTextSize(20);
            format.setStyle(PrnTextStyle.BOLD);
            mPrinter.setPrintAppendString("", format);
            format.setAli(Layout.Alignment.ALIGN_NORMAL);
            format.setStyle(PrnTextStyle.BOLD);
            format.setTextSize(25);
            mPrinter.setPrintAppendString(" -----------------------------", format);
            mPrinter.setPrintAppendString(" ", format);

            mPrinter.setPrintAppendString(" ", format);*/
        }
    }


    public interface UpdateMpesaMessage{
        void SendMessageToRepository(MpesaMessage mpesaMessage);
    }

}
