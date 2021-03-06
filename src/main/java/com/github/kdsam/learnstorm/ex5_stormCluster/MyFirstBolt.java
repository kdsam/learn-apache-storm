package com.github.kdsam.learnstorm.ex5_stormCluster;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class MyFirstBolt extends BaseBasicBolt {
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        basicOutputCollector.emit(new Values(tuple.getInteger(0) * 2));
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("field"));
    }

    @Override
    public void cleanup() {

    }
}
