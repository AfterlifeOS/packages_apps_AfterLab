/*
 * Copyright (C) 2020 The Dirty Unicorns Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.afterlife.afterlab.fragments.team;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settings.R;
import java.util.List;

public class LeadsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DevInfoAdapter> mList;

    public LeadsAdapter(List<DevInfoAdapter> list) {
        super();
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
            R.layout.leads_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        DevInfoAdapter itemAdapter = mList.get(position);

        ((ViewHolder) viewHolder).mDevImage.setImageResource(itemAdapter.getImage());
        ((ViewHolder) viewHolder).mDevName.setText(itemAdapter.getDevName());
        ((ViewHolder) viewHolder).mDevTitle.setText(itemAdapter.getDevTitle());
        ((ViewHolder) viewHolder).mDevDescribe.setText(itemAdapter.getDevDescribe());

        ((ViewHolder) viewHolder).mNoWa.setText(itemAdapter.getNoWa());
        ((ViewHolder) viewHolder).mAkunFb.setText(itemAdapter.getAkunFb());
        ((ViewHolder) viewHolder).mAkunIg.setText(itemAdapter.getAkunIg());
        ((ViewHolder) viewHolder).mAkunTele.setText(itemAdapter.getTelegramName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public FrameLayout mLeadsBase;
        public ImageView mDevImage;
        public TextView mDevName;
        public TextView mDevTitle;
        public TextView mDevDescribe;
        private Dialog customDialog;
        private LinearLayout mWa, mTele, mFb, mIg;

        public TextView mNoWa, mAkunFb, mAkunIg, mAkunTele, hubName;

        private Context mContext;

        public ViewHolder(View itemView) {
            super(itemView);

            mLeadsBase = itemView.findViewById(R.id.leads_base);
            mDevImage = itemView.findViewById(R.id.devImage);
            mDevName = itemView.findViewById(R.id.devName);
            mDevTitle = itemView.findViewById(R.id.devTitle);
            mDevDescribe = itemView.findViewById(R.id.devDescribe);

            mNoWa = itemView.findViewById(R.id.no_wa);
            mAkunTele = itemView.findViewById(R.id.akun_tele);
            mAkunFb = itemView.findViewById(R.id.akun_fb);
            mAkunIg = itemView.findViewById(R.id.akun_ig);

            mContext = itemView.getContext();

            mLeadsBase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initCustomDialog();
                        customDialog.show();
                    }

                    private void initCustomDialog() {
                        customDialog = new Dialog(mContext);
                        customDialog.setContentView(R.layout.leads_dialog);
                        customDialog.setCancelable(true);


                        hubName = customDialog.findViewById(R.id.hub_name);
                        mWa = customDialog.findViewById(R.id.leads_wa);
                        mTele = customDialog.findViewById(R.id.leads_tele);
                        mFb = customDialog.findViewById(R.id.leads_fb);
                        mIg = customDialog.findViewById(R.id.leads_ig);
                        hubName.setText(mDevName.getText());
                        mWa.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                                                   Uri.parse("https://github.com/" + mNoWa.getText()));
                                        mContext.startActivity(intent);
                                    } catch (ActivityNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    customDialog.dismiss();
                                }

                            });
                        mFb.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                                                   Uri.parse("https://www.facebook.com/" + mAkunFb.getText()));
                                        mContext.startActivity(intent);
                                    } catch (ActivityNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    customDialog.dismiss();
                                }
                            });
                        mTele.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                                                   Uri.parse("https://t.me/" + mAkunTele.getText()));
                                        mContext.startActivity(intent);
                                    } catch (ActivityNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    customDialog.dismiss();
                                }
                            });
                        mIg.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                                                   Uri.parse("https://instagram.com/" + mAkunIg.getText()));
                                        mContext.startActivity(intent);
                                    } catch (ActivityNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    customDialog.dismiss();
                                }
                            });
                    }
                });

        }
    }
}
