package com.oseamiya.esewa.esewabysneha.EsewaLib;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import com.google.appinventor.components.runtime.ComponentContainer;
import android.content.Context;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.oseamiya.esewa.esewabysneha.CardView.CardView;

import java.io.IOException;

public class LayoutPaymentConfirmation {
    private ComponentContainer container;
    private Context context;
    private LinearLayout linearLayout;
    private AppCompatTextView welcomeText;
    private AppCompatTextView nameOfPerson;
    private Button buttonCancel;
    private Button buttonPay;
    private AppCompatTextView textViewBalance;
    private AppCompatTextView textViewMerchantName;
    private AppCompatTextView textViewProductName;
    private AppCompatImageView backButton;
    private AppCompatTextView totalAmount;
    private EditText editTextOtp;
    private LinearLayout otpPLL;
    private CardView comissionView;
    private LinearLayout cashBackLL;
    private LinearLayout chargeLL;
    private AppCompatTextView textViewCashBack;
    private AppCompatTextView textViewCharge;
    private AppCompatTextView comissionViewtotalPaymentAmount;



    public void CreateLayout() throws IOException {
        linearLayout = new LinearLayout(container.$context());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1 , -1));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(Color.parseColor("#ffff"));

        //lets mAKE Relative layout
        RelativeLayout relativeLayout = new RelativeLayout(container.$context());
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1 , -2));
        relativeLayout.setBackgroundColor(Color.parseColor("#61bc47"));

        linearLayout.addView(relativeLayout);

        // In this relative layout, the first thing we have to add is back button(image) at top corner so
        // So i will create a imageview and add back button with proper size

         backButton = new AppCompatImageView(container.$context());
        backButton.setBackgroundDrawable(MediaUtil.getBitmapDrawable(container.$form() , "back_button_white_imageview"));
        relativeLayout.addView(backButton);

        //So back button is created and now adding image of white esewa slogan image
        // so i will create an image view

        AppCompatImageView esewa_slogan_white = new AppCompatImageView(container.$context());
        View esewa_slogann_white_view = (View) esewa_slogan_white;
        esewa_slogann_white_view.setLayoutParams(new ViewGroup.LayoutParams(dptopx(125) , dptopx(40)));
        if(esewa_slogann_white_view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams){
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) esewa_slogann_white_view.getLayoutParams();
            p.setMargins(0 , dptopx(14) , 0 , 0);
        }
        esewa_slogann_white_view.setBackground(MediaUtil.getBitmapDrawable(container.$form() , "img_esewa_white_logo_with_slogan"));
        relativeLayout.addView(esewa_slogann_white_view);

        // Now this first relative view is finished .. again we have to create a linear layout and add all components inside of the linear layout
        // Creating a horizontal linear layout for the container purpose

        LinearLayout linearLayout1 = new LinearLayout(container.$context());
        linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(-1 , -2));
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout1.setWeightSum(2);
        linearLayout.addView(linearLayout1);

        //Again creating relativeLayout inside of linear layout
        RelativeLayout relativeLayout1 = new RelativeLayout(container.$context());
        relativeLayout1.setLayoutParams(new RelativeLayout.LayoutParams(0 , -1));
        relativeLayout1.setBackgroundColor(Color.parseColor("#61bc47"));
        relativeLayout1.setPadding(dptopx(7) , dptopx(7) , dptopx(7) , dptopx(7));
        linearLayout1.addView(relativeLayout1);

        // Then creating a image view inside linearlayout1
        ImageView userIconImage = new ImageView(container.$context());
        View userIconImageView = (View) userIconImage;
        userIconImageView.setLayoutParams(new ViewGroup.LayoutParams(dptopx(30) , dptopx(30)));
        userIconImageView.setBackground(context.getDrawable(0x7f080065));
        linearLayout1.addView(userIconImageView);

        // Again creating a linear layout inside of the linear layout 1 programatically
        LinearLayout linearLayout2 = new LinearLayout(container.$context());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(-1 , -1);
        lp.addRule(RelativeLayout.RIGHT_OF , 0x7f0b00ae);
        linearLayout2.setLayoutParams(lp);
        linearLayout2.setBackgroundColor(Color.parseColor("#61bc47"));
        linearLayout1.addView(linearLayout2);

        //Adding welcome text in linear layout 2
         welcomeText = new AppCompatTextView(container.$context());
         welcomeText.setText("Welcome");
         welcomeText.setTextSize(TypedValue.COMPLEX_UNIT_SP , 11);
         welcomeText.setTextColor(Color.WHITE);
         linearLayout2.addView(welcomeText);

         //Adding name oseamia text
        nameOfPerson = new AppCompatTextView(container.$context());
        nameOfPerson.setBackgroundColor(Color.parseColor("#61bc47"));
        nameOfPerson.setTextColor(Color.WHITE);
        nameOfPerson.setTextSize(TypedValue.COMPLEX_UNIT_SP , 17);
        nameOfPerson.setMaxLines(2);
        nameOfPerson.setEllipsize(TextUtils.TruncateAt.END);
        nameOfPerson.setText("Oseamiya");
        linearLayout2.addView(nameOfPerson);

        // Now linearLayout2 and relativeLayout1 is finished , and there will be no work related to them downwards
        /////////////////////////////////////////////

        // Randomly added for testing purposes only
        ////////////////////////////////////////////////////////////////////////////////////////////////
        buttonCancel = new Button(container.$context());
        buttonCancel.setText("Button Cancel");
        linearLayout.addView(buttonCancel);

        buttonPay = new Button(container.$context());
        buttonPay.setText("Button Pay");
        linearLayout.addView(buttonPay);

        textViewBalance = new AppCompatTextView(container.$context());
        linearLayout.addView(textViewBalance);

        ScrollView scrollView = new ScrollView(container.$context());
        linearLayout.addView(scrollView);

        textViewMerchantName = new AppCompatTextView(container.$context());
        scrollView.addView(textViewMerchantName);

        textViewProductName = new AppCompatTextView(container.$context());
        scrollView.addView(textViewProductName);

        totalAmount = new AppCompatTextView(container.$context());
        scrollView.addView(totalAmount);

        editTextOtp = new EditText(container.$context());
        scrollView.addView(editTextOtp);

        otpPLL = new LinearLayout(container.$context());
        scrollView.addView(otpPLL);

        comissionView = new CardView(container.$context());
        scrollView.addView(comissionView);

        cashBackLL = new LinearLayout(container.$context());
        scrollView.addView(cashBackLL);

        chargeLL = new LinearLayout(container.$context());
        scrollView.addView(chargeLL);

        textViewCashBack = new AppCompatTextView(container.$context());
        scrollView.addView(textViewCashBack);

        textViewCharge = new AppCompatTextView(container.$context());
        scrollView.addView(textViewCharge);

        comissionViewtotalPaymentAmount = new AppCompatTextView(container.$context());
        scrollView.addView(comissionViewtotalPaymentAmount);











    }
    public void setComissionViewtotalPaymentAmount(){

    }
    public AppCompatTextView getComissionViewtotalPaymentAmount(){
        return comissionViewtotalPaymentAmount;
    }
    public void setTextViewCharge(){
        //
    }
    public AppCompatTextView getTextViewCharge(){
        return textViewCharge;
    }

    public void setTextViewCashBack(){

    }
    public AppCompatTextView getTextViewCashBack(){
        return textViewCashBack;
    }
    public void setChargeLL(){
        //
    }
    public LinearLayout getChargeLL(){
        return chargeLL;
    }
    public void setCashBackLL(){
        //
    }

    public LinearLayout getCashBackLL() {
        return cashBackLL;
    }

    public void setComissionView(){
        //
    }
    public CardView getComissionView(){
        return comissionView;
    }
    public void setOtpPLL(){
        //
    }
    public LinearLayout getOtpPLL(){
        return otpPLL;
    }
    public void setEditTextOtp(){
        //
    }
    public EditText getEditTextOtp(){
        return  editTextOtp;
    }
    public void setTotalAmount(){
        //
    }
    public AppCompatTextView getTotalAmount(){
        return totalAmount;
    }
    public void setTextViewMerchantName(){
        //
    }
    public AppCompatTextView getTextViewMerchantName(){
        return textViewMerchantName;
    }
    public void setTextViewProductName(){
        //
    }

    public AppCompatTextView getTextViewProductName(){
        return textViewProductName;
    }
    public AppCompatTextView getTextViewBalance(){
        return textViewBalance;
    }
    public void setTextViewBalance(){
        //
    }
    public void setButtonCancel(){
        //
    }
    public Button getButtonCancel(){
        return buttonCancel;
    }
    public void setButtonPay(){
        //
    }

    public Button getButtonPay() {
        return buttonPay;
    }

    ///////////////////////////////////
    public int dptopx(int dp){
        return Math.round(dp * context.getResources().getDisplayMetrics().density);
    }
    public void setLinearLayout (){
        //
    }
    public LinearLayout getLinearLayout(){
        return linearLayout;
    }
    public void setWelcomeText(){
        //
    }
    public AppCompatTextView getWelcomeText(){
        return welcomeText;
    }
    public void setNameOfPerson(){
        //
    }
    public AppCompatTextView getNameOfPerson(){
        return nameOfPerson;
    }
    public void setBackButton(){
        //
    }
    public AppCompatImageView getBackButton(){
        return backButton;
    }

}
