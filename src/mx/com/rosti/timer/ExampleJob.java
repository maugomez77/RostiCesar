package mx.com.rosti.timer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Job;

public class ExampleJob implements Job {

  private int timeout;
  
  /**
   * Setter called after the ExampleJob is instantiated
   * with the value from the JobDetailBean (5)
   */ 
  public void setTimeout(int pTimeout) {
    this.timeout = pTimeout;
    System.out.println("timeout -> " + timeout);
  }
  
  public void execute(JobExecutionContext ctx) throws JobExecutionException {
      // do the actual work	  
	  TestScheduler();
	  System.out.println("Test Scheduler :D ");
  }
  
  public void TestScheduler(){
	  Date curDate=new Date();
	  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	  System.out.println("LOG: " + sdf.format(curDate));
	 }
}