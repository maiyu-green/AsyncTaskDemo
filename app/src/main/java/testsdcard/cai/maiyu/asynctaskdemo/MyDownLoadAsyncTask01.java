package testsdcard.cai.maiyu.asynctaskdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by maiyu on 2017/6/14.
 * @param  --- 参数类型，如访问地址String类的url
 * @progress---进度显示类型，如Integer(没有则设为void)
 * @result----返回结果类型,如byte[]
 */

public class MyDownLoadAsyncTask01 extends AsyncTask<String , Integer , byte[]> {

    //上下文对象
    private Context mContext;
    //图片的显示
    private ImageView mImageView;



    /**
     * 初始化上下文
     * @param context
     */
    public MyDownLoadAsyncTask01(Context context , ImageView imageView){
        mContext = context;
        mImageView = imageView;
    }


    /**
     * 主线程：在执行doInBackground前执行
     */
    @Override
    protected void onPreExecute() {


    }


    /**
     * 辅助线程：执行耗时操作
     * 若调用publishProgress(参数）方法--则会触发主线程的onProgressUpdate(参数)方法
     * @param params    --- AsyncTask的第一个参数
     * @return   --- AsyncTask的第三个参数
     */
    @Override
    protected byte[] doInBackground(String... params) {

        //创建返回结果类型对象
        byte[] result = null;

        //定义URL
        URL url = null;
        try {
            //创建URL对象
            url = new URL(params[0]);
            //调用url.openConnection()方法来创建HttpURLConnection对象
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置支持input
            conn.setDoInput(true);
            //设置get请求
            conn.setRequestMethod("GET");
            //开始连接
            conn.connect();

            //创建ByteArrayOutputStream对象，用于写入结果
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();


            //获取responseCode,判断是否为200，为200则请求成功
            int responseCode = conn.getResponseCode();
            if(responseCode == 200){

                //获取输入流
                InputStream ins = conn.getInputStream();
                //定义temp
                int temp = 0;

                //创建byte[]数组，初始化1024
                byte[] data = new byte[1024];

                //读取输入流
                while((temp = ins.read(data)) != -1){

                    //写入到outputStream
                    outputStream.write(data , 0 , temp);
                    //缓冲
                    outputStream.flush();

                }

                //把outputStream转化为byte数组赋给result
                result = outputStream.toByteArray();
                //关闭流
                ins.close();
                outputStream.close();

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //返回结果
        return result;
    }


    /**
     * 主线程：
     * 在doInBackground方法执行完毕后执行此方法
     * @param result --- 结果，第3参数
     */
    @Override
    protected void onPostExecute(byte[] result) {

        //判断结果是否为null，并判断长度是否为0
        if(result != null && result.length != 0){

            //转化图片
            Bitmap bitmap = BitmapFactory.decodeByteArray(result , 0 , result.length);
            //设置显示图片
            mImageView.setImageBitmap(bitmap);

        }else {

            //加载出错
            Toast.makeText(mContext , "下载图片出错" , Toast.LENGTH_SHORT).show();
        }

    }
}
