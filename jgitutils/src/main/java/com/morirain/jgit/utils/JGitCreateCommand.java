package com.morirain.jgit.utils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

class JGitCreateCommand {

    protected interface Command {
        void doSomething(ObservableEmitter<CallbackCommand> emitter) throws Exception;
    }

        protected static Observable<CallbackCommand> create(Command command){
            return Observable.defer(() -> Observable.create((ObservableOnSubscribe<CallbackCommand>) emitter -> {
                command.doSomething(emitter);
                emitter.onComplete();
            }));
        }
}
