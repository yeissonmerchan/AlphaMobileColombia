package com.example.alphamobilecolombia.mvp.recycler.queryCreditActivity.Model;

import com.example.alphamobilecolombia.data.remote.Models.Response.PostQueryCredit;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Client extends ExpandableGroup <PostQueryCredit>{

    public Client(String title, List<PostQueryCredit> items) {
        super(title, items);
    }

}
