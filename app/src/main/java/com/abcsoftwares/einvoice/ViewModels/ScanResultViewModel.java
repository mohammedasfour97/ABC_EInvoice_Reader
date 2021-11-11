package com.abcsoftwares.einvoice.ViewModels;

import com.abcsoftwares.einvoice.Models.TLV;
import com.abcsoftwares.einvoice.Repositories.ScanResultRepository;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScanResultViewModel extends ViewModel {

    private ScanResultRepository scanResultRepository;

    public ScanResultViewModel(){

        scanResultRepository = new ScanResultRepository();
    }

    public MutableLiveData<List<TLV>> tlvListMutableLiveData(String qrBarcode){

        return scanResultRepository.tlvListMutableLiveData(qrBarcode);
    }
}
