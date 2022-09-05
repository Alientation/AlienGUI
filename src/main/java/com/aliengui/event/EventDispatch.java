package com.aliengui.event;

public interface EventDispatch<T extends EventListener> {
    void dispatch(T listener);
}
