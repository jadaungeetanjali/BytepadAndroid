package in.silive.bo.Activities;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import in.silive.bo.Adapters.PapersListAdapter;
import in.silive.bo.Application.BytepadApplication;
import in.silive.bo.PrefManager;
import in.silive.bo.R;
import in.silive.bo.SnackBarListener;
import in.silive.bo.database.RoomDb;
import in.silive.bo.util.Mapping;
import in.silive.bo.util.PaperDetails;
import in.silive.bo.viewmodel.BytepadAndroidViewModel;

/**
 * Created by root on 9/11/17.
 */

public class DownloadActivity extends AppCompatActivity implements SnackBarListener {
    public static List<PaperDetails> paperList = new ArrayList<>();
    public AutoCompleteTextView search_paper;
    public RecyclerView recyclerView;
    public TabLayout tabLayout;
    public CoordinatorLayout coordinatorLayout;
    PapersListAdapter papersListAdapter;
    String query = "%";
    ToggleButton all,st1,st2,put,ut;
    int paperType[];
    Toolbar toolbar;
    ImageView downloadbutton;
    ImageView ivClearSearch;
    RelativeLayout recyclerEmptyView;
//    FlowContentObserver observer;
    PrefManager prefManager;
    Mapping mapping;
    private RoomDb appDatabase;
    int counter=0;
    ArrayList<Integer> paper;
    private BytepadAndroidViewModel viewModel;
    private View st1line;
    View st2line;
    View putline;
    View utline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download);
        BytepadApplication application = (BytepadApplication)getApplication();
        //     Tracker mTracker = application.getDefaultTracker();
//        mTracker.setScreenName("MainActivity");
        //       mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mapping=new Mapping();
        prefManager = new PrefManager(this);
        //   this.observer = new FlowContentObserver();
        //  this.observer.registerForContentChanges(this, PaperDatabaseModel.class);

        //this.observer.addModelChangeListener(this);
        paper=new ArrayList<>();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  downloadbutton=(ImageView)findViewById(R.id.donloadbutton);
        //all=(ToggleButton)findViewById(R.id.all);

        st1=(ToggleButton)findViewById(R.id.St1);
        st2=(ToggleButton)findViewById(R.id.st2);
        put=(ToggleButton)findViewById(R.id.put);
        ut=(ToggleButton)findViewById(R.id.ut);

        downloadbutton=(ImageView)findViewById(R.id.downloadbutton);
        appDatabase = RoomDb.getDatabase(this);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        Log.d("Bytepad", "MainActivity created");
        search_paper = (AutoCompleteTextView) findViewById(R.id.search_paper);
        Log.d("Bytepad", "Search bar added");
        //tabLayout = (TabLayout) findViewById(R.id.tabview);
        Log.d("Bytepad", "Tab Layout added");
        recyclerView = (RecyclerView) findViewById(R.id.rview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        query = "";
        ivClearSearch = (ImageView) findViewById(R.id.ivClearSearch);
        recyclerEmptyView = (RelativeLayout) findViewById(R.id.recyclerEmptyView);
        search_paper.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                query = editable.toString();

                initialise();
            }
        });

        initialise();

        ivClearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_paper.setText("");
            }
        });


        /*all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((all.isChecked())) {
                    all.setTextColor(Color.WHITE);
                    paper.add(1);
                    paper.add(2);
                    paper.add(3);
                    paper.add(4);
                    setUpList(query);
                }
                else {
                    all.setTextColor(Color.BLACK);
                    Iterator itr = paper.iterator();
                    while(itr.hasNext()){
                        Object e = itr.next();
                        if(counter<4)
                            itr.remove();
                        counter++;
                    }

                    setUpList(query);
                }


            }
        });*/


        /*all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((all.isChecked())) {
                    all.setTextColor(Color.WHITE);
                    paper.add(1);
                    paper.add(2);
                    paper.add(3);
                    paper.add(4);
                    setUpList(query);
                }
                else {
                    all.setTextColor(Color.BLACK);
                    Iterator itr = paper.iterator();
                    while(itr.hasNext()){
                        Object e = itr.next();
                        if(counter<4)
                            itr.remove();
                        counter++;
                    }

                    setUpList(query);
                }


            }
        });*/

        st1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if((st1.isChecked())) {

                    st1.setTextColor(Color.parseColor("#FFFFFF"));
                    st1.setBackgroundResource(R.drawable.backtoggleselected);
                    //st1line.setVisibility(View.GONE);
                    paper.add(3);
                    setUpList(query);
                }
                else
                {   st1.setTextColor(Color.BLACK);
                    st1.setBackgroundResource(R.drawable.backtoggle);
                    //st1line.setVisibility(View.GONE);
                    Iterator itr = paper.iterator();
                    while(itr.hasNext()){
                        if(itr.next().equals(3))
                            itr.remove();
                    }
                    //    paper.remove(3);


                    setUpList(query);
                }

            }
        });

        st2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(st2.isChecked()) {
                    st2.setTextColor(Color.parseColor("#FFFFFF"));
                    st2.setBackgroundResource(R.drawable.backtoggleselected);
                    //st2line.setVisibility(View.VISIBLE);
                    paper.add(4);
                    setUpList(query);
                }
                else
                {   st2.setTextColor(Color.BLACK);
                    st2.setBackgroundResource(R.drawable.backtoggle);
                    //            st2line.setVisibility(View.GONE);
                    Iterator itr = paper.iterator();
                    while(itr.hasNext()){
                        if(itr.next().equals(4))
                            itr.remove();
                    }
                    // paper.remove(4);
                    setUpList(query);
                }

            }
        });


        put.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                if(put.isChecked()) {
                    put.setTextColor(Color.parseColor("#FFFFFF"));
                    put.setBackgroundResource(R.drawable.backtoggleselected);
                    // putline.setVisibility(View.VISIBLE);
                    paper.add(2);
                    setUpList(query);
                }
                else {
                    put.setTextColor(Color.BLACK);
                    put.setBackgroundResource(R.drawable.backtoggle);
                    //putline.setVisibility(View.GONE);
                    Iterator itr = paper.iterator();
                    while(itr.hasNext()){
                        if(itr.next().equals(2))
                            itr.remove();
                    }
                    // paper.remove(2);
                    setUpList(query);
                }
            }
        });


        ut.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if(ut.isChecked()) {
                    ut.setTextColor(Color.parseColor("#FFFFFF"));
                    ut.setBackgroundResource(R.drawable.backtoggleselected);
                    //utline.setVisibility(View.VISIBLE);
                    paper.add(1);
                    setUpList(query);
                }
                else
                { ut.setTextColor(Color.BLACK);
                    ut.setBackgroundResource(R.drawable.backtoggle);
                    //   utline.setVisibility(View.GONE);
                    Iterator itr = paper.iterator();
                    while(itr.hasNext()){
                        if(itr.next().equals(1))
                            itr.remove();
                    }
                    //paper.remove(1);
                    setUpList(query);
                }
            }
        });
        downloadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               backpress();
            }
        });








        //tabLayout.addTab(tabLayout.newTab().setText("ALL"), 0);
        //tabLayout.addTab(tabLayout.newTab().setText("ST1"), 1);
        //tabLayout.addTab(tabLayout.newTab().setText("ST2"),2);
        //tabLayout.addTab(tabLayout.newTab().setText("PUT"), 3);
        //tabLayout.addTab(tabLayout.newTab().setText("UT"), 4);
        //tabLayout.addTab(tabLayout.newTab().setText("SAVED"), 5);
        /*tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int i = tab.getPosition();
                switch (i) {
                    case 0:
                        paperType = 0;
                        break;
                    case 1:
                        paperType = 3;
                        break;

                    case 2:
                        paperType = 2;
                        break;
                    case 3:
                        paperType = 1;
                        break;
                    case 4:
                        paperType = 5;
                        break;
                    case 5:
                        paperType=4;
                        break;

                }
                setUpList(paperType);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (!CheckConnectivity.isNetConnected(this)) {
            TabLayout.Tab tab = tabLayout.getTabAt(5);
            tab.select();
        }
        search_paper.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                query = editable.toString();

                //setUpList(query);
            }
        });

        ivClearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_paper.setText("");
            }
        });*/
        //setUpList(query);

    }

    @Override
    protected void onStop() {
//        observer.unregisterForContentChanges(this);
        super.onStop();
    }

    public void setUpList(String query) {
  //      SQLCondition secondCondition;
        //if (paperType==5)

        //  paperList=appDatabase.itemAndPersonModel().setval(true);
        //else
        paperType=new int[paper.size()];
        for(int i=0;i<paper.size();i++)
        {
            paperType[i]=paper.get(i);
            Log.d("debugg",Integer.toString(paperType[i]));
        }
        paperList = appDatabase.itemAndPersonModel().setPaperType(paperType,query,true);
        papersListAdapter = new PapersListAdapter(this, paperList);

        viewModel = ViewModelProviders.of(this).get(BytepadAndroidViewModel.class);
        recyclerView.setAdapter(papersListAdapter);

        viewModel.getAllBorrowedItems().observe(DownloadActivity.this, new Observer<List<PaperDetails>>() {
            @Override
            public void onChanged(@Nullable List<PaperDetails> PaperDatabaseModel) {
                recyclerView.setAdapter(papersListAdapter);
                //papersListAdapter.addItems(PaperDatabaseModel);

            }


        });
        recyclerView.setAdapter(papersListAdapter);
        if (paperList.size() != 0) {
            recyclerEmptyView.setVisibility(View.GONE);
        } else {
            recyclerEmptyView.setVisibility(View.VISIBLE);
        }

    }
    void initialise()
    {
        put.setChecked(true);
        st1.setChecked(true);
       // st1.setTextColor(Color.WHITE);
        st2.setChecked(true);
        //st2.setTextColor(Color.WHITE);

        ut.setChecked(true);
        //ut.setTextColor(Color.WHITE);
        if(put.isChecked()) {
            put.setTextColor(Color.parseColor("#FFFFFF"));
            put.setBackgroundResource(R.drawable.backtoggleselected);
            //putline.setVisibility(View.VISIBLE);
            paper.add(2);
            setUpList(query);
        }
        else {
            put.setTextColor(Color.BLACK);
            put.setBackgroundResource(R.drawable.backtoggle);
            put.setVisibility(View.GONE);
            Iterator itr = paper.iterator();
            while(itr.hasNext()){
                if(itr.next().equals(2))
                    itr.remove();
            }
            // paper.remove(2);
            setUpList(query);
        }
        if((st1.isChecked())) {

            st1.setTextColor(Color.parseColor("#FFFFFF"));
            st1.setBackgroundResource(R.drawable.backtoggleselected);
            //st1line.setVisibility(View.GONE);
            paper.add(3);
            setUpList(query);
        }
        else
        {   st1.setTextColor(Color.BLACK);
            st1.setBackgroundResource(R.drawable.backtoggle);
            //st1line.setVisibility(View.GONE);
            Iterator itr = paper.iterator();
            while(itr.hasNext()){
                if(itr.next().equals(3))
                    itr.remove();
            }
            //    paper.remove(3);


            setUpList(query);
        }
        if(st2.isChecked()) {
            st2.setTextColor(Color.parseColor("#FFFFFF"));
            st2.setBackgroundResource(R.drawable.backtoggleselected);
            //st2line.setVisibility(View.VISIBLE);
            paper.add(4);
            setUpList(query);
        }
        else
        {   st2.setTextColor(Color.BLACK);
            st2.setBackgroundResource(R.drawable.backtoggle);
            //            st2line.setVisibility(View.GONE);
            Iterator itr = paper.iterator();
            while(itr.hasNext()){
                if(itr.next().equals(4))
                    itr.remove();
            }
            // paper.remove(4);
            setUpList(query);
        }
        if(ut.isChecked()) {
            ut.setTextColor(Color.parseColor("#FFFFFF"));
            ut.setBackgroundResource(R.drawable.backtoggleselected);
            //utline.setVisibility(View.VISIBLE);
            paper.add(1);
            setUpList(query);
        }
        else
        { ut.setTextColor(Color.BLACK);
            ut.setBackgroundResource(R.drawable.backtoggle);
            //   utline.setVisibility(View.GONE);
            Iterator itr = paper.iterator();
            while(itr.hasNext()){
                if(itr.next().equals(1))
                    itr.remove();
            }
            //paper.remove(1);
            setUpList(query);
        }
    }
    @Override
    public CoordinatorLayout getCoordinatorLayout() {
        return this.coordinatorLayout;
    }

    /*@Override
    public void onModelStateChanged(@Nullable Class<? extends Model> table, BaseModel.Action action, @NonNull SQLCondition[] primaryKeyValues) {
        if (action.equals(BaseModel.Action.UPDATE)) {
            for (SQLCondition cond : primaryKeyValues) {
                if (cond.columnName().contains("id")) {
                    updateModelView(Integer.parseInt(cond.value().toString()));
                }
            }
        }
    }
    private void updateModelView(int id) {
        for (int i = 0; i < papersListAdapter.getItemCount(); i++) {
            if (id == papersListAdapter.getPapersList().get(i).id) {
                final int finalI = i;
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        papersListAdapter.notifyItemChanged(finalI);
                    }
                });
                break;
            }
        }
    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backpress();

    }
    void backpress()
    {
        Intent i=new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        finish();
    }
}
