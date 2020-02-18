using GigaSpaces.Core.Metadata;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MasterWorkerModel
{
    [SpaceClass]
    public class Base
    {
        [SpaceIndex(Type = SpaceIndexType.Basic)]
        [SpaceProperty(NullValue = -1)]
        public int JobID { get; set; }

        [SpaceID(AutoGenerate = false)]
        public String TaskID { get; set; }
        public String Payload { get; set; }
        public String ServiceName { get; set; }
        public String FunctionName { get; set; }
        public Dictionary<String, String> Parameters { get; set; }
    }
}
