package com.sttech.tvdownload;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DataAdapter1 extends RecyclerView.Adapter<DataAdapter1.ViewHolder> {

    private List<File> carItemList=new ArrayList<>();
    Activity context;
    boolean isEnable=false;
    boolean isSelectAll=false;
    ArrayList<String> selectList=new ArrayList<>();
    private AdapterView.OnItemClickListener onItemClickListenerSongPlay;

    public DataAdapter1(Activity context, List<File> carItemList) {
        this.carItemList = carItemList;
        this.context=context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View carItemView = layoutInflater.inflate(R.layout.item_view, parent, false);

        // Get car title text view object.
        final View view = carItemView;
        final TextView txtid = (TextView)carItemView.findViewById(R.id.txtid);
        final TextView carnameview = (TextView)carItemView.findViewById(R.id.text_view);

        carnameview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListenerSongPlay != null) {

                    onItemClickListenerSongPlay.onItemClick(null, view, (Integer.parseInt(txtid.getText().toString()))-1, -1);
                }

            }
        });

        // Create and return our custom Car Recycler View Item Holder object.
        ViewHolder ret = new ViewHolder(carItemView);
        return ret;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(carItemList!=null) {


                holder.txtid.setText(""+(position+1));
                holder.txtcode.setText(""+carItemList.get(position).getName());
//            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    // check condition
//                    if (!isEnable)
//                    {
//                        // when action mode is not enable
//                        // initialize action mode
//                        ActionMode.Callback callback=new ActionMode.Callback() {
//                            @Override
//                            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//                                // initialize menu inflater
//                                MenuInflater menuInflater= mode.getMenuInflater();
//                                // inflate menu
//                                menuInflater.inflate(R.menu.menu_delete,menu);
//                                // return true
//                                return true;
//                            }
//
//                            @Override
//                            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//                                // when action mode is prepare
//                                // set isEnable true
//                                isEnable=true;
//                                // create method
//                                ClickItem(holder);
//                                // set observer on getText method
//                                mainViewModel.getText().observe((LifecycleOwner) context
//                                        , new Observer<String>() {
//                                            @Override
//                                            public void onChanged(String s) {
//                                                // when text change
//                                                // set text on action mode title
//                                                mode.setTitle(String.format("%s Selected",s));
//                                            }
//                                        });
//                                // return true
//                                return true;
//                            }
//
//                            @Override
//                            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//                                // when click on action mode item
//                                // get item  id
//                                int id=item.getItemId();
//                                // use switch condition
//                                switch(id)
//                                {
//                                    case R.id.menu_delete:
//                                        // when click on delete
//                                        // use for loop
//                                        for(String s:selectList)
//                                        {
//                                            // remove selected item list
//                                            arrayList.remove(s);
//                                        }
//                                        // check condition
//                                        if(arrayList.size()==0) {
//                                            // when array list is empty
//                                            // visible text view
//                                            tvEmpty.setVisibility(View.VISIBLE);
//                                        }
//                                        // finish action mode
//                                        mode.finish();
//                                        break;
//
//                                    case R.id.menu_select_all:
//                                        // when click on select all
//                                        // check condition
//                                        if(selectList.size()==arrayList.size()) {
//                                            // when all item selected
//                                            // set isselectall false
//                                            isSelectAll=false;
//                                            // create select array list
//                                            selectList.clear();
//                                        } else {
//                                            // when  all item unselected
//                                            // set isSelectALL true
//                                            isSelectAll=true;
//                                            // clear select array list
//                                            selectList.clear();
//                                            // add value in select array list
//                                            selectList.addAll(arrayList);
//                                        }
//                                        // set text on view model
//                                        mainViewModel.setText(String .valueOf(selectList.size()));
//                                        // notify adapter
//                                        notifyDataSetChanged();
//                                        break;
//                                }
//                                // return true
//                                return true;
//                            }
//
//                            @Override
//                            public void onDestroyActionMode(ActionMode mode) {
//                                // when action mode is destroy
//                                // set isEnable false
//                                isEnable=false;
//                                // set isSelectAll false
//                                isSelectAll=false;
//                                // clear select array list
//                                selectList.clear();
//                                // notify adapter
//                                notifyDataSetChanged();
//                            }
//                        };
//                        // start action mode
//                        ((AppCompatActivity) v.getContext()).startActionMode(callback);
//                    }
//                    else
//                    {
//                        // when action mode is already enable
//                        // call method
//                        ClickItem(holder);
//                    }
//                    // return true
//                    return true;
//                }
//            });
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // check condition
//                    if(isEnable)
//                    {
//                        // when action mode is enable
//                        // call method
//                        ClickItem(holder);
//                    }
//                    else
//                    {
//                        // when action mode is not enable
//                        // display toast
//                        Toast.makeText(activity,"You Clicked"+arrayList.get(holder.getAdapterPosition()),
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//            // check condition
//            if(isSelectAll) {
//                // when value selected
//                // visible all check boc image
//                holder.checkbox.setVisibility(View.VISIBLE);
//                //set background color
//                holder.itemView.setBackgroundColor(Color.LTGRAY);
//            }else {
//                // when all value unselected
//                // hide all check box image
//                holder.checkbox.setVisibility(View.GONE);
//                // set background color
//                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
//            }





        }
    }

//    private void ClickItem(ViewHolder holder) {
//
//        // get selected item value
//        String s=arrayList.get(holder.getAdapterPosition());
//        // check condition
//        if(holder.checkbox.getVisibility()==View.GONE) {
//            // when item not selected
//            // visible check box image
//            holder.checkbox.setVisibility(View.VISIBLE);
//            // set background color
//            holder.itemView.setBackgroundColor(Color.LTGRAY);
//            // add value in select array list
//            selectList.add(s);
//        } else {
//            // when item selected
//            // hide check box image
//            holder.checkbox.setVisibility(View.GONE);
//            // set background color
//            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
//            // remove value from select arrayList
//            selectList.remove(s);
//
//        }
//        // set text on view model
//        mainViewModel.setText(String.valueOf(selectList.size()));
//    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if(carItemList!=null)
        {
            ret = carItemList.size();
        }
        return ret;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void clear() {
        int size = carItemList.size();
        carItemList.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListenerSongPlay = onItemClickListener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtid;
        public TextView txtcode;
        public ImageView imgdelete;
        ImageView checkbox;
        public ViewHolder(View itemView) {
            super(itemView);
            this.txtid = (TextView) itemView.findViewById(R.id.txtid);
            this.txtcode = (TextView) itemView.findViewById(R.id.text_view);
            this.imgdelete = (ImageView) itemView.findViewById(R.id.imgapp);
            checkbox=itemView.findViewById(R.id.check_box);
        }
    }



}
