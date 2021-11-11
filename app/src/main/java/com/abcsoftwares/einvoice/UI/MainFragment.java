package com.abcsoftwares.einvoice.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abcsoftwares.einvoice.BaseClasses.BaseFragment;
import com.abcsoftwares.einvoice.Constants;
import com.abcsoftwares.einvoice.LocaleHelper;
import com.abcsoftwares.einvoice.MainActivity;
import com.abcsoftwares.einvoice.MyApplication;
import com.abcsoftwares.einvoice.R;
import com.abcsoftwares.einvoice.databinding.FragmentMainBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;

public class MainFragment extends BaseFragment {

    private FragmentMainBinding fragmentMainBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        return fragmentMainBinding.getRoot();
    }


    @Nullable
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkToNavigateToScanner();
        setViews();
        setListeners();
    }


    private void setViews(){

        if (MyApplication.getTinyDB().getString("loc").equals("ar-rSA"))
            fragmentMainBinding.translationTxt.setText(getResources().getString(R.string.ar));
    }


    private void setListeners(){

        fragmentMainBinding.transLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MyApplication.getTinyDB().getString("loc").equals("ar-rSA")){

                    LocaleHelper.setLocale(getContext(), "en");
                    MyApplication.getTinyDB().putString("loc", "en");
                }


                else {

                    LocaleHelper.setLocale(getContext(), "ar");
                    MyApplication.getTinyDB().putString("loc", "ar-rSA");
                }


                getActivity().startActivity(new Intent(((MainActivity)getActivity()), MainActivity.class));
                getActivity().finish();
            }
        });

        fragmentMainBinding.scanBarcodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navigateToScanner();
            }
        });
    }


    private void checkToNavigateToScanner(){

        if (getArguments()!= null && getArguments().getString("scan_again").equals("yes"))
            navigateToScanner();
    }

    private void navigateToScanner(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Constants.checkCameraPermission(getActivity())) {
                requestPermissions(new String[] {Manifest.permission.CAMERA}, 1);
                return;
            }
        }

        Intent i = new Intent(getActivity() , BarcodeActivity.class);
        startActivityForResult(i, 1);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 1){

            Bundle bundle = new Bundle();
            bundle.putString("qr", data.getStringExtra("result"));

            ((MainActivity)getActivity()).navController.navigate(R.id.action_fragment_main_to_fragment_scan_result, bundle);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    navigateToScanner();
                }
                break;
        }
    }

}
