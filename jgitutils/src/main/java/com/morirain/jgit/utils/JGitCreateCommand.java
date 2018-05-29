package com.morirain.jgit.utils;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;

class JGitCreateCommand {

    protected interface Command {
        void doSomething(CompletableEmitter emitter) throws Exception;
    }

    protected static Completable create(Command command) {
        return Completable.defer(() ->
                Completable.create(emitter -> {
                    command.doSomething(emitter);
                    emitter.onComplete();
                })
        );
    }
}
