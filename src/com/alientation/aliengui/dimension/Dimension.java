package com.alientation.aliengui.dimension;

import com.alientation.aliengui.api.view.View;
@SuppressWarnings("unused")
public interface Dimension {

    int val();
    void registerDependency(View v); //Views that use this as a dimension
    void unregisterDependency(View v);

}
