# SimpleMVVM
Base MVVM implementation for android inspired by kickstarter.

## Introduction
This library uses the architecture component internally.
Learn more: https://developer.android.com/topic/libraries/architecture/viewmodel.html


## Download
```groovy
dependencies {
	 compile 'com.github.IVIanuu:SimpleMVVM:LATEST-VERSION'
}
```
## Usage

This example uses a activity but it works the same for fragments(Just replace Activity with Fragment).
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
The library is meant to be used with rxjava and for convenience it includes RxLifecycle from trello so you can easily bind your observables to the lifecycle of your activities, fragments or view models. This should look like the following.

```java
@RequiresActivityViewModel(DetailActivityViewModel.class)
public class DetailActivity extends MVVMActivity<DetailActivityViewModel> {

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
	
	viewModel.loadData()
                .compose(bindToLifecycle()) // will complete the observable in onDestroy
		.compose(bindUntilEvent(Lifecycle.Event.ON_PAUSE)) // will complete the observable in onPause
                .subscribe()
    }
}
```
In view models the observable completes in onCleared which will be called when your activity or fragment will be destroyed.

```java
public class DetailActivityViewModel extends MVVMActivityViewModel {

    private final MyCoolRepository repo;

    @Inject
    public DetailActivityViewModel(@NonNull MyCoolRepository repo) {
           this.repo = repo;
	
	   repo.loadDetails()
                .compose(bindToLifecycle()) // will complete the observable onCleared
                .subscribe(data -> {
                    // do something cool with your data
                });
    }
}
```

You can observe new intents and activity results in your activity view models.

```java
 public class CommentActivityViewModel extends MVVMActivityViewModel {

        private static final int REQUEST_CODE = 1234;
        
        public CommentActivityViewModel() {
            
            intent()
                    .compose(bindToLifecycle())
                    .subscribe(intent -> {
                        // do something with the intent
                    });
            
            activityResult()
                    .compose(bindToLifecycle())
                    .filter(activityresult -> activityresult.isRequestCode(REQUEST_CODE))
                    .subscribe(activityResult -> {
                        // do something with the activity result
                    });
        }
    }
```

In fragment view model you can observe arguments.

```java
 public class ProfileFragmentViewModel extends MVVMFragmentViewModel {

        private static final int REQUEST_CODE = 1234;
        
        public ProfileFragmentViewModel() {
            
            arguments()
                    .compose(bindToLifecycle())
                    .subscribe(bundle -> {
                        // do something with the bundle
                    });
		    
        }
    }
```
