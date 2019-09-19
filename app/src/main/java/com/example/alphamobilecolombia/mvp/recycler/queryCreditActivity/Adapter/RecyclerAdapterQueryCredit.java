package com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.remote.Models.Response.ObjectExpandible;
import com.example.alphamobilecolombia.data.remote.Models.Response.PostQueryCredit;
import com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.Model.Client;
import com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.Model.ClientDescription;
import com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.ViewHolder.ClientDescriptionViewHolder;
import com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.ViewHolder.ClientViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class RecyclerAdapterQueryCredit extends ExpandableRecyclerViewAdapter <ClientViewHolder, ClientDescriptionViewHolder>  {

    RecyclerView recyclerView;

    public RecyclerAdapterQueryCredit(List<? extends ExpandableGroup> groups, RecyclerView recyclerView) {
        super(groups);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onGroupExpanded(int positionStart, int itemCount) {
        super.onGroupExpanded(positionStart, itemCount);
        int counter;
        for(int index = 0; index < getGroups().size(); index++){
            if(isGroupExpanded(getGroups().get(index))){
                counter = getGroups().get(index).getItemCount();
                int groupIndex = expandableList.getUnflattenedPosition(positionStart).groupPos;
                if ((groupIndex == (getGroups().size() - 1))) {
                    recyclerView.smoothScrollToPosition(positionStart + counter);
                }
            }
        }
    }


    @Override
    public ClientViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recycler_expandable_client, parent, false);
        return new ClientViewHolder(v);
    }

    @Override
    public ClientDescriptionViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_recycler_expandable_client_description, parent, false);
        return new ClientDescriptionViewHolder(v);
    }

    @Override
    public void onBindChildViewHolder(ClientDescriptionViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final PostQueryCredit clientDescription = (PostQueryCredit) group.getItems().get(childIndex);
        holder.bind(clientDescription);
    }

    @Override
    public void onBindGroupViewHolder(ClientViewHolder holder, int flatPosition, ExpandableGroup group) {
        final Client client = (Client) group;
        holder.bind(client);
    }
}
