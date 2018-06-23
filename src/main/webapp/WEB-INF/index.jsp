<%--
  Created by IntelliJ IDEA.
  User: huxiaoqiang
  Date: 2018/6/23
  Time: 下午3:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>Spark分布式过程挖掘界面</title>
    <link rel="stylesheet" href="lib/bootstrap/css/bootstrap.css">

    <script src="lib/jquery-3.3.1.min.js"></script>
    <script src="lib/bootstrap/js/bootstrap.js"></script>
    <script src="lib/vue.min.js"></script>
    <script src="js/index.js?v=2018"></script>

</head>
<body>
<header>
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <p class="navbar-text">Spark分布式挖掘界面</p>
            <p class="navbar-text"><a href="http://166.111.80.15:8080">任务监控</a></p>
            <p class="navbar-text"><a>结果显示</a></p>
        </div>
    </nav>
</header>


<div id="main-body" class="container" style="margin-top: 40px;">

    <div id="spark" class="page-header">
        <h1>页面说明</h1>
        <p>本页面用于Spark-PMBER框架算法的提交运行和结果查看，集群相关信息如下</p>
        <p>Spark Master: {{spark_master_url}}</p>
        <p>HDFS: {{hdfs_url}}</p>
    </div>

    <form id="form">
        <div class="form-group">
            <label>请选择挖掘算法</label>
            <div>
                <select v-model="algorithm">
                    <option disabled value="">请选择挖掘算法</option>
                    <option>Spark Aplha Miner</option>
                    <option>Spark Flexible Heuristic Miner</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label>请输入数据集目录</label>
            <input type="text" class="form-control" placeholder="请输入数据集路径" v-model="input_log">
        </div>
        <div>
            <label>请输入输出目录</label>
            <input type="text" class="form-control" placeholder="请输入输出路径" v-model="output">
        </div>

        <div class="form-group">
            <label>请配置适当的Spark资源</label>
            <label>executor内存大小/单位G</label>
            <input type="text" class="form-control" placeholder="--executor-memory" v-model="executor_memory">
            <label >驱动程序内存大小/单位G</label>
            <input type="text" class="form-control" placeholder="--driver-memory" v-model="driver_memory">
            <label>执行核数/单位个</label>
            <input type="text" class="form-control" placeholder="total-executor-cores" v-model="total_executor_cores">
        </div>
        <div class="form-group" v-if="seen">
            <label>请配置FHM参数</label>
            <div class="row">
                <div class="col-md-6">
                    <label>直接依赖阈值</label>
                    <input class="form-control" placeholder="直接依赖阈值" v-model="DeltaA">
                </div>
                <div class="col-md-6">
                    <label>1-循环阈值</label>
                    <input class="form-control" placeholder="1-循环阈值" v-model="DeltaL1L">
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <label>2-循环阈值</label>
                    <input class="form-control" placeholder="2-循环阈值" v-model="DeltaL2L">
                </div>
                <div class="col-md-6">
                    <label>长距离依赖阈值</label>
                    <input class="form-control" placeholder="长距离依赖阈值" v-model="DeltaLong">
                </div>
            </div>
            <div class="row">
                <div class="col-md-6">
                    <label>最相关关系阈值</label>
                    <input class="form-control" placeholder="最相关关系阈值" v-model="DeltaRel">
                </div>
            </div>

        </div>
        <button type="button" class="btn btn-default" @click="submit">提交</button>

    </form>
</div>

<footer style="margin-top:100px;">
    <footer class="footer navbar-fixed-bottom">
        <div class="container">
            <p class=" navbar-text navbar-right">© 清华大学软件学院信息技术与工程研究所BPM组</p>
        </div>
    </footer>
</footer>

</body>
</html>