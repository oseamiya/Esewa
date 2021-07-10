package com.oseamiya.esewa.esewabysneha;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.widget.SwitchCompat;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.util.MediaUtil;
import com.oseamiya.esewa.esewabysneha.CardView.CardView;

import java.io.IOException;

public class LoginActivityLayout {
    private ComponentContainer container;
    private Context context;
    private ScrollView scrollView;
    private AppCompatEditText appCompatEditText;
    private AppCompatEditText appCompatEditText1;
    private Button signInButton;
    private AppCompatImageView appCompatImageView1;
    private Button registerButton;
    private SwitchCompat switchCompat;


    public void CreateLayout() throws IOException {
        // At first this should be scroll view so i made scroll view...
         scrollView = new ScrollView(container.$context());
        scrollView.setLayoutParams(new ViewGroup.LayoutParams(-2,-2));
        //Then a relative layout is created inside scroll view
        RelativeLayout relativeLayout = new RelativeLayout(container.$context());
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-2 , -2));
        scrollView.addView(relativeLayout);

       // This linearLayout is the header picture of esewa login
        LinearLayout linearLayout = new LinearLayout(container.$context());

        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1 , dptopx(170)));
        linearLayout.setBackground(MediaUtil.getBitmapDrawable(container.$form() , "img_background_header"));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        relativeLayout.addView(linearLayout);

        //This forappCompatImageView is image view with white logo of esewa on the linear layout
        AppCompatImageView appCompatImageView = new AppCompatImageView(container.$context());
        View forappCompatImageView = (View) appCompatImageView;
        forappCompatImageView.setLayoutParams(new ViewGroup.LayoutParams(dptopx(150), dptopx(160)));
        forappCompatImageView.setBackground(MediaUtil.getBitmapDrawable(container.$form() , "img_esewa_white_logo_with_slogan"));

        //Since this white image view is on Linear layout so
        linearLayout.addView(forappCompatImageView);

        //Again creating a appcompatimageview , this is for cancelling the login but Esewa sdk has never edited this , so let it remain nothing
         appCompatImageView1 = new AppCompatImageView(container.$context());

        relativeLayout.addView(appCompatImageView1);

        //Again creating linear layout
        LinearLayout linearLayout1 = new LinearLayout(container.$context());
        // ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(-1,-1);
        linearLayout1.setPadding(dptopx(40) , dptopx(20) , dptopx(40) , 0);

        //Adding linearlayout1 in relative layout
        relativeLayout.addView(linearLayout1);

        //Creating cardview
        CardView cardView = new CardView(container.$context());
        cardView.setCardElevation(dptopxinfloat((float) 1.5));
        linearLayout1.addView(cardView);

        // Creating the textinput layout
         appCompatEditText = new AppCompatEditText(container.$context());
        appCompatEditText.setBackgroundColor(Color.WHITE);
        Drawable imgForAppCompatEditTextLeft = MediaUtil.getBitmapDrawable(container.$form() , "ic_user");
        imgForAppCompatEditTextLeft.setBounds(0 , 0 , 60 , 60);
        appCompatEditText.setCompoundDrawables(imgForAppCompatEditTextLeft , null , null , null);
        appCompatEditText.setCompoundDrawablePadding(dptopx(12));
        appCompatEditText.setGravity(Gravity.CENTER_VERTICAL);
        appCompatEditText.setHint("eSewa Id");
        appCompatEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        appCompatEditText.setMaxLines(1);
        appCompatEditText.setPadding(dptopx(12) , dptopx(12) , dptopx(12) , dptopx(12));

        //text input layout is inside card view so
        cardView.addView(appCompatEditText);

        ///////////////////////////////////////////////////////////////////////////
        CardView cardView1 =  new CardView(container.$context());
        ViewGroup.MarginLayoutParams marginlayoutParamsForCardView1 = (ViewGroup.MarginLayoutParams) cardView1.getLayoutParams();
        marginlayoutParamsForCardView1.setMargins(0 , dptopx(15) , 0 , dptopx(15));
        cardView1.requestLayout();

         appCompatEditText1 = new AppCompatEditText(container.$context());
        appCompatEditText1.setBackgroundColor(Color.WHITE);
        appCompatEditText1.setGravity(Gravity.CENTER_VERTICAL);
        appCompatEditText1.setHint("MPIN / Password");


        Drawable imgForAppCompatEditText1 = MediaUtil.getBitmapDrawable(container.$form() , "ic_password");
        imgForAppCompatEditText1.setBounds(0 , 0 , 60 , 60);
        appCompatEditText1.setCompoundDrawables(imgForAppCompatEditText1 , null, null , null);
        appCompatEditText1.setMaxLines(1);
        appCompatEditText1.setInputType(InputType.TYPE_CLASS_TEXT);

        cardView1.addView(appCompatEditText1);

        /////////////////////////////////////////////////////////////////////////////////////
         switchCompat = new SwitchCompat(container.$context());
        switchCompat.setTextSize(sptopx(14));

        signInButton = new Button(container.$context());
        signInButton.setText("Sign In");
        LinearLayout.LayoutParams  layoutParamsForButton = new LinearLayout.LayoutParams(-1 , -1);
        layoutParamsForButton.setMargins(0 , dptopx(10) , 0 , 0);
        signInButton.setLayoutParams(layoutParamsForButton);

        linearLayout1.addView(signInButton);

        ////////////

        LinearLayout linearLayoutStyleHorizontal = new LinearLayout(container.$context());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1 , -1);
        layoutParams.setMargins(0 , dptopx(24) , 0 , dptopx(24));
        linearLayoutStyleHorizontal.setGravity(Gravity.CENTER_VERTICAL);

        View viewInsidelinearLayoutStyleHorizontal = new View(container.$context());
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(0 , -1);
        viewInsidelinearLayoutStyleHorizontal.setBackgroundColor(Color.parseColor("#15000000"));
        viewInsidelinearLayoutStyleHorizontal.setLayoutParams(lp);

        AppCompatTextView appCompatTextView = new AppCompatTextView(container.$context());
        appCompatTextView.setWidth(dptopx(16));
        appCompatTextView.setHeight(dptopx(16));
        appCompatTextView.setGravity(Gravity.CENTER);
        appCompatTextView.setBackground(MediaUtil.getBitmapDrawable(container.$form() , "bg_circle_grey"));
        appCompatTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP , 6);

        linearLayoutStyleHorizontal.addView(appCompatTextView);

        //Now i will be creating a button for register , let say it as registorButton
        registerButton = new Button(container.$context());
        registerButton.setBackgroundColor(Color.WHITE);
        registerButton.setText("Register For Free");




    }
    public int dptopx(int dp){
        float valueInFloat = dp * context.getResources().getDisplayMetrics().density;
        return Math.round(valueInFloat);
    }
    public float dptopxinfloat(float dp){
        return dp * context.getResources().getDisplayMetrics().density;
    }
    public float sptopx(float sp){
        return sp * context.getResources().getDisplayMetrics().scaledDensity;
    }
    public void setScrollView(){
        //
    }
    public ScrollView getScrollView(){
        return scrollView;
    }
    public void setAppCompatEditText(){
        //
    }
    public AppCompatEditText getAppCompatEditText(){
        return appCompatEditText;
    }
    public void setAppCompatEditText1(){
        //
    }
    public AppCompatEditText getAppCompatEditText1(){
        return appCompatEditText1;
    }
    public void setSignInButton(){
        //
    }
    public Button getSignInButton(){
        return signInButton;
    }
    public void setAppCompatImageView1(){
        //
    }
    public AppCompatImageView getAppCompatImageView1(){
        return appCompatImageView1;
    }
    public void setRegisterButton(){
        //
    }
    public Button getRegisterButton(){
        return registerButton;
    }
    public void setSwitchCompat(){
        //
    }

    public SwitchCompat getSwitchCompat() {
        return switchCompat;
    }
}
