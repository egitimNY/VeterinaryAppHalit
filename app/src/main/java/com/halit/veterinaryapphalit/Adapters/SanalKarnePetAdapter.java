package com.halit.veterinaryapphalit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.halit.veterinaryapphalit.Models.PetModel;
import com.halit.veterinaryapphalit.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SanalKarnePetAdapter extends RecyclerView.Adapter<SanalKarnePetAdapter.ViewHolder>{

    List<PetModel> list;
    Context context;

    public SanalKarnePetAdapter(List<PetModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sanal_karne_pet_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.sanalKarnePetText.setText(list.get(position).getPetisim().toString());
        holder.sanalKarneBilgiText.setText(list.get(position).getPetisim().toString()+" isimli "+
                list.get(position).getPettur()+" türünün "+list.get(position).getPetcins()+" cinsine ait petinizin gecmis " +
                "asilarini görmek için tiklayiniz..." );

//        Picasso.get().load(list.get(position).getPetresim()).into(holder.petLayoutPetImage);
        Picasso.get().load(list.get(position).getPetresim()).into(holder.sanalKarnePetImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView sanalKarnePetText,sanalKarneBilgiText;
        CircleImageView sanalKarnePetImage;

        // itemView ile LisView'un he elemani icin Layout ile olusturdugumuz View tanimlamasi islemi gerceklesiyor
        public ViewHolder(View itemView) {
            super(itemView);
            sanalKarneBilgiText = itemView.findViewById(R.id.sanalKarneBilgiText);
            sanalKarnePetText = itemView.findViewById(R.id.sanalKarnePetText);
            sanalKarnePetImage = itemView.findViewById(R.id.sanalKarnePetImage);
        }
    }
}
