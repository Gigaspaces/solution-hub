using GigaSpaces.Core;
using MasterWorkerModel;
using System;
using System.Collections.Generic;
using System.Threading;

namespace MasterProject
{
    class GridService
    {
        Job job = null;
        JobResult jobResult = null;
        String serviceName;

        public GridService(String serviceName)
        {
            this.serviceName = serviceName;
        }
        public Job Submit(String functionName, Dictionary<String, String> paramters)
        {
            job = Master.submitJob(Master.NumberOfTask, serviceName, functionName, paramters);
            jobResult = new JobResult();
            jobResult.StartDateTime = DateTime.Now;
            jobResult.JobID = job.JobID;
            jobResult.JobStatus = "Started";
            Master.SpaceProxy.Write(jobResult);
            return job;
        }
        public ServiceData CollectNext(int timeout)
        {
            ServiceData serviceData = CollectNext(job.JobID, timeout, job.NumberOfTask);
            return serviceData;
        }
        public ServiceData CollectNext(int jobId, int timeout, int totalTask)
        {
            //Console.WriteLine("From GridService.CollectNext called for jobId=" + jobId);
            ServiceData serviceData = new ServiceData();
            Result reponseTemplate = new Result();
            reponseTemplate.JobID = jobId;
            int previousResultCount = 0;
            int newResultCount = 0;
            Result[] finalResult = new Result[totalTask];
            while (newResultCount < totalTask)
            {
                using (ITransaction txn = Master.TxnManager.Create(30000))
                {
                    try
                    {
                        Result[] resultA = Master.SpaceProxy.TakeMultiple(reponseTemplate);
                        if (resultA.Length > 0)
                        {
                            newResultCount += resultA.Length;

                            for (int i = previousResultCount; i < newResultCount; i++)
                            {
                                finalResult[i] = resultA[i - previousResultCount];
                            }
                            Console.WriteLine("From GridService.CollectNext for jobId=" + jobId + " numberOfResultsFound =" + serviceData.Data.Length + ", newResultCount=" + newResultCount);
                            previousResultCount = newResultCount;
                        }
                        txn.Commit();
                        Thread.Sleep(timeout);
                    }
                    catch (Exception e)
                    {
                        e.ToString();
                        // rollback the transaction
                        txn.Abort();
                    }
                }
            }
            Master.SpaceProxy.Take(job);
            jobResult.JobStatus = "Completed";
            Master.SpaceProxy.Write(jobResult);
            Console.WriteLine(DateTime.Now +
                " - Done executing Job and job object removed from space " + job.JobID);
            return serviceData;
        }

        //public void RemoveCompletedJob()
        //{
        //    Master.SpaceProxy.TakeById<Job>(job.JobID);
        //    Console.WriteLine(new DateTime() +
        //        " - Done executing Job and job object removed from space " + job.JobID);
        //    jobResult.JobStatus = "Completed";
        //    jobResult.EndDateTime = DateTime.Now;
        //    Master.SpaceProxy.Write(jobResult);
        //}
    }
}
