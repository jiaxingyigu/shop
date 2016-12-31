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
import com.yigu.shop.base.BaseActivity;
import com.yigu.shop.commom.result.IndexData;
import com.yigu.shop.commom.result.MapiCartResult;
import com.yigu.shop.commom.result.MapiItemResult;
import com.yigu.shop.commom.util.DPUtil;
import com.yigu.shop.commom.util.DebugLog;
import com.yigu.shop.shopinterface.AdapterSelListener;
import com.yigu.shop.view.PurcaseSheetLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by brain on 2016/9/8.
 */
public class PurcaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    List<MapiCartResult> mList = new ArrayList<>();
    List<IndexData> list = new ArrayList<>();
    AdapterSelListener listener;

    public List<MapiCartResult> getmList() {
        return mList;
    }

    public List<IndexData> getList(){
        return list;
    }

    public void setOnAdapterSelListener(AdapterSelListener listener){
        this.listener = listener;
    }
    public PurcaseAdapter(Context context,List<MapiCartResult> list) {
        inflater = LayoutInflater.from(context);
        this.mList = list;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        DebugLog.i("getItemCount");
        int count;
        if(mList==null||mList.size()==0){

            count = 0;
        }else{
            count = 1;

            list.add(new IndexData(count++,"divider", new Object()));
            for (MapiCartResult ware : mList) {
                list.add(new IndexData(count++,"head",ware));
                for (int i=0;i<ware.getCart_goods().size();i++) {
                    if(i == ware.getCart_goods().size()-1){
                        ware.getCart_goods().get(i).setLast(true);
                    }else
                        ware.getCart_goods().get(i).setLast(false);
                    list.add(new IndexData(count++,"item", ware.getCart_goods().get(i)));

                }
                list.add(new IndexData(count++,"divider", new Object()));
            }
        }


        return count;

    }

    @Override
    public int getItemViewType(int position) {
        String type = list.get(position).getType();
        if (type.equals("item")) {
            return 2;
        } else if (type.equals("head")) {
            return 1;
        }
        return 3;
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
        DebugLog.i("HeadViewHolder=load");
        holder.shopRl.setTag(position);
        holder.shopRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        MapiCartResult ware = (MapiCartResult) list.get(position).getData();
        holder.name.setText(TextUtils.isEmpty(ware.getShop_name())?"":ware.getShop_name());
        boolean isAll = true;
        for (MapiItemResult item : ware.getCart_goods()) {
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
                MapiCartResult ware = (MapiCartResult) list.get(position).getData();
//                    if (!ware.isSel()) {
//                        holder.rootSel.setImageResource(R.mipmap.circle_yellow_sel);
//                    } else {
//                        holder.rootSel.setImageResource(R.mipmap.circle_white);
//                    }
                ware.setSel(!ware.isSel());
                for (MapiItemResult item : ware.getCart_goods()) {
                    item.setSel(ware.isSel());
                }
                if(null!=listener){
                    listener.isAll();
                }

                notifyDataSetChanged();
            }
        });
    }

    private  void setItem(ItemViewHolder holder,int position){
        DebugLog.i("ItemViewHolder=load");
        MapiItemResult result = (MapiItemResult) list.get(position).getData();

        //创建将要下载的图片的URI
        Uri imageUri = Uri.parse(TextUtils.isEmpty(result.getGoods_img())?"":result.getGoods_img());
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

        holder.price.setText(TextUtils.isEmpty(result.getGoods_price())?"":result.getGoods_price());
        String number = TextUtils.isEmpty(result.getGoods_number())?"0":result.getGoods_number();
        holder.purcaseSheetLayout.setNum(Integer.parseInt(number));
        holder.purcaseSheetLayout.setRec_id(result.getRec_id());
        holder.purcaseSheetLayout.setActivity((BaseActivity) mContext);
        holder.purcaseSheetLayout.setTag(position);
        holder.purcaseSheetLayout.setNumListener(new PurcaseSheetLayout.NumInterface() {
            @Override
            public void modify(View view,int num,String price) {
                int position = (int) view.getTag();

                MapiItemResult item = (MapiItemResult) list.get(position).getData();
                item.setGoods_number(num+"");
                item.setGoods_price(price);
                if(null!=listener){
                    listener.isAll();
                }
                notifyDataSetChanged();
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

                MapiItemResult item = (MapiItemResult) list.get(position).getData();
//                    if (!item.isSel()) {
//                        holder.itemSel.setImageResource(R.mipmap.circle_white);
//                    } else {
//                        holder.itemSel.setImageResource(R.mipmap.circle_yellow_sel);
//                    }
                item.setSel(!item.isSel());
                if(null!=listener){
                    listener.isAll();
                }
                notifyDataSetChanged();
            }
        });

    }


}
