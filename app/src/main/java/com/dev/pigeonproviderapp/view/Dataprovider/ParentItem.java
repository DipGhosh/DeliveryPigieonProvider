package com.dev.pigeonproviderapp.view.Dataprovider;

import java.util.List;

public class ParentItem {

    private String ParentItemTitle;
    private List<ChildItem> ChildItemList;


    public String getParentItemTitle()
    {
        return ParentItemTitle;
    }

    public void setParentItemTitle(String parentItemTitle)
    {
        ParentItemTitle = parentItemTitle;
    }

    public List<ChildItem> getChildItemList()
    {
        return ChildItemList;
    }

    public void setChildItemList(
            List<ChildItem> childItemList)
    {
        ChildItemList = childItemList;
    }
}
