package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.fragments.MyModalFragment;
import com.example.myapplication.models.Model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RVAdapter adapter = new RVAdapter();
        recyclerView = findViewById(R.id.recyclerView_main_items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Model> models = new ArrayList<>();
        models.add(new Model("Abcd1", "Abcd1@gmail.com"));
        models.add(new Model("Abcd2", "Abcd2@gmail.com"));
        models.add(new Model("Abcd3", "Abcd3@gmail.com"));
        models.add(new Model("Abcd1", "Abcd1@gmail.com"));
        models.add(new Model("Abcd2", "Abcd2@gmail.com"));
        models.add(new Model("Abcd3", "Abcd3@gmail.com"));
        models.add(new Model("Abcd1", "Abcd1@gmail.com"));
        models.add(new Model("Abcd2", "Abcd2@gmail.com"));
        models.add(new Model("Abcd3", "Abcd3@gmail.com"));
        models.add(new Model("Abcd1", "Abcd1@gmail.com"));
        models.add(new Model("Abcd2", "Abcd2@gmail.com"));
        models.add(new Model("Abcd3", "Abcd3@gmail.com"));

        adapter.setModels(models);

        final FloatingActionButton fab = findViewById(R.id.floatingActionButton_main_next);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
            return new MyViewHolder(row);
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
        }
    }
}
