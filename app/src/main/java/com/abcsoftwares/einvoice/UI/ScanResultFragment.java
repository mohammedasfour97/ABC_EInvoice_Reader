package com.abcsoftwares.einvoice.UI;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.abcsoftwares.einvoice.Adapters.ScanResultsAdapter;
import com.abcsoftwares.einvoice.BaseClasses.BaseFragment;
import com.abcsoftwares.einvoice.LocaleHelper;
import com.abcsoftwares.einvoice.MainActivity;
import com.abcsoftwares.einvoice.Models.TLV;
import com.abcsoftwares.einvoice.MyApplication;
import com.abcsoftwares.einvoice.R;
import com.abcsoftwares.einvoice.Utils;
import com.abcsoftwares.einvoice.ViewModels.ScanResultViewModel;
import com.abcsoftwares.einvoice.databinding.FragmentScanResultBinding;
import com.google.zxing.WriterException;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import static android.content.Context.WINDOW_SERVICE;

public class ScanResultFragment extends BaseFragment {
    private FragmentScanResultBinding scanResultBinding;


    ////vars for api requests ////
    private ScanResultViewModel scanResultViewModel;
    private Observer<List<TLV>> tlvListObserver;

    //// vars for RecyclerView ////
    private ScanResultsAdapter scanResultsAdapter;
    private List<TLV> tlvList;

    //// vars for showing qr ////
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        scanResultBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_scan_result, container, false);
        return scanResultBinding.getRoot();
    }


    @Nullable
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecyclerView();
        setQrImg();
        setListeners();
        initObservers();
        requestScanResult();
    }


    private void initRecyclerView(){

        tlvList = new ArrayList<>();
        scanResultsAdapter = new ScanResultsAdapter(tlvList, getContext());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        scanResultBinding.scanResultsRecyclerview.setLayoutManager(mLayoutManager);
        scanResultBinding.scanResultsRecyclerview.setAdapter(scanResultsAdapter);
    }


    private void setListeners(){

        scanResultBinding.scanAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("scan_again", "yes");
                ((MainActivity)getActivity()).navController.navigate(R.id.action_fragment_scan_result_to_fragment_main, bundle);
            }
        });

        scanResultBinding.homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).navController.navigate(R.id.action_fragment_scan_result_to_fragment_main);
            }
        });
    }

    private void setQrImg(){

        WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);

        // initializing a variable for default display.
        Display display = manager.getDefaultDisplay();

        // creating a variable for point which
        // is to be displayed in QR Code.
        Point point = new Point();
        display.getSize(point);

        // getting width and
        // height of a point
        int width = point.x;
        int height = point.y;

        // generating dimension from width and height.
        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;

        // setting this dimensions inside our qr code
        // encoder to generate our qr code.
        qrgEncoder = new QRGEncoder(getArguments().getString("qr"), null, QRGContents.Type.TEXT, dimen);
        try {
            // getting our qrcode in the form of bitmap.
            bitmap = qrgEncoder.encodeAsBitmap();
            // the bitmap is set inside our image
            // view using .setimagebitmap method.
            scanResultBinding.qrImg.setImageBitmap(bitmap);
        } catch (WriterException e) {
            // this method is called for
            // exception handling.
            e.printStackTrace();
        }
    }

    private void requestScanResult() {

        if (getArguments().getString("qr")!=null){

            showProgressDialog(getResources().getString(R.string.loading), getResources().getString(R.string.loading_qr_data),
                    false);

            scanResultViewModel.tlvListMutableLiveData(getArguments().getString("qr")).observe(getViewLifecycleOwner(),
                    tlvListObserver);
        }

    }

    private void initObservers() {

        scanResultViewModel = ViewModelProviders.of(this).get(ScanResultViewModel.class);

        tlvListObserver = new Observer<List<TLV>>() {
            @Override
            public void onChanged(List<TLV> tlvs) {

                if (tlvs != null) {

                    hideProgress();

                    if (getLifecycle().getCurrentState() == Lifecycle.State.RESUMED) {

                        if (tlvs.isEmpty()) {

                            showFailedDialog(getResources().getString(R.string.no_qr_data),
                                    false);
                            return;
                        }

                        tlvList.addAll(tlvs);
                        scanResultsAdapter.notifyDataSetChanged();
                    }
                }
            }
        };
    }
}
