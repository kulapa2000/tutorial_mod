package net.hhc.tutorial.multiblock;

import java.util.ArrayList;
import java.util.List;

public class CheckEventDispatcher {

    private List<CheckEventListener> listeners = new ArrayList<>();

    public void addListener(CheckEventListener listener)
    {
        listeners.add(listener);
    }

    public void removeListener(CheckEventListener listener)
    {
        listeners.remove(listener);
    }

    public void dispatchEvent(CheckEvent event)
    {
        for (CheckEventListener listener : listeners)
        {
            listener.onEvent(event);
        }
    }
}
