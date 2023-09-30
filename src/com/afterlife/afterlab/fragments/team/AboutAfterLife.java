package com.afterlife.afterlab.fragments.team;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settings.R;
import java.util.ArrayList;
import java.util.List;


public class AboutAfterLife extends Activity {

    private List<DevInfoAdapter> mList = new ArrayList<>();
    private List<DevInfoAdapter> mList2 = new ArrayList<>();
    private List<DevInfoAdapter> mList3 = new ArrayList<>();
    private LinearLayout mAlGithub, mAlTele;
    private TextView mTmore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_recyclerview);

        initTeam();

        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                         WindowManager.LayoutParams.WRAP_CONTENT);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        window.getDecorView().setSystemUiVisibility(uiOptions);
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    private void initTeam() {
        RecyclerView mRecycleview = findViewById(R.id.listView);
        RecyclerView mRecycleview2 = findViewById(R.id.listView2);
        RecyclerView mRecycleview3 = findViewById(R.id.listView3);
        mAlGithub = findViewById(R.id.al_github);
        mAlTele = findViewById(R.id.al_telegram);
        mTmore = findViewById(R.id.text_more);
        LinearLayoutManager HorizontalLayout;
        LinearLayoutManager HorizontalLayout2;
        LinearLayoutManager HorizontalLayout3;

        mTmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAlGithub.getVisibility() == View.GONE) {
                        mAlGithub.setVisibility(View.VISIBLE);
                        mAlTele.setVisibility(View.VISIBLE);
                        mTmore.setText("hide");
                    } else {
                        mAlGithub.setVisibility(View.GONE);
                        mAlTele.setVisibility(View.GONE);
                        mTmore.setText(R.string.more);
                    }

                }
            });


        mAlGithub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                                   Uri.parse("https://github.com/AfterLifePrjkt13/"));
                        AboutAfterLife.this.startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });

        mAlTele.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                                   Uri.parse("https://t.me/AfterLifePrjkt"));
                        AboutAfterLife.this.startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });


        mRecycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (dy > 0) {
                        // Scrolling up
                        TextView tv = findViewById(R.id.title_dev);
                        tv.setVisibility(View.GONE);
                    } else {
                        // Scrolling down
                    }
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);

                    if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                        // Do something
                        TextView tv = findViewById(R.id.title_dev);
                        tv.setVisibility(View.VISIBLE);
                    } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                        // Do something
                        TextView tv = findViewById(R.id.title_dev);
                        //tv.setVisibility(View.GONE);
                    } else {
                        // Do something
                    }
                }
            });
        setLeadsMember(getString(R.string.afl_team_1), //devName
                       getString(R.string.founder), //devTitle
                       getString(R.string.afl_team1_description), // devDescribe
                       getString(R.string.afl_team1_no_wa), // nowa
                       getString(R.string.afl_team1_fb), // fb
                       getString(R.string.afl_team1_ig), // ig
                       getString(R.string.afl_team1_tele), // tele
                       R.drawable.ava_romeo); // img
        setLeadsMember(getString(R.string.afl_team_2), //devName
                       getString(R.string.coli), //devTitle
                       getString(R.string.afl_team2_description), // devDescribe
                       getString(R.string.afl_team1_no_wa), // nowa
                       getString(R.string.afl_team1_fb), // fb
                       getString(R.string.afl_team1_ig), // ig
                       getString(R.string.afl_team1_tele), // tele
                       R.drawable.ava_ari); // img
        setLeadsMember(getString(R.string.afl_team_3), //devName
                       getString(R.string.coli_design), //devTitle
                       getString(R.string.afl_team3_description), // devDescribe
                       getString(R.string.afl_team3_no_wa), // nowa
                       getString(R.string.afl_team3_fb), // fb
                       getString(R.string.afl_team3_ig), // ig
                       getString(R.string.afl_team3_tele), // tele
                       R.drawable.ava_dotkit); // img  


        setDesignerMember(getString(R.string.afl_designer_1), //nama
                          getString(R.string.afl_designer_description),//jabatan
                          getString(R.string.afl_github1), //git uname
                          getString(R.string.afl_tele1), //tele uname
                          R.drawable.ava_designer1); // avatar
        setDesignerMember(getString(R.string.afl_designer_2), //nama
                          getString(R.string.afl_designer_description),//jabatan
                          getString(R.string.afl_github2), //git uname
                          getString(R.string.afl_tele2), //tele uname
                          R.drawable.ava_designer2); // avatar
        setDesignerMember(getString(R.string.afl_designer_3), //nama
                          getString(R.string.afl_designer_description),//jabatan
                          getString(R.string.afl_github3), //git uname
                          getString(R.string.afl_tele3), //tele uname
                          R.drawable.ava_designer3); // avatar
        setDesignerMember(getString(R.string.afl_designer_4), //nama
                          getString(R.string.afl_designer_description),//jabatan
                          getString(R.string.afl_github4), //git uname
                          getString(R.string.afl_tele4), //tele uname
                          R.drawable.ava_designer4); // avatar
        setDesignerMember(getString(R.string.afl_designer_5), //nama
                          getString(R.string.afl_designer_description),//jabatan
                          getString(R.string.afl_github5), //git uname
                          getString(R.string.afl_tele5), //tele uname
                          R.drawable.ava_designer5); // avatar
        setDesignerMember(getString(R.string.afl_designer_6), //nama
                          getString(R.string.afl_designer_description),//jabatan
                          getString(R.string.afl_github6), //git uname
                          getString(R.string.afl_tele6), //tele uname
                          R.drawable.ava_designer6); // avatar
        setDesignerMember(getString(R.string.afl_designer_7), //nama
                          getString(R.string.afl_designer_description),//jabatan
                          getString(R.string.afl_github7), //git uname
                          getString(R.string.afl_tele7), //tele uname
                          R.drawable.ava_designer7); // avatar


        setOtherMember(getString(R.string.afl_contri_1), //nama
                       getString(R.string.afl_contri_description),//jabatan
                       getString(R.string.afl_contri_github), //git uname
                       getString(R.string.afl_contri_tele), //tele uname
                       R.drawable.ava_contri1); // avatar


        LeadsAdapter mAdapter = new LeadsAdapter(mList);
        ListAdapter mAdapter2 = new ListAdapter(mList2);
        ListAdapter mAdapter3 = new ListAdapter(mList3);
        mRecycleview.setAdapter(mAdapter);
        mRecycleview2.setAdapter(mAdapter2);
        mRecycleview3.setAdapter(mAdapter3);
        //mRecycleview.setLayoutManager(new LinearLayoutManager(this));
        HorizontalLayout
            = new LinearLayoutManager(
            AboutAfterLife.this,
            LinearLayoutManager.HORIZONTAL,
            false);
        HorizontalLayout2
            = new LinearLayoutManager(
            AboutAfterLife.this,
            LinearLayoutManager.HORIZONTAL,
            false);
        HorizontalLayout3
            = new LinearLayoutManager(
            AboutAfterLife.this,
            LinearLayoutManager.HORIZONTAL,
            false);
        mRecycleview.setLayoutManager(HorizontalLayout);
        mRecycleview2.setLayoutManager(HorizontalLayout2);
        mRecycleview3.setLayoutManager(HorizontalLayout3);
        mAdapter.notifyDataSetChanged();
        mAdapter2.notifyDataSetChanged();
        mAdapter3.notifyDataSetChanged();
    }

    private void setLeadsMember(String devName, String devTitle, String devDescribe, 
                                String noWa, String fbLink, String igLink, String teleLink,
                                int devImage) {
        DevInfoAdapter adapter;

        adapter = new DevInfoAdapter();
        adapter.setImage(devImage);
        adapter.setDevName(devName);
        adapter.setDevTitle(devTitle);
        adapter.setDevDescribe(devDescribe);
        adapter.setNoWa(noWa);
        adapter.setAkunFb(fbLink);
        adapter.setAkunIg(igLink);
        adapter.setTelegramName(teleLink);
        mList.add(adapter);
    }
    private void setDesignerMember(String devName, String devTitle,
                                   String githubLink, String telegram, int devImage) {
        DevInfoAdapter adapter;

        adapter = new DevInfoAdapter();
        adapter.setImage(devImage);
        adapter.setDevName(devName);
        adapter.setDevTitle(devTitle);
        adapter.setGithubName(githubLink);
        adapter.setTelegramName(telegram);
        mList2.add(adapter);
    }
    private void setOtherMember(String devName, String devTitle,
                                String githubLink, String telegram, int devImage) {
        DevInfoAdapter adapter;

        adapter = new DevInfoAdapter();
        adapter.setImage(devImage);
        adapter.setDevName(devName);
        adapter.setDevTitle(devTitle);
        adapter.setGithubName(githubLink);
        adapter.setTelegramName(telegram);
        mList3.add(adapter);
    }
}
