package smartTerminal.com.dc.esc.client.zookeeper.support;

import lombok.extern.slf4j.Slf4j;
import smartTerminal.com.dc.esc.client.zookeeper.ChildListener;
import smartTerminal.com.dc.esc.client.zookeeper.StateListener;
import smartTerminal.com.dc.esc.client.zookeeper.ZookeeperClient;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * author Hao
 * date 2025/9/2 10:50
 */
@Slf4j
public abstract class AbstractZookeeperClient<TargetChildListener> implements ZookeeperClient {
    private final String url;
    private final Set<StateListener> stateListeners = new CopyOnWriteArraySet<>();
    private final ConcurrentMap<String, ConcurrentMap<ChildListener, TargetChildListener>> childListeners = new ConcurrentHashMap<>();
    private volatile boolean closed = false;

    public AbstractZookeeperClient(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    @Override
    public void create(String path, boolean ephemeral) {
        int i = path.lastIndexOf(47);
        if (i > 0) {
            this.create(path.substring(0, i), false);
        }
        if (ephemeral) {
            this.createEphemeral(path);
        } else {
            this.createPersistent(path);
        }
    }

    public void addStateListener(StateListener listener) {
        this.stateListeners.add(listener);
    }

    @Override
    public void removeStateListener(StateListener var1) {
        this.stateListeners.remove(var1);
    }

    public Set<StateListener> getSessionListeners() {
        return this.stateListeners;
    }

    public List<String> addChildListener(String path, ChildListener listener) {
        ConcurrentMap<ChildListener, TargetChildListener> listeners = this.childListeners.get(path);
        if (listeners == null) {
            this.childListeners.putIfAbsent(path, new ConcurrentHashMap<>());
            listeners = this.childListeners.get(path);
        }
        TargetChildListener targetListener = listeners.get(listener);
        if (targetListener == null) {
            listeners.putIfAbsent(listener, this.createTargetChildListener(path, listener));
            targetListener = listeners.get(listener);
        }
        return this.addTargetChildListener(path, targetListener);
    }

    public void removeChildListener(String path, ChildListener listener) {
        ConcurrentMap<ChildListener, TargetChildListener> listeners = this.childListeners.get(path);
        if (listeners != null) {
            TargetChildListener targetListener = listeners.remove(listener);
            if (targetListener != null) {
                this.removeTargetChildListener(path, targetListener);
            }
        }
    }

    protected void stateChanged(int state) {
        Iterator var2 = this.stateListeners.iterator();

        while (var2.hasNext()) {
            StateListener sessionListener = (StateListener) var2.next();
            sessionListener.stateChanged(state);
        }
    }

    public void close() {
        if (!this.closed) {
            this.closed = true;
            try {
                this.doClose();
            } catch (Throwable var2) {
                log.warn("Failed to close zookeeper client " + this.url + ", cause: " + var2.getMessage(), var2);
            }
        }
    }

    protected abstract void doClose();

    protected abstract void createPersistent(String var1);

    protected abstract void createEphemeral(String var1);

    protected abstract TargetChildListener createTargetChildListener(String var1, ChildListener var2);

    protected abstract List<String> addTargetChildListener(String var1, TargetChildListener var2);

    protected abstract void removeTargetChildListener(String var1, TargetChildListener var2);
}
