package com.stallar.vms.ResultView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.stallar.vms.model.NationalIID;

import java.util.List;

/**
 * Created by lucie on 11/21/2017.
 */

public class NationalIDAdapter {
    private List<NationalIID> idList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }

}
