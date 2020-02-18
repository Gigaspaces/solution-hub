using GigaSpaces.Core;
using MasterWorkerModel;
using System;
using System.Threading;

namespace WorkerProject
{
    class WorkerHeartbeat
    {
        int timeout = 1000;
        public WorkerHeartbeat(int timeout)
        {
            this.timeout = timeout;
        }
        public void DoWork()
        {
            WorkerProcess workerProcess = new WorkerProcess();
            workerProcess.HostName = Worker.HostName;
            workerProcess.ProcessID = Worker.CurrentProcess.Id;
            workerProcess.ID = workerProcess.HostName + "=" + workerProcess.ProcessID;
            workerProcess.StartDateTime = DateTime.Now;
            workerProcess.LastUpdateDateTime = DateTime.Now; 
            Worker.SpaceProxy.Write(workerProcess, 5000);

            while (true)
            {
                workerProcess.StartDateTime = DateTime.Now;
                Worker.SpaceProxy.Change(new IdQuery<WorkerProcess>(workerProcess.ID), new ChangeSet().Set("LastUpdateDateTime", DateTime.Now).Lease(5000));
                Thread.Sleep(timeout);
            }
        }
    }
}
