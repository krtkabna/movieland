package com.wasp.rottenpotatoes.config.log;

import ch.qos.logback.core.joran.spi.NoAutoStart;
import ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP;
import java.io.File;

@NoAutoStart
public class StartupSizeTimeBasedTriggeringPolicy<E> extends SizeAndTimeBasedFNATP<E> {

    private boolean policyStarted;

    @Override
    public boolean isTriggeringEvent(File activeFile, E event) {
        if (!policyStarted) {
            policyStarted = true;
            if (activeFile.exists() && activeFile.length() > 0) {
                nextCheck = 0L;
                return true;
            }
        }
        return super.isTriggeringEvent(activeFile, event);
    }

}