package yousef.example.com.baking.idlingResource;

import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleIdlingResource implements IdlingResource {
    AtomicBoolean idlingState = new AtomicBoolean(true);
    ResourceCallback mCallback;

    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return idlingState.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback = callback;
    }

    public void setIdlingState(boolean idlingState) {
        this.idlingState.set(idlingState);

        if(idlingState  && mCallback != null){
            mCallback.onTransitionToIdle();
        }
    }
}
