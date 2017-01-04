package can.practice.com.canapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends Activity {

    boolean running = false;
    StringBuilder text = new StringBuilder();
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.text);
        tv.setMovementMethod(new ScrollingMovementMethod());
        running = true;
        new CanGenerator().start();
    }

    @Override
    protected void onPause() {
        running = false;
        super.onPause();
    }

    class CanGenerator extends Thread {
        @Override
        public void run() {

            while (running) {
                final String message = generate();

                try {
                    Thread.sleep(1500);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            text.append("\n");
                            text.append(message);
                            tv.setText(text.toString());
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }

        public String generate() {
            int i;
            String length = "08";
            String message = "0415FE";
            String id = "04";

            StringBuilder canMessage = new StringBuilder(id);
            canMessage.append(message);
            canMessage.append(id);
            canMessage.append(randomoffset());
            return canMessage.toString();

        }

        private String randomoffset() {
            Random generator = new Random();
            int offset = generator.nextInt(99);
            return String.valueOf(offset);
        }
    }
}
