package com.example.dhobijunction.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dhobijunction.R;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PaymentActivity extends AppCompatActivity {
    String key = "ub4zyyCk";
    String txnid = "1";
    String amount = "10";
    String productinfo = "Dhobi Junction";
    String firstname = "Faizan";
    String email = "faizanshaikh654933@gmail.com";
    String salt = "lmBlYqpa5L";
    String merchantId = "7380570";
    String serverCalculatedHash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().hide();
        String hashSequence = key + "|" + txnid + "|" + amount + "|" + productinfo + "|" + firstname + "|" + email + "|||||||||||" + salt;
        serverCalculatedHash = hashCal("SHA-512", hashSequence);

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new
                PayUmoneySdkInitializer.PaymentParam.Builder();
        builder.setAmount(amount)                          // Payment amount
                .setTxnId(txnid)                                             // Transaction ID
                .setPhone("7405719399")                                           // User Phone number
                .setProductName("Dhobi Junction")                   // Product Name or description
                .setFirstName("Faizan")                              // User First name
                .setEmail(email)                                            // User Email ID
                .setsUrl("https://www.payumoney.com/mobileapp/payumoney/success.php")                    // Success URL (surl)
                .setfUrl("https://www.payumoney.com/mobileapp/payumoney/failure.php")     //Failure URL (furl)
                .setUdf1("")
                .setUdf2("")
                .setUdf3("")
                .setUdf4("")
                .setUdf5("")
                .setUdf6("")
                .setUdf7("")
                .setUdf8("")
                .setUdf9("")
                .setUdf10("")
                .setIsDebug(false)                              // Integration environment - true (Debug)/ false(Production)
                .setKey(key)                        // Merchant key
                .setMerchantId(merchantId);

        PayUmoneySdkInitializer.PaymentParam paymentParam = null;
        try {
            paymentParam = builder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
//set the hash
        paymentParam.setMerchantHash(serverCalculatedHash);
        // Invoke the following function to open the checkout page.
        PayUmoneyFlowManager.startPayUMoneyFlow(
                paymentParam,
                this,
                R.style.AppTheme_default,
                true);
    }

    public static String hashCal(String type, String hashString) {
        StringBuilder hash = new StringBuilder();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(type);
            messageDigest.update(hashString.getBytes());
            byte[] mdbytes = messageDigest.digest();
            for (byte hashByte : mdbytes) {
                hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash.toString();
    }
}