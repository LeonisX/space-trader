package spacetrader.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Timer {

    private EventHandler<Object, EventArgs> tick;

    private final javax.swing.Timer timer = new javax.swing.Timer(0, new ActionListener() {
        public void actionPerformed(ActionEvent arg0) {
            tick.handle(Timer.this, null);
            stop();
        }
    });

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void setInterval(int interval) {
        timer.setDelay(interval);
        timer.setInitialDelay(interval);
    }

    public void setTick(EventHandler<Object, EventArgs> tick) {
        this.tick = tick;
    }
}
