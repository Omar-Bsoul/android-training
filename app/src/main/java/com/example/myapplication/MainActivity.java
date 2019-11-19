package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.fragments.MyModalFragment;
import com.example.myapplication.models.Model;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String SHARED_PREFS_NAME = "SHARED_PREFS_NAME";
    private static final String BOTTOM_SHEET_STATE_KEY = "BOTTOM_SHEET_STATE_KEY";

    private RecyclerView recyclerView;
    private ImageView noItemsImageView;
    private TextView noItemsTextView;

    private BottomSheetBehavior<CardView> bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        int state = preferences.getInt(BOTTOM_SHEET_STATE_KEY, BottomSheetBehavior.STATE_COLLAPSED);

        final RVAdapter adapter = new RVAdapter();
        recyclerView = findViewById(R.id.recyclerView_main_items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noItemsImageView = findViewById(R.id.imageView1);
        noItemsTextView = findViewById(R.id.textView1);

        final List<Model> models = new ArrayList<>();

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout1);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                models.add(new Model("Abcd1", "Abcd1@gmail.com"));
                models.add(new Model("Abcd2", "Abcd2@gmail.com"));
                models.add(new Model("Abcd3", "Abcd3@gmail.com"));

                adapter.setModels(models);

                if (noItemsImageView.getVisibility() == View.VISIBLE) {
                    noItemsImageView.setVisibility(View.GONE);
                    noItemsTextView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } else if (models.size() == 0) {
                    noItemsImageView.setVisibility(View.VISIBLE);
                    noItemsTextView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        adapter.setModels(models);

        final CardView bottomSheet = findViewById(R.id.cardView1);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheetBehavior.setState(state);

        final FloatingActionButton fab = findViewById(R.id.floatingActionButton_main_next);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int state) {
                SharedPreferences preferences = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(BOTTOM_SHEET_STATE_KEY, state);
                editor.apply();
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        //HideBottomViewOnScrollBehavior<FloatingActionButton> fabBehavior = ((HideBottomViewOnScrollBehavior<FloatingActionButton>) ((CoordinatorLayout.LayoutParams)fab.getLayoutParams()).getBehavior());
        //fabBehavior. ()

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fab.getVisibility() == View.VISIBLE) {
                    fab.hide();
                } else if (dy < 0 && fab.getVisibility() != View.VISIBLE) {
                    fab.show();
                }
            }
        });
    }

    private static class RVAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private List<Model> models;
        private WeakReference<MainActivity> reference;

        RVAdapter() {
            models = new ArrayList<>();
        }

        RVAdapter(MainActivity activity) {
            reference = new WeakReference<>(activity);
        }

        public void setModels(List<Model> models) {
            this.models = models;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
            MyViewHolder holder = new MyViewHolder(row);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Model model = models.get(position);
            holder.bind(model);
        }

        @Override
        public int getItemCount() {
            return models.size();
        }
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView email;
        ImageView more;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textView_main_name);
            email = itemView.findViewById(R.id.textView_main_email);
            more = itemView.findViewById(R.id.imageView_main_moreOptions);
        }

        public void bind(Model model) {
            name.setText(model.getName());
            email.setText(model.getEmail());

            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyModalFragment myModalFragment = new MyModalFragment();
                    myModalFragment.show(((AppCompatActivity) v.getContext()).getSupportFragmentManager(), null);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(v.getContext(), App.NOTIFICATION_CHANNEL_1)
                            .setSmallIcon(R.drawable.ic_person_outline_black_24dp)
                            .setContentTitle("New Notification")
                            .setContentText("This is my first notification!");

                    NotificationManagerCompat.from(v.getContext()).notify(getAdapterPosition(), builder.build());
                }
            });
        }
    }
}
