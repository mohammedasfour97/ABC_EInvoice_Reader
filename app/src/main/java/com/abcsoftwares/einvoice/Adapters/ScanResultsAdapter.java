package com.abcsoftwares.einvoice.Adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.abcsoftwares.einvoice.Models.TLV;
import com.abcsoftwares.einvoice.R;
import com.abcsoftwares.einvoice.TinyDB;
import com.abcsoftwares.einvoice.Utils;
import com.abcsoftwares.einvoice.databinding.ItemScanResultBinding;
import com.awesomedialog.blennersilva.awesomedialoglibrary.interfaces.Closure;

import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


public class ScanResultsAdapter extends RecyclerView.Adapter<ScanResultsAdapter.ScanResultsViewHolder> {
    private List<TLV> tlvList;
    private Context context;

    public class ScanResultsViewHolder extends RecyclerView.ViewHolder {

        private ItemScanResultBinding itemScanResultBinding;

        public ScanResultsViewHolder(ItemScanResultBinding itemScanResultBinding) {
            super(itemScanResultBinding.getRoot());

            this.itemScanResultBinding = itemScanResultBinding;
        }

    }

    public ScanResultsAdapter(List<TLV> tlvList, Context context) {
        this.tlvList = tlvList;
        this.context = context;
    }

    @Override
    public ScanResultsAdapter.ScanResultsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemScanResultBinding itemScanResultBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_scan_result, parent, false);
        return new ScanResultsAdapter.ScanResultsViewHolder(itemScanResultBinding);

    }

    @Override
    public void onBindViewHolder(ScanResultsAdapter.ScanResultsViewHolder holder, int position) {

        TLV tlv = tlvList.get(position);

        holder.itemScanResultBinding.titleTxt.setText(Utils.getLangString(tlv.getTagEn(), tlv.getTagAr()) + " : ");
        holder.itemScanResultBinding.resultTxt.setText(tlv.getDataValue());
    }

    @Override
    public int getItemCount() {
        return tlvList.size();
    }

    
}