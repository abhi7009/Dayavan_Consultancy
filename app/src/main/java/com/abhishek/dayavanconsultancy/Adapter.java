package com.abhishek.dayavanconsultancy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private Context context;
        private List<Model> items;

        public Adapter(Context context, List<Model> items) {
            this.context = context;
            this.items = items;
        }

        public Adapter() {
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_view, parent, false));
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.vehicle_no.setText(this.items.get(position).getVehicle_no());
            holder.vehicle_id.setText(this.items.get(position).getId());
        }

        public int getItemCount() {
            return this.items.size();
        }

        public void FilterList(Context context,ArrayList<Model> filteredList) {
            items = filteredList;
            notifyDataSetChanged();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView vehicle_no;
            TextView vehicle_id;

            public ViewHolder(View itemView) {
                super(itemView);
                vehicle_no = (TextView) itemView.findViewById(R.id.vehicle_no);
                vehicle_id = (TextView) itemView.findViewById(R.id.vehicle_id);
            }
        }

    }
