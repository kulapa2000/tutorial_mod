package net.hhc.tutorial.multiblock;

public class CheckEventHandler {

    public static final CheckEventHandler INSTANCE = new CheckEventHandler();
    public final CheckEventDispatcher checkEventDispatcher = new CheckEventDispatcher();

    public CheckEventHandler() {}

    public static CheckEventHandler getInstance() {
        return INSTANCE;
    }

    public void addCheckEventListener(CheckEventListener listener) {
        checkEventDispatcher.addListener(listener);
    }

    public void removeCheckEventListener(CheckEventListener listener) {
        checkEventDispatcher.removeListener(listener);
    }

    public void dispatchCheckEvent(CheckEvent event) {
        checkEventDispatcher.dispatchEvent(event);
    }
}
