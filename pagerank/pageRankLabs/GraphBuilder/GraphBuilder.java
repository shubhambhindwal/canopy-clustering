//
// Author - Jack Hebert (jhebert@cs.washington.edu)
// Copyright 2007
// Distributed under GPLv3
//
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.SequenceFileInputFormat;

import org.apache.nutch.parse.Parse;
import org.apache.nutch.parse.ParseException;
import org.apache.nutch.parse.ParseUtil;
import org.apache.nutch.protocol.Content;


public class GraphBuilder {

  public static void main(String[] args) {
    JobClient client = new JobClient();
    JobConf conf = new JobConf(GraphBuilder.class);
    conf.setJobName("Page-rank Graph Builder");


    conf.setOutputKeyClass(Text.class);
    conf.setOutputValueClass(Text.class);

    conf.setInputFormat(org.apache.hadoop.mapred.TextInputFormat.class);
    conf.setOutputFormat(org.apache.hadoop.mapred.SequenceFileOutputFormat.class);

    //conf.setInputPath(new Path("wikiTest"));
    conf.setInputPath(new Path("graph1"));
    conf.setOutputPath(new Path("graph2"));

    conf.setMapperClass(GraphBuilderMapper.class);
    conf.setReducerClass(GraphBuilderReducer.class);
    conf.setCombinerClass(GraphBuilderReducer.class);

    client.setConf(conf);
    try {
      JobClient.runJob(conf);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
