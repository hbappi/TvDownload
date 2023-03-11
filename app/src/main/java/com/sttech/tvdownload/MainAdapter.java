package com.sttech.tvdownload;

import android.app.Activity;
import android.app.Dialog;
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
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProviders;

import java.io.File;
import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    // initialize variables
    Activity activity;
    ArrayList<File> arrayList;
    TextView tvEmpty;
    MainViewModel mainViewModel;
    boolean isEnable=false;
    boolean isSelectAll=false;
    ArrayList<File> selectList=new ArrayList<>();

    // create constructor
    public MainAdapter(Activity activity, ArrayList<File> arrayList, TextView tvEmpty)
    {
        this.activity=activity;
        this.arrayList=arrayList;
        this.tvEmpty=tvEmpty;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // initialize variables
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_view,parent,false);

        // initialize view Model
        mainViewModel= ViewModelProviders.of((FragmentActivity) activity)
                .get(MainViewModel.class);

        // return view
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // set text on text view
        holder.textView.setText(arrayList.get(position).getName());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // check condition
                if (!isEnable)
                {
                    // when action mode is not enable
                    // initialize action mode
                    ActionMode.Callback callback=new ActionMode.Callback() {
                        @Override
                        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                            // initialize menu inflater
                            MenuInflater menuInflater= mode.getMenuInflater();
                            // inflate menu
                            menuInflater.inflate(R.menu.menu_delete,menu);
                            // return true
                            return true;
                        }

                        @Override
                        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                            // when action mode is prepare
                            // set isEnable true
                            isEnable=true;
                            // create method
                            ClickItem(holder);
                            // set observer on getText method
                            mainViewModel.getText().observe((LifecycleOwner) activity
                                    , new Observer<String>() {
                                        @Override
                                        public void onChanged(String s) {
                                            // when text change
                                            // set text on action mode title
                                            mode.setTitle(String.format("%s Selected",s));
                                        }
                                    });
                            // return true
                            return true;
                        }

                        @Override
                        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                            // when click on action mode item
                            // get item  id
                            int id=item.getItemId();
                            // use switch condition
                            switch(id)
                            {
                                case R.id.menu_delete:
                                    // when click on delete
                                    // use for loop
                                    for(File s:selectList) {
                                        // remove selected item list
                                        s.delete();
                                        arrayList.remove(s);
                                    }
                                    // check condition
                                    if(arrayList.size()==0) {
                                        // when array list is empty
                                        // visible text view
                                        tvEmpty.setVisibility(View.VISIBLE);
                                    }
                                    // finish action mode
                                    mode.finish();
                                    break;

                                case R.id.menu_select_all:
                                    // when click on select all
                                    // check condition
                                    if(selectList.size()==arrayList.size()) {
                                        // when all item selected
                                        // set isselectall false
                                        isSelectAll=false;
                                        // create select array list
                                        selectList.clear();
                                    } else {
                                        // when  all item unselected
                                        // set isSelectALL true
                                        isSelectAll=true;
                                        // clear select array list
                                        selectList.clear();
                                        // add value in select array list
                                        selectList.addAll(arrayList);
                                    }
                                    // set text on view model
                                    mainViewModel.setText(String .valueOf(selectList.size()));
                                    // notify adapter
                                    notifyDataSetChanged();
                                    break;
                            }
                            // return true
                            return true;
                        }

                        @Override
                        public void onDestroyActionMode(ActionMode mode) {
                            // when action mode is destroy
                            // set isEnable false
                            isEnable=false;
                            // set isSelectAll false
                            isSelectAll=false;
                            // clear select array list
                            selectList.clear();
                            // notify adapter
                            notifyDataSetChanged();
                        }
                    };
                    // start action mode
                    ((AppCompatActivity) v.getContext()).startActionMode(callback);
                }
                else
                {
                    // when action mode is already enable
                    // call method
                    ClickItem(holder);
                }
                // return true
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check condition
                if(isEnable) {
                    // when action mode is enable
                    // call method
                    ClickItem(holder);
                } else {
                    showDialog(activity,holder.getAdapterPosition());
                    // when action mode is not enable
                    // display toast
//                    Toast.makeText(activity,"You Clicked"+arrayList.get(holder.getAdapterPosition()),
//                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        // check condition
        if(isSelectAll)
        {
            // when value selected
            // visible all check boc image
            holder.checkbox.setVisibility(View.VISIBLE);
            //set background color
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }
        else
        {
            // when all value unselected
            // hide all check box image
            holder.checkbox.setVisibility(View.GONE);
            // set background color
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void ClickItem(ViewHolder holder) {

        // get selected item value
        File s=arrayList.get(holder.getAdapterPosition());
        // check condition
        if(holder.checkbox.getVisibility()==View.GONE) {
            // when item not selected
            // visible check box image
            holder.checkbox.setVisibility(View.VISIBLE);
            // set background color
            holder.itemView.setBackgroundColor(Color.LTGRAY);
            // add value in select array list
            selectList.add(s);
        } else {
            // when item selected
            // hide check box image
            holder.checkbox.setVisibility(View.GONE);
            // set background color
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            // remove value from select arrayList
            selectList.remove(s);

        }
        // set text on view model
        mainViewModel.setText(String.valueOf(selectList.size()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // initialize variables
        TextView textView;
        ImageView checkbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // assign variables
            textView=itemView.findViewById(R.id.text_view);
            checkbox=itemView.findViewById(R.id.check_box);

        }
    }


    public void showDialog(Activity activity, int i){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);

        Button btn_install = (Button) dialog.findViewById(R.id.btn_install);
        Button btn_delete = (Button) dialog.findViewById(R.id.btn_delete);
        Button btn_close = (Button) dialog.findViewById(R.id.btn_close);


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.get(i).delete();
                arrayList.remove(i);
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        btn_install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".provider", arrayList.get(i));
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);

                dialog.dismiss();
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}
