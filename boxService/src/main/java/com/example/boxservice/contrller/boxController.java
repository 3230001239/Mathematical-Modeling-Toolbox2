package com.example.boxservice.contrller;

import com.example.boxservice.pojo.box_model;
import com.example.boxservice.pojo.box_user;
import com.example.boxservice.service.boxService;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.RombergIntegrator;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.apache.poi.ss.usermodel.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.Logistic;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils;
import weka.core.converters.Saver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.boxservice.service.boxService.convertCsvToArff;



@RestController
public class boxController {

    @Autowired
    private boxService us;

    @GetMapping("/login")
    public HashMap<String, Object> userLogin(@RequestParam("utele") String utele, @RequestParam("upwd") String upwd) {
        List<box_user> user = us.userLogin(utele, upwd);
        HashMap<String, Object> res = new HashMap<>();
        if (user == null) {
            res.put("code", 0);
            res.put("desc", "用户名或密码错误！");
            res.put("data", null);
        } else {
            res.put("code", 200);
            res.put("desc", "登陆成功");
            res.put("data", user);
        }
        return res;
    }

    @GetMapping("/addUser")
    public HashMap<String, Object> addUser(@RequestParam("uname") String uname, @RequestParam("upwd") String upwd, @RequestParam("utele") String utele) {
        int count = us.addUser(uname, upwd, utele);
        HashMap<String, Object> res = new HashMap<>();
        if (count > 0) {
            res.put("code", 200);
            res.put("desc", "注册成功!");
        } else {
            res.put("code", 0);
            res.put("desc", "注册失败!");
        }
        return res;
    }

   @PostMapping("/upload")
    public HashMap<String, Object> uploadFile(@RequestParam("file") MultipartFile file) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            double[][] array = new double[1000][1000];
            int i = 0, j;
            for (Row row : sheet) {
                j = 0;
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.NUMERIC) {
                        array[i][j] = cell.getNumericCellValue();
                        j++;
                    }
                }
                i++;
            }
            workbook.close();
            boxService.BaseCal analysis = new boxService.BaseCal(array);
            double[][] pearsonCorrelationMatrix = analysis.getPearsonCorrelationMatrix();
            res.put("Row count", analysis.getRowCount());
            res.put("Column count", analysis.getColumnCount());
            res.put("Row means", Arrays.toString(analysis.getRowMeans()));
            res.put("Column means", Arrays.toString(analysis.getColumnMeans()));
            res.put("Column standard deviations",Arrays.toString(analysis.getColumnStdDevs()));
            res.put("Pearson correlation matrix", pearsonCorrelationMatrix);
        } catch (Exception e) {
            e.printStackTrace();
            res.put("desc","解析失败");
        }
        return res;
    }
    //一元线性回归
    @PostMapping("/uploadm11")
    public HashMap<String,Object> uploadFilem11(@RequestParam("file") MultipartFile file) {
        HashMap<String,Object> res = new HashMap<>();
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            int i=0,j,a = 0,k = 0;
            double[][] array = new double[1000][1000];
            for (Row row : sheet) {
                j = 0;
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.NUMERIC) {
                        array[i][j] = cell.getNumericCellValue();
                        j++;
                        a=j;
                    }
                }
                i++;
            }
            workbook.close();
            double[] xValues = new double[a]; // �Ա���
            double[] yValues = new double[a]; // �����
            for(k=0;k<a;k++){
                xValues[k]=array[0][k];
                yValues[k]=array[1][k];
            }
            SimpleRegression regression = new SimpleRegression();
            for ( i = 0; i < a; i++) {
                regression.addData(xValues[i], yValues[i]); // ÿ�����һ������
            }
            res.put("k",regression.getSlope());
            res.put("b",regression.getIntercept());
            res.put("R2",regression.getRSquare());
            res.put("x",xValues);
            res.put("y",yValues);
            res.put("desc","解析成功");
        } catch (Exception e) {
            e.printStackTrace();
            res.put("desc","失败!");
        }
        return res;
    }
    //多项式回归
    @PostMapping("/uploadm12")
    public HashMap<String,Object> uploadFilem12(@RequestParam("file") MultipartFile file) {
        HashMap<String,Object> res = new HashMap<>();
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            int i=0,j,a=0,k=0;
            double[][] array = new double[1000][1000];
            for (Row row : sheet) {
                j = 0;
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.NUMERIC) {
                        array[i][j] = cell.getNumericCellValue();
                        j++;
                        if(j>a) a=j;
                    }
                }
                i++;
            }
            workbook.close();
            double[] xData = new double[a];
            double[] yData = new double[a];
            for(k=0;k<a;k++){
                xData[k]=array[0][k];
                yData[k]=array[1][k];
            }
            int degree = (int) array[i-1][0];
            PolynomialCurveFitter fitter = PolynomialCurveFitter.create(degree);
            WeightedObservedPoints obs = new WeightedObservedPoints();
            for ( i = 0; i < a; i++) {
                obs.add(xData[i], yData[i]);
            }
            double[] coeff = fitter.fit(obs.toList());

            for ( i = 0; i <= degree; i++) {
                System.out.println("Coefficient " + i + ": " + coeff[i]);
            }
            res.put("x",xData);
            res.put("y",yData);
            res.put("Coefficient",coeff);
            res.put("desc","计算成功");
        } catch (Exception e) {
            e.printStackTrace();
            res.put("desc","计算失败");
        }
        return res;
    }
    //逻辑回归二分类
    @PostMapping("/uploadm13")
    public HashMap<String,Object> handleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {
        HashMap<String, Object> res = new HashMap<>();
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            // 保存文件到服务器端临时目录
            File tempFile = File.createTempFile("temp_", fileName);
            file.transferTo(tempFile);

            // 根据文件名生成对应的 ARFF 文件名
            String arffFileName = fileName.replace(".csv", ".arff");
            File arffFile = new File(tempFile.getParent(), arffFileName);

            // 将 CSV 文件转换为 ARFF 文件
            convertCsvToArff(tempFile.getAbsolutePath(), arffFile.getAbsolutePath());

            ConverterUtils.DataSource source = new ConverterUtils.DataSource(arffFile.getAbsolutePath());
            Instances data = source.getDataSet();

            if (data.classIndex() == -1) {
                data.setClassIndex(data.numAttributes() - 1);
            }
            Classifier logistic = new Logistic();
            logistic.buildClassifier(data);
            Evaluation eval = new Evaluation(data);
            Random rand = new Random(1);
            eval.crossValidateModel(logistic, data, 10, rand);
            System.out.println(eval.toSummaryString("\nResults\n======\n", false));
            System.out.println(logistic.toString());
            String ss = logistic.toString();
            Pattern ridgePattern = Pattern.compile("Logistic Regression with ridge parameter of (\\d+\\.\\d+E[-+]?\\d+)");
            Matcher ridgeMatcher = ridgePattern.matcher(ss);
            String ridgeParameter = null;
            if (ridgeMatcher.find()) {
                ridgeParameter = ridgeMatcher.group(1);
                System.out.println("Ridge Parameter: " + ridgeParameter);
            }
            String logisticOutput = ss;
            String coefficientsHeader = "Coefficients...";
            int startIndex = logisticOutput.indexOf(coefficientsHeader);
            StringBuilder coefficients = null;
            if (startIndex != -1) {
                startIndex += coefficientsHeader.length();
                startIndex = logisticOutput.indexOf('\n', startIndex) + 1;
                startIndex = logisticOutput.indexOf('\n', startIndex) + 1;
                coefficients = new StringBuilder();
                String line;
                Scanner scanner = new Scanner(logisticOutput.substring(startIndex));
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine().trim();
                    if (line.contains("Variable: 0")) {
                        continue;
                    }
                    if (line.startsWith("Odds Ratios...")) {
                        break;
                    }
                    coefficients.append(line).append("\n");
                }
                scanner.close();
                System.out.println(coefficients.toString());
            } else {
                System.out.println("Coefficients section not found.");
            }
            String oddsRatiosHeader = "Odds Ratios...";
            startIndex = logisticOutput.indexOf(oddsRatiosHeader);
            StringBuilder oddsRatios = null;
            if (startIndex != -1) {
                startIndex += oddsRatiosHeader.length();
                startIndex = logisticOutput.indexOf('\n', startIndex) + 1;
                startIndex = logisticOutput.indexOf('\n', startIndex) + 1;
                oddsRatios = new StringBuilder();
                String line;
                Scanner scanner = new Scanner(logisticOutput.substring(startIndex));
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine().trim();
                    if (line.contains("Variable: 0")) {
                        continue;
                    }
                    if (line.contains("Variable: 0")) {
                        continue;
                    }
                    if (line.isEmpty()) {
                        break;
                    }
                    oddsRatios.append(line).append("\n");
                }
                System.out.println(oddsRatios.toString());
            } else {
                System.out.println("Odds Ratios section not found.");
            }
            res.put("out1", eval.toSummaryString("\nResults\n======\n", false));
            res.put("out2", ridgeParameter);
            res.put("out3", coefficients.toString());
            res.put("out4", oddsRatios.toString());
        } else {
            res.put("desc", "失败");
        }
        return res;
    }
    @PostMapping("/uploadm31")
    public HashMap<String, Object> handleFileUploadm31(@RequestParam("file") MultipartFile file) throws Exception {
        HashMap<String, Object> res = new HashMap<>();
        if (!file.isEmpty()) {
            try (InputStream inputStream = file.getInputStream()) {
                // 创建CSVLoader和读取数据
                CSVLoader loader = new CSVLoader();
                loader.setSource(inputStream);
                Instances dat = loader.getDataSet();

                // 将数据保存为临时ARFF文件（如果需要）
                File tempArffFile = File.createTempFile("kmeansplus", ".arff");
                Saver saver = new ArffSaver();
                saver.setFile(tempArffFile);
                saver.setInstances(dat);
                saver.writeBatch();

                // 从临时ARFF文件中读取数据
                Instances data = new Instances(new FileReader(tempArffFile));


                SimpleKMeans clusterer = new SimpleKMeans();
                 clusterer.setNumClusters(3);
                 clusterer.buildClusterer(data);
                 Map<Integer, List<String>> clusteredInstances = new HashMap<Integer, List<String>>();
                 String attributeName = data.attribute(0).name();
                 for (int i = 0; i < data.numInstances(); i++) {
                     int clusterId = clusterer.clusterInstance(data.instance(i));
                     String instanceValue = data.instance(i).stringValue(0);
                     if (!clusteredInstances.containsKey(clusterId)) {
                         clusteredInstances.put(clusterId, new ArrayList<String>());
                     }
                     clusteredInstances.get(clusterId).add(instanceValue);
                 }
                 int m = 0, n = 0,j=0;
                 String[][] array = new String[1000][1000];
                 for (Map.Entry<Integer, List<String>> entry : clusteredInstances.entrySet()) {
                     int clusterId = entry.getKey();
                     List<String> instances = entry.getValue();
                     n=0;
                     array[m][n]=("Cluster " + clusterId + ":");
                     n=1;
                     System.out.println("Cluster " + clusterId + ":");
                     for (String instance : instances) {
                         System.out.println(attributeName + " = " + instance);
                         array[m][n]=(attributeName + " = " + instance);
                         n++;
                         j++;
                     }
                    // System.out.println();
                     m++;
                 }
                 int m1=m,n1=n,j1=j+m;
                 String[] a1 = new String[j1];
                 j=0;
                 for (m = 0; m < m1; m++) {
                     for ( n = 0; n <n1 ; n++) {
                         if(array[m][n]!=null){
                             a1[j]=array[m][n];
                             j++;
                         }
                     }
                 }
                     if (!tempArffFile.delete()) {
                         tempArffFile.deleteOnExit();
                     }

                     res.put("a1",a1);
                 res.put("desc", "成功");
                 } catch (IOException e) {
                     // 处理异常
                     res.put("desc", "文件处理失败: " + e.getMessage());
            }
        } else {
            res.put("desc", "失败");
        }
        return res;
    }
    //一致性检验
    @PostMapping("/uploadm41")
    public HashMap<String, Object> uploadFilem41(@RequestParam("file") MultipartFile file) {
        HashMap<String, Object> res = new HashMap<>();
        String c;
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            int i=0,j;
            double[][] array = new double[1000][1000];
            for (Row row : sheet) {
                j=0;
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.NUMERIC) {
                        array[i][j]=cell.getNumericCellValue();
                        j++;
                    }
                }
                i++;
            }
            workbook.close();
            RealMatrix matrix = MatrixUtils.createRealMatrix(array);
            double[] RI= {0,0,0,0.52,0.89,1.12,1.26,1.36,1.41,1.46,1.49,1.52,1.54,1.56,1.58,1.59};
            EigenDecomposition ed = new EigenDecomposition(matrix);
            double[] realEigenvalues = ed.getRealEigenvalues();
            RealMatrix eigenvectorsMatrix = ed.getV();
            double[][] eigenvectors = eigenvectorsMatrix.getData();
            double maxrealEigenvalues=realEigenvalues[0];
            for (double eigenvalue : realEigenvalues) {
                if(eigenvalue>maxrealEigenvalues)
                {
                    maxrealEigenvalues=eigenvalue;
                }
            }
            int n=i;
            double CI=(maxrealEigenvalues-n)/(n-1);
            double CR=CI/RI[n];
            if (CR<0.1){
                c="通过检验,可视为一致性矩阵";
            }else{
                c="未通过检验,非一致性矩阵";
            }
            res.put("CR",CR);
            res.put("cc",c);
        } catch (Exception e) {
            e.printStackTrace();
            res.put("desc","文件解析失败");
        }
        return res;
    }

    //数值积分
    @PostMapping("/uploadm51")
    public HashMap<String,Object> uploadFilem51(@RequestParam("file") MultipartFile file) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            int i=0,j;
            double[][] array = new double[1000][1000];
            for (Row row : sheet) {
                j=0;
                for (Cell cell : row) {
                    if (cell.getCellType() == CellType.NUMERIC) {
                        array[i][j]=cell.getNumericCellValue();
                        j++;
                    }
                }
                i++;
            }
            workbook.close();
            double a=array[0][0],b=array[0][1],c=array[0][2],e=array[0][3],f=array[0][4],g=array[0][5];
            double up= array[1][1];
            double low = array[1][0];
            UnivariateFunction function = new UnivariateFunction() {
                public double value(double x)
                {
                    return a+b*x+c*Math.pow(x,2)+e*Math.pow(x,3)+f*Math.exp(x)+g*Math.sin(x);
                }
            };
            SimpsonIntegrator integrator = new SimpsonIntegrator();
            double output = integrator.integrate(Integer.MAX_VALUE, function, low, up);
            System.out.println("outcome:"+output);
            RombergIntegrator integrator_two = new RombergIntegrator();
            double output_two = integrator_two.integrate(Integer.MAX_VALUE, function, low, up);
            System.out.println("outcome:"+output_two);
            res.put("output",output);
            res.put("output_two",output_two);
    } catch (Exception e) {
            e.printStackTrace();
            res.put("desc","失败");
        }
        return res;
    }
    @GetMapping("/getAllModel")
    public HashMap<String,Object> getAllModel(){
        List<box_model> bm = us.getAllModel();
        HashMap<String,Object> res= new HashMap<>();
        res.put("data",bm);
        return res;
    }
    @GetMapping("/modelSearch")
    public HashMap<String,Object> modelSearch(@RequestParam("mname") String mname){
        HashMap<String,Object> res = new HashMap<>();
        List<box_model> list = us.modelSearch(mname);
        res.put("data",list);
        return res;
    }
    @GetMapping("/modelType")
    public HashMap<String,Object> modelType(@RequestParam("mtid") String mtid){
        HashMap<String,Object> res = new HashMap<>();
        List<box_model> list = us.modelType(mtid);
        res.put("data",list);
        return res;
    }
    private static double Obj_fun1(double[] position) {
        double x = position[0];
        double y = position[1];
        return -(x * x + y * y+5*x-6*y); // 目标函数
    }
    @PostMapping("/uploadm71")
    public HashMap<String,Object> handleNumbers(@RequestBody Map<String, List<Double>> numbersMap) {
        HashMap<String,Object> res = new HashMap<>();
        List<Double> numbers = numbersMap.get("numbers");
        if (numbers == null || numbers.isEmpty()) {
            res.put("desc","失败");
        }
        double[][] array = new double[1000][1000];
        double[]arr2 = new double[1000];
        int m=0;
        Double[] numbersArray = numbers.toArray(new Double[0]);
        final int narvs = 2; // 变量个数
        double n,k;
        double c1,c2,w,vmax,x_lb,x_ub;
        n= numbers.get(0);
        c1=numbers.get(1);
        c2=numbers.get(2);
        w=numbers.get(3);
        k=numbers.get(4);
        vmax=numbers.get(5);
        x_lb=numbers.get(6);
        x_ub=numbers.get(7);
        double[][] x = new double[(int) n][narvs]; // 粒子位置
        double[][] v = new double[(int) n][narvs]; // 粒子的速度
        double[][] pbest = new double[(int) n][narvs]; // 个体最佳位置
        double[] gbest = new double[narvs]; // 全局最佳位置
        double[] fitness = new double[(int) n]; // 粒子适应度
        double[] bestFitness = new double[1]; // 最佳适应度值
        double[] pbest_fitness = new double[(int) n];
        double[][] good = new double[(int) (k*n)][];  //向前端传输所有的历史粒子
        int control =0;
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < narvs; j++) {
                x[i][j] = x_lb + (x_ub - x_lb) * rand.nextDouble(); // 随机初始化粒子位置
                v[i][j] = -vmax + 2 * vmax * rand.nextDouble(); // 随机初始化粒子速度
                pbest[i][j] = x[i][j]; // 将个体最优位置初始化为当前位置
            }
            fitness[i] = Obj_fun1(x[i]); // 计算适应度值
        }
        bestFitness[0] = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < n; i++) {
            if (fitness[i] > bestFitness[0]) {
                bestFitness[0] = fitness[i];
                System.arraycopy(x[i], 0, gbest, 0, narvs);
            }
        }
        for (int i = 0; i < n; i++) {
            pbest_fitness[i] = Double.NEGATIVE_INFINITY;
        }
        for (int d = 0; d < k; d++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < narvs; j++) {
                    v[i][j] = w * v[i][j] + c1 * rand.nextDouble() * (pbest[i][j] - x[i][j]) + c2 * rand.nextDouble() * (gbest[j] - x[i][j]);
                    v[i][j] = Math.max(Math.min(v[i][j], vmax), -vmax);
                    x[i][j] += v[i][j];
                    x[i][j] = Math.max(Math.min(x[i][j], x_ub), x_lb);
                }
                fitness[i] = Obj_fun1(x[i]);
            }
            double currentBestFitness = bestFitness[0];
            for (int i = 0; i < n; i++) {
                // 如果当前适应度更优，则更新个体最优
                if (fitness[i] > pbest_fitness[i]) {
                    pbest_fitness[i] = fitness[i];
                    for (int j = 0; j < narvs; j++) {
                        pbest[i][j] = x[i][j];
                    }
                    if (fitness[i] > currentBestFitness) {
                        currentBestFitness = fitness[i];
                        for (int j = 0; j < narvs; j++) {
                            gbest[j] = x[i][j];
                        }
                        bestFitness[0] = currentBestFitness;
                    }
                }
            }
            int iteration=d;

           //System.out.println("Iteration " + iteration + ":");
          for (int i = 0; i < n; i++) {
                //System.out.println("Particle " + i + ": Position = " + Arrays.toString(x[i]) + ", Fitness = " + fitness[i]);
                good[i+control]=x[i];
           }
            control+=n;
           //System.out.println("Global Best Position: " + Arrays.toString(gbest) + ", Fitness = " + bestFitness[0]);
          //System.out.println();
        }

       // double[][][] mm = new double[(int) (k*n)][][];
       double x1[]=new double[(int) (k*n)];
       double y1[]=new double[(int) (k*n)];
       double z1[]=new double[(int) (k*n)];
       for (int i = 0; i < k*n; i++) {
           x1[i]= good[i][0];
           y1[i]= good[i][1];
           z1[i]=-(x1[i] * x1[i] + y1[i] * y1[i]+5*x1[i]-6*y1[i]);
           //System.out.println("x"+x1[i]+"y"+y1[i]+"z"+z1[1]);
       }

     res.put("x",x1);
     res.put("y",y1);
     res.put("z",z1);
     res.put("k",k);
     res.put("n",n);

        res.put("bestposition",Arrays.toString(gbest));
        res.put("bestvalue",bestFitness[0]);
        return res;
    }

    @PostMapping("/uploadai")
    public HashMap<String, Object> uploadFileai(@RequestParam("file") MultipartFile file)throws IOException {
        HashMap<String, Object> res = new HashMap<>();
        try {
            InputStream inputStream = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            List<String> cellDataList = new ArrayList<>(); // 使用ArrayList来动态存储数据

            // 遍历Excel中的所有行和列，将数据保存在ArrayList中
            for (Row row : sheet) {
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case NUMERIC:
                            cellDataList.add(String.valueOf(cell.getNumericCellValue()));
                            break;
                        case STRING:
                            cellDataList.add(cell.getStringCellValue());
                            break;
                        // 可以根据需要添加其他类型的处理逻辑（如FORMULA, BOOLEAN等）
                    }
                }
            }

            String[] array = cellDataList.toArray(new String[0]); // 将ArrayList转换为数组

            final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();
            String url = "https://aip.baidubce.com/oauth/2.0/token?client_id=ej13ss6uEA1VkVajSwLzSj75&client_secret=o3CZcCPiMWddQcSe7qkNqT5atGnAllCv&grant_type=client_credentials" + "&charset=UTF-8";
            MediaType mediaType = MediaType.parse("application/json");
            okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url(url)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .build();
            Response response = HTTP_CLIENT.newCall(request).execute();
            //System.out.println(response.body().string());
            JSONObject js = new JSONObject(response.body().string());
            String access_token = js.getString("access_token");
            //System.out.println("access_token: " + access_token);
            //截止到这里已经成功获取了access_token的值了
            boxService.goresult ret = new boxService.goresult();

            String[] text = new String[array.length];
            for (int i = 0; i < array.length; i++) {
                text[i] = array[i];
                //System.out.println(text[i]);
            }

            ret.settext(text);
            ret.api(access_token);
            //System.out.println(ret.percent[0]);
            //System.out.println(ret.percent[1]);
            //System.out.println(ret.percent[2]);

            res.put("02",ret.percent[2]);
            res.put("01",ret.percent[1]);
            res.put("00",ret.percent[0]);
        } catch (Exception e) {
            e.printStackTrace();
            res.put("desc","失败");
        }
        return res;
    }

}

