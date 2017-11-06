package in.silive.bo.Activities;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.raizlabs.android.dbflow.runtime.FlowContentObserver;
import com.raizlabs.android.dbflow.sql.language.SQLCondition;

import java.util.ArrayList;
import java.util.List;

import in.silive.bo.Adapters.PapersListAdapter;
import in.silive.bo.Application.BytepadApplication;
import in.silive.bo.Mapping;

import in.silive.bo.PrefManager;
import in.silive.bo.R;
import in.silive.bo.SnackBarListener;
import in.silive.bo.database.RoomDb;
import in.silive.bo.util.PaperDetails;
import in.silive.bo.viewmodel.BytepadAndroidViewModel;

public class MainActivity extends LifecycleActivity implements SnackBarListener {
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
    ImageView ivClearSearch;
    RelativeLayout recyclerEmptyView;
    FlowContentObserver observer;
    PrefManager prefManager;
    Mapping mapping;
    private RoomDb appDatabase;

    ArrayList<Integer> paper;
    private BytepadAndroidViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BytepadApplication application = (BytepadApplication)getApplication();
   //     Tracker mTracker = application.getDefaultTracker();
//        mTracker.setScreenName("MainActivity");
 //       mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mapping=new Mapping(this);
        prefManager = new PrefManager(this);
     //   this.observer = new FlowContentObserver();
      //  this.observer.registerForContentChanges(this, PaperDatabaseModel.class);

        //this.observer.addModelChangeListener(this);
        paper=new ArrayList<>();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        all=(ToggleButton)findViewById(R.id.all);
        st1=(ToggleButton)findViewById(R.id.St1);
        st2=(ToggleButton)findViewById(R.id.st2);
        put=(ToggleButton)findViewById(R.id.put);
        ut=(ToggleButton)findViewById(R.id.ut);
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
        all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    all.setTextColor(Color.WHITE);
                    paper.add(1);
                    paper.add(2);
                    paper.add(3);
                    paper.add(4);
                    setUpList();
                }
                else {
                    paper.remove(1);
                    paper.remove(2);
                    paper.remove(3);
                    paper.remove(4);
                    setUpList();
                }
            }
        });
        st1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    st1.setTextColor(Color.WHITE);
                    paper.add(3);
                    setUpList();
                }
                else
                {
                    paper.remove(3);
                    setUpList();
                }
            }
        });
        st2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    st2.setTextColor(Color.WHITE);
                    paper.add(4);
                    setUpList();
                }
                else
                {
                    paper.remove(4);
                    setUpList();
                }
            }
        });
        put.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    put.setTextColor(Color.WHITE);
                    paper.add(2);
                    setUpList();
                }
                else {
                    paper.remove(2);
                    setUpList();
                }
            }
        });
        ut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    ut.setTextColor(Color.WHITE);
                    paper.add(1);
                    setUpList();
                }
                else
                {
                    paper.remove(1);
                    setUpList();
                }

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
        observer.unregisterForContentChanges(this);
        super.onStop();
    }

  public void setUpList() {
        SQLCondition secondCondition;
        //if (paperType==5)
          //  paperList=appDatabase.itemAndPersonModel().setval(true);
        //else
        for(int i=0;i<paper.size();i++)
        {
            paperType[i]=paper.get(i);
        }
        paperList = appDatabase.itemAndPersonModel().setPaperType(paperType);
        papersListAdapter = new PapersListAdapter(this, paperList);

        viewModel = ViewModelProviders.of(this).get(BytepadAndroidViewModel.class);

        viewModel.getAllBorrowedItems().observe(MainActivity.this, new Observer<List<PaperDetails>>() {
            @Override
            public void onChanged(@Nullable List<PaperDetails> PaperDatabaseModel) {
                papersListAdapter.addItems(PaperDatabaseModel);

            }


        });
        recyclerView.setAdapter(papersListAdapter);
        if (paperList.size() != 0) {
            recyclerEmptyView.setVisibility(View.GONE);
        } else {
            recyclerEmptyView.setVisibility(View.VISIBLE);
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
}