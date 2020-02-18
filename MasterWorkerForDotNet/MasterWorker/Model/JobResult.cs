using GigaSpaces.Core.Metadata;
using System;

namespace MasterWorkerModel
{
    [SpaceClass]
    public class JobResult
    {
        [SpaceIndex(Type = SpaceIndexType.Basic)]
        [SpaceID(AutoGenerate = false)]
        public int JobID { get; set; }
        public string JobStatus { get; set; }
        public DateTime StartDateTime { get; set; }
        public DateTime EndDateTime { get; set; }
    }
}
