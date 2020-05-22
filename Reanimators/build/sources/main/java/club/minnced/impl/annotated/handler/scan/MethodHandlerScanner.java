package club.minnced.impl.annotated.handler.scan;

import club.minnced.filter.EventFilterScanner;
import club.minnced.handler.EventHandler;
import club.minnced.handler.scan.EventHandlerScanner;
import club.minnced.impl.annotated.filter.MethodFilterScanner;
import club.minnced.impl.annotated.handler.MethodEventHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

/**
 * An implementation of {@link EventHandlerScanner} that locates
 * all methods of a class that are event listeners.
 *
 * @author Daniel
 * @since May 31, 2017
 */
public final class MethodHandlerScanner implements EventHandlerScanner {
    private final AnnotatedListenerPredicate annotatedListenerPredicate =
            new AnnotatedListenerPredicate();
    private final EventFilterScanner<Method> filterScanner = new MethodFilterScanner();

    @Override
    public Map<Class<?>, Set<EventHandler>> locate(final Object listenerContainer) {
        final Map<Class<?>, Set<EventHandler>> eventHandlers = new HashMap<>();
        Stream.of(listenerContainer.getClass().getDeclaredMethods())
                .filter(annotatedListenerPredicate).forEach(method -> eventHandlers
                .computeIfAbsent(method.getParameterTypes()[0], obj -> new TreeSet<>())
                .add(new MethodEventHandler(listenerContainer, method,
                        filterScanner.scan(method))));
        return eventHandlers;
    }
}
