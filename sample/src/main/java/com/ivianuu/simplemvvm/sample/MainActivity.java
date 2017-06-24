package com.ivianuu.simplemvvm.sample;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;

import com.ivianuu.simplemvvm.activity.MVVMActivity;
import com.ivianuu.simplemvvm.activity.RequiresActivityViewModel;

@RequiresActivityViewModel(MainActivityViewModel.class)
public class MainActivity extends MVVMActivity<MainActivityViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setIntent(new Intent()); // dummy
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_container, new MainFragment()).commit();
        }
    }

}
