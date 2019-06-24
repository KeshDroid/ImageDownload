package lk.ac.kln.asynctask;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void download(View view){
        ImageDownloadClass ImageDownloadClass = new ImageDownloadClass();
        ImageDownloadClass.execute(getString(R.string.image_url));
    }

    private class ImageDownloadClass extends AsyncTask<String, Integer, Bitmap>{

        private ProgressDialog progressDialog = null;
        @Override
        protected  void onPreExecute(){
            super.onPreExecute();
            progressDialog = new ProgressDialog( MainActivity.this);
            progressDialog.setMessage("Loading....");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(false);
            progressDialog.setProgress(0);
            progressDialog.setMax(100);
            progressDialog.show();

        }

        @Override
        protected Bitmap doInBackground(String[] strings){
            Bitmap bitmap = null;
            try{
                URL imageURL = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) imageURL.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream is  = connection.getInputStream();

                for (int i =0; i<100;i++){
                    Integer arr[] ={i};
                    publishProgress(arr);
                    
                }

                bitmap = BitmapFactory.decodeStream(is,null,null);

            }catch(IOException e){
                e.printStackTrace();
            }

            return bitmap;



        }

        @Override
        protected void onProgressUpdate(Integer[] integers){
            progressDialog.setProgress(integers [0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);
            ImageView iv = findViewById(R.id.imageView);
            progressDialog.hide();
            iv.setImageBitmap(bitmap);

        }

    }
}
