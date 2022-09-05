package com.aliengui.event.view;

import com.aliengui.api.view.View;
import com.aliengui.event.Event;

public class ViewEvent extends Event {
    protected View view;

    public ViewEvent(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }
}
