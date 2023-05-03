package net.hhc.tutorial.multiblock;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class CheckEventDispatcher {

    private static final Logger LOGGER = LogUtils.getLogger();
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
        LOGGER.info("length:"+listeners.size());
        for (CheckEventListener listener : listeners)
        {
            listener.onEvent(event);
        }
    }
}
