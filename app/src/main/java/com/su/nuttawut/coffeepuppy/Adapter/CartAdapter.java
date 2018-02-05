package com.su.nuttawut.coffeepuppy.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.su.nuttawut.coffeepuppy.Data.CartDetail;
import com.su.nuttawut.coffeepuppy.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<CartDetail> cartDetails;
    private Context context;

    public CartAdapter(List<CartDetail> cartDetails, Context context) {
        this.cartDetails = cartDetails;
        this.context = context;
    }

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        return new CartAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .activity_cart_adapter, parent, false));

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CartDetail detail = cartDetails.get(position);

        holder.txtFood.setText(detail.getFood_name());
        holder.txtPrice.setText(String.valueOf((int)detail.getFood_price()*detail.getFood_count()));
        holder.txtCount.setText(String.valueOf(detail.getFood_count()));
    }


    @Override
    public int getItemCount() {
        return cartDetails.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtFood, txtPrice, txtCount;



        public ViewHolder(View itemView) {
            super(itemView);


            txtFood = itemView.findViewById(R.id.f_food_name);
            txtCount = itemView.findViewById(R.id.f_food_num);
            txtPrice = itemView.findViewById(R.id.f_food_price);




        }


    }
}