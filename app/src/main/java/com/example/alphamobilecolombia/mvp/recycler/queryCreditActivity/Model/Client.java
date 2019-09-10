package com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.Model;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Client extends ExpandableGroup <ClientDescription>{

    public Client(String title, List<ClientDescription> items) {
        super(title, items);
    }

}
