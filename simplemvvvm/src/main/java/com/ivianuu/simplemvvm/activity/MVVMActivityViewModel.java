/*
 * Copyright 2017 Manuel Wrage
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ivianuu.simplemvvm.activity;

import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ivianuu.simplemvvm.ViewModelLifecycleTransformer;
import com.ivianuu.simplemvvm.model.ActivityResult;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Activity view model
 */

public class MVVMActivityViewModel extends ViewModel {

    private final PublishSubject<ActivityResult> activityResult = PublishSubject.create();
    private final PublishSubject<Object> cleared = PublishSubject.create();
    private final PublishSubject<Intent> intent = PublishSubject.create();

    /**
     * By composing this transformer with an observable you guarantee that every observable in your view model
     * will be properly completed when the view model completes.
     */
    protected<T> ViewModelLifecycleTransformer<T> bindToLifecycle() {
        return new ViewModelLifecycleTransformer<>(cleared);
    }

    /**
     * Call this on every activity result
     */
    public void activityResult(@NonNull ActivityResult activityResult) { this.activityResult.onNext(activityResult); }

    /**
     * Emits when a new activity result arrives
     */
    protected Observable<ActivityResult> activityResult() { return activityResult; }

    /**
     * Call this on every new intent
     */
    public void intent(@Nullable Intent intent) {
        if (intent != null) {
            // rx java only likes non null values
            this.intent.onNext(intent);
        }
    }

    /**
     * Emits when a new intent is available
     */
    protected Observable<Intent> intent() { return intent; }

    @Override
    protected void onCleared() {
        super.onCleared();
        cleared.onNext(new Object());
    }
}
