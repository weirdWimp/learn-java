package org.learn.something.concurrent.pattern.activeobject;

public class ActiveObjectFactory {

    public static ActiveObject createActiveObject() {
        return new ActiveObjectImpl();
    }

}
