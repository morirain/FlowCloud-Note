package me.morirain.dev.flowmemo.viewmodel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.morirain.dev.flowmemo.base.BaseAdapter;
import me.morirain.dev.flowmemo.bean.Notes;

/**
 * @author morirain
 * @email morirain.dev@outlook.com
 * @since 2018/6/2
 */


public class NotesViewModel extends ViewModel {

    private BaseAdapter<Notes> mNotesAdapter;

    private MutableLiveData<List<Notes>> mNotesList;

    private List<Notes> mList;

    public LiveData<List<Notes>> getNotesList() {
        if (mNotesList == null) mNotesList = mNotesAdapter.getList();
        return mNotesList;
    }

    public void init(BaseAdapter<Notes> notesAdapter) {
        mNotesAdapter = notesAdapter;
        mNotesList = notesAdapter.getList();
        mList = mNotesList.getValue();
        getNotes();
    }
    private void getNotes() {
        MutableLiveData<String> label = new MutableLiveData<String>();
        label.setValue("hello");
        Notes notes = new Notes("asdsa", "fsad", "afsdew");
        mNotesList.getValue().add(notes);

        label = new MutableLiveData<String>();
        label.setValue("safdfdsasdaf");
        Notes n2 = new Notes("asdf", "gs", "fsd");
        mNotesList.getValue().add(n2);
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {

            Thread.sleep(5000);
            emitter.onNext(1);
            emitter.onComplete();

        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        //Notes n212 = new Notes("asdaaa", "gs", "fsssssaaaaaaaaaaaaaaaasd");
                        if (mNotesList.hasObservers()) mNotesList.getValue().add(n2);
                        mList.add(n2);
                        mList.add(n2);
                        mList.add(n2);
                        mList.add(n2);
                        //mNotesList.getValue().add(n212);mNotesList.getValue().add(n212);mNotesList.getValue().add(n212);mNotesList.getValue().add(n212);mNotesList.getValue().add(n212);mNotesList.getValue().add(n212);mNotesList.getValue().add(n212);mNotesList.getValue().add(n212);mNotesList.getValue().add(n212);mNotesList.getValue().add(n212);mNotesList.getValue().add(n212);mNotesList.getValue().add(n212);mNotesList.getValue().add(n212);mNotesList.getValue().add(n212);mNotesList.getValue().add(n212);
                        //mNotesList.setValue(mNotesList.getValue());
                        //mNotesAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Notes n212 = new Notes("asdaaa", "gs", "fsssssaaaaaaaaaaaaaaaasd");
                        mNotesList.getValue().add(n212);
                        mList.add(n2);
                        mList.add(n2);
                        mList.add(n2);
                        mList.add(n2);
                    }
                });
        /*Disposable d2 = Observable.timer(2, TimeUnit.SECONDS)
                //.observeOn(AndroidSchedulers.mainThread())
                //.subscribeOn(Schedulers.io())
                .subscribe(aLong -> {
            Notes n21 = new Notes("asdaaa", "gssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss", "fsd");
            mNotesList.getValue().set(1, n21);
        });*/
        mList.add(n2);
        mList.add(n2);
    }
}
