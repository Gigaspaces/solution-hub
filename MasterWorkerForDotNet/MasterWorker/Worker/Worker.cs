using System;
using GigaSpaces.Core;
using GigaSpaces.XAP.Events;
using MasterWorkerModel;
using System.Diagnostics;
using System.Net;
using System.Threading;

namespace WorkerProject
{
    class Worker
    {
        public static ISpaceProxy SpaceProxy;
        public static int SleepTime;
        public static String HostName;
        public static Process CurrentProcess = null;
        public static int Timeout = 1000;

        public static void Main(string[] args)
        {
            HostName = Dns.GetHostName();
            CurrentProcess = Process.GetCurrentProcess();
            String url = args[0];
            Console.WriteLine("*** Connecting to remote space named '" + url + "' from Worker...");
            SpaceProxy = GigaSpacesFactory.FindSpace(url);

            WorkerHeartbeat masterHeartbeat = new WorkerHeartbeat(Timeout);
            Thread workerThread = new Thread(masterHeartbeat.DoWork);
            workerThread.Start();
            new Worker(args);
        }

        public Worker(string[] args)
        {
            Console.WriteLine(Environment.NewLine + "Welcome to XAP.NET 11 Worker!" + Environment.NewLine + " hostName=" + HostName + "currentProcess.Id="+CurrentProcess.Id);
            
            WorkerProcess workerProcess = new WorkerProcess();
            workerProcess.HostName = HostName;
            workerProcess.ProcessID = CurrentProcess.Id;
            workerProcess.ID = HostName + "=" + CurrentProcess.Id;
            workerProcess.StartDateTime = DateTime.Now;
            SpaceProxy.Write(workerProcess, 5000);

            String ioType = args.Length == 1 ? "NIO" : args[1];
            SleepTime = args.Length == 2 ? 2000 : Convert.ToInt32(args[2]);
            if (ioType.Equals("IO"))
            {
                IEventListenerContainer<Request> eventListenerContainer = EventListenerContainerFactory.CreateContainer<Request>(SpaceProxy, new WorkeIO());
                eventListenerContainer.Start();
            } else
            {
                IEventListenerContainer<Request> eventListenerContainer = EventListenerContainerFactory.CreateContainer<Request>(SpaceProxy, new WorkeNIO());
                eventListenerContainer.Start();
            }
        }
    }
}
