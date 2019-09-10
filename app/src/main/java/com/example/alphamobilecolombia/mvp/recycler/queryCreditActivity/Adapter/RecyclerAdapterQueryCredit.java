package com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alphamobilecolombia.R;
import com.example.alphamobilecolombia.data.remote.Models.Response.PostConsultarReporteCreditoResponse;
import com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.Model.Client;
import com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.Model.ClientDescription;
import com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.ViewHolder.ClientDescriptionViewHolder;
import com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.ViewHolder.ClientViewHolder;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterQueryCredit extends ExpandableRecyclerViewAdapter <ClientViewHolder, ClientDescriptionViewHolder>  {
    ArrayList<PostConsultarReporteCreditoResponse> listReporteCredito = new ArrayList<>();
    PostConsultarReporteCreditoResponse postConsultarReporteCreditoResponse;


    public RecyclerAdapterQueryCredit(List<? extends ExpandableGroup> groups) {
        super(groups);
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
        final ClientDescription clientDescription = (ClientDescription) group.getItems().get(childIndex);

        postConsultarReporteCreditoResponse = listReporteCredito.get(childIndex);
        holder.bind(clientDescription);
    }

    @Override
    public void onBindGroupViewHolder(ClientViewHolder holder, int flatPosition, ExpandableGroup group) {
        final Client client = (Client) group;
        holder.bind(client);
    }
}
