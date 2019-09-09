package com.example.alphamobilecolombia.mvp.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.remote.instance.implement.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables

    Context mContext;
    LayoutInflater inflater;
    private List<String> Lista = null;
    private ArrayList<String> arraylist;

    public ListViewAdapter(Context context, List<String> animalNamesList) {
        mContext = context;
        this.Lista = animalNamesList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<String>();
        this.arraylist.addAll(animalNamesList);
    }

    public class ViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return Lista.size();
    }

    @Override
    public String getItem(int position) {
        return Lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public  int GetPositionByValue(String value)
    {
        return arraylist.indexOf(value);
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_view_items, null);
            // Locate the TextViews in listview_item.xml
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.name.setText(Lista.get(position));
        return view;
    }

    // Filter Class
    public boolean filter(String charText) {

        try {

            charText = charText.toLowerCase(Locale.getDefault());

            if (Lista != null)
                Lista = new ArrayList<>();

            if (charText.length() == 0) {
                //Lista.addAll(arraylist);
            } else {
                for (String wp : arraylist) {
                    if (wp.toLowerCase(Locale.getDefault()).contains(charText)) { //getAnimalName()
                        Lista.add(wp);
                    }
                }
            }

            notifyDataSetChanged();

            if (Lista.size() > 0) {
                return true;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

}