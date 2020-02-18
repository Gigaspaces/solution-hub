using GigaSpaces.Core.Metadata;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MasterWorkerModel
{
    [SpaceClass]
    public class Job
    {
        [SpaceIndex(Type = SpaceIndexType.Basic)]
        [SpaceProperty(NullValue = -1)]
        [SpaceID(AutoGenerate = false)]
        public int JobID { get; set; }

        [SpaceProperty(NullValue = -1)]
        public int NumberOfTask { get; set; }
    }
}
