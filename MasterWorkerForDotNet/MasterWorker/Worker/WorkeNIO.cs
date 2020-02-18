using GigaSpaces.Core;
using GigaSpaces.XAP.Events;
using GigaSpaces.XAP.Events.Polling;
using GigaSpaces.XAP.Events.Polling.Receive;
using MasterWorkerModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace WorkerProject
{
    [PollingEventDriven(MinConcurrentConsumers = 1, MaxConcurrentConsumers = 1)]
    public class WorkeNIO
    {
        public WorkeNIO()
        {
            // Connect to space:
            Console.WriteLine("*** Worker started in NonBlocking IO mode.");
            Console.WriteLine();
        }

        [EventTemplate]
        public Request UnprocessedData
        {
            get
            {
                Request template = new Request();
                template.JobID = -1;
                return template;
            }
        }

        [DataEventHandler]
        public Result ProcessData(Request request)
        {
            Console.WriteLine("Worker.ProcessData called for " + request.JobID + " - " + request.TaskID);
            //process Data here and return processed data
            Thread.Sleep(Worker.SleepTime);

            Result result = new Result();
            result.JobID = request.JobID;
            result.TaskID = request.TaskID;
            return result;
        }

        [ReceiveHandler]
        public IReceiveOperationHandler<Request> ReceiveHandler()
        {
            TakeReceiveOperationHandler<Request> receiveHandler = new TakeReceiveOperationHandler<Request>();
            receiveHandler.NonBlocking = true;
            receiveHandler.NonBlockingFactor = 1;
            return receiveHandler;
        }
    }
}