package me.morirain.dev.flowmemo.view.fragment;

import android.os.Bundle;
import android.view.View;

import me.morirain.dev.flowmemo.R;
import me.morirain.dev.flowmemo.base.BaseAdapter;
import me.morirain.dev.flowmemo.base.BaseFragment;
import me.morirain.dev.flowmemo.databinding.FragmentMemoryBinding;
import me.morirain.dev.flowmemo.viewmodel.MemoryViewModel;

public class MemoryFragment extends BaseFragment<FragmentMemoryBinding, MemoryViewModel> implements View.OnClickListener, BaseAdapter.OnItemClickListener {

    @Override
    protected void init(Bundle savedInstanceState) {


        getViewModel().inputText.observe(this, message -> {
            if (message != null && message.length() > 0) {
                getBinding().buttonSend.setVisibility(View.VISIBLE);
                getBinding().buttonMore.setVisibility(View.INVISIBLE);
            } else {
                getBinding().buttonSend.setVisibility(View.INVISIBLE);
                getBinding().buttonMore.setVisibility(View.VISIBLE);
            }
        });
        initRecyclerView();
    }

    private void initRecyclerView() {

    }

    /**
     * Bottom 被点击
     *
     * @param view 被点击 的 View
     */
    @Override
    public void onClick(View view) {
        //if (view == getBinding().buttonNewNote) {

//        }
    }

    /**
     * MemoryItem 被点击
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
        return R.layout.fragment_memory;
    }

    @Override
    protected Class<MemoryViewModel> getViewModelClass() {
        return MemoryViewModel.class;
    }


}
