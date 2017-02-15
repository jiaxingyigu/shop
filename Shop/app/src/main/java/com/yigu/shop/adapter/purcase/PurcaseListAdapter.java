package com.yigu.shop.adapter.purcase;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.yigu.shop.R;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiCartResult;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.widget.MainToast;
import com.yigu.shop.shopinterface.AdapterSelListener;
import com.yigu.shop.view.PurcaseSheetLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2017/1/11.
 */
public class PurcaseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private LayoutInflater inflater;
    private Context mContext;
    List<IndexData> mList = new ArrayList<>();

    private boolean isDel = false;

    public void setDel(boolean del) {
        isDel = del;
    }

    AdapterSelListener listener;
    public void setOnAdapterSelListener(AdapterSelListener listener){
        this.listener = listener;
    }

    public PurcaseListAdapter(Context context,List<IndexData> list) {
        inflater = LayoutInflater.from(context);
        this.mList = list;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        String type = mList.get(position).getType();
        if (type.equals("item")) {
            return 2;
        } else if (type.equals("head")) {
            return 1;
        }
        return 3;
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                return new HeadViewHolder(inflater.inflate(R.layout.item_purcase_head, parent, false));
            case 2:
                return new ItemViewHolder(inflater.inflate(R.layout.item_purcase_item, parent, false));
            case 3:
                return new DividerViewHolder(inflater.inflate(R.layout.item_purcase_divider,parent,false));
            default:
                return new HeadViewHolder(inflater.inflate(R.layout.item_purcase_head, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder) {
            setHead((HeadViewHolder) holder,position);
        } else if (holder instanceof ItemViewHolder) {
            setItem((ItemViewHolder) holder,position);
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.root_sel)
        ImageView rootSel;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.shop_rl)
        RelativeLayout shopRl;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_sel)
        ImageView itemSel;
        @Bind(R.id.image)
        SimpleDraweeView image;
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.price)
        TextView price;
        @Bind(R.id.goods_attr)
        TextView goods_attr;
        @Bind(R.id.purcaseSheetLayout)
        PurcaseSheetLayout purcaseSheetLayout;
        @Bind(R.id.divider)
        View divider;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class DividerViewHolder extends RecyclerView.ViewHolder {
        public DividerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void setHead(HeadViewHolder holder,int position){
        MapiCartResult ware = (MapiCartResult) mList.get(position).getData();
        holder.name.setText(TextUtils.isEmpty(ware.getTitle())?"":ware.getTitle());
        if(isDel){
            holder.rootSel.setVisibility(View.VISIBLE);
        }else{
            holder.rootSel.setVisibility(View.INVISIBLE);
        }

        boolean isAll = true;
        for (MapiItemResult item : ware.getList()) {
            if (!item.isSel()) {
                isAll = false;
                break;
            }
        }
        ware.setSel(isAll);
        if(null!=listener){
            listener.isAll();
        }
        if(ware.isSel()){
            holder.rootSel.setImageResource(R.mipmap.circle_red_sel);
        }else{
            holder.rootSel.setImageResource(R.mipmap.circle_white);
        }
        holder.rootSel.setTag(position);
        holder.rootSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                if(null!=listener){
                    listener.notifyParentStatus(position);
                }

            }
        });

    }

    private  void setItem(ItemViewHolder holder,int position){

        MapiItemResult result = (MapiItemResult) mList.get(position).getData();
        //创建将要下载的图片的URI
        Uri imageUri = Uri.parse(TextUtils.isEmpty(result.getImg())?"":result.getImg());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(imageUri)
                .setResizeOptions(new ResizeOptions(DPUtil.dip2px(120), DPUtil.dip2px(120)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(holder.image.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>())
                .build();
        holder.image.setController(controller);

        holder.content.setText(TextUtils.isEmpty(result.getGoods_name())?"":result.getGoods_name());
        if(TextUtils.isEmpty(result.getGoods_attr()))
            holder.goods_attr.setVisibility(View.GONE);
        else{
            holder.goods_attr.setVisibility(View.VISIBLE);
            holder.goods_attr.setText(result.getGoods_attr());
        }

        if(isDel){
            holder.itemSel.setVisibility(View.VISIBLE);
            holder.purcaseSheetLayout.setCanDo(false);
        }else{
            holder.itemSel.setVisibility(View.INVISIBLE);
            holder.purcaseSheetLayout.setCanDo(true);
        }

        holder.price.setText(TextUtils.isEmpty(result.getGoods_price())?"":result.getGoods_price());
        String number = TextUtils.isEmpty(result.getGoods_number())?"0":result.getGoods_number();
        holder.purcaseSheetLayout.setNum(Integer.parseInt(number));

        holder.purcaseSheetLayout.setTag(position);
        holder.purcaseSheetLayout.setNumListener(new PurcaseSheetLayout.NumInterface() {
            @Override
            public void modify(View view,int num,String price) {
                int position = (int) view.getTag();
//                MapiItemResult item = (MapiItemResult) mList.get(position).getData();
//                item.setGoods_number(num+"");
//                item.setGoods_price(price);
//                notifyDataSetChanged();

                if(null!=listener){
                    listener.notifyChildNum(position,num);
                }

            }
        });

        if(result.isSel())
            holder.itemSel.setImageResource(R.mipmap.circle_red_sel);
        else
            holder.itemSel.setImageResource(R.mipmap.circle_white);
        if(result.isLast())
            holder.divider.setVisibility(View.GONE);
        else
            holder.divider.setVisibility(View.VISIBLE);
        holder.itemSel.setTag(position);
        holder.itemSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                if(null!=listener){
                    listener.notifyChildStatus(position);
                }

            }
        });

    }



}
