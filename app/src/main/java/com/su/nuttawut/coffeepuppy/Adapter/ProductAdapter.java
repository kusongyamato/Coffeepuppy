package com.su.nuttawut.coffeepuppy.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.su.nuttawut.coffeepuppy.Data.Product;
import com.su.nuttawut.coffeepuppy.R;

import java.util.List;

/**
 * Created by chainrongkst on 18/1/2018 AD.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    private Context mCtx;
    private List<Product> productList;

    //code ส่วนที่ทำให้ recicleview คลิกได้
    private static OnItemClickListener mLisener;

    public  interface  OnItemClickListener{
        void onItemClick(int position);
    }

    public static void setOnItemClickListener(OnItemClickListener listener){
        mLisener = listener;
    }


    public ProductAdapter(Context mCtx, List<Product> productList, Response.Listener<String> listener) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.product_list, null);


        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, final int position) {
        final Product product = productList.get(position);

        Glide.with(mCtx)
                .load(product.getPicture())
                .into(holder.imageView);

        holder.textViewProductName.setText(product.getProductName());
        holder.textViewPrice.setText(String.valueOf((int)product.getPrice() + " ฿"));


////        ส่วนของโค้ดใหม่
//        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mCtx, " you click " + product.getProductName(), Toast.LENGTH_LONG).show();
//
//
//
//            }
//        });



    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        //code ส่วนที่ทำให้ recicleview คลิกได้
        // public RelativeLayout relativeLayout;


        public TextView textViewProductName, textViewPrice;
        public ImageView imageView;


        public ProductViewHolder(View itemView) {
            super(itemView);


            textViewProductName = itemView.findViewById(R.id.txtProductName);
            textViewPrice = itemView.findViewById(R.id.txtPrice);
            imageView = itemView.findViewById(R.id.imageView);


            //code ส่วนที่ทำให้ recicleview คลิกได้
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mLisener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            mLisener.onItemClick(position);
                        }
                    }
                }
            });


        }


    }
}
