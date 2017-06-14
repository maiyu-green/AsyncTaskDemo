# AsyncTaskDemo
AsyncTask的Demo
AsyncTask的使用例子：3个
（1）使用AsyncTask下载显示一张图片，
（2）增加下载进度条的显示
（3）增加下载过程中取消的监听

（1）主线程                                                                          辅助线程
onPreExecute()方法：执行耗时操作前调用                                      doInBackground()方法：耗时操作

onProgressUpdate()方法                                       <--------------   调用publishProgress时会触发左边方法

onCancelled()方法(在onPreExecute里掉为ProgressDialog设置
监听时调用cancel(true)方法时会触发此方法，
一旦触发此方法，将中断，不会再执行下面的onPostExecute()方法       ------------------


onPostExecute()方法：在doInBacground（）方法执行完毕后调用此方法
