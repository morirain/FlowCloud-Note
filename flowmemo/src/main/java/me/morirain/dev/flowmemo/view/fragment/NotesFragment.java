package me.morirain.dev.flowmemo.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.mikepenz.materialdrawer.DrawerBuilder;

import me.morirain.dev.flowmemo.R;
import me.morirain.dev.flowmemo.base.BaseAdapter;
import me.morirain.dev.flowmemo.base.BaseFragment;
import me.morirain.dev.flowmemo.bean.Notes;
import me.morirain.dev.flowmemo.databinding.FragmentNotesBinding;
import me.morirain.dev.flowmemo.viewmodel.NotesViewModel;

public class NotesFragment extends BaseFragment<FragmentNotesBinding, NotesViewModel> implements View.OnClickListener, BaseAdapter.OnItemClickListener {

    private BaseAdapter<Notes> mNotesAdapter;

    private NotesViewModel mNotesViewModel;

    @Override
    protected void init(Bundle savedInstanceState) {
        initRecyclerView();
        getViewModel().setAdapter(mNotesAdapter);
    }



    private void initRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        getBinding().notesRecyclerView.setLayoutManager(layoutManager);

        mNotesAdapter = new BaseAdapter<>(this, me.morirain.dev.flowmemo.BR.notes, R.layout.item_notes);
        mNotesAdapter.setOnItemClickListener(this);
        getBinding().notesRecyclerView.setAdapter(mNotesAdapter);
        getBinding().buttonNewNote.setOnClickListener(this);
        getBinding().buttonNewPhotoNote.setOnClickListener(this);
        getBinding().buttonNewVoiceNote.setOnClickListener(this);

    }


    /**
     * Bottom 被点击
     *
     * @param view 被点击 的 View
     */
    @Override
    public void onClick(View view) {
        if (view == getBinding().buttonNewNote) {

        } else if (view == getBinding().buttonNewPhotoNote) {

        } else if (view == getBinding().buttonNewVoiceNote) {

        }
    }

    /**
     * NotesItem 被点击
     *
     * @param view Item 的 View
     * @param position 被点击的 Item 的位置
     */
    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    protected void setViewModel() {
        getBinding().setViewModel(getViewModel());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_notes;
    }

    @Override
    protected Class<NotesViewModel> getViewModelClass() {
        return NotesViewModel.class;
    }


}
