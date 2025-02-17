package com.example.testseries.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testseries.R;
import com.example.testseries.model.Question;

import java.util.Arrays;
import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {

    private final Context context;
    private final List<String> options;
    private final Question question;  // Question object passed in constructor

    public OptionAdapter(Context context, Question question) {
        this.context = context;
        this.options = Arrays.asList(
                question.getOption1(),
                question.getOption2(),
                question.getOption3(),
                question.getOption4()
        );
        this.question = question;  // Use the same Question object passed to the constructor
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.option_item, parent, false);
        return new OptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.optionView.setText(options.get(position));

        // Reset the background to default
        holder.itemView.setBackgroundResource(R.drawable.option_item_bg);

        // Check if this option is selected
        if (options.get(position).equals(question.getUserAnswer())) {
            holder.itemView.setBackgroundResource(R.drawable.option_item_selected_bg);
        }

        // Set click listener once
        holder.itemView.setOnClickListener(v -> {
            question.setUserAnswer(options.get(position));  // Update userAnswer
            notifyDataSetChanged();  // Refresh the view
        });
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    static class OptionViewHolder extends RecyclerView.ViewHolder {
        TextView optionView;

        public OptionViewHolder(@NonNull View itemView) {
            super(itemView);
            optionView = itemView.findViewById(R.id.quiz_option);
        }
    }
}
