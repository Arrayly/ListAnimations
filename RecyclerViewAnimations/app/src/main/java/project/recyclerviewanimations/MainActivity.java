package project.recyclerviewanimations;

import android.content.DialogInterface;
import android.os.Handler;
import android.provider.Contacts.People;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private int[] imageArray = new int[7];

    private String loremIpsum;

    private List<Data> mDataList = new ArrayList<>();

    private AdapterListAnimation mAdapter;

    private int animation_type = ItemAnimation.BOTTOM_UP;

    private static final String[] ANIMATION_TYPE = new String[]{
            "Bottom Up", "Fade In", "Left to Right", "Right to Left"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initComponents();

    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cool Animations");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initComponents() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        showSingleChoiceDialog();

    }


    private void showSingleChoiceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Animation Type");
        builder.setCancelable(false);
        builder.setSingleChoiceItems(ANIMATION_TYPE, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String selected = ANIMATION_TYPE[i];
                if (selected.equalsIgnoreCase("Bottom Up")) {
                    animation_type = ItemAnimation.BOTTOM_UP;
                } else if (selected.equalsIgnoreCase("Fade In")) {
                    animation_type = ItemAnimation.FADE_IN;
                } else if (selected.equalsIgnoreCase("Left to Right")) {
                    animation_type = ItemAnimation.LEFT_RIGHT;
                } else if (selected.equalsIgnoreCase("Right to Left")) {
                    animation_type = ItemAnimation.RIGHT_LEFT;
                }

                getSupportActionBar().setTitle(selected);
                setAdapter();
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    public void setAdapter() {
        //set data and list adapter
        mAdapter = new AdapterListAnimation(this, mDataList, animation_type);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(mAdapter);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                prepareData();
                mAdapter.showShimmer = false;
                mAdapter.notifyDataSetChanged();
            }
        }, 2500);
    }

    private void prepareData() {
        loremIpsum = getResources().getString(R.string.lorem_ipsum);
        for (int x = 0; x < 5; x++) {
            for (int i = 0; i < 7; i++) {
                imageArray[i] = getResources()
                        .getIdentifier("drawable/" + "image_" + i, null, getPackageName());
                Data data = new Data(imageArray[i], loremIpsum);
                mDataList.add(data);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_animation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_refresh:
                setAdapter();
                break;
            case R.id.action_mode:
                showSingleChoiceDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
