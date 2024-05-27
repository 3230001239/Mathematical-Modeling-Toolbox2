package com.example.boxservice.service;

import com.example.boxservice.contrller.boxController;
import com.example.boxservice.mapper.boxMapper;
import com.example.boxservice.pojo.box_model;
import com.example.boxservice.pojo.box_user;
import okhttp3.*;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils;
import weka.core.converters.Saver;

import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.Semaphore;
import java.util.concurrent.CountDownLatch;
@Service

public class boxService {
    @Autowired
    private boxMapper um;

    boxController bc = new boxController();

    public List<box_user> userLogin(String utele, String upwd) {
        List user = um.userLogin(utele,upwd);
        if(user.isEmpty()){
            return null;
        }else{
            return user;
        }
    }

    public int addUser(String uname, String upwd, String utele) {
        return um.addUser(uname,upwd,utele);
    }


    public List<box_model> modelSearch(String mname) {
        return um.modelSearch(mname);
    }

    public List<box_model> getAllModel() {
        return um.getAllModel();
    }

    public List<box_model> modelType(String mtid) {
        return um.modelType(mtid);
    }


    public static class BaseCal {
        private double[][] matrix;
        public BaseCal(double[][] matrix) {
            this.matrix = matrix;
        }
        public int getRowCount() {
            int i=0,j=0;
            while(matrix[i][j]!=0.0){
                i++;
            }
            return i;
        }
        public int getColumnCount() {
            int i=0,j=0;
            while(matrix[i][j]!=0.0){
                j++;
            }
            return j;
        }
        public double[] getRowMeans() {
            int rowCount = getRowCount();
            double[] rowMeans = new double[rowCount];
            for (int i = 0; i < rowCount; i++) {
                double sum = 0;
                for (int j = 0; j < getColumnCount(); j++) {
                    sum += matrix[i][j];
                }
                rowMeans[i] = sum / getColumnCount();
            }
            return rowMeans;
        }
        public double[] getColumnMeans() {
            int columnCount = getColumnCount();
            double[] columnMeans = new double[columnCount];
            for (int j = 0; j < columnCount; j++) {
                double sum = 0;
                for (int i = 0; i < getRowCount(); i++) {
                    sum += matrix[i][j];
                }
                columnMeans[j] = sum / getRowCount();
            }
            return columnMeans;
        }
        public double[] getColumnStdDevs() {
            int columnCount = getColumnCount();
            double[] columnStdDevs = new double[columnCount];
            double[] columnMeans = getColumnMeans();
            for (int j = 0; j < columnCount; j++) {
                double sum = 0;
                for (int i = 0; i < getRowCount(); i++) {
                    sum += Math.pow(matrix[i][j] - columnMeans[j], 2);
                }
                columnStdDevs[j] = Math.sqrt(sum / (getRowCount() - 1));
            }
            return columnStdDevs;
        }
        public double[][] getPearsonCorrelationMatrix() {
            int rowCount = getRowCount();
            int columnCount = getColumnCount();
            double[][] correlationMatrix = new double[columnCount][columnCount];
            double[] columnMeans = getColumnMeans();
            for (int i = 0; i < columnCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    double numerator = 0;
                    double denominator1 = 0;
                    double denominator2 = 0;
                    for (int k = 0; k < rowCount; k++) {
                        numerator += (matrix[k][i] - columnMeans[i]) * (matrix[k][j] - columnMeans[j]);
                        denominator1 += Math.pow(matrix[k][i] - columnMeans[i], 2);
                        denominator2 += Math.pow(matrix[k][j] - columnMeans[j], 2);
                    }
                    correlationMatrix[i][j] = numerator / Math.sqrt(denominator1 * denominator2);
                }
            }
            return correlationMatrix;
        }
    }



    public void performKMeansClustering(String arffFilePath) throws Exception {
        Instances data = ConverterUtils.DataSource.read(arffFilePath);
        SimpleKMeans clusterer = new SimpleKMeans();
        clusterer.setNumClusters(3); // 设置聚类数为3，可以根据需要调整
        clusterer.buildClusterer(data);
        // ... 聚类结果的输出或处理逻辑 ...
    }

    public static void convertCsvToArff(String csvFilePath, String arffFilePath) {
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(csvFilePath));
            writer = new BufferedWriter(new FileWriter(arffFilePath));

            // ��ȡCSV�ļ��ĵ�һ�У�ͨ����������
            String line = reader.readLine();
            String[] headers = line.split(",");
            List<String> featureHeaders = new ArrayList<String>();
            for (int i = 0; i < headers.length - 1; i++) { // ���һ���Ƿ�����������Բ���������
                featureHeaders.add(headers[i]);
            }

            // д��ARFF�ļ�ͷ
            writer.write("@RELATION your_dataset\n\n");

            // д����������
            for (String header : featureHeaders) {
                writer.write("@ATTRIBUTE " + header.trim() + " NUMERIC\n");
            }

            // д��������ԣ������Ƕ��������⣬���Ϊ0��1��
            writer.write("@ATTRIBUTE class {0,1}\n\n");

            // д�����ݲ���
            writer.write("@DATA\n");
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                for (int i = 0; i < values.length - 1; i++) { // ���һ���Ƿ���������Ժ���
                    writer.write(values[i].trim() + ",");
                }
                // д����������������
                writer.write(values[values.length - 1].trim() + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static final class goresult {
        static final String errorstr = "{\"error_msg\":\"Open api qps request limit reached\",\"error_code\":18}";
        static final JSONObject errorjob = new JSONObject(errorstr);
        static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();
        public String[] text;
       public int[] percent;
        public CountDownLatch latch;

       public  goresult()
        {
            this.percent = new int[3];
            this.percent[0] = 0;
            this.percent[1] = 0;
            this.percent[2] = 0;
        }

       public  void settext(String[] text)
        {
            this.text = text;
        }

        // 添加一个Semaphore来控制并发请求数
        private static final Semaphore semaphore = new Semaphore(10); // 允许同时进行的最大请求数

        public void api(String token) {
            latch = new CountDownLatch(text.length);
            for (String e : this.text) {
                semaphore.acquireUninterruptibly(); // 获取一个许可，如果没有则等待
                makeAsyncRequest(token, e);
            }
            try {
                latch.await(); // 等待所有请求完成
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        private void makeAsyncRequest(String token, String text) {

            MediaType mediaType = MediaType.parse("application/json");
            String jsonBody = "{\"text\":\"" + text + "\"}";
            RequestBody body = RequestBody.create(mediaType, jsonBody);
            String url = "https://aip.baidubce.com/rpc/2.0/nlp/v1/sentiment_classify?access_token=" + token + "&charset=UTF-8";
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build();

            HTTP_CLIENT.newCall(request).enqueue(new Callback() {

                public void onFailure(Call call, IOException e) {
                    semaphore.release(); // 释放一个许可
                    latch.countDown();
                    e.printStackTrace();

                }


                public void onResponse(Call call, Response response) throws IOException {
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    }
                    // 处理响应...


                    JSONObject js = new JSONObject(response.body().string());
                    System.out.println(js);
                    //System.out.println(errorjob);
                    if((js.toString()).equals(errorstr))
                    {
                        makeAsyncRequest(token,text);
                    }
                    else
                    {
                    JSONArray ja = js.getJSONArray("items");
                    JSONObject jobj = ja.getJSONObject(0);
                    int num = jobj.getInt("sentiment");
                    percent[num]++;
                        //System.out.println(errorjob);
                    response.close(); // 确保关闭响应体
                    semaphore.release(); // 释放一个许可
                    latch.countDown();}
                }
            });
        }

    }
    }





