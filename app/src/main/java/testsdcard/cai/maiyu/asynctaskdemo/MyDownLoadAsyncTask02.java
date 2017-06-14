package testsdcard.cai.maiyu.asynctaskdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
 * 第二个AsyncTask的使用：增加了进度条的进度显示
 * @param  --- 参数类型，如访问地址String类的url
 * @progress---进度显示类型，如Integer(没有则设为void)
 * @result----返回结果类型,如byte[]
 */

public class MyDownLoadAsyncTask02 extends AsyncTask<String , Integer , byte[]> {

    //上下文对象
    private Context mContext;
    //图片的显示
    private ImageView mImageView;

    //增加窗口进度条
    private ProgressDialog pd ;


    /**
     * 初始化上下文
     * @param context
     * @param imageView
     */
    public MyDownLoadAsyncTask02(Context context , ImageView imageView){
        mContext = context;
        mImageView = imageView ;
    }


    /**
     * 主线程：在执行doInBackground前执行
     */
    @Override
    protected void onPreExecute() {

        //创建ProgressDialog对象
        pd  =   new ProgressDialog(mContext);
        //设置标题
        pd.setTitle("正在下载");
        //设置style:为水平线
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //进度条窗口的显示
        pd.show();

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

                //获取总长度
                int totalLength = conn.getContentLength();
                //初始化当前进度0
                int currentLength = 0;


                //定义temp
                int temp = 0;
                //创建byte[]数组，初始化1024
                byte[] data = new byte[1024];

                //读取输入流
                while((temp = ins.read(data)) != -1){

                    //增加
                    currentLength += temp;
                    //获取进度百分比
                    int progress = (int)((currentLength/(float)totalLength)*100);
                    //调用publishProgress方法会把进度推送到主线程的onProgressUpdate(progress)方法，
                    //即会执行onProgressUpdate(progress)方法
                    publishProgress(progress);

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
     * 主线程中执行：当在doInBackground方法执行publishProgress方法时
     * 会调用此方法
     * @param values
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        //设置进度条的显示
        pd.setProgress(values[0]);
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


        //取消进度条的显示
        pd.dismiss();
    }
}
