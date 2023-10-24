package com.vo1d.projetws;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.vo1d.projetws.beans.Etudiant;

import java.util.Collection;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private List<Etudiant> students;

    public StudentAdapter(List<Etudiant> students) {
        this.students = students;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Etudiant student = students.get(position);
        holder.bind(student);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public void clear() {
        students.clear();
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    public void addAll(Collection<Etudiant> newStudents) {
        students.addAll(newStudents);
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }

    // Implement the getItem method to retrieve an item by position

    public Etudiant getItem(int position) {
        if (position >= 0 && position < students.size()) {
            return students.get(position);
        }
        return null;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nomTextView;
        private TextView prenomTextView;
        private TextView sexeTextView;
        private TextView villeTextView;

        ViewHolder(View itemView) {
            super(itemView);
            nomTextView = itemView.findViewById(R.id.nomTextView);
            prenomTextView = itemView.findViewById(R.id.prenomTextView);
            sexeTextView = itemView.findViewById(R.id.sexeTextView);
            villeTextView = itemView.findViewById(R.id.villeTextView);
        }

        void bind(Etudiant student) {
            nomTextView.setText(student.getNom());
            prenomTextView.setText(student.getPrenom());
            sexeTextView.setText(student.getSexe());
            villeTextView.setText(student.getVille());
        }
    }
}