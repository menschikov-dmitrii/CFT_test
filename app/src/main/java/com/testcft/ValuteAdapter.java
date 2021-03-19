package com.testcft;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ValuteAdapter extends RecyclerView.Adapter<ValuteAdapter.BaseViewHolder>
{
    private MainObject object;

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position)
    {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public int getItemCount()
    {
        int result = 0;
        if (object != null && object.getValuteList() != null)
            result = object.getValuteList().size();
        return result;
    }

    public void addData(MainObject obj)
    {
        object = obj;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public class BaseViewHolder extends RecyclerView.ViewHolder
    {
        TextView mTextViewNameValute;
        TextView mTextViewNominal;
        TextView mTextViewCorse;
        TextView mTextViewDiff;
        ImageView mImageViewDiff;

        public BaseViewHolder(View itemView)
        {
            super(itemView);
            mTextViewNameValute = itemView.findViewById(R.id.mTextViewNameValute);
            mTextViewNominal = itemView.findViewById(R.id.mTextViewNominal);
            mTextViewDiff = itemView.findViewById(R.id.mTextViewDiff);
            mTextViewCorse = itemView.findViewById(R.id.mTextViewCorse);
            mImageViewDiff = itemView.findViewById(R.id.mImageViewDiff);
        }

        protected void clear()
        {
            mTextViewNameValute.setText("");
            mTextViewNominal.setText("");
            mTextViewDiff.setText("");
            mTextViewCorse.setText("");
            mImageViewDiff.setVisibility(View.INVISIBLE);
        }

        public void onBind(int position)
        {
            clear();
            final Valute valute = object.getValuteList().get(position);
            if (valute.getName() != null)
                mTextViewNameValute.setText(valute.getName());

            if (valute.getNominal()!=null || valute.getCharCode() != null)
                mTextViewNominal.setText((valute.getNominal()!=null?valute.getNominal():"")+" "+(valute.getCharCode()!=null?valute.getCharCode():""));

            if (valute.getValue() != null) {
                mTextViewCorse.setText(String.valueOf(valute.getValue()));
                if (valute.getPrevious()!=null)
                {
                    double diff = valute.getValue() - valute.getPrevious();
                    if(diff!=0.0)
                    {
                        mImageViewDiff.setVisibility(View.VISIBLE);
                        if (diff > 0)
                            mImageViewDiff.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_up));
                        else if (diff < 0)
                            mImageViewDiff.setImageDrawable(itemView.getResources().getDrawable(R.drawable.ic_down));
                    }
                    else
                        mImageViewDiff.setVisibility(View.INVISIBLE);
                    mTextViewDiff.setText(String.format("%.4f",diff));
                }
            }

            itemView.setOnClickListener(v -> {
                if(valute.getNominal()!=null && valute.getValue()!=null && valute.getNominal()!=0 && valute.getValue()!=0.0)
                {
                    Intent i = new Intent(itemView.getContext(), CalcActivity.class);
                    i.putExtra(Valute.class.getCanonicalName(),valute);
                    itemView.getContext().startActivity(i);
                }
                else
                    Toast.makeText(itemView.getContext().getApplicationContext(),itemView.getContext().getString(R.string.errorCalc),Toast.LENGTH_SHORT).show();

            });
        }
    }

}
