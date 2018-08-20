package com.stallar.vms.ResultView;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.stallar.vms.R;
import com.stallar.vms.ResultRecognition.RecognitionResultEntry;

import java.util.List;

/**
 * Created by lucie on 11/14/2017.
 */

public class ResultEntryAdapter extends ArrayAdapter<RecognitionResultEntry> {

    private Context mContext;

    public ResultEntryAdapter(Context context, int resource, List<RecognitionResultEntry> entries) {
        super(context, resource, entries);
        this.mContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.content_results, null);
        }

        final RecognitionResultEntry entry = getItem(position);
        TextInputLayout keyText = (TextInputLayout) convertView.findViewById(R.id.resultLabel);
        keyText.setHint(entry.getKey());
        //TextInputLayout

        final EditText valueText = (EditText) convertView.findViewById(R.id.resultValue);
        valueText.setText(entry.getValue());

        return convertView;
    }
}

