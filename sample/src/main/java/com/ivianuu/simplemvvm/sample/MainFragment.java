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

package com.ivianuu.simplemvvm.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.ivianuu.simplemvvm.fragment.MVVMFragment;
import com.ivianuu.simplemvvm.fragment.RequiresFragmentViewModel;

/**
 * @author Manuel Wrage (IVIanuu)
 */
@RequiresFragmentViewModel(MainFragmentViewModel.class)
public class MainFragment extends MVVMFragment<MainFragmentViewModel> {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
