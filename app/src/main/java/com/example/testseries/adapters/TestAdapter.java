package com.example.testseries.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testseries.R;
import com.example.testseries.activities.QuestionAcitvity;
import com.example.testseries.model.Test;
import com.example.testseries.utils.ColorPicker;
import com.example.testseries.utils.IconPicker;

import java.util.List;


public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {
    private Context context;
    private List<Test> Test;

    public TestAdapter(Context context, List<Test> quizzes) {
        this.context = context;
        this.Test = quizzes;
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.test_item, parent, false);
        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.textViewTitle.setText(Test.get(position).getTitle());
        holder.cardContainer.setCardBackgroundColor(Color.parseColor(ColorPicker.getColor()));
        holder.iconView.setImageResource(IconPicker.getIcon());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, Test.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, QuestionAcitvity.class);
                intent.putExtra("DATE", Test.get(position).getTitle());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Test.size();
    }

    public static class TestViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        ImageView iconView;
        CardView cardContainer;

        public TestViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.test_title);
            iconView = itemView.findViewById(R.id.test_icon);
            cardContainer = itemView.findViewById(R.id.cardContainer);
        }
    }
}