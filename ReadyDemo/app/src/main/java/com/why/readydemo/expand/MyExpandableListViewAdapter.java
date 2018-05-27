package com.why.readydemo.expand;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.why.readydemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 11516 on 2018-1-30.
 */

public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "MyExpandableAdapter";
    private Context mContext;
    private ArrayList<String> mParents;
    private ArrayList<List<String>> mChilds;
    public MyExpandableListViewAdapter(Context applicationContext, ArrayList<String> childs, ArrayList<List<String>> parents) {
        mContext = applicationContext;
        mParents = childs;
        mChilds = parents;
    }

    /**
     * 获得父项的数量
     * @return
     */
    @Override
    public int getGroupCount() {
        return mParents.size();
    }

    /**
     * 获得某个父项的子项数量
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return mChilds.get(groupPosition).size();
    }

    /**
     * 获得某个父项
     * @param groupPosition
     * @return
     */
    @Override
    public Object getGroup(int groupPosition) {
        return mParents.get(groupPosition);
    }

    /**
     * 获得某个父项的某个子项
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChilds.get(groupPosition).get(childPosition);
    }

    /**
     * 获得某个父项的id
     * @param groupPosition
     * @return
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * 获得某个父项的某个子项的id
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * 用来判断ExpandableListView内容id是否有效的(返回true or false)
     * 系统会跟据id来确定当前显示哪条内容，也就是firstVisibleChild的位置。
     * @return
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 获得父项显示的view
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ParentViewHolder parentViewHolder = null;
        if (convertView == null){
            parentViewHolder = new ParentViewHolder();
            convertView = View.inflate(mContext, R.layout.home_expand_list_item, null);
            parentViewHolder.parentImageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(parentViewHolder);
        }else {
            parentViewHolder = (ParentViewHolder) convertView.getTag();
        }
        if (isExpanded){
//            parentViewHolder.parentImageView.setImageResource(R.drawable.filter_list_selected);
        }else {
//            parentViewHolder.parentImageView.setImageResource(R.drawable.filter_list_unselected);
        }

//        parentViewHolder.parentTextView.setText(mParents.get(groupPosition));
        return convertView;
    }

    /**
     * 获得子项显示的view
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (convertView == null){
            childViewHolder = new ChildViewHolder();
            convertView = View.inflate(mContext, R.layout.home_expand_child_list_item, null);
            childViewHolder.childLinearLayout = (LinearLayout) convertView.findViewById(R.id.ll_child_root);
            childViewHolder.childImageView = (ImageView) convertView.findViewById(R.id.childImageView);
            convertView.setTag(childViewHolder);
        }else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        return convertView;
    }

    /**
     * 子项是否可选中，如果需要设置子项的点击事件，返回true
     * 用来判断某Group某个child是否可可选。我们可以添加条件控制某Group某个child可点或不可点击。
     * 当不加任何条件直接返回false,所有的组的child均不可点击。
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class ChildViewHolder{
        LinearLayout childLinearLayout;
        ImageView childImageView;
    }

    static class ParentViewHolder{
        ImageView parentImageView;
    }

}
