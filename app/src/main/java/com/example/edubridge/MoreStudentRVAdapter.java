package com.example.edubridge;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MoreStudentRVAdapter extends RecyclerView.Adapter<MoreStudentRVAdapter.ViewHolder> {

    Context context;
    ArrayList<MoreStudentRV> list;

    public MoreStudentRVAdapter(Context context, ArrayList<MoreStudentRV> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.more_students, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        MoreStudentRV user = list.get(position);

        holder.studentName.setText(user.getStudentName());
        holder.studentDescription.setText(user.getDescriptionHome());
        // Convert the raisedAmount and totalAmount strings to integers
        int rasiedAmountInt = Integer.parseInt(user.getRasiedAmount());
        int totalAmountInt = Integer.parseInt(user.getTotalAmount());
        holder.rasiedAmountMore.setText(String.valueOf(rasiedAmountInt));
        holder.totalAmountMore.setText(String.valueOf(totalAmountInt));

        // Load the image into the ImageView using Glide
        Glide.with(context)
                .load(user.getImgHome())
                .into(holder.studentImg);

        // total amount == raised amount then change color and text of button and also disable
        int currentAmount = Integer.parseInt(list.get(position).getRasiedAmount());
        int totalAmount = Integer.parseInt(list.get(position).getTotalAmount());
        int progress1 = (int) ((float) currentAmount / (float) totalAmount * 100);
        holder.progressBar1.setProgress(progress1);
        if(currentAmount >= totalAmount){
//            Toast.makeText(context, "Donation is not possible, amount already raised fully", Toast.LENGTH_SHORT).show();
            holder.donate.setText("Done");
            holder.donate.setEnabled(false);
        }
        // when click on donate button
        holder.donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.rasiedAmountMore.getContext())
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.donate_dialog))
                        .setExpanded(true, 1100)
                        .create();

                View bottomSheetView = dialogPlus.getHolderView();
                TextView donateEditText = bottomSheetView.findViewById(R.id.donateEditText);
                Button pay_btn = bottomSheetView.findViewById(R.id.pay_btn);

                // total amount == raised amount then change color and text of button and also disable
                int currentAmount = Integer.parseInt(list.get(position).getRasiedAmount());
                int totalAmount = Integer.parseInt(list.get(position).getTotalAmount());
                String studentName = user.getStudentName();
                if(currentAmount >= totalAmount){
                    Toast.makeText(context, "Donation is not possible, amount already raised fully", Toast.LENGTH_SHORT).show();
                    holder.donate.setText("Done");
                    holder.donate.setEnabled(false);
                }
                else {
                    dialogPlus.show();
                }

                // adding data to activity database for fetching activity fragment
                DatabaseReference activityRef = FirebaseDatabase.getInstance().getReference("userActivity");
                FirebaseAuth auth = FirebaseAuth.getInstance();
                DataSnapshot dataSnapshot = null;
                // get current time
                DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                String date = df.format(Calendar.getInstance().getTime());

                // when click on pay button
                Intent intent = new Intent(v.getContext(), SuccessfulTransaction.class);
                pay_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            // Firebase code that might cause a crash
                            FirebaseApp.initializeApp(donateEditText.getContext());
                            // Other Firebase code that depends on initialization
                        } catch (Exception e) {
                            // Handle the exception gracefully
                            Toast.makeText(pay_btn.getContext(), "App crash firebase not open", Toast.LENGTH_SHORT).show();
                        }

                        String donatedAmount = donateEditText.getText().toString();
                        int currentAmount = Integer.parseInt(list.get(position).getRasiedAmount());
                        int totalAmount = Integer.parseInt(list.get(position).getTotalAmount());


                        if(totalAmount <= currentAmount){
                            Toast.makeText(pay_btn.getContext(), "Donation Done for Student", Toast.LENGTH_SHORT).show();
                        }
                        else if (Integer.parseInt(donatedAmount) > totalAmount - currentAmount) {
                            Toast.makeText(pay_btn.getContext(), "Donation amount cannot be greater than the remaining amount", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            try {
                                // Calculate the new raised amount by adding the donated amount to the current amount
                                int newRaisedAmount = currentAmount + Integer.parseInt(donatedAmount);
                                // Update the database with the new raised amount
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("students").child(String.valueOf(position)).child("rasiedAmount");

                                databaseReference.setValue(String.valueOf(newRaisedAmount)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        activityRef.child(auth.getUid()).push().setValue(donatedAmount+"$   on "+date+" to "+studentName) // sending to userActivity database
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(context, "Donation added successfully", Toast.LENGTH_SHORT).show();
                                                        dialogPlus.dismiss();
                                                        v.getContext().startActivity(intent);
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.e("Firebase", "Error adding donation", e);
                                                    }
                                                });
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Failed to add donation", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (NumberFormatException e) {
                                Toast.makeText(pay_btn.getContext(), "Enter Valid Number", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentName, studentDescription, rasiedAmountMore, totalAmountMore;
        ImageView studentImg;
        Button donate;
        ProgressBar progressBar1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.studentName);
            studentImg = itemView.findViewById(R.id.studentImg);
            studentDescription = itemView.findViewById(R.id.studentDescription);
            rasiedAmountMore = itemView.findViewById(R.id.rasiedAmountMore);
            totalAmountMore = itemView.findViewById(R.id.totalAmountMore);
            donate = itemView.findViewById(R.id.donate);
            progressBar1 = itemView.findViewById(R.id.progressBar1);
        }
    }
}
