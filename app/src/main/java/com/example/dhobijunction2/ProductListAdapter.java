package com.example.dhobijunction2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    Context context;
    List<ProductModel> productList;

    public ProductListAdapter(Context context, List<ProductModel> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ProductListAdapter.ViewHolder holder, final int position) {
            holder.tv_item.setText(productList.get(position).getTitle());
            holder.tv_item_price.setText(productList.get(position).getPrice());

            Glide.with(context).load(productList.get(position).getImage()).into(holder.imageView);
            holder.total_price.setText("\u20B9 " + productList.get(position).getPrice());
            holder.imgbtn_remove.setOnClickListener(view -> {
                int count = Integer.parseInt(String.valueOf(holder.total_items.getText()));
                if (count > 1) {
                    count--;
                    holder.total_items.setText(String.valueOf(count));
                    holder.total_price.setText("\u20B9 " + (count * Integer.parseInt(productList.get(position).getPrice())));
                }
            });
        holder.imgbtn_add.setOnClickListener(v -> {
            int count = Integer.parseInt(String.valueOf(holder.total_items.getText()));
            count++;
            holder.total_items.setText(String.valueOf(count));
            holder.total_price.setText("\u20B9 " + (count * Integer.parseInt(productList.get(position).getPrice())));
        });

      //  if (position % 2 == 1)
        //   holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.odd));
        //else
          //  holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.even));

        holder.tv_kg.setText("  Per " + productList.get(position).getKgGm());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv_item, tv_item_price, total_items, total_price, tv_kg;
        ImageButton imgbtn_remove, imgbtn_add;
        Button btn_add;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_kg = itemView.findViewById(R.id.pricePerKg);
            imageView = itemView.findViewById(R.id.item_img);
            tv_item = itemView.findViewById(R.id.tv_item);
            tv_item_price = itemView.findViewById(R.id.tv_item_price);
            total_price = itemView.findViewById(R.id.tv_total_price);
            total_items = itemView.findViewById(R.id.total_items);
            imgbtn_remove = itemView.findViewById(R.id.imgbtn_remove);
              imgbtn_add = itemView.findViewById(R.id.imgbtn_add);
            btn_add = itemView.findViewById(R.id.btn_add);
        }
    }

}
