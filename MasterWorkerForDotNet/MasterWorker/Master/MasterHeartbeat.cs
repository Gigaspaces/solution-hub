using GigaSpaces.Core;
using MasterWorkerModel;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace MasterProject
{
    class MasterHeartbeat
    {
        int timeout = 1000;
        public MasterHeartbeat(int timeout)
        {
            this.timeout = timeout;
        }
        public void DoWork()
        {
            MasterProcess masterProcess = new MasterProcess();
            masterProcess.HostName = Master.HostName;
            masterProcess.ProcessID = Master.CurrentProcess.Id;
            masterProcess.ID = masterProcess.HostName + "=" + masterProcess.ProcessID;
            masterProcess.StartDateTime = DateTime.Now;
            masterProcess.LastUpdateDateTime = DateTime.Now;
            Master.SpaceProxy.Write(masterProcess, 5000);

            while (true)
            {
                masterProcess.StartDateTime = DateTime.Now;
                Master.SpaceProxy.Change(new IdQuery<MasterProcess>(masterProcess.ID), new ChangeSet().Set("LastUpdateDateTime", DateTime.Now).Lease(5000));
                Thread.Sleep(timeout);
            }
        }
    }
}
