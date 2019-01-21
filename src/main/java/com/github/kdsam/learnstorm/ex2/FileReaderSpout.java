package com.github.kdsam.learnstorm.ex2;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;

public class FileReaderSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;
    private boolean completed = false;
    private FileReader fileReader;
    private String str;
    private BufferedReader reader;

    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        try {
            this.fileReader = new FileReader(map.get("fileToRead").toString());
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error reading file [" + map.get("wordFile") + "]");
        }

        this.collector = spoutOutputCollector;
        this.reader = new BufferedReader(fileReader);
    }

    public void nextTuple() {
        if (!completed) {
            try {
                this.str = reader.readLine();
                if (this.str != null) {
                    this.collector.emit(new Values(str));
                } else {
                    completed = true;
                    fileReader.close();
                }
            } catch (Exception e) {
                throw new RuntimeException("Error reading tuple", e);
            }
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("word"));
    }

    @Override
    public void ack(Object msgId) {
    }

    @Override
    public void fail(Object msgId) {
    }
}
