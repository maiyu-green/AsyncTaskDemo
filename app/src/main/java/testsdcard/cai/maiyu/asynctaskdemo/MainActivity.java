package testsdcard.cai.maiyu.asynctaskdemo;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private ImageView mImgView;
    private  String imgPath = "http://img3.3lian.com/2013/s1/18/d/3.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImgView = (ImageView)findViewById(R.id.load_img_view);
    }


    /**
     * 无进度条的下载
     * @param view
     */
    public void loadImageView1(View view){

        new MyDownLoadAsyncTask01(MainActivity.this , mImgView).execute(imgPath);

    }

    /**
     * 有进度条的下载
     * @param view
     */
    public void loadImageView2(View view){

        new MyDownLoadAsyncTask01(MainActivity.this , mImgView).execute(imgPath);

    }

    /**
     * 可以取消的下载
     * @param view
     */
    public void loadImageView3(View view){

        new MyDownLoadAsyncTask01(MainActivity.this , mImgView).execute(imgPath);

    }



}
