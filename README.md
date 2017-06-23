# SimpleMVVM
Base MVVM implementation for android inspired by kickstarter.

## Introduction
This library uses the architecture component implementation.

## Download
```groovy
dependencies {
	 compile 'com.github.IVIanuu:SimpleMVVM:LATEST-VERSION'
}
```
## Usage

For activities.

First create a view model it needs to extend SimpleMVVMActivityViewModel.
```java
public class LoginActivityViewModel extends MVVMActivityViewModel {

    public LoginActivityViewModel() {
       
    }
    
    public void login() {
      // todo implement login
    }
}
```

Then create your activity class. The activity needs to extend SimpleMVVMActivity<MyViewModel>
and must be annotated with a @RequiresActivityViewModel(MyViewModel.class) annotation.

```java
@RequiresActivityViewModel(LoginActivityViewModel.class)
public class LoginActivity extends MVVMActivity<LoginActivityViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // use your view model here
	viewModel.login();
    }
    
    ...
    
}
```
By default the library will use the default ViewModelProvider.Factory. 
If you want to use your own (For example to inject dependencies in your view model via dagger) you have to override the provideViewModelFactory method like this.

```java
@RequiresActivityViewModel(LoginActivityViewModel.class)
public class LoginActivity extends MVVMActivity<LoginActivityViewModel> {

    ...

    @Override
    protected ViewModelProvider.Factory provideViewModelFactory() {
        return mySuperCoolViewModelFactory;
    }
}
```

