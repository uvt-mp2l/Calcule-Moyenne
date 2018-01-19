package tn.rnu.isi.calcule_moyenne.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import tn.rnu.isi.calcule_moyenne.R;
import tn.rnu.isi.calcule_moyenne.listener.RecyclerItemClickListener;
import tn.rnu.isi.calcule_moyenne.model.Matiere;
import tn.rnu.isi.calcule_moyenne.widget.LetterTile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wim on 5/1/16.
 */
public class MatiereListAdapter extends RecyclerView.Adapter<MatiereListAdapter.MatiereHolder>{

    private List<Matiere> matiereList;
    private Context context;

    private RecyclerItemClickListener recyclerItemClickListener;

    public MatiereListAdapter(Context context) {
        this.context = context;
        this.matiereList = new ArrayList<>();
    }

    private void add(Matiere item) {
        matiereList.add(item);
        notifyItemInserted(matiereList.size() - 1);
    }

    public void addAll(List<Matiere> matiereList) {
        for (Matiere matiere : matiereList) {
            add(matiere);
        }
    }

    public void remove(Matiere item) {
        int position = matiereList.indexOf(item);
        if (position > -1) {
            matiereList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public Matiere getItem(int position) {
        return matiereList.get(position);
    }

    @Override
    public MatiereHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_matiere_item, parent, false);

        final MatiereHolder matiereHolder = new MatiereHolder(view);

        matiereHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPos = matiereHolder.getAdapterPosition();
                if (adapterPos != RecyclerView.NO_POSITION) {
                    if (recyclerItemClickListener != null) {
                        recyclerItemClickListener.onItemClick(adapterPos, matiereHolder.itemView);
                    }
                }
            }
        });

        return matiereHolder;
    }

    @Override
    public void onBindViewHolder(MatiereHolder holder, int position) {
        final Matiere matiere = matiereList.get(position);

        final Resources res = context.getResources();
        final int tileSize = res.getDimensionPixelSize(R.dimen.letter_tile_size);

        LetterTile letterTile = new LetterTile(context);

        Bitmap letterBitmap = letterTile.getLetterTile(matiere.getName(),
                String.valueOf(matiere.getId()), tileSize, tileSize);

        double cal_moyenne =  0.0;

        if (matiere.getDs_note().length() > 0 && matiere.getTp_note().length() > 0 && matiere.getExm_note().length() > 0){
            if(matiere.getDs_note().length() > 0){
                cal_moyenne += (new BigDecimal(matiere.getDs_note())).doubleValue() * (new BigDecimal(matiere.getDs_coef())).doubleValue();
            }

            if(matiere.getTp_note().length() > 0){
                cal_moyenne += (new BigDecimal(matiere.getTp_note())).doubleValue() * (new BigDecimal(matiere.getTp_coef())).doubleValue();
            }

            if(matiere.getExm_note().length() > 0){
                cal_moyenne += (new BigDecimal(matiere.getExm_note())).doubleValue() * (new BigDecimal(matiere.getExm_coef())).doubleValue();
            }

            if(cal_moyenne < 10)
                holder.img_btn1.setImageResource(R.mipmap.flag_red);
            else
                holder.img_btn1.setImageResource(R.mipmap.flag_green);
        }else
            holder.img_btn1.setImageResource(R.mipmap.flag_yellow);

        holder.thumb.setImageBitmap(letterBitmap);
        holder.name.setText(matiere.getName());
        holder.coef.setText("Coef DS: " + matiere.getDs_coef() + " | " + "Coef Tp: " + matiere.getTp_coef() + " | " + "Coef Exam: " + matiere.getExm_coef());
    }

    @Override
    public int getItemCount() {
        return matiereList.size();
    }

    public void setOnItemClickListener(RecyclerItemClickListener recyclerItemClickListener) {
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    static class MatiereHolder extends RecyclerView.ViewHolder {

        ImageView thumb;
        TextView name;
        TextView coef;
        ImageView img_btn1;

        public MatiereHolder(View itemView) {
            super(itemView);

            thumb = (ImageView) itemView.findViewById(R.id.thumb);
            name = (TextView) itemView.findViewById(R.id.name);
            coef = (TextView) itemView.findViewById(R.id.coef);
            img_btn1 = (ImageView) itemView.findViewById(R.id.img_btn1);
        }
    }
}
