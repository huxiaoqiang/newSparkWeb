package com.huxiaoqiang.sparkweb.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

import org.apache.spark.deploy.SparkSubmit;

import java.util.Map;

@Controller
@RequestMapping("/")
public class SparkController {

    private String Master = "spark://192.168.3.200:7077";

    @RequestMapping(value = {"/index", "/"})
    public String index() {
        return "index";
    }


    @RequestMapping(value = "/submit_alpha", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, String> submit_alpha(@RequestParam(value = "param") String param) {
        JSONObject jsonObject = JSONObject.parseObject(param);

        String name = "Spark Alpha Miner";
        String jarClass = "cn.edu.tsinghua.thssbpm.Main";
        String jar = "/home/ubuntu/IdeaProjects/SparkAlphaMiner/target/com.huxiaoqiang-1.0-SNAPSHOT.jar";
        String filePath = jsonObject.getString("input_log");
        String outFilePath = jsonObject.getString("output");
        String driverMemory = jsonObject.getString("driver_memory") + "G";
        String totalExecutorCores = jsonObject.getString("total_executor_cores");
        String executorMemory = jsonObject.getString("executor_memory") + "G";

        String[] SubmitString = new String[]{

                "--master", Master,
                "--name", name,
                "--executor-memory", executorMemory,
                "--driver-memory", driverMemory,
                "--total-executor-cores", totalExecutorCores,
                "--class", jarClass,
                "--jars", "/home/ubuntu/.m2/repository/com/github/scopt/scopt_2.11/3.7.0/scopt_2.11-3.7.0.jar",
                "--driver-library-path", "/home/ubuntu/.m2/repository/com/github/scopt/scopt_2.11/3.7.0/scopt_2.11-3.7.0.jar",
                jar,
                "--filePath", filePath,
                "--outfilePath", outFilePath
        };
        Map<String, String> result = new HashMap<String, String>();
        try {
            Runnable alpha = new SparkAlphaMiner(SubmitString);
            new Thread(alpha).start();
            result.put("status", "success");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            result.put("status", "failed");
            result.put("errorMsg", e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/submit_fhm", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, String> submit_fhm(@RequestParam(value = "param") String param) {
        JSONObject jsonObject = JSONObject.parseObject(param);

        String name = "Spark Flexible Heuristic Miner";
        String jarClass = "cn.edu.tsinghua.thssbpm.Main";
        String jar = "/home/ubuntu/IdeaProjects/SparkFHM/target/Spark-FHM-1.0-SNAPSHOT.jar";
        String filePath = jsonObject.getString("input_log");
        String outFilePath = jsonObject.getString("output");
        String driverMemory = jsonObject.getString("driver_memory") + "G";
        String totalExecutorCores = jsonObject.getString("total_executor_cores");
        String executorMemory = jsonObject.getString("executor_memory") + "G";

        String DeltaA = jsonObject.getString("executor_memory");
        String DeltaL1L = jsonObject.getString("DeltaL1L");
        String DeltaL2L = jsonObject.getString("DeltaL2L");
        String DeltaLong = jsonObject.getString("DeltaLong");
        String DeltaRel = jsonObject.getString("DeltaRel");

        String[] SubmitString = new String[]{
                "--master", Master,
                "--name", name,
                "--executor-memory", executorMemory,
                "--driver-memory", driverMemory,
                "--total-executor-cores", totalExecutorCores,
                "--class", jarClass,
                "--jars", "/home/ubuntu/.m2/repository/com/github/scopt/scopt_2.11/3.7.0/scopt_2.11-3.7.0.jar",
                "--driver-library-path", "/home/ubuntu/.m2/repository/com/github/scopt/scopt_2.11/3.7.0/scopt_2.11-3.7.0.jar",
                jar,
                "--filePath", filePath,
                "--outfilePath", outFilePath,
                "--DeltaA", DeltaA,
                "--DeltaL1L", DeltaL1L,
                "--DeltaL2L", DeltaL2L,
                "--DeltaLong", DeltaLong,
                "--DeltaRel", DeltaRel
        };
        Map<String, String> result = new HashMap<String, String>();
        try {
            Runnable fhm = new SparkFHM(SubmitString);
            new Thread(fhm).start();
            result.put("status", "success");
        } catch (Exception e) {
            result.put("status", "failed");
            result.put("errorMsg", e.getMessage());
        }
        return result;
    }
}

class SparkAlphaMiner implements Runnable {

    public String[] args;

    public SparkAlphaMiner(String[] args) {
        this.args = args;
    }

    public void run() {
        System.out.println("Start Spark Alpha Miner");
        try {
            SparkSubmit.main(this.args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class SparkFHM implements Runnable {

    public String[] args;

    public SparkFHM(String[] args) {
        this.args = args;
    }

    public void run() {
        System.out.println("Start Spark Flexible Heuristic Miner");
        try {
            SparkSubmit.main(this.args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}