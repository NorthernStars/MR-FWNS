package remotecontrol;

import java.io.Serializable;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

@Plugin(name = "StubAppender", category = "Core", elementType = "appender", printObject = true)
public final class StubAppender extends AbstractAppender {

    private StubAppender(String name, final Layout<? extends Serializable> layout, Filter filter) {
        super(name, filter, layout);
    }

    public void append(LogEvent event) {
        RemoteControlServer.getInstance().writeToLoglistener( event );
    }

    @PluginFactory
    public static StubAppender createAppender(
         @PluginElement("Layout") Layout<? extends Serializable> layout,
         @PluginAttribute("name") String name,
         @PluginElement("filters") Filter filter) {
        return new StubAppender(name, layout, filter);
    }
}