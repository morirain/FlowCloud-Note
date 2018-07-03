package com.morirain.flowmemo.adapter;


import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.morirain.flowmemo.R;
import com.morirain.flowmemo.base.BaseAdapter;
import com.morirain.flowmemo.model.Folder;
import com.morirain.flowmemo.model.repository.NoteLibraryRepository;
import com.morirain.flowmemo.utils.SingletonFactory;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/7/1
 */


public class FolderAdapter extends BaseAdapter<Folder> {

    private NoteLibraryRepository mRepository = SingletonFactory.getInstance(NoteLibraryRepository.class);

    public FolderAdapter(LifecycleOwner lifecycleOwner, int layoutId) {
        super(lifecycleOwner, layoutId);
        mRepository.getFolderList().observe(lifecycleOwner, folders -> {
            if (folders != null) {
                getListValue().clear();
                getListValue().addAll(folders);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        getListValue().get(position)
                .isSelected()
                .observe(getLifecycleOwner(), aBoolean -> {
                    if (aBoolean != null && aBoolean) {
                        holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorFolderItemSelected));
                    } else {
                        holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.colorFolderItem));
                    }
                });
    }
}
