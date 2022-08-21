package com.alientation.aliengui.event.view;

import com.alientation.aliengui.api.view.View;
import com.alientation.aliengui.event.Event;

public class ViewEvent extends Event {
    protected View view;

    public ViewEvent(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }
}
