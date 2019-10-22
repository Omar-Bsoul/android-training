package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.models.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ActivityPages extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pages);

        ViewPager2 viewPager2 = findViewById(R.id.viewPager2_pages_intro);
        viewPager2.setAdapter(new VPAdapter());
    }

    private static class VPAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private List<Integer> pages;

        VPAdapter() {
            pages = new ArrayList<>();
            pages.addAll(Arrays.asList(1, 2, 3));
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.page, parent, false);
            return new MyViewHolder(row);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            int page = pages.get(position);
            holder.bind(page);
        }

        @Override
        public int getItemCount() {
            return pages.size();
        }
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView pageNum;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            pageNum = itemView.findViewById(R.id.textView_pages_pageNum);
        }

        public void bind(int page) {
            pageNum.setText("Page " + page);
        }
    }
}























/*
class Ad extends BaseAdapter{

    Activity context ;
    String [] titles ;
    String [] imgs;
    Ad(Activity context , String []title ,String [] imgs  ){
        this.titles = title ;
        this.context = context ;
        this.imgs = imgs;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inf = context.getLayoutInflater();
        View row = inf.inflate(R.layout.row , null , false);

        TextView ti = row.findViewById(R.id.textView2);
        TextView img  = row.findViewById(R.id.textView3);

        ti.setText(titles[position]);

        img.setText(imgs[position]);
        return row;
    }
}*/
