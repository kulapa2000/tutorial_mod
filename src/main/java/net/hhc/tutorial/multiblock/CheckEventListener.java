package net.hhc.tutorial.multiblock;

import java.util.logging.Logger;
import java.util.EventListener;

public interface CheckEventListener extends EventListener {

     void onEvent(CheckEvent event);

}
