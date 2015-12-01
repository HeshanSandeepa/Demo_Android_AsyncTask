package heshansandeepa.demo_asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private String []texts;
    private String longString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_PROGRESS);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        longString = "Soviet Premier Nikita Khrushchev had ordered the development of a 100 megaton weapon in July 1961, leaving the engineers " +
                "only 14 weeks before the first test. Unlike normal thermonuclear weapons, the Tsar Bomba comprised a third stage, whereas " +
                "thermonuclear warheads usually comprise only two. By adding more stages, the explosive power of a thermonuclear bomb can" +
                " theoretically be increased indefinitely. Soviet engineers had reduced the actual yield of 100 megatons by around half to limit fallout.";
        texts = longString.split(" ");
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList()));
        new MyAsynTask().execute();
    }

    class MyAsynTask extends AsyncTask<Void, String, Void> {

        private ArrayAdapter arrayAdapter;
        private int count = 0;
        @Override
        protected void onPreExecute() {
            arrayAdapter = (ArrayAdapter) listView.getAdapter();
            setProgressBarIndeterminate(false);
            setProgressBarVisibility(true);
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (String text : texts) {
                publishProgress(text);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            arrayAdapter.add(values[0]);
            count ++;
            setProgress((int)(double)(count / texts.length) * 10000 );
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("Tag", "All added");
        }
    }

}
