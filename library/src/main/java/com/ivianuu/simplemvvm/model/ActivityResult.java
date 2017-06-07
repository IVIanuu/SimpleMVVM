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

package com.ivianuu.simplemvvm.model;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Wraps a activity result which can be used inside the view model
 */

public final class ActivityResult {

    private int requestCode;
    private int resultCode;
    private Intent intent;

    private ActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.intent = intent;
    }

    public static @NonNull ActivityResult create(final int requestCode, final int resultCode, final @Nullable Intent intent) {
        return new ActivityResult(requestCode, resultCode, intent);
    }

    public int getRequestCode() {
        return requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public Intent getIntent() {
        return intent;
    }

    public boolean isCanceled() {
        return resultCode == Activity.RESULT_CANCELED;
    }

    public boolean isOk() {
        return resultCode == Activity.RESULT_OK;
    }

    public boolean isRequestCode(final int requestCode) {
        return this.requestCode == requestCode;
    }
}
