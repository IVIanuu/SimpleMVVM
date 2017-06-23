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

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ivianuu.simplemvvm.model.ActivityResult;
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle2.LifecycleProvider;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

/**
 * @author Manuel Wrage (IVIanuu)
 */

public class MVVMActivity<ViewModelType extends MVVMActivityViewModel>
        extends AppCompatActivity implements LifecycleRegistryOwner {

    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    private final LifecycleProvider<Lifecycle.Event> provider
            = AndroidLifecycle.createLifecycleProvider(this);

    protected ViewModelType viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // assign the view model
        assignViewModel();

        // pass the intent to our view model
        viewModel.intent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // pass the new intent to our view model
        viewModel.intent(getIntent());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // pass the activity result to our view model
        viewModel.activityResult(ActivityResult.create(requestCode, resultCode, data));
    }

    /**
     * Returns an observable of the activity's lifecycle events.
     */
    protected final Observable<Lifecycle.Event> lifecycle() {
        return provider.lifecycle();
    }

    /**
     * Completes an observable when an {@link android.arch.lifecycle.Lifecycle.Event} occurs in the activity's lifecycle.
     */
    protected final <T> ObservableTransformer<T, T> bindUntilEvent(final Lifecycle.Event event) {
        return provider.bindUntilEvent(event);
    }

    /**
     * Completes an observable when the lifecycle event opposing the current lifecycle event is emitted.
     * For example, if a subscription is made during {@link android.arch.lifecycle.Lifecycle.Event#ON_CREATE}, the observable will be completed
     * in {@link android.arch.lifecycle.Lifecycle.Event#ON_DESTROY}.
     */
    protected final <T> ObservableTransformer<T, T> bindToLifecycle() {
        return provider.bindToLifecycle();
    }

    private void assignViewModel() {
        final RequiresActivityViewModel annotation = getClass().getAnnotation(RequiresActivityViewModel.class);
        final Class<ViewModelType> viewModelClass = annotation == null ? null : (Class<ViewModelType>) annotation.value();
        if (viewModelClass == null) {
            throw new IllegalStateException("class needs RequiresActivityViewModel annotation");
        }

        ViewModelProvider.Factory viewModelFactory = getViewModelFactory();
        if (viewModelFactory != null) {
            // we use the provided view model factory
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass);
        } else {
            // no view model factory provided
            viewModel = ViewModelProviders.of(this).get(viewModelClass);
        }
    }

    /**
     * Override this method to provide your own view model factory
     */
    protected ViewModelProvider.Factory getViewModelFactory() {
        return null;
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
