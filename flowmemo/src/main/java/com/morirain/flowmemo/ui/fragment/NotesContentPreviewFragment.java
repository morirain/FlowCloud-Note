package com.morirain.flowmemo.ui.fragment;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;

import com.morirain.flowmemo.R;
import com.morirain.flowmemo.base.BaseCommandHandler;
import com.morirain.flowmemo.base.BaseFragment;
import com.morirain.flowmemo.databinding.FragmentNotesContentPreviewBinding;
import com.morirain.flowmemo.viewmodel.NotesContentViewModel;
import com.yydcdut.markdown.MarkdownProcessor;
import com.yydcdut.markdown.syntax.text.TextFactory;

public class NotesContentPreviewFragment extends BaseFragment<FragmentNotesContentPreviewBinding, NotesContentViewModel> {

    @Override
    protected void init(Bundle savedInstanceState) {
        initPreview();
    }

    private void initPreview() {
        if (getActivity() != null) {
            MarkdownProcessor markdownProcessor = new MarkdownProcessor(getActivity());
            //markdownProcessor.config(markdownConfiguration);
            markdownProcessor.factory(TextFactory.create());
            getViewModel().notesContent.observe(this, content ->
                    getBinding().tvNotesContentMarkdown.setText(markdownProcessor.parse(content)));
        }


    }

    @Override
    protected void setCustomViewModelConnect() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_notes_content_preview;
    }

    @Override
    protected Class<NotesContentViewModel> getViewModelClass() {
        return NotesContentViewModel.class;
    }

    @Override
    protected BaseCommandHandler getHandler() {
        return null;
    }
}
