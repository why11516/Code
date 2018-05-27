package com.why.readydemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.os.SystemClock;
import android.support.v4.media.RatingCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 11516 on 2018-5-24.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private Context mContext;
    private List<Integer> mBitmapIdList;
    private List<Integer> mParentIndex;
    public boolean isOpen = false;
    private OnItemClickListener mMyItemOnClickListener;
    public RecyclerViewAdapter(Context context, List<Integer> bitmapIdList){
        mContext = context;
        mBitmapIdList = bitmapIdList;
        initData();
        Log.i(TAG, "RecyclerViewAdapter: ");
    }

    private void initData() {
        mParentIndex = new ArrayList<>();
        for (int i=0; i<mBitmapIdList.size(); i++){
            mParentIndex.add(i);
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder: ]");
        View inflate = View.inflate(mContext, R.layout.item_recyclerview, null);
        return new RecyclerViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        Log.i(TAG, "onBindViewHolder: ");
        holder.imageView.setImageResource(mBitmapIdList.get(position));
        if (mMyItemOnClickListener != null){
            Log.i(TAG, "onBindViewHolder: ");
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: ");
                    mMyItemOnClickListener.onItemClick(v, position);
                }
            });

        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mMyItemOnClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: ");
        return mBitmapIdList.size();
    }

    /**
     *
     * @param idList 需要添加的item和减少的item
     * @param position 在哪一个地方添加减少
     */
    public void expandState(List<Integer> idList, int position){
        //Log.i(TAG, "expandState_removeAdapterData:1 " + idList.size());
        int removeIndex = position;
        for (int i=0; i<idList.size(); i++){
            Log.i(TAG, "expandState: for");
            if (!isOpen){
                addAdapterData(removeIndex, idList.get(i));
            }else {
                Log.i(TAG, "expandState_removeAdapterData: 2");
                removeAdapterData(removeIndex);
            }
        }
        isOpen = !isOpen;

    }

    public void addAdapterData(int position, int drawableId) {
        //mParentIndex.set(position, mParentIndex.get(position)+position);
        mBitmapIdList.add(position, drawableId);
        Log.i(TAG, "addAdapterData:mBitmapIdList.size() " + mBitmapIdList.size());
        notifyItemInserted(position);
    }

    public boolean getOpenState(){
        return isOpen;
    }

    /**
     * 移除数据
     */
    private void removeAdapterData(int position) {
        //mParentIndex.set(position, mParentIndex.get(position)+position);
        mBitmapIdList.remove(position);
        notifyItemRemoved(position);
    }

    static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.tv_item);
        }

    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
