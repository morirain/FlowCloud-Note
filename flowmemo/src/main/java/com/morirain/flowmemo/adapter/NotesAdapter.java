package com.morirain.flowmemo.adapter;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.morirain.flowmemo.BR;
import com.morirain.flowmemo.base.BaseViewModel;
import com.morirain.flowmemo.model.Notes;
import com.morirain.flowmemo.model.repository.NoteLibraryRepository;
import com.morirain.flowmemo.utils.SingleLiveEvent;
import com.morirain.flowmemo.utils.SingletonFactory;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/7/1
 */

public class NotesAdapter extends RecyclerView.Adapter<com.morirain.flowmemo.adapter.NotesAdapter.ViewHolder> {

    public final static int SORT_MODE_POSITIVE = 1;

    public final static int SORT_MODE_REVERSE = 2;

    private NoteLibraryRepository mRepository = SingletonFactory.getInstance(NoteLibraryRepository.class);

    // Binding
    private ViewDataBinding mBind;
    // Lifecycle
    private LifecycleOwner mLifecycleOwner;
    // List Data
    private MutableLiveData<SortedList<Notes>> mDataList = new MutableLiveData<>();

    private SingleLiveEvent<Void> mItemChangeEvent = new SingleLiveEvent<>();
    // viewModel
    private BaseViewModel mViewModel;
    // BR Id
    private int mVariableId;
    // Layout Id
    private int mLayoutId;

    protected SortedList<Notes> getListValue() {
        if (mDataList != null) return mDataList.getValue();
        throw new NullPointerException();
    }

    public MutableLiveData<SortedList<Notes>> getDataList() {
        return mDataList;
    }

    protected LifecycleOwner getLifecycleOwner() {
        return mLifecycleOwner;
    }

    public NotesAdapter(LifecycleOwner lifecycleOwner, int layoutId) {
        this.mLifecycleOwner = lifecycleOwner;
        this.mVariableId = BR.item;
        this.mLayoutId = layoutId;
        this.mDataList.setValue(new SortedList<>(Notes.class, new NotesSortedListAdapterCallback(this)));
        initEvent(lifecycleOwner);
    }

    private void initEvent(LifecycleOwner lifecycleOwner) {
        mRepository.getCurrentFolder().observe(lifecycleOwner, folder -> {
            if (folder != null) {
                getListValue().beginBatchedUpdates();
                getListValue().clear();
                getListValue().addAll(folder.getNotesListValue());
                getListValue().endBatchedUpdates();
                //getListValue().replaceAll(folder.getNotesListValue());
            }
        });
        // update note anim
        mRepository.getNoteChangeEvent().observe(lifecycleOwner, notesNotesPair -> {
                    if (notesNotesPair != null && notesNotesPair.second != null) {
                        Notes source = notesNotesPair.first;
                        Notes after = notesNotesPair.second;
                        getListValue().beginBatchedUpdates();
                        if (source != null) {
                            int position = getListValue().indexOf(source);
                            getListValue().updateItemAt(position, after);
                        } else {
                            getListValue().add(after);
                        }
                        getListValue().endBatchedUpdates();
                        getItemChangeEvent().call();
                    }
                }
        );
    }

    public SingleLiveEvent<Void> getItemChangeEvent() {
        return mItemChangeEvent;
    }

    public void setViewModel(BaseViewModel viewModel) {
        mViewModel = viewModel;
    }

    public void setVariableId(int variableId) {
        this.mVariableId = variableId;
    }

    @NonNull
    @Override
    public ViewHolder<ViewDataBinding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mBind = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), mLayoutId, parent, false);
        if (mLifecycleOwner != null) mBind.setLifecycleOwner(mLifecycleOwner);
        return new ViewHolder<>(mBind);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.getBinding().setVariable(mVariableId, getListValue().get(position));
        if (mViewModel != null) holder.getBinding().setVariable(BR.viewModel, mViewModel);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : getListValue().size();
    }

    class ViewHolder<D extends ViewDataBinding> extends RecyclerView.ViewHolder {
        private D binding;

        ViewHolder(D binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        D getBinding() {
            return binding;
        }
    }

    public class NotesSortedListAdapterCallback extends SortedListAdapterCallback<Notes> {

        private int mSortMode = SORT_MODE_POSITIVE;

        public NotesSortedListAdapterCallback(RecyclerView.Adapter adapter) {
            super(adapter);
        }

        @Override
        public int compare(Notes notes, Notes t21) {
            if (mSortMode == SORT_MODE_POSITIVE)
                return t21.getNoteLastUpdateDate().compareTo(notes.getNoteLastUpdateDate());
            //if (mSortMode == SORT_MODE_REVERSE)
            return notes.getNoteLastUpdateDate().compareTo(t21.getNoteLastUpdateDate());
        }

        @Override
        public boolean areContentsTheSame(Notes notes, Notes t21) {
            //if (!ObjectUtils.equals(notes.getNoteLastUpdateDate(), t21.getNoteLastUpdateDate()))
            //    return false;
            if (!notes.noteLabel.getValue().equals(t21.noteLabel.getValue()))
                return false;
            if (!ObjectUtils.equals(notes.getNoteParentDirName(), t21.getNoteParentDirName())) {
                return false;
            }
            return true;
        }

        @Override
        public boolean areItemsTheSame(Notes notes, Notes t21) {
            return areContentsTheSame(notes, t21);
        }

        public void setSortMode(int sortMode) {
            mSortMode = sortMode;
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            super.onMoved(fromPosition, toPosition);
            notifyItemChanged(toPosition);
        }
    }
}